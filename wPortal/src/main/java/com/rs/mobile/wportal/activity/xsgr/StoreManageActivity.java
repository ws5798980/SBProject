package com.rs.mobile.wportal.activity.xsgr;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class StoreManageActivity extends BaseActivity {
    MyShopInfoBean bean;
    TextView tv_name, tv_phone, tv_add;
    WImageView wImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_my_info);

        initView();

        initData();


        setListener();

    }

    private void setListener() {


    }

    private void initData() {
        initShopInfoData();
    }

    private void initView() {
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_name = (TextView) findViewById(R.id.tv_name);
        wImageView = (WImageView) findViewById(R.id.img_head);
        LinearLayout close_btn = (LinearLayout) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //评论列表
    public void initShopInfoData() {
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(StoreManageActivity.this, C.KEY_JSON_TOKEN, ""));
        param.put("custom_code", S.getShare(StoreManageActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
//        param.put("custom_code", "01071390001abcde");
//        param.put("token", "186743935020f829f883e9fe-c8cf-4f60-9ed2-bd645cb1c118");
        OkHttpHelper okHttpHelper = new OkHttpHelper(StoreManageActivity.this);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                bean = GsonUtils.changeGsonToBean(responseDescription, MyShopInfoBean.class);

                tv_name.setText(bean.getCustom_name() + "");
                tv_phone.setText(bean.getTelephon() + "");
                tv_add.setText(bean.getAddress() + "");

//            Glide.with(XsMyShopActivity.this).load(bean.getShop_thumnail_image()).into(img_myshop);
                if (bean.getShop_thumnail_image() != null && !bean.getShop_thumnail_image().isEmpty()) {
                    ImageUtil.drawImageFromUri(bean.getShop_thumnail_image(), wImageView);
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {

            }
        }, Constant.XS_BASE_URL + "AppSM/requestSaleOrderAmount", param);

    }

}
