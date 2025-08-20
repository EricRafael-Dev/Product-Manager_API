package rest.productsmanager.exception;

import javax.ws.rs.core.Response;

public class ProductException extends Exception {

    private final Response.Status status;

    public ProductException(String erro) {
        super(erro);
        this.status = Response.Status.NOT_FOUND;
    }

    public ProductException(String erro, Response.Status status) {
        super(erro);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }
}
