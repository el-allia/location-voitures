package agenceLocation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static Connection connection = null;
    
    
    private static final String DB_TYPE = "sqlite"; 
    
    
    
    
    private static final String SQLITE_URL = "jdbc:sqlite:database/agencelocation.db";
    
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                 if (DB_TYPE.equals("sqlite")) {
                   
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

