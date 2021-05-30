package com.idictionary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idictionary.model.Bill;

public interface BillMapper {

	//获取用户所有单词集
	List<Bill> getListByUser(@Param("uId")String userId);

	//根据id获取单词集，（携带单词）
	Bill getWithWords(@Param("bId")String bId);

	//根据用户id和单词集id查询数量（用于判别）
	int countByUser(@Param("uId")String uId, @Param("bId")String bId);
	
	//公开词典计数
	int countPublic(@Param("name")String name,@Param("user")String user);
	
	//添加单词计数
	int addWord(@Param("bId")String bId);
	
	//删除单词计数
	int deleteWord(@Param("bId")String bId);
	
	//添加单词集
	int add(@Param("bill")Bill bill);
	
	//删除单词集
	int delete(@Param("bId")String bId);

	//获取首页推荐单词集
	List<Bill> getList(@Param("begin")int begin,@Param("length")int length,@Param("name")String name,@Param("user")String user);
	
	//切换单词集的私有属性
	int privateSwitch(@Param("bId")String bId,@Param("private")boolean privation);

	int update(@Param("id")String bId,@Param("name") String name,@Param("introduction") String introduction);

	int countPublicById(@Param("id")String bId);
}
