package com.idictionary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idictionary.mapper.DictMapper;
import com.idictionary.model.Dict;

@Service
public class DictService {

	@Autowired
	DictMapper dm;
	
	public List<Dict> searchByPrefix(String eng){
		return dm.searchByPrefix(eng);
	}

	public List<Dict> searchByCn(String cn) {
		// TODO Auto-generated method stub
		return dm.searchByCn(cn);
	}
}
