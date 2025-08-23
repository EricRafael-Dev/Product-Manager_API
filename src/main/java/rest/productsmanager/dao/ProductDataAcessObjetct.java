package rest.productsmanager.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import rest.productsmanager.model.Product;
import rest.productsmanager.model.Report;
import rest.productsmanager.util.JPAUtil;

public class ProductDataAcessObjetct {
	
	public Product findById(EntityManager manager, Long id){
		manager.getTransaction().begin();
		return manager.find(Product.class, id);
	}
	
	public List<Product> list(String name, Integer length, Integer page){
		
		EntityManager manager = JPAUtil.getEntityManager();
		List<Product> products;
		try {
			manager.getTransaction().begin();
			if(!name.isEmpty()){
				products = manager.createQuery("SELECT product FROM Product product WHERE name = :name", Product.class)
						.setParameter("name", name)
						.getResultList();				

			}else{
				products = manager.createQuery("SELECT product FROM Product product", Product.class)
						.setFirstResult(page*length)
						.setMaxResults(length)
						.getResultList();				
			}
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
	
	public Report report(){
		
		EntityManager manager = JPAUtil.getEntityManager();
		Report report = new Report();
		
		try {
			manager.getTransaction().begin();
			
			Long totalQuantity = manager.createQuery("SELECT COUNT(product) FROM Product product", Long.class)
					.getSingleResult();
			report.setTotalQuantity(totalQuantity);
			
			BigDecimal  maxPrice = manager.createQuery("SELECT COALESCE(SUM (product.valor*product.quantity)) FROM Product product", BigDecimal.class)
					.getSingleResult();
			report.setMaxPrice(maxPrice);
			
			List<Product> expensiveProduct = manager.createQuery("SELECT product FROM Product product ORDER BY product.valor DESC", Product.class)
					.setMaxResults(1)
					.getResultList();
			report.setExpensiveProduct(expensiveProduct);
			
			List<Product> cheapestProduct = manager.createQuery("SELECT product FROM Product product ORDER BY product.valor ASC", Product.class)
					.setMaxResults(1)
					.getResultList();
			report.setCheapestProduct(cheapestProduct);
			
        	return report;
        	
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
