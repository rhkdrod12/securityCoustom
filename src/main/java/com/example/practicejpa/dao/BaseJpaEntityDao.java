package com.example.practicejpa.dao;

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
public class BaseJpaEntityDao {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	Session session;
	
	@Autowired
	JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	ObjectMapper objectMapper;
	public void flush(){		session.flush();	}
	public void detach(Object object){session.detach(object);}
	
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
	public <T extends BaseVo> BaseEntity save(T vo, Class<? extends BaseEntity> clazz) {
		BaseEntity baseEntity = convertObject(clazz, vo);
		session.save(baseEntity);
		return baseEntity;
	}
	public <T extends BaseVo> List<BaseEntity> save(Collection<T> vos, Class<? extends BaseEntity> clazz) {
		return vos.stream().map(vo-> save(vo, clazz)).collect(Collectors.toList());
	}
	public <T extends BaseVo> BaseEntity saveOrUpdate(T vo, Class<? extends BaseEntity> clazz) {
		BaseEntity baseEntity = convertObject(clazz, vo);
		session.saveOrUpdate(baseEntity);
		return baseEntity;
	}
	public <T extends BaseVo> List<BaseEntity> saveOrUpdate(Collection<T> vos, Class<? extends BaseEntity> clazz) {
		return vos.stream().map(vo -> saveOrUpdate(vo, clazz)).collect(Collectors.toList());
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
