package com.idictionary.mapper;

import org.apache.ibatis.annotations.Param;

import com.idictionary.model.Token;


public interface TokenMapper {

	public Token getByToken(@Param("token")String token);
	
	public int removeByUId(@Param("uId")String uId);
	
	public int addToken(@Param("token")Token token);

	public int removeByToken(@Param("token")String token);
}
