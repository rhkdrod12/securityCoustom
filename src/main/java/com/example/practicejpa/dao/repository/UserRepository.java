package com.example.practicejpa.dao.repository;

import com.example.practicejpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigDecimal> {
	
	Optional<User> findByUserId(String userId);
	
	User findByRefrehId(String refreshId);
	
	boolean existsByUserId(String userId);
	
	@Query("SELECT userPw FROM User WHERE userId = :userId")
	String findUserPwByUserId(String userId);
	
}