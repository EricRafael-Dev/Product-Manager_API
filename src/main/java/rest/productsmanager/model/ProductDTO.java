package rest.productsmanager.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import rest.productsmanager.exception.ProductException;

public class ProductDTO {
	
	private String name;
	private BigDecimal valor;
	private Integer quantity;
	
	public void validate() throws ProductException {
        Map<String, String> errors = new HashMap<>();

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "'name' obrigatorio.");
        } else if (name.length() < 2 || name.length() > 100) {
            errors.put("name", "'name' precisa ter entre 2 e 100 caracteres.");
        }

        if (valor == null) {
            errors.put("valor", "'valor' obrigatorio.");
        } else if (valor.compareTo(BigDecimal.ZERO) < 0) {
            errors.put("valor", "'valor' nao pode ser negativo.");
        }

        if (quantity == null) {
            errors.put("quantity", "'quantity' obrigatoria.");
        } else if (quantity < 0) {
            errors.put("quantity", "'quantity' nao pode ser negativo.");
        }

        if (!errors.isEmpty()) {
            throw new ProductException("Erro de validação nos dados de entrada.", Response.Status.BAD_REQUEST, errors);
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
