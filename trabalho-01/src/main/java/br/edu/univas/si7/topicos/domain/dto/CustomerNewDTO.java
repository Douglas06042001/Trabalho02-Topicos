package br.edu.univas.si7.topicos.domain.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustomerNewDTO {

	private String cpf;// pesquisar como validar o CPF

	@NotNull(message = "Value can not be null.")
	@NotEmpty(message = "Value can not be empty.")
	@Size(min = 5, max = 80, message = "The size must be between 5 and 80.")
	private String name;

	@NotNull(message = "Value can not be null.")
	@NotEmpty(message = "Value can not be empty.")
	@Email(message = "Invalid email.")
	private String email;

	@NotNull(message = "Value can not be null.")
	private Integer type;

	@NotEmpty(message = "Value can not be empty.")
	private String street;

	@NotNull(message = "Value can not be null.")
	@PositiveOrZero
	private Integer number;

	private String extra;

	@NotEmpty(message = "Value can not be empty.")
	private String neighbour;

	@NotEmpty(message = "Value can not be empty.")
	private String zipCode;

	@NotNull(message = "Value can not be null.")
	@Min(0)
	private String city;

	@NotNull(message = "Value can not be null.")
	@Min(0)
	private String state;

	@NotNull(message = "Phone number can not be null.")
	@NotEmpty(message = "Phone number can not be empty.")
	@Size(min = 8, max = 20, message = "The size must be between 8 and 20.")
	private String phoneNumber;
	
	@NotNull(message = "Password can not be null.")
	@NotEmpty(message = "Password can not be empty.")
	@Size(min = 5, max = 20, message = "The size must be between 5 and 20.")
	private String password;
	
	private List<String> profiles;

}
