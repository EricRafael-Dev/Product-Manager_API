package rest.productsmanager;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class JAXRSConfiguration extends ResourceConfig {
    public JAXRSConfiguration() {
        packages("rest.productsmanager");
        register(JacksonFeature.class);
    }
}

