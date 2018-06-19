package com.rs.mobile.wportal.activity.market.util;

import org.json.JSONObject;

public class ListOrder {
	public String rnum;
	public String order_num;
	public String order_date;
	public String custom_id;
	public String item_name_cnt;
	public String order_o;
	public String real_amount;
	public String point_amount;
	public String coupons_amount;
	public String item_image_url;

	public static ListOrder setOrderFood(JSONObject obj) {
		ListOrder order = new ListOrder();
		order.setCoupons_amount(obj.optString("coupons_amount"));
		order.setCustom_id(obj.optString("custom_id"));
		order.setItem_image_url(obj.optString("item_image_url"));
		order.setItem_name_cnt(obj.optString("item_name_cnt"));
		order.setOrder_date(obj.optString("order_date"));
		order.setOrder_num(obj.optString("order_num"));
		order.setOrder_o(obj.optString("order_o"));
		order.setPoint_amount(obj.optString("point_amount"));
		order.setReal_amount(obj.optString("real_amount"));
		order.setRnum(obj.optString("rnum"));
		String[] aStrings = obj.optString("item_name_cnt").split("\\|");

		for (int i = 0; i < aStrings.length; i++) {
			switch (i) {
			case 0:
				order.setItem_name_cnt(aStrings[0]);
				break;
			case 1:
				order.setRnum(aStrings[1]);
				break;

			default:
				break;
			}
		}
		return order;
	}

	public static ListOrder setOrderMarket(JSONObject obj) {
		ListOrder order = new ListOrder();
		order.setCoupons_amount(obj.optString("coupons_amount"));
		order.setCustom_id(obj.optString("custom_id"));
		String[] aStrings = obj.optString("item_name_cnt").split("\\|");

		for (int i = 0; i < aStrings.length; i++) {
			switch (i) {
			case 0:
				order.setItem_name_cnt(aStrings[0]);
				break;
			case 1:
				order.setItem_image_url(aStrings[1]);
				break;
			case 2:
				order.setRnum(aStrings[2]);
				break;
			default:
				break;
			}
		}
		order.setOrder_date(obj.optString("order_date"));
		order.setOrder_num(obj.optString("order_num"));
		order.setOrder_o(obj.optString("order_o"));
		order.setPoint_amount(obj.optString("point_amount"));
		order.setReal_amount(obj.optString("real_amount"));

		return order;
	}

	public String getRnum() {
		return rnum;
	}

	public void setRnum(String rnum) {
		this.rnum = rnum;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getCustom_id() {
		return custom_id;
	}

	public void setCustom_id(String custom_id) {
		this.custom_id = custom_id;
	}

	public String getItem_name_cnt() {
		return item_name_cnt;
	}

	public void setItem_name_cnt(String item_name_cnt) {
		this.item_name_cnt = item_name_cnt;
	}

	public String getOrder_o() {
		return order_o;
	}

	public void setOrder_o(String order_o) {
		this.order_o = order_o;
	}

	public String getReal_amount() {
		return real_amount;
	}

	public void setReal_amount(String real_amount) {
		this.real_amount = real_amount;
	}

	public String getPoint_amount() {
		return point_amount;
	}

	public void setPoint_amount(String point_amount) {
		this.point_amount = point_amount;
	}

	public String getCoupons_amount() {
		return coupons_amount;
	}

	public void setCoupons_amount(String coupons_amount) {
		this.coupons_amount = coupons_amount;
	}

	public String getItem_image_url() {
		return item_image_url;
	}

	public void setItem_image_url(String item_image_url) {
		this.item_image_url = item_image_url;
	}

}
