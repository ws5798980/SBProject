package com.rs.mobile.wportal.biz.ht;



public class HotelService {
	public HotelService(String seqID, String serviceDetailName, String ImageURL) {
		super();
		SeqID = seqID;
		ServiceDetailName = serviceDetailName;
		this.ImageURL = ImageURL;
	}
	private String SeqID;
	private String ServiceDetailName;
	private String ImageURL;
	public String getSeqID() {
		return SeqID;
	}
	public void setSeqID(String seqID) {
		SeqID = seqID;
	}
	public String getServiceDetailName() {
		return ServiceDetailName;
	}
	public void setServiceDetailName(String serviceDetailName) {
		ServiceDetailName = serviceDetailName;
	}
	public String getPicUrl() {
		return ImageURL;
	}
	public void setPicUrl(String ImageURL) {
		this.ImageURL = ImageURL;
	}

}
