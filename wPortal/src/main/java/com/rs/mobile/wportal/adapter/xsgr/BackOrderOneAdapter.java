package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.XsBackOrderDetailActivity;
import com.rs.mobile.wportal.biz.xsgr.BackOrderBean;
import com.rs.mobile.wportal.entity.BaseEntity;

import java.util.List;

public class BackOrderOneAdapter extends BaseQuickAdapter<BackOrderBean.DataBean, BaseViewHolder> {


    Context context;
    List<Fragment> list;


    private String[] titles;

    public BackOrderOneAdapter(Context context, int layoutResId, @Nullable List<BackOrderBean.DataBean> data) {
        super(layoutResId, data);
        this.context = context;

    }


    @Override
    protected void convert(BaseViewHolder helper, BackOrderBean.DataBean item) {
        helper.addOnClickListener(R.id.bt_check)
                .setText(R.id.tv_order_new_name, item.getOrder_num())
                .setText(R.id.tv_phone, item.getMobilepho())
                .setText(R.id.tv_price, item.getTot_amt())
                .setText(R.id.tv_status, item.getStatus_classify())
                .setText(R.id.tv_time, item.getCancel_date());


    }
}
