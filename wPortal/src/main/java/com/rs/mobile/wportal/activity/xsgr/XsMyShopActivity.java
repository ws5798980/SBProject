package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.MyShopInfoBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;


public class XsMyShopActivity extends BaseActivity {

    LinearLayout layout1;
    LinearLayout layout2;
    ImageView img1;
    ImageView img2;

    PopupWindow window;
    LinearLayout selectView, close_btn;
    WImageView img_myshop;
    TextView tv_xs_my_shopname, tv_select, tv_sale, tv_salenum;
    int select = 1;
    MyShopInfoBean bean;

    View layout_order, layout_info, layout_date, layout_goods, layout_comment, layout_backorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_my_shopinfo);

        initView();
        initShopInfoData();
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
                Intent intent = new Intent(XsMyShopActivity.this, OrderActivity.class);
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
                startActivity(new Intent(XsMyShopActivity.this, CommodityManagementActivity.class));
            }
        });

        layout_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XsMyShopActivity.this, StoreCommentActivity.class);
                startActivity(intent);
            }
        });

        layout_backorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XsMyShopActivity.this, BackOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showSelectState() {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_myshop, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        window = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移

        layout1 = (LinearLayout) contentView.findViewById(R.id.layout_1);
        layout2 = (LinearLayout) contentView.findViewById(R.id.layout_2);
        img1 = (ImageView) contentView.findViewById(R.id.img_1);
        img2 = (ImageView) contentView.findViewById(R.id.img_2);

        if (select == 1) {
            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.GONE);
            tv_select.setText(getResources().getString(R.string.yingyezhong));
        } else {
            img2.setVisibility(View.VISIBLE);
            img1.setVisibility(View.GONE);
            tv_select.setText(getResources().getString(R.string.dayang));
        }


        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initShopInfoStatus("Y");
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initShopInfoStatus("N");
            }
        });


        window.showAsDropDown(selectView, -40, 0);


    }

    private void initView() {
        selectView = (LinearLayout) findViewById(R.id.layout_select);
        close_btn = (LinearLayout) findViewById(R.id.close_btn);
        img_myshop = (WImageView) findViewById(R.id.img_myshop);
        img_myshop.setCircle(true);
        tv_xs_my_shopname = (TextView) findViewById(R.id.tv_xs_my_shopname);
        tv_select = (TextView) findViewById(R.id.tv_select);
        tv_sale = (TextView) findViewById(R.id.tv_sale);
        tv_salenum = (TextView) findViewById(R.id.tv_salenum);

        layout_order = findViewById(R.id.layout_order);
        layout_info = findViewById(R.id.layout_store);
        layout_date = findViewById(R.id.layout_data);
        layout_goods = findViewById(R.id.layout_goods);
        layout_comment = findViewById(R.id.layout_comments);
        layout_backorder = findViewById(R.id.layout_saleback);
    }


    public void initShopInfoData() {

        HashMap<String, String> param = new HashMap<String, String>();

        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(XsMyShopActivity.this, C.KEY_JSON_TOKEN, ""));
        param.put("custom_code", S.getShare(XsMyShopActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
//        param.put("custom_code", "01071390001abcde");
//        param.put("token", "186743935020f829f883e9fe-c8cf-4f60-9ed2-bd645cb1c118");
        OkHttpHelper okHttpHelper = new OkHttpHelper(XsMyShopActivity.this);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                Log.i("xyz", "onBizSuccess: "+responseDescription);
                bean = GsonUtils.changeGsonToBean(responseDescription, MyShopInfoBean.class);

                tv_xs_my_shopname.setText(bean.getCustom_name() + "");
                tv_sale.setText(bean.getSale_amount() + "");
                tv_salenum.setText(bean.getOrder_amount() + "");
                tv_select.setText(bean.getSales_status());
//            Glide.with(XsMyShopActivity.this).load(bean.getShop_thumnail_image()).into(img_myshop);
                if (bean.getShop_thumnail_image() != null && !bean.getShop_thumnail_image().isEmpty()) {
                    ImageUtil.drawImageFromUri(bean.getShop_thumnail_image(), img_myshop);
                }
                if ("영업중".equals(bean.getSales_status()) || "营业中".equals(bean.getSales_status())) {
                    select = 1;
                } else {
                    select = 2;
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {

            }
        }, Constant.XS_BASE_URL + "AppSM/requestSaleOrderAmount", param);

    }

    public void initShopInfoStatus(final String statu) {

        HashMap<String, String> param = new HashMap<String, String>();

        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(XsMyShopActivity.this, C.KEY_JSON_TOKEN, ""));
        param.put("custom_code", S.getShare(XsMyShopActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
//        param.put("custom_code", "01071390001abcde");
//        param.put("token", "186743935020f829f883e9fe-c8cf-4f60-9ed2-bd645cb1c118");
        param.put("status", statu);
        OkHttpHelper okHttpHelper = new OkHttpHelper(XsMyShopActivity.this);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                try {
                    JSONObject jsonObject = new JSONObject(responseDescription);
                    String status = jsonObject.getString("status");
                    if ("1".equals(status)) {

                        if (statu.equals("N")) {
                            tv_select.setText(getResources().getString(R.string.dayang));
                            window.dismiss();
                            select = 2;
                        } else {
                            tv_select.setText(getResources().getString(R.string.yingyezhong));
                            window.dismiss();
                            select = 1;
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {

            }
        }, Constant.XS_BASE_URL + "AppSM/requestUpdateSalesStatus", param);

    }
}
