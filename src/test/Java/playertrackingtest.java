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
    void RetrievalTest() {
        try {
            // 1. Use a known existing player name for testing
            String testPlayerName = "Ben White"; // Replace with a name that exists in your DB

            String query = "SELECT player_name, market_value FROM players WHERE player_name = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // 2. Set the parameter as String, not Int
                preparedStatement.setString(1, testPlayerName);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // 3. First verify a player was found
                    assertTrue(resultSet.next(), "Should find player with name: " + testPlayerName);

                    // 4. Get values
                    String playerName = resultSet.getString("player_name");

                    // 5. Handle market_value appropriately (might be string or int)
                    try {
                        // Try reading as int first
                        int marketValue = resultSet.getInt("market_value");
                        assertTrue(marketValue > 0, "Market value should be greater than zero");
                    } catch (SQLException e) {
                        // If not int, try as string
                        String marketValueStr = resultSet.getString("market_value");
                        assertNotNull(marketValueStr, "Market value should not be null");
                        assertFalse(marketValueStr.isEmpty(), "Market value should not be empty");
                    }

                    assertNotNull(playerName, "Player name should not be null");
                }
            }
        } catch (SQLException e) {
            fail("Database error occurred: " + e.getMessage());
        }
    }
}
