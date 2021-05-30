package com.idictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idictionary.mapper.UserMapper;
import com.idictionary.model.User;


@Service
public class UserService {

	@Autowired
	UserMapper um;

	public User getByPhone(String phone) {
		// TODO Auto-generated method stub
		return um.getByPhone(phone);
	}
	
	public int countByPhone(String phone) {
		return um.count("phone", phone);
	}
	
	public int countByNickname(String nickname) {
		return um.count("nickname", nickname);
	}

	public int register(User user, String registerTime) {
		// TODO Auto-generated method stub
		return um.register(user,registerTime);
	}

	public int update(User user) {
		// TODO Auto-generated method stub
		return um.update(user);
	}
	
	public User getById(String id) {
		return um.getById(id);
	}

}
