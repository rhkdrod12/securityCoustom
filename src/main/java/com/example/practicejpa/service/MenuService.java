package com.example.practicejpa.service;

import com.example.practicejpa.vo.MenuVo;

import java.util.Collection;
import java.util.List;

public interface MenuService {
	
	List<MenuVo> getMenuList(String menuType);
	
	
	void save(MenuVo menuVo);
	
	void save(Collection<MenuVo> menuVos);
	
	void saveOrUpdate(MenuVo menuVo);
	
	void saveOrUpdate(Collection<MenuVo> menuVos);
	
	void saveOrUpdateSee(Collection<MenuVo> menuVos);
	
}
