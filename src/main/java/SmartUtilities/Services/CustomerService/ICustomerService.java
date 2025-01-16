package SmartUtilities.Services.CustomerService;

import SmartUtilities.Model.Customer.Customer;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public interface ICustomerService {

    void addNewCustomer(Customer customer);

    void updateCustomer(Customer customer);

    boolean deleteCustomer(String uuid);

    Customer getCustomer(int id);

    List<Customer> getCustomers();

    Customer getCustomerByUuid(String Uuid);
}

