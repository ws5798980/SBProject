package com.rs.mobile.wportal.biz.rt;

import java.util.List;

import com.rs.mobile.wportal.biz.paging.Pager;

/**
 * @description 订单List带分页
 * @author ZhaoYun
 * @date 2017-3-20
 */
public class ReserveList extends Pager{

	private List<Reserve> reserveList;

	public List<Reserve> getReserveList() {
		return reserveList;
	}

	public void setReserveList(List<Reserve> reserveList) {
		this.reserveList = reserveList;
	}

	@Override
	public String toString() {
		return "ReserveList [reserveList=" + reserveList + ", currentPage="
				+ currentPage + ", nextPage=" + nextPage + ", totalSize="
				+ totalSize + ", pageSize=" + pageSize + ", canLoadMore="
				+ canLoadMore + "]";
	}
	
}
