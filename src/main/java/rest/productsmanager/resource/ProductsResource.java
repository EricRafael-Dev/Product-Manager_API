package rest.productsmanager.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;
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
import rest.productsmanager.model.ProductDTO;
import rest.productsmanager.model.Report;

@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource implements ProductsInterface {
	
	public Product findById(EntityManager manager, Long id){
		manager.getTransaction().begin();
		return manager.find(Product.class, id );
	}
	
	
	// Rota GET: Listar Produtos -> "/product"
	// QueryParams: 
		// 'Name'   -> Retorna um Produto pelo 'name';
		// 'Lenght' -> Limita a quantidade de produtos em cada página;
		// 'Page'   -> Controla em qual página o cliente se encontra;
	@GET
	public List<Product> getProducts(@QueryParam("name") @DefaultValue("") String name,
			@QueryParam("length") @DefaultValue("20") Integer length,
			@QueryParam("page") @DefaultValue("0") Integer page) throws ProductException {
		List<Product> products = new ProductDataAcessObjetct().list(name, length, page);
		return products;
	}
	

	// Rota GET: Encontrar Produto através do ID -> "/product/[id]"
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

	/* Rota GET: Retorna um relatório com: Valor total armazenado,
										   Total de produtos em estoque,
										   Produto mais caro,
										   Produto mais barato.
	*/
	@Path("/report")
	@GET
	public Report getReport() throws ProductException {
		Report report = new ProductDataAcessObjetct().report();
		return report;
	}
	
	
	// Rota POST: Adicionar Produtos -> "/product"
	@POST
	public Response addProduct(ProductDTO productDTO) throws ProductException {
		productDTO.validate();
		Product newProduct = new Product();
		newProduct.setName(productDTO.getName());
		newProduct.setValor(productDTO.getValor());
		newProduct.setQuantity(productDTO.getQuantity());
		
		Product product = new ProductDataAcessObjetct().save(newProduct);
		return Response.status(Status.CREATED)
				.entity(product)
				.build();
	}
	
	
	// Rota PUT: Atualizar Produto através do ID -> "/product/[id]"
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

	
	// Rota DELETE: Deletar Produto através do ID -> "/product/[id]"
	@Path("/{id}")
	@DELETE
	public Response deleteProduct(@PathParam("id") Long id) throws ProductException {
		boolean isDeleted = new ProductDataAcessObjetct().delete(id);
		if(isDeleted){
			return Response.status(Status.NO_CONTENT)
    			.build();
		}
		throw new ProductException("Produto nao encontrado...");
	}

}