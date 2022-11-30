package com.example.practicejpa.controller;

import com.example.practicejpa.dao.UserDao;
import com.example.practicejpa.jwtSecurity.JwtPublishProvider;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityMessage;
import com.example.practicejpa.jwtSecurity.jwtEnum.JwtState;
import com.example.practicejpa.service.AuthService;
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
	@Autowired
	AuthService userDao;
	
	/**
	 * refreshToken 과 accessToken을 이용하여 토큰 재발급이 가능한 상태이면
	 * 재발급함
	 * @param accessToken
	 * @param refreshToken
	 * @return
	 */
	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@RequestParam(name = "accessToken") String accessToken, @RequestParam(name = "refreshToken") String refreshToken) {
		if (ParamUtils.isEmpty(accessToken) || ParamUtils.isEmpty(refreshToken)) {
			throw new JwtSecurityException(SystemMessage.NOT_EXIST_PARAM);
		} else if (jwtPublishProvider.validToken(accessToken, refreshToken) == JwtState.SUCCESS) {
			return CommResponse.done(userDao.republishAccessToken(accessToken, refreshToken));
		} else{
			throw new JwtSecurityException(JwtSecurityMessage.INVALID_AUTHORIZED);
		}
	}
	
	/**
	 * 토큰을 검증하여 인증된 사용자인지 검증함
	 * @param request
	 * @return
	 */
	@PostMapping("/access")
	public ResponseEntity<?> accessValid(HttpServletRequest request) {
		String accessToken = request.getHeader(SystemCode.AUTHORIZATION_HEADER);
		if (ParamUtils.isNotEmpty(accessToken)) {
			JwtState jwtState = jwtPublishProvider.validAccessToken(accessToken);
			if (jwtState == JwtState.SUCCESS) {
				return CommResponse.done(true);
			} else if (jwtState == JwtState.EXPIRE) {
				return CommResponse.fail(JwtSecurityMessage.EXPIRE_AUTHORIZED);
			} else {
				return CommResponse.fail(SystemMessage.REQUEST_FAIL);
			}
		}
		return CommResponse.fail(JwtSecurityMessage.NOT_EXIST_AUTH);
	}
}
