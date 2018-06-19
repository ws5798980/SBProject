package com.rs.mobile.wportal.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {
	
	protected Context mContext;
	protected LayoutInflater mLi;
	protected List<T> mDatas;
	private int mLayoutId;
	
	public CommonAdapter(Context context , 
			List<T> datas , int layoutId ){
		this.mContext = context;
		this.mDatas = datas;
		this.mLayoutId = layoutId;
	}
	
	public void setDatas(List<T> datas){
		this.mDatas = datas;
	}

	@Override
	public int getCount() {
		int count = (mDatas == null ? 0 : mDatas.size());
		return count;
	}

	@Override
	public T getItem(int position) {
		return mDatas == null ? null : mDatas.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		CommonViewHolder viewHolder = CommonViewHolder.get(mContext, convertView, parent, mLayoutId, position);
		convert(viewHolder, getItem(position));
		return viewHolder.getConvertView();
	}
		
	protected abstract void convert(CommonViewHolder viewHolder , T t);
	
	@Override
	public boolean isEmpty() {
		return (getCount()-1) == 0;
	}

}
