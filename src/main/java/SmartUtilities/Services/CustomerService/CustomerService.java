package SmartUtilities.Services.CustomerService;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;

import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerService implements ICustomerService{

    private Database _database;

    public CustomerService(Database database)
    {
        this._database = database;
    }
    @Override
    public void addNewCustomer(Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name, birthdate, gender, uui_id) VALUES ('" +
                customer.getFirstName() + "', '" +
                customer.getLastName() + "', '" +
                customer.getBirthDate() + "', '" +
                customer.getGender() + "', '" +
                customer.getUuid() + "')";
        try {
            this._database.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            System.out.println("Error while adding new customer: " + e.getMessage());
        }

    }

    @Override
    public void updateCustomer(Customer customer) {
        Optional<Integer> idOptional = customer.getId();

        // Check if the id is present and retrieve the value, or handle if not present
        String id = idOptional.map(String::valueOf).orElse("NULL"); // Converts id to String, or "NULL" if not present

        String sql = "UPDATE customers SET first_name = '" + customer.getFirstName() +
                "', last_name = '" + customer.getLastName() +
                "', birthdate = '" + customer.getBirthDate() +
                "', gender = '" + customer.getGender() +
                "' WHERE id = " + id;
        try {
            this._database.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            System.out.println("Error while updating customer: " + e.getMessage());
        }
    }

    @Override
    public void deleteCustomer(int id) {
        String sql = "DELETE from customers WHERE id = '" + id + "'";
        String sqlReading = "UPDATE data_reading SET customer_id = NULL WHERE customer_id = '" + id + "'";

        //first execute sqlReading because data_reading depends on customers
        try {
            this._database.executeUpdate(sqlReading);
        } catch (SQLException e) {
            System.out.println("Error while deleting customer: " + e.getMessage());
        }

        try {
            this._database.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error while deleting reading: " + e.getMessage());
        }
    }

    @Override
    public Customer getCustomer(int id) {
        String sql = "SELECT * FROM customers WHERE id = '" + id + "'";  //nao precisa desse ultimo ' entender pq !!!!
        Customer customer;

        try (ResultSet rs = this._database.executeQuery(sql)){
            if(rs.next())
            {
                //depois pensar em colocar um Id dentro de customer para receber do banco de dados. Id precisa ser opcional !!!!!!!
                Customer dbCustomer = new Customer(null, rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("birthDate"),
                        rs.getString("gender"));

                dbCustomer.setUuid(UUID.fromString(rs.getString("uui_id")));

                return  dbCustomer;
            }
        }
        catch (SQLException e){
            System.out.println("Error while retrieving customer: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Customer> getCustomers() {
        String sql = "SELECT * from customers";
        List<Customer> customers = new ArrayList<>();

        try (ResultSet rs = this._database.executeQuery(sql);){
            while (rs.next())
            {
                Customer dbCustomer = new Customer(null, rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("birthDate"),
                        rs.getString("gender"));

                dbCustomer.setUuid(UUID.fromString(rs.getString("uui_id")));
                customers.add(dbCustomer);
            }
            return customers;
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving customers: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Customer getCustomerByUuid(String id_uui) {
        String sql = "SELECT * FROM customers WHERE uui_id = '" + id_uui + "'";
        Customer customer;

        try (ResultSet rs = this._database.executeQuery(sql)){
            if(rs.next())
            {  //depois pensar em colocar um Id dentro de customer para receber do banco de dados !!!!!!!!!!
                customer = new Customer(rs.getInt("id") ,rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("birthDate"),
                        rs.getString("gender"));
                customer.setUuid(UUID.fromString(rs.getString("uui_id")));
                return customer;
            }
        }
        catch (SQLException e){
            System.out.println("Error while retrieving customer: " + e.getMessage());
        }

        return null;
    }
}
