package com.example.practicejpa.dto.vo;

import com.example.practicejpa.utils.other.JSONRefGenerator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = JSONRefGenerator.class)
public class MenuVo extends BaseVo{
	Long menuId;
	String type;
	String category;
	String name;
	String url;
	Long upperMenu;
	int menuDepth;
	int menuOrder;
}
