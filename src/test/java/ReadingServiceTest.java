import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Model.Reading.Reading;
import SmartUtilities.Enums.KindOfMeter;
import SmartUtilities.Services.CustomerService.CustomerService;
import SmartUtilities.Services.CustomerService.ICustomerService;
import SmartUtilities.Services.ReadingService.ReadingService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;   // For running code after each test method
import org.junit.jupiter.api.BeforeEach;  // For running code before each test method
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*; // For static assertions like assertEquals(), assertTrue(), etc.


public class ReadingServiceTest {

    private CustomerService _customerService;
    private ReadingService _readingService;
    private Database _database;
    private Connection _connection;

    @BeforeEach
    void setUp() throws SQLException //start connection
    {
        _database = new Database();
        _connection = Database.connect();
        _readingService = new ReadingService(_database);
        _customerService = new CustomerService(_database);
    }

    @Test
    public void testAddNewReading() {

        //add customer
        Customer newCustumer = new Customer(null, "John", "Doe", "2000-01-01","M");
        String uuidCustomer = newCustumer.getUuid().toString();
        _customerService.addNewCustomer(newCustumer);
        Customer dbCustomer = _customerService.getCustomerByUuid(uuidCustomer);
        int idCustomer = dbCustomer.getId().orElse(0);

        Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", idCustomer, dbCustomer);
        String uuid = newReading.getUuid().toString();

        _readingService.addNewReading(newReading);
        Reading dbReading = _readingService.getReadingByUuid(uuid);

        assertNotNull(dbReading);
        assertEquals("HEIZUNG", dbReading.getKindOfMeter().toString());
        assertEquals("new checking gas", dbReading.getComment());
        assertEquals("X1100", dbReading.getMeterId());
        assertEquals(11111.0, dbReading.getMeterCount());
        assertEquals("2000-01-01", dbReading.getDateOfReading());
        assertEquals(idCustomer, dbReading.getCustomerId());

        _readingService.deleteReading(1, "2000-01-01");
        _customerService.deleteCustomer(uuidCustomer);
    }

    @Test
    public void testUpdateReading() {

         //add customer
         Customer newCustumer = new Customer(null, "John", "Doe", "2000-01-01","M");
         String uuidCustomer = newCustumer.getUuid().toString();
         _customerService.addNewCustomer(newCustumer);
         Customer dbCustomer = _customerService.getCustomerByUuid(uuidCustomer);
         int idCustomer = dbCustomer.getId().orElse(0);
 
         Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", idCustomer, dbCustomer);
         String uuid = newReading.getUuid().toString();

        _readingService.addNewReading(newReading);
        Reading dbReading = _readingService.getReadingByUuid(uuid);

        dbReading.setKindOfMeter(KindOfMeter.valueOf("STROM"));
        dbReading.setComment("new checking eletricity");
        dbReading.setMeterId("Y2200");
        dbReading.setMeterCount(222222.0);
        dbReading.setSubstitute(false);
        dbReading.setDateOfReading("1990-01-01");

        _readingService.updateNewReading(dbReading);

        Reading retrievedReading = _readingService.getReadingByUuid(uuid);

        assertNotNull(retrievedReading);
        assertEquals("STROM", retrievedReading.getKindOfMeter().toString());
        assertEquals("new checking eletricity", retrievedReading.getComment());
        assertEquals("Y2200", retrievedReading.getMeterId());
        assertEquals(222222.0, retrievedReading.getMeterCount());
        assertEquals(false, retrievedReading.getSubstitute());
        assertEquals("1990-01-01", retrievedReading.getDateOfReading());

        _readingService.deleteReading(1, "1990-01-01");
        _customerService.deleteCustomer(uuidCustomer);

    }

    @Test
    public void testDeleteReading() {
        //add customer
        Customer newCustumer = new Customer(null, "John", "Doe", "2000-01-01","M");
        String uuidCustomer = newCustumer.getUuid().toString();
        _customerService.addNewCustomer(newCustumer);
        Customer dbCustomer = _customerService.getCustomerByUuid(uuidCustomer);
        int idCustomer = dbCustomer.getId().orElse(0);

        Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", idCustomer, dbCustomer);
        //String uuid = newReading.getUuid().toString();

        _readingService.addNewReading(newReading);
        //Reading dbReading = _readingService.getReadingByUuid(uuid);
        _readingService.deleteReading(idCustomer, "2000-01-01");

        Reading retrievedReading = _readingService.getReadingOfCustomer(idCustomer, "2000-01-01");
        assertNull(retrievedReading);

        _customerService.deleteCustomer(uuidCustomer);
    }

   @Test
   public void testGetReading() {
       //add customer
       Customer newCustumer = new Customer(null, "John", "Doe", "2000-01-01","M");
       String uuidCustomer = newCustumer.getUuid().toString();
       _customerService.addNewCustomer(newCustumer);
       Customer dbCustomer = _customerService.getCustomerByUuid(uuidCustomer);
       int idCustomer = dbCustomer.getId().orElse(0); 

       Reading newReading1 = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", idCustomer, dbCustomer);
       Reading newReading2 = new Reading("STROM", "new checking eletricity", "Y2200", 22222.0, true, "1990-01-01", idCustomer, dbCustomer);

       String uuid = newReading1.getUuid().toString();

       _readingService.addNewReading(newReading1);
       _readingService.addNewReading(newReading2);
       List<Reading> readingCustomer = _readingService.getReading(idCustomer);

       assertEquals(2, readingCustomer.size());
       Reading retrievedReading1 = readingCustomer.get(0);//
       Reading retrievedReading2 = readingCustomer.get(1);

       assertEquals("HEIZUNG", retrievedReading1.getKindOfMeter().toString());
       assertEquals("new checking gas", retrievedReading1.getComment());
       assertEquals("X1100", retrievedReading1.getMeterId());
       assertEquals(11111.0, retrievedReading1.getMeterCount());
       assertEquals(true, retrievedReading1.getSubstitute());
       assertEquals("2000-01-01", retrievedReading1.getDateOfReading());
       assertEquals(idCustomer, retrievedReading1.getCustomerId());

       assertEquals("STROM", retrievedReading2.getKindOfMeter().toString());
       assertEquals("new checking eletricity", retrievedReading2.getComment());
       assertEquals("Y2200", retrievedReading2.getMeterId());
       assertEquals(22222.0, retrievedReading2.getMeterCount());
       assertEquals(true, retrievedReading2.getSubstitute());
       assertEquals("1990-01-01", retrievedReading2.getDateOfReading());
       assertEquals(idCustomer, retrievedReading1.getCustomerId());

       _readingService.deleteReading(idCustomer, "2000-01-01");
       _readingService.deleteReading(idCustomer, "1990-01-01");
       _customerService.deleteCustomer(uuidCustomer);
   }

}
