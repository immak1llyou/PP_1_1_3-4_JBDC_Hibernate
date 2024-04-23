package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/katadb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1mkr26kv22";

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            System.err.println("Не удалось загрузить класс драйвера!");
        }
        return null;
    }

//    public static void connectionIsClosed() {
//        try {
//            if (!connection.isClosed() && connection != null) {
//                connection.close();
//                connection = null;
//                System.out.println("Соединение успешно закрыто.");
//            }
//        } catch (SQLException e) {
//            System.err.println("Ошибка закрытия соединения\n" + e);
//        }
//    }
}
