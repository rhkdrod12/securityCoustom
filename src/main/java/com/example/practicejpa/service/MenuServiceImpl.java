package com.example.practicejpa.service;

import com.example.practicejpa.dao.MenuDao;
import com.example.practicejpa.handler.ChannelHandler;
import com.example.practicejpa.modal.BaseEntity;
import com.example.practicejpa.modal.Menu;
import com.example.practicejpa.repository.MenuRepository;
import com.example.practicejpa.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	MenuDao menuDao;
	@Autowired
	MenuRepository menuRepository;
	@Autowired
	ChannelHandler channelHandler;
	
	@Override
	public List<MenuVo> getMenuList(String menuType) {
		return menuDao.getMenuList(menuType);
	}
	
	@Override
	public void save(MenuVo menuVo) {
		menuDao.save(menuVo, Menu.class);
	}
	
	@Override
	public void save(Collection<MenuVo> menuVos) {
		menuDao.save(menuVos, Menu.class);
	}
	
	@Override
	public void saveOrUpdate(MenuVo menuVo) {
		menuDao.saveOrUpdate(menuVo, Menu.class);
	}
	
	@Override
	public List<MenuVo> saveOrUpdate(Collection<MenuVo> menuVos) {
		List<BaseEntity> baseEntities = menuDao.saveOrUpdate(menuVos, Menu.class);
		menuDao.flush();
		return baseEntities.stream().map(entity -> menuDao.convertVo(MenuVo.class, entity)).collect(Collectors.toList());
	}
	
	@Override
	public void saveOrUpdateSee(Collection<MenuVo> menuVos) {
		List<MenuVo> menuVos1 = this.saveOrUpdate(menuVos);
		menuVos1.forEach(item ->{
			channelHandler.getSink().tryEmitNext(item);
		});
		System.out.println("구독자 수: " + channelHandler.getSink().currentSubscriberCount());
	}
}
