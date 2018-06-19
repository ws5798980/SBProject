package com.rs.mobile.wportal.biz.ht;

public class HtContact {
	public HtContact(String userName, String phonenum) {
		super();
		this.userName = userName;
		Phonenum = phonenum;
	}

	private String userName;
	private String Phonenum;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhonenum() {
		return Phonenum;
	}

	public void setPhonenum(String phonenum) {
		Phonenum = phonenum;
	}

	@Override
	public String toString() {
		return "HtContact [userName=" + userName + ", Phonenum=" + Phonenum + "]";
	}
	
}
