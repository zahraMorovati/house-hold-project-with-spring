package dao;

import com.mysql.cj.util.StringUtils;
import dao.interfaces.CustomerDao;
import dto.UserDto;
import model.entity.Customer;
import model.entity.Order;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
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
    public List<Customer> getCustomerByEmail(String email) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Customer> query = session.createQuery("from Customer c where c.email=:email");
        query.setParameter("email", email);
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

    @Override
    public List<UserDto> filter(String name, String family, String email, int maxResult, int firstResult) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class, "c");

        if (!StringUtils.isNullOrEmpty(name)) {
            criteria.add(Restrictions.eq("c.name", name));
        }

        if (!StringUtils.isNullOrEmpty(family)) {
            criteria.add(Restrictions.eq("c.family", family));
        }

        if (!StringUtils.isNullOrEmpty(email)) {
            criteria.add(Restrictions.eq("c.email", email));
        }

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("c.name"), "name")
                .add(Projections.property("c.family"), "family")
                .add(Projections.property("c.email"), "email")
                .add(Projections.property("c.balance"), "balance")
                .add(Projections.property("c.state"), "state")
        );

        criteria.setResultTransformer(Transformers.aliasToBean(UserDto.class));
        /*criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResult);*/

        List<UserDto> list = criteria.list();
        transaction.commit();
        session.close();
        return list;
    }

}
