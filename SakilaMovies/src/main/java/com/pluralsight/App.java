package com.pluralsight;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataSource dataSource = DatabaseConnector.getDataSource();

        try (Connection conn = dataSource.getConnection()) {
            // Step 1: Ask for last name
            System.out.print("Enter the last name of an actor: ");
            String lastName = scanner.nextLine();

            String actorQuery = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(actorQuery)) {
                stmt.setString(1, lastName);
                try (ResultSet results = stmt.executeQuery()) {
                    if (results.next()) {
                        System.out.println("\nMatching actors:");
                        do {
                            int id = results.getInt("actor_id");
                            String firstName = results.getString("first_name");
                            String lName = results.getString("last_name");
                            System.out.println(id + ": " + firstName + " " + lName);
                        } while (results.next());
                    } else {
                        System.out.println("No actors found with last name: " + lastName);
                        return;
                    }
                }
            }

            // Step 2: Ask for first + last name
            System.out.print("\nEnter the FIRST name of the actor to see their movies: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter the LAST name of the actor: ");
            String fullLastName = scanner.nextLine();

            String filmQuery = """
                SELECT f.title
                FROM film f
                JOIN film_actor fa ON f.film_id = fa.film_id
                JOIN actor a ON a.actor_id = fa.actor_id
                WHERE a.first_name = ? AND a.last_name = ?
                ORDER BY f.title
                """;

            try (PreparedStatement stmt = conn.prepareStatement(filmQuery)) {
                stmt.setString(1, firstName);
                stmt.setString(2, fullLastName);

                try (ResultSet results = stmt.executeQuery()) {
                    if (results.next()) {
                        System.out.println("\nFilms featuring " + firstName + " " + fullLastName + ":");
                        do {
                            System.out.println("- " + results.getString("title"));
                        } while (results.next());
                    } else {
                        System.out.println("No films found for that actor.");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}