import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ie.atu.Football_Management.DatabaseCentral;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

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

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
