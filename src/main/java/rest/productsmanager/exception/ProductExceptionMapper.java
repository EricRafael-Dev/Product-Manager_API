package rest.productsmanager.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ProductExceptionMapper implements ExceptionMapper<ProductException> {

	public Response toResponse(ProductException exception) {
		return Response.status(Status.NOT_FOUND)
				.type(MediaType.APPLICATION_JSON)
				.entity(exception.getMessage())
				.build();
	}
	
}
