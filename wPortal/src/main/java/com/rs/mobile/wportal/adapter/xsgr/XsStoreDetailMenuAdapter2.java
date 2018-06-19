package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.StoreItemDetailEntity;

import java.util.List;

public class XsStoreDetailMenuAdapter2 extends BaseQuickAdapter<StoreItemDetailEntity.Storeitem, BaseViewHolder> {
    private Context mContext;

    public XsStoreDetailMenuAdapter2(Context context, int layoutResId, List data){
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreItemDetailEntity.Storeitem item) {
        helper.setText(R.id.name, item.item_name);
        helper.setText(R.id.price, item.item_p);
        Glide.with(mContext).load(item.image_url).into((ImageView) helper.getView(R.id.thumbnail));
    }
}
