package com.example.practicejpa.config;

import com.example.practicejpa.handler.SecurityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SecurityInterceptor())
		        .excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		        .exposedHeaders("Content-Disposition", "Set-Cookie", "Authorization")
		        .allowedHeaders("*")
		        .allowedOrigins("http://localhost:3000", "http://192.168.10.79:3000", "http://127.0.0.1:3000")
		        .allowedMethods("*")
		        .maxAge(7200)
		        .allowCredentials(true);
		
		//response.setHeader("Access-Control-Max-Age", "7200");
	}
}
