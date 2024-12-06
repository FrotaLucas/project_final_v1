package SmartUtilities.Controllers;

import SmartUtilities.Services.CustomerService.ICustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Services.CustomerService.CustomerService;
import org.glassfish.jersey.internal.inject.Custom;


import java.io.IOException;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

@Path("customers")
public class CustomerController {

    //option 1
    private Database database = new Database();
    private CustomerService customerService = new CustomerService(database);


    //option 2
//    private final ICustomerService customerService;
//
//    @Inject
//    public CustomerController(ICustomerService costumerService)
//    {
//        this.customerService = costumerService;
//    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(Customer customer) {
        //corrigir metod customerService para boleano
        //if(customer == null || !customerService.addNewCustomer(customer))
        if(customer == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(customer).build();
        customerService.addNewCustomer(customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();

    }

    //@Path("api")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers()
    {
        List<Customer> customers = customerService.getCustomers();
        return Response.ok(customers).build();
    }



}
