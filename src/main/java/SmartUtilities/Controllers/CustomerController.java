package SmartUtilities.Controllers;

import SmartUtilities.Services.CustomerService.ICustomerService;
import SmartUtilities.Shared.ServiceResponse;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

@Path("api/customers")
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
            {return Response.status(Response.Status.BAD_REQUEST)
                .entity("No customer to add.")
                .build();
            }
        
        customerService.addNewCustomer(customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers()
    {
        List<Customer> customers = customerService.getCustomers();
        if(customers != null)
        {
            return Response.ok(customers).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
        .entity("Customers not found")
        .build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") String id) {
        if (id == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer with id null.")
                    .build();
        }
    
        Customer dbCustomer = customerService.getCustomerByUuid(id);
    
        if (dbCustomer != null) {
            Map<String, Object> dbCustomerProperties = new LinkedHashMap<>();
            dbCustomerProperties.put("id", dbCustomer.getId());
            dbCustomerProperties.put("firstName", dbCustomer.getFirstName());
            dbCustomerProperties.put("lastName", dbCustomer.getLastName());
            dbCustomerProperties.put("birthDay", dbCustomer.getBirthDate());
            dbCustomerProperties.put("gender", dbCustomer.getGender());

            Map<String, Object> serviceResponseProperties = Map
            .of("customer", Map
                .of("type", "object",
                "required", List.of("firstName","lastName","gender"),
                "properties",dbCustomerProperties));

            ServiceResponse<Customer> serviceResponse = new ServiceResponse<> (
                    "Customer-JSON-Schema",
                    "object",
                    "customer",
                    serviceResponseProperties
            );
    
            return Response.ok(serviceResponse).build();
        }
    
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Customer with id " + id + " not found.")
                .build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") String id)
    {
        if (id == null)
         {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Id cannont be null")
                .build(); }
            
        boolean isDeleted = customerService.deleteCustomering(id);
        if( !isDeleted)
        {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Customer with Id" + id + "not found")
                .build();
             
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}
