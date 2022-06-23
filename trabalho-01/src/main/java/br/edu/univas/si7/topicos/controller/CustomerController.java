package br.edu.univas.si7.topicos.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.univas.si7.topicos.domain.Customer;
import br.edu.univas.si7.topicos.domain.dto.CustomerDTO;
import br.edu.univas.si7.topicos.domain.dto.CustomerNewDTO;
import br.edu.univas.si7.topicos.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("")
    public ResponseEntity<List<CustomerDTO>> findAll() {
        List<CustomerDTO> list = service.findAll()
            .stream()
            .map(c -> new CustomerDTO(c))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

  //extra
    @GetMapping("/ordered")
    public ResponseEntity<List<CustomerDTO>> findAllPage( 
        @RequestParam(value="orderBy", defaultValue = "name") String orderBy,
        @RequestParam(value="direction", defaultValue = "ASC") String direction) {

        List<CustomerDTO> customers = 
        		service.findAllWithOrder(orderBy, direction)
        		.stream().map(c -> new CustomerDTO(c))
        		.collect(Collectors.toList());
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    public ResponseEntity<CustomerDTO> find(@PathVariable String id) {
        Customer customer = service.findById(id);
        return ResponseEntity.ok().body(new CustomerDTO(customer));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerNewDTO newCustomer) {
        Customer customer = service.createCustomer(service.toCustomer(newCustomer));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@Valid @RequestBody CustomerDTO customer, @PathVariable String id) {
        service.updateCustomer(service.toCustomer(customer), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String id) {
        service.deleteCustomer(id);
    }
}