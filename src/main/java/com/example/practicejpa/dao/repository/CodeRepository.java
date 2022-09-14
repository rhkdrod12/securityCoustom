package com.example.practicejpa.dao.repository;

import com.example.practicejpa.model.CodeMgm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<CodeMgm, String> {
	
	List<CodeMgm> findByCodeLike(String code);
	
	List<CodeMgm> findByCode(String code);
	
	List<CodeMgm> findByUpperCodeCode(String upperCode);
	
}