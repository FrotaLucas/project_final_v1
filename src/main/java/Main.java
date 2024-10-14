// Required imports for JDBC
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import SmartUtilities.DataBase.Database;
public class Main {
    public static void main(String[] args) throws IOException {

        System.out.printf("Hello and welcome!");

// 
        try {
            //Class.forName("org.mariadb.jdbc.Driver");
        //     final Connection con = DriverManager.getConnection(
        //             "jdbc:mariadb://localhost:3306/gm3?allowMultiQueries=true",
        //             "root", "frotalucas");
        
        Connection con = Database.connect();
        
        System.out.println("... connected");

            final DatabaseMetaData meta = con.getMetaData();
            System.out.format("Driver : %s %s.%s\n", meta.getDriverName(),
                    meta.getDriverMajorVersion(), meta.getDriverMinorVersion());
            System.out.format("DB : %s %s.%s (%s)\n",
                    meta.getDatabaseProductName(), meta.getDatabaseMajorVersion(),
                    meta.getDatabaseMinorVersion(),
                    meta.getDatabaseProductVersion());

            System.out.println("... Driver running");
            
        //read the SQL script from file and execute
        //String sqlFilePath = "D:/Java Projects/Vs Code/project_final_v1/src/main/java/smartUtilities/sql/createCustomerTable.sql"; // Path to the SQL file
        String sqlFilePath = "D:/Java Projects/Vs Code/project_final_v1/src/main/java/smartUtilities/sql/addNewCustomer.sql"; // Path to the SQL file
        String sqlScript = Database.readSQLFile(sqlFilePath); // Read SQL file content
        Database.executeSQLScript(con, sqlScript); // Execute the SQL script

       
            con.close();

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading SQL file: " + e.getMessage());
        }
     }
}