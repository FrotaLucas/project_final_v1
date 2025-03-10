
import static io.restassured.RestAssured.*;

import SmartUtilities.Services.CustomerService.CustomerService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import SmartUtilities.Enums.KindOfMeter;
import SmartUtilities.Model.Reading.Reading;

public class ReadingControllerTest {
    private static final String BASE_URI = "http://localhost:8080/api/readings";
    private static CustomerService _customerService;
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
                .queryParam("kindOfMeter", "HEIZUNG")
        .when()
                .get() // Perform GET request
        .then()
                .statusCode(200)
                .body("type", equalTo("object")) // Validate type is "object"
                .body("title", equalTo("JSON - Schema Customer with Reading")) // Validate schema
                .body("required", equalTo("readings")) // Validate schema
                .body("properties.readings.items", not(empty())) // Validate items are not empty
                .body("properties.readings.items.size()", greaterThan(0)) // Ensure customers list is not empty
                .body("properties.readings.items.properties.comment", equalTo("new checking gas"))
                .body("properties.readings.items.properties.kindOfMeter", equalTo("Doe"))
                .body("properties.readings.items.properties.meterId", equalTo("X1100"))
                .body("properties.readings.items.properties.meterCount", equalTo(11111.0))
                .body("properties.readings.items.properties.dateOfReading", equalTo("2000-01-01"))
                .body("properties.readings.items.properties.substitute", equalTo(true));
    }

    @Test
    public void testAddReading()
    {
        Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", 1);
        String uuid = newReading.getUuid().toString();

           given()
                .contentType("application/json")
                .body(newReading)
            .when()
                .post("/") // Perform POST request to add customer
            .then()
                .statusCode(201) // Validate creation status
                .body("properties.reading.properties.comment", equalTo("new checking gas"))
                .body("properties.reading.properties.kindOfMeter", equalTo("Doe"))
                .body("properties.reading.properties.meterId", equalTo("X1100"))
                .body("properties.reading.properties.meterCount", equalTo(11111.0))
                .body("properties.reading.properties.dateOfReading", equalTo("2000-01-01"))
                .body("properties.reading.properties.substitute", equalTo(true));
                //.body("properties.customer.properties.birthDay", equalTo(date));
            
            //deleting added reading
            when()
                .delete("/{uuid}", uuid.toString()) // Perform DELETE request
            .then()
                .statusCode(200)
                .body("properties.reading.properties.size()", notNullValue()); 

    }

    @Test void deleteReading()
    {
        String uuid = "c45930e2-7a55-428f-ab9e-e9ffa56a3312"; 
        when()
            .delete("/{uuid}", uuid) // Perform DELETE request
        .then()
                .statusCode(200)
                .body("type", equalTo("object")) // Validate type is "object"
                .body("title", equalTo("JSON - Schema Readings")) // Validate schema
                .body("required", equalTo("readings")) // Validate schema
                .body("properties.readings.items", not(empty())) // Validate items are not empty
                .body("properties.readings.items.size()", greaterThan(0)) // Ensure customers list is not empty
                .body("properties.readings.items.properties.comment", equalTo("new checking gas"))
                .body("properties.readings.items.properties.kindOfMeter", equalTo("Doe"))
                .body("properties.readings.items.properties.meterId", equalTo("X1100"))
                .body("properties.readings.items.properties.meterCount", equalTo(11111.0))
                .body("properties.readings.items.properties.dateOfReading", equalTo("2000-01-01"))
                .body("properties.readings.items.properties.substitute", equalTo(true));
        
    }

    @Test void updateReading()
    {
          Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", 1);
        String uuid = newReading.getUuid().toString();

           given()
                .contentType("application/json")
                .body(newReading)
            .when()
                .post("/") // Perform POST request to add customer
            .then()
                .statusCode(201) // Validate creation status
                .body("properties.reading.properties.comment", equalTo("new checking gas"))
                .body("properties.reading.properties.kindOfMeter", equalTo("Doe"))
                .body("properties.reading.properties.meterId", equalTo("X1100"))
                .body("properties.reading.properties.meterCount", equalTo(11111.0))
                .body("properties.reading.properties.dateOfReading", equalTo("2000-01-01"))
                .body("properties.reading.properties.substitute", equalTo(true));
        
                newReading.setKindOfMeter(KindOfMeter.valueOf("STROM"));
                newReading.setComment("new checking eletricity");
                newReading.setMeterId("Y2200");
                newReading.setMeterCount(222222.0);
                newReading.setSubstitute(false);
                newReading.setDateOfReading("1990-01-01");


        //update
        given()
            .contentType("application/json")
            .body(newReading)
        .when()
            .put() // Perform POST request to add customer
        .then()
            .statusCode(200);

        //deleting added reading
        when()
                .delete("/{uuid}", uuid.toString()) // Perform DELETE request
        .then()
                .statusCode(200)
                .body("properties.reading.properties.size()", notNullValue()); 
    }

}
