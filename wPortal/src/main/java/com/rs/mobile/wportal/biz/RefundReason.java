package com.rs.mobile.wportal.biz;

public class RefundReason {
	public RefundReason(String code_name, String sub_code) {
		super();
		this.code_name = code_name;
		this.sub_code = sub_code;
	}

	private String code_name;

	public String getCode_name() {
		return code_name;
	}

	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}

	public String getSub_code() {
		return sub_code;
	}

	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}

	private String sub_code;
}
