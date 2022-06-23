package br.edu.univas.si7.topicos.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.edu.univas.si7.topicos.domain.Address;
import br.edu.univas.si7.topicos.domain.Customer;
import br.edu.univas.si7.topicos.domain.dto.CustomerDTO;
import br.edu.univas.si7.topicos.domain.dto.CustomerNewDTO;
import br.edu.univas.si7.topicos.domain.enums.CustomerType;
import br.edu.univas.si7.topicos.domain.enums.UserProfile;
import br.edu.univas.si7.topicos.repositories.AddressRepository;
import br.edu.univas.si7.topicos.repositories.CustomerRepository;
import br.edu.univas.si7.topicos.security.UserPrincipal;
import br.edu.univas.si7.topicos.support.exceptions.AuthorizationException;
import br.edu.univas.si7.topicos.support.exceptions.InvalidDataException;
import br.edu.univas.si7.topicos.support.exceptions.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;

	@Autowired
	private AddressRepository addressRepo;

	public List<Customer> findAll() {
		return repo.findAll();
	}

	public Customer findById(String id) {
		checkCustomer(id);
		Optional<Customer> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Customer " + id + " not found."));
	}

	public Customer createCustomer(Customer newCustomer) {
		Optional<Customer> existingCustomer = repo.findById(newCustomer.getId());
		if (existingCustomer.isPresent()) {
			throw new InvalidDataException("Customer already exist with this id: " + newCustomer.getId());
		}
		checkDuplicateEmail(newCustomer.getEmail());

		Customer customer = repo.save(newCustomer);
		addressRepo.saveAll(customer.getAddresses());
		return customer;
	}

	public void updateCustomer(Customer customer, String id) {
		if (id == null || customer == null || !id.equals(customer.getId())) {
			throw new InvalidDataException("Invalid customer id.");
		}

		checkEmailAlreadyExists(customer, id);
		Customer existingObj = findById(id);

		updateData(existingObj, customer);
		repo.save(customer);
	}

	public void deleteCustomer(String id) {
		if (id == null) {
			throw new InvalidDataException("Customer id can not be null.");
		}
		Customer customer = findById(id);
		try {
			repo.delete(customer);
		} catch (DataIntegrityViolationException e) {
			throw new InvalidDataException("Can not delete a customer with products.");
		}
	}

	public List<Customer> findAllWithOrder(String orderBy, String direction) {
		return repo.findAll(Sort.by(Direction.valueOf(direction), orderBy));
	}

	private void checkDuplicateEmail(String email) {
		Customer existingSameEmail = repo.findByEmail(email);
		if (existingSameEmail != null) {
			throw new InvalidDataException("Customer already exist with this email: " + email);
		}
	}

	private void checkEmailAlreadyExists(Customer customer, String id) {
		Customer existingSameEmail = repo.findByEmail(customer.getEmail());
		if (existingSameEmail != null && !id.equals(existingSameEmail.getId())) {
			throw new InvalidDataException("Another customer is already using this email: " + customer.getEmail());
		}
	}

	public Customer toCustomer(CustomerNewDTO customerNewDTO) {
		Customer customerEntity = newCustomerToCustomer(customerNewDTO);
		Address addr = newCustomerToAddress(customerNewDTO, customerEntity);
		customerEntity.addAddress(addr);
		return customerEntity;
	}

	private void updateData(Customer existingObj, Customer customer) {
		existingObj.setName(customer.getName());
	}

	public Customer toCustomer(CustomerDTO customer) {
		Customer entity = new Customer(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhoneNumber(),
				CustomerType.toEnum(customer.getType()), customer.getPassword());
		Set<UserProfile> list = customer.getProfiles().stream().map(p -> UserProfile.toEnum(p)).collect(Collectors.toSet());
		entity.addProfiles(list.toArray(new UserProfile[list.size()]));
		return entity;
	}

	public Customer newCustomerToCustomer(CustomerNewDTO customerNewDTO) {
		Customer entity = new Customer(customerNewDTO.getCpf(), customerNewDTO.getName(), customerNewDTO.getEmail(),
				customerNewDTO.getPhoneNumber(), CustomerType.toEnum(customerNewDTO.getType()), 
				customerNewDTO.getPassword());
		Set<UserProfile> list = customerNewDTO.getProfiles().stream().map(p -> UserProfile.toEnum(p)).collect(Collectors.toSet());
		entity.addProfiles(list.toArray(new UserProfile[list.size()]));
		return entity;
	}

	public Address newCustomerToAddress(CustomerNewDTO customerNewDTO, Customer customerEntity) {
		return new Address(customerNewDTO.getStreet(), customerNewDTO.getNumber(), customerNewDTO.getExtra(),
				customerNewDTO.getNeighbour(), customerNewDTO.getZipCode(), customerEntity, customerNewDTO.getCity(),
				customerNewDTO.getState());
	}
	
	public void checkCustomer(String id) {
		UserPrincipal user = UserService.getAuthenticated();
		if (user == null || !user.hasRole(UserProfile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
	}
}
