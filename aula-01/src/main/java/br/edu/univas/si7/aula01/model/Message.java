package br.edu.univas.si7.aula01.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.edu.univas.si7.aula01.model.support.MessagePriority;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;
	private String message;
	
	//@Enumerated(EnumType.ORDINAL) //não funciona junto com o MessagePriorityConverter
	private MessagePriority priority;

	public Message() {
	}

	public Message(int code, String message, MessagePriority priority) {
		super();
		this.code = code;
		this.message = message;
		this.priority = priority;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessagePriority getPriority() {
		return priority;
	}

	public void setPriority(MessagePriority priority) {
		this.priority = priority;
	}
}
