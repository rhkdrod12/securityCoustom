package com.example.practicejpa.jwtSecurity.exception;

import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.codeMessage.messageInterface.MessageCode;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum JwtSecurityMessage implements MessageCode {
	/**
	 * USER CREDENTIAL
	 */
	NOT_EXIST_USER_ID("EPUC01", "아이디가 존재하지 않습니다."),
	NOT_EXIST_USER_PW("EPUC02", "비밀번호가 존재하지 않습니다."),
	NOT_FOUND_USER("EPUC03", "해당 사용자를 찾을 수 없습니다."),
	NOT_EXIST_USER("EPUC04", "존재하지 않는 사용자입니다."),
	NOT_EXIST_AUTH("EPUC05", "인증정보가 존재하지 않습니다."),
	
	MISMATCH_USEID("EPUC06", "아이디가 일치하지 않습니다."),
	MISMATCH_PASSWORD("EPUC07", "비밀번호가 일치하지 않습니다."),
	MISMATCH_USER("EPUC08", "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다."),
	
	UNAUTHORIZED("ECUC09", "인증되지 않은 사용자입니다."),
	INVALID_AUTHORIZED("ECUC10", "유효하지 않은 인증정보입니다."),
	EXPIRE_AUTHORIZED("ECUC11", "만료된 인증정보입니다."),
	
	ACCESS_DENIED("ECUC12", "접근 권한이 없습니다"),
	;
	
	private final String code;
	private final String message;
	
	JwtSecurityMessage(String code, String message) {
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
	// @Override
	// public String Name() {
	// 	return name();
	// }
	
	/**
	 * code를 enum으로 변환
	 * @param code systemCode
	 * @return systemCode enum
	 */
	@JsonCreator
	public static JwtSecurityMessage toEnum(String code){
		return Arrays.stream(JwtSecurityMessage.values()).filter(systemMessage ->  systemMessage.code.equals(code)).findFirst().orElse(null);
	}
	
}
