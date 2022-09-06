package com.example.practicejpa.service;

import com.example.practicejpa.dto.vo.MenuVo;

import java.util.Collection;
import java.util.List;

public interface MenuService {
	
	List<MenuVo> getMenuList(String menuType);
	
	List<MenuVo> getAllMenuList();
	
	void save(MenuVo menuVo);
	
	void save(Collection<MenuVo> menuVos);
	
	void saveOrUpdate(MenuVo menuVo);
	
	List<MenuVo> saveOrUpdate(Collection<MenuVo> menuVos);
	
	void saveOrUpdateSee(Collection<MenuVo> menuVos);
	
}
