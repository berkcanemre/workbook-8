package com.pluralsight;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataManager dataManager = new DataManager(DatabaseConnector.getDataSource());

        System.out.print("Enter an actor's last name to search: ");
        String lastName = scanner.nextLine();

        List<Actor> actors = dataManager.findActorsByLastName(lastName);

        if (actors.isEmpty()) {
            System.out.println("No actors found with last name: " + lastName);
            return;
        }

        System.out.println("\nMatching actors:");
        for (Actor actor : actors) {
            System.out.println(actor);
        }

        System.out.print("\nEnter the actor ID to see their films: ");
        int actorId = scanner.nextInt();
        scanner.nextLine(); // clear newline

        List<Film> films = dataManager.getFilmsByActorId(actorId);

        if (films.isEmpty()) {
            System.out.println("No films found for that actor.");
        } else {
            System.out.println("\nFilms:");
            for (Film film : films) {
                System.out.println(film + "\n");
            }
        }

        scanner.close();
    }
}