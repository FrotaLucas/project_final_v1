// Required imports for JDBC
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Reading.Reading;
import SmartUtilities.Services.CustomerService.CustomerService;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Services.ReadingService.ReadingService;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.printf("Hello and welcome! \n");
        try {

        Database database = new Database();
        CustomerService service = new CustomerService(database);

        //Create

        //Customer newCustomer = new Customer("Alpai", "Elmer", "2005-08-20", "M");
        //service.addNewCustomer(newCustomer);

        //Customer newCustomer = new Customer("testN", "TestL", "2001-10-10", "W");
        //service.addTestCustomer(newCustomer);

        //udpate

        //Customer updatedCustomer = new Customer("Hanne", "Schutz", "1990-05-20", "M");
        //service.updateCustomer(updatedCustomer, 2);

        //delete
        //service.deleteCustomer(3);


        //PQ aqui eu preciso usar getFirstName e nao posso usar direto customer.firstName
        //getCustomer id
        //Customer customer = service.getCustomer(2);
        //System.out.println("Customer: " + "\n" +
        //"First Name: " + customer.getFirstName() + "\n" +
        //"Last Name: " + customer.getLastName() + "\n" +
        //"Birthdate: " + customer.getBirthDate() + "\n" +
        //"Gender: " + customer.getGender());

        //getCustomers List
//            List<Customer> customerList= service.getCustomers();
//            for (Customer c : customerList) {
//                System.out.println("Customer: " + "\n" +
//                        "First Name: " + c.getFirstName() + "\n" +
//                        "Last Name: " + c.getLastName() + "\n" +
//                        "Birthdate: " + c.getBirthDate() + "\n" +
//                        "Gender: " + c.getGender() + "\n");
//            }


            //Create Reading
            Reading reading = new Reading("HEIZUNG", "cheking gas", "B1000", 81.25, false, 2);
            ReadingService readingService = new ReadingService(database);

            readingService.addNewReading(reading);

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } //catch (IOException e) {
            //System.out.println("Error reading SQL file: " + e.getMessage());
        //}
     }
}