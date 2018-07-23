package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.biz.xsgr.BackOrderBean;

public class XsBackOrderDetailActivity extends AppCompatActivity {

    LinearLayout layout_xiangqing;
    BackOrderBean.DataBean item;
    ImageView callImg;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_my_orderback_detail);
        item = getIntent().getParcelableExtra("item");
        phone=item.getMobilepho();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();
    }

    private void initView() {
        LinearLayout close_btn = (LinearLayout) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_xiangqing = (LinearLayout) findViewById(R.id.layout_xiangqing);
        callImg= (ImageView) findViewById(R.id.img_call);

        callImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        ((TextView) (findViewById(R.id.tv_order_new_name))).setText(item.getCustom_name());
        ((TextView) (findViewById(R.id.tv_order_new_phone))).setText(item.getMobilepho());
        ((TextView) (findViewById(R.id.tv_order_new_add))).setText(item.getTo_address());
        ((TextView) (findViewById(R.id.textview_ordernum))).setText(item.getOrder_num());
        ((TextView) (findViewById(R.id.textview_backordernum))).setText(item.getCancel_num());
        ((TextView) (findViewById(R.id.textview_price))).setText(item.getTot_amt());
        ((TextView) (findViewById(R.id.textview_status))).setText(item.getStatus_classify());
        ((TextView) (findViewById(R.id.textview_time))).setText(item.getCancel_date());
        ((TextView) (findViewById(R.id.tv_order_new_name))).setText(item.getCustom_name());


        for (int i = 0; i < item.getDataitem().size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_backorder_detail, null);
            Glide.with(this).load(item.getDataitem().get(i).getItem_image_url()).into((ImageView) view.findViewById(R.id.img_backorder));
            ((TextView) (view.findViewById(R.id.textview_name))).setText(item.getDataitem().get(i).getItem_name());
            ((TextView) (view.findViewById(R.id.textview_num))).setText("x " + item.getDataitem().get(i).getOrder_q());
            ((TextView) (view.findViewById(R.id.textview_price))).setText("￥ " + item.getDataitem().get(i).getOrder_o());

            layout_xiangqing.addView(view);
        }

    }


    private boolean isLogined() {
        if (UiUtil.checkLogin(XsBackOrderDetailActivity.this) == true) {
            return true;
        } else {
            return false;
        }
    }
}
