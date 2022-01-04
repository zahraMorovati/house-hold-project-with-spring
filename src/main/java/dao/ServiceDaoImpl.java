package dao;

import dao.interfaces.ServiceDao;
import model.entity.Customer;
import model.entity.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class ServiceDaoImpl implements ServiceDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Service service) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(service);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Service service) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(service);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Service service) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(service);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Service> getServiceById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Service> query = session.createQuery("from Service s where s.id=:id");
        query.setParameter("id", id);
        List<Service> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public List<Service> getAllServices() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Service> query = session.createQuery("from Service");
        List<Service> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }
}
