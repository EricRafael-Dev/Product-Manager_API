package rest.productsmanager.exception;

import java.util.Collections;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import rest.productsmanager.model.ErrorResponseDTO;

@Provider
public class ProductExceptionMapper implements ExceptionMapper<ProductException> {

    @Override
    public Response toResponse(ProductException exception) {
    	Response.Status status = exception.getStatus(); 
    	int statusCode = status.getStatusCode();
        
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(statusCode, exception.getMessage());
        errorResponse.setDetalhes(Collections.emptyMap());
        
        return Response.status(status).entity(errorResponse).build();
    }
}
