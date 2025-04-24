package ie.atu.Football_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class FPD_Main {
    public static void main(String[] args) {
        // Hardcoded credentials
        String correctUsername = "admin";
        String correctPassword = "password123";

        String correctTeam = "Al-Nasar";
        String correctPlayer = "Ronaldo";

        Scanner scanner = new Scanner(System.in);

        // Prompt user for credentials
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check credentials
        if (username.equals(correctUsername) && password.equals(correctPassword)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }

        System.out.println("Enter in a Team name: ");
        String teamName = scanner.nextLine();

        System.out.println("Enter in a player name:  ");
        String playerName = scanner.nextLine();

        if (teamName.equals(correctTeam) && playerName.equals(correctPlayer)) {
            System.out.println("Team and player names match.!");
        }else{
            System.out.println("Invalid team or player name.");
        }

        System.out.println("Choose the following Options: ");
        System.out.println("1. Performance");
        System.out.println("2. Disaplinary");
        System.out.println("3. PlayerID");
        System.out.println("4. Player Rating");
        int option = scanner.nextInt();

        if (option == 1) {
            System.out.println("Entering option One");
            System.out.println("Connecting.......");
        } else if (option == 2) {
            System.out.println("Entering option Two");
            System.out.println("Connecting.......");
        } else if (option == 3) {
            System.out.println("Entering option Three");
            System.out.println("Connecting.......");
        } else if (option == 4) {
            System.out.println("Entering option Four");
            System.out.println("Connecting.......");
        }else{
            System.out.println("Invalid option.");
        }


        scanner.close();
    }
}