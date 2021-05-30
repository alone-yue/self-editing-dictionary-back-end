package com.idictionary.controller;

import java.util.Base64;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.sql.visitor.functions.If;
import com.idictionary.model.User;
import com.idictionary.service.TokenService;
import com.idictionary.service.UserService;
import com.idictionary.util.Base64Util;
import com.idictionary.util.DateUtil;
import com.idictionary.util.Message;
import com.idictionary.util.ResultCode;
import com.idictionary.util.StringFromatCharge;
import com.idictionary.util.UserVerificationUtil;



@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService us;
	
	@Autowired
	Message result;
	
	@Autowired
	TokenService ts;
	
	@Autowired
	DateUtil date;
	
	@RequestMapping("login")
	public Message login(HttpServletRequest request,String phone,String password) {
		result.init();
		
		if(UserVerificationUtil.isUserLoggedIn(request)) {
			result.setResult(ResultCode.USER_LOGGED_IN);
			return result;
		}
		
		if(phone == null || "".equals(phone) || password == null || "".equals(password)) {
			result.setResult(ResultCode.USER_NULL_FIELD);
			return result;
		}
		
		User user = us.getByPhone(phone);
		
		if(user == null) {
			result.setResult(ResultCode.USER_DO_NOT_EXSIT);
			return result;
		}
		
		if(!password.equals(Base64Util.decode(user.getPassword()))) {
			result.setResult(ResultCode.USER_PASSWORD_WRONG);
			return result;
		}
		
		String token = UUID.randomUUID().toString();
		
		ts.setToken(token, user.getId());
		
		result.setData(token);
		result.setResult(ResultCode.USER_LOGIN_SUCCESSFULLY);
		return result;
	}
	
	
	
	@RequestMapping("logout")
	public Message logout(HttpServletRequest request) {
		result.init();
		
		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			result.setResult(ResultCode.USER_DOES_NOT_LOGGED_IN);
			return result;
		}
		
		String userId = UserVerificationUtil.getUserId(request);
		
		ts.remove(UserVerificationUtil.getUserToken(request));
		
		result.setResult(ResultCode.USER_LOGOUT_SUCCESSFULLY);
		return result;
	}
	//��ȡ��¼״̬
	@RequestMapping("loginStatus")
	public Message loginState(HttpServletRequest request) {
		result.init();
		
		result.setResult(ResultCode.USER_FETCH_LOGIN_INFO_SUCCESSFULLY);
		result.setData(UserVerificationUtil.getUserState(request));
		
		return result;
		
	}
	
	@RequestMapping("register")
	public Message register(HttpServletRequest request,String phone,String nickname,String password) {
		result.init();
		
		if(UserVerificationUtil.isUserLoggedIn(request)) {
			result.setResult(ResultCode.USER_LOGGED_IN);
			return result;
		}
		
		if(phone == null || "".equals(phone) || nickname == null || "".equals(nickname) || password == null || "".equals(password)) {
			result.setResult(ResultCode.USER_NULL_FIELD);
			return result;
		}
		
		if(!StringFromatCharge.isLegalPhoneNumber(phone)) {
			result.setResult(ResultCode.USER_PHONE_FORMAT_WRONG);
			return result;
		}
		
		System.out.println(us.countByNickname(nickname) + " " + nickname);
		if(us.countByNickname(nickname) != 0) {
			result.setResult(ResultCode.USER_NICKNAME_EXSITED);
			return result;
		}
		
		System.out.println(us.countByPhone(phone) + " " + phone);
		if(us.countByPhone(phone) != 0) {
			result.setResult(ResultCode.USER_PHONE_EXSITED);
			return result;
		}
		
		User user = new User();
		
		user.setNickname(nickname);
		user.setPhone(phone);
		user.setPassword(Base64Util.encode(password));
		user.setIcon(Math.floor(Math.random() * 255) + "," + Math.floor(Math.random() * 255) + "," + Math.floor(Math.random() * 255));
		
		us.register(user,date.getCurrentDateTime());
		result.setResult(ResultCode.USER_REGISTER_SUCCESSFULLY);
		return result;
	}
	
	@RequestMapping("profile")
	public Message profile(HttpServletRequest request,String phone,String nickname,String password) {
		result.init();
		
		if(nickname == null || "".equals(nickname)) {
			result.setResult(ResultCode.USER_NULL_FIELD);
			return result;
		}
		
		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			result.setResult(ResultCode.USER_NOT_LOGIN);
			return result;
		}
		User user = us.getById(UserVerificationUtil.getUserId(request));
		
		if(us.countByNickname(nickname) != 0 && !user.getNickname().equals(nickname)) {
			result.setResult(ResultCode.USER_NICKNAME_EXSITED);
			return result;
		}
		
		
		
		user.setNickname(nickname);
		user.setPassword((password == null || "".equals(password)) ? user.getPassword() : Base64Util.encode(password));
		
		us.update(user);
		result.setResult(ResultCode.USER_UPDATE_SUCCESSFULLY);
		return result;
	}
	
	@RequestMapping("loginInfo")
	public Message loginInfo(HttpServletRequest request) {
		result.init();
		
		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			result.setResult(ResultCode.USER_NOT_LOGIN);
			return result;
		}
		User user = us.getById(UserVerificationUtil.getUserId(request));
		
		result.setResult(ResultCode.USER_GET_LOGIN_INFO_SUCCESSFULLY);
		user.setPassword(null);
		result.setData(user);
		return result;
	}
	
	@RequestMapping("update")
	public Message update(HttpServletRequest request,String nickname,String icon,String password) {
		result.init();

		if(!UserVerificationUtil.isUserLoggedIn(request)) {
			return result.setResult(ResultCode.USER_NOT_LOGIN);
		}
		
		if (StringFromatCharge.hasNullField(nickname,icon)) {
			return result.setResult(ResultCode.USER_NULL_FIELD);
		}
		
		User user = us.getById(UserVerificationUtil.getUserId(request));
		
		if(!user.getNickname().equals(nickname) && us.countByNickname(nickname) != 0) {
			return result.setResult(ResultCode.USER_NICKNAME_EXSITED);
		}
		
		user.setNickname(nickname);
		user.setIcon(icon);
		if(!"".equals(password)) {
			user.setPassword(Base64Util.encode(password));
		}
		
		us.update(user);
		
		return result.setResult(ResultCode.USER_UPDATE_SUCCESSFULLY);
	}
}
