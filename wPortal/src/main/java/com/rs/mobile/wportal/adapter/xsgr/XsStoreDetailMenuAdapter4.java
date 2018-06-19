package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.StoreMenuListEntity1;

import java.util.List;

public class XsStoreDetailMenuAdapter4 extends BaseQuickAdapter<StoreMenuListEntity1.foodSpec, BaseViewHolder> {
    private Context mContext;


    public XsStoreDetailMenuAdapter4(Context context, int layoutResId, List data){
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
    protected void convert(BaseViewHolder helper, StoreMenuListEntity1.foodSpec item) {

        helper.setText(R.id.tv_listitemname, item.item_name);
        helper.addOnClickListener(R.id.tv_listitemname);

    }
}
