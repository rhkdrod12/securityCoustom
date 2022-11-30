package com.example.practicejpa.jwtSecurity;

import com.example.practicejpa.exception.JwtResponseManager;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 스프링에 등록시킬 필터
 * 인증, 인가 절차는 내부 필터로 동작시킬 것임
 */
@Slf4j
public class JwtSecurityManagerFilter implements Filter {
	JwtResponseManager jwtResponseManager;
	JwtFilterChain jwtFilterChain;
	
	public JwtSecurityManagerFilter(JwtFilterChain jwtFilterChain, JwtResponseManager jwtResponseManager) {
		this.jwtFilterChain     = jwtFilterChain;
		this.jwtResponseManager = jwtResponseManager;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		// 혹시 남아있을 인증 정보 제거
		JwtContext.init();
		// 응답 가능으로 셋팅
		jwtResponseManager.init();
		// 응답 객체에 해당 요청 정보 셋팅
		jwtResponseManager.setHttpServlet((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
		
		try {
			//jwtReponseManager.sendData("정상 처리 되었습니다.");
			// 내부 로직 실행
			jwtFilterChain.doInit(servletRequest, servletResponse, filterChain);
			
		} catch (JwtSecurityException exception) {
			jwtResponseManager.sendError(exception);
		}
	}
	
	
}
