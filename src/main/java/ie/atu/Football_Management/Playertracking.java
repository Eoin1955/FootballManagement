package ie.atu.Football_Management;

import java.sql.*;

public class Playertracking {
    public static void main(String[] args) {
        try(Connection connection = DatabaseCentral.getConnection()) {
            String query = "SELECT ";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DatabaseCentral.close();
        }
    }
}

