package com.example.practicejpa.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.persistence.EntityManager;

@Configuration
public class BeanConfig {
	
	
	private Hibernate5Module hibernate5Module() {
		Hibernate5Module hm = new Hibernate5Module();
		hm.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
		return hm;
	}
	
	
	private ObjectMapper objectMapper(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(hibernate5Module());
		objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
		
		return objectMapper;
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
		return new MappingJackson2HttpMessageConverter(objectMapper());
	}
	
	
	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
		return new JPAQueryFactory(entityManager);
	}
	
	@Bean
	public Session session(EntityManager entityManager) {
		return entityManager.unwrap(Session.class);
	}
	
}
