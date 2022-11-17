package com.example.practicejpa.jwtSecurity.jwtEnum;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

/**
 * 결과값을 받기 위한 enum
 */
public enum JwtState {
	
	SUCCESS(1),
	EXPIRE(2),
	ERROR(3),
	CRITICAL(4),    // 아직 사용안함, 일단 모든 에러부분은 ERROR로 처리하고 있음
	;
	
	final int result;
	
	JwtState(int result) {
		this.result = result;
	}
	
	@JsonCreator
	public static JwtState toEnum(int code){
		return Arrays.stream(JwtState.values()).filter(value -> value.result == code).findFirst().orElse(null);
	}
}
