
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ReadingControllerTest {
    private static final String BASE_URI = "http://localhost:8080/api/readings";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void testGetReadings() {

        when()
                .get() // Perform GET request
        .then() // Validate the response
                .statusCode(200) // Status code 200
                .body("type", equalTo("object")) // Validate type is "object"
                .body("title", equalTo("JSON - Schema Readings")) // Validate schema
                .body("required", equalTo("readings")) // Validate schema
                .body("properties.readings.items", not(empty())) // Validate items are not empty
                .body("properties.readings.items.size()", greaterThan(0)); // Ensure customers list is not empty
    }

    @Test
    public void testGetReadingsOfCustomer() {
        // endpoint:
        // http://localhost:8080/api/readings?customer=1&start=2000-02-01&end=2000-06-01
        given()
                .queryParam("customer", 1) // Define o parâmetro "customer"
                .queryParam("start", "2000-02-01") // Define o parâmetro "start"
                .queryParam("end", "2000-06-01") // Define o parâmetro "end"
        .when()
                .get() // Perform GET request
        .then()
                .statusCode(200)
                .body("type", equalTo("object")) // Validate type is "object"
                .body("title", equalTo("JSON - Schema Readings")) // Validate schema
                .body("required", equalTo("readings")) // Validate schema
                .body("properties.readings.items", not(empty())) // Validate items are not empty
                .body("properties.readings.items.size()", greaterThan(0)); // Ensure customers list is not empty
    }

}
