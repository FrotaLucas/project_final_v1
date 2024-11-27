package SmartUtilities.Tests;


//
//import SmartUtilities.DataBase.Database;
//import SmartUtilities.Model.Customer.Customer;
//import SmartUtilities.Enums.Gender;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;


////adicionar
//import org.junit.jupiter.api.AfterEach;   // For running code after each test method
//import org.junit.jupiter.api.BeforeEach;  // For running code before each test method
//import org.junit.jupiter.api.Test;       // For marking a method as a test
//import static org.junit.jupiter.api.Assertions.*; // For static assertions like assertEquals(), assertTrue(), etc.


//
//class CustomerServiceTest {
//  private CustomerService _costumerService;
//
//   //usar framework mockito para simular uma interacao com o banco de dados
//  //DataBase dataBaseMock = Mockito.mock(DataBase.class);
//
//  @BeforeEach
//  public void setUp(){
//    _costumerService = new CustomerService();
//    //_costumerService = new CustomerService(dataBaseMock);
//  }
//
//  @Test
//  public void testAddNewCustomer()
//  {
//    Customer newCustumer = new Customer("John", "Doe", "2000-01-01","M");
//    //mudar getId
//    //criar getId depois de ter alterado o getId atual para getUUid;
//
//    _costumerService.addNewCustomer(newCustumer);
//
//    Optional<Customer> dbCustomer = _costumerService.getCustomerByUuid(Uuid.ToString());
//    assertTrue(dbCustomer.isPresent());
//    assertEquals("John",dbCustomer.getFirstName());
//    assertEquals("Doe", dbCustomer.getLastName());
//    assertEquals("2000-01-01", dbCustomer.getBirthDate());
//    //dbCustomer vem do banco de dados, salva gender como string;
//    assertEquals("M", dbCustomer.getGender());
//    //assertEquals(newCustomer.getGender(),Gender.valueOf(dbCustomer.getGender())); //getGender mesmso sendo do DB, getGender nao deveria retornar tipo Gender por causa do metodo ?
//  }
//
//  @Test
//  public void testUpdateCustomer()
//  {
//    Customer newCustomer = new Customer("Jhon", "Doe", "2000-01-01","M");
//    UUID uuid = newCustomer.getId();
//    _costumerService.addNewCustomer(newCustomer);
//    Optional<Customer> dbCustomer = _costumerService.getCustomerByUuid(uuid);
//
//    dbCustomer.setFirstName("Mary");
//    dbCustomer.setLastName("Jane");
//    dbCustomer.setBirthDate("1900-01-12");
//    //ja converte direto para tipo Gender!!
//    dbCustomer.setGender(Gender.valueOf("W"));
//
//    _costumerService.updateCustomer(dbCustomer);
//
//    Optional<Customer> retrievedCustomer = _costumerService.getCustomerByUuid(uuid);
//
//    assertTrue(retrievedCustomer.isPresent());
//    assertEquals("Mary", retrievedCustomer.getFirstName());
//    assertEquals("Jane", retrievedCustomer.getLastName());
//    assertEquals(gender.valueOf("W"), retrievedCustomer.getGender());
//  }
//
//  @Test
//  public void testDeleteCustomer()
//  {
//    Customer newCustomer = new Customer("Jhon", "Doe", "2000-01-01","M");
//    UUID uuid = newCustomer.getId();
//    _costumerService.addNewCustomer(newCustomer);
//    Optional<Customer> dbCustomer = _costumerService.getCustomerByUuid(uuid)
//    //Customer dbCustomer = _costumerService.getCustomerByUuid(uuid);
//
//    //int id = dbCustomer.getId();
//    //getId() != getUuid(); 1 trocar nome getId  2 adicionar getUuid ao codigo
//    _costumerService.deleteCustomer(100);
//
//    Customer retrievedCustomer = _costumerService.getCustomerByUuid(uuid);  //1
//    //Optional<Customer> retrievedCustomer = _costumerService.getCustomerByUuid(uuid);  //2
//    assertFalse(retrievedCustomer.isPresent());
//  }
//
//  @Test
//  public void testGetCustomer()
//  {
//      //adcionar metodo setId() na class Customer
//      Customer newCustomer = new Customer("John", "Doe", "2000-10-01","M");
//      //criar setId depois de ter alterado o setId atual para setUUid;
//      //newCustomer.setId(1);
//      Optional<Customer> dbCustomer = _costumerService.getCustomer(1);
//      assertEquals(1, newCustomer.getId());
//      assertTrue(dbCustomer.isPresent());
//  }
//
//  @Test
//  public void testGetCustomers()
//  {      Customer newCustomer1 = new Customer("John", "Doe", "2000-10-01","M");
//         Customer newCustomer2 = new Customer("John", "Doe", "2000-10-01","M");
//
//          _costumerService.addNewCustomer(newCustomer1);
//          _costumerService.addNewCustomer(newCustomer2);
//
//          List<Customer> customers = _costumerService.getCustomers();
//          assertEquals(2, customers.size());
//          assertTrue(customers.contains(newCustomer1));
//          assertTrue(customers.contains(newCustomer2));
//  }
//
//  @Test
//  public void testGetCustomerByUuid()
//  {
//      Customer newCustomer = new Customer("John", "Doe", "2000-10-01","M");
//      UUID uuid = newCustomer.getId();
//      _costumerService.addNewCustomer(newCustomer);
//      //Optional<Customer> retrievedCustomer = _costumerService.getCustomerByUuid(uuid);
//      Customer retrievedCustomer = _costumerService.getCustomerByUuid(uuid);
//      assertTrue(retrievedCustomer.isPresent());
//  }
//
//
//
//
//
//
//}