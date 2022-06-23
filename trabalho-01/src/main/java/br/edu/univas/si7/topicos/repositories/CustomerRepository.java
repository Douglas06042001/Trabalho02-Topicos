package br.edu.univas.si7.topicos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.univas.si7.topicos.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Transactional(readOnly = true)
    Customer findByEmail(String email);
}
