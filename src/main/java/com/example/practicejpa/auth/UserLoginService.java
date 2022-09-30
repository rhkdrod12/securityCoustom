package com.example.practicejpa.auth;

import com.example.practicejpa.exception.GlobalException;
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
	
	// 로그아웃!
	public void logout(String id, String refreshToken){
	}
	
}
