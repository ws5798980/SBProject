package com.rs.mobile.wportal.biz.rt;

import java.io.Serializable;

import com.rs.mobile.common.C;

public class SellerTypeCondition implements Serializable{
	
	private int divCode = 1;//餐厅地区
	private int currentPage;//当前页
	private boolean canLoadMore;//默认可下拉加载
	
	private String categoryCode;//菜系
	
	private String floorCode;//楼层
	
	
	private int holyDay = 2;//只看免预约，默认false，服务器数值为2
	private int takeOut = 2;//节假日可用，默认false，服务器数值为2
	private String timeCode;//用餐时段
	private String setMenuCode;//用餐人数
	private String serviceCode;//餐厅服务
	
	private String searchValue;//搜索关键字
	
	private String smartSearch;//智能排序
	
	private String displayName;
	
	public int getDivCode() {
		return Integer.parseInt(C.DIV_CODE);
	}

	public void setDivCode(int divCode) {
		this.divCode = divCode;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getFloorCode() {
		return floorCode;
	}

	public void setFloorCode(String floorCode) {
		this.floorCode = floorCode;
	}

	public int getHolyDay() {
		return holyDay;
	}

	public void setHolyDay(int holyDay) {
		this.holyDay = holyDay;
	}

	public int getTakeOut() {
		return takeOut;
	}

	public void setTakeOut(int takeOut) {
		this.takeOut = takeOut;
	}

	public String getTimeCode() {
		return timeCode;
	}

	public void setTimeCode(String timeCode) {
		this.timeCode = timeCode;
	}

	public String getSetMenuCode() {
		return setMenuCode;
	}

	public void setSetMenuCode(String setMenuCode) {
		this.setMenuCode = setMenuCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSmartSearch() {
		return smartSearch;
	}

	public void setSmartSearch(String smartSearch) {
		this.smartSearch = smartSearch;
	}
	
	public boolean isCanLoadMore() {
		return canLoadMore;
	}

	public void setCanLoadMore(boolean canLoadMore) {
		this.canLoadMore = canLoadMore;
	}
	
	public void resetPage(){
		currentPage = 1;
		canLoadMore = true;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void resetSearchCondition(){
		resetPage();
		categoryCode = "";
		floorCode = "";
		holyDay = 2;
		takeOut = 2;
		timeCode = "";
		setMenuCode = "";
		serviceCode = "";
		searchValue = "";
		smartSearch = "";
		displayName = "";
	}
	
	public SellerTypeCondition(){
		resetSearchCondition();
	}
	
}
