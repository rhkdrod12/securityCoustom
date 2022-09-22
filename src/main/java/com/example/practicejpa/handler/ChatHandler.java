package com.example.practicejpa.handler;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.utils.codeMessage.SystemMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 이건 SSE 형태로 사용했을 떄의 구조이고
 * WebSocket형태로 사용해봅시다.
 */
public class ChatHandler {
	
	public static final Map<String, ChatSink> sinkMap = new HashMap<>();
	
	private static final Consumer<String> callback = id -> {
		ChatSink chatSink = sinkMap.get(id);
		// 구독자 수가 0이면 제거 맵에서 제거
		if (chatSink != null ) {
			if(chatSink.getSink().currentSubscriberCount() == 0){
				sinkMap.remove(id);
			}else{
				chatSink.tryEmitNext("유저가 나갔습니다.");
			}
		}else{
			throw new GlobalException(SystemMessage.ERROR_REQUEST_FAIL);
		}
	};
	
	public static SinkResult createSink() {
		String uuid = UUID.randomUUID().toString();
		ChatSink chatSink = new ChatSink(uuid, callback);
		sinkMap.put(uuid, chatSink);
		return new SinkResult(uuid, chatSink);
	}
	public static ChatSink getSink(String id) {
		return sinkMap.get(id);
	}
	public static boolean isSink(String id) {
		return sinkMap.containsKey(id);
	}
	
	public static boolean removeSink(String id) {
		return sinkMap.remove(id) != null;
	}
}


