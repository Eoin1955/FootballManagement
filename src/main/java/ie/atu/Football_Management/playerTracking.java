package ie.atu.Football_Management;

import java.sql.Connection;
import java.sql.SQLException;

public class playerTracking {
    public void PlayerTracking{
        try(Connection connection = DatabaseCentral.getConnection()){

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


