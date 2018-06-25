package com.rs.mobile.wportal.activity.xsgr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.R;

public class XsBackOrderDetailActivity extends AppCompatActivity {

    LinearLayout layout_xiangqing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_my_orderback_detail);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();
    }

    private void initView() {

        layout_xiangqing = (LinearLayout) findViewById(R.id.layout_xiangqing);
    }

    private void initData() {


        layout_xiangqing.addView(LayoutInflater.from(this).inflate(R.layout.item_backorder_detail, null));
        layout_xiangqing.addView(LayoutInflater.from(this).inflate(R.layout.item_backorder_detail, null));
        layout_xiangqing.addView(LayoutInflater.from(this).inflate(R.layout.item_backorder_detail, null));

    }


    private boolean isLogined() {
        if (UiUtil.checkLogin(XsBackOrderDetailActivity.this) == true) {
            return true;
        } else {
            return false;
        }
    }
}
