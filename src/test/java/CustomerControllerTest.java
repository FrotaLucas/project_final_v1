import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

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
                .body("properties.customer.properties.birthDay.size()", greaterThan(0)); // Ensure that birthDate is a valid array (if applicable)
    }

    // You can now add more test methods here if needed
}
