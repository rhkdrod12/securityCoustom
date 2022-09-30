package com.example.practicejpa.auth.authentication;

import com.example.practicejpa.auth.UserLoginService;
import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.utils.Jwt.JwtProvider;
import com.example.practicejpa.utils.Jwt.JwtState;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutHandler implements LogoutHandler {
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UserLoginService userLoginService;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		String accessToken = request.getHeader("AccessToken");
		String refreshToken = request.getHeader("RefreshToken");
		
		if (accessToken == null || refreshToken == null) {
			throw new GlobalException();
		}
		
		if( jwtProvider.validToken(accessToken, refreshToken) != JwtState.ERROR){
			String id = jwtProvider.getID(accessToken);
			userLoginService.logout(id, refreshToken);
		}
	}
}
