package com.example.practicejpa.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

// @Component
// public class HttpListener implements ApplicationListener<ApplicationStartedEvent> {
// 	@Override
// 	public void onApplicationEvent(ApplicationStartedEvent event) {
// 		System.out.println("시작시작");
// 		System.out.println(event);
// 	}
// }


