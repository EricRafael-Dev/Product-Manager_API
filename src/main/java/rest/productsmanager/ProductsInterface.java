package rest.productsmanager;

import java.util.List;

import javax.ws.rs.core.Response;

import rest.productsmanager.exception.ProductException;
import rest.productsmanager.model.Product;

public interface ProductsInterface {
	List<Product> getProducts(String name, Integer lenght, Integer page) throws ProductException;
	Response findProduct(Long id) throws ProductException;
	Response addProduct(Product product) throws ProductException;	
	Response updateProduct(Product product, Long id) throws ProductException;
	Response deleteProduct(Long id) throws ProductException;
}
