import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.*;

public class CustomerControllerTest {

    private static final String BASE_URI = "http://localhost:4080/api/customers";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    // You can now write test methods here
}
