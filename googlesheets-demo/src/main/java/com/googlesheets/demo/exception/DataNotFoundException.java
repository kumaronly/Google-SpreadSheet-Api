package com.googlesheets.demo.exception;

import lombok.Data;

@Data
public class DataNotFoundException extends RuntimeException {
	
	private String errorCode;
	public DataNotFoundException( String errorcode,String message) {
		super(message);
		this.errorCode=errorcode;
	}

}
