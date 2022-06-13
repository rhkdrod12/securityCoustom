package com.example.practicejpa.repository;

import com.example.practicejpa.modal.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, String> {
	
	List<Code> findByCodeLike(String code);
	
	List<Code> findByCode(String code);
	
	List<Code> findByUpperCodeCode(String upperCode);
}