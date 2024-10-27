package SmartUtilities.Services.CustomerServiceTest;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Enums.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//adicionar
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
  private CustomerService _costumerService;


  @BeforeEach
  public void setUp(){  
    _costumerService = new CustomerService();
  }

  @Test
  public void testAddNewCustomer()
  {  
    Customer newCustumer = new Customer("John", "Doe", "2000-01-01","M");
    UUID Uuid = newCustomer.getId();

    _costumerService.addNewCustomer(newCustumer);

    Optional<Customer> dbCustomer = _costumerService.getCustomerByUuid(Uuid.ToString());
    assertTrue(dbCustomer.isPresent());
    assertEquals("John",dbCustomer.getFirstName());
    assertEquals("Doe", dbCustomer.getLastName());
    assertEquals("2000-01-01", dbCustomer.getBirthDate());
    //dbCustomer vem do banco de dados, salva gender como string;
    assertEquals("M", dbCustomer.getGender());
    //assertEquals(newCustomer.getGender(),Gender.valueOf(dbCustomer.getGender())); //getGender mesmso sendo do DB, getGender nao deveria retornar tipo Gender por causa do metodo ?
  }



}