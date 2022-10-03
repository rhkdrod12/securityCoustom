package com.example.practicejpa.auth.authentication;

import com.example.practicejpa.auth.MemberDto;
import com.example.practicejpa.auth.UserLoginService;
import com.example.practicejpa.dao.repository.AuthTokenRepository;
import com.example.practicejpa.dao.repository.UserRepository;
import com.example.practicejpa.exception.AuthException;
import com.example.practicejpa.model.Auth;
import com.example.practicejpa.model.AuthToken;
import com.example.practicejpa.model.User;
import com.example.practicejpa.service.UserService;
import com.example.practicejpa.utils.Jwt.JWTResult;
import com.example.practicejpa.utils.Jwt.JwtProvider;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	UserLoginService userDetailsService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		System.out.println("provider 진입");
		String userName = authentication.getName();
		String userPassword = (String) authentication.getCredentials();
		
		// 유저 찾기
		User user = userRepository.findByUserId(userName);
		
		if (user == null) {
			throw new AuthException(SystemMessage.MISMATCH_USEID.name());
		} else if (!passwordEncoder.matches(userPassword, user.getUserPw())) {
			throw new AuthException(SystemMessage.MISMATCH_PASSWORD.name());
		}
		
		// DTO로 변환
		MemberDto member = CopyUtils.CopyObject(MemberDto.class, user);
		
		Optional.ofNullable(user.getAuths())
		        .ifPresent(auths ->member.setAuths(auths.stream().map(Auth::getGrantedAuthority).collect(Collectors.toSet())));
		
		// 토큰에 지정할 ID 생성
		String uuid = UUID.randomUUID().toString();
		// 토큰 생성
		JWTResult token = jwtProvider.createToken(member, uuid);
		// Index용 refreshId 셋
		user.setRefrehId(uuid);
		// RefreshToken 셋
		user.setRefreshToken(token.getRefreshToken());
		// 리플래쉬 토큰 저장
		userRepository.saveAndFlush(user);
		
		// 비밀정보 정보 제거
		member.setUserPw("");
		// 토큰 생성
		member.setToken(token);
		// 일치하면 권한정보를 포함하는 유저정보 토큰을 던짐, 토큰이 반환되면 로그인에 성공한 것으로 간주하여 securityContextHodler에 저장함
		// securityContextHodler에
		return new UsernamePasswordAuthenticationToken(member, token, member.getAuths());
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
