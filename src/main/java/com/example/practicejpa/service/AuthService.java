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
	 * Token을 발행
	 * @param user
	 * @return
	 */
	JWTResult userLogin(String userId, String userPw);
	
	
}
