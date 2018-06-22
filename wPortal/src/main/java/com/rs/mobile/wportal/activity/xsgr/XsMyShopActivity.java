package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;


public class XsMyShopActivity extends BaseActivity {

    LinearLayout selectView, close_btn;
    ImageView img_myshop;
    TextView tv_xs_my_shopname, tv_select, tv_sale, tv_salenum;
    int select = 1;

    View layout_order, layout_info, layout_date, layout_goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_my_shopinfo);

        initView();

        setListener();
    }

    private void setListener() {
        selectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectState();
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layout_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XsMyShopActivity.this, Activity_Order.class);
                startActivity(intent);
            }
        });

        layout_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XsMyShopActivity.this, StoreManageActivity.class);
                startActivity(intent);
            }
        });
        layout_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XsMyShopActivity.this, StoreDataActivity.class);
                startActivity(intent);
            }
        });
        layout_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XsMyShopActivity.this,CommodityManagementActivity.class));
            }
        });
    }

    private void showSelectState() {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_myshop, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        final PopupWindow window = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移

        LinearLayout layout1 = (LinearLayout) contentView.findViewById(R.id.layout_1);
        LinearLayout layout2 = (LinearLayout) contentView.findViewById(R.id.layout_2);
        final ImageView img1 = (ImageView) contentView.findViewById(R.id.img_1);
        final ImageView img2 = (ImageView) contentView.findViewById(R.id.img_2);

        if (select == 1) {
            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.GONE);
            tv_select.setText("营业中");
        } else {
            img2.setVisibility(View.VISIBLE);
            img1.setVisibility(View.GONE);
            tv_select.setText("打烊中");
        }


        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setVisibility(View.VISIBLE);
                img2.setVisibility(View.GONE);
                tv_select.setText("营业中");
                window.dismiss();
                select = 1;
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img2.setVisibility(View.VISIBLE);
                img1.setVisibility(View.GONE);
                tv_select.setText("打烊中");
                window.dismiss();
                select = 2;
            }
        });


        window.showAsDropDown(selectView, -40, 0);


    }

    private void initView() {
        selectView = (LinearLayout) findViewById(R.id.layout_select);
        close_btn = (LinearLayout) findViewById(R.id.close_btn);
        img_myshop = (ImageView) findViewById(R.id.img_myshop);
        tv_xs_my_shopname = (TextView) findViewById(R.id.tv_xs_my_shopname);
        tv_select = (TextView) findViewById(R.id.tv_select);
        tv_sale = (TextView) findViewById(R.id.tv_sale);
        tv_salenum = (TextView) findViewById(R.id.tv_salenum);

        layout_order = findViewById(R.id.layout_order);
        layout_info = findViewById(R.id.layout_store);
        layout_date = findViewById(R.id.layout_data);
        layout_goods = findViewById(R.id.layout_goods);
    }

}
