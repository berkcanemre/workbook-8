package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind"; // connection string
        String username = "root"; // replace with your MySQL username
        String password = "yearup"; // replace with your MySQL password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            //////////////// EXERCISE 1 CODE (Preserved) //////////////////
            /*
            Statement statement = connection.createStatement();
            String query = "SELECT ProductName FROM products";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String productName = results.getString("ProductName");
                System.out.println(productName);
            }
            */
            ///////////////////////////////////////////////////////////////
            /////////////// EXERCISE 2 CODE (Expanded Functionality) ///////////////

            Statement statement = connection.createStatement();
            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";
            ResultSet results = statement.executeQuery(query);
            System.out.printf("%-5s %-30s %-10s %-10s%n", "Id", "Name", "Price", "Stock");

            while (results.next()) {
                int id = results.getInt("ProductID");
                String name = results.getString("ProductName");
                double price = results.getDouble("UnitPrice");
                int stock = results.getInt("UnitsInStock");

                System.out.printf("%-5d %-30s $%-9.2f %-10d%n", id, name, price, stock);
            }
            //////////////////////////////////////////////////////////////////////////////
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