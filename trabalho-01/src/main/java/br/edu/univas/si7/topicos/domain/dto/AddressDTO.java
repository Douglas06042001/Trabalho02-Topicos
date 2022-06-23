package br.edu.univas.si7.topicos.domain.dto;

import br.edu.univas.si7.topicos.domain.Address;

public class AddressDTO {

	private String street;
	private int number;
	private String extra;
	private String neighbour;
	private String zipCode;
	private String city;
	private String state;

	public AddressDTO() {
	}

	public AddressDTO(Address address) {
		this.street = address.getStreet();
		this.number = address.getNumber();
		this.extra = address.getExtra();
		this.neighbour = address.getNeighbour();
		this.zipCode = address.getZipCode();
		this.city = address.getCity();
		this.state = address.getState();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getNeighbour() {
		return neighbour;
	}

	public void setNeighbour(String neighbour) {
		this.neighbour = neighbour;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
