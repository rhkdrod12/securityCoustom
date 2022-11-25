package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.jwtSecurity.authentication.JwtAuthFilter;
import com.example.practicejpa.jwtSecurity.JwtPublishProvider;
import com.example.practicejpa.jwtSecurity.jwtEnum.JwtState;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.ParamUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 관련 필터 
 */
@Slf4j
public class JwtAuthenticationFilterOrg extends JwtAuthFilter {
	
	public JwtAuthenticationFilterOrg(JwtPublishProvider jwtPublishProvider, JwtSecurityHandler jwtSecurityHandler) {
		super(jwtPublishProvider, jwtSecurityHandler);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String accessToken = request.getHeader(AUTHORIZATION_HEADER);
		
		if (ParamUtils.isNotEmpty(accessToken)) {
			JwtState state = jwtPublishProvider.validAccessToken(accessToken);
			if(state == JwtState.SUCCESS){
				log.info("인증 성공");
				//SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(accessToken));
			} else if (state == JwtState.EXPIRE) {
				log.info("만료된 인증정보");
				throw new GlobalException(SystemMessage.EXPIRE_AUTHORIZED);
			} else if (state == JwtState.ERROR) {
				log.info("잘못된 인증정보, 여러번 재시도시 막아야함 -> 인증정보를 조작했다라는 의미임");
				throw new GlobalException(SystemMessage.INVALID_AUTHORIZED);
			}
			// 다음 필터로 넘김
			filterChain.doFilter(request, response);
			
		}else{
			log.info("인증정보 없음");
			throw new JwtSecurityException(SystemMessage.UNAUTHORIZED);
		}
	}
	
}
