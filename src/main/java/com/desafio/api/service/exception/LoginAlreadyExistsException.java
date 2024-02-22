package com.desafio.api.service.exception;

public class LoginAlreadyExistsException extends RuntimeException {
   
	private static final long serialVersionUID = 5425097962304052199L;

	public LoginAlreadyExistsException(String msg) {
        super(msg);
    }
}
