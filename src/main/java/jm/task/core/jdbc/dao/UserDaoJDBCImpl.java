package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private final Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "CREATE TABLE IF NOT EXISTS users (id int NOT NULL AUTO_INCREMENT, name varchar(45), lastname varchar(45), age tinyint, PRIMARY KEY (id));";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void dropUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "DROP TABLE IF EXISTS users;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM users WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> allUsers = new ArrayList<>();

        String query = "Select id, name, lastname, age FROM users";

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));

                allUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    public void cleanUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "TRUNCATE TABLE users;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
