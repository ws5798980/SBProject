package com.rs.mobile.wportal.biz.basic;

import java.io.Serializable;

public abstract class BaseModel<DATA> implements Serializable{
	
	public int status;
	public String msg;
	public DATA data;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public DATA getData() {
		return data;
	}
	public void setData(DATA data) {
		this.data = data;
	}
	
}
