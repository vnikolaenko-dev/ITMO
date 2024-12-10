package net.proselyte.web3.db;

import net.proselyte.web3.db.models.PointModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PointDao {
    private Session session;
    public PointDao(){
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }
    public PointModel findById(int id) {
        return session.get(PointModel.class, id);
    }

    public void save(PointModel point) {
        Transaction tx1 = session.beginTransaction();
        session.save(point);
        tx1.commit();
    }

    public void update(PointModel point) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(point);
        tx1.commit();
    }

    public void delete(PointModel point) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(point);
        tx1.commit();
    }


    public List<PointModel> findAll() {
        List<PointModel> users = (List<PointModel>)  session.createQuery("From PointModel ").list();
        return users;
    }
}
