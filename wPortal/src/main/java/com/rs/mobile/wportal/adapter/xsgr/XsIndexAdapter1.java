package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.IndexImageItem;
import com.rs.mobile.wportal.entity.StoreMenuListEntity1;
import com.rs.mobile.wportal.entity.XsStoreDetailMenuItem;

import java.util.List;

public class XsIndexAdapter1 extends BaseQuickAdapter<XsStoreDetailMenuItem.Storeitem, BaseViewHolder> {
    private Context mContext;


    public XsIndexAdapter1(Context context, int layoutResId, List data){
        super(layoutResId, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, XsStoreDetailMenuItem.Storeitem item) {

        //helper.setText(R.id.iv_itemimage, item);
        if(item.image_url!=null && !item.image_url.equals("")) {
            Glide.with(mContext).load(item.image_url).into((ImageView) helper.getView(R.id.iv_itemimage));
        }
        helper.addOnClickListener(R.id.iv_itemimage);

    }
}
