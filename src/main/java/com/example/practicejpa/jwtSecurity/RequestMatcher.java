package com.example.practicejpa.jwtSecurity;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;

public class RequestMatcher {
	
	static PathMatcher antPathMatcher = new AntPathMatcher();
	
	static public boolean antMatches(HttpServletRequest request, String[] matchPaths) {
		String servletPath = request.getServletPath();
		for (String matchPath : matchPaths) {
			if (antPathMatcher.matchStart(matchPath, servletPath)) {
				return true;
			}
		}
		return false;
	}

}
