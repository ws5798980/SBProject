package com.rs.mobile.wportal.biz.ht;

public class City {
	public City(String cityname, String zipCode) {
		super();
		this.cityname = cityname;
		this.zipCode = zipCode;
	}

	// "cityname": "长沙市"
	private String cityname;

	private String zipCode;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

}
