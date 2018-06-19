package com.rs.mobile.wportal.biz.rt;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.rs.mobile.wportal.biz.paging.BasePageModel;

/**
 * @description 订单List带分页
 * @author ZhaoYun
 * @date 2017-3-20
 */
public class ReservePageModel extends BasePageModel<Reserve> {

	@SerializedName(value="reserveList")
	public List<Reserve> list;

	@Override
	public String toString() {
		return "ReservePageModel [list=" + list + ", currentPage="
				+ currentPage + ", nextPage=" + nextPage + ", totalSize="
				+ totalSize + ", pageSize=" + pageSize + ", canLoadMore="
				+ canLoadMore + "]";
	}

	@Override
	public List<Reserve> getList() {
		return list;
	}

	@Override
	public void setList(List<Reserve> list) {
		this.list = list;
	}
	
	public int getNextPage() {
		
		return nextPage;
		
	}
	
	public int getCurrentPage() {
		
		return currentPage;
		
	}

}
