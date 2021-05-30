package com.idictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idictionary.mapper.TokenMapper;
import com.idictionary.model.Token;
import com.idictionary.util.DateUtil;


@Service
public class TokenService {

	@Autowired
	TokenMapper tm;
	
	@Autowired
	DateUtil date;
	
	public Token getByToken(String token) {
		return tm.getByToken(token);
	}
	
	public int remove(String token) {
		return tm.removeByToken(token);
	}
	
	public int setToken(String token,String userId) {
		Token t = new Token();
		
		t.setToken(token);
		t.setuId(userId);
		t.setCreateTime(date.getCurrentDateTime());
		
		return tm.addToken(t);
	}
}

