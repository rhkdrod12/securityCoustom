package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.dao.repository.UserRepository;
import com.example.practicejpa.exception.JwtResponseManager;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityMessage;
import com.example.practicejpa.jwtSecurity.filter.JwtFilter;
import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtLoginFilter extends JwtFilter {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, JwtResponseManager jwtResponseManager) {
		System.out.println("로그인 필터 여기 진입!");
		
		String username = request.getParameter("username");
		String password = request.getParameter("passward");
		// 유저 ID 존재 여부 확인
		if(ObjectUtils.isEmpty(username)){
			onFailure(new JwtSecurityException(JwtSecurityMessage.NOT_EXIST_USER_ID));
		}
		// 유저 PW 존재 여부 확인
		else if(ObjectUtils.isEmpty(password)){
			onFailure(new JwtSecurityException(JwtSecurityMessage.NOT_EXIST_USER_PW));
		}
		
		
		
		
	}
}
