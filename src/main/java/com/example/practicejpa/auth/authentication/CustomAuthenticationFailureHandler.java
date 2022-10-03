package com.example.practicejpa.auth.authentication;

import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.codeMessage.messageInterface.MessageCode;
import com.example.practicejpa.utils.responseEntity.ResultMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("여기 CustomAuthenticationFailureHandler 진입");
        SystemMessage message = Optional.ofNullable(SystemMessage.valueOf(exception.getMessage())).orElse(SystemMessage.UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(ResultMessage.fail(message)));
    }
}
