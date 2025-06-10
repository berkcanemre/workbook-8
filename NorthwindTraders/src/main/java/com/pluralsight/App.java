package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup";

        Connection connection = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open connection
            connection = DriverManager.getConnection(url, username, password);

            // Menu loop
            boolean running = true;
            while (running) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        displayProducts(connection);
                        break;
                    case "2":
                        displayCustomers(connection);
                        break;
                    case "0":
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                        break;
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error.");
            e.printStackTrace();
        } finally {
            // Close connection
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection.");
            }
        }
    }

    private static void displayProducts(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";
            ResultSet results = statement.executeQuery(query);

            System.out.printf("%-5s %-30s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("--------------------------------------------------------------");

            while (results.next()) {
                int id = results.getInt("ProductID");
                String name = results.getString("ProductName");
                double price = results.getDouble("UnitPrice");
                int stock = results.getInt("UnitsInStock");

                System.out.printf("%-5d %-30s $%-9.2f %-10d%n", id, name, price, stock);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving products.");
        }
    }

    private static void displayCustomers(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM customers ORDER BY Country";
            ResultSet results = statement.executeQuery(query);

            System.out.printf("%-25s %-35s %-20s %-15s %-15s%n",
                    "Contact Name", "Company", "City", "Country", "Phone");
            System.out.println("----------------------------------------------------------------------------------------------");

            while (results.next()) {
                String contact = results.getString("ContactName");
                String company = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");

                System.out.printf("%-25s %-35s %-20s %-15s %-15s%n",
                        contact, company, city, country, phone);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customers.");
        }
    }

    ////////////////////// EXERCISE 1 CODE (Preserved Below) //////////////////////
    /*
    Statement statement = connection.createStatement();
    String query = "SELECT ProductName FROM products";
    ResultSet results = statement.executeQuery(query);

    while (results.next()) {
        String productName = results.getString("ProductName");
        System.out.println(productName);
    }
    */
    ////////////////////////////////////////////////////////////////////////////////
}