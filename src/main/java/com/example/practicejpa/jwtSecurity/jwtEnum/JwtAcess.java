package com.example.practicejpa.jwtSecurity.jwtEnum;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum JwtAcess {
	// 모든 접근을 허용하지 않음
	DENY_ALL(0),
	// 모든 접근을 허용함
	PERMIT_ALL(1),
	;
	
	private final int code;
	
	JwtAcess(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	/**
	 * code를 enum으로 변환
	 * @param code JwtPermit code
	 * @return JwtPermit enum
	 */
	@JsonCreator
	public static JwtAcess toEnum(int code){
		return Arrays.stream(JwtAcess.values()).filter(value -> value.code == code).findFirst().orElse(null);
	}
}
