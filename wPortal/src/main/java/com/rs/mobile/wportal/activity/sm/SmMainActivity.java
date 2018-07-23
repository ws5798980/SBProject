
package com.rs.mobile.wportal.activity.sm;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.LoginActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.XsMyActivity;
import com.rs.mobile.wportal.activity.xsgr.XsStoreDetailActivity;
import com.rs.mobile.wportal.activity.xsgr.XsStoreListActivity;
import com.rs.mobile.wportal.activity.xsgr.kfmemain;

import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;

import okhttp3.Request;

@SuppressWarnings("deprecation")
public class SmMainActivity extends TabActivity {

    public static SmMainActivity instance = null;

    private RadioGroup mTabButtonGroup;

    private RadioButton rButton1, rButton2, rButton3, rButton4, rButton5;

    private TabHost mTabHost;

    private String TAB_HIGHLIGHTS = "highlights";

    private String TAB_CLASSIFY = "classify";

    private String TAB_SELF = "selfticket";

    private String TAB_SHOPPINGCART = "shoppingcart";

    private long exitTime = 0;
    int checked;
    int lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_sm_main);
        initview();
    }

    private void initview() {

        try {

            mTabButtonGroup = (RadioGroup) findViewById(R.id.common_toolbar_radiogroup);

            rButton1 = (RadioButton) findViewById(R.id.common_toolbar_2_home);

            rButton3 = (RadioButton) findViewById(R.id.common_toolbar_2_classify);
            rButton4 = (RadioButton) findViewById(R.id.common_toolbar_2_shoppingcart);
            rButton5 = (RadioButton) findViewById(R.id.common_toolbar_2_mine);
            Intent i_homepage = new Intent(this, SmHomePageActivity.class);
            Intent i_shoppingcart = new Intent(this, SmShoppingCart.class);
            i_shoppingcart.putExtra("closeFlag", true);
            Intent i_self = new Intent(this, SmMyActivity.class);
            //Intent i_self = new Intent(this, PersnalCenterActivity.class);
            Intent i_classify = new Intent(this, SmClassifyActivity.class);

            Intent xsHomeIntent = new Intent(this, kfmemain.class);
            Intent xsStoreListIntent = new Intent(this, XsStoreListActivity.class);
            Intent xsStoreDetailIntent = new Intent(this, XsStoreDetailActivity.class);
            Intent xsMyIntent = new Intent(this, XsMyActivity.class);

            mTabHost = getTabHost();
            mTabHost.addTab(mTabHost.newTabSpec(TAB_HIGHLIGHTS).setIndicator(TAB_HIGHLIGHTS).setContent(xsHomeIntent));
            mTabHost.addTab(mTabHost.newTabSpec(TAB_CLASSIFY).setIndicator(TAB_CLASSIFY).setContent(xsStoreListIntent));
            mTabHost.addTab(mTabHost.newTabSpec(TAB_SHOPPINGCART).setIndicator(TAB_SHOPPINGCART).setContent(i_shoppingcart));
            mTabHost.addTab(mTabHost.newTabSpec(TAB_SELF).setIndicator(TAB_SELF).setContent(xsMyIntent));

//			mTabHost.addTab(mTabHost.newTabSpec(TAB_HIGHLIGHTS).setIndicator(TAB_HIGHLIGHTS).setContent(i_homepage));
//			mTabHost.addTab(mTabHost.newTabSpec(TAB_CLASSIFY).setIndicator(TAB_CLASSIFY).setContent(i_homepage));
//			mTabHost.addTab(mTabHost.newTabSpec(TAB_SHOPPINGCART).setIndicator(TAB_SHOPPINGCART).setContent(i_classify));
//			mTabHost.addTab(mTabHost.newTabSpec(TAB_SELF).setIndicator(TAB_SELF).setContent(i_self));

            mTabHost.setCurrentTabByTag(TAB_HIGHLIGHTS);
            lastId = R.id.common_toolbar_2_home;

            mTabButtonGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    View viewById = group.findViewById(checkedId);
                    if (!viewById.isPressed()) {
                        return;
                    }
                    try {

//						if (lastId == R.id.common_toolbar_2_shoppingcart) {
//
//							SmShoppingCart.upDateShopcart(SmMainActivity.this);
//
//						}
                        checked = checkedId;
                        switch (checkedId) {
                            case R.id.common_toolbar_2_home:

                                try {

                                    mTabHost.setCurrentTabByTag(TAB_HIGHLIGHTS);
                                    changeTextColor(checkedId);

                                } catch (Exception e) {

                                    L.e(e);

                                }

                                break;
                            case R.id.common_toolbar_2_classify:

                                try {

                                    mTabHost.setCurrentTabByTag(TAB_CLASSIFY);
                                    changeTextColor(checkedId);

                                } catch (Exception e) {

                                    L.e(e);

                                }

                                break;
                            case R.id.common_toolbar_2_shoppingcart:
                                checkLogin(SmMainActivity.this, true, lastId, checkedId, TAB_SHOPPINGCART);
//                                try {
//                                    if (S.get(SmMainActivity.this, C.KEY_JSON_TOKEN) != null && !S.get(SmMainActivity.this, C.KEY_JSON_TOKEN).isEmpty()) {
//                                        mTabHost.setCurrentTabByTag(TAB_SHOPPINGCART);
//                                        changeTextColor(checkedId);
//                                    } else {
//                                        Intent intent = new Intent(SmMainActivity.this, LoginActivity.class);
//                                        startActivity(intent);
//                                        checked = lastId;
//                                        mTabButtonGroup.check(lastId);
//                                        changeTextColor(lastId);
//                                    }
//
////								if (UiUtil.checkLogin(SmMainActivity.this, lastId) == true) {
////									mTabHost.setCurrentTabByTag(TAB_SHOPPINGCART);
////									changeTextColor(checkedId);
////								} else {
////									checkedId = lastId;
////									mTabButtonGroup.check(lastId);
////									changeTextColor(lastId);
////								}
//
//                                } catch (Exception e) {
//
//                                    L.e(e);
//
//                                }

                                break;
                            case R.id.common_toolbar_2_mine:
                                checkLogin(SmMainActivity.this, true, lastId, checkedId, TAB_SELF);
//                                try {
//                                    if (S.get(SmMainActivity.this, C.KEY_JSON_TOKEN) != null && !S.get(SmMainActivity.this, C.KEY_JSON_TOKEN).isEmpty()) {
//                                        mTabHost.setCurrentTabByTag(TAB_SELF);
//                                        changeTextColor(checkedId);
//                                    } else {
//                                        Intent intent = new Intent(SmMainActivity.this, LoginActivity.class);
//                                        startActivity(intent);
//
//                                    }
//
////								if (UiUtil.checkLogin(SmMainActivity.this, lastId) == true) {
////
////									mTabHost.setCurrentTabByTag(TAB_SELF);
////									changeTextColor(checkedId);
////
////								} else {
////									checkedId = lastId;
////									mTabButtonGroup.check(lastId);
////									changeTextColor(lastId);
////
////								}
//                                } catch (Exception e) {
//                                    L.e(e);
//                                }
                                break;
                            default:
                                break;
                        }
                        lastId = checked;
                    } catch (Exception e) {
                        L.e(e);
                    }
                }
            });
        } catch (Exception e) {
            L.e(e);
        }
    }

    private void changeTextColor(int checkedId) {

        try {

            rButton1.setTextColor(getResources().getColor(R.color.inputblack));
            rButton3.setTextColor(getResources().getColor(R.color.inputblack));
            rButton4.setTextColor(getResources().getColor(R.color.inputblack));
            rButton5.setTextColor(getResources().getColor(R.color.inputblack));

            switch (checkedId) {
                case R.id.common_toolbar_2_home:
                    rButton1.setTextColor(getResources().getColor(R.color.mainblue001));
                    break;
                case R.id.common_toolbar_2_classify:
                    rButton3.setTextColor(getResources().getColor(R.color.mainblue001));

                    break;
                case R.id.common_toolbar_2_shoppingcart:
                    rButton4.setTextColor(getResources().getColor(R.color.mainblue001));
                    break;
                case R.id.common_toolbar_2_mine:
                    rButton5.setTextColor(getResources().getColor(R.color.mainblue001));
                default:
                    break;
            }

        } catch (Exception e) {

            L.e(e);

        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN &&
                (System.currentTimeMillis() - exitTime) > 2000
                ) {
            Toast.makeText(getApplicationContext(), "다시 한번 클릭하면 프로그램을 종료합니다.",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 检查匹配登陆（每个要求登陆接口都需要调用）
     *
     * @param context
     */
    public void checkLogin(final Context context, final boolean needLogin, final int lastId0, final int checkid, final String Tab) {

        try {

            OkHttpHelper helper = new OkHttpHelper(context, true);

            helper.addGetRequest(new OkHttpHelper.CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {
                    String netError_result = e.toString();
                }

                @Override
                public void onBizSuccess(String responseDescription,
                                         final JSONObject data, final String flag) {

                    try {
                        if (data.getString("status").equals("1")
                                && data.getString("flag").equals("1501")) {
                            S.setShare(context, C.KEY_JSON_TOKEN,
                                    // data.getString(C.KEY_JSON_TOKEN));
                                    data.getString(C.KEY_JSON_TOKEN) + "|"
                                            + data.getString("ssoId") + "|"
                                            + data.getString("custom_code")
                                            + "|" + Util.getDeviceId(context));

                            S.set(context, C.KEY_JSON_TOKEN,
                                    // data.getString(C.KEY_JSON_TOKEN));
                                    data.getString(C.KEY_JSON_TOKEN) + "|"
                                            + data.getString("ssoId") + "|"
                                            + data.getString("custom_code")
                                            + "|" + Util.getDeviceId(context));

                            S.set(SmMainActivity.this, C.KEY_JSON_CUSTOM_CODE, data.getString("custom_code"));
                            S.setShare(SmMainActivity.this, C.KEY_JSON_CUSTOM_CODE, data.getString("custom_code"));

                            S.set(SmMainActivity.this, C.KEY_REQUEST_MEMBER_ID, data.getString("custom_code"));
                            S.set(SmMainActivity.this, C.KEY_JSON_CUSTOM_ID, data.getString("custom_id"));
                            S.set(SmMainActivity.this, C.KEY_JSON_NICK_NAME, data.getString("nick_name"));
                            S.set(SmMainActivity.this, C.KEY_JSON_PROFILE_IMG, data.getString("profile_img"));
                            S.set(SmMainActivity.this, C.KEY_JSON_DIV_CODE, data.getString("div_code"));
                            S.set(SmMainActivity.this, C.KEY_JSON_SSOID, data.getString("ssoId"));
                            S.set(SmMainActivity.this, C.KEY_JSON_SSO_REGIKEY, data.getString("sso_regiKey"));
                            S.set(SmMainActivity.this, C.KEY_JSON_MALL_HOME_ID, data.getString("mall_home_id"));
                            S.set(SmMainActivity.this, C.KEY_JSON_POINT_CARD_NO, data.getString("point_card_no"));
                            S.set(SmMainActivity.this, C.KEY_JSON_PARENT_ID, data.getString("parent_id"));


                            mTabHost.setCurrentTabByTag(Tab);
                            changeTextColor(checkid);


                        } else {

                            checked = lastId0;
                            mTabButtonGroup.check(lastId0);
                            changeTextColor(lastId0);
                            lastId = lastId0;
                            if (needLogin)
                                context.startActivity(new Intent(context,
                                        LoginActivity.class));
                        }


                    } catch (Exception e) {

                        L.e(e);

                    }

                }


                @Override
                public void onBizFailure(String responseDescription,
                                         JSONObject data, String responseCode) {
                    if (data != null) {
                        Log.d("rsapp", data.toString());
                    }
                }
            }, C.CHECK_LOGIN
                    + "?deviceNo="
                    + Util.getDeviceId(context)
                    + "&sign="
                    + encryption(Util.getDeviceId(context)
                    + "ycssologin1212121212121"));
            Log.i("xyz", C.CHECK_LOGIN
                    + "?deviceNo="
                    + Util.getDeviceId(context)
                    + "&sign="
                    + encryption(Util.getDeviceId(context)
                    + "ycssologin1212121212121"));
        } catch (Exception e) {
            e.getMessage();

        }
    }

    private String encryption(String userPassword) {

        MessageDigest md;

        String encritPassword = "";

        try {
            md = MessageDigest.getInstance("SHA-512");

            md.update(userPassword.getBytes());
            byte[] mb = md.digest();
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                encritPassword += s;
            }

        } catch (Exception e) {
            String ecryption_error = e.toString();
        }

        return encritPassword;
    }

}
