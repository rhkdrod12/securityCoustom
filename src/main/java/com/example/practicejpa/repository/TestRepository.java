package com.example.practicejpa.repository;

import com.example.practicejpa.modal.TestA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestA, Long> {

}
