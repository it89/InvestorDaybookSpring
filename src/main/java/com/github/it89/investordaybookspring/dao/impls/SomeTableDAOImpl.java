package com.github.it89.investordaybookspring.dao.impls;

import com.github.it89.investordaybookspring.dao.entities.SomeTableEntity;
import com.github.it89.investordaybookspring.dao.interfaces.SomeTableDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SomeTableDAOImpl implements SomeTableDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(SomeTableEntity t) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(t);
        tx.commit();
        session.close();
    }

    @Override
    public List<SomeTableEntity> list() {
        Session session = this.sessionFactory.openSession();
        List<SomeTableEntity> SomeTableEntityList = (List<SomeTableEntity>) session.createQuery("from SomeTableEntity").list();
        session.close();
        return SomeTableEntityList;
    }
}
