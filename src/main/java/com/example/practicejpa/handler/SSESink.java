package com.example.practicejpa.handler;

import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.responseEntity.CommResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.function.Consumer;


@Slf4j
public class SSESink {
	
	private final String sinkId;
	private final Sinks.Many<ResponseEntity<?>> scheduleEvents;
	private final Consumer<String> handler;
	
	private static final String HEART_BEAT_PING = "ping";
	
	
	public SSESink(String sinkId, Consumer<String> handler) {
		this.sinkId         = sinkId;
		this.handler        = handler;
		this.scheduleEvents = Sinks.many()
		                           // .replay()
		                           // .all();
		                           .multicast()
		                           // .onBackpressureBuffer(5);
		                           .directAllOrNothing();
	}
	
	public Sinks.Many<ResponseEntity<?>> getSink() {
		return this.scheduleEvents;
	}
	
	public Flux<ResponseEntity<?>> asFlux() {
		return Flux.merge(scheduleEvents.asFlux(), getHeartbeatStream());
	}
	
	public Flux<ResponseEntity<?>> getFlux() {
		return this.asFlux()
		           .onBackpressureBuffer(999, (result) -> log.warn("{} SSE buffer overflow", result.getBody()), BufferOverflowStrategy.DROP_LATEST)
		           .onErrorReturn(CommResponse.fail(SystemMessage.REQUEST_FAIL));
	}
	
	public Flux<ResponseEntity<String>> getHeartbeatStream() {
		return Flux.interval(Duration.ofSeconds(2))
		           .map(i -> ResponseEntity.ok(HEART_BEAT_PING))
		           .doFinally(signalType -> handler.accept(this.sinkId));
		// 어차피 서버에서 열렸는지 확인이 필요하니까 그래서 SSE보다는 WebSocket이 더 적당하다는 건가..
	}
	
	public void tryEmitNext(Object response) {
		this.scheduleEvents.tryEmitNext(CommResponse.done(response));
	}
	
	public void tryEmitComplete() {
		this.scheduleEvents.tryEmitComplete();
	}
	
}
