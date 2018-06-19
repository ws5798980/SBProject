package com.rs.mobile.wportal.adapter.xsgr;

import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.MainBusinessListItem;

import java.util.List;

public class MainBusinessAdapter extends BaseQuickAdapter<MainBusinessListItem, BaseViewHolder> {
    public MainBusinessAdapter(int layoutResId, List data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainBusinessListItem item) {
        helper.setImageResource(R.id.iv_img_res, item.imgRes);
        helper.setText(R.id.tv_business_nm, item.businessNm);
        helper.setText(R.id.tv_address, item.address);
        helper.setText(R.id.tv_distance, item.distance);
        helper.setImageResource(R.id.iv_heart, item.heart);
        ((RatingBar)helper.getView(R.id.rating_bar)).setRating(4);
    }
}
