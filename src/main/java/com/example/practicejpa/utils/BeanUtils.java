package com.example.practicejpa.utils;

public class BeanUtils {
	static public <T> T getBean(Class<T> clz){
		return ApplicationContextProvider.getApplicationContext().getBean(clz);
	}
	static public Object getBean(String beanName) {
		return ApplicationContextProvider.getApplicationContext().getBean(beanName);
	}
	static public <T> T getBean(String beanName, Class<T> clz) {
		return ApplicationContextProvider.getApplicationContext().getBean(beanName, clz);
	}
}
