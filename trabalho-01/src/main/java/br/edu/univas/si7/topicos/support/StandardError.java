package br.edu.univas.si7.topicos.support;

import java.io.Serializable;
import java.util.Date;

public class StandardError implements Serializable { //imutable

	private String message;
	private Integer status;
	private Date date;
	
	public StandardError(String message, Integer status, Date date) {
		super();
		this.message = message;
		this.status = status;
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getDate() {
		return date;
	}
}
