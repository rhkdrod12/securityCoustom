package com.example.practicejpa;

import com.example.practicejpa.dao.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PracticeJpaApplicationTests {
	
	@Autowired
	MenuRepository menuRepository;
	
	
	
	@Test
	void contextLoads() {
		System.out.println();
	}
}
