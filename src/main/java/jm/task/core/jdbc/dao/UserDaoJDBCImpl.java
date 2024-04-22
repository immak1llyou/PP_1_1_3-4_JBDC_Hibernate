package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String quary = "USE katadb";
    private static final String quary1 = "CREATE TABLE IF NOT EXISTS Users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), lastname VARCHAR(255), age TINYINT)";
    private static final String quary2 = "DROP TABLE Users";
    private static final String quary3 = "INSERT INTO Users VALUES (?, ?, ?, ?)";
    private static final String quary4 = "DELETE FROM Users WHERE id = ?";
    private static final String quary5 = "TRUNCATE TABLE Users";
    private static final String quary6 = "SHOW TABLES LIKE 'Users'";

    private List<User> userList = new ArrayList<>();

    Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(quary6);
            if (!resultSet.next()) {
                connection.createStatement().execute(quary);
                connection.createStatement().execute(quary1);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы\n" + e);
        }
    }

    public void dropUsersTable() {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(quary6);
            if (!resultSet.next()) {
                connection.createStatement().execute(quary2);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка удаления таблицы\n" + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        userList.add(new User(name,lastName,age));
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(quary3);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.err.println("Ошибка сохранения пользователя с именем " + name + "\n" + e);
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(quary4);
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            System.err.println("Ошибка удаления User'a с id = " + id + "\n" + e);
        }
    }

    public List<User> getAllUsers() {
        return userList;
    }
    public void cleanUsersTable() {
        try {
            if (!getAllUsers().isEmpty()) {
                connection.createStatement().execute(quary5);
                userList.removeAll(userList);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка очистки таблицы\n" + e);
        }
    }
}
