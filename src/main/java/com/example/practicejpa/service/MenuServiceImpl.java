package com.example.practicejpa.service;

import com.example.practicejpa.dto.MenuDto;
import com.example.practicejpa.handler.ChannelHandler;
import com.example.practicejpa.modal.Menu;
import com.example.practicejpa.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	MenuDto menuDto;
	@Autowired
	ChannelHandler channelHandler;
	
	@Override
	public List<MenuVo> getMenuList(String menuType) {
		Map<Long, MenuVo> menuMap = menuDto.getMenuMap(menuType);
		List<MenuVo> collect = menuMap.entrySet().stream().map(entry -> {
			if (entry.getValue().getUpperMenu() != null) {
				MenuVo upperMenuVo = menuMap.get(entry.getKey());
				if (upperMenuVo != null) {
					upperMenuVo.getChildMenu().add(entry.getValue());
				}
			}
			return entry.getValue();
		}).collect(Collectors.toList());
		
		return collect;
	}
	
	@Override
	public void save(MenuVo menuVo) {
		menuDto.save(menuVo, Menu.class);
	}
	
	@Override
	public void save(Collection<MenuVo> menuVos) {
		menuDto.save(menuVos, Menu.class);
	}
	
	@Override
	public void saveOrUpdate(MenuVo menuVo) {
		menuDto.saveOrUpdate(menuVo, Menu.class);
	}
	
	@Override
	public void saveOrUpdate(Collection<MenuVo> menuVos) {
		menuDto.saveOrUpdate(menuVos, Menu.class);
	}
	
	@Override
	public void saveOrUpdateSee(Collection<MenuVo> menuVos) {
		
		menuVos.forEach(item->{
			this.saveOrUpdate(item);
			channelHandler.getSink().tryEmitNext(item);
			
			System.out.println("구독자 수: " + channelHandler.getSink().currentSubscriberCount());
		});
		
	}
}
