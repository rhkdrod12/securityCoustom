package com.example.practicejpa.dto.vo;


import com.example.practicejpa.utils.codeMessage.CrudStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseVo{
	

	
	
	
	@Builder.Default
	CrudStatus status = CrudStatus.READ;
	
	static Class clz = BaseVo.class;
}
