package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rs.mobile.wportal.entity.StoreMenuListEntity1;

public class SpSelectAdapter extends BaseAdapter {
    private StoreMenuListEntity1 data;
    private  Context context;

    public SpSelectAdapter(Context context,StoreMenuListEntity1 data){
        this.data=data;
        this.context=context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
