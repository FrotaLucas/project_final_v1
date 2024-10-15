package SmartUtilities.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/gm3?allowMultiQueries=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "frotalucas";

    private Connection _connection;

    public Database() throws SQLException{
        this._connection = connect();
    }

    // Method to establish a connection with the database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    //reafactoring executeSQLScript
    public void refactoredExecuteSQl(String sql) throws SQLException {
        try (Statement stmt = _connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("query executed successfully!");
        } catch (SQLException e) {
            System.out.println("Error while executing SQL script: " + e.getMessage());
        }
    }
}
