package com.example.practicejpa.controller;

import com.example.practicejpa.utils.responseMessage.CommResponse;
import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.utils.code.SystemMessage;
import com.example.practicejpa.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/Code")
public class CodeController {
	
	@Autowired
	CodeService codeService;
	
	
	@GetMapping("/getType")
	public ResponseEntity<?> getType(@RequestParam("code") String code){
		return CommResponse.done(codeService.getCode(Optional.of(code).orElseThrow(()->new GlobalException(SystemMessage.NOT_EXIST_PARAM))));
	}



}
