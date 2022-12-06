package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.exception.JwtResponseManager;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends JwtFilter {
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, JwtResponseManager jwtResponseManager) throws ServletException, IOException {
		
	}
}
