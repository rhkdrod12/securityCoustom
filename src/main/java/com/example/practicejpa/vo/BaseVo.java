package com.example.practicejpa.vo;


import com.example.practicejpa.utils.CrudStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseVo {
	
	@Builder.Default
	CrudStatus status = CrudStatus.READ;
	
}
