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
		registry.addInterceptor(new SameSiteInterceptor()).excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
		.addMapping("/**")
		// .allowedOriginPatterns("*")
		.exposedHeaders("Content-Disposition", "Set-Cookie")
        .allowedHeaders("*")
		.allowedOrigins("http://localhost:3000", "http://192.168.10.79:3000")
		.allowedMethods("*")
		.allowCredentials(true)
		;
		        // .allowedOrigins("http://127.0.0.1:5500")
		        // .allowedMethods("POST", "GET")
				// .maxAge(3600)
				// .allowedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		
		//response.setHeader("Access-Control-Allow-Headers",
		//                 "Origin, X-Requested-With, Content-Type, Accept, Authorization");
	}
}
