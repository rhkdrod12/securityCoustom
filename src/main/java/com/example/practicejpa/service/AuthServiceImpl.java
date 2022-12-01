package com.example.practicejpa.service;


import com.example.practicejpa.dao.UserDao;
import com.example.practicejpa.dao.repository.UserRepository;
import com.example.practicejpa.jwtSecurity.JWTResult;
import com.example.practicejpa.jwtSecurity.JwtPublishProvider;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityMessage;
import com.example.practicejpa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	UserDao userDao;
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtPublishProvider jwtPublishProvider;
	
	public JWTResult republishAccessToken(String accessToken, String refreshToken) {
		
		String refreshId = jwtPublishProvider.getID(accessToken,false);
		String userId = jwtPublishProvider.getUser(accessToken, false).getUserId();
		
		User user = userRepository.findByUserId(userId)
		                          .orElseThrow(() -> new JwtSecurityException(JwtSecurityMessage.NOT_FOUND_USER));
		
		// refreshId와 refreshToken 이 모두 같으면 재발급함
		if (refreshId.equals(user.getRefrehId()) && refreshToken.equals(user.getRefreshToken())) {
			String newAccessToken = jwtPublishProvider.createAccessToken(userId, user);
			return new JWTResult(newAccessToken);
		}else{
			throw new JwtSecurityException(JwtSecurityMessage.INVALID_AUTHORIZED);
		}
	}
	
	/**
	 * 로그인 성공시 토큰을 발행한다.
	 * @param userId
	 * @param userPw
	 * @return
	 */
	@Override
	public JWTResult userLogin(String userId, String userPw) {
		
		if(ObjectUtils.isEmpty(userId)){
			throw new JwtSecurityException(JwtSecurityMessage.NOT_EXIST_USER_ID);
		} else if(ObjectUtils.isEmpty(userPw)){
			throw new JwtSecurityException(JwtSecurityMessage.NOT_EXIST_USER_PW);
		}
		
		User user = userDao.findUserId(userId)
		                   .orElseThrow(() -> new JwtSecurityException(JwtSecurityMessage.MISMATCH_USER));
		
		if (userPw.equals(user.getUserPw())) {
			JWTResult token = jwtPublishProvider.createToken(user);
			user.setRefrehId(jwtPublishProvider.getID(token.getAccessToken()));
			user.setRefreshToken(token.getRefreshToken());
			return token;
		} else {
			throw new JwtSecurityException(JwtSecurityMessage.MISMATCH_USER);
		}
	}
}
