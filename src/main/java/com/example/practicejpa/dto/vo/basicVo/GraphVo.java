package com.example.practicejpa.dto.vo.basicVo;

import com.example.practicejpa.dto.vo.BaseVo;
import com.example.practicejpa.utils.codeMessage.CrudStatus;
import com.example.practicejpa.utils.other.JSONRefGenerator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 부모, 자식 형태의 데이터를 보낼 때 사용되는 상위 클래스
 * 이 클래스를 상속받아서 사용
 * @param <T>
 */
@Getter
@Setter
@JsonIdentityInfo(generator = JSONRefGenerator.class)
public class GraphVo<T> extends BaseVo {
	// 해당 T의 부모 객체 <-> 여기서 ManyToMany는...?
	T parent;
	// 해당 T의 자식 객체
	List<T> child = new ArrayList<>();
	
	@Builder.Default
	CrudStatus status = CrudStatus.READ;
	
	static Class clz = GraphVo.class;
}
