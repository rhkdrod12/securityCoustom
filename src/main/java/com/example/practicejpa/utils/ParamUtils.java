package com.example.practicejpa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParamUtils {
	
	static public boolean isEmpty(Object obj){
		return obj == null || "".equals(obj);
	}
	
	static public boolean isNotEmpty(Object obj){
		return !isEmpty(obj);
	}
	
	static public String getCurrentTime(String pattern){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(new Date());
	}
	static public String getCurrentTime(){
		return getCurrentTime("yyyyMMdd");
	}
	
}
