package com.example.practicejpa.dto.vo;


import com.example.practicejpa.utils.codeMessage.CrudStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class BaseVo{
	

	
	
	
	@Builder.Default
	CrudStatus status = CrudStatus.READ;
	
	static Class clz = BaseVo.class;
}
