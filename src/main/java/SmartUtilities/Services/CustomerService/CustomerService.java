package SmartUtilities.Services.CustomerService;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import jakarta.inject.Singleton;

import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public boolean addNewCustomer(Customer customer) {
        if (customer != null)
        {
            String sql = "INSERT INTO customers (first_name, last_name, birthdate, gender, uui_id) VALUES ('" +
                    customer.getFirstName() + "', '" +
                    customer.getLastName() + "', '" +
                    customer.getBirthDate() + "', '" +
                    customer.getGender() + "', '" +
                    customer.getUuid() + "')";
            this._database.queryWithoutReturn(sql);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {

        if( customer != null && customer.getId() != null)
        {
            Optional<Integer> idOptional = customer.getId();
            if (idOptional.isEmpty())
            {
                return false;
            }
            //String id = idOptional.map(String::valueOf).orElse("NULL");
    
            int id = idOptional.get();
            String sql = "UPDATE customers SET first_name = '" + customer.getFirstName() +
                    "', last_name = '" + customer.getLastName() +
                    "', birthdate = '" + customer.getBirthDate() +
                    "', gender = '" + customer.getGender() +
                    "' WHERE id = " + id;
    
            this._database.queryWithoutReturn(sql);
            return true;
        }

        return false;
    }

    //substituir deleCustomer por deleteCustomering
    @Override
    public boolean deleteCustomer(String uuid) {
        if (uuid !=null)
        {
            Customer dbCustomer = getCustomerByUuid(uuid);
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
        }
        return false;
    }

    @Override
    public Customer getCustomer(int id) {
        if(id != 0)
        {
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
            }
            catch (SQLException e) {
                System.out.println("Error retrieving customer with id: " + id + e.getMessage());
                return null;
            }
        }
        return null;
    }

    @Override
    public List<Customer> getCustomers() {
        String sql = "SELECT * from customers";
        List<Customer> customers = new ArrayList<>();

        try (ResultSet rs = this._database.queryWithReturn(sql)) {
            while (rs.next()) {

                Customer dbCustomer = new Customer(rs.getInt("Id"), rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("birthDate"),
                        rs.getString("gender"));
                dbCustomer.setUuid(UUID.fromString(rs.getString("uui_id")));
                customers.add(dbCustomer);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving data." + e.getMessage());
            return null;
        }
        return customers;
    }

    @Override
    public Customer getCustomerByUuid(String id_uui) {
        
        if( id_uui != null)
        {
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

        }
        return null;
    }
}
