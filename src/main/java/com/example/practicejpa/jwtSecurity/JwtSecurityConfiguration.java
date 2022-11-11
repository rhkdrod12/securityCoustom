package com.example.practicejpa.jwtSecurity;


import com.example.practicejpa.jwtSecurity.authentication.JwtSecurityFilterProvider;
import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;
import lombok.Getter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.util.comparator.Comparators;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class JwtSecurityConfiguration {
	
	// JWT 인증, 인가시 사용할 필터
	List<JwtSecurityFilterProvider> jwtFilterList = new ArrayList<>();
	
	
	public JwtSecurityConfiguration addJwtFilter(JwtSecurityFilterProvider filterProvider){
		// 필터 추가
		jwtFilterList.add(filterProvider);
		// 필터 order에 따른 재정렬
		jwtFilterList.sort(Comparator.comparingInt(JwtSecurityFilterProvider::getFilterOrder));
		
		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		
		return this;
	}
	
	public Filter getInitfilter(){
		
		return null;
	}
	
}

class JwtRootFilter implements Filter {
	
	List<JwtSecurityFilterProvider> jwtFilterList;
	
	public JwtRootFilter(List<JwtSecurityFilterProvider> jwtFilterList) {
		this.jwtFilterList = jwtFilterList;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		
		for (JwtSecurityFilterProvider jwtSecurityFilterProvider : this.jwtFilterList) {
			
			Filter filter = jwtSecurityFilterProvider.getFilter();
			
			
			
		}
	
	
		
	}
}
