import static io.restassured.RestAssured.*;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Model.Reading.Reading;
import SmartUtilities.Services.CustomerService.CustomerService;
import SmartUtilities.Services.ReadingService.ReadingService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import SmartUtilities.Enums.KindOfMeter;
import SmartUtilities.Model.Reading.Reading;
import org.mockito.exceptions.verification.NeverWantedButInvoked;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class DataBaseControllerTest {
    private static final String BASE_URI = "http://localhost:8080/api/readings/setUpDB";
    private static CustomerService _customerService;
    private static ReadingService _readingService;
    private static Database _database;
    private static Connection _connection;


    @BeforeAll
    public static void setUp() throws SQLException {
        RestAssured.baseURI = BASE_URI;
        _database = new Database();
        _connection = _database.connect();
        _customerService = new CustomerService(_database);
        _readingService = new ReadingService(_database);
    }


    @Test
    public void deteleAllReadingsTest(){
       String sql1 = """
                    CREATE TABLE IF NOT EXISTS customers_test (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        first_name VARCHAR(50),
                        last_name VARCHAR(50),
                        birthDate DATE,
                        gender VARCHAR(10),
                        uui_id VARCHAR(255)
                    );
                    """;

       String sql2 = """
                    CREATE TABLE IF NOT EXISTS data_reading_test (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        customer_id INT,
                        kind_of_meter VARCHAR(10),
                        comment VARCHAR(255),
                        meter_id VARCHAR(20),
                        meter_count DOUBLE,
                        substitute BOOLEAN,
                        date_of_reading VARCHAR(20),
                        uui_id VARCHAR(255),
                        FOREIGN KEY (customer_id) REFERENCES customers(id)
                    );
                    """;
      _database.queryWithoutReturn(sql2);

       when()
            .delete() // Perform DELETE request
        .then()
                .statusCode(200);              
    }
}