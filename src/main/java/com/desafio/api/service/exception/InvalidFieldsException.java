package com.desafio.api.service.exception;

public class InvalidFieldsException extends RuntimeException {
   
	private static final long serialVersionUID = 5425097962304052199L;

	public InvalidFieldsException(String msg) {
        super(msg);
    }
}
