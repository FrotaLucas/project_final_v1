package SmartUtilities.Tests

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


class DataBaseTest {
  private DataBase _database;
  private Connection _connection;

  @BeforeEach
  void setUp() throws SQLException //start connection
  {
    _database = new DataBase(); //forma de usar _database porem sem injetar no construtor DataBaseTest
    _connection = DataBase.connect(); //connect eh um metodo static e so pode ser chamado pela classe original
  }

  @AfterEach //close connection
  void tearDown() throws SQLException
  {
      if(_connection != null && !_connection.isClosed())
        _connection.close();
  }

  @Test
  void testConnectionIsValid()
  { 
      try{
        assertNotNull(_connection, "Connection can not be null.");
        //check connectionfor 2 sec
        assertTrue(_connection.isValid(2));
      }
      catch (SQLException e){
        fail("Error while retrieving connection: " + e.getMessage());
      }
  }
 
}
