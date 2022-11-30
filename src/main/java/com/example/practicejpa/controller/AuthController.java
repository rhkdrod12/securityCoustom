package com.example.practicejpa.controller;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.jwtSecurity.JwtPublishProvider;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityMessage;
import com.example.practicejpa.jwtSecurity.jwtEnum.JwtState;
import com.example.practicejpa.utils.codeMessage.SystemCode;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.ParamUtils;
import com.example.practicejpa.utils.responseEntity.CommResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	JwtPublishProvider jwtPublishProvider;
	
	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@RequestParam(name = "accessToken") String accessToken, @RequestParam(name = "refreshToken") String refreshToken) {
		
		if (ParamUtils.isEmpty(accessToken) || ParamUtils.isEmpty(refreshToken)) {
			throw new JwtSecurityException(SystemMessage.NOT_EXIST_PARAM);
		} else if (jwtPublishProvider.validToken(accessToken, refreshToken) == JwtState.SUCCESS) {
		
		}
		
		return CommResponse.done();
	}
	
	@PostMapping("/access")
	public ResponseEntity<?> accessValid(HttpServletRequest request) {
		String accessToken = request.getHeader(SystemCode.AUTHORIZATION_HEADER);
		if (ParamUtils.isNotEmpty(accessToken)) {
			//return CommResponse.done(jwtPublishProvider.validAccessToken(accessToken) == JwtState.SUCCESS);
			JwtState jwtState = jwtPublishProvider.validAccessToken(accessToken);
			if (jwtState == JwtState.SUCCESS) {
				return CommResponse.done(true);
			} else if (jwtState == JwtState.EXPIRE) {
				throw new JwtSecurityException(JwtSecurityMessage.EXPIRE_AUTHORIZED);
			} else {
				throw new JwtSecurityException(SystemMessage.REQUEST_FAIL);
			}
		}
		throw new JwtSecurityException(JwtSecurityMessage.NOT_EXIST_AUTH);
	}
	
}
