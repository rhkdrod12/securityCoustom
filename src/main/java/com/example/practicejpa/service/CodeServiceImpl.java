package com.example.practicejpa.service;

import com.example.practicejpa.modal.Code;
import com.example.practicejpa.repository.CodeRepository;
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
	public List<Code> getCode(String code) {
		return codeRepository.findByUpperCodeCode(code);
	}
}
