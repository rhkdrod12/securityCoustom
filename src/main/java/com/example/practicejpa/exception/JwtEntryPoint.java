package com.example.practicejpa.exception;

import com.example.practicejpa.config.WebConfig;
import com.example.practicejpa.jwtSecurity.exception.JwtSecurityException;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.responseEntity.ResultMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtEntryPoint {
	@Autowired
	private ObjectMapper objectMapper;
	
	public void sendJson(HttpServletResponse response, ResponseEntity<?> responseEntity) throws IOException, ServletException {
		
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Origin");
		
		response.setStatus(responseEntity.getStatusCodeValue());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
	}
}
