package com.example.practicejpa.jwtSecurity;


import com.example.practicejpa.jwtSecurity.handler.JwtAuthenticationHandler;
import com.example.practicejpa.jwtSecurity.handler.JwtAuthorizationHandler;
import com.example.practicejpa.jwtSecurity.handler.JwtLoginHandler;
import com.example.practicejpa.jwtSecurity.handler.JwtLogoutHandler;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.List;

@Configuration
public class JwtSecurityConfig {
	
	private JwtSecurityConfiguration jwtSecurityConfiguration;
	
	/**
	 * 필요한거
	 * login, logout, 경로별 인증필터, 경로별 인가필터
	 * 실패, 성공시의 handler
	 *
	 * 근데 나는 어차피 별로로 만드는 건데 굳이 설정파일 만들 필요가 있을까..?
	 *
	 *
	 */
	public void configure(){
		jwtSecurityConfiguration = new JwtSecurityConfiguration();
	}
	
	@Bean
	public FilterRegistrationBean<Filter> filterRegistrationBean(){
		
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>();
		
		registrationBean.setFilter(null);
		registrationBean.addUrlPatterns("/*");
		
		return registrationBean;
	}
	
}


