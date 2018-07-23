package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.CommentBean;

import java.util.List;

public class XsStoreCommentAdapter extends BaseQuickAdapter<CommentBean.ShopAssessDataBean, BaseViewHolder> {


    Context context;
    List<Fragment> list;


    public XsStoreCommentAdapter(Context context, int layoutResId, @Nullable List<CommentBean.ShopAssessDataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, CommentBean.ShopAssessDataBean item) {


        RatingBar ratingBar = helper.getView(R.id.rating_bar);
        ratingBar.setRating(Float.parseFloat(item.getScore()));

        helper.setText(R.id.textview, item.getRep_content());




    }
}
