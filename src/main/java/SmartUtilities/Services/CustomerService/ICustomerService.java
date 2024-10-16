package SmartUtilities.Services.CustomerService;

import SmartUtilities.Model.Customer.Customer;

import java.util.List;

public interface ICustomerService {

    void addNewCustomer(Customer customer);

    void updateCustomer(Customer customer, int id);

    void deleteCustomer(int id);

    Customer getCustomer(int id);

    List<Customer> getCustomers();
}

