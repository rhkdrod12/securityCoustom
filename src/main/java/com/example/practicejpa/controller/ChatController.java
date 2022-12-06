package com.example.practicejpa.controller;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.handler.SEEConnectHandler;
import com.example.practicejpa.handler.SSESink;
import com.example.practicejpa.utils.JwtAuth;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.responseEntity.CommResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

@RestController
@RequestMapping("/chat")
@Slf4j
@JwtAuth
public class ChatController {
	
	@GetMapping("/create")
	public ResponseEntity<?> create() {
		return CommResponse.done(SEEConnectHandler.createSink().getId());
	}
	
	@GetMapping("/beginChat/{id}")
	public Flux<ResponseEntity<?>> beginChat(@PathVariable(name = "id") String chatId, @RequestParam(name = "name", required = false, defaultValue = "USER") String name) {
		if (SEEConnectHandler.isSink(chatId)) {
			SSESink sink = SEEConnectHandler.getSink(chatId);
			return sink.getFlux()
			             .doFirst(() -> {
				           // 처음 연결이 시작되었을 때 작동하는 부분
				           sink.tryEmitNext(name + "가 참여하였습니다.");
			           })
			           .doOnCancel(() -> {
				           // 연결이 끊겼을 때 작동하는 부분
				           sink.tryEmitNext(name + "가 나갔습니다.");
			           })
			           .doFinally(signalType -> {
				           // 무엇이 되었든 해당 에러든, 종료든, 완료든 마지막에 동작하는 부분
				           // 해당 연결의 마지막에 작동하는 기능임
				           if (SignalType.CANCEL.equals(signalType)) {
					           System.out.println("취소 요청임 해당 유저의 마지막임");
				           }
			           });
		} else {
			return Flux.error(new GlobalException(SystemMessage.NOT_EXIST_DATA));
		}
	}
	
	@GetMapping("/existChat/{id}")
	public ResponseEntity<?> existChat(@PathVariable(name = "id") String chatId) {
		if (SEEConnectHandler.isSink(chatId)) {
			// 해당 채팅 종료 처리
			SEEConnectHandler.getSink(chatId).tryEmitComplete();
			return CommResponse.done();
		} else {
			return CommResponse.fail(SystemMessage.NOT_EXIST_DATA);
		}
	}
	
	@GetMapping("/send/{id}")
	public ResponseEntity<?> send(@PathVariable(name = "id") String chatId,
	                              @RequestParam(name = "name") String name,
	                              @RequestParam(name = "message") String message) {
		if (SEEConnectHandler.isSink(chatId)) {
			log.info("chatId: {} userName: {} message: {}", chatId, name, message);
			SEEConnectHandler.getSink(chatId).tryEmitNext(String.format("%s: %s", name, message));
			return CommResponse.done();
		} else {
			return CommResponse.fail(SystemMessage.NOT_EXIST_DATA);
		}
	}
	
}
