// Required imports for JDBC

import java.io.IOException;
import java.sql.SQLException;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Reading.Reading;
import SmartUtilities.Server;
import SmartUtilities.Services.CustomerService.CustomerService;
import SmartUtilities.Model.Customer.Customer;
//import SmartUtilities.Services.ReadingService.ReadingService;

public class Main {
    public static void main(String[] args) throws IOException {

        try {
            System.out.printf("Hello and welcome! \n");

//            Database database = new Database();
//            CustomerService service = new CustomerService(database);
//            ReadingService readingService = new ReadingService(database, service);
            
            String url = "http://localhost:8080/";
            Server.startServer(url);

        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
        } //catch (IOException e) {
        //System.out.println("Error reading SQL file: " + e.getMessage());
        //}
    }
}