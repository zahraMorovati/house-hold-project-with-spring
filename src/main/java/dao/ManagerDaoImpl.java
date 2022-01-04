package dao;

import dao.interfaces.ManagerDao;
import model.entity.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class ManagerDaoImpl implements ManagerDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Manager manager) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(manager);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Manager manager) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(manager);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Manager manager) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(manager);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Manager> getAllManagers() {
        return null;
    }

    @Override
    public List<Manager> getManagerById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Manager> query = session.createQuery("from Manager m where m.id=:id");
        query.setParameter("id", id);
        List<Manager> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public List<Manager> getManagerByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Manager> query = session.createQuery("from Manager m where m.email=:email and m.password=:password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<Manager> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }


}
