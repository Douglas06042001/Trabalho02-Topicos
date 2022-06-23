package br.edu.univas.si7.topicos.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValidationError extends StandardError {
    
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String message, Date date) {
        super(message, status, date);
    }

    public void addError(String fieldName, String message){
        this.errors.add(new FieldMessage(fieldName, message));
    }
    
    public List<FieldMessage> getErrors() {
		return errors;
	}
}
