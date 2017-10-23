package com.github.it89.investordaybookspring.dao.impls;

import com.github.it89.investordaybookspring.dao.entities.SecurityEntity;
import com.github.it89.investordaybookspring.dao.interfaces.SecurityDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SecurityDAOImpl implements SecurityDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void merge(SecurityEntity entity) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        SecurityEntity securityEntity = getEntityByISIN(entity.getIsin());
        if (securityEntity == null) {
            session.save(entity);
        } else {
            securityEntity.setTicker(entity.getTicker());
            securityEntity.setCaption(entity.getCaption());
            securityEntity.setCodeGRN(entity.getCodeGRN());
            session.update(securityEntity);
        }

        tx.commit();
        session.close();
    }

    @Override
    public SecurityEntity getEntityByISIN(String isin) {
        SecurityEntity result;
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from SecurityEntity where isin = :isin").setString("isin", isin);
        List<SecurityEntity> entityList = (List<SecurityEntity>) query.list();
        if (entityList.isEmpty()) {
            result = null;
        } else if (entityList.size() == 1) {
            result = entityList.get(0);
        } else {
            throw new IllegalStateException("Too many rows in Security with isin = " + isin);
        }

        tx.commit();
        session.close();
        return result;
    }

    @Override
    public SecurityEntity getEntityByCodeGRN(String codeGRN) {
        SecurityEntity result;
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from SecurityEntity where codeGRN = :codeGRN").setString("codeGRN", codeGRN);
        List<SecurityEntity> entityList = (List<SecurityEntity>) query.list();
        if (entityList.isEmpty()) {
            result = null;
        } else if (entityList.size() == 1) {
            result = entityList.get(0);
        } else {
            throw new IllegalStateException("Too many rows in Security with codeGRN = " + codeGRN);
        }

        tx.commit();
        session.close();
        return result;
    }

    @Override
    public SecurityEntity getEntityByCaption(String caption) {
        SecurityEntity result;
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from SecurityEntity where caption = :caption").setString("caption", caption);
        List<SecurityEntity> entityList = (List<SecurityEntity>) query.list();
        if (entityList.isEmpty()) {
            result = null;
        } else if (entityList.size() == 1) {
            result = entityList.get(0);
        } else {
            throw new IllegalStateException("Too many rows in Security with caption = " + caption);
        }

        tx.commit();
        session.close();
        return result;
    }
}
