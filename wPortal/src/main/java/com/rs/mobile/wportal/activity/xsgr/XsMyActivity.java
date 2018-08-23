package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.LoginActivity;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.MyEvaluateActivity;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;
import com.rs.mobile.wportal.activity.sm.SmAddressActivity;
import com.rs.mobile.wportal.activity.sm.SmCollectionActivity;
import com.rs.mobile.wportal.activity.sm.SmCouponActivity;
import com.rs.mobile.wportal.activity.sm.SmMainActivity;
import com.rs.mobile.wportal.persnal.SettingActivity;

public class XsMyActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llLogined;
    private TextView tvLogin, tvNickname, tvTel;
    private TextView tvOrder;
    private LinearLayout llAddress, llLive, llCoupon, llNotice, llSetting, llCollection, llActivity, llAnnouncement, llService, text_to_myshop;
    private WImageView ivPhoto;
    private LinearLayout llSet;
    private boolean finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_my);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        llLogined = (LinearLayout) findViewById(R.id.ll_logined);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvNickname = (TextView) findViewById(R.id.tv_nickname);
        tvTel = (TextView) findViewById(R.id.tv_tel);
        tvOrder = (TextView) findViewById(R.id.text_to_order);
        llAddress = (LinearLayout) findViewById(R.id.text_to_address);
        llLive = (LinearLayout) findViewById(R.id.text_to_live);
//        llCoupon = (LinearLayout) findViewById(R.id.text_to_coupon);
        llNotice = (LinearLayout) findViewById(R.id.text_to_notice);
        llSetting = (LinearLayout) findViewById(R.id.text_to_setting);
        llCollection = (LinearLayout) findViewById(R.id.text_to_collection);
        llActivity = (LinearLayout) findViewById(R.id.text_to_activity);
        llAnnouncement = (LinearLayout) findViewById(R.id.text_to_announcement);
        llService = (LinearLayout) findViewById(R.id.text_to_service);
        ivPhoto = (WImageView) findViewById(R.id.iv_photo);
        ivPhoto.setCircle(true);
        llSet = (LinearLayout) findViewById(R.id.ll_setting);
        text_to_myshop = (LinearLayout) findViewById(R.id.text_to_myshop);

        tvLogin.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        llAddress.setOnClickListener(this);
        llLive.setOnClickListener(this);
//        llCoupon.setOnClickListener(this);
        llNotice.setOnClickListener(this);
        llSetting.setOnClickListener(this);
        llCollection.setOnClickListener(this);
        llActivity.setOnClickListener(this);
        llAnnouncement.setOnClickListener(this);
        llService.setOnClickListener(this);
        llSet.setOnClickListener(this);
        text_to_myshop.setOnClickListener(this);
    }

    private void initData() {

        if (S.get(XsMyActivity.this, C.KEY_JSON_TOKEN) != null && !S.get(XsMyActivity.this, C.KEY_JSON_TOKEN).isEmpty()) {
            tvLogin.setVisibility(View.GONE);
            llLogined.setVisibility(View.VISIBLE);
            tvNickname.setText(S.get(XsMyActivity.this, C.KEY_JSON_NICK_NAME));
            tvTel.setText(S.get(XsMyActivity.this, C.KEY_JSON_CUSTOM_ID));
            String imgUrl = S.get(XsMyActivity.this, C.KEY_JSON_PROFILE_IMG);
            if (imgUrl != null && !imgUrl.isEmpty()) {
//            Glide.with(XsMyActivity.this).load(imgUrl).into(ivPhoto);
                ImageUtil.drawImageFromUri(imgUrl, ivPhoto);
            }
        } else {
            llLogined.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
            ivPhoto.setImageResource(R.drawable.img_defaultheadphoto_002);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                Intent intentLogin1 = new Intent(XsMyActivity.this, LoginActivity.class);
                startActivity(intentLogin1);
                break;
            case R.id.text_to_order:
                UtilClear.CheckLogin(XsMyActivity.this, new UtilCheckLogin.CheckListener() {

                    @Override
                    public void onDoNext() {
                        Intent intentOrder = new Intent(XsMyActivity.this, MyOrderActivity.class);
                        startActivity(intentOrder);
                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });
//                if(isLogined()){
//                    Intent intentOrder = new Intent(XsMyActivity.this, MyOrderActivity.class);
//                    startActivity(intentOrder);
//                }else{
//                    Intent intentLogin2 = new Intent(XsMyActivity.this, LoginActivity.class);
//                    startActivity(intentLogin2);
//                }
                break;
            case R.id.text_to_address:
                UtilClear.CheckLogin(XsMyActivity.this, new UtilCheckLogin.CheckListener() {

                    @Override
                    public void onDoNext() {
                        Intent intentAddress = new Intent(XsMyActivity.this, SmAddressActivity.class);
                        startActivity(intentAddress);
                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });
//                if(isLogined()){
//                    Intent intentAddress = new Intent(XsMyActivity.this, SmAddressActivity.class);
//                    startActivity(intentAddress);
//                }else{
//                    Intent intentLogin3 = new Intent(XsMyActivity.this, LoginActivity.class);
//                    startActivity(intentLogin3);
//                }
                break;
            case R.id.text_to_live:
//                if(isLogined()){
//
//                }else{
//                    Intent intentLogin4 = new Intent(XsMyActivity.this, LoginActivity.class);
//                    startActivity(intentLogin4);
//                }
                break;
//            case R.id.text_to_coupon:
//                if(isLogined()){
//                    Intent intentCoupon = new Intent(XsMyActivity.this, SmCouponActivity.class);
//                    startActivity(intentCoupon);
//                }else{
//                    Intent intentLogin5 = new Intent(XsMyActivity.this, LoginActivity.class);
//                    startActivity(intentLogin5);
//                }
//                break;
            case R.id.text_to_notice:
                UtilClear.CheckLogin(XsMyActivity.this, new UtilCheckLogin.CheckListener() {

                    @Override
                    public void onDoNext() {
                        Intent intentNotice = new Intent(XsMyActivity.this, MyEvaluateActivity.class);
                        startActivity(intentNotice);
                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });

//                if(isLogined()){
//                    Intent intentNotice = new Intent(XsMyActivity.this, MyEvaluateActivity.class);
//                    startActivity(intentNotice);
//                }else{
//                    Intent intent6 = new Intent(XsMyActivity.this, LoginActivity.class);
//                    startActivity(intent6);
//                }
                break;
            case R.id.text_to_setting:
                UtilClear.CheckLogin(XsMyActivity.this, new UtilCheckLogin.CheckListener() {

                    @Override
                    public void onDoNext() {
                        Intent intentSetting = new Intent(XsMyActivity.this, SettingActivity.class);
                        startActivity(intentSetting);
                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });

//                if(isLogined()){
//                    Intent intentSetting = new Intent(XsMyActivity.this, SettingActivity.class);
//                    startActivity(intentSetting);
//                }else{
//                    Intent intent7 = new Intent(XsMyActivity.this, LoginActivity.class);
//                    startActivity(intent7);
//                }
                break;
            case R.id.text_to_collection:
                UtilClear.CheckLogin(XsMyActivity.this, new UtilCheckLogin.CheckListener() {

                    @Override
                    public void onDoNext() {
                        Intent intentCollection = new Intent(XsMyActivity.this, SmCollectionActivity.class);
                        startActivity(intentCollection);
                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });

//                if(isLogined()){
//                    Intent intentCollection = new Intent(XsMyActivity.this, SmCollectionActivity.class);
//                    startActivity(intentCollection);
//                }else{
//                    Intent intent8 = new Intent(XsMyActivity.this, LoginActivity.class);
//                    startActivity(intent8);
//                }
                break;
            case R.id.text_to_activity:
                UtilClear.CheckLogin(XsMyActivity.this, new UtilCheckLogin.CheckListener() {

                    @Override
                    public void onDoNext() {
                        Intent intentActivity = new Intent(XsMyActivity.this, XsActivityActivity.class);
                        startActivity(intentActivity);
                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });

//                if(isLogined()){
//                    Intent intentActivity = new Intent(XsMyActivity.this, XsActivityActivity.class);
//                    startActivity(intentActivity);
//                }else{
//                    Intent intent9 = new Intent(XsMyActivity.this, LoginActivity.class);
//                    startActivity(intent9);
//                }

                break;
            case R.id.text_to_announcement:
                UtilClear.CheckLogin(XsMyActivity.this, new UtilCheckLogin.CheckListener() {

                    @Override
                    public void onDoNext() {
                        Intent intentAmmouncement = new Intent(XsMyActivity.this, XsAnnouncementActivity.class);
                        startActivity(intentAmmouncement);
                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });

//                if(isLogined()){
//                    Intent intentAmmouncement = new Intent(XsMyActivity.this, XsAnnouncementActivity.class);
//                    startActivity(intentAmmouncement);
//                }else{
//                    Intent intent10 = new Intent(XsMyActivity.this, LoginActivity.class);
//                    startActivity(intent10);
//                }

                break;
            case R.id.text_to_service:
                UtilClear.CheckLogin(XsMyActivity.this, new UtilCheckLogin.CheckListener() {

                    @Override
                    public void onDoNext() {
                        Intent intentService = new Intent(XsMyActivity.this, XsServiceActivity.class);
                        startActivity(intentService);
                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });

//                if(S.get(XsMyActivity.this, C.KEY_JSON_TOKEN) != null && !S.get(XsMyActivity.this, C.KEY_JSON_TOKEN).isEmpty()){
//                    Intent intentService = new Intent(XsMyActivity.this, XsServiceActivity.class);
//                    startActivity(intentService);
//                }else{
//                    Intent intent11 = new Intent(XsMyActivity.this, LoginActivity.class);
//                    startActivity(intent11);
//                }

                break;
            case R.id.ll_setting:
                UtilClear.CheckLogin(XsMyActivity.this, new UtilCheckLogin.CheckListener() {

                    @Override
                    public void onDoNext() {
                        Intent intentSetting = new Intent(XsMyActivity.this, SettingActivity.class);
                        startActivity(intentSetting);
                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });
                break;
            case R.id.text_to_myshop:
                UtilClear.CheckLogin(XsMyActivity.this, new UtilCheckLogin.CheckListener() {

                    @Override
                    public void onDoNext() {
//                        String realUrl="http://www.gigawon.cn:1314/80_StoreAdmin/storeMain.aspx?custom_code="+S.get(XsMyActivity.this, C.KEY_JSON_CUSTOM_CODE)+"&sale_custom_code=01071390009abcde&token="+S.get(XsMyActivity.this, C.KEY_JSON_TOKEN);
//                        Intent intent = new Intent(XsMyActivity.this,WebActivity.class);
//                        intent.putExtra(C.KEY_INTENT_URL,realUrl);
//                        XsMyActivity.this.startActivity(intent);
                        Intent intent = new Intent(XsMyActivity.this, XsMyShopActivity.class);
                        startActivity(intent);

                    }
                }, new UtilCheckLogin.CheckError() {
                    @Override
                    public void onError() {
                    }
                });
                break;
        }
    }

    private boolean isLogined() {
        if (UiUtil.checkLogin(XsMyActivity.this) == true) {
            return true;
        } else {
            return false;
        }
    }

}
