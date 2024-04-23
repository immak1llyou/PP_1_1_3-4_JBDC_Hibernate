package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String quaryCreateTable = "CREATE TABLE IF NOT EXISTS Users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), lastname VARCHAR(255), age TINYINT)";
    private static final String quaryDropTable = "DROP TABLE IF EXISTS Users";
    private static final String quaryInsertUser = "INSERT INTO Users VALUES (id, ?, ?, ?)";
    private static final String quaryDeleteUser = "DELETE FROM Users WHERE id = ?";
    private static final String quaryCleanTable = "TRUNCATE TABLE Users";
    private static final String quaryGetUser = "SELECT * FROM Users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            assert connection != null;
            connection.createStatement().execute(quaryCreateTable);
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы\n" + e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            assert connection != null;
            connection.createStatement().execute(quaryDropTable);
        } catch (SQLException e) {
            System.err.println("Ошибка удаления таблицы\n" + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(quaryInsertUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User с именем — " + name + " добавлен в базу данных");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка сохранения пользователя с именем " + name + "\n" + e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(quaryDeleteUser);
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            System.err.println("Ошибка удаления User'a с id = " + id + "\n" + e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(quaryGetUser);
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getByte("age")));
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            assert connection != null;
            connection.createStatement().execute(quaryCleanTable);
            if (!getAllUsers().isEmpty()) {
                connection.createStatement().execute(quaryCleanTable);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка очистки таблицы\n" + e);
        }
    }
}
