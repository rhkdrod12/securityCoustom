package com.example.practicejpa.jwtSecurity.handler;

import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtLoginHandler extends JwtSecurityHandler{
	
	@Override
	public void onSuccess(HttpServletRequest request, HttpServletResponse response, Object jwtResult) {
		System.out.println("로그인 핸들러~");
		jwtResponseManager.sendData(jwtResult);
	}
	
	@Override
	public void onFailure(HttpServletRequest request, HttpServletResponse response, JwtSecurityException authException) {
		System.out.println("로그인 핸들러~ 실패");
		jwtResponseManager.sendError(authException);
	}
}
