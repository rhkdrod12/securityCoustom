package com.example.practicejpa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/webflux")
public class WebFluxController {
	
	@RequestMapping("/test1")
	public Flux<String> test1(){
		return null;
	}
}
