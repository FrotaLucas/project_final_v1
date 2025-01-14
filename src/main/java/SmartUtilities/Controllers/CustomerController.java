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

import javax.management.ObjectName;

//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

@Path("api/customers")
public class CustomerController {

    // option 1
    private Database database = new Database();
    private CustomerService customerService = new CustomerService(database);

    // option 2
    // private final ICustomerService customerService;
    //
    // @Inject
    // public CustomerController(ICustomerService costumerService)
    // {
    // this.customerService = costumerService;
    // }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) //INVESTIGAR SE POST METHOD PRECISA DEVOLVER JSON DE CUSTOMER
    public Response addCustomer(Customer customer) {
        // corrigir metod customerService para boleano
        // if(customer == null || !customerService.addNewCustomer(customer))
        if (customer == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No customer to add.")
                    .build();
        }
        Map<String, Object> customerProperties = new LinkedHashMap<>();
        customerProperties.put("id", customer.getId());
        customerProperties.put("firstName", customer.getFirstName());
        customerProperties.put("lastName", customer.getLastName());
        customerProperties.put("birthDay", customer.getBirthDate());
        customerProperties.put("gender", customer.getGender());

        Map<String, Object> serviceResponseProperties = Map
                .of("customer", Map
                        .of("type", "object",
                                "required", List.of("firstName", "lastName", "gender"),
                                "properties", customerProperties));

        ServiceResponse<Customer> serviceResponse = new ServiceResponse<>(
                "Customer-JSON-Schema",
                "object",
                "customer",
                serviceResponseProperties);

        customerService.addNewCustomer(customer);
        return Response.status(Response.Status.CREATED).entity(serviceResponse).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers() {
        List<Customer> dbCustomers = customerService.getCustomers();
        if (dbCustomers != null) {

            List<Map<String, Object>> dbCustomerListProperties = new ArrayList<>();

            for (Customer customer : dbCustomers) {
                Map<String, Object> customerProperties = new LinkedHashMap<>();

                    customerProperties.put("id", customer.getId());
                    customerProperties.put("firstName", customer.getFirstName());
                    customerProperties.put("lastName",customer.getLastName());
                    customerProperties.put("birthDate", customer.getBirthDate());
                    customerProperties.put("gender", customer.getGender());

                dbCustomerListProperties.add(customerProperties);
            }

             Map<String, Object> customerItems = new LinkedHashMap<>();
             customerItems.put("type","array");
             customerItems.put("items", Map.of("type","object",
                                                    "required",List.of("id","firstName","lastName","gender"),
                                                    "properties",dbCustomerListProperties));

             Map<String, Object> serviceResponseProperties = Map
                .of("customers", customerItems);

            //nao deveria retornar Map<String, Object> ??
            ServiceResponse<List<Customer>> serviceResponse = new ServiceResponse<>(
                "Customers-JSON-Schema",
                "object",
                "customers", 
                serviceResponseProperties);

            return Response.ok(serviceResponse).build();
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

        Customer customer = customerService.getCustomerByUuid(id);

        if (customer != null) {
            Map<String, Object> customerProperties = new LinkedHashMap<>();
            customerProperties.put("id", customer.getId());
            customerProperties.put("firstName", customer.getFirstName());
            customerProperties.put("lastName", customer.getLastName());
            customerProperties.put("birthDay", customer.getBirthDate());
            customerProperties.put("gender", customer.getGender());

            Map<String, Object> serviceResponseProperties = Map
                    .of("customer", Map
                            .of("type", "object",
                                    "required", List.of("firstName", "lastName", "gender"),
                                    "properties", customerProperties));

            ServiceResponse<Customer> serviceResponse = new ServiceResponse<>(
                    "Customer-JSON-Schema",
                    "object",
                    "customer",
                    serviceResponseProperties);

            return Response.ok(serviceResponse).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Customer with id " + id + " not found.")
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") String id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Id cannont be null")
                    .build();
        }

        boolean isDeleted = customerService.deleteCustomering(id);
        if (!isDeleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer with Id" + id + "not found")
                    .build();

        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
