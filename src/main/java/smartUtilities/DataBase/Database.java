package smartUtilities.DataBase;

// Database.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/gm3?allowMultiQueries=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "frotalucas";

    // Method to establish a connection with the database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Method to read the SQL file and return its content as a string
    public static String readSQLFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    // Method to execute the SQL script (e.g., create table)
    public static void executeSQLScript(Connection connection, String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created successfully!");
        } catch (SQLException e) {
            System.out.println("Error while executing SQL script: " + e.getMessage());
        }
    }
}
