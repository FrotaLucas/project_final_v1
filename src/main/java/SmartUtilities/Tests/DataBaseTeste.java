//
//import SmartUtilities.DataBase.Database;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Properties;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class DataBaseTest {
//    private Database _database;
//    private Connection _connection;
//
//    @BeforeEach
//    void setUp() throws SQLException //start connection
//    {
//        _database = new Database(); //forma de usar _database porem sem injetar no construtor DataBaseTest
//        _connection = Database.connect();
//    }
//
//    @AfterEach //close connection
//    void tearDown() throws SQLException
//    {
//        if(_connection != null && !_connection.isClosed())
//            _connection.close();
//    }
//
//    @Test
//    void testConnectionIsValid()
//    {
//        try{
//            assertNotNull(_connection, "Connection can not be null.");
//            //check connectionfor 2 sec
//            assertTrue(_connection.isValid(2));
//        }
//        catch (SQLException e){
//            fail("Error while retrieving connection: " + e.getMessage());
//        }
//    }
//
//    @Test
//    void testExecuteQuery(){
//        String sql = "SELECT 1"; //basic query to test
//
//        try(ResultSet rs = _database.executeQuery(sql)){
//            assertNotNull(rs,"Result should not be null.");
//            assertTrue(rs.next(), "Result with at least 1 line.");
//            assertEquals(1, rs.getInt(1), "Returned value is 1");
//        }
//        catch (SQLException e) {
//            fail("Error while executing query: " + e.getMessage());
//        }
//    }
//
//}
