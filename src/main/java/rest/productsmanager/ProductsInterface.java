package rest.productsmanager;

import java.util.List;

import javax.ws.rs.core.Response;

public interface ProductsInterface {
	public List<Product> getProducts();
	public Product findProduct(Long id);
	public void addProduct(Product product);	
	public Product updateProduct();
	public Product deleteProduct();
}
