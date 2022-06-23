package br.edu.univas.si7.topicos.support;

import java.util.Date;

import lombok.Getter;

@Getter
public class LoginError extends StandardError {
	String error;
	String path;

	public LoginError(Integer status, String error, String message, String path) {
		super(message, status, new Date());
		this.error = error;
		this.path = path;
	}
}
