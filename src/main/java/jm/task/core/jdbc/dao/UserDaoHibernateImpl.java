package jm.task.core.jdbc.dao;

import com.mysql.cj.Query;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static Transaction transaction = null;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS User (id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255), lastname VARCHAR(255), age TINYINT)").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS User").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("from User").list();
            transaction.commit();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE User").executeUpdate();
            transaction.commit();
        }
    }
}
