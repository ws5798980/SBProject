package com.rs.mobile.wportal.activity.market.util;

import org.json.JSONObject;

public class ListGood {
	private String order_num;
	private String item_code;
	private String item_name;
	private String ser_no;
	private String order_q;
	private String order_p;
	private String order_o;
	private String item_image;
	private String refund_status;

	public static ListGood setGood(JSONObject obj) {
		ListGood listGood = new ListGood();
		listGood.setOrder_num(obj.optString("order_num"));
		listGood.setItem_code(obj.optString("item_code"));
		listGood.setItem_name(obj.optString("item_name"));
		listGood.setSer_no(obj.optString("ser_no"));
		listGood.setOrder_q(obj.optString("order_q"));
		listGood.setOrder_p(obj.optString("order_p"));
		listGood.setOrder_o(obj.optString("order_o"));
		listGood.setItem_image(obj.optString("item_image"));
		listGood.setRefund_status(obj.optString("refund_status"));
		return listGood;

	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getSer_no() {
		return ser_no;
	}

	public void setSer_no(String ser_no) {
		this.ser_no = ser_no;
	}

	public String getOrder_q() {
		return order_q;
	}

	public void setOrder_q(String order_q) {
		this.order_q = order_q;
	}

	public String getOrder_p() {
		return order_p;
	}

	public void setOrder_p(String order_p) {
		this.order_p = order_p;
	}

	public String getOrder_o() {
		return order_o;
	}

	public void setOrder_o(String order_o) {
		this.order_o = order_o;
	}

	public String getItem_image() {
		return item_image;
	}

	public void setItem_image(String item_image) {
		this.item_image = item_image;
	}

	public String getRefund_status() {
		return refund_status;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}

}
