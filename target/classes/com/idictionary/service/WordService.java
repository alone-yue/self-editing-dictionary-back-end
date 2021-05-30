package com.idictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idictionary.mapper.WordMapper;
import com.idictionary.model.Word;
import com.idictionary.util.Base64Util;

@Service
public class WordService {

	@Autowired
	WordMapper wm;
	
	@Autowired
	BillService bs;
	
	@Transactional
	public int add(Word word) {
		
		return bs.addWord(word.getbId(),true) + wm.add(word);
	}
	
	@Transactional
	public int delete(String bId,String wId) {
		
		return bs.addWord(bId, false) + wm.delete(wId);
	}
}
