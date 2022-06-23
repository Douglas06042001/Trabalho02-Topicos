package br.edu.univas.si7.topicos.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.univas.si7.topicos.domain.Product;

public class ProductDTO {

	private Integer id;

	@NotEmpty(message = "O nome do produto não pode ser vazio.")
	@Size(min = 5, max = 30)
	private String name;
	@NotNull
	private Double price;
	@NotEmpty
	
	private String category;

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.category = product.getCategory().getName();
	}

	public ProductDTO() {
	}

	public ProductDTO(Integer id, String name, Double price, String category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", price=" + price + "]";
	}

}
