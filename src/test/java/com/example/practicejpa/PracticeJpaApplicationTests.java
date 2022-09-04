package com.example.practicejpa;

import com.example.practicejpa.dao.BaseJpaEntityDao;
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
	
	@Autowired
	BaseJpaEntityDao baseJpaEntityDao;
	
	@Test
	void contextLoads() {
		
		// TestA testA = TestA.builder().data("테스트 데이텀11").build();
		// TestB testB = TestB.builder().data("테스트 B데이터입니다.").testA(testA).build();
		// testA.setTestB(testB);
		//
		// TestA testA2 = new TestA();
		// testA2.setData("테스트A2 데이터입니다.");
		// TestB testB2 = TestB.builder().data("테스트2 B데이터입니다.").testA(testA2).build();
		// testA2.setTestB(testB2);
		//
		//
		// List<TestA> list = new ArrayList<>();
		// list.add(testA);
		// list.add(testA2);
		//
		// baseJpaEntityDao.save(list);
		// baseJpaEntityDao.flush();
		//
		// System.out.println();
		
	}
}
