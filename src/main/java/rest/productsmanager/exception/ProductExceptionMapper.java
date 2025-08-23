package rest.productsmanager.exception;

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
        if (exception.getDetails() != null) {
            errorResponse.setDetails(exception.getDetails());
        }
        return Response.status(status).entity(errorResponse).build();

    }
}
