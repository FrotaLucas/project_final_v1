package SmartUtilities.Controllers;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Services.CustomerService.CustomerService;
import SmartUtilities.Services.CustomerService.ICustomerService;
import SmartUtilities.Services.ReadingService.ReadingService;
import jakarta.ws.rs.DELETE;  // Importando o verbo DELETE
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("api/setUpDB")
public class DataBaseController {
    
    private Database database = new Database();
    private ICustomerService _ICustomerService;
    private ReadingService _readingService = new ReadingService(database, _ICustomerService);
    private CustomerService _customerService = new CustomerService(database);

    @DELETE  // Mapeando o método como DELETE
    public Response setUpDB()
    {
        boolean successfullDeleteCustomers = _customerService.deleteAllCustomers();
        boolean successfullDeleteReadings = _readingService.deleteAllReadins();

        if(successfullDeleteCustomers && successfullDeleteReadings) // Usando o operador correto (&&)
            return Response.status(Response.Status.OK)
                            .entity("All tables successfully reset.")
                            .build();

        return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Error while resetting tables.")
                            .build();
    }
}
