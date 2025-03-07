import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Enums.Gender;
import SmartUtilities.Services.CustomerService.CustomerService;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerControllerTest {

    private static final String BASE_URI = "http://localhost:8080/api/customers";
    CustomerService _customerService;
    DataBase _database;
    Connection _connection;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
        _database = new DataBase(); 
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
        _costumerService.addNewCustomer(newCustumer);
        //apagar
        String customerId = "88268a59-2ef4-45e0-897b-167d8fb13702"; // Example customer ID

        when()
            .get("/{uuid}", customerId) // Perform GET request with customer ID
        .then() // Validate the response
            .statusCode(200) // Status code should be 200
            .body("properties.customer.properties.id", equalTo(1)) // Validate that the ID in the response matches the requested ID
            .body("properties.customer.properties.firstName", notNullValue()) // Validate that firstName is not null
            .body("properties.customer.properties.lastName", notNullValue()) // Validate that lastName is not null
            .body("properties.customer.properties.gender", notNullValue()) // Validate that gender is not null
            .body("properties.customer.properties.birthDay", notNullValue()) // Validate that birthDate is not null
            .body("properties.customer.properties.birthDay.size()", greaterThan(0)); 

            boolean deletedCustomer = _customerService.deleteCustomer(uuid);
    }

    @Test
    public void testAddCustomer() throws JsonProcessingException
    {
        Customer newCustomer = new Customer(null, "John", "Doe", "2000-01-01","M");
        UUID uuid = newCustomer.getUuid();
        String customerJson = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthDate\":\"2000-01-01\",\"gender\":\"M\"}";

        //creating jsonSting
        // HashMap<String, String> objMap= new HashMap<String, String>();
        // objMap.put("firstName", newCustomer.getFirstName());
        // objMap.put("lastName", newCustomer.getLastName());
        // objMap.put("uui_id", newCustomer.getUuid().toString());
        // objMap.put("birthDate", "2000-01-01");
        //objMap.put("birthDate", newCustomer.getBirthDate());
        //objMap.put("gender", newCustomer.getGender().toString());
         
        // ObjectMapper mapper = new ObjectMapper();
        // String jsonString = mapper.writeValueAsString(objMap);
        
        //LocalDate date = LocalDate.parse("2000-01-01");
        // ObjectMapper mapper = new ObjectMapper();
        // String jsonString = mapper.writeValueAsString(newCustomer);
        // System.out.println("Generated JSON: " + jsonString);

            //adding customer
            given()
                .contentType("application/json")
                .body(customerJson)
            .when()
                .post("/") // Perform POST request to add customer
            .then()
                .statusCode(201) // Validate creation status
                .body("properties.customer.properties.firstName", equalTo("John"))
                .body("properties.customer.properties.lastName", equalTo("Doe"))
                .body("properties.customer.properties.gender", equalTo("M"));
                //.body("properties.customer.properties.birthDay", equalTo(date));
            
            //deleting added customer
            // when()
            //     .delete("/{uuid}", uuid.toString()) // Perform DELETE request
            // .then()
            //     .statusCode(200)
            //     .body("properties.customer.properties.id", notNullValue()); 
    }

    @Test
    public void testDeleteCustomer()
    {
        // Customer newCustomer = new Customer(null, "John", "Doe", "2000-01-01","M");
        // UUID uuid = newCustomer.getUuid();

        // given()
        //     .contentType("application/json")
        //     .body(newCustomer)
        // .when()
        //     .post() // Perform POST request to add customer
        // .then()
        //     .statusCode(201); // Validate creation status

          //deleting added customer
        String uuid = "062bdd1e-1a13-4ab0-834a-87be19441a25"; 
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
        Customer newCustomer = new Customer(null, "John", "Doe", "2000-01-01","M");
        UUID uuid = newCustomer.getUuid();

        given()
            .contentType("application/json")
            .body(newCustomer)
        .when()
            .post() // Perform POST request to add customer
        .then()
            .statusCode(201); // Validate creation status
        
        //changing properties of customer
        newCustomer.setFirstName("Mary");
        newCustomer.setLastName("Jane");
        newCustomer.setBirthDate(LocalDate.parse("1900-01-12"));
        newCustomer.setGender(Gender.valueOf("W"));

        //update
        given()
            .contentType("application/json")
            .body(newCustomer)
        .when()
            .put() // Perform POST request to add customer
        .then()
            .statusCode(200);

        //retrieving data from database
        when()
            .get("/{uuid}", uuid.toString()) // Perform GET request with customer ID
        .then() // Validate the response
            .statusCode(200) // Status code should be 200
            .body("properties.customer.properties.firstName",equalTo("Mary"))
            .body("properties.customer.properties.lastName", equalTo("Jane")) // Validate that lastName is not null
            .body("properties.customer.properties.gender", equalTo("W")) // Validate that gender is not null
            .body("properties.customer.properties.birthDay", equalTo("1900-01-12")) // Validate that birthDate is not null
            .body("properties.customer.properties.birthDay.size()", greaterThan(0)); 
        
         //deleting addded customer
        when()
            .delete("/{uuid}", uuid.toString()) // Perform DELETE request
        .then()
            .statusCode(200)
            .body("properties.customer.properties.id", notNullValue()); 

    }
}
