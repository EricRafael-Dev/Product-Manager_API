package rest.productsmanager.model;

import java.math.BigDecimal;
import javax.ws.rs.core.Response;
import rest.productsmanager.exception.ProductException;

public class ProductDTO {
	
	private String name;
	private BigDecimal valor;
	private Integer quantity;
	
	public void validate() throws ProductException{
		String err = "";
		if(name == null || name.isEmpty()){
			err += "'name' obrigatorio.";
		}
		if(valor == null){
			err += "'valor' obrigatorio.";
			
		}else if(valor.compareTo(BigDecimal.ZERO) == -1){
				err += "'valor' nao pode ser negativo.";
			}
		if(quantity == null){
			err += "'quantity' obrigatoria.";
			
		}else if(quantity < 0){
				err += "'quantity' nao pode ser negativo.";
			}
		if (!err.isEmpty()){
			throw new ProductException(err, Response.Status.BAD_REQUEST);
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
