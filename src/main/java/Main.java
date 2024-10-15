// Required imports for JDBC
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Services.CustomerService.CustomerService;
import SmartUtilities.Services.CustomerService.ICustomerService;
import SmartUtilities.models.Customer.Customer;
import org.checkerframework.checker.units.qual.C;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.printf("Hello and welcome!");

// 
        try {
            //Class.forName("org.mariadb.jdbc.Driver");
        //     final Connection con = DriverManager.getConnection(
        //             "jdbc:mariadb://localhost:3306/gm3?allowMultiQueries=true",
        //             "root", "frotalucas");
        
//        Connection con = Database.connect();
//
//        System.out.println("... connected");
//
//            final DatabaseMetaData meta = con.getMetaData();
//            System.out.format("Driver : %s %s.%s\n", meta.getDriverName(),
//                    meta.getDriverMajorVersion(), meta.getDriverMinorVersion());
//            System.out.format("DB : %s %s.%s (%s)\n",
//                    meta.getDatabaseProductName(), meta.getDatabaseMajorVersion(),
//                    meta.getDatabaseMinorVersion(),
//                    meta.getDatabaseProductVersion());
//
//            System.out.println("... Driver running");
            
        //read the SQL script from file and execute
        //String sqlFilePath = "D:/Java Projects/Project Final V1/src/main/java/SmartUtilities/SQL/createCustomerTable.sql"; // Path to the SQL file
        //String sqlFilePath = "D:/Java Projects/Project Final V1/src/main/java/SmartUtilities/SQL/addNewCustomer.sql"; // Path to the SQL file
        //String sqlFilePath = "D:/Java Projects/Project Final V1/src/main/java/SmartUtilities/SQL/updateCustomer.sql";
            //String sqlFilePath = "D:/Java Projects/Project Final V1/src/main/java/SmartUtilities/SQL/deleteCustomer.sql"; // Path to the SQL file
        //String sqlScript = Database.readSQLFile(sqlFilePath); // Read SQL file content
        //Database.executeSQLScript(con, sqlScript); // Execute the SQL script

        Database database = new Database();
        CustomerService service = new CustomerService(database);
        //Customer newCustomer = new Customer("Maria", "Helena", "1997-05-20", "W");
        //create
        //service.addNewCustomer(newCustomer);

        //udpate
//            Customer updatedCustomer = new Customer("Lucia", "Schultz", "1997-05-20", "W");
//            service.updateCustomer(updatedCustomer, 4);
        //delete
        service.deleteCustomer(4);

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } //catch (IOException e) {
            //System.out.println("Error reading SQL file: " + e.getMessage());
        //}
     }
}