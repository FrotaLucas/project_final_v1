
import SmartUtilities.DaoLayer.DaoCustomer.CustomerService;
import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Enums.Gender;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;   // For running code after each test method
import org.junit.jupiter.api.BeforeEach;  // For running code before each test method
import org.junit.jupiter.api.Test;       // For marking a method as a test
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import static org.junit.jupiter.api.Assertions.*; // For static assertions like assertEquals(), assertTrue(), etc.

public class CustomerServiceTest {
    //private Database dataBaseMock;

    private CustomerService _costumerService;
    private Database _database;
    private Connection _connection;

  @BeforeEach
  void setUp() throws SQLException //start connection
  {
    _database = new Database(); //forma de usar _database porem sem injetar no construtor DataBaseTest
    _connection = Database.connect(); //connect eh um metodo static e so pode ser chamado pela classe original
    _costumerService = new CustomerService(_database);
  }

  @Test
  public void testAddNewCustomer()
  {
    LocalDate birthDate = LocalDate.of(2000, 1, 1);
    Customer newCustumer = new Customer(null, "John", "Doe", birthDate,"M");
    UUID uuid = newCustumer.getUuid();
    _costumerService.addNewCustomer(newCustumer);

    Customer dbCustomer;
    dbCustomer = _costumerService.getCustomerByUuid(uuid.toString());

    assertNotNull(dbCustomer);
    assertEquals("John",dbCustomer.getFirstName());
    assertEquals("Doe", dbCustomer.getLastName());
    assertEquals(birthDate, dbCustomer.getBirthDate());
    //dbCustomer vem do banco de dados, salva gender como string;
    assertEquals(Gender.valueOf("M"), dbCustomer.getGender());
    boolean response = _costumerService.deleteCustomer(uuid.toString());
    assertTrue(response);
  }

  @Test
  public void testUpdateCustomer()
  {
    LocalDate birthDate = LocalDate.of(2000, 1, 1);
    Customer newCustumer = new Customer(null, "John", "Doe", birthDate,"M");
    UUID uuid = newCustumer.getUuid();
    _costumerService.addNewCustomer(newCustumer);

    Customer dbCustomer;
    dbCustomer = _costumerService.getCustomerByUuid(uuid.toString());

    dbCustomer.setFirstName("Mary");
    dbCustomer.setLastName("Jane");
    LocalDate newBirthDate = LocalDate.of(1999, 1, 1);
    dbCustomer.setBirthDate(newBirthDate);
    dbCustomer.setGender(Gender.valueOf("W"));

    _costumerService.updateCustomer(dbCustomer);

    Customer retrievedCustomer = _costumerService.getCustomerByUuid(uuid.toString());

    assertNotNull(retrievedCustomer);
    assertEquals("Mary", retrievedCustomer.getFirstName());
    assertEquals("Jane", retrievedCustomer.getLastName());
    assertEquals(Gender.valueOf("W"), retrievedCustomer.getGender());

    boolean response = _costumerService.deleteCustomer(uuid.toString());
    assertTrue(response);
  }

  @Test
  public void testDeleteCustomer()
  {

    LocalDate birthDate = LocalDate.of(2000, 1, 1);
    Customer newCustumer = new Customer(null, "John", "Doe",birthDate,"M");
    UUID uuid = newCustumer.getUuid();
    _costumerService.addNewCustomer(newCustumer);

    boolean response = _costumerService.deleteCustomer(uuid.toString());
    assertTrue(response);

    Customer retrievedCustomer = _costumerService.getCustomerByUuid(uuid.toString());  //1
    //Optional<Customer> retrievedCustomer = _costumerService.getCustomerByUuid(uuid);  //2
    assertNull(retrievedCustomer);
  }

    @Test
  public void testGetCustomer()
  {

      LocalDate birthDate = LocalDate.of(2000, 1, 1);
      //adcionar metodo setId() na class Customer
      Customer newCustomer = new Customer(null,"John", "Doe",birthDate,"M");
      UUID uuid = newCustomer.getUuid();

      _costumerService.addNewCustomer(newCustomer);
      Customer dbCustomer = _costumerService.getCustomerByUuid(uuid.toString());

      int id = dbCustomer.getId().orElse(0);
      dbCustomer = _costumerService.getCustomer(id);

      assertNotNull(dbCustomer);
      boolean response = _costumerService.deleteCustomer(uuid.toString());
      assertTrue(response);
  
  }

  @AfterEach //close connection
  void tearDown() throws SQLException
  {
      if(_connection != null && !_connection.isClosed())
        _connection.close();
  }
}
