package com.example.practicejpa.jwtSecurity.authentication;

import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;

public class JwtSecurityFilterProvider {
	// 필터 경로
	String filterPath = "/*";
	// 필터 적용 순서
	int filterOrder = 0;
	// 필터
	Filter filter;
	// 성공 또는 실패 핸들러, 핸들러가 있는 경우 다음 필터로 넘기지 않음
	JwtSecurityHandler handler;
	
	private JwtSecurityFilterProvider(Filter filter) {
		this.filter = filter;
	}
	
	static public JwtSecurityFilterProvider Filter(Filter filter){
		return new JwtSecurityFilterProvider(filter);
	}
	
	public JwtSecurityFilterProvider setFilterPath(String filterPath) {
		this.filterPath = filterPath;
		return this;
	}
	
	public JwtSecurityFilterProvider setFilterOrder(int filterOrder) {
		this.filterOrder = filterOrder;
		return this;
	}
	
	public JwtSecurityFilterProvider setFilter(Filter filter) {
		this.filter = filter;
		return this;
	}
	
	public JwtSecurityFilterProvider setHandler(JwtSecurityHandler handler) {
		this.handler = handler;
		return this;
	}
}
