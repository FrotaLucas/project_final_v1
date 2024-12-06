package SmartUtilities.DataBase;

//import SmartUtilities.Exceptions.DatabaseException;
import jakarta.inject.Singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Singleton
public class Database {

    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    private Connection _connection;

    static {
        try {
            Properties props = new Properties();
            FileInputStream input = new FileInputStream("src/main/resources/db.properties");
            props.load(input);

            System.setProperty("db.url", props.getProperty("db.url"));
            System.setProperty("db.user", props.getProperty("db.user"));
            System.setProperty("db.password", props.getProperty("db.password"));

            DB_URL = System.getProperty("db.url");
            DB_USER = System.getProperty("db.user");
            DB_PASSWORD = System.getProperty("db.password");

        } catch (IOException e) {
            throw new RuntimeException("Could not load database properties: " + e.getMessage(), e);
        }
    }

    //constructor
    public Database() {
        try {
            this._connection = connect();
        } catch (SQLException e) {
            throw new DatabaseException("Database connection error", e);
        }
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public ResultSet queryWithReturn(String sql) {
        try {
            Statement stm = _connection.createStatement();
            return stm.executeQuery(sql);
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + sql, e);
        }
    }

    public void queryWithoutReturn(String sql) {
        try (Statement stmt = _connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Query executed successfully!");
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing update: " + sql, e);
        }
    }
}
