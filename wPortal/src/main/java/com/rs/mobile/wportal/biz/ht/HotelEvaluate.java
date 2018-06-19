package com.rs.mobile.wportal.biz.ht;

import java.util.List;

public class HotelEvaluate {
	public HotelEvaluate(String ratedid, String nick_name, float total_score, String ratedtime, String roomtypename,
			List<HotelPhoto> imgurl, String context, String parent_rated) {
		super();
		this.ratedid = ratedid;
		this.nick_name = nick_name;
		this.total_score = total_score;
		this.ratedtime = ratedtime;
		this.roomtypename = roomtypename;
		this.imgurl = imgurl;
		this.context = context;
		this.parent_rated = parent_rated;
	}

	private String ratedid;

	public String getRatedid() {
		return ratedid;
	}

	public void setRatedid(String ratedid) {
		this.ratedid = ratedid;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public float getTotal_score() {
		return total_score;
	}

	public void setTotal_score(float total_score) {
		this.total_score = total_score;
	}

	public String getRatedtime() {
		return ratedtime;
	}

	public void setRatedtime(String ratedtime) {
		this.ratedtime = ratedtime;
	}

	public String getRoomtypename() {
		return roomtypename;
	}

	public void setRoomtypename(String roomtypename) {
		this.roomtypename = roomtypename;
	}

	public List<HotelPhoto> getImgurl() {
		return imgurl;
	}

	public void setImgurl(List<HotelPhoto> imgurl) {
		this.imgurl = imgurl;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getParent_rated() {
		return parent_rated;
	}

	public void setParent_rated(String parent_rated) {
		this.parent_rated = parent_rated;
	}

	private String nick_name;
	private float total_score;
	private String ratedtime;
	private String roomtypename;
	private List<HotelPhoto> imgurl;
	private String context;
	private String parent_rated;

}
