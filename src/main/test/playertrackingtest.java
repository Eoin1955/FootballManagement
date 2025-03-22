import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ie.atu.Football_Management.DatabaseCentral;
import java.sql.Connection;
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

    @Test
    void RetrievalTest(){
        try(Connection connection = DatabaseCentral.getConnection())
        {

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
