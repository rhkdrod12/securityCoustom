package com.example.practicejpa.jwtSecurity.authentication;

import com.example.practicejpa.jwtSecurity.JwtPublishProvider;
import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;
import lombok.extern.slf4j.Slf4j;
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
	
	protected final JwtPublishProvider jwtPublishProvider;
	protected final JwtSecurityHandler jwtSecurityHandler;
	
	public JwtAuthFilter(JwtPublishProvider jwtPublishProvider, JwtSecurityHandler jwtSecurityHandler) {
		this.jwtPublishProvider = jwtPublishProvider;
		this.jwtSecurityHandler = jwtSecurityHandler;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		filterChain.doFilter(request, response);
	}
}
