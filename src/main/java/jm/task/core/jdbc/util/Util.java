package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    private static final Configuration configuration = new Configuration().addAnnotatedClass(User.class);
    private static final SessionFactory sessionFactory = configuration.buildSessionFactory();

    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
