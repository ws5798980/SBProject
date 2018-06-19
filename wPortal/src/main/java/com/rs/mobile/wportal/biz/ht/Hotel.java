package com.rs.mobile.wportal.biz.ht;

public class Hotel {
	public Hotel(String hotelinfoid, String hotelname, String district, String photeurl, String hotellevel,
			String hotellev, float nomemeberprice, float total_score, int rated_count) {
		super();
		this.hotelinfoid = hotelinfoid;
		this.hotelname = hotelname;
		this.district = district;
		this.photeurl = photeurl;
		this.hotellevel = hotellevel;
		this.hotellev = hotellev;
		this.nomemeberprice = nomemeberprice;
		this.total_score = total_score;
		this.rated_count = rated_count;
	}

	// "hotelinfoid": "6BA9AD78-536B-4557-9BB3-3EFA9EC20CF0",
	// "hotelname": "宇成朝阳酒店",
	// "district": "芙蓉区",
	// "photeurl":
	// "http://192.168.2.19/wshotel/757f7097-64cb-4b45-8930-9083a18e8fb4.png",
	// "hotellevel": "C843D9E2-8308-4B68-915E-1699B884E44C",
	// "hotellev": "五星级",
	// "nomemeberprice": 186,
	// "total_score": 3.95,
	// "rated_count": 2
	private String hotelinfoid;
	private String hotelname;
	private String district;
	private String photeurl;
	private String hotellevel;
	private String hotellev;
	private float nomemeberprice;
	private float total_score;
	private int rated_count;

	public String getHotelinfoid() {
		return hotelinfoid;
	}

	public void setHotelinfoid(String hotelinfoid) {
		this.hotelinfoid = hotelinfoid;
	}

	public String getHotelname() {
		return hotelname;
	}

	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPhoteurl() {
		return photeurl;
	}

	public void setPhoteurl(String photeurl) {
		this.photeurl = photeurl;
	}

	public String getHotellevel() {
		return hotellevel;
	}

	public void setHotellevel(String hotellevel) {
		this.hotellevel = hotellevel;
	}

	public String getHotellev() {
		return hotellev;
	}

	public void setHotellev(String hotellev) {
		this.hotellev = hotellev;
	}

	public float getNomemeberprice() {
		return nomemeberprice;
	}

	public void setNomemeberprice(float nomemeberprice) {
		this.nomemeberprice = nomemeberprice;
	}

	public float getTotal_score() {
		return total_score;
	}

	public void setTotal_score(float total_score) {
		this.total_score = total_score;
	}

	public int getRated_count() {
		return rated_count;
	}

	public void setRated_count(int rated_count) {
		this.rated_count = rated_count;
	}

}
