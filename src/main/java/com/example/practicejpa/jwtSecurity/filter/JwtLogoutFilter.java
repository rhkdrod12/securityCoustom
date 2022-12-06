package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.exception.JwtResponseManager;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtLogoutFilter extends JwtFilter {
	
	@Override
	public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain, JwtResponseManager jwtResponseManager) {
		System.out.println("로그아웃 필터 여기 진입!");
		
	}
}
