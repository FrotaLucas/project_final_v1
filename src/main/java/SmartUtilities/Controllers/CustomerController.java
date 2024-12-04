package SmartUtilities.Controllers;

import SmartUtilities.Services.CustomerService.ICustomerService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Services.CustomerService.CustomerService;
import org.glassfish.jersey.internal.inject.Custom;

import java.util.List;

//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;


public class CustomerController {

    private ICustomerService customerService;
    CustomerController(ICustomerService costumerService)
    {
        this.customerService = costumerService;
    }

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





}
