package com.idictionary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idictionary.model.Dict;

public interface DictMapper {

	List<Dict> searchByPrefix(@Param("eng")String eng);

	List<Dict> searchByCn(@Param("cn")String cn);
}
