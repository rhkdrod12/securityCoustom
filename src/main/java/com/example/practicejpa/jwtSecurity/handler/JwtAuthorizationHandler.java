package com.example.practicejpa.jwtSecurity.handler;

import com.example.practicejpa.jwtSecurity.JWTResult;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthorizationHandler extends JwtSecurityHandler {
	
	@Override
	public void onSuccess(HttpServletRequest request, HttpServletResponse response, Object jwtResult) {
		System.out.println("여기 인증 핸들러 성공 진입");
	}
	
	@Override
	public void onFailure(HttpServletRequest request, HttpServletResponse response, JwtSecurityException authException) {
		System.out.println("여기 인증 핸들러 실패 진입");
	}
}
