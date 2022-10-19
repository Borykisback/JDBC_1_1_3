package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lesson1_1_3";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }
}
