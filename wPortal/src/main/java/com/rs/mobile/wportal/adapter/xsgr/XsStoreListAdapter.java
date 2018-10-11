package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.StoreCateListEntity;
import com.rs.mobile.wportal.entity.StoreListItem;

import java.util.List;

public class XsStoreListAdapter extends BaseQuickAdapter<StoreCateListEntity.Store, BaseViewHolder> {
    public Context mContext;

    public XsStoreListAdapter(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreCateListEntity.Store item) {
//        helper.setImageResource(R.id.iv_img_res, item.imgRes);
        helper.setText(R.id.tv_prodoct_nm, item.custom_name);
        helper.setText(R.id.tv_reviews, mContext.getResources().getString(R.string.pinglun) + item.cnt);
        helper.setText(R.id.tv_boss_comments, mContext.getResources().getString(R.string.huifu) + item.sale_custom_cnt);
        helper.setText(R.id.tv_distance, item.distance + "km");
        helper.setText(R.id.tv_address, item.kor_addr);
        Glide.with(mContext).load(item.shop_thumnail_image).into((ImageView) helper.getView(R.id.iv_img_res));

        try {
            ((RatingBar) helper.getView(R.id.rating_bar)).setRating(Float.parseFloat(item.score));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
