package com.example.practicejpa.service;

import com.example.practicejpa.dto.vo.MenuVo;
import com.example.practicejpa.model.MenuMgm;

import java.util.Collection;
import java.util.List;

public interface MenuService {
	
	List<MenuVo> getMenuList(String menuType);
	
	List<MenuVo> getAllMenuList();
	
	MenuMgm getMenuById(Long id);
	
	void save(MenuVo menuVo);
	
	void save(Collection<MenuVo> menuVos);
	
	void saveOrUpdate(MenuVo menuVo);
	
	List<MenuVo> saveOrUpdate(Collection<MenuVo> menuVos);
	
	void saveOrUpdateSee(Collection<MenuVo> menuVos);
	
}
