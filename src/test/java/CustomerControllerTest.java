import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Enums.Gender;
import SmartUtilities.Services.CustomerService.CustomerService;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerControllerTest {

    private static final String BASE_URI = "http://localhost:8080/api/customers";
    private static CustomerService _customerService;
    private static Database _database;
    private static Connection _connection;

    //BeforeEach does not need static because each test a new class CustomerControllerTest is created
    @BeforeAll
    public static void setUp() throws SQLException {
        RestAssured.baseURI = BASE_URI;
        _database = new Database(); 
        _connection = _database.connect();
        _customerService =  new CustomerService(_database);

    }

    @Test
    public void testGetCustomers() {
        when()
            .get() // Perform GET request
        .then() // Validate the response
            .statusCode(200) // Status code 200
            .body("type", equalTo("object")) // Validate type is "object"
            .body("title", equalTo("Customers-JSON-Schema")) // Validate schema
            .body("properties.customers.items", not(empty())) // Validate items are not empty
            .body("properties.customers.items.size()", greaterThan(0)); // Ensure customers list is not empty
    }

    @Test
    public void testGetCustomer()
    {
        Customer newCustumer = new Customer(null, "John", "Doe", "2000-01-01","M");
        String uuid = newCustumer.getUuid().toString();
        _customerService.addNewCustomer(newCustumer);
        Customer dbCustomer = _customerService.getCustomerByUuid(uuid);
        int id = dbCustomer.getId().orElse(0);

        when()
            .get("/{uuid}", uuid) // Perform GET request with customer ID
        .then() // Validate the response
            .statusCode(200) // Status code should be 200
            .body("properties.customer.properties.id", equalTo(id)) // Validate that the ID in the response matches the requested ID
            .body("properties.customer.properties.firstName", equalTo(newCustumer.getFirstName()))
            .body("properties.customer.properties.lastName", equalTo(newCustumer.getLastName())) 
            .body("properties.customer.properties.gender", equalTo(newCustumer.getGender().toString())) 
            .body("properties.customer.properties.birthDay", contains(2000,1,1));

        _customerService.deleteCustomer(uuid);
    }

    @Test
    public void testAddCustomer() throws JsonProcessingException
    {
        String customerJson = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthDate\":\"2000-01-01\",\"gender\":\"M\"}";
        
            //adding customer
             Response response = given()
                .contentType("application/json")
                .body(customerJson)
            .when()
                .post("/") // Perform POST request to add customer
            .then()
                .statusCode(201) // Validate creation status
                .body("properties.customer.properties.firstName", equalTo("John"))
                .body("properties.customer.properties.lastName", equalTo("Doe"))
                .body("properties.customer.properties.gender", equalTo("M"))
                .body("properties.customer.properties.birthDay", contains(2000,1,1))
                .body("properties.customer.properties.uuiId", notNullValue())
                .extract().response();
            
            String uuid = response.path("properties.customer.properties.uuiId");
            _customerService.deleteCustomer(uuid);
    }

    @Test
    public void testDeleteCustomer()
    {
        Customer newCustomer = new Customer(null, "John", "Doe", "2000-01-01","M");
        String uuid = newCustomer.getUuid().toString();
        _customerService.addNewCustomer(newCustomer);
        
        when()
            .delete("/{uuid}", uuid) // Perform DELETE request
        .then()
            .statusCode(200)
            .body("properties.customer.properties.firstName", equalTo("John"))
            .body("properties.customer.properties.lastName", equalTo("Doe"))
            .body("properties.customer.properties.gender", equalTo("M"))
            .body("properties.customer.properties.birthDay", contains(2000,1,1));
    }

    @Test
    public void testUpdateCustomer()
    {
        String customerJson = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthDate\":\"2000-01-01\",\"gender\":\"M\"}";
        
        Response response = given()
            .contentType("application/json")
            .body(customerJson)
        .when()
            .post() // Perform POST request to add customer
        .then()
            .statusCode(201)
            .extract().response();

        String uuid = response.path("properties.customer.properties.uuiId");
        Customer dbCustomer = _customerService.getCustomerByUuid(uuid);

        //changing properties of customer
        dbCustomer.setFirstName("Mary");
        dbCustomer.setLastName("Jane");
        dbCustomer.setBirthDate(LocalDate.parse("1900-01-12"));
        dbCustomer.setGender(Gender.valueOf("W"));

        //melhorar essa string com /n!!!!!
        customerJson = String
        .format("{\"uuid\":\"%s\",\"firstName\":\"Mary\",\"lastName\":\"Jane\",\"birthDate\":\"1900-01-12\",\"gender\":\"W\"}", 
            uuid);
        //update
        given()
            .contentType("application/json")
            .body(customerJson)
        .when()
            .put() // Perform PUT request to add customer
        .then()
            .statusCode(200);

        //retrieving data from database
        when()
            .get("/{uuid}", uuid) // Perform GET request with customer ID
        .then() // Validate the response
            .statusCode(200) // Status code should be 200
            .body("properties.customer.properties.firstName",equalTo(dbCustomer.getFirstName()))
            .body("properties.customer.properties.lastName", equalTo(dbCustomer.getLastName())) 
            .body("properties.customer.properties.gender", equalTo(dbCustomer.getGender().toString()))
            .body("properties.customer.properties.birthDay", contains(1900,1,12));
        
         //deleting addded customer
        when()
            .delete("/{uuid}", uuid) // Perform DELETE request
        .then()
            .statusCode(200)
            .body("properties.customer.properties.id", notNullValue()); 

    }
}
