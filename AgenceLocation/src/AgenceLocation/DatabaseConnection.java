package AgenceLocation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class for AgenceLocation
 * Supports both MySQL and SQLite databases
 */
public class DatabaseConnection {
    private static Connection connection = null;
    
    // Database configuration - modify these according to your setup
    private static final String DB_TYPE = "sqlite"; // "mysql" or "sqlite"
    
    
    
    // SQLite configuration
    private static final String SQLITE_URL = "jdbc:sqlite:agence_location.db";
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                 if (DB_TYPE.equals("sqlite")) {
                    // Load SQLite JDBC driver
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection(SQLITE_URL);
                    System.out.println("Connected to SQLite database");
                } else {
                    throw new SQLException("Unsupported database type: " + DB_TYPE);
                }
            } catch (ClassNotFoundException e) {
                throw new SQLException("JDBC Driver not found. Please add the driver to your classpath.", e);
            }
        }
        return connection;
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    /**
     * Test database connection
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }
}

