package com.github.it89.investordaybookspring.dao.impls;

import com.github.it89.investordaybookspring.dao.entities.DealEntity;
import com.github.it89.investordaybookspring.dao.entities.SecurityEntity;
import com.github.it89.investordaybookspring.dao.interfaces.DealDAO;
import com.github.it89.investordaybookspring.dao.interfaces.SecurityDAO;
import com.github.it89.investordaybookspring.daybook.stockmarket.Deal;
import com.github.it89.investordaybookspring.daybook.stockmarket.Security;
import com.github.it89.investordaybookspring.main.Run;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DealDAOImpl implements DealDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void merge(DealEntity entity) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        DealEntity dealEntity = getEntityByNumber(entity.getDealNumber());
        if (dealEntity == null) {
            session.save(entity);
        } else {
            dealEntity.setSecurity(entity.getSecurity());
            dealEntity.setDealNumber(entity.getDealNumber());
            dealEntity.setDateTime(entity.getDateTime());
            dealEntity.setOperation(entity.getOperation());
            dealEntity.setAmount(entity.getAmount());
            dealEntity.setVolume(entity.getVolume());
            dealEntity.setCommission(entity.getCommission());
            dealEntity.setPrice(entity.getPrice());
            dealEntity.setPricePct(entity.getPricePct());
            dealEntity.setAccumulatedCouponYield(entity.getAccumulatedCouponYield());
            session.update(dealEntity);
        }

        tx.commit();
        session.close();
    }

    @Override
    public DealEntity getEntityByNumber(String dealNumber) {
        DealEntity result;
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from DealEntity where dealNumber = :dealNumber").setString("dealNumber", dealNumber);
        List<DealEntity> entityList = (List<DealEntity>) query.list();
        if (entityList.isEmpty()) {
            result = null;
        } else if (entityList.size() == 1) {
            result = entityList.get(0);
        } else {
            throw new IllegalStateException("Too many rows in Deal with number = " + dealNumber);
        }

        tx.commit();
        session.close();
        return result;
    }

    @Override
    public List<Deal> getList() {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from DealEntity");
        List<DealEntity> entityList = (List<DealEntity>) query.list();
        List<Deal> dealList = new ArrayList<>();
        for(DealEntity dealEntity : entityList) {
            dealList.add(dealEntity.toDeal());
        }

        tx.commit();
        session.close();
        return dealList;
    }

    @Override
    public List<Deal> getListBySecurity(Security security) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        SecurityDAO securityDAO = Run.securityDAO;

        SecurityEntity securityEntity = securityDAO.getEntityByISIN(security.getIsin());
        Query query = session.createQuery("from DealEntity where security = :security_id").setString("security_id", Long.toString(securityEntity.getId()));
        List<DealEntity> entityList = (List<DealEntity>) query.list();
        List<Deal> dealList = new ArrayList<>();
        for(DealEntity dealEntity : entityList) {
            dealList.add(dealEntity.toDeal());
        }

        tx.commit();
        session.close();
        return dealList;
    }
}
