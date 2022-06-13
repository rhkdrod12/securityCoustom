package com.example.practicejpa.exception;

import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException {

	private String message ;
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	public GlobalException(String message) {
		this.message    = message;
	}
	
	public GlobalException(HttpStatus httpStatus, String message) {
		this.message    = message;
		this.httpStatus = httpStatus;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
