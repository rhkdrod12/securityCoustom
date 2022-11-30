package com.example.practicejpa.utils.codeMessage;

import com.example.practicejpa.utils.codeMessage.messageInterface.FailMessage;
import lombok.Getter;

@Getter
public enum FileFailMessage implements FailMessage{
	
	// 서버설정 관련은 100번대
	FILE_MAX_OVER_SIZE("ECF101", "허용된 용량을 초과한 파일입니다."),
	FAIL_SAVE_FILE("EPF102", "파일을 저장하는데 실패하였습니다."),
	
	// 요청     관련은 200번대
	EXIST_FILE("ECF201", "등록된 파일입니다."),
	NOT_EXIST_FILE("ECF202", "등록하지 않는 파일입니다."),
	NOT_FOUND_FILE("ECF203", "존재하지 않는 파일입니다."),
	NOT_FOUND_PATH("ECF204", "지정된 경로를 찾을 수 없습니다."),
	
	;
	
	private final String code;
	private final String message;
	
	FileFailMessage(String code, String message) {
		this.code    = code;
		this.message = message;
	}
	
	@Override
	public String Code() {
		return code;
	}
	
	@Override
	public String Message() {
		return message;
	}
	
	// @Override
	// public String Name() {
	// 	return name();
	// }
}
