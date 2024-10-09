package org.example;

// Required imports for JDBC
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
  //mvn -version and java -version
        //ja instalei maven e java 21

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

//        for (int i = 1; i <= 5; i++) {
//            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
//            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
//            System.out.println("i = " + i);
//        }
        try {
            //Class.forName("org.mariadb.jdbc.Driver");
            final Connection con = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/gm3?allowMultiQueries=true",
                    "root", "frotalucas");
            System.out.println("... connected");

            final DatabaseMetaData meta = con.getMetaData();
            System.out.format("Driver : %s %s.%s\n", meta.getDriverName(),
                    meta.getDriverMajorVersion(), meta.getDriverMinorVersion());
            System.out.format("DB : %s %s.%s (%s)\n",
                    meta.getDatabaseProductName(), meta.getDatabaseMajorVersion(),
                    meta.getDatabaseMinorVersion(),
                    meta.getDatabaseProductVersion());
            con.close();

        } catch (final SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
     }
}