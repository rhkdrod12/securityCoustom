package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.exception.JwtResponseManager;
import com.example.practicejpa.jwtSecurity.JwtPublishProvider;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.jwtSecurity.jwtEnum.JwtState;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.ParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends JwtFilter {
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, JwtResponseManager jwtResponseManager) throws ServletException, IOException {
		
	}
}
