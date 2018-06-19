package com.rs.mobile.wportal.biz.rt;

public class SellerItem {
	
	private String thumbnailUrl;
	private String sellerName;
	private float sellerRating;
	private float price;
	private String foodType;
	private float distance;
	private int count;
	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public float getSellerRating() {
		return sellerRating;
	}
	public void setSellerRating(float sellerRating) {
		this.sellerRating = sellerRating;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getFoodType() {
		return foodType;
	}
	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
