package com.example.practicejpa.auth.authorityCRUD;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authority {
	String table();
	String column() default  "";
	String key();
	Class<?> keyClazz() default BigDecimal.class;
	Class<?> targetClazz() default Object.class;
	
}
