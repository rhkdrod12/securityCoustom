package com.example.practicejpa.utils.responseEntity;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.codeMessage.messageInterface.FailMessage;
import com.example.practicejpa.utils.codeMessage.messageInterface.MessageCode;
import com.example.practicejpa.utils.codeMessage.messageInterface.SucessMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommResponse {
	
	static public ResponseEntity<Object> done(){
		return ResponseEntity.ok(ResultMessage.messageCode(SystemMessage.SUCCUES_REQUEST));
	}
	static public ResponseEntity<Object> done(SucessMessage messageCode){
		return ResponseEntity.ok(ResultMessage.done(true, messageCode));
	}
	static public ResponseEntity<Object> done(Object result){
		return ResponseEntity.ok(ResultMessage.done(result));
	}
	static public ResponseEntity<Object> done(Object result, SucessMessage messageCode){
		return ResponseEntity.ok(ResultMessage.done(result, messageCode));
	}
	static public ResponseEntity<Object> done(Object result, MessageCode messageCode){
		return ResponseEntity.ok(ResultMessage.done(result, messageCode));
	}
	static public ResponseEntity<Object> done(Object result, MessageCode messageCode, HttpStatus httpStatus) {return ResponseEntity.status(httpStatus).body(ResultMessage.done(result, messageCode));}
	
	
	static public ResponseEntity<Object> fail(GlobalException exception){
		return new ResponseEntity<>(ResultMessage.result(false, exception.getCode(), exception.getMessage()), exception.getHttpStatus());
	}
	static public ResponseEntity<Object> fail(){return ResponseEntity.badRequest().body(ResultMessage.fail(false,SystemMessage.REQUEST_FAIL));}
	static public ResponseEntity<Object> fail(MessageCode failMessage){return ResponseEntity.badRequest().body(ResultMessage.fail(false, failMessage));}
	static public ResponseEntity<Object> fail(MessageCode failMessage, HttpStatus httpStatus){return ResponseEntity.status(httpStatus).body(ResultMessage.fail(false, failMessage));}
	static public ResponseEntity<Object> fail(Object result){return ResponseEntity.badRequest().body(ResultMessage.fail(result));}
	static public ResponseEntity<Object> fail(Object result, MessageCode failMessage) {return ResponseEntity.badRequest().body(ResultMessage.fail(result, failMessage));}
	static public ResponseEntity<Object> fail(Object result, MessageCode failMessage, HttpStatus httpStatus) {return ResponseEntity.status(httpStatus).body(ResultMessage.fail(result, failMessage));}
}
