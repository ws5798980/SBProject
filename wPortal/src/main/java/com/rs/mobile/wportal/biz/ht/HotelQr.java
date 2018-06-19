package com.rs.mobile.wportal.biz.ht;

public class HotelQr {
	public HotelQr(String HotelID, String roomno) {
		super();
		this.HotelID = HotelID;
		Roomno = roomno;
	}

	private String HotelID;
	private String Roomno;

	public String getRegisterID() {
		return HotelID;
	}

	public void setRegisterID(String HotelID) {
		this.HotelID = HotelID;
	}

	public String getRoomno() {
		return Roomno;
	}

	public void setRoomno(String roomno) {
		Roomno = roomno;
	}
}
