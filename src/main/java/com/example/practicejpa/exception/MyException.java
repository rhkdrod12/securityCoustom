package com.example.practicejpa.exception;

import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.codeMessage.messageInterface.MessageCode;
import org.springframework.http.HttpStatus;

/**
 * 임시 이름
 * 현재 프로젝트 최상위 에러
 */
public class MyException extends RuntimeException{
	
	protected String code = SystemMessage.REQUEST_FAIL.Code();
	protected String message = SystemMessage.REQUEST_FAIL.Message();
	
	public MyException() {
	}
	
	public MyException(String message) {
		this.message = message;
	}
	
	public MyException(String code, String message) {
		this.code    = code;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getCode(){
		return code;
	}
	
	public MyException(MessageCode message) {
		this.code       = message.Code();
		this.message    = message.Message();
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setMessage(MessageCode message) {
		this.code = message.Code();
		this.message = message.Message();
	}
	
}
