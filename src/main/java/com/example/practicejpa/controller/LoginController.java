package com.example.practicejpa.controller;

import com.example.practicejpa.auth.MemberDto;
import com.example.practicejpa.auth.UserLoginService;
import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.utils.Jwt.JWTResult;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.ParamUtils;
import com.example.practicejpa.utils.responseEntity.CommResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	UserLoginService userLoginService;
	
	@PostMapping("/refreshToken")
	
	public ResponseEntity<?> refreshToken(@RequestParam(name = "accessToken") String accessToken, @RequestParam(name = "refreshToken") String refreshToken) {
		
		if (ParamUtils.isEmpty(accessToken) || ParamUtils.isEmpty(refreshToken)) {
			throw new GlobalException(SystemMessage.NOT_EXIST_PARAM);
		}
		
		return CommResponse.done(new JWTResult(userLoginService.rePublishToken(accessToken, refreshToken)));
	}
	
	
}
