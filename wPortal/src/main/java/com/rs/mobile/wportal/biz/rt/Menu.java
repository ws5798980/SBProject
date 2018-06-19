package com.rs.mobile.wportal.biz.rt;

import java.io.Serializable;

public class Menu implements Serializable{
	
	/**
	 "itemCode":"1703000000005",
     "itemName":"香辣鸡腿",
     "quantity":3,
     "amount":90
     */
	
	/** 菜品ID */
	private String itemCode;
	/** 菜品名字 */
	private String itemName;
	/** 点菜份数 */
	private int quantity;
	/** 该菜品应支付金额 */
	private float amount;
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}
