package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.biz.xsgr.NewShopBean;
import com.rs.mobile.wportal.entity.XsStoreDetailMenuItem;

import java.util.List;

public class NewshopAdapter extends BaseQuickAdapter<NewShopBean.DataBean.NewStoreBean, BaseViewHolder> {
    private Context mContext;


    public NewshopAdapter(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, NewShopBean.DataBean.NewStoreBean item) {

        helper.setText(R.id.tv_name, item.getCUSTOM_NAME());
        helper.setText(R.id.tv_ad, item.getSHOP_INFO());
        Glide.with(mContext).load(item.getSHOP_THUMNAIL_IMAGE()).into((ImageView) helper.getView(R.id.img_newshop));

        helper.addOnClickListener(R.id.layout_newshop);
    }
}
