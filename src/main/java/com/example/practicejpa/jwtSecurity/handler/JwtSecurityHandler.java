package com.example.practicejpa.jwtSecurity.handler;

import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.JWTResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface JwtSecurityHandler {
	/**
	 * 인증 성공시 핸들러
	 * @param request
	 * @param response
	 * @param jwtResult
	 */
	void onSuccess(HttpServletRequest request, HttpServletResponse response, JWTResult jwtResult);
	
	/**
	 * 인증 실패시 핸들러
	 * @param request
	 * @param response
	 * @param authException
	 */
	void onFailure(HttpServletRequest request, HttpServletResponse response, JwtSecurityException authException);
	
}
