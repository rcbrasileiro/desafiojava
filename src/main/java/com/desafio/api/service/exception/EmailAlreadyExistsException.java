package com.desafio.api.service.exception;

public class EmailAlreadyExistsException extends RuntimeException {
   
	private static final long serialVersionUID = 5425097962304052199L;

	public EmailAlreadyExistsException(String msg) {
        super(msg);
    }
}
