package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class JwtFilter implements Filter {
	
	private JwtSecurityHandler JWT_SECURITY_HANDLER = null;
	
	public JwtFilter() {
	}
	
	public JwtFilter(JwtSecurityHandler JWT_SECURITY_HANDLER) {
		this.JWT_SECURITY_HANDLER = JWT_SECURITY_HANDLER;
	}

	public void setJwtSecurityHandler(JwtSecurityHandler jwtSecurityHandler){
		this.JWT_SECURITY_HANDLER = jwtSecurityHandler;
	}
	
	public JwtSecurityHandler getJwtSecurityHandler(){
		return this.JWT_SECURITY_HANDLER;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
		this.doFilter((HttpServletRequest) servletRequest, (HttpServletResponse)servletResponse, filterChain, null);
	}
	
	/**
	 * 오버리이딩 금지 처리
	 */
	public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain, JwtSecurityHandler jwtSecurityHandler){
		this.doFilter((HttpServletRequest) servletRequest, (HttpServletResponse)servletResponse, filterChain, jwtSecurityHandler);
	}
	
	abstract public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, JwtSecurityHandler jwtSecurityHandler);
	
}
