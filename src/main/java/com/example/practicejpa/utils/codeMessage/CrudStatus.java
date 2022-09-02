package com.example.practicejpa.utils.codeMessage;

import lombok.Getter;

@Getter
public enum CrudStatus {
	CREATE('C'), READ('R'), UPDATE('U'), DELETE('D');
	
	private char status;
	
	CrudStatus(char status) {
		this.status = status;
	}
	
}
