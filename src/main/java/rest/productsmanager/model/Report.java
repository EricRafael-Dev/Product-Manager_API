package rest.productsmanager.model;

import java.math.BigDecimal;
import java.util.List;

public class Report {
	private Long totalQuantity;
	private BigDecimal maxPrice;
	private List<Product> expensiveProduct;
	
	public Report(){
		
	}

	public Report(Long totalQuantity, BigDecimal maxPrice, List<Product> expensiveProduct) {
		super();
		this.totalQuantity = totalQuantity;
		this.maxPrice = maxPrice;
		this.expensiveProduct = expensiveProduct;
	}

	public Long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public List<Product> getExpensiveProduct() {
		return expensiveProduct;
	}

	public void setExpensiveProduct(List<Product> expensiveProduct) {
		this.expensiveProduct = expensiveProduct;
	}

}
