package com.example.practicejpa.service;

import com.example.practicejpa.dao.repository.CodeRepository;
import com.example.practicejpa.model.CodeMgm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CodeServiceImpl implements CodeService{
	
	@Autowired
	CodeRepository codeRepository;
	
	@Override
	public List<CodeMgm> getCode(String code) {
		List<CodeMgm> byUpperCodeCode = codeRepository.findByUpperCodeCode(code);
		return byUpperCodeCode;
	}
}
