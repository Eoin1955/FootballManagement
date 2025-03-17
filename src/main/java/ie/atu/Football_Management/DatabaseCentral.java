import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseCentral {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/football_market"); // Change database name
        config.setUsername("root"); // Change username
        config.setPassword("your_password"); // Change password
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Connection Pool settings
        config.setMaximumPoolSize(10);  // Max connections
        config.setMinimumIdle(2);       // Min idle connections
        config.setIdleTimeout(30000);   // 30s idle timeout
        config.setMaxLifetime(1800000); // 30 mins max connection lifetime

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
