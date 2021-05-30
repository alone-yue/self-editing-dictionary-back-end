package com.idictionary.mapper;

import org.apache.ibatis.annotations.Param;

import com.idictionary.model.Word;

public interface WordMapper {

	int add(@Param("word")Word word);
	
	int delete(@Param("wId")String wId);
}
