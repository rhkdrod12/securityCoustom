package com.example.practicejpa.dto.vo;

import com.example.practicejpa.utils.other.JSONRefGenerator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = JSONRefGenerator.class)
public class CodeVo {
	String code;
	String codeName;
	int codeDepth = 0;
	Set<CodeVo> childCodes;
	String upperCode;
	Object data;
}
