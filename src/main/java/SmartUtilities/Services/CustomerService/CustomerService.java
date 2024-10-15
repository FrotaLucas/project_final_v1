package SmartUtilities.Services.CustomerService;

import SmartUtilities.DataBase.Database;
import SmartUtilities.models.Customer.Customer;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService implements ICustomerService{

    private Database _database;

    public CustomerService(Database database)
    {
        this._database = database;
    }
    @Override
    public void addNewCustomer(Customer customer) {
        String sql = "INSERT INTO customers (firstName, lastName, birthDate, gender) VALUES ('" +
                customer.getFirstName() + "', '" +
                customer.getLastName() + "', '" +
                customer.getBirthDate() + "', '" +
                customer.getGender() + "')";
        try {
            this._database.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            System.out.println("Error while adding new customer: " + e.getMessage());
        }

    }

    @Override
    public void updateCustomer(Customer customer, int id) {
        String sql = "UPDATE customers SET firstName = '" + customer.getFirstName() +
                "', lastName = '" + customer.getLastName() +
                "', birthDate = '" + customer.getBirthDate() +
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

        try {
            this._database.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error while deleting customer: " + e.getMessage());
        }
    }

    @Override
    public Customer getCustomer(int id) {
        String sql = "SELECT * FROM customers WHERE id = '" + id + "'";  //nao precisa desse ultimo ' revisar !!!!
        Customer customer;


        try (ResultSet rs = this._database.executeQuery(sql)){
            if(rs.next())
            {  //depois pensar em colocar um Id dentro de customer para receber to banco de dados !!!!!!!!!!
                return new Customer(rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("birthDate"),
                        rs.getString("gender"));
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
                customers.add( new Customer(rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("birthDate"),
                        rs.getString("gender"))
                );
            }
            return customers;
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving customers: " + e.getMessage());
        }

        return null;
    }
}
