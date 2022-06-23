package br.edu.univas.si7.topicos.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.univas.si7.topicos.domain.Category;

public class CategoryDTO {

	@NotNull(message = "Name can not be null.")
	@NotEmpty(message = "Name can not be empty.")
	@Size(min = 5, max = 80, message = "The size must be between 5 and 80.")
	private String name;

	public CategoryDTO(Category category) {
		this.name = category.getName();
	}

	public CategoryDTO() {
	}

	public CategoryDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CategoryDTO [name=" + name + "]";
	}

}
