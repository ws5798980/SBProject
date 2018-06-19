package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.XsStoreDetailActivity;
import com.rs.mobile.wportal.entity.StoreItemDetailEntity;
import com.rs.mobile.wportal.entity.XsStoreDetailMenuItem;

import java.util.List;

public class XsStoreDetailMenuAdapter extends BaseQuickAdapter<StoreItemDetailEntity.Storeitem, BaseViewHolder> {
    private Context mContext;

    public XsStoreDetailMenuAdapter(Context context, int layoutResId, List data){
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreItemDetailEntity.Storeitem item) {
        helper.setText(R.id.tv_product_nm, item.item_name);
        helper.setText(R.id.tv_price, "가격: " + item.item_p);
        Glide.with(mContext).load(item.image_url).into((ImageView) helper.getView(R.id.iv_img_res));
    }
}
