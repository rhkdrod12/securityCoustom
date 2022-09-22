package com.example.practicejpa.controller;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.handler.ChatHandler;
import com.example.practicejpa.handler.ChatSink;
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
public class ChatController {
	
	@GetMapping("/create")
	public ResponseEntity<?> create() {
		return CommResponse.done(ChatHandler.createSink().getId());
	}
	
	@GetMapping("/beginChat/{id}")
	public Flux<ResponseEntity<?>> beginChat(@PathVariable(name = "id") String chatId) {
		if (ChatHandler.isSink(chatId)) {
			ChatSink sink = ChatHandler.getSink(chatId);
			return sink.getFlux()
			           .doFirst(() -> {
				           System.out.println("처음시작?");
				           sink.tryEmitNext("유저가 참여하였습니다.");
			           })
				       .doOnCancel(() -> {
					       sink.tryEmitNext("유저가 나갔습니다2.");
				       })
			           .doFinally(signalType -> {
				           if (SignalType.CANCEL.equals(signalType)) {
					           System.out.println("취소 요청임 해당 유저의 마지막임");
				           }
				           System.out.println("테스트 마지막");
				           System.out.println(signalType);
			           });
		} else {
			return Flux.error(new GlobalException(SystemMessage.NOT_EXIST_DATA));
		}
	}
	
	@GetMapping("/existChat/{id}")
	public ResponseEntity<?> existChat(@PathVariable(name = "id") String chatId) {
		if (ChatHandler.isSink(chatId)) {
			return CommResponse.done();
		} else {
			return CommResponse.fail(SystemMessage.NOT_EXIST_DATA);
		}
	}
	
	@GetMapping("/send/{id}")
	public ResponseEntity<?> send(@PathVariable(name = "id") String chatId, @RequestParam(name = "message") String message) {
		log.info("chatId: {} message: {}", chatId, message);
		if (ChatHandler.isSink(chatId)) {
			log.info("Sink 진입확인");
			ChatSink sink = ChatHandler.getSink(chatId);
			sink.tryEmitNext(message);
			return CommResponse.done();
		} else {
			return CommResponse.fail(SystemMessage.NOT_EXIST_DATA);
		}
	}
	
}
