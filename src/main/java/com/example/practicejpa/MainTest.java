package com.example.practicejpa;

import com.example.practicejpa.auth.MemberDto;
import com.example.practicejpa.utils.Jwt.JwtState;
import com.example.practicejpa.utils.Jwt.JwtProvider;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MainTest {
	
	public static void main(String[] args) {
		
		SystemMessage sa0001 = SystemMessage.toEnum("SA0001");
		System.out.println(sa0001);
		
		PathMatcher pathMatcher = new AntPathMatcher();
		
		boolean b = pathMatcher.matchStart("/start/**", "/start/eee/qqq");
		System.out.println("b = " + b);
		
		//
		// JwtProvider jwtProvider = new JwtProvider();
		//
		// MemberDto memberDto = new MemberDto();
		// memberDto.setUserId("1");
		// memberDto.setName("홍길동");
		//
		// Set<GrantedAuthority> auths = new HashSet<>();
		//
		// auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		// auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		// memberDto.setAuths(auths);
		//
		// String uuid = UUID.randomUUID().toString();
		//
		// String accessToken = jwtProvider.createAccessToken(uuid, memberDto);
		// String refreshToken = jwtProvider.createRefreshToken(uuid);
		//
		// System.out.println("accessToken = " + accessToken);
		// System.out.println("refreshToken = " + refreshToken);
		//
		// JwtState state = jwtProvider.validRefreshToken(refreshToken, uuid);
		//
		// System.out.println(state);
		//
		// System.out.println();
		//
	}
}
