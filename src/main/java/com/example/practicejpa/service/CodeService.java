package com.example.practicejpa.service;

import com.example.practicejpa.model.CodeMgm;

import java.util.List;


public interface CodeService {
	
	List<CodeMgm> getCode(String Code);
}
