package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.BaseEntity;

import java.util.List;

public class OrderOneAdapter extends BaseQuickAdapter<BaseEntity, BaseViewHolder> {
    LinearLayout includelayout;
    View openLayout;
    TextView openTv;
    ImageView img_order_iocn;

    Context context;

    public OrderOneAdapter(Context context, int layoutResId, @Nullable List<BaseEntity> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseEntity item) {

        includelayout = helper.getView(R.id.layout_include);
        openLayout = helper.getView(R.id.layout_open);
        img_order_iocn = helper.getView(R.id.img_order_iocn);
        openTv = helper.getView(R.id.tv_order_new_pull);

        includelayout.removeAllViews();
        includelayout.setVisibility(View.GONE);
        openTv.setText(context.getResources().getString(R.string.orderopen));
        img_order_iocn.setImageResource(R.drawable.icon_open_goods);

        helper.addOnClickListener(R.id.layout_open);

        for (int i = 0; i < 5; i++) {
            View childView = LayoutInflater.from(context).inflate(R.layout.item_order_open, null);
            includelayout.addView(childView);
        }

    }
}
