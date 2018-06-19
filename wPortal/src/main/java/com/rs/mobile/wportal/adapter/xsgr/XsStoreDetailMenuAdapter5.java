package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.StoreMenuListEntity1;

import java.util.List;

public class XsStoreDetailMenuAdapter5 extends BaseQuickAdapter<StoreMenuListEntity1.foodFlavor, BaseViewHolder> {
    private Context mContext;


    public XsStoreDetailMenuAdapter5(Context context, int layoutResId, List data){
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if(position==0)
        {
            holder.setBackgroundRes(R.id.tv_listitemname,R.drawable.new_shop_shape4);
            holder.setTextColor(R.id.tv_listitemname,Color.parseColor("#ffffff"));
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreMenuListEntity1.foodFlavor item) {

        helper.setText(R.id.tv_listitemname, item.flavorName);
        helper.addOnClickListener(R.id.tv_listitemname);


    }
}
