package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.OrderBean;

import java.util.List;

public class OrderTwoAdapter extends BaseQuickAdapter<OrderBean.DataBean, BaseViewHolder> {
    LinearLayout includelayout;
    View openLayout;
    TextView openTv;
    ImageView img_order_iocn;

    Context context;

    public OrderTwoAdapter(Context context, int layoutResId, @Nullable List<OrderBean.DataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean.DataBean item) {

        helper.setText(R.id.tv_order_new_name, item.getCustom_name())
                .setText(R.id.tv_order_new_phone, item.getMobilepho())
                .setText(R.id.tv_order_new_add, item.getTo_address())
                .setText(R.id.tv_num, "#" + item.getOrder_seq())
                .setText(R.id.tv_order_sendprice, context.getResources().getString(R.string.peisongfei) + item.getDelivery_o() + ")")
                .setText(R.id.tv_price, item.getTot_amt())
                .setText(R.id.tv_ordernum, context.getResources().getString(R.string.datanum) + item.getOrder_num())
                .setText(R.id.tv_ordertime, context.getResources().getString(R.string.datedate) + item.getCreate_date())
                .addOnClickListener(R.id.button_sure)
                .addOnClickListener(R.id.button_cancel);
        includelayout = helper.getView(R.id.layout_include);
        openLayout = helper.getView(R.id.layout_open);
        img_order_iocn = helper.getView(R.id.img_order_iocn);
        openTv = helper.getView(R.id.tv_order_new_pull);

        includelayout.removeAllViews();
        includelayout.setVisibility(View.GONE);
        openTv.setText(context.getResources().getString(R.string.orderopen));
        img_order_iocn.setImageResource(R.drawable.icon_open_goods);

        helper.addOnClickListener(R.id.layout_open);

        if (item.getDataitem() != null) {

            for (int i = 0; i < item.getDataitem().size(); i++) {
                View childView = LayoutInflater.from(context).inflate(R.layout.item_order_open, null);
                ((TextView) childView.findViewById(R.id.tv_order_item_name)).setText(item.getDataitem().get(i).getItem_name());
                ((TextView) childView.findViewById(R.id.tv_order_item_num)).setText("x " + item.getDataitem().get(i).getOrder_q());
                ((TextView) childView.findViewById(R.id.tv_order_item_price)).setText("￥ " + item.getDataitem().get(i).getOrder_o());

                includelayout.addView(childView);
            }
        }

    }
}
