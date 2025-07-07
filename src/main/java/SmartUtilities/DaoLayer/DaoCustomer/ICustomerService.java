package SmartUtilities.DaoLayer.DaoCustomer;

import SmartUtilities.Model.Customer.Customer;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public interface ICustomerService {

    boolean addNewCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(String uuid);

    Customer getCustomer(int id);

    List<Customer> getCustomers();

    Customer getCustomerByUuid(String Uuid);

    boolean deleteAllCustomers();
}

