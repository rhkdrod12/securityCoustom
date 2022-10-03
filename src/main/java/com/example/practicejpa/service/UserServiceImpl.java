package com.example.practicejpa.service;

import com.example.practicejpa.dao.repository.AuthTokenRepository;
import com.example.practicejpa.model.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	AuthTokenRepository authTokenRepository;
	
	@Override
	public void saveAuthToken(AuthToken authToken) {
		authTokenRepository.save(authToken);
	}
}
