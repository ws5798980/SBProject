package com.rs.mobile.wportal.biz.ht;

public class HotelPhoto {
    public HotelPhoto(int imagetype, String imageid, String imgurl) {
		super();
		this.imagetype = imagetype;
		this.imageid = imageid;
		this.imgurl = imgurl;
	}
	private int imagetype;
    private String imageid;
    private String imgurl;
	public int getImagetype() {
		return imagetype;
	}
	public void setImagetype(int imagetype) {
		this.imagetype = imagetype;
	}
	public String getImageid() {
		return imageid;
	}
	public void setImageid(String imageid) {
		this.imageid = imageid;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
}
