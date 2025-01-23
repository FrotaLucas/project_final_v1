import SmartUtilities.Model.Customer.Customer;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;
public class CustomerControllerTest {

    private static final String BASE_URI = "http://localhost:8080/api/customers";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
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
        String customerId = "499e4cb1-4f0f-4376-b0b7-b5d6a1879134"; // Example customer ID

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
    }

    @Test
    public void testAddCustomer()
    {
        Customer newCustumer = new Customer(null, "John", "Doe", "2000-01-01","M");
        UUID uuid = newCustumer.getUuid();

            given()
                    .contentType("application/json")
                    .body(newCustomer)
                    .when()
                    .post() // Perform POST request to add customer
                    .then()
                    .statusCode(201) // Validate creation status
                    .body("id", notNullValue()) // Ensure ID is returned
                    .body("properties.customer.properties.firstName", equalTo("John"))
                    .body("properties.customer.properties.lastName", equalTo("Doe"))
                    .body("properties.customer.properties.gender", equalTo("M"))
                    .body("birthDay", equalTo("2000-01-01"));

            //deleting added customer
            when()
                    .delete("/{uuid}",uuid.toString())
                    .then()
                    .statusCode(200)
                    .body("properties.customer.properties.id", equalTo(1));
    }

    @Test
    public void testDeleteCustomer()
    {
        
    }
}
