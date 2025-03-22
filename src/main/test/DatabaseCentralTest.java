import ie.atu.Football_Management.DatabaseCentral;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;

public class DatabaseCentralTest {

        @Test
            void testMultipleConnections()
        {
            try(Connection conn1 = DatabaseCentral.getConnection();
                Connection conn2 = DatabaseCentral.getConnection())
            {
                assertNotNull(conn1);
                assertNotNull(conn2, "Connection shouldn't be null");
                assertFalse(conn1.isClosed(), "Connection should be open");
                assertFalse(conn2.isClosed(), "Connection should be open");
            }
            catch(Exception e){
                fail("failed to get connection" + e.getMessage());
            }
        }


}

