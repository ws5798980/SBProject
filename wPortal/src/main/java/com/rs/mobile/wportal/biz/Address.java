package com.rs.mobile.wportal.biz;

public class Address {
	private String name;
	private String phone;
	private String address;
	private boolean isSleceted;
	private String id;
	private String position;
	private String latitude;
	private String zipCode;
	private boolean hasDelivery;
	public String zipName;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 
	 * @param name
	 * @param phone
	 * @param address
	 * @param isSleceted
	 * @param id
	 * @param position
	 * @param latitude
	 * @param longitude
	 */
	public Address(String name, String phone, String address, boolean isSleceted, String id, String position,
			String latitude, String longitude, String zipName, String zipCode, boolean hasDelivery) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.isSleceted = isSleceted;
		this.id = id;
		this.position = position;
		this.latitude = latitude;
		this.longitude = longitude;
		this.hasDelivery = hasDelivery;
		this.zipName = zipName;
		this.zipCode = zipCode;
	}

	public boolean isHasDelivery() {
		return hasDelivery;
	}

	public void setHasDelivery(boolean hasDelivery) {
		this.hasDelivery = hasDelivery;
	}

	private String longitude;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isSleceted() {
		return isSleceted;
	}

	public void setSleceted(boolean isSleceted) {
		this.isSleceted = isSleceted;
	}

}
