package dao;

import dao.interfaces.OrderDao;
import model.entity.Customer;
import model.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(order);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(order);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Order> getCustomerOrder(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Order> query = session.createQuery("from Order o where o.customer.id=:id");
        query.setParameter("id", customer.getId());
        List<Order> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }
}
