package com.example.practicejpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOriginPatterns("/**")
		.exposedHeaders("Content-Disposition")
        .allowedHeaders("*")
		.allowedOrigins("*")
		.allowedMethods("*")
		.allowCredentials(false)
		;
		        // .allowedOrigins("http://127.0.0.1:5500")
		        // .allowedMethods("POST", "GET")
				// .maxAge(3600)
				// .allowedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		
		//response.setHeader("Access-Control-Allow-Headers",
		//                 "Origin, X-Requested-With, Content-Type, Accept, Authorization");
	}
}
