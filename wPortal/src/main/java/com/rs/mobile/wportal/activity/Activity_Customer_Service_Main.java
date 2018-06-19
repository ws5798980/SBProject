package com.rs.mobile.wportal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.R;

public class Activity_Customer_Service_Main extends Activity {

    private ImageView iv_customer_back;
    private TextView tv_custom_rs_send, tv_custom_rs_gift, tv_custom_rs_backmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__customer__service__main);

        iv_customer_back = (ImageView)findViewById(R.id.customer_back);
        iv_customer_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_custom_rs_send = (TextView)findViewById(R.id.custom_rs_send);
        tv_custom_rs_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    UiUtil.doButtonClickAnimation(getApplicationContext(), tv_custom_rs_send);
                    UtilClear.IntentToLongLiao(getApplicationContext(), "cn.rsapp.im.ui.activity.CustomerActivity","12099999999");
                } catch (Exception e) {

                }
            }
        });

        tv_custom_rs_gift = (TextView)findViewById(R.id.custom_rs_gift);
        tv_custom_rs_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    UiUtil.doButtonClickAnimation(getApplicationContext(), tv_custom_rs_gift);
                    UtilClear.IntentToLongLiao(getApplicationContext(), "cn.rsapp.im.ui.activity.CustomerActivity","12088888888");
                } catch (Exception e) {

                }

            }
        });

        tv_custom_rs_backmoney = (TextView)findViewById(R.id.custom_rs_backmoney);
        tv_custom_rs_backmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    UiUtil.doButtonClickAnimation(getApplicationContext(), tv_custom_rs_backmoney);
                    UtilClear.IntentToLongLiao(getApplicationContext(), "cn.rsapp.im.ui.activity.CustomerActivity","12077777777");
                } catch (Exception e) {

                }

            }
        });

    }
}
