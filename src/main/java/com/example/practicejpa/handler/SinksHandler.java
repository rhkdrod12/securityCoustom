package com.example.practicejpa.handler;

import com.example.practicejpa.vo.MenuVo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class SinksHandler {
	
	private final Sinks.Many<MenuVo> scheduleEvents;
	
	public SinksHandler() {
		this.scheduleEvents = Sinks.many().multicast().directAllOrNothing();
	}
	
	public Sinks.Many<MenuVo> getSink(){
		
		return this.scheduleEvents;
	}
	
	public Flux<MenuVo> asFlux(){
		return scheduleEvents.asFlux();
		
	}
	
}
