package dao;

import dao.interfaces.SpecialistDao;
import model.entity.Specialist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
    public List<Specialist> getAllSpecialists() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Specialist> query = session.createQuery("from Specialist ");
        List<Specialist> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }
}
