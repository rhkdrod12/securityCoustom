package com.example.practicejpa.dto;

import com.example.practicejpa.modal.QMenu;
import com.example.practicejpa.vo.MenuVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuDto extends BaseJpaEntityDto {
	
	public List<MenuVo> getMenuList(String menuType) {
		QMenu menu = QMenu.menu;
		
		QBean<MenuVo> bean = Projections.bean(MenuVo.class,
				menu.menuId,
				menu.name,
				menu.type,
				menu.menuDepth,
				menu.menuOrder,
				menu.upperMenu,
				menu.url
		);
		
		return jpaQueryFactory.select(bean)
		                      .from(menu)
		                      .where(menu.type.eq(menuType))
		                      .fetch();
	}
	
}
