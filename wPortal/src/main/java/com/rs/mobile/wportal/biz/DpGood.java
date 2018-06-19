package com.rs.mobile.wportal.biz;

public class DpGood {
	public DpGood(String item_code, String img_url, float price, String ea) {
		super();
		this.item_code = item_code;
		this.img_url = img_url;
		this.price = price;
		this.ea = ea;
	}
	private String item_code;
	private String img_url;
	private float  price;
	private String  ea;
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getEa() {
		return ea;
	}
	public void setEa(String ea) {
		this.ea = ea;
	}
	

}
