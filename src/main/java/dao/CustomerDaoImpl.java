package dao;

import dao.interfaces.CustomerDao;
import model.entity.Customer;
import model.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(customer);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(customer);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(customer);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Customer> getCustomerById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Customer> query = session.createQuery("from Customer c where c.id=:id");
        query.setParameter("id", id);
        List<Customer> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public List<Customer> getAllCustomers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Customer> query = session.createQuery("from Customer ");
        List<Customer> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public void addOrder(Customer customer, Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        customer.getOrders().add(order);
        session.update(customer);
        transaction.commit();
        session.close();
    }
}
