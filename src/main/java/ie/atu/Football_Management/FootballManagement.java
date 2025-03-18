package ie.atu.Football_Management;

import java.sql.*;

public class FootballManagement {
    public static void main(String[] args) {
        try(Connection connection = DatabaseCentral.getConnection()) {
            String query = "SELECT * FROM players";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DatabaseCentral.close();
        }
    }
}

