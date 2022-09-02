package com.example.practicejpa.controller;


import com.example.practicejpa.utils.responseEntity.CommResponse;
import com.example.practicejpa.handler.ChannelHandler;
import com.example.practicejpa.service.MenuService;
import com.example.practicejpa.dto.vo.CodeVo;
import com.example.practicejpa.dto.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	MenuService menuService;
	@Autowired
	ChannelHandler channelHandler;
	@Autowired
	HttpSession httpSession;
	
	@GetMapping("/get")
	public ResponseEntity<?> getMenu(){
		return CommResponse.done(menuService.getMenuList("MT001"));
	}
	
	@GetMapping("/get2")
	public ResponseEntity<?> getMenu2(@RequestParam("menuType") String menuType){
		return CommResponse.done(menuService.getMenuList(menuType));
	}
	
	@GetMapping("/get3")
	public ResponseEntity<?> getMenu3(@RequestParam("menuType") String menuType){
		
		List<MenuVo> menuList = menuService.getMenuList(menuType);
		
		List<CodeVo> collect = menuList.stream().map(item -> CodeVo.builder()
	                                                           .code(item.getMenuId().toString())
	                                                           .codeDepth(item.getMenuDepth())
	                                                           .codeName(item.getName())
	                                                           .upperCode(item.getUpperMenu() != null ? item.getUpperMenu().toString(): null)
	                                                           .build()).collect(Collectors.toList());
		
		collect.forEach(item->{
			Set<CodeVo> set = new LinkedHashSet<>();
			collect.stream().filter(item2->item2.getUpperCode() != null).forEach(item2->{
				if(item.getCode().equals(item2.getUpperCode())){
					set.add(item2);
				}
			});
			item.setChildCodes(set);
		});
		
		return CommResponse.done(collect);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> updateMenu(){
		return CommResponse.done();
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insertMenu(@RequestBody List<MenuVo> menuVos){
		System.out.println(menuVos);
		if (menuVos != null && menuVos.size() > 0) {
			menuService.saveOrUpdate(menuVos);
		}
		
		return CommResponse.done();
	}
	
	@GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<MenuVo> fluxTest(){
		
		//Sinks.Many<MenuVo> many = Sinks.many().multicast().directAllOrNothing();
		System.out.println("ID: " + httpSession.getId());
		System.out.println("sse 진입");
		System.out.println("구독자 수: " + channelHandler.getSink().currentSubscriberCount());
		return channelHandler.asFlux();
	}
	
	@PostMapping(value = "/insertSee")
	public ResponseEntity<?> getSee(@RequestBody List<MenuVo> menuVos){
		System.out.println(menuVos);
		if (menuVos != null && menuVos.size() > 0) {
			menuService.saveOrUpdateSee(menuVos);
		}
		return CommResponse.done();
	}
	
	
	
	
}
