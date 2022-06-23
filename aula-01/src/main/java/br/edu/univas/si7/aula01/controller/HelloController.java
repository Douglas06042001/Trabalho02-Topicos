package br.edu.univas.si7.aula01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.univas.si7.aula01.model.Message;
import br.edu.univas.si7.aula01.model.support.MessagePriority;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@GetMapping("/v1")
	public String hello() {
		return "Hello v1!";
	}

	@GetMapping("/v3")
	public Message helloMessage() {
		return new Message(1, "Hello from v3", MessagePriority.LOW);
	}
}
