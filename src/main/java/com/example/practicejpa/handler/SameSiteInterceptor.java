package com.example.practicejpa.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;

public class SameSiteInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//request.getSession();
		
		// HttpSession session = request.getSession();
		// String sessionId = session.getId();
		// String sameSite = "None";
		//
		// String setCookieHeader = response.getHeader(HttpHeaders.SET_COOKIE);
		// if (setCookieHeader == null || !setCookieHeader.endsWith("SameSite=")) {
		// 	ResponseCookie build = ResponseCookie.from("JSESSIONID", sessionId)
		// 	                                     .sameSite(sameSite)
		// 	                                     .secure(true)
		// 	                                     .path("/")
		// 	                                     .httpOnly(true)
		// 	                                     .build();
		//
		// 	response.setHeader(HttpHeaders.SET_COOKIE, build.toString());
		// }
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
}
