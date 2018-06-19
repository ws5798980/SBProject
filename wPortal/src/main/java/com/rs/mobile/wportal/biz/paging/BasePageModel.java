package com.rs.mobile.wportal.biz.paging;

import java.util.List;

public abstract class BasePageModel<ENTITY> extends Pager {
	
	public List<ENTITY> list;

	public abstract List<ENTITY> getList();

	public abstract void setList(List<ENTITY> list);
	
}
