package dao;

import dao.interfaces.HomeServiceDao;
import model.entity.HomeService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class HomeServiceDaoImpl implements HomeServiceDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(HomeService service) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(service);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(HomeService service) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(service);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(HomeService service) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(service);
        transaction.commit();
        session.close();
    }

    @Override
    public List<HomeService> getServiceById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<HomeService> query = session.createQuery("from HomeService s where s.id=:id");
        query.setParameter("id", id);
        List<HomeService> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public List<HomeService> getAllServices() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<HomeService> query = session.createQuery("from HomeService");
        List<HomeService> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public int getSpecialistById(int id, HomeService homeService) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sqlQuery = String.format("select home_service_id from home_service_specialist " +
                        "where specialist_list_id='%d'",id);
        int i = session.createSQLQuery(sqlQuery).getMaxResults();
        transaction.commit();
        session.close();
        return i;
    }
}
