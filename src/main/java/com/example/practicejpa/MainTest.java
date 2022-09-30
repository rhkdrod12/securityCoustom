package com.example.practicejpa;

import com.example.practicejpa.auth.MemberDto;
import com.example.practicejpa.utils.Jwt.JwtState;
import com.example.practicejpa.utils.Jwt.JwtProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MainTest {
	
	public static void main(String[] args) {
		JwtProvider jwtProvider = new JwtProvider();
		
		MemberDto memberDto = new MemberDto();
		memberDto.setUserId("1");
		memberDto.setName("홍길동");
		
		Set<GrantedAuthority> auths = new HashSet<>();
		
		auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		memberDto.setAuths(auths);
		
		String uuid = UUID.randomUUID().toString();
		
		String accessToken = jwtProvider.createAccessToken(uuid, memberDto);
		String refreshToken = jwtProvider.createRefreshToken(uuid);
		
		System.out.println("accessToken = " + accessToken);
		System.out.println("refreshToken = " + refreshToken);
		
		JwtState state = jwtProvider.validRefreshToken(refreshToken, uuid);
		
		System.out.println(state);
		
		System.out.println();
		
	}
}
