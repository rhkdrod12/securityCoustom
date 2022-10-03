package com.example.practicejpa.auth.authentication;

import com.example.practicejpa.auth.MemberDto;
import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.utils.Jwt.JwtProvider;
import com.example.practicejpa.utils.Jwt.JwtState;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.ParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
	
	private final JwtProvider jwtProvider;
	
	public JwtAuthFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String LOGIN_PATH = "/user/login";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String accessToken = request.getHeader(AUTHORIZATION_HEADER);
		
		if (!LOGIN_PATH.equals(request.getRequestURI()) && ParamUtils.isNotEmpty(accessToken)) {
			JwtState state = jwtProvider.validAccessToken(accessToken);
			if(state == JwtState.SUCCESS){
				log.info("인증 성공");
				SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(accessToken));
			} else if (state == JwtState.EXPIRE) {
				log.info("만료된 인증정보");
				throw new GlobalException(SystemMessage.EXPIRE_AUTHORIZED);
			} else if (state == JwtState.ERROR) {
				log.info("잘못된 인증정보, 여러번 재시도시 막아야함 -> 인증정보를 조작했다라는 의미임");
				throw new GlobalException(SystemMessage.INVALID_AUTHORIZED);
			}
		}
		
		// 다음 필터로 넘김
		filterChain.doFilter(request, response);
	}
	
}
