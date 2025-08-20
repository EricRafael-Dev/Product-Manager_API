package rest.productsmanager.dao;

import java.util.List;

import javax.persistence.EntityManager;

import rest.productsmanager.model.Product;
import rest.productsmanager.util.JPAUtil;

public class ProductDataAcessObjetct {
	
	private static final int Product = 0;

	public Product findById(EntityManager manager, Long id){
		manager.getTransaction().begin();
		return manager.find(Product.class, id);
	}
	
	public List<Product> list(){
		
		EntityManager manager = JPAUtil.getEntityManager();
		
		try {
			manager.getTransaction().begin();
			List<Product> products = manager.createQuery("SELECT product FROM Product product", Product.class).getResultList();
        	return products;
        	
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

	public Product find(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		
		try {
			Product product = findById(manager, id);
			if(product != null){
				return product;
			}
        	
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
	
	public Product save(Product product){
		
		EntityManager manager = JPAUtil.getEntityManager();
		
        try {
        	manager.getTransaction().begin();
        	manager.persist(product);
        	manager.getTransaction().commit();
        	return product;
        	
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

	public Product update(Product newProduct, Long id){
		
		EntityManager manager = JPAUtil.getEntityManager();
		
		Product product = findById(manager, id);
        try {
        	System.out.println(product);
        	if(product != null){
        		if(newProduct.getName() != null && !newProduct.getName().isEmpty()){
        			product.setName(newProduct.getName());
        		}
        		if(newProduct.getValor() != null){
        			product.setValor(newProduct.getValor());;
        		}
        		if(newProduct.getQuantity() != null){
        			product.setQuantity(newProduct.getQuantity());
        		}
        		manager.getTransaction().commit();
        		return product;
        	}
        	
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

	public boolean delete(Long id){
		
		EntityManager manager = JPAUtil.getEntityManager();
		
		Product product = findById(manager, id);
		
        try {
        	if(product != null){
        		manager.remove(product);
        		manager.getTransaction().commit();
        		return true;
        	}
        	
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
            	manager.getTransaction().rollback();
            }
            e.printStackTrace();
            
        } finally {
        	manager.close();
        }
        return false;
	}

}
