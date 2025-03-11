import static io.restassured.RestAssured.*;

import SmartUtilities.DataBase.Database;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;


public class DataBaseControllerTest {
    private static final String BASE_URI = "http://localhost:8080/api/setUpDB";
    private static Database _database;
    private static Connection _connection;


    @BeforeAll
    public static void setUp() throws SQLException {
        RestAssured.baseURI = BASE_URI;
        _database = new Database();
        _connection = _database.connect();
        
    }


    @Test
    public void deteleAllReadingsTest(){
       
       when()
            .delete() // Perform DELETE request
        .then()
                .statusCode(200);              
    }
}