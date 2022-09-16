package com.example.practicejpa.utils.codeMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CrudStatus {
	CREATE('C'), READ('R'), UPDATE('U'), DELETE('D');
	
	private char status;
	
	CrudStatus(char status) {
		this.status = status;
	}
	
	@JsonValue
	public char getStatus() {
		return status;
	}
	
	@JsonCreator
	public static CrudStatus toEnum(char value){
		return Arrays.stream(CrudStatus.values()).filter(crudStatus -> crudStatus.status == value).findFirst().orElse(CrudStatus.READ);
	}
	
}
