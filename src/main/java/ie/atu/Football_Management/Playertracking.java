package ie.atu.Football_Management;

import java.sql.*;
import java.util.Scanner;

public class Playertracking {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Football Player Database Search");
        System.out.print("Enter player name: ");
        String playerName = scanner.nextLine();

        searchPlayer(playerName, scanner);

        scanner.close();
    }

    public static void searchPlayer(String playerName, Scanner scanner) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DatabaseCentral.getConnection()) {

                // 1. First query to get basic player info
                String playerQuery = "SELECT player_id, player_name, nationality, position, age, market_value " +
                        "FROM players WHERE player_name LIKE ?";

                try (PreparedStatement playerStmt = connection.prepareStatement(playerQuery)) {
                    playerStmt.setString(1, "%" + playerName + "%");
                    ResultSet playerRs = playerStmt.executeQuery();

                    boolean found = false;

                    while (playerRs.next()) {
                        found = true;
                        System.out.println("\nPlayer Found:");
                        System.out.println("Name: " + playerRs.getString("player_name"));
                        System.out.println("Nationality: " + playerRs.getString("nationality"));
                        System.out.println("Position: " + playerRs.getString("position"));
                        System.out.println("Age: " + playerRs.getInt("age"));
                        System.out.println("Market Value: " + playerRs.getString("market_value"));

                        // Ask if user wants additional statistics after showing basic info
                        System.out.print("\nWould you like to see additional statistics? (y/n): ");
                        String response = scanner.nextLine().trim().toLowerCase();

                        if (response.equals("y") || response.equals("yes")) {
                            String statsQuery = "SELECT goals, assists, red_cards, yellow_cards " +
                                    "FROM statistics WHERE player_id = ?";

                            try (PreparedStatement statsStmt = connection.prepareStatement(statsQuery)) {
                                statsStmt.setInt(1, playerRs.getInt("player_id"));
                                ResultSet statsRs = statsStmt.executeQuery();

                                if (statsRs.next()) {
                                    System.out.println("\nAdditional Statistics:");
                                    System.out.println("Goals: " + statsRs.getInt("goals"));
                                    System.out.println("Assists: " + statsRs.getInt("assists"));
                                    System.out.println("Red Cards: " + statsRs.getInt("red_cards"));
                                    System.out.println("Yellow Cards: " + statsRs.getInt("yellow_cards"));
                                } else {
                                    System.out.println("\nNo statistics available for this player.");
                                }
                            }
                        }
                    }

                    if (!found) {
                        System.out.println("No player found with name containing: " + playerName);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Include it in your library path");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database error:");
            e.printStackTrace();
        }
    }
}