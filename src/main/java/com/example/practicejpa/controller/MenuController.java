package com.example.practicejpa.controller;


import com.example.practicejpa.dto.ResponseDto;
import com.example.practicejpa.modal.Menu;
import com.example.practicejpa.vo.MenuVo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@GetMapping("/get")
	public ResponseDto<?> getMenu(){
		
		List<MenuVo> menuList = new ArrayList<>();
		menuList.add(MenuVo.builder().menuId(1L).name("사용자 정보").type("HEADER").build());
		menuList.add(MenuVo.builder().menuId(1L).name("일반인 정보").type("HEADER").build());
		menuList.add(MenuVo.builder().menuId(1L).name("관리자 정보").type("HEADER").build());
		return new ResponseDto<>(menuList);
	}
	
	@GetMapping("/get2")
	public ResponseDto<?> getMenu2(@RequestParam("menuType") String menuType){
		
		System.out.println(menuType);
		return new ResponseDto<>(MenuVo.builder().menuId(1L).name("사용자 정보").type("HEADER").build());
	}
	
	
	@PutMapping("/insert")
	public ResponseDto<?> insertMenu(){
		return new ResponseDto<>();
	}

}
