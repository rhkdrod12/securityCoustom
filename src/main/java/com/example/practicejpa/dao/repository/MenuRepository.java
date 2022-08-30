package com.example.practicejpa.dao.repository;

import com.example.practicejpa.model.MenuMgm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuMgm, Long> {
}
