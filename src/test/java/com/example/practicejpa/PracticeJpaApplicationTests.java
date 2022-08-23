package com.example.practicejpa;

import com.example.practicejpa.modal.Menu;
import com.example.practicejpa.modal.TestA;
import com.example.practicejpa.modal.TestB;
import com.example.practicejpa.repository.MenuRepository;
import com.example.practicejpa.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@Transactional
class PracticeJpaApplicationTests {
	
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	TestRepository testRepository;
	
	@Test
	void contextLoads() {
		System.out.println();
	}
}
