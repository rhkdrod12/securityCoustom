package com.example.practicejpa.auth;

import com.example.practicejpa.dao.repository.UserRepository;
import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.model.User;
import com.example.practicejpa.utils.Jwt.JwtProvider;
import com.example.practicejpa.utils.Jwt.JwtState;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService implements UserDetailsService {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserLoginDao userLoginDao;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	UserRepository userRepository;
	
	public MemberDto findUserByUserId(String userId) {
		return Optional.ofNullable(userLoginDao.findUserByName(userId)).orElseThrow(() -> new GlobalException(SystemMessage.NOT_EXIST_USER));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberDto mem = Optional.ofNullable(userLoginDao.findUserByName(username)).orElseThrow(() -> new GlobalException(SystemMessage.NOT_EXIST_USER));
		
		return AuthUser.builder()
		               .username(mem.getUserId())
		               .password(mem.getUserPw())
		               .authorities(mem.getAuths())
		               .build();
	}
	
	// refreshToken 검증 및 발급
	public String rePublishToken(String accessToken, String refreshToken) {
		// 토큰 검증 방식
		// 1. 토큰 검증
		// 2. DB 존재 여부 확인
		// 3. 모두 통과시 재발급, 아니면 발급 하지 않음
		
		JwtState state = jwtProvider.validToken(accessToken, refreshToken);
		// 검증 성공. DB에서 해당 값 조회
		if(state == JwtState.SUCCESS){
			String refreshId = jwtProvider.getID(refreshToken);
			User user = userRepository.findByRefrehId(refreshId);
			
			if (user == null) {
				throw new GlobalException(SystemMessage.INVALID_AUTHORIZED);
			}else{
				return jwtProvider.createAccessToken(refreshId, user);
			}
		}
		// 만료된 토큰
		else if (state == JwtState.EXPIRE) {
			throw new GlobalException(SystemMessage.EXPIRE_AUTHORIZED);
		} 
		// 에러
		else{
			throw new GlobalException(SystemMessage.INVALID_AUTHORIZED);
		}
	}
	
	// 로그아웃!
	public void logout(String id, String refreshToken) {
	}
	
}
