package com.example.practicejpa.utils.code;

import com.example.practicejpa.utils.code.messageInterface.FailMessage;
import com.example.practicejpa.utils.code.messageInterface.SucessMessage;

public enum SystemMessage implements SucessMessage, FailMessage {
	
	
	/**
	 * 필요없는 문자의 경우 0로 채움 -> 0은 그냥 기본을 의미
	 * 4개의 문자로 표현
	 * 뒤에 01의 순번을 붙여 에러 코드를 완성시킴 최대 같은 문자에서 99개까지 표현 가능하도록(99개를 안넘길 것 같음)
	 * 만약 필요하다면 나중에 에러 코드를 손보든가 해야지
	 * SR0001       :   정상적인 요청을 처리를 의미
	 *
	 * 1는 성공, 실패
	 * SUCCESS      S   성공적인 처리를 의미
	 * ERROR        E   서버에서 발생한 예외를 의미
	 * OTHER        O   기타
	 *
	 * 2은 재요청시(파라미터 변경이라든지, 나중에 재요청한다면) 복구 여부가능 여부
	 * ANYTHING     A   상관없음 -> 정상처리된경우 사용
	 * POSSIBLE     P   가능       ->에러시
	 * CRITICAL     C   불가능     ->에러시
	 *
	 * 3~6
	 * 0000
	 * 4는 대상
	 * 쓰든 말든 상관없음 필요에 의해 표기 필요없으면 0으로 표기
	 */
	
	// 성공관련
	SUCCUES_REQUEST("SA0001", "정상적으로 처리되었습니다"),
	
	// 실패관련
	ERROR_REQUEST_FAIL("EC0001", "처리 중 오류가 발생하였습니다"),
	ERROR_NETWORK("EC0002", "네트워크에 문제가 발생하였습니다."),
	NOT_EXIST_PARAM("EP0001", "필수 파라미터가 누럭되었습니다."),
	
	// 기타 관련
	CANCEL_REQUEST("OA0001", "취소요청이 발생하였습니다."),
	;
	
	
	private final String code;
	private final String message;
	
	SystemMessage(String code, String message) {
		this.code    = code;
		this.message = message;
	}
	
	@Override
	public java.lang.String Code() {
		return code;
	}
	@Override
	public java.lang.String Message() {
		return message;
	}
	@Override
	public String Name() {
		return name();
	}
}

