package com.rs.mobile.wportal.biz.rt;

public class CartItem {
	
	private String imgUrl;
	private String adTitle;
	private String linkURL;
	private String ver;
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getAdTitle() {
		return adTitle;
	}
	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}
	public String getLinkURL() {
		return linkURL;
	}
	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	
	public CartItem(String imgUrl, String adTitle, String linkURL, String ver) {
		this.imgUrl = imgUrl;
		this.adTitle = adTitle;
		this.linkURL = linkURL;
		this.ver = ver;
	}

	public CartItem(){
		
	}

}
