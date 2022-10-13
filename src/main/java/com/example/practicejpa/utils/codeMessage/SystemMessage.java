package com.example.practicejpa.utils.codeMessage;

import com.example.practicejpa.utils.codeMessage.messageInterface.FailMessage;
import com.example.practicejpa.utils.codeMessage.messageInterface.SucessMessage;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

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
	 * 2은 재요청시(파라미터 변경이라든지, 나중에 재요청한다면 - 클라이언트에서 처리하면) 복구 여부가능 여부
	 * ANYTHING     A   상관없음   ->정상처리된경우 사용
	 * POSSIBLE     P   가능       ->에러시
	 * CRITICAL     C   불가능     ->에러시
	 *
	 * 3~6
	 * 0000
	 * 4는 대상
	 * 쓰든 말든 상관없음 필요에 의해 표기 필요없으면 0으로 표기
	 */
	
	// 성공관련
	HEART_BEAT("SA0000", ""),
	SUCCUES_REQUEST("SA0001", "정상적으로 처리되었습니다"),
	SUCCUES_LOGIN("SA0002", "로그인에 성공하였습니다"),
	SUCCUES_LOGOUT("SA0003", "로그아웃에 성공하였습니다"),
	
	// 실패관련
	REQUEST_FAIL("EC0001", "처리 중 오류가 발생하였습니다"),
	ERROR_NETWORK("EC0002", "네트워크에 문제가 발생하였습니다."),
	UNNOMAL_REQUEST("EC0003", "정상적이지 않은 요청입니다"),
	
	NOT_EXIST_PARAM("EP0001", "필수 파라미터가 누락되었습니다."),
	NOT_EXIST_DATA("EP0002", "존재하지 않는 데이터입니다."),
	ACCESS_DENIED("EP0003", "접근 권한이 없습니다"),
	
	UNAUTHORIZED("EP0004", "인증되지 않은 사용자입니다."),
	INVALID_AUTHORIZED("EP0005", "유효하지 않은 인증정보입니다."),
	EXPIRE_AUTHORIZED("EP0006", "만료된 인증정보입니다."),
	NOT_EXIST_USER("EP0007", "존재하지 않는 사용자입니다."),
	
	
	// 기타 관련
	CANCEL_REQUEST("OA0001", "취소요청이 발생하였습니다."),
	MISMATCH_USEID("OA0002", "아이디가 일치하지 않습니다."),
	MISMATCH_PASSWORD("OA0003", "비밀번호가 일치하지 않습니다."),
	MISMATCH_USER("OA0004", "아이디 또는 비밀번호가 일치하지 않습니다."),
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
	
	/**
	 * code를 enum으로 변환
	 * @param code systemCode
	 * @return systemCode enum
	 */
	@JsonCreator
	public static SystemMessage toEnum(String code){
		return Arrays.stream(SystemMessage.values()).filter(systemMessage ->  systemMessage.code.equals(code)).findFirst().orElse(null);
	}
}

