package com.rs.mobile.common.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.LoginActivity;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.util.UrlUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHelper {

    private Context context;

    private Handler handler;

    private OkHttpClient httpClient;

    private static final String GET = "GET";

    private static final String POST = "POST";

    private boolean isShowProgress = true;

    /**
     * contructor
     *
     * @param context
     */
    public OkHttpHelper(Context context) {

        if (Util.checkNetwork(context) == false)
            return;

        this.context = context;

        this.handler = new Handler(context.getMainLooper());

        this.httpClient = new OkHttpClient();
//		this.httpClient = new OkHttpClient.Builder().connectTimeout(30000, TimeUnit.SECONDS).build();

        this.isShowProgress = true;

    }

    /**
     * contructor
     *
     * @param context
     */
    public OkHttpHelper(Context context, boolean isShowProgress) {

        if (Util.checkNetwork(context) == false)
            return;

        this.context = context;

        this.handler = new Handler(context.getMainLooper());

        this.httpClient = new OkHttpClient();

        this.isShowProgress = isShowProgress;

    }

    /**
     * addGetRequest
     *
     * @param callbackLogic
     * @param baseUrl
     */
    public void addGetRequest(CallbackLogic callbackLogic, String baseUrl) {

        addRequest(callbackLogic, baseUrl);

    }

    /**
     * addPostRequest
     *
     * @param callbackLogic
     * @param baseUrl
     */
    public void addPostRequest(CallbackLogic callbackLogic, String baseUrl) {

        addRequest(callbackLogic, baseUrl);

    }

    public void addSMPostRequest(CallbackLogic callbackLogic, String baseUrl) {

        Map<String, String> paramsKeyValue = new HashMap<String, String>();

        paramsKeyValue.put(C.KEY_JSON_TOKEN,
                S.getShare(context, C.KEY_JSON_TOKEN, ""));

        addRequest(POST, callbackLogic, baseUrl, paramsKeyValue);

    }

    /**
     * addGetRequest
     *
     * @param callbackLogic
     * @param baseUrl
     * @param paramsKeyValue
     */
    public void addGetRequest(CallbackLogic callbackLogic, String baseUrl,
                              Map<String, String> paramsKeyValue) {

        addRequest(GET, callbackLogic, baseUrl, paramsKeyValue);

    }

    /**
     * addPostRequest
     *
     * @param callbackLogic
     * @param baseUrl
     * @param paramsKeyValue
     */
    public void addPostRequest(CallbackLogic callbackLogic, String baseUrl,
                               Map<String, String> paramsKeyValue) {

        addRequest(POST, callbackLogic, baseUrl, paramsKeyValue);

    }

    /**
     * addPostRequest
     *
     * @param callbackLogic
     * @param baseUrl
     */
    public void addPostRequest(CallbackLogic callbackLogic, String baseUrl, Map<String, String> headers, String body) {

        addRequest(POST, callbackLogic, baseUrl, headers, body);

    }

    public void addPostRequest(CallbackLogic callbackLogic, String baseUrl, String body) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;Charset=UTF-8");
        addRequest(POST, callbackLogic, baseUrl, headers, body);
    }

    public void addSMPostRequest(CallbackLogic callbackLogic, String baseUrl,
                                 Map<String, String> paramsKeyValue) {

        paramsKeyValue.put(C.KEY_JSON_TOKEN,
                S.getShare(context, C.KEY_JSON_TOKEN, ""));

        addRequest(POST, callbackLogic, baseUrl, paramsKeyValue);

    }

    /**
     * addRequest
     *
     * @param method         get/post
     * @param callbackLogic
     * @param baseUrl
     * @param paramsKeyValue
     */
    private void addRequest(String method, final CallbackLogic callbackLogic,
                            String baseUrl, Map<String, String> paramsKeyValue) {

        try {

            if (isShowProgress == true) {
                if (D.isshowing==false){
                    D.showProgressDialog(context, "", true);
                }

            }

            Request request = createRequest(baseUrl, method, paramsKeyValue);

            enqueue(request, callbackLogic);

        } catch (Exception e) {

            L.e(e);

        }

    }

    private void addRequest(String method, final CallbackLogic callbackLogic,
                            String baseUrl, Map<String, String> headers, String body) {

        try {

            if (isShowProgress == true) {

                if (D.isshowing==false){
                    D.showProgressDialog(context, "", true);
                }
            }

            Request request = createRequest(baseUrl, method, headers, body);

            enqueue(request, callbackLogic);

        } catch (Exception e) {

            L.e(e);

        }

    }

    /**
     * addRequest
     *
     * @param callbackLogic
     * @param baseUrl
     */
    private void addRequest(final CallbackLogic callbackLogic, String baseUrl) {

        try {

            if (isShowProgress == true) {

                if (D.isshowing==false){
                    D.showProgressDialog(context, "", true);
                }
            }

            Request request = createRequest(baseUrl);

            enqueue(request, callbackLogic);

        } catch (Exception e) {

            L.e(e);

        }

    }

    private void enqueue(Request request, CallbackLogic callbackLogic) {

        try {
            // httpClient.setCookieHandler(new CookieManager(new
            // PersistentCookieStore(context),CookiePolicy.ACCEPT_ALL));

            httpClient.newCall(request).enqueue(getCallBack(callbackLogic));

        } catch (Exception e) {

            L.e(e);

        }

    }

    /**
     * getCallBack
     *
     * @param callbackLogic
     * @return Callback
     */
    public Callback getCallBack(final CallbackLogic callbackLogic) {

        return new Callback() {

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                try {
                    if (D.isshowing){
                        D.hideProgressDialog();
                    }
                    callbackLogic.onBizFailure("", null, "");
                    if (callbackLogic != null) {
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                // T.showToast(context, request.toString());
                                // Toast.makeText(context, A.getToken(),
                                // Toast.LENGTH_SHORT).show();
                                //T.showToast(context, context.getString(com.rs.mobile.common.R.string.common1_text015));
                            }

                        });
                    }
                } catch (Exception ex) {
                    L.e(ex);
                }
            }

            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                try {

                    if (D.isshowing){
                        D.hideProgressDialog();
                    }
                    final String responseBody = arg1.body().string().trim();
                    L.d("789456" + responseBody);
                    final JSONObject json = new JSONObject(responseBody);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                // 토큰 갱신
                                if (json.has(C.KEY_JSON_FM_STATUS)) {

                                    String status = json.get(C.KEY_JSON_FM_STATUS).toString();

                                    //TODO 登录失败
                                    if (status.equals("-9001") || status.equals("1603") || status.equals("-1")) {
                                        if (json.has("message")) {
                                            T.showToast(context, json.getString("message"));
                                        } else if (json.has("msg")) {
                                            T.showToast(context, json.getString("msg"));
                                        }

                                        C.INTERFACE_PARAMS.clear();

                                        S.set(context, C.KEY_SHARED_KNICK_NAME, "");
                                        S.set(context, C.KEY_JSON_TOKEN, "");

                                        S.set(context, C.KEY_REQUEST_MEMBER_ID, "");

                                        S.setShare(context, C.KEY_JSON_TOKEN, "");

                                        S.setShare(context, C.KEY_REQUEST_MEMBER_ID, "");

                                        BaseActivity activity = (BaseActivity) context;
                                        Intent i = new Intent(context, LoginActivity.class);
                                        activity.startActivity(i);
                                        activity.finish();

                                        return;
                                    }
                                }
                                String flag = C.VALUE_RESPONSE_SUCCESS;
                                if (json != null
                                        && json.has(C.KEY_RESPONSE_FLAG)) {
                                    flag = json.getString(C.KEY_RESPONSE_FLAG);
                                }
                                callbackLogic.onBizSuccess(responseBody, json,
                                        flag);
                            } catch (Exception e) {
                                L.e(e);
                            }
                        }
                    });
                    L.d(responseBody);
                } catch (Exception e) {
                    L.e(e);
                    callbackLogic.onBizFailure("", null, "");
                }
            }

        };

    }

    private Request createRequest(String baseUrl) {
        return new Request.Builder().url(baseUrl).get().build();
    }

    private Request createRequest(String baseUrl, String method,
                                  Map<String, String> paramsKeyValue) {
        String fullUrl = null;

        if (method.equalsIgnoreCase(GET)) {
            fullUrl = UrlUtil.concatUrlAndParams(baseUrl,
                    UrlUtil.convertMapToHttpParams(paramsKeyValue));

            StringUtil.map_key_value(paramsKeyValue, baseUrl);
            return new Request.Builder().url(fullUrl).get().build();

        } else if (method.equalsIgnoreCase(POST)) {
            FormBody.Builder postFormBuilder = new FormBody.Builder();
//			FormEncodingBuilder postFormBuilder = new FormEncodingBuilder();
            for (Map.Entry<String, String> entry : paramsKeyValue.entrySet()) {
                postFormBuilder.add(entry.getKey(), entry.getValue());
            }
            StringUtil.map_key_value(paramsKeyValue, baseUrl);

            return new Request.Builder().url(baseUrl)
                    .post(postFormBuilder.build()).build();

        }

        return new Request.Builder().url(baseUrl).get().build();

    }

    private Request createRequest(String baseUrl, String method, Map<String, String> headers, String body) {

        Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : headers.entrySet()) {

            builder.addHeader(entry.getKey(), entry.getValue());

        }
        L.d(baseUrl);
        L.d(body);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, body);

        return builder.url(baseUrl).post(requestBody).build();


    }

    /**
     * @author ZZooN
     */
    public interface CallbackLogic {

        void onBizSuccess(String responseDescription, JSONObject data,
                          String flag);

        void onBizFailure(String responseDescription, JSONObject data,
                          String flag);

        void onNetworkError(Request request, IOException e);

    }

}
