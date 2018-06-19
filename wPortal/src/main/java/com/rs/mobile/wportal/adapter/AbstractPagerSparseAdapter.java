package com.rs.mobile.wportal.adapter;

import com.rs.mobile.common.T;

import android.util.SparseArray;
import android.view.View;

/**
 * 抽象的PagerAdapter实现类,封装了内容为View,数据为SparseArray类型的适配器实现. 
 * @author ZhaoYun
 * @date 2017-3-12
 */
public abstract class AbstractPagerSparseAdapter extends AbstractViewPagerAdapter {

	protected SparseArray<T> mData;
	  
    public AbstractPagerSparseAdapter(SparseArray<T> data) {  
        mData = data;  
    }  
  
    @Override  
    public int getCount() {  
        return mData.size();  
    }  
  
    public abstract View newView(int position);  
  
    public T getItem(int position) {  
        return mData.valueAt(position);  
    }  

}
