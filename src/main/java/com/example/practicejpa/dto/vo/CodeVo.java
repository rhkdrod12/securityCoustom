package com.example.practicejpa.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeVo {
	String code;
	String codeName;
	int codeDepth = 0;
	Set<CodeVo> childCodes;
	String upperCode;
}
