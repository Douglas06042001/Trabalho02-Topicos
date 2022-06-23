package br.edu.univas.si7.topicos.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.edu.univas.si7.topicos.domain.Order;
import br.edu.univas.si7.topicos.domain.dto.OrderDTO;
import br.edu.univas.si7.topicos.service.OrderService;

@RestController
@RequestMapping("/Orders")
public class OrderController {

	@Autowired
	private OrderService service;

	@GetMapping("")
	public ResponseEntity<List<OrderDTO>> findAll() {
		List<OrderDTO> list = service.findAll().stream().map(o -> new OrderDTO(o)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> find(@PathVariable Integer id) {
		Order ord = service.findById(id);
		return ResponseEntity.ok().body(new OrderDTO(ord));
	}
	
	@Transactional
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderDTO order) {
		Integer newOrderId = service.createOrder(service.toOrder(order));

		// não é obrigatório. É só para ficar em conformidade com o Restful
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOrderId).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateOrder(@Valid @RequestBody OrderDTO order, @PathVariable Integer id) {
		service.updateOrder(service.toOrder(order), id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable Integer id) {
		service.deleteOrder(id);
	}

}
