import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.ApplicationPath;
import SmartUtilities.Controllers.CustomerController;

import com.sun.net.httpserver.HttpServer;
import java.net.URI;

@ApplicationPath("/api")
public class Servering extends ResourceConfig {

    private static HttpServer server;

    public Servering() {
        // Registrar pacotes contendo recursos
        packages("SmartUtilities.Controllers");

        // Registrar serviços e controladores
        //register(CustomerService.class);
        register(CustomerController.class);

        // Propriedade para desativar WADL
        property("jersey.config.server.wadl.disableWadl", true);
    }

    public static void startServer(String url) {
        try {
            // Criar configuração com os recursos registrados
            ResourceConfig config = new Servering();
            
            // Criar e iniciar o servidor
            URI uri = URI.create(url);
            server = JdkHttpServerFactory.createHttpServer(uri, config);

            System.out.println("Server running on: " + url);
        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    public static void stopServer() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server stopped.");
        }
    }
}
