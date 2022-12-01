package com.example.practicejpa;

import com.example.practicejpa.jwtSecurity.exception.JwtSecurityMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 메시지 자바 객체를 JS 객체로 변환
 */
public class MakeJsMessgaeCode {
	public static void main(String[] args) {
		
		Class<JwtSecurityMessage> jwtSecurityMessageClass = JwtSecurityMessage.class;
		
		String result = makeJsObject(makeEnumMap(jwtSecurityMessageClass));
		
		System.out.println(result);
	}
	
	public static String makeJsObject(List<JsObject> objectList){
		
		StringBuilder sb = new StringBuilder();
		
		for (JsObject jsObject : objectList) {
			sb.append(jsObject.print());
		}
		return sb.toString();
	}
	
	public static <T extends Enum> List<JsObject> makeEnumMap(Class<T> clz) {
		// 선언한 필드 찾기
		List<Field> declareField = findEnumField(clz);
		// enum 가져오기
		T[] enumConstants = clz.getEnumConstants();
		
		return Arrays.stream(enumConstants).map(item -> {
			             JsObject jsObject = new JsObject(item.name());
			
			             for (Field field : declareField) {
				             String name = field.getName();
				             try {
					             jsObject.setValue(name, field.get(item).toString());
				             } catch (IllegalAccessException e) {
					             throw new RuntimeException(e);
				             }
			             }
						 
			             return jsObject;
		             })
		             .collect(Collectors.toList());
		
	}
	
	public static <T> List<Field> findEnumField(Class<T> clz) {
		return Arrays.stream(clz.getDeclaredFields())
		             .filter(item -> !Modifier.isStatic(item.getModifiers()))
		             .peek(item -> item.setAccessible(true))
		             .collect(Collectors.toList());
	}
	
	
}

class JsObject {
	String name;
	Map<String, String> data = new HashMap<>();
	
	public JsObject(String name) {
		this.name = name;
	}
	
	public void setValue(String key, String value) {
		data.put(key, value);
	}
	
	public String print(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(name);
		sb.append(": {\n");
		
		
		LinkedHashSet<String> temp = new LinkedHashSet<>(data.keySet());
		data.keySet().stream().sorted().forEach(item->{
			sb.append("\t"+item + ": \"" + data.get(item) + "\",\n");
		});
		
		sb.append("},\n");
		
		return sb.toString();
	}
	
}
