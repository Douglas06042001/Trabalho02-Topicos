package br.edu.univas.si7.aula01.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.edu.univas.si7.aula01.model.Message;

public class MessageDTO {

	@NotNull(message = "O texto da mensagem não pode ser null.")
	@NotEmpty(message = "O texto da mensagem não pode ser vazia.")
	@Size(min = 5, max = 30, message = "O texto da mensagem deve ter no mínimo 5 e no máximo 30 caracteres.")
	private String message;
	
	@NotNull(message = "A prioridade não pode ser null.")
	@Positive(message = "A prioridade deve ser um valor positivo.")
	private Integer priority;
	
	private String priorityText;
	
	public MessageDTO() {
	}
	
	public MessageDTO(Message msg) {
		this.message = msg.getMessage();
		this.priority = msg.getPriority().getCode();
		this.priorityText = msg.getPriority().name();
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public String getPriorityText() {
		return priorityText;
	}
}
