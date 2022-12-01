package com.example.practicejpa.utils.responseEntity;


import com.example.practicejpa.utils.codeMessage.messageInterface.FailMessage;
import com.example.practicejpa.utils.codeMessage.messageInterface.MessageCode;
import com.example.practicejpa.utils.codeMessage.messageInterface.SucessMessage;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResultMessage {
	Object result;
	String resultCode;
	String resultMessage;
	
	// 성공시 결과값 전송용
	static public ResultMessage done(Object result) {
		return new ResultMessage(result, SystemMessage.SUCCUES_REQUEST.Code(), SystemMessage.SUCCUES_REQUEST.Message());
	}
	// 성공시 결과값 전송용
	static public ResultMessage done(Object result, SucessMessage messageCode) {
		return new ResultMessage(result, messageCode.Code(), messageCode.Message());
	}
	
	static public ResultMessage done(SucessMessage messageCode) {
		return new ResultMessage(true, messageCode.Code(), messageCode.Message());
	}
	
	static public ResultMessage done(Object result, MessageCode messageCode) {
		return new ResultMessage(result, messageCode.Code(), messageCode.Message());
	}
	// 실패시 결과값 전송용
	static public ResultMessage fail(Object result) {
		return new ResultMessage(result, SystemMessage.REQUEST_FAIL.Code(), SystemMessage.REQUEST_FAIL.Message());
	}
	static public ResultMessage fail(Object result, FailMessage messageCode) {
		return new ResultMessage(result, messageCode.Code(), messageCode.Message());
	}
	static public ResultMessage fail(MessageCode messageCode) {
		return new ResultMessage(false, messageCode.Code(), messageCode.Message());
	}
	static public ResultMessage fail(Object result, MessageCode messageCode) {
		return new ResultMessage(result, messageCode.Code(), messageCode.Message());
	}
	
	// 코드성 데이터 전송용
	static public ResultMessage messageCode(MessageCode messageCode){
		return new ResultMessage(null, messageCode.Code(), messageCode.Message());
	}
	
	static public ResultMessage message(MessageCode messageCode){
		return new ResultMessage(null, messageCode.Code(), messageCode.Message());
	}
	
	// 사용자 지정용
	static public ResultMessage result(Object result, String resultCode, String resultMessage) {
		return new ResultMessage(result, resultCode, resultMessage);
	}
	// 사용자 지정용
	static public ResultMessage result(Object result, MessageCode messageCode) {
		return new ResultMessage(result, messageCode.Code(), messageCode.Message());
	}
}
