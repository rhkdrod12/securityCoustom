package com.example.practicejpa.exception;

import com.example.practicejpa.utils.codeMessage.messageInterface.MessageCode;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException {
	
	private String code = SystemMessage.REQUEST_FAIL.Code();
	private String message = SystemMessage.REQUEST_FAIL.Message();
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	
	public GlobalException() {
	}
	
	public GlobalException(String message) {
		this.message = message;
	}
	
	public GlobalException(MessageCode message) {
		this.code       = message.Code();
		this.message    = message.Message();
	}
	
	public GlobalException(HttpStatus httpStatus, String message) {
		this.message    = message;
		this.httpStatus = httpStatus;
	}
	
	public GlobalException(HttpStatus httpStatus, MessageCode message) {
		this.code       = message.Code();
		this.message    = message.Message();
		this.httpStatus = httpStatus;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	public String getCode(){
		return code;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setMessage(MessageCode message) {
		this.code = message.Code();
		this.message = message.Message();
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
