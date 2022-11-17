package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtLogoutFilter extends JwtFilter {
	
	@Override
	public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain, JwtSecurityHandler jwtSecurityHandler) {
		System.out.println("여기 진입!");
		
		jwtSecurityHandler.onSuccess(servletRequest, servletResponse, null);
		
		jwtSecurityHandler.onFailure(servletRequest, servletResponse, new JwtSecurityException("테스트"));
	}
}
