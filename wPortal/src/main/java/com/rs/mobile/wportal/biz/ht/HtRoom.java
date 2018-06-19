package com.rs.mobile.wportal.biz.ht;

public class HtRoom {
public HtRoom(String roomTypeID, String roomTypeName, float noMemeberPrice, float memeberPrice, String roomTypeImg,
			String remark) {
		super();
		RoomTypeID = roomTypeID;
		RoomTypeName = roomTypeName;
		NoMemeberPrice = noMemeberPrice;
		MemeberPrice = memeberPrice;
		RoomTypeImg = roomTypeImg;
		Remark = remark;
	}
	//	 "RoomTypeID": "371d8385-f78a-4427-ba2f-4ffc5892e1a1",
//     "RoomTypeName": "商务套房",
//     "NoMemeberPrice": 268,
//     "MemeberPrice": 228,
//     "RoomTypeImg": "http://192.168.2.19/wshotel/bad53849-dd06-491d-9ebc-29c9b76cc005.png",
//     "Remark": "",
	 private String RoomTypeID;
	 private String RoomTypeName;
	 private float  NoMemeberPrice;
	 private float  MemeberPrice;
	 private String RoomTypeImg;
	 private String Remark;
	public String getRoomTypeID() {
		return RoomTypeID;
	}
	public void setRoomTypeID(String roomTypeID) {
		RoomTypeID = roomTypeID;
	}
	public String getRoomTypeName() {
		return RoomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		RoomTypeName = roomTypeName;
	}
	public float getNoMemeberPrice() {
		return NoMemeberPrice;
	}
	public void setNoMemeberPrice(float noMemeberPrice) {
		NoMemeberPrice = noMemeberPrice;
	}
	public float getMemeberPrice() {
		return MemeberPrice;
	}
	public void setMemeberPrice(float memeberPrice) {
		MemeberPrice = memeberPrice;
	}
	public String getRoomTypeImg() {
		return RoomTypeImg;
	}
	public void setRoomTypeImg(String roomTypeImg) {
		RoomTypeImg = roomTypeImg;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
}
