package com.pluralsight;

import java.sql.*;
import java.util.Scanner;
import javax.sql.DataSource;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataSource dataSource = DatabaseConnector.getDataSource(); // Using DataSource

        try (Connection connection = dataSource.getConnection()) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            while (true) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                String input = scanner.nextLine();

                if (input.equals("1")) {
                    /////////////// EXERCISE 2: Display Products ///////////////
                    try (Statement statement = connection.createStatement();
                         ResultSet results = statement.executeQuery(
                                 "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products")) {

                        System.out.printf("%-5s %-30s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
                        System.out.println("--------------------------------------------------------------");

                        while (results.next()) {
                            int id = results.getInt("ProductID");
                            String name = results.getString("ProductName");
                            double price = results.getDouble("UnitPrice");
                            int stock = results.getInt("UnitsInStock");

                            System.out.printf("%-5d %-30s $%-9.2f %-10d%n", id, name, price, stock);
                        }
                    }

                } else if (input.equals("2")) {
                    ///////////// EXERCISE 3: Display Customers ////////////////
                    try (Statement statement = connection.createStatement();
                         ResultSet results = statement.executeQuery(
                                 "SELECT ContactName, CompanyName, City, Country, Phone FROM customers ORDER BY Country")) {

                        System.out.printf("%-25s %-30s %-20s %-20s %-15s%n",
                                "Contact Name", "Company Name", "City", "Country", "Phone");
                        System.out.println("---------------------------------------------------------------------------------------------");

                        while (results.next()) {
                            String contact = results.getString("ContactName");
                            String company = results.getString("CompanyName");
                            String city = results.getString("City");
                            String country = results.getString("Country");
                            String phone = results.getString("Phone");

                            System.out.printf("%-25s %-30s %-20s %-20s %-15s%n",
                                    contact, company, city, country, phone);
                        }
                    }

                } else if (input.equals("3")) {
                    ///////////// EXERCISE 4: Display Categories and Products by Category ////////////
                    try (Statement statement = connection.createStatement();
                         ResultSet categories = statement.executeQuery(
                                 "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID")) {

                        System.out.printf("%-10s %-30s%n", "Category ID", "Category Name");
                        System.out.println("-----------------------------------------");

                        while (categories.next()) {
                            int categoryId = categories.getInt("CategoryID");
                            String categoryName = categories.getString("CategoryName");

                            System.out.printf("%-10d %-30s%n", categoryId, categoryName);
                        }
                    }

                    System.out.print("\nEnter a Category ID to view its products: ");
                    int selectedCategoryId = Integer.parseInt(scanner.nextLine());

                    String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                            "FROM products WHERE CategoryID = ?";

                    try (PreparedStatement ps = connection.prepareStatement(query)) {
                        ps.setInt(1, selectedCategoryId);
                        try (ResultSet results = ps.executeQuery()) {

                            System.out.printf("%-5s %-30s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
                            System.out.println("--------------------------------------------------------------");

                            while (results.next()) {
                                int id = results.getInt("ProductID");
                                String name = results.getString("ProductName");
                                double price = results.getDouble("UnitPrice");
                                int stock = results.getInt("UnitsInStock");

                                System.out.printf("%-5d %-30s $%-9.2f %-10d%n", id, name, price, stock);
                            }
                        }
                    }

                } else if (input.equals("0")) {
                    System.out.println("Goodbye!");
                    break;

                } else {
                    System.out.println("Invalid option. Please choose 0, 1, 2, or 3.");
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter a number.");
        } finally {
            scanner.close();
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