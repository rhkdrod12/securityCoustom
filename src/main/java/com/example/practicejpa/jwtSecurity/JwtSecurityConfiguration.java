package com.example.practicejpa.jwtSecurity;


import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

public class JwtSecurityConfiguration {
	
	// JWT 인증, 인가시 사용할 필터
	List<Filter> jwtFilter = new ArrayList<>();
	
	// 인증 후 사용할 Handler
	JwtSecurityHandler loginHandler;
	JwtSecurityHandler logoutHandler;
	JwtSecurityHandler authenticationHandler;
	JwtSecurityHandler authorizationhandler;
	
	public JwtSecurityConfiguration() {
	
	}
}
