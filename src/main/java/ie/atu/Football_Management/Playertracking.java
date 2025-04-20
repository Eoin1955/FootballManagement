package ie.atu.Football_Management;

import java.sql.*;

public class Playertracking {
    public static void main(String[] args) {

        String playerName = null;
        String playerAge = null;

        try(Connection connection = DatabaseCentral.getConnection()) {
            String query = "SELECT p.name, p.age" + "FROM player u";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                playerName = resultSet.getString("name");
                playerAge = resultSet.getString("age");
            }

            System.out.println(playerName + "Aged: " + playerAge);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DatabaseCentral.close();
        }
    }
}

