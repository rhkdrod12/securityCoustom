package com.example.practicejpa.service;

import com.example.practicejpa.dao.MenuDao;
import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.handler.ChannelHandler;
import com.example.practicejpa.model.BaseEntity;
import com.example.practicejpa.model.MenuMgm;
import com.example.practicejpa.dao.repository.MenuRepository;
import com.example.practicejpa.dto.vo.MenuVo;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
		List<MenuVo> menuList = menuDao.getMenuList(menuType);
		return menuList;
	}
	
	public MenuMgm getMenuById(Long id){
		return menuRepository.findById(id).orElseThrow(GlobalException::new);
	}
	
	public List<MenuVo> getAllMenuList() {
		return menuDao.getAllMenuList();
	}
	
	@Override
	public void save(MenuVo menuVo) {
		menuDao.save(menuVo, MenuMgm.class);
	}
	
	@Override
	public void save(Collection<MenuVo> menuVos) {
		menuDao.save(menuVos, MenuMgm.class);
	}
	
	@Override
	public void saveOrUpdate(MenuVo menuVo) {
		menuDao.saveOrUpdate(menuVo, MenuMgm.class);
	}
	
	@Override
	public List<MenuVo> saveOrUpdate(Collection<MenuVo> menuVos) {
		List<BaseEntity> baseEntities = menuDao.saveOrUpdate(menuVos, MenuMgm.class);
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
