package com.idictionary.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Message {

	private String message;
	
	private int code;
	
	private Object data;
	
	private boolean usingMap;//是否使用映射作为返回值

	public String getMessage() {
		return message;
	}

	public Message setMessage(String message) {
		this.message = message;
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public Message setData(Object data) {
		this.data = data;
		this.usingMap = false;
		return this;
	}
	
	public Message setResult(ResultCode result) {
		this.code = result.getCode();
		this.message = result.getMessage();
		return this;
	}
	
	public void init() {
		this.data = null;
		this.code = 0;
		this.message = "";
		this.usingMap = false;
	}
	
	public Message useMap() {
		this.data = new HashMap<String, Object>();
		this.usingMap = true;
		return this;
	}
	
	public Message setDataToMap(String key,Object value) {
		if(!usingMap) {
			return useMap().setDataToMap(key, value);
		}else {
			Map<String, Object>map = (Map<String, Object>) this.data;
			map.put(key, value);
			return this;
		}
	}
}
