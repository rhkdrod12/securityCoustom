package com.example.practicejpa.service;

import com.example.practicejpa.vo.MenuVo;

import java.util.Collection;
import java.util.List;

public interface MenuService {
	
	List<MenuVo> getMenus(String menuType);
	
	void saveOrUpdate(MenuVo menu);
	
	void saveOrUpdate(Collection<MenuVo> menus);
	
}
