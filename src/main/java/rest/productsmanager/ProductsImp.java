package rest.productsmanager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductsImp implements ProductsInterface {
	
	@GET
	public List<Product> getProducts() {
		EntityManager manager = JPAUtil.getEntityManager();
		try {
        	return manager.createQuery("SELECT product FROM Product product", Product.class).getResultList();
        	
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
            	manager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
        	manager.close();
        }
		return null;
	}

	@Path("/{id}")
	@GET
	public Product findProduct(@PathParam("id") Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		try {
        	return manager.find(Product.class, id );
        	
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
            	manager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
        	manager.close();
        }
		return null;
	}
	
	@POST
	public void addProduct(Product product) {
		
		EntityManager manager = JPAUtil.getEntityManager();

        try {
        	manager.getTransaction().begin();
        	manager.persist(product);
        	manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
            	manager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
        	manager.close();
        }
    }
	

	@Path("/{id}")
	@PUT
	public Product updateProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Path("/{id}")
	@DELETE
	public Product deleteProduct() {
		// TODO Auto-generated method stub
		return null;
	}

}
