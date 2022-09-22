package com.example.practicejpa.dto.vo;

import com.example.practicejpa.dto.vo.basicVo.GraphVo;
import com.example.practicejpa.utils.other.JSONRefGenerator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = JSONRefGenerator.class)
public class CodeVo2 extends GraphVo<CodeVo2> {
	String code;
	String codeName;
	int codeDepth = 0;
}
