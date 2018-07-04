package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.BaseEntity;
import com.rs.mobile.wportal.entity.CommentBean;

import java.util.List;

public class CommentAdapter extends BaseQuickAdapter<CommentBean.ShopAssessDataBean, BaseViewHolder> {


    Context context;
    List<Fragment> list;


    public CommentAdapter(Context context, int layoutResId, @Nullable List<CommentBean.ShopAssessDataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, CommentBean.ShopAssessDataBean item) {

        LinearLayout linearLayout = helper.getView(R.id.layout_huifu);
        WImageView imageView = helper.getView(R.id.img_head);
        if (item.getImg_path() != null && !item.getImg_path().isEmpty()) {
            ImageUtil.drawImageFromUri(item.getImg_path(), imageView);
        }
        RatingBar ratingBar = helper.getView(R.id.rating_bar);
        ratingBar.setRating(Float.parseFloat(item.getScore()));

        helper.setText(R.id.textview_name, item.getCustom_name())
                .setText(R.id.textview_time, item.getReg_date())
                .setText(R.id.textview_comment, item.getRep_content())
                .addOnClickListener(R.id.bt_huifu);


        if (item.getSale_content().equals("")) {
            linearLayout.setVisibility(View.GONE);
            helper.setText(R.id.bt_huifu, "回复");

        } else {
            linearLayout.setVisibility(View.VISIBLE);
            helper.setText(R.id.bt_huifu, "修改")
                    .setText(R.id.textview_backtime, item.getSale_reg_date())
                    .setText(R.id.textview_salecomment, item.getSale_content());
        }


    }
}
