package com.example.practicejpa.dao.repository;

import com.example.practicejpa.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
}