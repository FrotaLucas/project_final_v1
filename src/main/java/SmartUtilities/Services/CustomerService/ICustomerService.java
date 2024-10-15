package SmartUtilities.Services.CustomerService;

import SmartUtilities.models.Customer.Customer;

import java.util.List;

public interface ICustomerService {

    void addNewCustomer(Customer customer);

    void updateCustomer(Customer customer, int id);

    void deleteCustomer(int id);

    Customer getCostumer(int id);

    List<Customer> getCustomer();
}

