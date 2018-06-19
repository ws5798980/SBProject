package com.rs.mobile.wportal.activity.market.util;

import org.json.JSONObject;


public class ListBeen {
	
	private String name ;
	private String code ;
	private String cartOfDetailId ;
	private String unitPrice ;
	private String unitQuantity ;
	private String stockQuantity ;
	private double price ;
	private int quantity ;
	private String imgUrl ;
	private int lastStatus ;//库存不足标记
	
	
	
	public static ListBeen getCarList(JSONObject data) {

		ListBeen carInfo = new ListBeen();
		carInfo.setName(data.optString("name"));
		carInfo.setCode(data.optString("code"));
		carInfo.setPrice(data.optDouble("price"));
		carInfo.setCartOfDetailId(data.optString("cartOfDetailId"));
		carInfo.setQuantity(data.optInt("quantity"));
		carInfo.setUnitPrice(data.optString("unitPrice"));
		carInfo.setUnitQuantity(data.optString("unitQuantity"));
		carInfo.setStockQuantity(data.optString("stockQuantity"));
		carInfo.setImgUrl(data.optString("imgUrl"));
		carInfo.setLastStatus(0);
		return carInfo;
	}

	

	public String getUnitPrice() {
		return unitPrice;
	}



	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}



	public String getUnitQuantity() {
		return unitQuantity;
	}



	public void setUnitQuantity(String unitQuantity) {
		this.unitQuantity = unitQuantity;
	}



	public String getStockQuantity() {
		return stockQuantity;
	}



	public void setStockQuantity(String stockQuantity) {
		this.stockQuantity = stockQuantity;
	}



	public int getLastStatus() {
		return lastStatus;
	}



	public void setLastStatus(int lastStatus) {
		this.lastStatus = lastStatus;
	}



	public String getImgUrl() {
		return imgUrl;
	}



	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCartOfDetailId() {
		return cartOfDetailId;
	}

	public void setCartOfDetailId(String cartOfDetailId) {
		this.cartOfDetailId = cartOfDetailId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
