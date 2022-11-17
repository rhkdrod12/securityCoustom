package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityMessage;
import com.example.practicejpa.jwtSecurity.filter.JwtFilter;
import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtLoginFilter extends JwtFilter {
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, JwtSecurityHandler jwtSecurityHandler) {
		System.out.println("여기 진입!");
		
		String username = request.getParameter("username");
		String password = request.getParameter("passward");
		
		if(ObjectUtils.isEmpty(username)){
			throw new JwtSecurityException(JwtSecurityMessage.NOT_EXIST_USER_ID);
		}
		if(ObjectUtils.isEmpty(password)){
			throw new JwtSecurityException(JwtSecurityMessage.NOT_EXIST_USER_PW);
		}
		
		jwtSecurityHandler.onSuccess(request, response, null);
		
		jwtSecurityHandler.onFailure(request, response,  new JwtSecurityException("테스트"));
	}
}
