package com.example.practicejpa.dto;

import com.example.practicejpa.modal.BaseEntity;
import com.example.practicejpa.vo.BaseVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BaseJpaEntityDto {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	Session session;
	
	@Autowired
	JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public <T extends BaseEntity> void save(T entity) {
		session.save(entity);
	}
	public <T extends BaseEntity> void save(Collection<T> entities) {
		entities.forEach(entity-> session.save(entity));
	}
	public <T extends BaseEntity> void saveOrUpdate(T entity) {
		session.saveOrUpdate(entity);
	}
	public <T extends BaseEntity> void saveOrUpdate(Collection<T> entities) {
		entities.forEach(entity-> session.saveOrUpdate(entity));
	}
	public <T extends BaseVo> void save(T vo, Class<? extends BaseEntity> clazz) {
		BaseEntity baseEntity = convertObject(clazz, vo);
		session.save(baseEntity);
	}
	public <T extends BaseVo> void save(Collection<T> vos, Class<? extends BaseEntity> clazz) {
		vos.forEach(vo-> save(vo, clazz));
	}
	public <T extends BaseVo> void saveOrUpdate(T vo, Class<? extends BaseEntity> clazz) {
		session.saveOrUpdate(convertObject(clazz, vo));
	}
	public <T extends BaseVo> void saveOrUpdate(Collection<T> vos, Class<? extends BaseEntity> clazz) {
		vos.forEach(vo -> saveOrUpdate(vo, clazz));
	}
	
	public <T extends BaseEntity> T convertObject(Class<T> clazz, Object obj) {
		return objectMapper.convertValue(obj, clazz);
	}
	
	public <T extends BaseVo> T convertVo(Class<T> clazz, Object obj) {
		return objectMapper.convertValue(obj, clazz);
	}
	
	public <T extends BaseEntity> List<T> convertCollection(Class<T> clazz, Collection<?> obj) {
		return obj.stream().map(o -> convertObject(clazz, o)).collect(Collectors.toList());
	}
}
