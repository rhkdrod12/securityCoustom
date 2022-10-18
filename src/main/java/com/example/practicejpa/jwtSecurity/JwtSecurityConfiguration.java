package com.example.practicejpa.jwtSecurity;


import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JwtSecurityConfiguration {
	
	// JWT 인증, 인가시 사용할 필터
	List<Filter> jwtFilterList = new ArrayList<>();
	
	// 인증 후 사용할 Handler
	JwtSecurityHandler loginHandler;
	JwtSecurityHandler logoutHandler;
	JwtSecurityHandler authenticationHandler;
	JwtSecurityHandler authorizationhandler;
	
	public JwtSecurityConfiguration() {
	
	
	
	}
	
	public JwtSecurityConfiguration setLoginHandler(JwtSecurityHandler loginHandler) {
		this.loginHandler = loginHandler;
		return this;
	}
	
	public JwtSecurityConfiguration setLogoutHandler(JwtSecurityHandler logoutHandler) {
		this.logoutHandler = logoutHandler;
		return this;
	}
	
	public JwtSecurityConfiguration setAuthenticationHandler(JwtSecurityHandler authenticationHandler) {
		this.authenticationHandler = authenticationHandler;
		return this;
	}
	
	public JwtSecurityConfiguration setAuthorizationhandler(JwtSecurityHandler authorizationhandler) {
		this.authorizationhandler = authorizationhandler;
		return this;
	}
}
