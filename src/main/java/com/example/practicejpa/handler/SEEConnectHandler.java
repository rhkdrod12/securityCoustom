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
public class SEEConnectHandler {
	
	public static final Map<String, SSESink> sinkMap = new HashMap<>();
	
	private static final Consumer<String> callback = id -> {
		SSESink chatSink = sinkMap.get(id);
		// 구독자 수가 0이면 제거 맵에서 제거 및 완료처리
		if (chatSink != null ) {
			if(chatSink.getSink().currentSubscriberCount() == 0){
				chatSink.getSink().tryEmitComplete();
				sinkMap.remove(id);
			}
		}else{
			throw new GlobalException(SystemMessage.REQUEST_FAIL);
		}
	};
	
	// 연결번호 ID
	private static long chatIdIdx = 1;
	
	/**
	 * 연결정보 생성 (연결ID와 Sink를 반환)
	 * @return 
	 */
	public static SinkResult createSink() {
		// 채팅방 ID부여 및 ID번호 증가
		String chatId = String.valueOf(chatIdIdx++);
		// ChatSink 생성 및 반환
		return new SinkResult(chatId, sinkMap.put(chatId, new SSESink(chatId, callback)));
	}
	
	/**
	 * 연결정보 반환
	 * @param id
	 * @return
	 */
	public static SSESink getSink(String id) {
		return sinkMap.get(id);
	}
	
	/**
	 * 연결ID에 해당하는 연결정보 존재 여부
	 * @param id
	 * @return
	 */
	public static boolean isSink(String id) {
		return sinkMap.containsKey(id);
	}
	
	/**
	 * 연결ID에 해당하는 연결정보 삭제
	 * @param id
	 * @return
	 */
	public static boolean removeSink(String id) {
		if (sinkMap.containsKey(id)) {
			SSESink sseSink = sinkMap.get(id);
			sseSink.getSink().tryEmitComplete();
		}
		return sinkMap.remove(id) != null;
	}
}


