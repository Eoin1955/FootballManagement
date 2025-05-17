package ie.atu.Football_Management;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseCentral {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCentral.class);
    private static final HikariDataSource dataSource;
    private static final String URL = "jdbc:mysql://localhost:3306/FPD";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Password";

    static {
        try{HikariConfig config = new HikariConfig();

            // Connection Pool setting
            config.setJdbcUrl(URL);
            config.setUsername(USERNAME);
            config.setPassword(PASSWORD);
            config.setMaximumPoolSize(10);  // Max connections
            config.setMinimumIdle(2);       // Min idle connections
            config.setIdleTimeout(30000);   // 30s idle timeout
            config.setMaxLifetime(1800000); // 30 mins max connection lifetime

            dataSource = new HikariDataSource(config);
        }

        catch (Exception e) {
            LOGGER.error("Error when initialising the database connection", e);
            throw new ExceptionInInitializerError(e);
        }

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
