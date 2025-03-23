import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ie.atu.Football_Management.DatabaseCentral;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


import static org.junit.jupiter.api.Assertions.*;

public class playertrackingtest {

    Scanner scanner;

    @BeforeEach
    void setUp() {
        scanner =new Scanner("testing");
    }

    @Test
    void ScannertestIsOpen()
    {
        assertDoesNotThrow(scanner::nextLine, "Scanner should be open and able ot read");
    }

    @Test
    void ScannertestIsClosed(){
        scanner.close();
        assertThrows(IllegalStateException.class, scanner::nextLine, "Scanner should be closed");
    }

    Connection connection;

    @BeforeEach
    void setUp2() throws SQLException{
        connection = DatabaseCentral.getConnection();
    }

    @AfterEach
    void tearDown() throws SQLException{
        if(connection!=null){
            connection.close();
        }
    }

    @Test
    void RetrievalTest(){
        try
        {
            String query = "SELECT * FROM players WHERE player_name LIKE ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query))
            {
                preparedStatement.setInt(1,1);
                try(ResultSet resultSet = preparedStatement.executeQuery()){

                        assertTrue(resultSet.next(), "should find player");

                        String playerName = resultSet.getString("name");
                        int marketValue = resultSet.getInt("market_value");

                        assertNotNull(playerName, "Player name should not be null");
                        assertTrue(marketValue > 0, "Market value should be greater than zero");
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
