package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.exception.JwtResponseManager;
import com.example.practicejpa.jwtSecurity.JwtContext;
import com.example.practicejpa.jwtSecurity.JwtPublishProvider;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;
import com.example.practicejpa.jwtSecurity.jwtEnum.JwtState;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.ParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends JwtFilter {
	
	protected static final String AUTHORIZATION_HEADER = "Authorization";
	protected static final String OPTION_METHOD = "OPTIONS";
	protected static final String ALLOW_ORIGIN = "";
	
	@Autowired
	JwtPublishProvider jwtPublishProvider;
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, JwtResponseManager jwtResponseManager) throws ServletException, IOException {
		
		String mehtod = request.getMethod();
		String accessToken = request.getHeader(AUTHORIZATION_HEADER);
		
		// 프리플라이트인 경우 허용처리
		if (OPTION_METHOD.equals(mehtod)) {
			onSuccess(true);
		}
		else if (ParamUtils.isNotEmpty(accessToken)) {
			JwtState state = jwtPublishProvider.validAccessToken(accessToken);
			if(state == JwtState.SUCCESS){
				log.info("인증 성공");
				JwtContext.setContext(jwtPublishProvider.getUser(accessToken));
			} else if (state == JwtState.EXPIRE) {
				log.info("만료된 인증정보");
				throw new JwtSecurityException(SystemMessage.EXPIRE_AUTHORIZED);
			} else if (state == JwtState.ERROR) {
				log.info("잘못된 인증정보, 여러번 재시도시 막아야함 -> 인증정보를 조작했다라는 의미임");
				throw new JwtSecurityException(SystemMessage.INVALID_AUTHORIZED);
			}
			// 다음 필터로 넘김
			filterChain.doFilter(request, response);
			
		}else{
			log.info("인증정보 없음");
			throw new JwtSecurityException(SystemMessage.UNAUTHORIZED);
		}
	}
}
