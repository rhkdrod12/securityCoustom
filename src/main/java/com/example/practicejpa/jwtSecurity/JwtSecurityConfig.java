package com.example.practicejpa.jwtSecurity;


import com.example.practicejpa.exception.JwtResponseManager;
import com.example.practicejpa.jwtSecurity.filter.JwtAuthenticationFilter;
import com.example.practicejpa.jwtSecurity.filter.JwtLoginFilter;
import com.example.practicejpa.jwtSecurity.authentication.JwtSecurityFilterProvider;
import com.example.practicejpa.jwtSecurity.filter.JwtLogoutFilter;
import com.example.practicejpa.jwtSecurity.handler.JwtAuthorizationHandler;
import com.example.practicejpa.jwtSecurity.handler.JwtLoginHandler;
import com.example.practicejpa.jwtSecurity.handler.JwtLogoutHandler;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	
	@Bean
	public JwtLoginFilter jwtLoginFilter(){
		return new JwtLoginFilter();
	}
	@Bean
	public JwtLogoutFilter jwtLogoutFilter() {
		return new JwtLogoutFilter();
	}
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	/**
	 * 나중에 수정 필요, 이런 형태로 진행하는 건 좀 아닌 것 같음
	 * config형태로 보이지 않음
	 */
	@Bean
	public JwtSecurityConfiguration configure(JwtLoginFilter jwtLoginFilter,
	                                          JwtLogoutFilter jwtLogoutFilter,
	                                          JwtAuthenticationFilter jwtAuthenticationFilter){
		// 자동주입을 사용하려면 bean으로 등록을 해야하는데 이 방식으로는 어렵단 말이지..
		// 로그인 필터 등록
		JwtSecurityFilterProvider loginProvider = JwtSecurityFilterProvider.Filter(jwtLoginFilter)
		                                                                   .setFilterOrder(1)
		                                                                   .setFilterPaths("/user/login");
		// 로그아웃 필터 등록
		JwtSecurityFilterProvider logoutProvider = JwtSecurityFilterProvider.Filter(jwtLogoutFilter)
		                                                                    .setFilterOrder(2)
		                                                                    .setFilterPaths("/user/logout");
		// 인증 필터 등록
		JwtSecurityFilterProvider authProvider = JwtSecurityFilterProvider.Filter(jwtAuthenticationFilter)
		                                                                  .setFilterOrder(3)
		                                                                  .addFilterPaths("/menu/*")    // 해당 경로 제외처리
		                                                                  .setPermitAll();                        // 나머지는 전부 인증요구
		
		// 인가 필터 등록
		JwtSecurityConfiguration jwtSecurityConfiguration = new JwtSecurityConfiguration();
		jwtSecurityConfiguration.addJwtFilter(loginProvider)
		                        .addJwtFilter(logoutProvider)
		                        .addJwtFilter(authProvider);
								
		
		return jwtSecurityConfiguration;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> filterRegistrationBean(JwtSecurityConfiguration jwtSecurityConfiguration, JwtResponseManager jwtResponseManager){
		
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>();
		
		// 내부에서 사용될 필터 등록
		JwtFilterChain jwtFilterChain = new JwtFilterChain(jwtSecurityConfiguration.getJwtFilters(), jwtResponseManager);
		// 스프링부트에 등록시킬 내부 필터들을 동작시킬 필터 생성
		JwtSecurityManagerFilter jwtSecurityManagerFilter = new JwtSecurityManagerFilter(jwtFilterChain, jwtResponseManager);
		
		registrationBean.setFilter(jwtSecurityManagerFilter);
		registrationBean.addUrlPatterns("/*");
		
		return registrationBean;
	}
	
}


