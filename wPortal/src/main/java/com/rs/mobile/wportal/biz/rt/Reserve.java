package com.rs.mobile.wportal.biz.rt;

import java.io.Serializable;
import java.util.List;

/**
 * @description 单条订单Item
 * @author ZhaoYun
 * @date 2017-3-20
 */
public class Reserve implements Serializable {

	/**
	 * "reserveStatus":"R",
	 * "shopThumImage":"http://222.240.51.146:8488//wsRestaurant/restaurant/store/JA01/JA01_MAIN.jpg", 
	 * "restaurantName":"海底捞（宇成店）", 
	 * "averageRate":"4",=>float 
	 * "averagePay":"5.6元/人", 
	 * "floor":"8楼",
	 * "distance":"203m", 
	 * "reserveId":"FR20170322142615", 
	 * "reserveDate":"2017-03-22 14:27", 
	 * "personCnt":"5人",
	 * "name":"u", 
	 * "phone":"6", 
	 * "payType":null, => integer , null => invalid
	 * 
	 * "address":"长沙市开福区宇成朝阳广场23-15", 
	 * 
	 * "menu":
	 * [ 
	 * {
	 * "itemCode":"1703000000005", 
	 * "itemName":"香辣鸡腿", 
	 * "quantity":3, 
	 * "amount":90
	 * } 
	 * ]
	 * 
	 * "totalAmount":null, => 双精浮点 , null => invalid 
	 * "actualTotalAmount":null, => 双精浮点 , null => invalid
	 * "date":null,
	 * 
	 * "restaurantCode":"JA01", 
	 * "remainingTime":null, 
	 * 
	 * "couponName":null, 
	 * "couponAmount":null, 
	 * "pointAmount":null, 
	 * 
	 * "ver":"1.0.0", 
	 */

	//详情使用
	/** 订单ID(列表和详情共用) */
	private String reserveId;
	/** 订单状态(列表和详情共用) */
	private String reserveStatus;
	/** 商家名称(列表和详情共用) */
	private String restaurantName;
	/** 商家缩略图 */
	private String shopThumImage;
	/** 商家评级 */
	private float averageRate;
	/** 价格字符串 */
	private String averagePay;
	/** 类型或楼层 */
	private String floor;
	/** 距离 */
	private String distance;
	
	/** 就餐时间(列表和详情共用) */
	private String reserveDate;
	/** 就餐人数 */
	private String personCnt;
	/** 顾客信息：姓名 */
	private String name;
	/** 顾客信息：电话 */
	private String phone;
	/** 支付方式 */
	private String payType;
	
	/** 地址 */
	private String address;
	
	/** 已点菜品详情 */
	private List<Menu> menu;
	
	/** 订单创建金额 */
	private double totalAmount;
	/** 订单应付金额或订单实付金额 */
	private double actualTotalAmount;
	/** 订单创建时间(列表和详情共用) */
	private String date;
	
	/** 优惠券优惠金额 */
    private double couponAmount;
    /** 优惠券名称 */
	private String couponName;
	/** 积分点数 */
	private double pointAmount;
	
	/** 商家ID */
	private String restaurantCode;
	/** 结算剩余时间(长整型) */
	private String remainingTime;
	
	//列表专用
	/** 订单金额 */
	private double amount;
	/** 就餐人数 */
	private int personCount;
	/** 点菜数 */
	private String orderNum;
	/** 菜单详情 */
	private String orderMenu;
	/** 是否为QRCode扫码进入 */
	private int visit;
	
	private String latitude;
	
	private String longitude;
	
	private String groupYN;
	
	private String groupId;
	
	private List<GroupMember> groupList;
	
	private String groupGameYN;
	
	private double realAmount;
	
	private double couponsAmount;
	
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public String getReserveStatus() {
		return reserveStatus;
	}
	public void setReserveStatus(String reserveStatus) {
		this.reserveStatus = reserveStatus;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getShopThumImage() {
		return shopThumImage;
	}
	public void setShopThumImage(String shopThumImage) {
		this.shopThumImage = shopThumImage;
	}
	public float getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(float averageRate) {
		this.averageRate = averageRate;
	}
	public String getAveragePay() {
		return averagePay;
	}
	public void setAveragePay(String averagePay) {
		this.averagePay = averagePay;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public String getPersonCnt() {
		return personCnt;
	}
	public void setPersonCnt(String personCnt) {
		this.personCnt = personCnt;
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
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Menu> getMenu() {
		return menu;
	}
	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getActualTotalAmount() {
		return actualTotalAmount;
	}
	public void setActualTotalAmount(double actualTotalAmount) {
		this.actualTotalAmount = actualTotalAmount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(double couponAmount) {
		this.couponAmount = couponAmount;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public double getPointAmount() {
		return pointAmount;
	}
	public void setPointAmount(double pointAmount) {
		this.pointAmount = pointAmount;
	}
	public String getRestaurantCode() {
		return restaurantCode;
	}
	public void setRestaurantCode(String restaurantCode) {
		this.restaurantCode = restaurantCode;
	}
	public String getRemainingTime() {
		return remainingTime;
	}
	public void setRemainingTime(String remainingTime) {
		this.remainingTime = remainingTime;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getPersonCount() {
		return personCount;
	}
	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderMenu() {
		return orderMenu;
	}
	public void setOrderMenu(String orderMenu) {
		this.orderMenu = orderMenu;
	}
	public int getVisit() {
		return visit;
	}
	public void setVisit(int visit) {
		this.visit = visit;
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
	public String getGroupYN() {
		return groupYN;
	}
	public void setGroupYN(String groupYN) {
		this.groupYN = groupYN;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<GroupMember> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<GroupMember> groupList) {
		this.groupList = groupList;
	}
	public String getGroupGameYN() {
		return groupGameYN;
	}
	public void setGroupGameYN(String groupGameYN) {
		this.groupGameYN = groupGameYN;
	}

	public double getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(double realAmount) {
		this.realAmount = realAmount;
	}
	public double getCouponsAmount() {
		return couponsAmount;
	}
	public void setCouponsAmount(double couponsAmount) {
		this.couponsAmount = couponsAmount;
	}
	@Override
	public String toString() {
		return "Reserve [reserveId=" + reserveId + ", reserveStatus="
				+ reserveStatus + ", restaurantName=" + restaurantName
				+ ", shopThumImage=" + shopThumImage + ", averageRate="
				+ averageRate + ", averagePay=" + averagePay + ", floor="
				+ floor + ", distance=" + distance + ", reserveDate="
				+ reserveDate + ", personCnt=" + personCnt + ", name=" + name
				+ ", phone=" + phone + ", payType=" + payType + ", address="
				+ address + ", menu=" + menu + ", totalAmount=" + totalAmount
				+ ", actualTotalAmount=" + actualTotalAmount + ", date=" + date
				+ ", couponAmount=" + couponAmount + ", couponName="
				+ couponName + ", pointAmount=" + pointAmount
				+ ", restaurantCode=" + restaurantCode + ", remainingTime="
				+ remainingTime + ", amount=" + amount + ", personCount="
				+ personCount + ", orderNum=" + orderNum + ", orderMenu=" + ", groupYN=" 
				+ groupYN + ", groupId=" + groupId + ", groupList=" + groupList + ", groupGameYN=" + groupGameYN + ", realAmount=" + realAmount + ", couponsAmount=" + couponsAmount + "]";
	}
	
}
