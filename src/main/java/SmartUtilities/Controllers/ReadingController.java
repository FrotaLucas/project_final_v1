package SmartUtilities.Controllers;

import java.io.ObjectInputFilter.Status;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Model.Reading.Reading;
import SmartUtilities.Services.CustomerService.CustomerService;
import SmartUtilities.Services.CustomerService.ICustomerService;
import SmartUtilities.Services.ReadingService.ReadingService;
import SmartUtilities.Shared.ServiceResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/readings")
public class ReadingController {
    
    private Database database = new Database();
    private ICustomerService _customerservice;
    private ReadingService _readingService = new ReadingService(database, _customerservice);
    private CustomerService _customerService = new CustomerService(database);

    // 4. Liste von Ablesungs-Objekten: PERGUNTAR PROFESSOR. Acredito que  nao tem que mostrar nada do customer pq eh a lista completa de Ablesungen
    //estou colocando os alesung dentro de properties com Array[]
    @GET  
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings()
    {
        List<Reading> readings = _readingService.getReadings();

        if(readings != null)
        {
            Map<String, Object> readingData = new LinkedHashMap<>();
            readingData.put("type","array");
            readingData.put("items", Map
                                        .of("type", "object",
                                            "required", List.of("id","customer","dateOfReading","meterId","substitute","meterCount","kindOfMeter"),
                                            "properties", readings));

            Map<String, Object> serviceResponseProperties = Map
                .of("readings", readingData);


             ServiceResponse<Reading> serviceResponse = new ServiceResponse<>(
                "Customer-JSON-Schema",
                "object",
                "readings",
                serviceResponseProperties);


            return Response.ok(serviceResponse).build();
        
        }
          // Error 404
          return Response.status(Response.Status.BAD_REQUEST).entity("No data found on database.").build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadingByUuid(@PathParam("uuid") String uuid)
    {
       
        Reading reading = _readingService.getReadingByUuid(uuid);
        int customerId = (reading == null) ? 0 : reading.getCustomerId();

        if (reading != null && customerId != 0)
        {
            Customer customer = _customerService.getCustomer(customerId);
            
            Map<String, Object> customerProperties = new LinkedHashMap<>();
            customerProperties.put("uuid", customer.getUuid());
            customerProperties.put("firstName", customer.getFirstName());
            customerProperties.put("lastName", customer.getLastName());
            customerProperties.put("birthDay", customer.getBirthDate());
            customerProperties.put("gender", customer.getGender());

            Map<String, Object> customerData = Map
                .of("anyOf", Map
                    .of("type", "object",
                        "required",List.of("firstName","lastName","gender"),
                        "properties", customerProperties));
            

            Map<String, Object> readingData = new LinkedHashMap<>();
            readingData.put("type","object");
            readingData.put("required", List.of("customer","dateOfReading","meterId","substitute","meterCount","kindOfMeter"));
            readingData.put("properties", Map
                                        .of("id", reading.getUuid(),
                                            "customer", customerData,
                                            "dateOfReading", reading.getDateOfReading(),
                                            "comment", reading.getComment(),
                                            "meterId",reading.getMeterId(),
                                            "substitute", reading.getSubstitute(),
                                            "meterCount",reading.getMeterCount(),
                                            "kindOfMeter",reading.getKindOfMeter()));

            
            Map<String, Object> serviceResponseProperties = Map
                .of("reading", readingData);


             ServiceResponse<Reading> serviceResponse = new ServiceResponse<>(
                "Customer-JSON-Schema",
                "object",
                "reading",
                serviceResponseProperties);


            return Response.ok(serviceResponse).build();
        }   
        
        
        // Error 404
        return Response.status(Response.Status.NOT_FOUND).entity("No data found on database.").build();
        
    
    }


}
