package com.example.practicejpa.dao;

import com.example.practicejpa.modal.BaseEntity;
import com.example.practicejpa.modal.QMenu;
import com.example.practicejpa.vo.MenuVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class MenuDao extends BaseJpaEntityDao{
	
	public List<MenuVo> getMenus(String menuType) {
		QMenu menu = QMenu.menu;
		
		
		return null;
	}
	
	public Object[] getColumnArr(Class<? extends BaseEntity> clzz, EntityPathBase object){
		
	
		return null;
	};
	

}
