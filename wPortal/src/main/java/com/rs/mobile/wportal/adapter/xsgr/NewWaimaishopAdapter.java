package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.biz.xsgr.NewShopBean;
import com.rs.mobile.wportal.biz.xsgr.NewWaimaiShopBean;

import java.util.List;

public class NewWaimaishopAdapter extends BaseQuickAdapter<NewWaimaiShopBean.DataBean.PopularBannerBean, BaseViewHolder> {
    private Context mContext;


    public NewWaimaishopAdapter(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, NewWaimaiShopBean.DataBean.PopularBannerBean item) {

        helper.setText(R.id.tv_name, item.getCUSTOM_NAME());
        helper.setText(R.id.tv_ad, item.getAd_title());
        Glide.with(mContext).load(item.getAd_image()).into((ImageView) helper.getView(R.id.img_newshop));

        helper.addOnClickListener(R.id.layout_newshop);
    }
}
