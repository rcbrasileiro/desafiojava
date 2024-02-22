package com.desafio.api.service.exception;

public class LicensePlateAlreadyExistsException extends RuntimeException {
   
	private static final long serialVersionUID = 5425097962304052199L;

	public LicensePlateAlreadyExistsException(String msg) {
        super(msg);
    }
}
