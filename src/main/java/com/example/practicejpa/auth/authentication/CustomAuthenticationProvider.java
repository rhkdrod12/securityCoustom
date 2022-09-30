package com.example.practicejpa.auth.authentication;

import com.example.practicejpa.auth.MemberDto;
import com.example.practicejpa.auth.UserLoginService;
import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.utils.Jwt.JWTResult;
import com.example.practicejpa.utils.Jwt.JwtProvider;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    @Autowired
    UserLoginService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtProvider jwtProvider;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("provider 진입");
        String userName = authentication.getName();
        String userPassword = (String) authentication.getCredentials();
    
        MemberDto user = userDetailsService.findUserByUserId(userName);
        
        if(user == null){
            throw new GlobalException(SystemMessage.MISMATCH_USEID);
        }else if (!passwordEncoder.matches(userPassword, user.getUserPw())) {
            throw new GlobalException(SystemMessage.MISMATCH_PASSWORD);
        }
        
        // 비밀정보 정보 제거
        user.setUserPw("");
        // 토큰 생성
        JWTResult token = jwtProvider.createToken(user);
        // 일치하면 권한정보를 포함하는 유저정보 토큰을 던짐, 토큰이 반환되면 로그인에 성공한 것으로 간주하여 securityContextHodler에 저장함
        return new UsernamePasswordAuthenticationToken(user, token, user.getAuths());
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
