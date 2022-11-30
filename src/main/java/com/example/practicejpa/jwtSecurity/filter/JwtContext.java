package com.example.practicejpa.jwtSecurity.filter;

import com.example.practicejpa.jwtSecurity.Member;

public class JwtContext {
	public static ThreadLocal<Object> threadLocal = new ThreadLocal<>();
	
	public static void setValue(Object object) {
		threadLocal.set(object);
	}
	
	public static Object getValue() {
		return threadLocal.get();
	}
	
	public static void remove() {
		threadLocal.remove();
	}
	
	public static void setAuthentication(Member member) {
		setValue(member);
	}
	public static Member getAuthentication() {
		return threadLocal.get() instanceof Member ? (Member) threadLocal.get() : null;
	}
	
}

