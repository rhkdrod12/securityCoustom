package com.example.practicejpa.jwtSecurity;

import com.example.practicejpa.config.WebConfig;
import com.example.practicejpa.exception.JwtEntryPoint;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.utils.responseEntity.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 스프링에 등록시킬 필터
 * 인증, 인가 절차는 내부 필터로 동작시킬 것임
 */
@Slf4j
public class JwtSecurityManagerFilter implements Filter {
	JwtEntryPoint jwtEntryPoint;
	JwtFilterChain jwtFilterChain;
	
	public JwtSecurityManagerFilter(JwtFilterChain jwtFilterChain, JwtEntryPoint jwtEntryPoint) {
		this.jwtFilterChain = jwtFilterChain;
		this.jwtEntryPoint = jwtEntryPoint;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		
		try {
			// 내부 로직 실행
			jwtFilterChain.doInit(servletRequest, servletResponse);
			// 다음 필터로 이동
			filterChain.doFilter(servletRequest, servletResponse);
			
		} catch (JwtSecurityException exception) {
			jwtEntryPoint.sendJson((HttpServletResponse)servletResponse, new ResponseEntity<>(ResultMessage.result(false, exception.getCode(), exception.getMessage()), exception.getHttpStatus()));
		}
	}
	
}
