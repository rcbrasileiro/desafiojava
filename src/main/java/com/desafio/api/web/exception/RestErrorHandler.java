package com.desafio.api.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.desafio.api.service.exception.EmailAlreadyExistsException;
import com.desafio.api.service.exception.InvalidFieldsException;
import com.desafio.api.service.exception.LicensePlateAlreadyExistsException;
import com.desafio.api.service.exception.LoginAlreadyExistsException;

@RestControllerAdvice
public class RestErrorHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RestError> requestMisssingFieldsException(MethodArgumentNotValidException ex) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		RestError error = new RestError("Missing fields", status.value());
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler({ InvalidFieldsException.class })
	public ResponseEntity<RestError> requestInvalidFieldsException(InvalidFieldsException ex) {
		return ResponseEntity.badRequest().body(new RestError("Invalid fields", HttpStatus.BAD_REQUEST.value()));
	}

	@ExceptionHandler({ LicensePlateAlreadyExistsException.class, EmailAlreadyExistsException.class,
			LoginAlreadyExistsException.class })
	public ResponseEntity<RestError> licensePlateAlreadyExistsException(LicensePlateAlreadyExistsException ex) {

		HttpStatus status = HttpStatus.CONFLICT;

		RestError restError = new RestError(ex.getMessage(), status.value());

		return ResponseEntity.status(status).body(restError);
	}

	@ExceptionHandler({ AuthenticationException.class })
	@ResponseBody
	public ResponseEntity<RestError> handleAuthenticationException(Exception ex) {
		RestError restError = new RestError("Invalid login or password", HttpStatus.UNAUTHORIZED.value());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restError);
	}
}
