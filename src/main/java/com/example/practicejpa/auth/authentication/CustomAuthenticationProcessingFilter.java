package com.example.practicejpa.auth.authentication;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.utils.Jwt.JwtProvider;
import com.example.practicejpa.utils.Jwt.JwtState;
import com.example.practicejpa.utils.codeMessage.SystemCode;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    
    @Autowired
    private JwtProvider jwtProvider;
    /*
     * 여기서 해당 URL이 들어오게되면 필터링으로 잡힐 것이고
     * 로그인 관련 필터이므로
     * 먼저 UsernamePasswordAuthenticationToken가 생성되어야하므로
     * username, password 기반으로 UsernamePasswordAuthenticationToken 을 생성하여 나머지 인증로직을 태워
     * 완전한 Authentication을 생성시켜 securityContext에 등록시킬 것임
     */
    
    public CustomAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    
        // String accessToken = request.getHeader(SystemCode.AUTHORIZATION_HEADER);
        // if (ParamUtils.isNotEmpty(accessToken)) {
        //     JwtState state = jwtProvider.validAccessToken(accessToken);
        //     // 토큰이 이미 있는 요청이면 어 음.. 재로그인 하는 걸 수도..?
        //     if(state == JwtState.SUCCESS){
        //         return getAuthenticationManager().authenticate(jwtProvider.getAuthentication(accessToken));
        //     }  else if (state == JwtState.ERROR) {
        //         throw new GlobalException(SystemMessage.UNNOMAL_REQUEST);
        //     }
        // }
    
        String username = request.getParameter("username");
        String password = request.getParameter("passward");
        
        if(ObjectUtils.isEmpty(username)){
            throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
        }
        if(ObjectUtils.isEmpty(password)){
            throw new AuthenticationCredentialsNotFoundException("비밀번호가 존재하지 않습니다.");
        }
        
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    
    
    
    
    
}

