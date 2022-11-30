package com.example.practicejpa.service;

import com.example.practicejpa.jwtSecurity.JWTResult;


public interface AuthService {
	
	/**
	 * RefreshToken을 검증하여 AccesToken을 재발급한다.
	 * @param accessToken
	 * @param refreshToken
	 * @return
	 */
	JWTResult republishAccessToken(String accessToken, String refreshToken);
	
	
	/**
	 * 로그인 성공시 토큰을 발행
	 * @param userId
	 * @param userPw
	 * @return
	 */
	JWTResult userLogin(String userId, String userPw);
	
	
}
