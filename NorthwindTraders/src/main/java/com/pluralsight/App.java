package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup";

        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();
            String query = "SELECT ProductName FROM products";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String productName = results.getString("ProductName");
                System.out.println(productName);
            }

            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error.");
            e.printStackTrace();
        }
    }
}