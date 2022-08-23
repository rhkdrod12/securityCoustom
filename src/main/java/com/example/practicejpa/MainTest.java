package com.example.practicejpa;

import com.example.practicejpa.modal.TestA;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class MainTest {
	public static void main(String[] args) {
		
		
		TestA testA = new TestA();
		
		Class<? extends TestA> aClass = testA.getClass();
		
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(testA.getClass());
		System.out.println();
		System.out.println(aClass);
		
		
		
	}
	
	static Object methodInvoke(Class<?> clz, Object object, Object ...param){
		try {
			return clz.getDeclaredMethod("getName").invoke(object);
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}