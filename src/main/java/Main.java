// Required imports for JDBC
import java.io.IOException;
import java.sql.SQLException;

import SmartUtilities.DataBase.Database;
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

//        Customer newCustomer = new Customer(null, "Marius", "Lehel", "1995-03-20", "M");
//        service.addNewCustomer(newCustomer);

        //Customer newCustomer = new Customer(null,"test", "Maia", "2001-10-10", "W");
        //service.addTestCustomer(newCustomer);

        //udpate

        Customer updatedCustomer = new Customer(null,"Lena", "Maia", "1990-05-20", "W");
        service.updateCustomer(updatedCustomer, 11);

        //delete
        //service.deleteCustomer(12);


        //PQ aqui eu preciso usar getFirstName e nao posso usar direto customer.firstName
        //getCustomer id
        Customer customer = service.getCustomer(11);
        System.out.println("Customer: " + "\n" +
        "First Name: " + customer.getFirstName() + "\n" +
        "Last Name: " + customer.getLastName() + "\n" +
        "Birthdate: " + customer.getBirthDate() + "\n" +
        "Gender: " + customer.getGender() + "\n" +
        "Uuid: " + customer.getUuid());

        //getCustomers List
//            List<Customer> customerList= service.getCustomers();
//            for (Customer c : customerList) {
//                System.out.println("Customer: " + "\n" +
//                        "First Name: " + c.getFirstName() + "\n" +
//                        "Last Name: " + c.getLastName() + "\n" +
//                        "Birthdate: " + c.getBirthDate() + "\n" +
//                        "Gender: " + c.getGender() + "\n" +
//                        "Uuid: " + c.getUuid());
//            }

            //Reading
            ReadingService readingService = new ReadingService(database);

            //Create Reading
            //Reading reading = new Reading("STROM", "cheking eletricity", "E1000", 81.25, false, "2024-10-15", 1);
            //readingService.addNewReading(reading);

            //Update reading

            //Reading updatedReading = new Reading("HEIZUNG", "new checking gas", "B1100", 11111.0, true, "2024-10-18", 2);
            //readingService.updateNewReading(updatedReading);

            //delete reading
            //readingService.deleteReading(1, "2024-10-15");

            //getReading
//            List<Reading> readingList = readingService.getReading(2);
//            for (Reading c : readingList) {
//                System.out.println("customer_id: " + c.getCustomerId()  + "\n" +
//                        "kind of meter: " + c.getKindOfMeter() + "\n" +
//                        "comment: " + c.getComment() + "\n" +
//                        "meterId: " + c.getMeterId() + "\n" +
//                        "meter amount: " + c.getMeterCount() + "\n" +
//                        "substitue: " + c.getSubstitute() + "\n" +
//                        "Date Of Reading: " + c.getDateOfReading() + "\n"  +
//                        "uui_id: " + c.getId());
//            }

            //getReading
//            List<Reading> readingList = readingService.getReadings();
//            for (Reading c : readingList) {
//                System.out.println("customer_id: " + c.getCustomerId()  + "\n" +
//                        "kind of meter: " + c.getKindOfMeter() + "\n" +
//                        "comment: " + c.getComment() + "\n" +
//                        "meterId: " + c.getMeterId() + "\n" +
//                        "meter amount: " + c.getMeterCount() + "\n" +
//                        "substitue: " + c.getSubstitute() + "\n" +
//                        "Date Of Reading: " + c.getDateOfReading() + "\n"  +
//                        "uui_id: " + c.getId());
//            }


        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } //catch (IOException e) {
            //System.out.println("Error reading SQL file: " + e.getMessage());
        //}
     }
}