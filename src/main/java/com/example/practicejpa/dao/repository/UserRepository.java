package com.example.practicejpa.dao.repository;

import com.example.practicejpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface UserRepository extends JpaRepository<User, BigDecimal> {
	
	User findByUserId(String userId);
	
	User findByRefrehId(String refreshId);
	
}