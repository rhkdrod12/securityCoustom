package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.dao.UserDao;
import com.example.practicejpa.dao.repository.UserRepository;
import com.example.practicejpa.exception.JwtResponseManager;
import com.example.practicejpa.jwtSecurity.JWTResult;
import com.example.practicejpa.jwtSecurity.JwtPublishProvider;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityMessage;
import com.example.practicejpa.model.User;
import com.example.practicejpa.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtLoginFilter extends JwtFilter {

	@Autowired
	AuthService authService;
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, JwtResponseManager jwtResponseManager) {
		System.out.println("로그인 필터 여기 진입!");
		
		String username = request.getParameter("username");
		String password = request.getParameter("passward");
		
		// 로그인
		onSuccess(authService.userLogin(username, password));
		
	}
}
