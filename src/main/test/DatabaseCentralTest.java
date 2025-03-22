import ie.atu.Football_Management.DatabaseCentral;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;

public class DatabaseCentralTest {


        @Test
           void testConnectionIsValid()
        {
            try(Connection connection = DatabaseCentral.getConnection()){
                assertNotNull(connection, "Connection shouldn't be null");
                assertFalse(connection.isClosed(), "Connection should be open");
            }
            catch(Exception e){
                fail("failed to get connection" + e.getMessage());
            }
        }
    }

