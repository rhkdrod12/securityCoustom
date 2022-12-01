package com.example.practicejpa.jwtSecurity.exception;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.utils.codeMessage.messageInterface.MessageCode;

public class JwtSecurityException extends GlobalException {
	
	private String code;
	private String message;
	
	public JwtSecurityException(String msg) {
		super(msg);
	}
	
	public JwtSecurityException(MessageCode messageCode){
		super(messageCode);
	}
	
}
