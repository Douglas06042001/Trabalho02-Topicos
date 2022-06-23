package br.edu.univas.si7.topicos.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.edu.univas.si7.topicos.domain.enums.CustomerType;
import br.edu.univas.si7.topicos.domain.enums.UserProfile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer implements Serializable {

	@Id
	@EqualsAndHashCode.Include
	private String id; // CPF ou CNPJ
	private String name;
	@Column(unique = true)
	private String email;

	// @Enumerated(EnumType.ORDINAL)
	// não funciona em conjunto com o AttributeConverter de Category
	private CustomerType type;
	private String phoneNumber;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	@Setter(AccessLevel.NONE)
	private List<Address> addresses = new ArrayList<>();

	@OneToMany(mappedBy = "customer")
	@Setter(AccessLevel.NONE)
	private List<Order> orders = new ArrayList<>();
	
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROFILES")
	private Set<UserProfile> profiles = new HashSet<UserProfile>();

	public Customer(String id, String name, String email, String phoneNumber, CustomerType type, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.type = type;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

	public void addAddress(Address... address) {
		this.addresses.addAll(Arrays.asList(address));
	}

	public void addOrders(Order... orders) {
		this.orders.addAll(Arrays.asList(orders));
	}
	
	public void addProfiles(UserProfile... profiles) {
		this.profiles.addAll(Arrays.asList(profiles));
	}
}