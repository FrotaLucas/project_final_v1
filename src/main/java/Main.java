// Required imports for JDBC
import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Services.CustomerService.CustomerService;
import SmartUtilities.Services.CustomerService.ICustomerService;
import SmartUtilities.models.Customer.Customer;
import org.checkerframework.checker.units.qual.C;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.printf("Hello and welcome! \n");
        try {

        Database database = new Database();
        CustomerService service = new CustomerService(database);

        //Create
//        Customer newCustomer = new Customer("Maria", "Helena", "1987-09-10", "W");
//        service.addNewCustomer(newCustomer);

        //udpate
        //Customer updatedCustomer = new Customer("Lucia", "Schultz", "1997-05-20", "W");
        //service.updateCustomer(updatedCustomer, 2);

        //delete
        //service.deleteCustomer(3);


        //getCustomer id
//        Customer customer = service.getCustomer(2);
//            System.out.println("Customer: " + "\n" +
//                    "First Name: " + customer.getFirstName() + "\n" +
//                    "Last Name: " + customer.getLastName() + "\n" +
//                    "Birthdate: " + customer.getBirthDate() + "\n" +
//                    "Gender: " + customer.getGender());

        //getCustomers List
            List<Customer> customerList= service.getCustomers();

            for (Customer c : customerList) {
                System.out.println("Customer: " + "\n" +
                        "First Name: " + c.getFirstName() + "\n" +
                        "Last Name: " + c.getLastName() + "\n" +
                        "Birthdate: " + c.getBirthDate() + "\n" +
                        "Gender: " + c.getGender() + "\n");
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } //catch (IOException e) {
            //System.out.println("Error reading SQL file: " + e.getMessage());
        //}
     }
}