package br.edu.univas.si7.aula01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.univas.si7.aula01.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

}
