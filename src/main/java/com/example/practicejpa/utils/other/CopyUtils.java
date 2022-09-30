package com.example.practicejpa.utils.other;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CopyUtils {
	
	static private ObjectMapper objectMapper = new ObjectMapper();
	
	static {
		objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
	}
	
	static public Map<String, Object> convertMap(Object object) {
		return objectMapper.convertValue(object, Map.class);
	}
	
	static public <T> T convertObject(Map<String, Object> map, Class<T> clz) {
		return objectMapper.convertValue(map, clz);
	}
	
	/**
	 * 객체를 해당 클래스로 복사
	 * @param clz
	 * @param obj
	 * @param <T>
	 * @return
	 */
	static public <T> T CopyObject(Class<T> clz, Object obj) {
		return obj!=null? objectMapper.convertValue(obj, clz) : null;
	}
	
	/**
	 * 객체를 해당 클래스로 복사
	 * @param clz
	 * @param obj
	 * @param <T>
	 * @return
	 */
	static public <T> List<T> CopyCollection(Class<T> clz, Collection<?> obj){
		return obj.stream().map(o -> CopyObject(clz, o)).collect(Collectors.toList());
	}
	
	static public void copyProperty(){
		
	}
	
}
