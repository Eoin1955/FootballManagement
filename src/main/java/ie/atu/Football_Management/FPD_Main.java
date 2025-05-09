package ie.atu.Football_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class FPD_Main {
    public static void main(String[] args) {
        // Hardcoded credentials


        Scanner scanner = new Scanner(System.in);

        // Prompt user for credentials
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (Connection connection = DatabaseCentral.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Check credentials
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or password.");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }

        System.out.println("Enter in a Team name: ");
        String teamName = scanner.nextLine();

        System.out.println("Enter in a player name:  ");
        String playerName = scanner.nextLine();

        String selectSQL = "SELECT * FROM players WHERE team_name = ? AND player_name = ?";
        try (Connection connection = DatabaseCentral.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectSQL)) {

            selectStmt.setString(1, teamName);
            selectStmt.setString(2, playerName);
            ResultSet rs = selectStmt.executeQuery();

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


        System.out.println("Choose the following Options: ");
        System.out.println("1. Performance");
        System.out.println("2. Disaplinary");
        System.out.println("3. PlayerID");
        System.out.println("4. Player Rating");
        int option = scanner.nextInt();


        try (Connection connection = DatabaseCentral.getConnection()) {
            switch (option) {
                case 1:
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
                    System.out.println("Invalid option.");
            }
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }


        scanner.close();
    }
}

