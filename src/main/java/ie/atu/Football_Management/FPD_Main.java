package ie.atu.Football_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FPD_Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Create scanner object for user input

        // Prompt user for login credentials
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Attempt to establish database connection and validate user credentials
        try (Connection connection = DatabaseCentral.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();  // Execute query
            if (rs.next()) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or password.");
                return; // Exit if login fails
            }

        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace(); // Print detailed error info
        }

        // Get team and player name from user
        System.out.println("Enter in a Team name: ");
        String teamName = scanner.nextLine();

        System.out.println("Enter in a player name:  ");
        String playerName = scanner.nextLine();

        // Prepare SQL to find player based on team and name
        String selectSQL = "SELECT * FROM players WHERE team_name = ? AND player_name = ?";
        try (Connection connection = DatabaseCentral.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectSQL)) {

            selectStmt.setString(1, teamName);
            selectStmt.setString(2, playerName);
            ResultSet rs = selectStmt.executeQuery();

            // Display player details if found
            if (rs.next()) {
                int playerId = rs.getInt("id");
                String fetchedTeam = rs.getString("team_name");
                String fetchedPlayer = rs.getString("player_name");

                System.out.println("Player Found:");
                System.out.println("ID: " + playerId);
                System.out.println("Team: " + fetchedTeam);
                System.out.println("Name: " + fetchedPlayer);
            } else {
                System.out.println("Player not found.");
            }
        } catch (SQLException e) {
            System.out.println("Database error during retrieval.");
            e.printStackTrace();
        }

        // Display options for user to choose from
        System.out.println("Choose the following Options: ");
        System.out.println("1. Performance");
        System.out.println("2. Disciplinary");
        System.out.println("3. PlayerID");
        System.out.println("4. Player Rating");
        int option = scanner.nextInt();  // Get user selection
        scanner.nextLine(); // Consume the leftover newline

        try (Connection connection = DatabaseCentral.getConnection()) {
            switch (option) {
                case 1:
                    // Add performance note
                    System.out.println("Enter performance note:");
                    String performance = scanner.nextLine();

                    String insertPerformance = "INSERT INTO player_stats (team_name, player_name, performance) VALUES (?, ?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(insertPerformance)) {
                        ps.setString(1, teamName);
                        ps.setString(2, playerName);
                        ps.setString(3, performance);
                        ps.executeUpdate();
                        System.out.println("Performance data saved.");
                    }
                    break;

                case 2:
                    // Update disciplinary note
                    System.out.println("Enter disciplinary note:");
                    String disciplinary = scanner.nextLine();

                    String insertDisciplinary = "UPDATE player_stats SET disciplinary = ? WHERE team_name = ? AND player_name = ?";
                    try (PreparedStatement ps = connection.prepareStatement(insertDisciplinary)) {
                        ps.setString(1, disciplinary);
                        ps.setString(2, teamName);
                        ps.setString(3, playerName);
                        int updated = ps.executeUpdate();
                        if (updated > 0) {
                            System.out.println("Disciplinary record updated.");
                        } else {
                            System.out.println("Player not found. Please add performance first.");
                        }
                    }
                    break;

                case 3:
                    // Retrieve player ID
                    String getPlayerId = "SELECT id FROM player_stats WHERE team_name = ? AND player_name = ?";
                    try (PreparedStatement ps = connection.prepareStatement(getPlayerId)) {
                        ps.setString(1, teamName);
                        ps.setString(2, playerName);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            System.out.println("Player ID: " + rs.getInt("id"));
                        } else {
                            System.out.println("Player not found.");
                        }
                    }
                    break;

                case 4:
                    // Add rating to performance note
                    System.out.println("Enter player rating (1–10):");
                    int rating = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    String updateRating = "UPDATE player_stats SET performance = CONCAT(performance, '\nRating: ', ?) WHERE team_name = ? AND player_name = ?";
                    try (PreparedStatement ps = connection.prepareStatement(updateRating)) {
                        ps.setInt(1, rating);
                        ps.setString(2, teamName);
                        ps.setString(3, playerName);
                        ps.executeUpdate();
                        System.out.println("Rating added to performance notes.");
                    }
                    break;

                default:
                    System.out.println("Invalid option."); // Invalid menu choice
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }

        scanner.close(); // Close scanner to prevent resource leak
    }
}

