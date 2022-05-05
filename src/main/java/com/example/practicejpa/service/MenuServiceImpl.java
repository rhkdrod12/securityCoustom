package com.example.practicejpa.service;

import com.example.practicejpa.dao.MenuDao;
import com.example.practicejpa.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	MenuDao menuDao;
	
	@Override
	public List<MenuVo> getMenus(String menuType) {
		return menuDao.getMenus(menuType);
	}
	
	@Override
	public void saveOrUpdate(MenuVo menu) {
	
	}
	
	@Override
	public void saveOrUpdate(Collection<MenuVo> menus) {
	
	}
}
