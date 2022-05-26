package com.example.practicejpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ResponseDto<T> {
	
	HttpStatus status = HttpStatus.OK;
	
	T message;
	
	public ResponseDto(HttpStatus status) {
		this.status = status;
	}
	
	public ResponseDto(T data) {
		this.message = data;
	}
	
	public ResponseDto(HttpStatus status, T data) {
		this.status  = status;
		this.message = data;
	}
	
	public int getStatus() {
		return status.value();
	}
}
