package dao;

import com.mysql.cj.util.StringUtils;
import dao.interfaces.ManagerDao;
import dto.ManagerDto;
import dto.UserDto;
import model.entity.Customer;
import model.entity.Manager;
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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Manager> query = session.createQuery("from Manager");
        List<Manager> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
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

    @Override
    public List<ManagerDto> filter(String name, String family, String email, int maxResult, int firstResult) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Manager.class, "m");

        if (!StringUtils.isNullOrEmpty(name)) {
            criteria.add(Restrictions.eq("m.name", name));
        }

        if (!StringUtils.isNullOrEmpty(family)) {
            criteria.add(Restrictions.eq("m.family", family));
        }

        if (!StringUtils.isNullOrEmpty(email)) {
            criteria.add(Restrictions.eq("m.email", email));
        }

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("m.name"), "name")
                .add(Projections.property("m.family"), "family")
                .add(Projections.property("m.email"), "email")
        );

        criteria.setResultTransformer(Transformers.aliasToBean(ManagerDto.class));
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResult);

        List<ManagerDto> list = criteria.list();
        transaction.commit();
        session.close();
        return list;
    }


}
