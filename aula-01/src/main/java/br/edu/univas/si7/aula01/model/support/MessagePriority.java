package br.edu.univas.si7.aula01.model.support;

import java.util.stream.Stream;

import br.edu.univas.si7.aula01.controller.exception.InvalidDataException;

public enum MessagePriority {

	LOW(10), MEDIUN(20), HIGH(30);
	
	private int code;
	
	private MessagePriority(int code) {
		this.code = code;
	}
	
	/**
	 * Coverte de Integer para MessagePriority
	 * @param code
	 * @return
	 */
	public static MessagePriority getPriority(Integer code) {
		//implementar do jeito que quiser
		return Stream.of(MessagePriority.values())
				.filter(t -> t.getCode() == code)
				.findFirst()
				.orElseThrow(() -> new InvalidDataException("Prioridade inválida: " + code));
	}
	
	public int getCode() {
		return code;
	}
}
