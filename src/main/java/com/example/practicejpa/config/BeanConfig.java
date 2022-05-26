package com.example.practicejpa.config;

import com.example.practicejpa.utils.P6spyPrettySqlFormatter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.p6spy.engine.spy.P6SpyOptions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Configuration
public class BeanConfig {
	
	
	Hibernate5Module hibernate5Module() {
		Hibernate5Module hm = new Hibernate5Module();
		hm.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
		return hm;
	}
	
	@Bean
	ObjectMapper objectMapper(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(hibernate5Module());
		objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
		
		return objectMapper;
	}
	
	@Bean
	Session session(EntityManager entityManager) {
		return entityManager.unwrap(Session.class);
	}
	
	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
		return new JPAQueryFactory(entityManager);
	}
	
	
	@Bean
	MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
		return new MappingJackson2HttpMessageConverter(objectMapper());
	}
	
	@PostConstruct
	public void setLogMessageFormat() {
		P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spyPrettySqlFormatter.class.getName());
	}
}
