package com.example.practicejpa.auth.authentication;

import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.responseEntity.ResultMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("여기 CustomAuthenticationEntryPoint 진입");
        ResponseEntity<String> test = ResponseEntity.ok("Test");
        response.setCharacterEncoding("UTF-8");
        // response.setHeader("Access-Control-Allow-Origin", "http://192.168.10.79:3000");
        // response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(ResultMessage.fail(SystemMessage.UNAUTHORIZED)));
    }
}
