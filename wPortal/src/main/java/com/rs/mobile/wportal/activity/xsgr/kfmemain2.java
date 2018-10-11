package com.rs.mobile.wportal.activity.xsgr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.CaptureUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.NaverMap.CustomDialogListener;
import com.rs.mobile.wportal.activity.sm.SmSeachActivity;
import com.rs.mobile.wportal.adapter.xsgr.CustomListAdapter;
import com.rs.mobile.wportal.entity.ListItem;
import com.rs.mobile.wportal.network.HttpConnection;
import com.rs.mobile.wportal.view.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

public class kfmemain2 extends Activity implements View.OnClickListener,XListView.IXListViewListener{
    private ImageView btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15;
    private Integer tb_totalCount = 16;
    private Integer side_totalCount = 10;
    private ImageView[] iv_topH = new ImageView[tb_totalCount];
    private ImageView[] iv_bottomH = new ImageView[tb_totalCount];
    private ImageView[] iv_leftH = new ImageView[side_totalCount];
    private ImageView[] iv_rightH = new ImageView[side_totalCount];
    private TextView[] iv_topCollect = new TextView[tb_totalCount];
    private TextView[] tv_topCollect2 = new TextView[side_totalCount];
    private TextView[] tv_topCollect3 = new TextView[side_totalCount];
    private TextView[] tv_topCollect4 = new TextView[tb_totalCount];
    private TextView tv_dial_btn;
    private TextView AddressTitle;
    private ImageView btnSearch,btnScan;
    private LinearLayout new_index_main_btn_sns;
    private WebView web_view;
    private ScrollView LeftScrollView,programPlayParentScroll,RightScrollView;

    private EditText et_topNumber1txt, et_topNumber2txt, et_topNumber3txt, et_topNumber4txt;

    private ImageView[] iv_store_image;

    //public ListView lv_event_store_list;

    public Activity thisActivity;

    private XListView listView;
    public ArrayList<ListItem> listMockData;

    Double latitude = 0.0;
    Double longitude = 0.0;

    public int page=1;
    LocationManager manager;
    public custom_dialog cdialog;

    public CustomListAdapter customad=null;

    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
            {
                S.setShare(getApplicationContext(), "address_naver", msg.obj.toString());
                AddressTitle.setText(msg.obj.toString());
            }
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kfmemain);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog().build());

        thisActivity = this;

        AddressTitle = (TextView) findViewById(R.id._addresstitle);
        if(S.getShare(getApplicationContext(), "address_naver","").isEmpty())
        {
            AddressTitle.setText("검색 결과가 없습니다");
        } else {
            AddressTitle.setText(S.getShare(getApplicationContext(), "address_naver",""));
        }

        btn1 = (ImageView) findViewById(R.id.MainMenu01);
        btn2 = (ImageView) findViewById(R.id.MainMenu02);
        btn3 = (ImageView) findViewById(R.id.MainMenu03);
        btn4 = (ImageView) findViewById(R.id.MainMenu04);
        btn5 = (ImageView) findViewById(R.id.MainMenu05);
        btn6 = (ImageView) findViewById(R.id.MainMenu06);
        btn7 = (ImageView) findViewById(R.id.MainMenu07);
        btn8 = (ImageView) findViewById(R.id.MainMenu08);
        btn9 = (ImageView) findViewById(R.id.MainMenu09);
        btn10 = (ImageView) findViewById(R.id.MainMenu10);
        btn11 = (ImageView) findViewById(R.id.MainMenu11);
        btn12 = (ImageView) findViewById(R.id.MainMenu12);
        btn13 = (ImageView) findViewById(R.id.MainMenu13);
        btn14 = (ImageView) findViewById(R.id.MainMenu14);
        btn15 = (ImageView) findViewById(R.id.MainMenu15);

        btnSearch = (ImageView) findViewById(R.id.btn_serch);
        btnScan = (ImageView) findViewById(R.id.btn_scan);
        new_index_main_btn_sns= (LinearLayout) findViewById(R.id.new_index_main_btn_scan);
        LeftScrollView = (ScrollView) findViewById(R.id.LeftScrollView);
        programPlayParentScroll = (ScrollView) findViewById(R.id.programPlayParentScroll);
        RightScrollView= (ScrollView) findViewById(R.id.RightScrollView);

        LeftScrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                programPlayParentScroll.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        RightScrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                programPlayParentScroll.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);

        btnSearch.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        AddressTitle.setOnClickListener(this);
        new_index_main_btn_sns.setOnClickListener(this);

        et_topNumber1txt = (EditText) findViewById(R.id.topNumber1txt);
        et_topNumber2txt = (EditText) findViewById(R.id.topNumber2txt);
        et_topNumber3txt = (EditText) findViewById(R.id.topNumber3txt);
        et_topNumber4txt = (EditText) findViewById(R.id.topNumber4txt);

        et_topNumber1txt.addTextChangedListener(new TextWatcher() {

            private String beforeValue = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
                //Toast.makeText(thisActivity, s.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                //Toast.makeText(thisActivity, arg0.toString(), Toast.LENGTH_SHORT).show();
                if(!arg0.toString().isEmpty()) {
                    if (Integer.parseInt(arg0.toString()) < 17) {
                        if(arg0.length() >= 2)
                        {
                            et_topNumber2txt.requestFocus();
                            Log.d("afaf", "et_topNumber2txt.focus");
                        }
                    } else {
                        et_topNumber1txt.setText(beforeValue);
                        et_topNumber1txt.setSelection(et_topNumber1txt.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
                beforeValue = s.toString();
            }
        });

        et_topNumber2txt.addTextChangedListener(new TextWatcher() {

            private String beforeValue = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
                //Toast.makeText(thisActivity, s.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                //Toast.makeText(thisActivity, arg0.toString(), Toast.LENGTH_SHORT).show();
                if(!arg0.toString().isEmpty()) {
                    if (Integer.parseInt(arg0.toString()) < 11) {
                        if(arg0.length() >= 2 || (Integer.parseInt(arg0.toString()) > 1))
                        {
                            et_topNumber3txt.requestFocus();
                            Log.d("afaf", "et_topNumber2txt.focus");
                        }
                    } else {
                        et_topNumber2txt.setText(beforeValue);
                        et_topNumber2txt.setSelection(et_topNumber2txt.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
                beforeValue = s.toString();
            }
        });

        et_topNumber2txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_topNumber2txt.setSelection(et_topNumber2txt.length());
            }
        });

        et_topNumber3txt.addTextChangedListener(new TextWatcher() {

            private String beforeValue = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
                //Toast.makeText(thisActivity, s.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                //Toast.makeText(thisActivity, arg0.toString(), Toast.LENGTH_SHORT).show();
                if(!arg0.toString().isEmpty()) {
                    if (Integer.parseInt(arg0.toString()) < 11) {
                        if(arg0.length() >= 2 || (Integer.parseInt(arg0.toString()) > 1))
                        {
                            et_topNumber4txt.requestFocus();
                            Log.d("afaf", "et_topNumber2txt.focus");
                        }
                    } else {
                        et_topNumber3txt.setText(beforeValue);
                        et_topNumber3txt.setSelection(et_topNumber3txt.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
                beforeValue = s.toString();
            }
        });

        et_topNumber3txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_topNumber3txt.setSelection(et_topNumber3txt.length());
            }
        });

        et_topNumber4txt.addTextChangedListener(new TextWatcher() {

            private String beforeValue = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
                //Toast.makeText(thisActivity, s.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                //Toast.makeText(thisActivity, arg0.toString(), Toast.LENGTH_SHORT).show();
                if(!arg0.toString().isEmpty()) {
                    if (Integer.parseInt(arg0.toString()) < 17) {

                    } else {
                        et_topNumber4txt.setText(beforeValue);
                        et_topNumber4txt.setSelection(et_topNumber4txt.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
                beforeValue = s.toString();
            }
        });

        et_topNumber4txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_topNumber4txt.setSelection(et_topNumber4txt.length());
            }
        });


        //lv_event_store_list = (ListView) findViewById(R.id.event_store_list);

        if(S.getShare(getApplicationContext(), "address_naver", "").isEmpty()) {
            currentLocationAddress();
        }

        cdialog = new custom_dialog(this);
        cdialog.setCustomDL(new CustomDialogListener() {
            @Override
            public void onDialogBtnClickListener(View v) {
                switch(v.getId())
                {
                    case R.id.res_img_compass:
                        //Toast.makeText(getApplicationContext(),"compass", Toast.LENGTH_LONG).show();
                        /*Intent intent1 = new Intent(thisActivity, com.rs.mobile.wportal.activity.NaverMap.Activity_NaverMap_Main.class);
                        intent1.putExtra("wiche", "compass");
                        startActivityForResult(intent1, 1000);*/
                        currentLocationAddress();
                        //MainInitialization();
                        break;
                    case R.id.res_img_location:
                        //Toast.makeText(getApplicationContext(), "location", Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(thisActivity, com.rs.mobile.wportal.activity.NaverMap.Activity_NaverMap_Main.class);
                        intent2.putExtra("wiche", "location");
                        startActivityForResult(intent2, 1001);
                        break;
                }
                cdialog.dismiss();
            }
        });
        listMockData = new ArrayList<ListItem>();
        listView = (XListView) findViewById(R.id.sv_homepage);
        listView.setXListViewListener(kfmemain2.this);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                ListItem newsData = listMockData.get(position-1);

//                              Toast.makeText(thisActivity, "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();

//                String realUrl="http://www.gigaworld.co.kr:8080/newslist/NewsDetail.html?postid="+newsData.sale_custom_code;
                String realUrl="http://www.gigaworld.co.kr:8080/newslist/NewsDetail.html?postid="+newsData.sale_custom_code;
                Intent intent = new Intent(kfmemain2.this,WebActivity.class);
                intent.putExtra(C.KEY_INTENT_URL,realUrl);
                kfmemain2.this.startActivity(intent);

            }
        });


        MainInitialization();



        //Select Button
        tv_dial_btn = (TextView) findViewById(R.id.dial_btn);
        tv_dial_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(thisActivity, Integer.toString(wiche2), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent.putExtra("lv1", et_topNumber1txt.getText().toString());
                intent.putExtra("lv2", et_topNumber2txt.getText().toString());
                intent.putExtra("lv3", et_topNumber3txt.getText().toString());
                intent.putExtra("lv4", et_topNumber4txt.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void MainInitialization(){

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;Charset=UTF-8");
        JSONObject j1 = new JSONObject();
        try {

//            j1.put("lang_type", "kor");
//            j1.put("div_code", "2");
//            j1.put("custom_code", "");
//            j1.put("token", "");
//            j1.put("pg", "1");
//            j1.put("pagesize", "5");
//            j1.put("custom_lev1", "13");
//            j1.put("custom_lev2", "1");
//            j1.put("custom_lev3", "1");
//            j1.put("latitude", S.getShare(getApplicationContext(), "pointX", "1"));
//            j1.put("longitude", S.getShare(getApplicationContext(), "pointY", "1"));
//            j1.put("order_by", "1");

           j1.put("pageIndex", page);
           j1.put("pageSize", "10");

        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        OkHttpHelper helper = new OkHttpHelper(
                kfmemain2.this);
        helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request,
                                       IOException e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                Log.d("rsapp", data.toString());
                try {
                    if (data.getInt("status") == 1) {
                        JSONArray j_arry_store = data.getJSONArray("data");

                        int rcont=0;
                        iv_store_image = new ImageView[j_arry_store.length()];

                        if(page<=1) {
                            listMockData = new ArrayList<ListItem>();
                        }


                        for (int i = 0; i < j_arry_store.length(); i++) {

                            JSONObject jObject = j_arry_store.getJSONObject(i);
                            ListItem newsData = new ListItem();
                            newsData.setUrl(jObject.getString("TitlePic"));
                            newsData.custom_name = jObject.getString("Title");
                            newsData.sale_custom_code = jObject.getString("PostId");
                            newsData.date = jObject.getString("PostTime");
                            listMockData.add(newsData);
                            rcont++;
                        }
                        stopLoad();

                        customad=new CustomListAdapter(thisActivity, listMockData);
                        listView.setAdapter(customad);
                        customad.notifyDataSetChanged();

                        if(rcont<10)
                        {
                            listView.setPullLoadEnable(false);
                        }
                        else
                        {
                            listView.setPullLoadEnable(true);
                        }
                        setListViewHeightBasedOnChildren(listView);
                    }

                    Log.d("rsapp", data.toString());
                    //PageUtil.jumpTo(getApplicationContext(), MainActivity.class, null);
                } catch (JSONException ex) {
                    stopLoad();
                    Log.d("rsapp", data.toString());
                    Util.Debug_Toast_Message(getApplicationContext(), getApplicationContext().getString(R.string.rs_Command_Member_NO));
                }

            }

            @Override
            public void onBizFailure(
                    String responseDescription,
                    JSONObject data, String responseCode) {
                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
                // TODO Auto-generated method stub

                stopLoad();

            }
        }, "http://www.gigaworld.co.kr:8080/Newsgiga/GetNewListBysb", headers, j1.toString());

    }

    @Override
    public void onRefresh() {
        listView.setRefreshTime("刚刚");
        page = 1;
    }

    // 停止加载
    private void stopLoad(){
        listView.stopLoadMore();
        listView.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub
        page=page+1;
        MainInitialization();
    }

    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(XListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void scrollToView(View view, final ScrollView scrollView, int count) {
        if (view != null && view != scrollView) {
            count += view.getTop();
            scrollToView((View) view.getParent(), scrollView, count);
        } else if (scrollView != null) {
            final int finalCount = count;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, finalCount);
                }
            }, 200);
        }
    }


    //GPS Start
    public String currentLocationAddress() {

        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        long minTime = 1000;
        float minDistance = 1;

        if(ActivityCompat.checkSelfPermission(this.thisActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(thisActivity, "Don't have permissions.", Toast.LENGTH_LONG).show();
            return "noPermission";
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mLocationListener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, mLocationListener);
        return "";
    }


    //GPS Stop
    public void stopLocationService() {
        if (ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(thisActivity, "Don't have permissions.", Toast.LENGTH_LONG).show();
        }
    }

    //Mobile Phone GPS Get Value <-> Namver API Address Searche
    private HttpConnection httpConn = HttpConnection.getInstance();
    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            S.setShare(getApplicationContext(), "pointX", latitude.toString());
            S.setShare(getApplicationContext(), "pointY", longitude.toString());
            String ret_value;

            //ret_value = httpConn.NaverAPIAddressToLatlng("https://openapi.naver.com/v1/map/geocode", tv_serche_text.getText().toString(),"UTF-8,","4chFLI5miI3dKcMQ6paO","fLSoYJawMm");

            ret_value = httpConn.NaverAPILatingToAddress("https://openapi.naver.com/v1/map/reversegeocode",Double.toString(latitude), Double.toString(longitude),"UTF-8","4chFLI5miI3dKcMQ6paO","fLSoYJawMm");
            //{    "errorMessage": "검색 결과가 없습니다.",    "errorCode": "MP03"}
            try {
                String errorMessage = "";
                //JSONArray array = new JSONArray(ret_value);
                //for(int i = 0; i<array.length(); i++){
                JSONObject obj = new JSONObject(ret_value);
                if(obj.getString("errorCode") != "0")
                {
                    errorMessage = obj.getString("errorMessage");
                    Log.i("ErrorMessage : ", errorMessage);
                } else {

                }
                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = errorMessage;
                mHandler.sendMessage(msg);
                //}
            } catch (JSONException ex) {

            }

            stopLocationService();
        }

        public void onProviderDisabled(String provider) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // 수행을 제대로 한 경우-----
        if(requestCode == 0) {
            if (resultCode == RESULT_OK && data != null) {
//                String result = data.getStringExtra("resultSetting");
                CaptureUtil.handleResultScaning(kfmemain2.this,
                        data.getStringExtra("result"), "");


            }
            // 수행을 제대로 하지 못한 경우
            else if (resultCode == RESULT_CANCELED) {

            }
        } else if(requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                AppConfig.address = result;
                AddressTitle.setText(result);

            }
        } else if(requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                AddressTitle.setText(result);
            }
        }
        //--------
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.MainMenu01:
                Intent intent1 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent1.putExtra("lv1", "1");
                startActivity(intent1);
                break;
            case R.id.MainMenu02:
                Intent intent2 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent2.putExtra("lv1", "2");
                startActivity(intent2);
                break;
            case R.id.MainMenu03:
                Intent intent3 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent3.putExtra("lv1", "3");
                startActivity(intent3);
                break;
            case R.id.MainMenu04:
                Intent intent4 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent4.putExtra("lv1", "4");
                startActivity(intent4);
                break;
            case R.id.MainMenu05:
                Intent intent5 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent5.putExtra("lv1", "5");
                startActivity(intent5);
                break;
            case R.id.MainMenu06:
                Intent intent6 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent6.putExtra("lv1", "6");
                startActivity(intent6);
                break;
            case R.id.MainMenu07:
                Intent intent7 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent7.putExtra("lv1", "7");
                startActivity(intent7);
                break;
            case R.id.MainMenu08:
                Intent intent8 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent8.putExtra("lv1", "8");
                startActivity(intent8);
                break;
            case R.id.MainMenu09:
                Intent intent9 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent9.putExtra("lv1", "9");
                startActivity(intent9);
                break;
            case R.id.MainMenu10:
                Intent intent10 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent10.putExtra("lv1", "10");
                startActivity(intent10);
                break;
            case R.id.MainMenu11:
                Intent intent11 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent11.putExtra("lv1", "11");
                startActivity(intent11);
                break;
            case R.id.MainMenu12:
                Intent intent12 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent12.putExtra("lv1", "12");
                startActivity(intent12);
                break;
            case R.id.MainMenu13:
                Intent intent13 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent13.putExtra("lv1", "13");
                startActivity(intent13);
                break;
            case R.id.MainMenu14:
                Intent intent14 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent14.putExtra("lv1", "14");
                startActivity(intent14);
                break;
            case R.id.MainMenu15:
                Intent intent15 = new Intent(kfmemain2.this, XsStoreListActivity.class);
                intent15.putExtra("lv1", "15");
                startActivity(intent15);
                break;
            case R.id.btn_serch:
                Intent intentSearch = new Intent(kfmemain2.this, SmSeachActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.btn_scan:
                Intent intentScan = new Intent(kfmemain2.this, CaptureActivity.class);
                startActivityForResult(intentScan, 0);
                break;
            case R.id._addresstitle://-----
                //cdialog = new custom_dialog(this);
                cdialog.show();
                break;
            case R.id.new_index_main_btn_sns:
                UtilClear.IntentToLongLiao(kfmemain2.this,"cn.sbapp.im","cn.sbapp.im.ui.activity.MainActivity");
                break;
        }
    }

    class MyWebViewClient extends WebViewClient {

        ProgressDialog progressDialog;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//网页页面开始加载的时候
//            if (progressDialog == null) {
//                progressDialog = new ProgressDialog(IMWebActivity.this);
//                progressDialog.setMessage("加载中...");
//                progressDialog.show();
//                mWebView.setEnabled(false);// 当加载网页的时候将网页进行隐藏
//            }

            web_view.setEnabled(false);// 当加载网页的时候将网页进行隐藏
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {//网页加载结束的时候
            //super.onPageFinished(view, url);
//            if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//                progressDialog = null;
//                mWebView.setEnabled(true);
//            }
            web_view.setEnabled(true);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { //网页加载时的连接的网址
            if(!url.contains("NewsList.html")){
                //对新的URL进行截取，去掉前面的newtab:
               // String realUrl=url.substring(7,url.length());
                String realUrl=url;
                Intent intent = new Intent(kfmemain2.this,WebActivity.class);
                intent.putExtra(C.KEY_INTENT_URL,realUrl);
                kfmemain2.this.startActivity(intent);
            }else{
                view.loadUrl(url);
            }
            return true;
        }
    }
}

