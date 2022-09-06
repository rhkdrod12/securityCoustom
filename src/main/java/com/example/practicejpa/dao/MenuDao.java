package com.example.practicejpa.dao;

import com.example.practicejpa.dto.vo.MenuVo;
import com.example.practicejpa.model.QCodeMgm;
import com.example.practicejpa.model.QMenuMgm;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MenuDao extends BaseJpaEntityDao {
	
	/**
	 * 입력한 메뉴 타입에 해당하는 메뉴 조회
	 * @param menuType 메뉴 타입(헤더인지)
	 * @return
	 */
	public List<MenuVo> getMenuList(String menuType) {
		QMenuMgm menu = QMenuMgm.menuMgm;
		QBean<MenuVo> bean = Projections.bean(MenuVo.class,
		menu.menuId,
		menu.name,
		menu.type,
		menu.category,
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
	
	/**
	 * 모든 메뉴 항목 조회
	 * @return
	 */
	public List<MenuVo> getAllMenuList() {
		QMenuMgm menuMgm = QMenuMgm.menuMgm;
		QCodeMgm codeMgm = QCodeMgm.codeMgm;
		
		
		// 모든 메뉴 항목 가져오기
		JPAQuery<String> jpaSubQuery = jpaQueryFactory.select(codeMgm.code)
		                                              .from(codeMgm)
		                                              .where(codeMgm.upperCode.code.eq("MT000"));
		
		QBean<MenuVo> bean = Projections.bean(MenuVo.class,
		menuMgm.menuId,
		menuMgm.name,
		menuMgm.type,
		menuMgm.category,
		menuMgm.menuDepth,
		menuMgm.menuOrder,
		menuMgm.upperMenu,
		menuMgm.url
		);
		
		return jpaQueryFactory.select(bean)
		                      .from(menuMgm)
		                      .where(menuMgm.type.in(jpaSubQuery))
		                      .fetch();
		
	}
	
	public Map<Long, MenuVo> getMenuMap(String menuType) {
		QMenuMgm menu = QMenuMgm.menuMgm;
		
		QBean<MenuVo> bean = Projections.bean(MenuVo.class,
		menu.menuId,
		menu.name,
		menu.type,
		menu.category,
		menu.menuDepth,
		menu.menuOrder,
		menu.upperMenu,
		menu.url
		);
		
		return jpaQueryFactory.select(bean)
		                      .from(menu)
		                      .where(menu.type.eq(menuType))
		                      .transform(GroupBy.groupBy(menu.menuId).as(bean));
	}
	
}
