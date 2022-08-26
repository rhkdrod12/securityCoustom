package com.example.practicejpa.exception;

import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException {
	static final String DEFAULT_FAIL_MESSAGE = "처리 중 오류가 발생하였습니다";
	
	private String message = DEFAULT_FAIL_MESSAGE;
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	public GlobalException() {
	}
	
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
