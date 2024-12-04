package SmartUtilities;

import SmartUtilities.Controllers.CustomerController;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

// Define a base URL para as APIs RESTful
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    private final Set<Class<?>> classes = new HashSet<>();

    public ApplicationConfig() {
        // Register class for RESTful ressources
        classes.add(CustomerController.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}

