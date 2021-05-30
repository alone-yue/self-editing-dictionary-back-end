package com.idictionary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idictionary.service.DictService;
import com.idictionary.util.Message;

@RestController
@RequestMapping("dict")
public class DictController {

	@Autowired
	DictService ds;
	
	@Autowired
	Message result;
	
	@RequestMapping("searchByPrefix")
	public Message searchByPrefix(String eng) {
		result.init();
		if(eng == null || eng.length() == 0) {
			return result.setData(null);
		}
		return result.setData(ds.searchByPrefix(eng)).setMessage(eng);
	}
	
	@RequestMapping("searchByCn")
	public Message searchByCh(String cn) {
		result.init();
		
		System.out.println(cn);
		
		if(cn == null || cn.length() == 0) {
			return result.setData(null);
		}
		
		return result.setData(ds.searchByCn(cn)).setMessage(cn);
	}
}
