package rest.productsmanager.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import rest.productsmanager.exception.ProductException;

@Entity
@XmlRootElement
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	private String name;
	private BigDecimal valor;
	private Integer quantity;

	public Product(){}

	public Product(Long id, String name, BigDecimal valor, Integer quantity) {
		super();
		this.id = id;
		this.name = name;
		this.valor = valor;
		this.quantity = quantity;
	}


	public void validate() throws ProductException{
		String err = "";
		if(name == null || name.isEmpty()){
			err += "'name' obrigatorio; \n";
		}
		if(valor == null){
			err += "'valor' obrigatorio; \n";
		}
		if(quantity == null){
			err += "'quantity' obrigatoria; \n";
		}
		
		if (!err.isEmpty()){
			throw new ProductException(err, Response.Status.BAD_REQUEST);
		}
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
