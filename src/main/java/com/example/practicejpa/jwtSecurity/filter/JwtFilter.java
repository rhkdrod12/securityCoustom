package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.exception.JwtResponseManager;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import org.springframework.http.ResponseEntity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class JwtFilter {
	
	private JwtResponseManager jwtResponseManager;
	
	public JwtFilter() {
	}
	
	public void setJwtResponseManager(JwtResponseManager jwtResponseManager) {
		this.jwtResponseManager = jwtResponseManager;
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
		this.doFilter((HttpServletRequest) servletRequest, (HttpServletResponse)servletResponse, filterChain, null);
	}
	
	/**
	 * 오버리이딩 금지 처리
	 */
	public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain, JwtResponseManager jwtResponseManager) throws ServletException, IOException {
		this.doFilter((HttpServletRequest) servletRequest, (HttpServletResponse)servletResponse, filterChain, jwtResponseManager);
	}
	
	abstract public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, JwtResponseManager jwtResponseManager) throws ServletException, IOException;
	
	protected void onSuccess(Object object){
		jwtResponseManager.sendData(object);
	}
	
	protected void onFailure(JwtSecurityException exception) {
		jwtResponseManager.sendError(exception);
	}
	
	protected void onResult(ResponseEntity<?> responseEntity){
		jwtResponseManager.sendResponse(responseEntity);
	}
	
}
