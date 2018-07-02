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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.BaseEntity;
import com.rs.mobile.wportal.entity.DateDataBean;

import java.util.List;

public class DateDayAdapter extends BaseQuickAdapter<DateDataBean.DataBean, BaseViewHolder> {


    Context context;


    public DateDayAdapter(Context context, int layoutResId, @Nullable List<DateDataBean.DataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, DateDataBean.DataBean item) {

        helper.setText(R.id.tv_order_new_phone, item.getOrder_num())
                .setText(R.id.textview_num, item.getOrder_seq())
                .setText(R.id.textview_phone, item.getMobilepho())
                .setText(R.id.textview_price, item.getTot_amt())
                .setText(R.id.textview_status, item.getOrder_status())
                .setText(R.id.textview_time, item.getCreate_date());


    }
}
