package com.rs.mobile.wportal.adapter;

import org.json.JSONArray;

import com.rs.mobile.common.L;

/**
 * 抽象的PagerAdapter实现类,封装了内容为View,数据为List类型的适配器实现.
 * @param <T>
 * @author ZhaoYun
 * @date 2017-3-12
 */
public abstract class AbstractPagerListAdapter<T> extends AbstractViewPagerAdapter {
	
	protected JSONArray mData;

	public AbstractPagerListAdapter(JSONArray data) {
		mData = data;
	}

	@Override
	public int getCount() {
		return mData.length();
	}

	public Object getItem(int position) {
		
		try {
		
			return mData.get(position);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return null;
		
	}
	
}
