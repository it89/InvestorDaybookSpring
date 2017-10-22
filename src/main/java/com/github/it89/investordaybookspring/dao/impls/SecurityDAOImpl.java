package com.github.it89.investordaybookspring.dao.impls;

import com.github.it89.investordaybookspring.dao.entities.SecurityEntity;
import com.github.it89.investordaybookspring.dao.interfaces.SecurityDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
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
            securityEntity.setCode(entity.getCode());
            securityEntity.setCaption(entity.getCaption());
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
}
