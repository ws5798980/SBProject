package com.rs.mobile.wportal.biz.rt;

import java.util.List;

public class HomePage {
	
	private List<SellerType> sellerTypes;
	
	private List<SellerAD> sellerADs;
	
	private List<SellerItem> sellerItems;

	public List<SellerType> getSellerTypes() {
		return sellerTypes;
	}

	public void setSellerTypes(List<SellerType> sellerTypes) {
		this.sellerTypes = sellerTypes;
	}

	public List<SellerAD> getSellerADs() {
		return sellerADs;
	}

	public void setSellerADs(List<SellerAD> sellerADs) {
		this.sellerADs = sellerADs;
	}

	public List<SellerItem> getSellerItems() {
		return sellerItems;
	}

	public void setSellerItems(List<SellerItem> sellerItems) {
		this.sellerItems = sellerItems;
	}
	
}
