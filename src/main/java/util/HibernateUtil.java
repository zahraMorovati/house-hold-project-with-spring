package util;

import model.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            try {
                DatabaseUtil.creatDatabase();
                Configuration configuration = new Configuration();
                configuration.setPhysicalNamingStrategy(new SnakeCasePhysicalNamingStrategy());
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/maktab_58_db?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "george1378");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Address.class);
                configuration.addAnnotatedClass(Comment.class);
                configuration.addAnnotatedClass(Customer.class);
                configuration.addAnnotatedClass(Manager.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(HomeService.class);
                configuration.addAnnotatedClass(Specialist.class);
                configuration.addAnnotatedClass(Suggestion.class);


                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                System.out.println("Hibernate Java Config serviceRegistry created");
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                return sessionFactory;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }


}
