package SmartUtilities.Services.ReadinServiceTest;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Enums.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


class ReadinServiceTest
{
    private ReadingService _readingService;


    @Test
    @BeforeEach
    public void setUp() {
      DataBase dataBaseMock = new Mockito.mock(DataBase.class);
      _readingService = new ReadingService(dataBaseMock);
    }

    @Test
    private void testAddNewReading()
    {
        Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", 2);
        //newReading.setId(1);
        _readingService.addNewReading(newReading);
        Optional<Customer> dbReading = _readingService.getReading(1);

        assertTrue(dbReading.isPresent());
        assertEquals("HEIZUNG", dbReading.getKindOfMeter());
        assertEquals("new checking gas", dbReading.getComment());
        assertEquals("X1100", dbReading.getMeterId());
        assertEquals(11111.0, dbReading.getMeterCount());
        assertEquals("2000-01-01", dbReading.getDateOfReading());
        assertEquals(2, dbReading.getCustomerId());
    }

    @Test
    private void testUpdateReading()
    {
      Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", 2);
      _readingService.addNewReading(newReading);
      //newReading.setId(1);
      Optional<Reading> dbReading = _readingService.getReading(1);
      dbReading.setKindOfMeter("STROM");
      dbReading.setComment("new checking eletricity");
      dbReading.setMeterId("Y2200");
      dbReading.setMeterCount(222222.0);
      dbReading.setSubstitute(false);
      dbReading.setDateOfReading("1990-01-01");
      // susbstituir setCustomer por setCustomerId para fazer os testes de ReadingServiceTest
      //dbReading.setCustomerId(4);
      _readingService.updatedReading(dbReading);

      Optional<Reading> retrievedReading = _readingService.getReading(1);

      assertTrue(retrievedReading.isPresent());
      assertEquals("STROM", retrievedReading.getKindOfMeter());
      assertEquals("new checking eletricity", retrievedReading.getComment());
      assertEquals("Y2200", retrievedReading.getMeterId());
      assertEquals(222222.0, retrievedCustomer.getMeterCount();)
      assertEquals(false, retrievedCustomer.getSubstitute());
      assertEquals("1990-01-01", retrievedCustomer.getDateOfReading());

    }

    @Test
    private void testDeleteReading()
    {
      Reading newReading = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", 2);
      _readingService.addNewReading(newReading);
      //newReading.setId(1);

      _readingService.deleteReading(1);

      Optional<Reading> dbReading = _readingService.getReading(1);
      assertFalse(dbReading.isPresent());
    }

    @Test
    private void testGetReading()
    {
      Reading newReading1 = new Reading("HEIZUNG", "new checking gas", "X1100", 11111.0, true, "2000-01-01", 1);
      _readingService.addNewReading(newReading);
      //newReading.setId(0); //talvez nao precise desse setId

       Reading newReading2 = new Reading("STROM", "new checking eletricity", "Y2200", 22222.0, true, "1990-01-01", 1);
      _readingService.addNewReading(newReading);
      //newReading.setId(1);
      //aqui vai ser  getReadingOfCustomer(1)
      //List<Reading> readingCustomer = _readingService.getReadingOfCustomer(1);
      assertTrue(2, readingCustomer.size());
      //pegar o primeiro salvo no bd. Metodo get da lib java.util.List
      Optional<Reading> retrievedReading1 = readingCustomer.get(0);//
      Optional<Reading> retrievedReading2 = readingCustomer.get(2);

        assertEquals("HEIZUNG", retrievedReading1.getKindOfMeter());
        assertEquals("new checking gas", retrievedReading1.getComment());
        assertEquals("X1100", retrievedReading1.getMeterId());
        assertEquals(11111.0, retrievedReading1.getMeterCount());
        assertEquals(true, retrievedReading1.getSubstitute());
        assertEquals("2000-01-01", retrievedReading1.getDateOfReading());
        assertEquals(1, retrievedReading1.getCustomerId());

        assertEquals("STROM", retrievedReading.getKindOfMeter());
        assertEquals("new checking eletricity", retrievedReading.getComment());
        assertEquals("Y2200", retrievedReading.getMeterId());
        assertEquals(22222.0, retrievedCustomer.getMeterCount();)
        assertEquals(true, retrievedCustomer.getSubstitute());
        assertEquals("1990-01-01", retrievedCustomer.getDateOfReading());
        assertEquals(1, retrievedReading1.getCustomerId());




    }
}