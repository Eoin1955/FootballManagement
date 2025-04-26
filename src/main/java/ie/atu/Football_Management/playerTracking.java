package ie.atu.Football_Management;

import java.sql.*;
import java.util.Scanner;

public class Playertracking {
    public void playertracking(){
        try(Connection connection = DatabaseCentral.getConnection()){
            //I want to find a player
            //I want to look at the stats for that player and their team
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter player name: ");
            String PlayerName = scanner.nextLine();

            String selectsql = "SELECT * FROM playerstats WHERE playerID=?";
            PreparedStatement statement = connection.prepareStatement(selectsql);
            statement.setString(1, PlayerName);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                System.out.println("Player name is " + resultSet.getString("playerName"));
                System.out.println("Team: " + resultSet.getString("team"));
                System.out.println("Goals: " + resultSet.getInt("goals"));
                System.out.println("Assists: " + resultSet.getInt("assists"));
                System.out.println("Appearances: " + resultSet.getInt("appearances"));
            }
            else{
                System.out.println("Player not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}