package com.desafio.api.web.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestError implements Serializable {

	private static final long serialVersionUID = 2683576546693319585L;

	private String message;

	private int errorCode;
	
}
