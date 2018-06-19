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
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.CaptureUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.MainActivity;
import com.rs.mobile.wportal.activity.NaverMap.CustomDialogListener;
import com.rs.mobile.wportal.activity.sm.SmNew_shopcatList;
import com.rs.mobile.wportal.activity.sm.SmSeachActivity;
import com.rs.mobile.wportal.adapter.xsgr.CustomListAdapter;
import com.rs.mobile.wportal.adapter.xsgr.XsIndexAdapter1;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreListAdapter;
import com.rs.mobile.wportal.entity.IndexImageItem;
import com.rs.mobile.wportal.entity.ListItem;
import com.rs.mobile.wportal.entity.StoreCateListEntity;
import com.rs.mobile.wportal.entity.StoreItemDetailEntity;
import com.rs.mobile.wportal.entity.XsStoreDetailMenuItem;
import com.rs.mobile.wportal.network.HttpConnection;
import com.rs.mobile.wportal.view.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class kfmemain extends Activity implements View.OnClickListener{
    private ImageView btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15,new_index_main_btn_sns2;
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
    private TextView AddressTitle,bnt_search;
    private ImageView btnSearch,btnScan;
    private LinearLayout new_index_main_btn_sns,thumbnailLinearLayout;
    private WebView web_view;
    private ScrollView LeftScrollView,programPlayParentScroll,RightScrollView;

    private EditText et_topNumber1txt, et_topNumber2txt, et_topNumber3txt, et_topNumber4txt,et_topNumber5txt;

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

    private RecyclerView mRecyclerView;
    private XsIndexAdapter1 adapter1;

    private List<IndexImageItem> indeximg=new ArrayList<>();
    private  XsStoreDetailMenuItem entity;

    private List<StoreCateListEntity.Store> mStoreList = new ArrayList<>();
    private XsStoreListAdapter mAdapter;
    private int mNextRequestPage=1;
    private String mOrderBy = "1";

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
        setContentView(R.layout.activity_kfmemain3);

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


        btnSearch = (ImageView) findViewById(R.id.btn_serch);
        btnScan = (ImageView) findViewById(R.id.btn_scan);
        new_index_main_btn_sns= (LinearLayout) findViewById(R.id.new_index_main_btn_scan);
        thumbnailLinearLayout= (LinearLayout) findViewById(R.id.thumbnailLinearLayout);
        bnt_search=(TextView) findViewById(R.id.bnt_search);

        new_index_main_btn_sns2= (ImageView) findViewById(R.id.new_index_main_btn_sns2);
//        LeftScrollView = (ScrollView) findViewById(R.id.LeftScrollView);
//        programPlayParentScroll = (ScrollView) findViewById(R.id.programPlayParentScroll);
      //  RightScrollView= (ScrollView) findViewById(R.id.RightScrollView);

//        LeftScrollView.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                programPlayParentScroll.requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });


        new_index_main_btn_sns2.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        AddressTitle.setOnClickListener(this);
        new_index_main_btn_sns.setOnClickListener(this);
        bnt_search.setOnClickListener(this);

        et_topNumber1txt = (EditText) findViewById(R.id.topNumber1txt);
        et_topNumber2txt = (EditText) findViewById(R.id.topNumber2txt);
        et_topNumber3txt = (EditText) findViewById(R.id.topNumber3txt);
        et_topNumber4txt = (EditText) findViewById(R.id.topNumber4txt);
        et_topNumber5txt= (EditText) findViewById(R.id.topNumber5txt);

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



        //Select Button
        tv_dial_btn = (TextView) findViewById(R.id.dial_btn);
        tv_dial_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(thisActivity, Integer.toString(wiche2), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(kfmemain.this, XsStoreListActivity.class);
//                intent.putExtra("lv1", et_topNumber1txt.getText().toString());
//                intent.putExtra("lv2", et_topNumber2txt.getText().toString());
//                intent.putExtra("lv3", et_topNumber3txt.getText().toString());
//                intent.putExtra("lv4", et_topNumber4txt.getText().toString());
//                startActivity(intent);

                String addata=et_topNumber1txt.getText().toString()+"-"+et_topNumber2txt.getText().toString()+"-"+et_topNumber3txt.getText().toString()+"-"+et_topNumber4txt.getText().toString()+"-"+et_topNumber5txt.getText().toString();
                requestFindShopInfo(addata,et_topNumber4txt.getText().toString(),et_topNumber5txt.getText().toString(),et_topNumber2txt.getText().toString(),et_topNumber3txt.getText().toString(),et_topNumber1txt.getText().toString());


            }
        });



        initList();

        requestStoreItemDetail();
        initselect();
    }

private  void initselect()
{
    List<String> list = new ArrayList<String>();
    list.add("모두");
    list.add("이");
    list.add("없");

    ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item,list);
    adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
    Spinner sp=(Spinner) findViewById(R.id.sp_select1);
    sp.setAdapter(adapter);

    List<String> list2 = new ArrayList<String>();
    list2.add("거리");
    list2.add("판매량");
    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item,list2);
    adapter2.setDropDownViewResource(android.R.layout.simple_list_item_checked);
    Spinner sp2=(Spinner) findViewById(R.id.sp_select2);
    sp2.setAdapter(adapter2);


    List<String> list3 = new ArrayList<String>();
    list3.add("한식");
    list3.add("중식");
    list3.add("양식");
    list3.add("일본요리");
    ArrayAdapter<String> adapter3=new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item,list3);
    adapter3.setDropDownViewResource(android.R.layout.simple_list_item_checked);
    Spinner sp3=(Spinner) findViewById(R.id.sp_select3);
    sp3.setAdapter(adapter3);


    ArrayAdapter<String> adapter4=new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item,list);
    adapter4.setDropDownViewResource(android.R.layout.simple_list_item_checked);
    Spinner sp4=(Spinner) findViewById(R.id.sp_select4);
    sp4.setAdapter(adapter4);

    ArrayAdapter<String> adapter5=new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item,list);
    adapter5.setDropDownViewResource(android.R.layout.simple_list_item_checked);
    Spinner sp5=(Spinner) findViewById(R.id.sp_select5);
    sp5.setAdapter(adapter5);


}
private void initPicList()
{
    //已经定义好的全局变量
    int listCount = 0;
    for(int i=0;i<entity.dataFav.size();i++){

        listCount++;

        LayoutInflater inflater = LayoutInflater.from(this);
        //加载布局
        View view = inflater.inflate(R.layout.horizontal_list_item1,null);
        //找到ImageView
        ImageView thumImg = (ImageView)view.findViewById(R.id.img_list_item);
        //设定图片宽高（80*80）
        int w = 120;
        int h = 120;
        //图片绑定设置

        Glide.with(this).load(entity.dataFav.get(i).image_url).into(thumImg);

        view.setTag(entity.dataFav.get(i));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           XsStoreDetailMenuItem.datafav d=(XsStoreDetailMenuItem.datafav)v.getTag();
           String catid=d.level1;

            if(catid.equals("1")) {
                Intent intent = new Intent(kfmemain.this, SmNew_shopcatList.class);
                intent.putExtra("lv1", catid);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(kfmemain.this, XsStoreListActivity.class);
                intent.putExtra("lv1", catid);
                startActivity(intent);
            }

            }
        });

        //添加到布局中
        thumbnailLinearLayout.addView(view);
    }


//    thumbnailLinearLayout.setOnTouchListener(new OnTouchListener() {
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            //获取到LinearLayout的宽度  注：在onCreat方法里面直接获取是0 所以要动态的获取
//            int width = thumbnailLinearLayout.getWidth();
//            //得到LinearLayout
//            int itemCount = thumbnailLinearLayout.getChildCount();
//            //计算出每一个child的平均宽度
//            float itemWidth = width/itemCount*1.0f;
//            float touchX = event.getX(0);
//            int coverCount = (int) (touchX/itemWidth);
//            thumbnailLinearLayout.getChildAt(coverCount).setBackgroundColor(Color.rgb(120, 120, 120));
//
//            String catid = entity.dataFav.get(coverCount).level1;
//            if(catid.equals("1")) {
//                Intent intent = new Intent(kfmemain.this, SmNew_shopcatList.class);
//                intent.putExtra("lv1", catid);
//                startActivity(intent);
//            }
//            else
//            {
//                Intent intent = new Intent(kfmemain.this, XsStoreListActivity.class);
//                intent.putExtra("lv1", catid);
//                startActivity(intent);
//            }
//
//            return false;
//        }
//    });
}

    private void initList()
    {

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new XsStoreListAdapter(this, R.layout.list_item_xs_store_list, mStoreList);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestStoreCateList("", "", "", mOrderBy, mNextRequestPage);
                return;

            }
        }, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(kfmemain.this, XsStoreDetailActivity2.class);
                intent.putExtra("custom_name", mStoreList.get(position).custom_name);
                intent.putExtra("sale_custom_code", mStoreList.get(position).custom_code);
                startActivity(intent);
            }
        });
    }

    private void requestFindShopInfo(String ad_num,String b_num,String c_num,String l_num,String r_num,String t_num) {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("div_code", S.get(kfmemain.this, C.KEY_JSON_DIV_CODE));
        params.put("custom_code", S.get(kfmemain.this, C.KEY_JSON_CUSTOM_CODE));
        params.put("token", S.get(kfmemain.this, C.KEY_JSON_TOKEN));
        params.put("ad_num", ad_num);
        params.put("b_num", b_num);
        params.put("c_num", c_num);
        params.put("l_num", l_num);
        params.put("r_num", r_num);
        params.put("t_num", t_num);
        params.put("latitude", ""+AppConfig.latitude);
        params.put("longitude", ""+AppConfig.longitude);
        OkHttpHelper okHttpHelper = new OkHttpHelper(kfmemain.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                try {
                    JSONObject data2=(JSONObject)data.getJSONObject("data");

                    Intent intent = new Intent(kfmemain.this, XsStoreDetailActivity2.class);
                    intent.putExtra("custom_name",data2.getString("visit_custom_name"));
                    intent.putExtra("sale_custom_code", data2.getString("visit_custom_code"));
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://apiAD.gigaroom.com:9280/api/apiNumeric/requestFindStore", GsonUtils.createGsonString(params));
    }


    private void requestStoreCateList(String customLv1, final String customLv2, final String customLv3, String orderBy, final int pg) {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("div_code", S.get(kfmemain.this, C.KEY_JSON_DIV_CODE));
        params.put("custom_code", S.get(kfmemain.this, C.KEY_JSON_CUSTOM_CODE));
        params.put("token", S.get(kfmemain.this, C.KEY_JSON_TOKEN));
        params.put("pg", ""+pg);
        params.put("pagesize", "5");
        params.put("custom_lev1", customLv1);
        params.put("custom_lev2", customLv2);
        params.put("custom_lev3", customLv3);
        params.put("latitude", ""+AppConfig.latitude);
        params.put("longitude", ""+AppConfig.longitude);
        params.put("order_by", orderBy);
        Log.i("123123", "pg="+pg);
        OkHttpHelper okHttpHelper = new OkHttpHelper(kfmemain.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                StoreCateListEntity entity = GsonUtils.changeGsonToBean(responseDescription, StoreCateListEntity.class);
                Log.i("123123", "responseDescription="+responseDescription);
                if("1".equals(entity.status)){

                    if(pg == 1){
                        mStoreList = entity.storelist;
                        mNextRequestPage = 2;
                        mAdapter.setNewData(mStoreList);
                        mAdapter.loadMoreEnd(true);
                    }else{
                        if(mStoreList != null && mStoreList.size() > 0){
                            mNextRequestPage++;
                            mStoreList.addAll(entity.storelist);
                            mAdapter.loadMoreComplete();
                            mAdapter.addData(entity.storelist);
                        }else{
                            mAdapter.loadMoreEnd(true);
                        }
                    }
                }else{
                    Toast.makeText(kfmemain.this, entity.msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL+"StoreCate/requestStoreCateList", GsonUtils.createGsonString(params));
    }


    private void requestStoreItemDetail(){
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);

        String custom_code=S.get(kfmemain.this, C.KEY_JSON_CUSTOM_CODE);
        String token=S.get(kfmemain.this, C.KEY_JSON_TOKEN);
        if(custom_code!=null && !custom_code.equals("") && token!=null && !token.equals("")) {
            params.put("user_id", custom_code);
            params.put("token",token);
        }
        else
        {
          params.put("custom_code", "");
          params.put("token", "");
        }

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {


                entity = GsonUtils.changeGsonToBean(responseDescription, XsStoreDetailMenuItem.class);
                if("1".equals(entity.status)){
                    try {
                        if(entity.dataFav!=null && entity.dataFav.size()>0)
                        {
                            initPicList();
                        }
                        if(entity.data!=null && entity.data.size()>0){


                            mRecyclerView = (RecyclerView) findViewById(R.id.rv_list1);
                            mRecyclerView.setLayoutManager(new GridLayoutManager(kfmemain.this, 5));
                            adapter1 = new XsIndexAdapter1(kfmemain.this, R.layout.layout_index_bigitem1, entity.data);
                            adapter1.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                                @Override
                                public void onLoadMoreRequested() {
                                    return;
                                }
                            }, mRecyclerView);


                            adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    String catid=entity.data.get(position).level1;
                                    if(catid.equals("1")) {
                                        Intent intent = new Intent(kfmemain.this, SmNew_shopcatList.class);
                                        intent.putExtra("lv1", catid);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(kfmemain.this, XsStoreListActivity.class);
                                        intent.putExtra("lv1", catid);
                                        startActivity(intent);
                                    }

                                }
                            });

                            mRecyclerView.setAdapter(adapter1);
                            adapter1.loadMoreComplete();
                            adapter1.loadMoreEnd(true);

                        }else{
                            adapter1.loadMoreEnd(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
                    adapter1.loadMoreComplete();
                    adapter1.loadMoreEnd(true);
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://mall.gigawon.co.kr:8800/api/StoreCate/requestStoreCate1FavList", GsonUtils.createGsonString(params));
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
                CaptureUtil.handleResultScaning(kfmemain.this,
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
            case R.id.btn_serch:
                Intent intentSearch = new Intent(kfmemain.this, SmSeachActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.btn_scan:
                Intent intentScan = new Intent(kfmemain.this, CaptureActivity.class);
                startActivityForResult(intentScan, 0);
                break;
            case R.id._addresstitle://-----
                //cdialog = new custom_dialog(this);
                cdialog.show();
                break;
            case R.id.new_index_main_btn_sns2:
                UtilClear.IntentToLongLiao(kfmemain.this,"cn.sbapp.im","cn.sbapp.im.ui.activity.MainActivity");
                break;
            case  R.id.bnt_search:
                requestStoreCateList("", "", "", "1", 1);
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
                Intent intent = new Intent(kfmemain.this,WebActivity.class);
                intent.putExtra(C.KEY_INTENT_URL,realUrl);
                kfmemain.this.startActivity(intent);
            }else{
                view.loadUrl(url);
            }
            return true;
        }
    }
}

