package com.example.practicejpa.controller;

import com.example.practicejpa.dto.ResponseDto;
import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseDto<?> getType(@RequestParam("code") String code){
		return new ResponseDto<>(codeService.getCode(Optional.of(code).orElseThrow(()->new GlobalException("요청값이 비어있습니다."))));
	}



}
