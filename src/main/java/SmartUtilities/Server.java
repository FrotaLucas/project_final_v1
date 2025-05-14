package SmartUtilities;

import com.sun.net.httpserver.HttpServer;

import SmartUtilities.Shared.CORSFilter;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.wadl.WadlFeature;

import java.net.URI;

// @ApplicationPath("/api")
public class Server {

    private static HttpServer server;

    public static void startServer(String url)
    {
        ResourceConfig config = new ResourceConfig().packages("SmartUtilities.Controllers").register(CORSFilter.class);

        //qual esta correto???
//        ResourceConfig config = new ResourceConfig()
//                .packages("SmartUtilities.Controllers", "SmartUtilities.Services.ICustomerService");

        // Disable WADL for XML
        config.property("jersey.config.server.wadl.disableWadl", true);
        server = JdkHttpServerFactory.createHttpServer(URI.create(url), config);
        System.out.println("Server running on port: " + url);
    }

    public static void stopServer()
    {
        if (server != null)
        {
            server.stop(1);
            System.out.println("Server stopped.");
        }
        else {
            System.out.println("Server not running.");
        }
    }
}
