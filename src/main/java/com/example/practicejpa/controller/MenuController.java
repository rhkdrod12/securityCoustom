package com.example.practicejpa.controller;


import com.example.practicejpa.dto.vo.CodeVo;
import com.example.practicejpa.dto.vo.MenuVo;
import com.example.practicejpa.handler.ChannelHandler;
import com.example.practicejpa.handler.SinksHandler;
import com.example.practicejpa.service.MenuService;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.responseEntity.CommResponse;
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
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpSession;
import java.time.Duration;
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
	SinksHandler sinksHandler;
	@Autowired
	HttpSession httpSession;
	
	@GetMapping("/get")
	public ResponseEntity<?> getMenu() {
		return CommResponse.done(menuService.getMenuList("MT001"));
	}
	
	@GetMapping("/get2")
	public ResponseEntity<?> getMenu2(@RequestParam("menuType") String menuType) {
		return CommResponse.done(menuService.getMenuList(menuType));
	}
	
	@GetMapping("/getMenu")
	public ResponseEntity<?> getMenu(@RequestParam("menuId") long menuId) {
		return CommResponse.done(menuService.getMenuById(menuId));
	}
	
	
	@GetMapping("/get3")
	public ResponseEntity<?> getMenu3(@RequestParam("menuType") String menuType) {
		
		List<MenuVo> menuList = menuService.getMenuList(menuType);
		
		List<CodeVo> collect = menuList.stream().map(item -> CodeVo.builder()
		                                                           .code(item.getMenuId().toString())
		                                                           .codeDepth(item.getMenuDepth())
		                                                           .codeName(item.getName())
		                                                           .upperCode(item.getUpperMenu() != null ? item.getUpperMenu().toString() : null)
		                                                           .data(item)
		                                                           .build()).collect(Collectors.toList());
		
		class NumberCompare {
			int value = Integer.MAX_VALUE;
			
			public int getValue() {
				return value;
			}
			
			public void setValue(int value) {
				this.value = Integer.min(this.value, value);
			}
		}
		
		
		collect.forEach(item -> {
			Set<CodeVo> set = new LinkedHashSet<>();
			collect.stream().filter(item2 -> item2.getUpperCode() != null).forEach(item2 -> {
				if (item.getCode().equals(item2.getUpperCode())) {
					set.add(item2);
				}
			});
			item.setChildCodes(set);
		});
		
		
		return CommResponse.done(collect);
	}
	
	@GetMapping("/getAllMenu")
	public ResponseEntity<?> getAllMenu() {
		List<MenuVo> menuList = menuService.getAllMenuList();
		return CommResponse.done(menuList);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> updateMenu() {
		return CommResponse.done();
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insertMenu(@RequestBody List<MenuVo> menuVos) {
		System.out.println(menuVos);
		if (menuVos != null && menuVos.size() > 0) {
			menuService.saveOrUpdate(menuVos);
		}
		
		return CommResponse.done();
	}
	
	@GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseEntity> fluxTest() {
		//Sinks.Many<MenuVo> many = Sinks.many().multicast().directAllOrNothing();
		System.out.println("ID: " + httpSession.getId());
		System.out.println("sse 진입");
		System.out.println("구독자 수: " + sinksHandler.getSink().currentSubscriberCount());
		return sinksHandler.asFlux()
		                   // 여러 개를 한꺼번에 보내는 경우 배압을 걸어줘야 문제없이 처리됨
		                   .onBackpressureBuffer(99, (result) -> System.out.printf("%s overflow%n", result.getBody()), BufferOverflowStrategy.DROP_LATEST)
		                   // .onErrorContinue((throwable, o) -> {
		                   //     System.out.println("에러 발생");
		                   //     System.out.println(throwable);
		                   //     System.out.println(o);
		                   // })
		                   // .timeout(Duration.ofSeconds(5))
		                   .onErrorReturn(CommResponse.fail(SystemMessage.ERROR_REQUEST_FAIL))
		// .onErrorMap(throwable -> {
		//     throw new GlobalException("에러에러에러");
		// })
		;
		// .delayElements(Duration.ofSeconds(3));
	}
	
	@PostMapping(value = "/insertSee")
	public ResponseEntity<?> getSee(@RequestBody List<MenuVo> menuVos) {
		System.out.println(menuVos);
		if (menuVos != null && menuVos.size() > 0) {
			menuService.saveOrUpdateSee(menuVos);
		}
		return CommResponse.done();
	}
	
}
