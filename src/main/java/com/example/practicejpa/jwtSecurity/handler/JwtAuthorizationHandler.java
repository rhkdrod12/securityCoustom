package com.example.practicejpa.jwtSecurity.handler;

import com.example.practicejpa.jwtSecurity.JWTResult;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthorizationHandler implements JwtSecurityHandler {
	
	@Override
	public void onSuccess(HttpServletRequest request, HttpServletResponse response, JWTResult jwtResult) {
	
	}
	
	@Override
	public void onFailure(HttpServletRequest request, HttpServletResponse response, JwtSecurityException authException) {
	
	}
}
