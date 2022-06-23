package br.edu.univas.si7.topicos.support;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.edu.univas.si7.topicos.support.exceptions.AuthorizationException;
import br.edu.univas.si7.topicos.support.exceptions.InvalidDataException;
import br.edu.univas.si7.topicos.support.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(e.getMessage(), HttpStatus.NOT_FOUND.value(), new Date());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError objectNotFound(InvalidDataException e, HttpServletRequest request) {
        return new StandardError(e.getMessage(), HttpStatus.BAD_REQUEST.value(), new Date());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError objectNotFound(MethodArgumentNotValidException e, HttpServletRequest request) {

        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation error.", new Date());
        e.getBindingResult().getFieldErrors().stream()
                .forEach(err -> error.addError(err.getField(), err.getDefaultMessage()));
        return error;
    }
    
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
		StandardError err = new StandardError(e.getMessage(), HttpStatus.FORBIDDEN.value(), new Date());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public StandardError authorization(Exception e, HttpServletRequest request) {
        return new StandardError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date());
    }   
}