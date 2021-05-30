package com.idictionary.model;

public class Word {

	private String id;
	
	private String eng;
	
	private String cn;
	
	private String shrt;
	
	private String createTime;
	
	private String bId;
	
	private Bill bill;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEng() {
		return eng;
	}

	public void setEng(String eng) {
		this.eng = eng;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getShrt() {
		return shrt;
	}

	public void setShrt(String shrt) {
		this.shrt = shrt;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
}
