package com.rs.mobile.wportal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

import org.json.JSONObject;

import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.View.OnClickListener;

public class CheckUpdate {

    public static void getScanInfo(final Activity context) {

        try {
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = packInfo.versionCode;


            Map<String, String> params = new HashMap<String, String>();
            params.put("action", "checkver");
            params.put("os", "3");
//            params.put("vercode", versionCode + "");
            params.put("vercode","11");
            OkHttpHelper okHttpHelper = new OkHttpHelper(context);
            okHttpHelper.addSMPostRequest(new CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {

                }

                @Override
                public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                    try {
                        if (data.getString("code").equals("200")) {
                            final JSONObject json = data.optJSONObject("data");
                            if (json.optInt("role") == 2) {//强制更新
                                new UpdateDialog(context, json.optString("curVerText"), false, new OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        new UpdateManager(context, json.optString("downUrl"), true).checkUpdateInfo();
                                    }
                                });

                            } else {//普通更新
                                new UpdateDialog(context, json.optString("curVerText"), true, new OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        new UpdateManager(context, json.optString("downUrl"), false).checkUpdateInfo();
                                    }
                                });
                            }


                        }
                    } catch (Exception e) {

                        L.e(e);

                    }
                }

                @Override
                public void onBizFailure(String responseDescription, JSONObject data, String flag) {

                }
            }, Constant.BASE_URL_UPDATE, params);

        } catch (Exception e) {

            L.e(e);

        }


    }

}
