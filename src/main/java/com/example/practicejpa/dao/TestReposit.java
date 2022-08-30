package com.example.practicejpa.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class TestReposit {
	@Autowired
	EntityManager entityManager;
	
	public void test(){
		
	
	}
}
