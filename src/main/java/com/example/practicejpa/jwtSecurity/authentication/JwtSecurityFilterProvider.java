package com.example.practicejpa.jwtSecurity.authentication;

import com.example.practicejpa.jwtSecurity.RequestMatcher;
import com.example.practicejpa.jwtSecurity.handler.JwtSecurityHandler;
import com.example.practicejpa.jwtSecurity.filter.JwtFilter;
import com.example.practicejpa.jwtSecurity.jwtEnum.JwtAcess;

import javax.servlet.http.HttpServletRequest;

public class JwtSecurityFilterProvider {
	static String MATCH_ALL = "/*";
	
	String filterName;
	
	// 0 : 모두 차단하는 상황에서 특정 path만 허용
	private JwtAcess access = JwtAcess.DENY_ALL;
	
	/**
	 * 필터 경로
	 * 필터 경로의 경우 access 에 따라
	 * 모든 접근 허용인 경우에는 입력한 경로는 차단함
	 * 모든 접근 불가인 경우에는 입력한 경로는 허용함
	 */
	private String[] filterPaths = { MATCH_ALL };
	
	// 필터 적용 순서
	private int filterOrder = Integer.MIN_VALUE;
	// 필터
	private JwtFilter filter;
	// 성공 또는 실패 핸들러, 핸들러가 있는 경우 다음 필터로 넘기지 않음
	private JwtSecurityHandler handler;
	
	public String[] getFilterPaths() {
		return filterPaths;
	}
	
	public int getFilterOrder() {
		return filterOrder;
	}
	
	public JwtFilter getFilter() {
		return filter;
	}
	
	public JwtSecurityHandler getHandler() {
		return handler;
	}
	
	private JwtSecurityFilterProvider(JwtFilter filter) {
		this.filter = filter;
	}
	
	static public JwtSecurityFilterProvider Filter(JwtFilter filter){
		return new JwtSecurityFilterProvider(filter);
	}
	
	public JwtSecurityFilterProvider setFilterPaths(String... filterPaths) {
		this.filterPaths = filterPaths;
		return this;
	}
	
	public JwtSecurityFilterProvider addFilterPaths(String... filterPaths) {
		int index = 0;
		String[] newFilterPaths = new String[this.filterPaths.length + filterPaths.length];
		
		for (String filterPath : this.filterPaths) {
			newFilterPaths[index++] = filterPath;
		}
		for (String filterPath : filterPaths) {
			newFilterPaths[index++] = filterPath;
		}
		this.filterPaths = newFilterPaths;
		return this;
	}
	
	public JwtSecurityFilterProvider setFilterOrder(int filterOrder) {
		this.filterOrder = filterOrder;
		return this;
	}
	
	public JwtSecurityFilterProvider setFilter(JwtFilter filter) {
		this.filter = filter;
		return this;
	}
	
	public JwtSecurityFilterProvider setPermitAll() {
		this.access = JwtAcess.PERMIT_ALL;
		return this;
	}
	public JwtSecurityFilterProvider setDenyAll() {
		this.access = JwtAcess.DENY_ALL;
		return this;
	}
	
	public JwtSecurityFilterProvider setHandler(JwtSecurityHandler handler) {
		this.handler = handler;
		this.filter.setJwtSecurityHandler(handler);
		return this;
	}
	
	public boolean requestMatch(HttpServletRequest request){
		if (access == JwtAcess.DENY_ALL) {
			return RequestMatcher.antMatches(request, filterPaths);
		}else{
			return !RequestMatcher.antMatches(request, filterPaths);
		}
	}
}
