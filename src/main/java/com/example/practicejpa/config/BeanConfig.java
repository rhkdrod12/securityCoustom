package com.example.practicejpa.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class BeanConfig {
	
	
	Hibernate5Module hibernate5Module() {
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
	MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
		return new MappingJackson2HttpMessageConverter(objectMapper());
	}
}
