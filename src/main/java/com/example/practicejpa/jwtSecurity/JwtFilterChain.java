package com.example.practicejpa.jwtSecurity;

import com.example.practicejpa.jwtSecurity.authentication.JwtSecurityFilterProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilterChain implements FilterChain {
	
	private final JwtSecurityFilterProvider[] jwtSecurityFilterProviders;
	private int filterIndex = 0;
	private final int filterSize;
	
	public JwtFilterChain(JwtSecurityFilterProvider[] jwtSecurityFilterProviders) {
		this.jwtSecurityFilterProviders = jwtSecurityFilterProviders;
		this.filterSize = jwtSecurityFilterProviders.length;
	}
	
	public void doInit(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		this.filterIndex = 0;
		this.doFilter(servletRequest, servletResponse);
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
		if (filterSize > filterIndex) {
			JwtSecurityFilterProvider filterProvider = jwtSecurityFilterProviders[filterIndex++];
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			// 해당 경로가 일치하는 경우 해당 필터로 이동, 아니면 다음 필터로 이동처리
			// 해당 필터가 잡힌 경우에는 해당 필터에서 다음으로 이동 여부를 잡음
			if (filterProvider.requestMatch(request)) {
				filterProvider.getFilter().doFilter(servletRequest, servletResponse, this, filterProvider.getHandler());
			}else{
				this.doFilter(servletRequest, servletResponse);
			}
		}
	}
}
