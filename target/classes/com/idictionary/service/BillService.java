package com.idictionary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idictionary.mapper.BillMapper;
import com.idictionary.model.Bill;

@Service
public class BillService {

	@Autowired
	BillMapper bm;

	public List<Bill> getListByUser(String userId) {
		// TODO Auto-generated method stub
		return bm.getListByUser(userId);
	}
	
	public Bill getWithWord(String bId) {
//		System.out.println(bId);
		return bm.getWithWords(bId);
	}
	
	public int countByUser(String uId,String bId) {
		return bm.countByUser(uId,bId);
	}
	
	@Transactional
	public int addWord(String bId,boolean flag) {
		if(flag) {//添加单词
			bm.addWord(bId);
		}else {//删除单词
			bm.deleteWord(bId);
			Bill bill = bm.getWithWords(bId);
			if(bill.getWordCount() <= 10) {
				bm.privateSwitch(bId, true);
			}
		}
		return 0;
	}

	public int add(Bill bill) {
		// TODO Auto-generated method stub
		int id = bm.add(bill);
		
		System.out.println(id);
		return id;
	}

	public int delete(String bId) {
		// TODO Auto-generated method stub
		return bm.delete(bId);
	}
	
	public List<Bill> getList(int begin,int length,String name,String user){
		return bm.getList(begin, length, name, user);
	}

	public void setPrivate(String bId) {
		// TODO Auto-generated method stub
		bm.privateSwitch(bId, true);
	}

	public void setPublic(String bId) {
		// TODO Auto-generated method stub
		bm.privateSwitch(bId, false);
	}

	public int countPublic(String name, String user) {
		// TODO Auto-generated method stub
		return bm.countPublic(name, user);
	}

	public int update(String bId, String name, String introduction) {
		// TODO Auto-generated method stub
		return bm.update(bId,name,introduction);
	}

	public boolean checkPublic(String bId,String uId) {
		//公开词典校验逻辑：
		//词典公开，可以访问
		//词典非公开，用户拥有，可以访问
		return bm.countPublicById(bId) + bm.countByUser(uId, bId) > 0;
	}
}
