package com.idictionary.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idictionary.model.Word;
import com.idictionary.service.BillService;
import com.idictionary.service.WordService;
import com.idictionary.util.DateUtil;
import com.idictionary.util.Message;
import com.idictionary.util.ResultCode;
import com.idictionary.util.StringFromatCharge;
import com.idictionary.util.UserVerificationUtil;
import com.mysql.cj.exceptions.RSAException;

@RestController
@RequestMapping("word")
public class WordController {

	@Autowired
	Message result;
	
	@Autowired
	WordService ws;
	
	@Autowired
	BillService bs;
	
	@RequestMapping("add")
	public Message add(HttpServletRequest request,Word word) {
		result.init();
		
		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			return result.setResult(ResultCode.USER_NOT_LOGIN);
		}
		
		if(StringFromatCharge.hasNullField(word.getEng(),word.getCn(),word.getShrt(),word.getbId())) {
			return result.setResult(ResultCode.USER_NULL_FIELD);
		}
		
		if(bs.countByUser(UserVerificationUtil.getUserId(request), word.getbId()) == 0) {
			return result.setResult(ResultCode.BILL_ID_WRONG);
		}
		
		word.setCreateTime(DateUtil.getCurrDateTime());
		ws.add(word);
		
		return result.setResult(ResultCode.WORD_ADD_SUCCESSFULLY);
	}
	
	@RequestMapping("delete")
	public Message delete(HttpServletRequest request,String bId,String wId) {
		result.init();
		
		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			return result.setResult(ResultCode.USER_NOT_LOGIN);
		}
		
		if(StringFromatCharge.hasNullField(bId,wId)) {
			return result.setResult(ResultCode.USER_NULL_FIELD);
		}
		
		if(bs.countByUser(UserVerificationUtil.getUserId(request), bId) == 0) {
			return result.setResult(ResultCode.BILL_ID_WRONG);
		}
		
		ws.delete(bId, wId);
		return result.setResult(ResultCode.WORD_DELETE_SUCCESSFULLY);
	}
}
