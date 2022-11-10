package com.example.practicejpa.jwtSecurity.authentication;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.jwtSecurity.JwtProvider;
import com.example.practicejpa.jwtSecurity.JwtState;
import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.ParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
	
	protected static final String AUTHORIZATION_HEADER = "Authorization";
	protected static final String LOGIN_PATH = "/user/login";
	protected static final String LOGOUT_PATH = "/user/logout";
	
	protected final JwtProvider jwtProvider;
	protected final JwtSecurityHandler jwtSecurityHandler;
	
	public JwtAuthFilter(JwtProvider jwtProvider, JwtSecurityHandler jwtSecurityHandler) {
		this.jwtProvider = jwtProvider;
		this.jwtSecurityHandler = jwtSecurityHandler;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		filterChain.doFilter(request, response);
	}
}
