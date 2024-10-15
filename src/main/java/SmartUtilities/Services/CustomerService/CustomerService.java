package SmartUtilities.Services.CustomerService;

import SmartUtilities.DataBase.Database;
import SmartUtilities.models.Customer.Customer;
import java.sql.SQLException;
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
            this._database.refactoredExecuteSQl(sql);
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
            this._database.refactoredExecuteSQl(sql);
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
            this._database.refactoredExecuteSQl(sql);
        } catch (SQLException e) {
            System.out.println("Error while deleting customer: " + e.getMessage());
        }
    }

    @Override
    public Customer getCostumer(int id) {
        return null;
    }

    @Override
    public List<Customer> getCustomer() {
        return List.of();
    }
}
