package com.idictionary.util;

public enum ResultCode {
	
	SUBJECT_SEARCH_NO_TITLE(400,"题目不能为空"),
	
	SUBJECT_SEARCH_SUCCESSFULLY(200,"题目查询成功"),
	
	USER_NULL_FIELD(400,"空字段异常"),
	
	USER_LOGIN_SUCCESSFULLY(200,"登录成功"),
	
	USER_LOGGED_IN(400,"用户已经登录，请不要重复登录"),
	
	USER_DO_NOT_EXSIT(400,"用户不存在"),
	
	USER_PASSWORD_WRONG(400,"账号或密码错误"),
	
	USER_DOES_NOT_LOGGED_IN(400,"用户未登录"),
	
	USER_LOGOUT_SUCCESSFULLY(200,"登出成功"),
	
	USER_FETCH_LOGIN_INFO_SUCCESSFULLY(200,"获取用户登录信息成功"),
	
	USER_NICKNAME_EXSITED(400,"昵称已被占用"),
	
	USER_PHONE_EXSITED(400,"电话已被占用"),
	
	USER_REGISTER_SUCCESSFULLY(200,"用户注册成功"),
	
	USER_PHONE_FORMAT_WRONG(400,"电话格式错误"),
	
	USER_NOT_LOGIN(400,"用户未登录"),
	
	USER_UPDATE_SUCCESSFULLY(200,"用户信息修改成功"),
	
	USER_GET_LOGIN_INFO_SUCCESSFULLY(200,"获取用户登录信息成功"),
	
	BILL_GET_LIST_SUCCESSFYLLY(200,"获取用户单词列表成功"),
	
	BILL_ID_WRONG(400,"单词集id错误"),
	
	BILL_GET_WITH_WORD_SUCCESSFULLY(200,"获取单词集成功"),
	
	BILL_ADD_SUCCESSFULLY(200,"单词集添加成功"),
	
	BILL_DELETE_SUCCESSFULLY(200,"单词集删除成功"),
	
	BILL_CHECK_USER_SUCCESSFULLY(200,"查询用户权限成功"),
	
	BILL_THE_SAME_PRIVATE_PROPERTY(400,"单词集私有性相同，请勿重复设置"),
	
	BILL_SHORTAGE_IN_WORDS_NUMBER(400,"单词数量不足，不能设置为公开"),
	
	BILL_NULL_LIST(400,"词典数量为零"),
	
	BILL_SWITCH_PRIVATE_SUCCESSFULLY(200,"单词集私有性切换成功"),
	
	BILL_PERMITION_DENIED(400,"无权查看该提单"),
	
	BILL_PAGE_OVERFLOW(400,"页码溢出"),
	
	BILL_UPDATE_SUCCESSFULLY(200,"词典信息修改成功"),
	
	WORD_ADD_SUCCESSFULLY(200,"单词添加成功"),
	
	WORD_DELETE_SUCCESSFULLY(200,"单词删除成功"),
	
	;
	
	ResultCode(int code,String message){
		this.message = message;
		this.code = code;
	}
	
	private String message;

	private int code;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
