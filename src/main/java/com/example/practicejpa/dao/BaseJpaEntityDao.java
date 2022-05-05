package com.example.practicejpa.dao;

import com.example.practicejpa.modal.BaseEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class BaseJpaEntityDao {
	
	
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private Session session;
	@Autowired
	public JPAQueryFactory jpaQueryFactory;
	
	// 저장
	public <T extends BaseEntity> void save(T object){
		session.save(object);
	}
	public <T extends BaseEntity> void save(Collection<T> collection){
		for (T t : collection) {
			session.save(t);
		}
	}
	
	// 저장및업데이트
	public <T extends BaseEntity> void saveOrUpdate(T object){
		session.saveOrUpdate(object);
	}
	public <T extends BaseEntity> void saveOrUpdate(Collection<T> collection){
		for (T t : collection) {
			session.saveOrUpdate(t);
		}
	}
	
	// 삭제
	public <T extends BaseEntity> void remove(T object) {
		session.remove(object);
	}
	public <T extends BaseEntity> void remove(Collection<T> collection){
		for (T t : collection) {
			session.remove(t);
		}
	}
	
}
