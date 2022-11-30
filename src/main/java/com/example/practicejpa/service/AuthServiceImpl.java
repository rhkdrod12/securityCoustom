package com.example.practicejpa.service;


import com.example.practicejpa.dao.UserDao;
import com.example.practicejpa.jwtSecurity.JWTResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl {
	
	@Autowired
	UserDao userDao;
	
	public JWTResult rePublishToken() {
		
		return null;
	}
	
}
