package com.rs.mobile.wportal.biz.paging;

import java.io.Serializable;

public abstract class Pager implements Serializable{
	
	public int currentPage;
	public int nextPage;
	public int totalSize;
	public int pageSize;
	public boolean canLoadMore;
	
	/**
	 * @description 返回当前页index
	 * @return
	 * @author ZhaoYun
	 * @date 2017-3-20
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @description 返回当前页的下一页index
	 * @return
	 * @author ZhaoYun
	 * @date 2017-3-20
	 */
	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
		setCanLoadMore(nextPage != 0);
	}

	/**
	 * @description 数据库中数据总条数
	 * @return
	 * @author ZhaoYun
	 * @date 2017-3-20
	 */
	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * @description 分页的每页size
	 * @return
	 * @author ZhaoYun
	 * @date 2017-3-20
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @description 是否可下拉加载
	 * @return
	 * @author ZhaoYun
	 * @date 2017-3-20
	 */
	public boolean getCanLoadMore() {
		return canLoadMore;
	}

	public void setCanLoadMore(boolean canLoadMore) {
		this.canLoadMore = canLoadMore;
	}

}
