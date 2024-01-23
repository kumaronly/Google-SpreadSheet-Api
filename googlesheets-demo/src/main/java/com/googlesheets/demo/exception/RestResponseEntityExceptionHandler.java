package com.googlesheets.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.googlesheets.demo.response.ErrorResponse;


public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException
	(DataNotFoundException dataNotFoundException) {

		return new ResponseEntity<ErrorResponse>(new ErrorResponse().builder()
				.errorCode(dataNotFoundException.getErrorCode())
				.errorMessage(dataNotFoundException.getMessage())
				.build(),HttpStatus.NOT_FOUND);
	}

}
