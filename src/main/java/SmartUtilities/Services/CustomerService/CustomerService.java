package SmartUtilities.Services.CustomerService;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import jakarta.inject.Singleton;

import java.io.Console;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class CustomerService implements ICustomerService {

    private Database _database;

    public CustomerService(Database database) {
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
        this._database.queryWithoutReturn(sql);
    }

    @Override
    public void updateCustomer(Customer customer) {
        Optional<Integer> idOptional = customer.getId();
        String id = idOptional.map(String::valueOf).orElse("NULL");

        String sql = "UPDATE customers SET first_name = '" + customer.getFirstName() +
                "', last_name = '" + customer.getLastName() +
                "', birthdate = '" + customer.getBirthDate() +
                "', gender = '" + customer.getGender() +
                "' WHERE id = " + id;

        this._database.queryWithoutReturn(sql);
    }

    @Override
    public void deleteCustomer(int id) {
        String sqlReading = "UPDATE data_reading SET customer_id = NULL WHERE customer_id = '" + id + "'";
        String sql = "DELETE from customers WHERE id = '" + id + "'";

        this._database.queryWithoutReturn(sqlReading);
        this._database.queryWithoutReturn(sql);
    }

   
    public boolean deleteCustomering(String Uuid) {

        Customer dbCustomer = getCustomerByUuid(Uuid);
        //Optional<Integer> id = customer.getId();
        int id = dbCustomer.getId().orElse(0);
        System.out.println(id);
        
        if(id != 0)
        {
            String sqlReading = "UPDATE data_reading SET customer_id = NULL WHERE customer_id = '" + id + "'";
            String sql = "DELETE from customers WHERE id = '" + id + "'";
        
            this._database.queryWithoutReturn(sqlReading);
            this._database.queryWithoutReturn(sql);
            return true;
        }
        return false;
    }

    @Override
    public Customer getCustomer(int id) {
        String sql = "SELECT * FROM customers WHERE id = '" + id + "'";
        try (ResultSet rs = this._database.queryWithReturn(sql)) {
            if (rs.next()) {
                Customer dbCustomer = new Customer(null, rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("birthDate"),
                        rs.getString("gender"));
                dbCustomer.setUuid(UUID.fromString(rs.getString("uui_id")));
                return dbCustomer;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving customer with id: " + id, e);
        }
        return null;
    }

    @Override
    public List<Customer> getCustomers() {
        String sql = "SELECT * from customers";
        List<Customer> customers = new ArrayList<>();

        try (ResultSet rs = this._database.queryWithReturn(sql)) {
            while (rs.next()) {
                Customer dbCustomer = new Customer(null, rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("birthDate"),
                        rs.getString("gender"));
                dbCustomer.setUuid(UUID.fromString(rs.getString("uui_id")));
                customers.add(dbCustomer);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving customers", e);
        }
        return customers;
    }

    @Override
    public Customer getCustomerByUuid(String id_uui) {
        String sql = "SELECT * FROM customers WHERE uui_id = '" + id_uui + "'";
        try (ResultSet rs = this._database.queryWithReturn(sql)) {
            if (rs.next()) {
                Customer customer = new Customer(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("birthDate"),
                        rs.getString("gender"));
                customer.setUuid(UUID.fromString(rs.getString("uui_id")));
                return customer;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving customer with UUID: " + id_uui, e);
        }
        return null;
    }
}
