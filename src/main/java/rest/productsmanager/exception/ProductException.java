package rest.productsmanager.exception;

import java.util.Map;
import javax.ws.rs.core.Response;

public class ProductException extends Exception {

    private final Response.Status status;
    private Map<String, String> details;

    public ProductException(String message, Response.Status status) {
        super(message);
        this.status = status;
        this.details = null;
    }

    public ProductException(String message, Response.Status status, Map<String, String> details) {
        super(message);
        this.status = status;
        this.details = details;
    }

    public Response.Status getStatus() {
        return status;
    }
    
    public Map<String, String> getDetails() {
        return details;
    }
}