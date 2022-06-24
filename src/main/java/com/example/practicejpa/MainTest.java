package com.example.practicejpa;

import com.example.practicejpa.vo.MenuVo;

import java.lang.reflect.InvocationTargetException;

public class MainTest {
	public static void main(String[] args) {
		
		MenuVo menuVo = new MenuVo();
		menuVo.setName("홍길동의 만만세");
		
		Object object = menuVo;
		System.out.println("methodInvoke(MenuVo.class, object) = " + methodInvoke(MenuVo.class, object));
		
		
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
