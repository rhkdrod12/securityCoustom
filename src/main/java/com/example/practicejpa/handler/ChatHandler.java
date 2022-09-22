package com.example.practicejpa.handler;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.utils.codeMessage.SystemMessage;

import java.util.HashMap;
import java.util.Map;
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
				System.out.println("제거 요청");
				sinkMap.remove(id);
			}
			// else{
			// 	chatSink.tryEmitNext("유저가 나갔습니다.");
			// }
		}else{
			throw new GlobalException(SystemMessage.ERROR_REQUEST_FAIL);
		}
	};
	
	
	private static long chatIdIdx = 1;
	
	public static SinkResult createSink() {
		// 이렇게 하면 매번 테스트하기 힘드니까 임시로 Long으로 처리
		// String uuid = UUID.randomUUID().toString();
		// ChatSink chatSink = new ChatSink(uuid, callback);
		// sinkMap.put(uuid, chatSink);
		// return new SinkResult(uuid, chatSink);
		
		// 채팅방 ID부여 및 ID번호 증가
		String chatId = String.valueOf(chatIdIdx++);
		// ChatSink 생성 및 반환
		return new SinkResult(chatId, sinkMap.put(chatId, new ChatSink(chatId, callback)));
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


