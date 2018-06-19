package com.rs.mobile.wportal.biz.ht;

public class HtOrder {
public HtOrder(String orderId, String hotelName, String roomTypeID, String roomTypeName, String arriveTime,
			String leaveTime, String roomPrice, String roomCount, String orderStatus, String reserveTime,
			String currentTime, String overTime) {
		super();
		this.orderId = orderId;
		this.hotelName = hotelName;
		this.roomTypeID = roomTypeID;
		this.roomTypeName = roomTypeName;
		this.arriveTime = arriveTime;
		this.leaveTime = leaveTime;
		this.roomPrice = roomPrice;
		this.roomCount = roomCount;
		this.orderStatus = orderStatus;
		this.reserveTime = reserveTime;
		this.currentTime = currentTime;
		this.overTime = overTime;
	}
	//	"orderId": "20170525000000015H",
//    "hotelName": "宇成朝阳酒店",
//    "roomTypeID": "02fbaf5d-3fd4-4cd0-aa70-c2ccc2ef1586",
//    "roomTypeName": "标准双人间",
//    "arriveTime": "2017-5-25",
//    "leaveTime": "2017-5-26",
//    "roomPrice": 148,
//    "roomCount": 1,
//    "orderStatus": "1",
//    "reserveTime": "2017-5-25 20:56:32",
//    "currentTime": "2017-05-26 10:22:55",
//    "overTime": "2017-5-25 21:36:32"
	private String orderId;
	private String hotelName;
	private String roomTypeID;
	private String roomTypeName;
	private String arriveTime;
	private String leaveTime;
	private String roomPrice;
	private String roomCount;
	private String orderStatus;
	private String reserveTime;
	private String currentTime;
	private String overTime;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getRoomTypeID() {
		return roomTypeID;
	}
	public void setRoomTypeID(String roomTypeID) {
		this.roomTypeID = roomTypeID;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getRoomPrice() {
		return roomPrice;
	}
	public void setRoomPrice(String roomPrice) {
		this.roomPrice = roomPrice;
	}
	public String getRoomCount() {
		return roomCount;
	}
	public void setRoomCount(String roomCount) {
		this.roomCount = roomCount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	@Override
	public String toString() {
		return "HtOrder [orderId=" + orderId + ", hotelName=" + hotelName + ", roomTypeID=" + roomTypeID
				+ ", roomTypeName=" + roomTypeName + ", arriveTime=" + arriveTime + ", leaveTime=" + leaveTime
				+ ", roomPrice=" + roomPrice + ", roomCount=" + roomCount + ", orderStatus=" + orderStatus
				+ ", reserveTime=" + reserveTime + ", currentTime=" + currentTime + ", overTime=" + overTime + "]";
	}
	
}
