package com.example.practicejpa.exception;

import com.example.practicejpa.utils.codeMessage.SystemMessage;
import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {
	
	private String code;
	private String message;
	
	public AuthException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public AuthException(String msg) {
		super(msg);
	}
}
