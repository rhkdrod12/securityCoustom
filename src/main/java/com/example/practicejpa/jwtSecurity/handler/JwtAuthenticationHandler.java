package com.example.practicejpa.jwtSecurity.handler;

import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthenticationHandler extends JwtSecurityHandler {
	
	@Override
	public void onSuccess(HttpServletRequest request, HttpServletResponse response, Object jwtResult) {
		System.out.println("핸들러 성공");
	}
	
	@Override
	public void onFailure(HttpServletRequest request, HttpServletResponse response, JwtSecurityException authException) {
		System.out.println("핸들러 실패");
	}
}
