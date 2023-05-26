package com.example.task2_goi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

public class MyJDBC {
    public static void executeQuery(String queryOrder) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            int rows = statement.executeUpdate(queryOrder);
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
    }

    public static Queue<Integer> getFromDatabase() {
        Queue<Integer> queue = new LinkedList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id FROM basic_table");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                queue.add(id);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
        return queue;
    }

    public static int checkDatabaseCount() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM basic_table");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0) {
                    return 0; // Table is empty
                } else {
                    return -2; // Table has records
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking count: " + e.getMessage());
        }
        return -3; // Error occurred
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/task2", "root", "");
    }
}
