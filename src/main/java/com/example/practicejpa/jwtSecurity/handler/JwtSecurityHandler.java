package com.example.practicejpa.jwtSecurity.handler;

import com.example.practicejpa.exception.JwtResponseManager;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class JwtSecurityHandler {
	
	protected JwtResponseManager jwtResponseManager;
	
	public void setJwtReponseManager(JwtResponseManager jwtResponseManager) {
		this.jwtResponseManager = jwtResponseManager;
	}
	
	/**
	 * 인증 성공시 핸들러
	 * @param request
	 * @param response
	 * @param jwtResult
	 */
	public void onSuccess(HttpServletRequest request, HttpServletResponse response, Object result){
		jwtResponseManager.sendData(result);
	};
	
	/**
	 * 인증 실패시 핸들러
	 * @param request
	 * @param response
	 * @param authException
	 */
	public void onFailure(HttpServletRequest request, HttpServletResponse response, JwtSecurityException authException){
		jwtResponseManager.sendError(authException);
	};
	
}
