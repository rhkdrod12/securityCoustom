package com.example.practicejpa.jwtSecurity;

public class JwtContext {
	
	static private final ThreadLocal<Member> userThreadLocal = new ThreadLocal<>();
	
	private JwtContext() {}
	
	static public void setContext(Member user) {
		userThreadLocal.remove();
		userThreadLocal.set(user);
	}
	
	static public Member getContext() {
		return userThreadLocal.get();
	}
	
	static public void remove() {
		userThreadLocal.remove();
	}
	
	static public void init(){
		remove();
	}
}
