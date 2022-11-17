package com.example.practicejpa.jwtSecurity;


import com.example.practicejpa.exception.JwtEntryPoint;
import com.example.practicejpa.jwtSecurity.filter.JwtAuthenticationFilter;
import com.example.practicejpa.jwtSecurity.filter.JwtLoginFilter;
import com.example.practicejpa.jwtSecurity.authentication.JwtSecurityFilterProvider;
import com.example.practicejpa.jwtSecurity.filter.JwtLogoutFilter;
import com.example.practicejpa.jwtSecurity.handler.JwtAuthorizationHandler;
import com.example.practicejpa.jwtSecurity.handler.JwtLoginHandler;
import com.example.practicejpa.jwtSecurity.handler.JwtLogoutHandler;
import com.example.practicejpa.utils.BeanUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class JwtSecurityConfig {
	
	/**
	 * 필요한거
	 * login, logout, 경로별 인증필터, 경로별 인가필터
	 * 실패, 성공시의 handler
	 *
	 * 근데 나는 어차피 별로로 만드는 건데 굳이 설정파일 만들 필요가 있을까..?
	 *
	 *
	 */
	public JwtSecurityConfiguration configure(){
		
		// 로그인 필터 등록
		JwtSecurityFilterProvider loginProvider = JwtSecurityFilterProvider.Filter(new JwtLoginFilter())
		                                                                    .setFilterOrder(1)
		                                                                    .setFilterPaths("/user/login")
		                                                                    .setHandler(new JwtLoginHandler());
		// 로그아웃 필터 등록
		JwtSecurityFilterProvider logoutProvider = JwtSecurityFilterProvider.Filter(new JwtLogoutFilter())
		                                                                   .setFilterOrder(2)
		                                                                   .setFilterPaths("/user/logout")
		                                                                   .setHandler(new JwtLogoutHandler());
		// 인증 필터 등록
		JwtSecurityFilterProvider authProvider = JwtSecurityFilterProvider.Filter(new JwtAuthenticationFilter())
		                                                                    .setFilterOrder(3)
		                                                                    .setPermitAll()
		                                                                    .setHandler(new JwtAuthorizationHandler());
		// 인가 필터 등록
		//
		//
		//RequestMappingHandlerMapping requestMappingHandlerMapping = BeanUtils.getBean(RequestMappingHandlerMapping.class);
		
		
		
		
		JwtSecurityConfiguration jwtSecurityConfiguration = new JwtSecurityConfiguration();
		jwtSecurityConfiguration.addJwtFilter(loginProvider)
		                        .addJwtFilter(logoutProvider)
		                        .addJwtFilter(authProvider);
		
		
		return jwtSecurityConfiguration;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> filterRegistrationBean(JwtEntryPoint jwtEntryPoint){
		
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>();
		
		// 내부에서 사용될 필터 등록
		JwtFilterChain jwtFilterChain = new JwtFilterChain(this.configure().getJwtFilters());
		// 스프링부트에 등록시킬 내부 필터들을 동작시킬 필터 생성
		JwtSecurityManagerFilter jwtSecurityManagerFilter = new JwtSecurityManagerFilter(jwtFilterChain, jwtEntryPoint);
		
		registrationBean.setFilter(jwtSecurityManagerFilter);
		// registrationBean.setOrder(1);
		registrationBean.addUrlPatterns("/*");
		
		return registrationBean;
	}
	
}


