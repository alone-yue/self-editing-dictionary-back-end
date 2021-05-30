package com.idictionary.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectWriter.GeneratorSettings;
import com.idictionary.model.Bill;
import com.idictionary.service.BillService;
import com.idictionary.util.DateUtil;
import com.idictionary.util.Message;
import com.idictionary.util.ResultCode;
import com.idictionary.util.StringFromatCharge;
import com.idictionary.util.UserVerificationUtil;

@RestController
@RequestMapping("bill")
public class BillController {

	@Value("${bill.numberPerPage}")
	int BILL_NUMBER_PER_PAGE;
	
	@Autowired
	Message result;
	
	@Autowired
	BillService bs;
	
	@RequestMapping("getListByUser")
	public Message getByUser(HttpServletRequest request) {
		result.init();
		
		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			return result.setResult(ResultCode.USER_NOT_LOGIN);
		}
		
		
		return result.setData(bs.getListByUser(UserVerificationUtil.getUserId(request))).setResult(ResultCode.BILL_GET_LIST_SUCCESSFYLLY);
	}
	
	@RequestMapping("getWithWord")
	public Message getWithWord(HttpServletRequest request,String bId) {
		result.init();
		
//		if(!UserVerificationUtil.isUserLoggedIn(request)) {
//			return result.setResult(ResultCode.USER_NOT_LOGIN);
//		}
		
		if(StringFromatCharge.hasNullField(bId)) {
			return result.setResult(ResultCode.USER_NULL_FIELD);
		}
		
		Bill bill = bs.getWithWord(bId);
		
		if(bill == null) {//无效的id
			return result.setResult(ResultCode.BILL_ID_WRONG);
		}
		
		//无权访问
		//该单词集不属于该用户且单词集为私有
		if(bs.countByUser(UserVerificationUtil.getUserId(request), bId) == 0 && bill.isPrivate()) {
			return result.setResult(ResultCode.BILL_PERMITION_DENIED);
		}
		
//		System.out.println(bill.isPrivate());
		
		return result.setResult(ResultCode.BILL_GET_WITH_WORD_SUCCESSFULLY).setData(bill);
	}
	
	@RequestMapping("add")
	public Message add(HttpServletRequest request,Bill bill) {
		result.init();
		
		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			return result.setResult(ResultCode.USER_NOT_LOGIN);
		}
		
		if(StringFromatCharge.hasNullField(bill.getName(),bill.getintroduction())) {
			return result.setResult(ResultCode.USER_NULL_FIELD);
		}
		
		bill.setCreateTime(DateUtil.getCurrDateTime());
		bill.setuId(UserVerificationUtil.getUserId(request));
		
		bs.add(bill);
		
		return result.setResult(ResultCode.BILL_ADD_SUCCESSFULLY).setData(bill);
	}
	
	@RequestMapping("delete")
	public Message delete(HttpServletRequest request,String bId) {
		result.init();

		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			return result.setResult(ResultCode.USER_NOT_LOGIN);
		}
		
		if(StringFromatCharge.hasNullField(bId)) {
			return result.setResult(ResultCode.USER_NULL_FIELD);
		}
		
		if(bs.countByUser(UserVerificationUtil.getUserId(request), bId) == 0) {
			return result.setResult(ResultCode.BILL_ID_WRONG);
		}
		
		bs.delete(bId);
		
		return result.setResult(ResultCode.BILL_DELETE_SUCCESSFULLY);
	}
	
	/**
	 * 	获取词典推荐列表
	 * 	可根据词典名称和作者昵称检索
	 * 	可分页
	 * 	@param page 表示页码
	 * 	@param name 名称模糊匹配
	 * 	@param user	用户模糊匹配
	 * */
	@RequestMapping("getList")
	@Transactional
	public Message getList(Integer page,String name,String user) {
		result.init();
		result.useMap();
		
		page = page == null ? 1 : page;
		StringFromatCharge.nullToBlank(name,user);
		
		//总条数
		int totCount = bs.countPublic(name,user);
		
		if(totCount == 0 || page == 0) {
			return result.setResult(ResultCode.BILL_NULL_LIST);
		}
		
//		System.out.println(name + " " + user + " " + page + " " + totCount + " " + BILL_NUMBER_PER_PAGE);
		
		//总页数
		int pageCount = totCount / BILL_NUMBER_PER_PAGE + (totCount % BILL_NUMBER_PER_PAGE == 0 ? 0 : 1);
		
		if(page > pageCount) {
			return result.setResult(ResultCode.BILL_PAGE_OVERFLOW);
		}
		
		//查询出结果
		List<Bill> list = bs.getList((page - 1) * BILL_NUMBER_PER_PAGE, BILL_NUMBER_PER_PAGE, name, user);
		
		result.setDataToMap("pages", pageCount).setDataToMap("currPage", page).setDataToMap("list", list);
		
		return result.setResult(ResultCode.BILL_GET_LIST_SUCCESSFYLLY);
	}
	
	@RequestMapping("checkPublic")
	public Message checkPublic(HttpServletRequest request,String bId) {
		result.init();
		
		if(bId == null || "".equals(bId)) {
			return result.setData(false);
		}
		
		if(UserVerificationUtil.isUserLoggedIn(request)) {
			//如果用户
			return result.setData(bs.checkPublic(bId, UserVerificationUtil.getUserId(request)));
		}else {
			return result.setData(bs.checkPublic(bId,"0"));
		}
		
	}
	
	@RequestMapping("checkUserBill")
	public Message  checkUserBill(HttpServletRequest request,String bId) {
		result.init();
		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			return result.setResult(ResultCode.USER_NOT_LOGIN);
		}
		
		if(StringFromatCharge.hasNullField(bId)) {
			return result.setResult(ResultCode.USER_NULL_FIELD);
		}
		
		return result.setData(bs.countByUser(UserVerificationUtil.getUserId(request), bId) > 0).setResult(ResultCode.BILL_CHECK_USER_SUCCESSFULLY);
	}
	
	@RequestMapping("publicBill")
	@Transactional
	public Message publicBill(HttpServletRequest request,String bId,boolean isPrivate) {
		
		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			return result.setResult(ResultCode.USER_NOT_LOGIN);
		}
		
		if(StringFromatCharge.hasNullField(bId)) {
			return result.setResult(ResultCode.USER_NULL_FIELD);
		}
		
		if(bs.countByUser(UserVerificationUtil.getUserId(request), bId) == 0) {
			return result.setResult(ResultCode.BILL_ID_WRONG);
		}
		
		Bill bill = bs.getWithWord(bId);
		
		//词典私有性相同，不用设置
		if(isPrivate == bill.isPrivate()) {
			return result.setResult(ResultCode.BILL_THE_SAME_PRIVATE_PROPERTY);
		}
		
		if(isPrivate) {
			bs.setPrivate(bId);
		}else if(bill.getWordCount() <= 10){//判断单词集单词数量，如果单词数量小于10则不允许设置成为公开
			 return result.setResult(ResultCode.BILL_SHORTAGE_IN_WORDS_NUMBER);
		}else {
			bs.setPublic(bId);
		}
		
		return result.setResult(ResultCode.BILL_SWITCH_PRIVATE_SUCCESSFULLY);
	}
	
	@RequestMapping("update")
	public Message update(HttpServletRequest request,String bId,String name,String introduction) {
		result.init();
		
		//校验用户登录
		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			return result.setResult(ResultCode.USER_NOT_LOGIN);
		}
		
		//空字段
		if(StringFromatCharge.hasNullField(bId,name,introduction)) {
			return result.setResult(ResultCode.USER_NULL_FIELD);
		}
		
		//校验用户权限
		if(bs.countByUser(UserVerificationUtil.getUserId(request), bId) == 0) {
			return result.setResult(ResultCode.BILL_ID_WRONG);
		}
		
		bs.update(bId,name,introduction);
		
		return result.setResult(ResultCode.USER_UPDATE_SUCCESSFULLY);
	}
}
