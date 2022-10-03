package com.example.practicejpa.service;

import com.example.practicejpa.auth.MemberDto;
import com.example.practicejpa.model.AuthToken;
import com.example.practicejpa.utils.Jwt.JWTResult;

public interface UserService {
	
	void saveAuthToken(AuthToken authToken);
	
}
