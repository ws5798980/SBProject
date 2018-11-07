package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.biz.xsgr.NewShopWaimaiBean;
import com.rs.mobile.wportal.biz.xsgr.NewWaimaiShopBean;

import java.util.List;

public class NewWaimaishop2Adapter extends BaseQuickAdapter<NewShopWaimaiBean.DataBean.NewStoreBean, BaseViewHolder> {
    private Context mContext;


    public NewWaimaishop2Adapter(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewShopWaimaiBean.DataBean.NewStoreBean item) {
        helper.setText(R.id.tv_name, item.getCUSTOM_NAME());
        helper.setText(R.id.tv_score, item.getSCORE());
        helper.setText(R.id.tv_ad, item.getSHOP_INFO());
        helper.setText(R.id.tv_range, item.getDistance());
        Glide.with(mContext).load(item.getSHOP_THUMNAIL_IMAGE ()).into((ImageView) helper.getView(R.id.img));

        helper.addOnClickListener(R.id.layout_newshop);
    }
}
