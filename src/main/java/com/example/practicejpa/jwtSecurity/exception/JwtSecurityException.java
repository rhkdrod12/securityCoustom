package com.example.practicejpa.jwtSecurity.exception;

import com.example.practicejpa.utils.codeMessage.messageInterface.MessageCode;
import org.springframework.security.core.AuthenticationException;

public class JwtSecurityException extends AuthenticationException {
	
	private String code;
	private String message;
	
	public JwtSecurityException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public JwtSecurityException(String msg) {
		super(msg);
	}
	
	public JwtSecurityException(MessageCode messageCode){
		super(messageCode.Message());
	}
	
}
