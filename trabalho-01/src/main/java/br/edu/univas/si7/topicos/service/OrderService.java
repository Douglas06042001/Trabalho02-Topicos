package br.edu.univas.si7.topicos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.edu.univas.si7.topicos.domain.Category;
import br.edu.univas.si7.topicos.domain.Order;
import br.edu.univas.si7.topicos.domain.dto.OrderDTO;
import br.edu.univas.si7.topicos.repositories.OrderRepository;
import br.edu.univas.si7.topicos.support.exceptions.InvalidDataException;
import br.edu.univas.si7.topicos.support.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;

	public List<Order> findAll() {
		return repo.findAll();
	}

	public Order findById(Integer id) {
		Optional<Order> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Order" + id + "not found"));

	}

	public Integer createOrder(Order order) {
		repo.save(order);
		return order.getId();
	}

	public void updateOrder(Order order, Integer id) {
		if (id == null || order == null || id.equals(order.getId())) {
			throw new InvalidDataException("Invalid order id.");
		}
		Order existingObj = findById(id);
		updateData(existingObj, order);
		repo.save(order);
	}

	public void updateData(Order existingOrder, Order order) {
		existingOrder.setCustomer(order.getCustomer());
	}

	public void deleteOrder(Integer id) {
		if (id == null) {
			throw new InvalidDataException("Order id can not be null.");
		}
		Order ord = findById(id);
		try {
			repo.delete(ord);
		} catch (DataIntegrityViolationException e) {
			throw new InvalidDataException("Can not delete a order with products.");
		}
	}

	public Order toOrder(OrderDTO order) {
		return new Order();
	}

	public static Category toCategory(String categoryName) {
		return new Category(categoryName);
	}
}
