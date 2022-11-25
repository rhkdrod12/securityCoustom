package com.example.practicejpa.dao;

import com.example.practicejpa.dao.repository.UserRepository;
import com.example.practicejpa.model.User;
import com.example.practicejpa.utils.other.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDao extends BaseJpaEntityDao {
	
	@Autowired
	UserRepository userRepository;
	
	public Optional<User> findUserId(String userId){
		return userRepository.findByUserId(userId);
	}
	
	public boolean existByUserId(String userId) {
		return userRepository.existsByUserId(userId);
	}
	
	public boolean matchPassword(String userId, String userPw) {
		return ParamUtils.isNotEmpty(userPw) && userPw.equals(userRepository.findUserPwByUserId(userId));
	}

}
