package dao;

import com.mysql.cj.util.StringUtils;
import dao.interfaces.SpecialistDao;
import dto.UserDto;
import model.entity.Specialist;
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

public class SpecialistDaoImpl implements SpecialistDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Specialist specialist) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(specialist);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Specialist specialist) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(specialist);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Specialist specialist) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(specialist);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Specialist> getSpecialistById(int id) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Specialist> query = session.createQuery("from Specialist s where s.id=:id");
        query.setParameter("id", id);
        List<Specialist> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public List<Specialist> getSpecialistByEmail(String email) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Specialist> query = session.createQuery("from Specialist s where s.email=:email");
        query.setParameter("email", email);
        List<Specialist> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public List<Specialist> getAllSpecialists() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Specialist> query = session.createQuery("from Specialist ");
        List<Specialist> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public List<UserDto> filter(String name, String family, String email, int maxResult, int firstResult) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Specialist.class, "s");

        if (!StringUtils.isNullOrEmpty(name)) {
            criteria.add(Restrictions.eq("s.name", name));
        }

        if (!StringUtils.isNullOrEmpty(family)) {
            criteria.add(Restrictions.eq("s.family", family));
        }

        if (!StringUtils.isNullOrEmpty(email)) {
            criteria.add(Restrictions.eq("s.email", email));
        }

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("s.name"), "name")
                .add(Projections.property("s.family"), "family")
                .add(Projections.property("s.email"), "email")
                .add(Projections.property("s.balance"), "balance")
                .add(Projections.property("s.state"), "state")
        );

        criteria.setResultTransformer(Transformers.aliasToBean(UserDto.class));
       /* criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResult);*/

        List<UserDto> list = criteria.list();
        transaction.commit();
        session.close();
        return list;
    }
}
