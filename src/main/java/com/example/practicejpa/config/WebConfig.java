package com.example.practicejpa.config;

import com.example.practicejpa.handler.SameSiteInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SameSiteInterceptor())
		        .excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		        .exposedHeaders("Content-Disposition", "Set-Cookie", "Authorization")
		        .allowedHeaders("*")
		        .allowedOrigins("http://localhost:3000", "http://192.168.10.79:3000")
		        .allowedMethods("*")
		        .allowCredentials(true);
	}
}
