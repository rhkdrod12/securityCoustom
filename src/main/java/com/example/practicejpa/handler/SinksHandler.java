package com.example.practicejpa.handler;

import com.example.practicejpa.utils.responseEntity.CommResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class SinksHandler {
	
	private final Sinks.Many<ResponseEntity> scheduleEvents;
	
	public SinksHandler() {
		this.scheduleEvents = Sinks.many().multicast().directAllOrNothing();
	}
	
	public Sinks.Many<ResponseEntity> getSink(){
		return this.scheduleEvents;
	}
	
	public void tryEmitNext(Object response){
		this.scheduleEvents.tryEmitNext(CommResponse.done(response));
	}
	
	public Flux<ResponseEntity> asFlux(){
		return scheduleEvents.asFlux();
		
	}
	
}
