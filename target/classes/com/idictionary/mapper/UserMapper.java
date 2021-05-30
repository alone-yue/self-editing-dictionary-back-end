package com.idictionary.mapper;

import org.apache.ibatis.annotations.Param;

import com.idictionary.model.User;


public interface UserMapper {

	User getByPhone(@Param("phone")String phone);

	int addUser(@Param("user")User user);
	
	int count(@Param("field")String field,@Param("value")String value);

	int register(@Param("user")User user,@Param("registerTime") String registerTime);

	int update(@Param("user")User user);
	
	User getById(@Param("uId")String uId);
}
