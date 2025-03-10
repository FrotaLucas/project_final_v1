
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

public class ReadingControllerTest {
    private static final String BASE_URI = "http://localhost:8080/api/readings";
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

        //add Customer
        Customer newCustumer = new Customer(null, "John", "Doe", "2000-01-01","M");
        String uuidCustomer = newCustumer.getUuid().toString();
        _customerService.addNewCustomer(newCustumer);
        Customer dbCustomer = _customerService.getCustomerByUuid(uuidCustomer);
        int idCustomer = dbCustomer.getId().orElse(0);

        //add Reading
        Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", idCustomer, dbCustomer);
        String uuid = newReading.getUuid().toString();
        _readingService.addNewReading(newReading);

        given()
                .queryParam("customer", idCustomer) // Define o parâmetro "customer"
                .queryParam("start", "1900-01-01") // Define o parâmetro "start"
                .queryParam("end", "2025-12-31") // Define o parâmetro "end"
                .queryParam("kindOfMeter", "HEIZUNG")
        .when()
                .get("/{uuid}", uuid) // Perform GET request
        .then()
                .statusCode(200)
                .body("type", equalTo("object")) // Validate type is "object"
                .body("title", equalTo("JSON - Schema Reading")) // Validate schema
                .body("required", equalTo("reading")) // Validate schema
                .body("properties.reading.size()", greaterThan(0)) // Ensure customers list is not empty
                .body("properties.reading.properties.comment", equalTo(newReading.getComment())) //hasItem checks List
                .body("properties.reading.properties.kindOfMeter", equalTo(newReading.getKindOfMeter().toString()))
                .body("properties.reading.properties.meterId", equalTo(newReading.getMeterId()))
                .body("properties.reading.properties.meterCount", equalTo(newReading.getMeterCount().floatValue())) //json returns float
                .body("properties.reading.properties.dateOfReading", equalTo(newReading.getDateOfReading()))
                .body("properties.reading.properties.substitute", equalTo(newReading.getSubstitute()));

         _readingService.deleteReadingByUuid(uuid);
        _customerService.deleteCustomer(uuidCustomer);
    }

    @Test
    public void testAddReading()
    {
        //add Customer
        Customer newCustumer = new Customer(null, "John", "Doe", "2000-01-01","M");
        String uuidCustomer = newCustumer.getUuid().toString();
        _customerService.addNewCustomer(newCustumer);
        Customer dbCustomer = _customerService.getCustomerByUuid(uuidCustomer);
        int idCustomer = dbCustomer.getId().orElse(0);

        Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", idCustomer, dbCustomer);

        String readingJson = "{"
                + "\"comment\": \"new checking gas\","
                + "\"kindOfMeter\": \"" + newReading.getKindOfMeter() + "\","
                + "\"meterId\": \"" + newReading.getMeterId() + "\","
                + "\"meterCount\": " + newReading.getMeterCount().floatValue() + ","
                + "\"dateOfReading\": \"" + newReading.getDateOfReading() + "\","
                + "\"substitute\": " + newReading.getSubstitute() + ","
                + "\"customer\": {"
                + "\"id\": " + idCustomer + ","
                + "\"firstName\": \"John\","
                + "\"lastName\": \"Doe\","
                + "\"birthDate\": \"2000-01-01\","
                + "\"gender\": \"M\""
                + "}"
                + "}";

        Response response =
        given()
                .contentType("application/json")
                .body(readingJson)
            .when()
                .post("/") // Perform POST request to add customer
            .then()
                .statusCode(201) // Validate creation status
                .body("properties.reading.properties.comment", equalTo(newReading.getComment())) //hasItem checks List
                .body("properties.reading.properties.kindOfMeter", equalTo(newReading.getKindOfMeter().toString()))
                .body("properties.reading.properties.meterId", equalTo(newReading.getMeterId()))
                .body("properties.reading.properties.meterCount", equalTo(newReading.getMeterCount().floatValue())) //json returns float
                .body("properties.reading.properties.dateOfReading", equalTo(newReading.getDateOfReading()))
                .body("properties.reading.properties.substitute", equalTo(newReading.getSubstitute()))
                .extract().response();

        String uuid = response.path("properties.reading.properties.id");
        _readingService.deleteReadingByUuid(uuid);
        _customerService.deleteCustomer(uuidCustomer);
    }

    @Test void testDeleteReading()
    {
        //add customer
        Customer newCustumer = new Customer(null, "John", "Doe", "2000-01-01","M");
        String uuidCustomer = newCustumer.getUuid().toString();
        _customerService.addNewCustomer(newCustumer);
        Customer dbCustomer = _customerService.getCustomerByUuid(uuidCustomer);
        int idCustomer = dbCustomer.getId().orElse(0);

        //add Reading
        Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", idCustomer, dbCustomer);
        String uuid = newReading.getUuid().toString();
        _readingService.addNewReading(newReading);

        when()
            .delete("/{uuid}", uuid) // Perform DELETE request
        .then()
                .statusCode(200)
                .body("type", equalTo("object")) // Validate type is "object"
                .body("title", equalTo("Customer-JSON-Schema")) // Validate schema
                .body("required", equalTo("reading")) // Validate schema
                .body("properties.reading.size()", greaterThan(0)) // Ensure customers list is not empty
                .body("properties.reading.properties.comment", equalTo(newReading.getComment())) //hasItem checks List
                .body("properties.reading.properties.kindOfMeter", equalTo(newReading.getKindOfMeter().toString()))
                .body("properties.reading.properties.meterId", equalTo(newReading.getMeterId()))
                .body("properties.reading.properties.meterCount", equalTo(newReading.getMeterCount().floatValue())) //json returns float
                .body("properties.reading.properties.dateOfReading", equalTo(newReading.getDateOfReading()))
                .body("properties.reading.properties.substitute", equalTo(newReading.getSubstitute()));
        
    }

    @Test void testUpdateReading()
    {
        //add customer
        Customer newCustumer = new Customer(null, "John", "Doe", "2000-01-01","M");
        String uuidCustomer = newCustumer.getUuid().toString();
        _customerService.addNewCustomer(newCustumer);
        Customer dbCustomer = _customerService.getCustomerByUuid(uuidCustomer);
        int idCustomer = dbCustomer.getId().orElse(0);

        //add Reading
        Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", idCustomer, dbCustomer);
        String uuid = newReading.getUuid().toString();
        _readingService.addNewReading(newReading);
        
        newReading.setKindOfMeter(KindOfMeter.valueOf("STROM"));
        newReading.setComment("new checking eletricity");
        newReading.setMeterId("Y2200");
        newReading.setMeterCount(222222.0);
        newReading.setSubstitute(false);
        newReading.setDateOfReading("1990-01-01");

        String readingJson = "{"
            + "\"comment\": \"new checking gas\","
            + "\"kindOfMeter\": \"" + newReading.getKindOfMeter() + "\","
            + "\"meterId\": \"" + newReading.getMeterId() + "\","
            + "\"meterCount\": " + newReading.getMeterCount().floatValue() + ","
            + "\"dateOfReading\": \"" + newReading.getDateOfReading() + "\","
            + "\"substitute\": " + newReading.getSubstitute() + ","
            + "\"customer\": {"
            + "\"id\": " + idCustomer + ","
            + "\"firstName\": \"John\","
            + "\"lastName\": \"Doe\","
            + "\"birthDate\": \"2000-01-01\","
            + "\"gender\": \"M\""
            + "}"
            + "}";

        //update
        given()
            .contentType("application/json")
            .body(readingJson)
        .when()
            .put() // Perform POST request to add customer
        .then()
            .statusCode(200);

        //retrievin reading
        .when()
                .get("/{uuid}", uuid) // Perform GET request
        .then()
                .statusCode(200)
                .body("type", equalTo("object")) // Validate type is "object"
                .body("title", equalTo("JSON - Schema Reading")) // Validate schema
                .body("required", equalTo("reading")) // Validate schema
                .body("properties.reading.size()", greaterThan(0)) // Ensure customers list is not empty
                .body("properties.reading.properties.comment", equalTo(newReading.getComment())) //hasItem checks List
                .body("properties.reading.properties.kindOfMeter", equalTo(newReading.getKindOfMeter().toString()))
                .body("properties.reading.properties.meterId", equalTo(newReading.getMeterId()))
                .body("properties.reading.properties.meterCount", equalTo(newReading.getMeterCount().floatValue())) //json returns float
                .body("properties.reading.properties.dateOfReading", equalTo(newReading.getDateOfReading()))
                .body("properties.reading.properties.substitute", equalTo(newReading.getSubstitute()));

        //deleting added reading
        _readingService.deleteReadingByUuid(uuid);
        _customerService.deleteCustomer(uuidCustomer);
    }

}
