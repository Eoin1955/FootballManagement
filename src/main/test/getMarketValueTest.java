import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class getMarketValueTest {
    PlayerRepository repository;

    @BeforeEach
    void setup() { repository = new PlayerRepository(); }

    @Test
    void testgetMarketValueSuccess() {
        assertEquals("50 000 000", repository.getMarketValue("Lionel Messi"));
    }

    @Test
    void testgetMarketValueFailure() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> repository.getMarketValue("Unknown Player"));
        assertEquals("Player not found", ex.getMessage());
    }
}