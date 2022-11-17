package com.example.practicejpa.jwtSecurity;


import com.example.practicejpa.jwtSecurity.authentication.JwtSecurityFilterProvider;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class JwtSecurityConfiguration {
	
	// JWT 인증, 인가시 사용할 필터
	List<JwtSecurityFilterProvider> jwtFilterList = new ArrayList<>();
	
	public JwtSecurityConfiguration addJwtFilter(JwtSecurityFilterProvider filterProvider){
		
		// order 정보가 없는 경우 맨 마지막으로 지정
		if(filterProvider.getFilterOrder() == Integer.MIN_VALUE){
			filterProvider.setFilterOrder(jwtFilterList.size() + 1);
		}
		
		// 필터 추가
		jwtFilterList.add(filterProvider);
		// 필터 order에 따른 재정렬
		jwtFilterList.sort(Comparator.comparingInt(JwtSecurityFilterProvider::getFilterOrder));
		
		return this;
	}
	
	public JwtSecurityConfiguration addJwtFilter(JwtSecurityFilterProvider filterProvider, int order){
		
		if (order > jwtFilterList.size()) {
			order = jwtFilterList.size() + 1;
		}else if(order < 1){
			order = 1;
		}
		
		// 정렬순서 추가
		filterProvider.setFilterOrder(order);
		
		for (JwtSecurityFilterProvider jwtSecurityFilterProvider : jwtFilterList) {
			if(jwtSecurityFilterProvider.getFilterOrder() >= order){
				jwtSecurityFilterProvider.setFilterOrder(jwtSecurityFilterProvider.getFilterOrder() + 1);
			}
		}
		
		// 필터 추가
		jwtFilterList.add(filterProvider);
		// 필터 order에 따른 재정렬
		jwtFilterList.sort(Comparator.comparingInt(JwtSecurityFilterProvider::getFilterOrder));
		
		return this;
	}
	
	public JwtSecurityFilterProvider[] getJwtFilters() {
		JwtSecurityFilterProvider[] filters = new JwtSecurityFilterProvider[jwtFilterList.size()];
		for (int i = 0; i < jwtFilterList.size(); i++) {
			filters[i] = jwtFilterList.get(i);
		}
		return filters;
	}
}
