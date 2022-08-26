package com.example.practicejpa.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDto {
	Object result;
	String resultCode;
	String resultMessage;
}
