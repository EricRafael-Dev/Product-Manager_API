package rest.productsmanager.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import rest.productsmanager.ProductsInterface;
import rest.productsmanager.dao.ProductDataAcessObjetct;
import rest.productsmanager.exception.ProductException;
import rest.productsmanager.model.Product;

@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource implements ProductsInterface {
	
	public Product findById(EntityManager manager, Long id){
		manager.getTransaction().begin();
		return manager.find(Product.class, id );
	}
	
	
	//Rota GET: Listar Produtos -> "/product"
	//QueryParams: Retorna um Produto pelo 'name'
	@GET
	public List<Product> getProducts(@QueryParam("name") @DefaultValue("") String name) throws ProductException {
		List<Product> products = new ProductDataAcessObjetct().list(name);
		return products;
	}
	
	
	//Rota POST: Adicionar Produtos -> "/product"
	@POST
	public Response addProduct(Product newProduct) throws ProductException {
		newProduct.validate();
		Product product = new ProductDataAcessObjetct().save(newProduct);
		return Response.ok()
    			.entity(product)
    			.build();
    }

	//Rota GET: Encontrar Produto através do ID -> "/product/[id]"
	@Path("/{id}")
	@GET
	public Response findProduct(@PathParam("id") Long id) throws ProductException {
		Product product = new ProductDataAcessObjetct().find(id);
		
		if(product == null){
			throw new ProductException("Produto nao encontrado...");
			}
        return Response.ok()
    			.entity(product)
    			.build();
	}

	
	//Rota PUT: Atualizar Produto através do ID -> "/product/[id]"
	@Path("/{id}")
	@PUT
	public Response updateProduct(Product newProduct, @PathParam("id") Long id) throws ProductException {
		Product product = new ProductDataAcessObjetct().update(newProduct, id);
		if(product == null){
			throw new ProductException("Produto nao encontrado...");
			}
        return Response.ok()
    			.entity(product)
    			.build();
	}

	
	//Rota DELETE: Deletar Produto através do ID -> "/product/[id]"
	@Path("/{id}")
	@DELETE
	public Response deleteProduct(@PathParam("id") Long id) throws ProductException {
		boolean isDeleted = new ProductDataAcessObjetct().delete(id);
		if(isDeleted){
			return Response.ok()
    			.entity("Deletado com Sucesso!")
    			.build();
		}
		throw new ProductException("Produto nao encontrado...");
	}

}