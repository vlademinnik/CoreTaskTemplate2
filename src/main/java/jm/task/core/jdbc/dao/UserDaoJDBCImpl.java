package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String str1 = "CREATE TABLE IF NOT EXISTS users_table" +
                "(ID BIGINT NOT NULL AUTO_INCREMENT," +
                " NAME VARCHAR(45) NOT NULL ," +
                " LAST_NAME VARCHAR(45) NOT NULL, " +
                " AGE TINYINT NOT NULL," +
                "PRIMARY KEY (ID))";
        try {
            connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(str1);
            preparedStatement.executeUpdate();
            System.out.printf("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void dropUsersTable() {
        String str2 = "DROP TABLE IF EXISTS users_table";
        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(str2);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String str3 = "INSERT INTO users_table (NAME, LAST_NAME, AGE) values (?,?,?)";
        try {
            connection = Util.getConnection();
            PreparedStatement prst = connection.prepareStatement(str3);
            prst.setString(1, name);
            prst.setString(2, lastName);
            prst.setByte(3, age);
            prst.executeUpdate();
            System.out.println("User добавлен");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeUserById(long id) {
        String str4 = "DELETE FROM users_table WHERE id = ?";
        try {
            connection = Util.getConnection();
            PreparedStatement prst = connection.prepareStatement(str4);
            prst.setLong(1, id);
            prst.executeUpdate();
            System.out.println("User удален по id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            connection = Util.getConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM users_table");
            while (rs.next()) {
                User u = new User(rs.getString("name"), rs.getString("last_Name"),
                        rs.getByte("age"));
                u.setId(rs.getLong("id"));
                users.add(u);
            }
            System.out.println("Получение users из таблицы");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE users_table");
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
