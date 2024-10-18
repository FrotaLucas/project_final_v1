package SmartUtilities.DataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {

    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    private Connection _connection;

    static {
        try {
            Properties props = new Properties();
            // Adjust the path to where your properties file is located
            FileInputStream input = new FileInputStream("src/main/resources/db.properties");
            props.load(input);
            // Set system properties
            System.setProperty("db.url", props.getProperty("db.url"));
            System.setProperty("db.user", props.getProperty("db.user"));
            System.setProperty("db.password", props.getProperty("db.password"));

            DB_URL = System.getProperty("db.url");
            DB_USER = System.getProperty("db.user");
            DB_PASSWORD = System.getProperty("db.password");

        } catch (IOException e) {
            System.err.println("Could not load database properties: " + e.getMessage());
        }
    }


    public Database() throws SQLException{
        this._connection = connect();
    }

    // Method to establish a connection with the database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    //read data from database
    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            Statement stm = _connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            System.out.println("Error while executing SQL script: " + e.getMessage());
            return null;
        }
    }

    //Update Database
    public void executeUpdate(String sql) throws SQLException {
        try (Statement stmt = _connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("query executed successfully!");
        } catch (SQLException e) {
            System.out.println("Error while executing SQL script: " + e.getMessage());
        }
    }
}
