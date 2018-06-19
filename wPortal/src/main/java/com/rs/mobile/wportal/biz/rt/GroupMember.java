package com.rs.mobile.wportal.biz.rt;

import java.io.Serializable;

public class GroupMember implements Serializable{

	private String memberID;

	private String iconImg;

	private String nickName;

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getIconImg() {
		return iconImg;
	}

	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
