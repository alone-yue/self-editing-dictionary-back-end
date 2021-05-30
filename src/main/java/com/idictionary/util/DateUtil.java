package com.idictionary.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	public static String getCurrDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	}
	
	public static String getCurrTime() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));	
	}
	
	public static String getCurrDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
	}
	
	
	private Date current;
	private SimpleDateFormat date;
	private SimpleDateFormat time;
	private SimpleDateFormat datetime;
	
	public DateUtil() {
		// TODO Auto-generated constructor stub
		current = new Date(System.currentTimeMillis());
		date = new SimpleDateFormat("yyyy-MM-dd");
		time = new SimpleDateFormat("HH:mm:ss");
		datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	private void refresh() {
		current.setTime(System.currentTimeMillis());
	}
	
	public String getCurrentDateTime() {
		refresh();
		return datetime.format(current);
	}
	
	public String getCurrentDate() {
		refresh();
		return date.format(current);
	}
	
	public String getCurrentTime() {
		refresh();
		return time.format(current);
	}
	
	public String getCurrentDateTime(long curr) {
		setCurrent(curr);
		return getCurrentDateTime();
	}
	
	public String getCurrentDate(long curr) {
		setCurrent(curr);
		return getCurrentDate();
	}
	
	public String getCurrentTime(long curr) {
		setCurrent(curr);
		return getCurrentTime();
	}
	
	public void setCurrent(long curr) {
		current.setTime(curr);
	}
}
