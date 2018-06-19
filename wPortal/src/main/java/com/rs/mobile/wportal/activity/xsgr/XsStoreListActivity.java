package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.CaptureUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.SmNew_shopcatList;
import com.rs.mobile.wportal.activity.sm.SmSeachActivity;
import com.rs.mobile.wportal.adapter.ShopListGridListAdapter;
import com.rs.mobile.wportal.adapter.xsgr.Lv3MoreAdapter;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreListAdapter;
import com.rs.mobile.wportal.entity.Category3ListEntity;
import com.rs.mobile.wportal.entity.ListItem;
import com.rs.mobile.wportal.entity.StoreCateListEntity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class XsStoreListActivity extends AppCompatActivity implements TextView.OnClickListener{
    private TabLayout tabLayout1, tabLayout2;
    private TextView tvLv1More, tvLv2More;
    private TextView radio1, radio2, radio3;
    private LinearLayout btnBack;
    private RecyclerView mRecyclerView;
    private XsStoreListAdapter mAdapter;
    private RecyclerView mRecyclerView2;
    private Lv3MoreAdapter mAdapter2;
    private LinearLayout llLv3;
    private TextView tvAddress;
    private LinearLayout btnMap, btnSearch;

    private List<String> mTitles1 = new ArrayList<>();
    private List<String> mTitles2 = new ArrayList<>();
    private List<StoreCateListEntity.Store> mStoreList = new ArrayList<>();
    private List<Category3ListEntity.lev3> mLev3List = new ArrayList<>();

    private int mPreLv2Position = -1;
    private String mLv1Position = "1";
    private int mLv2Position = 1;
    private int mLv3Position = 1;
    private boolean mLv2FirstFlag = true;
    private boolean mLv3FirstFlag = true;
    private String mOrderBy = "1";
    private int mNextRequestPage = 2;
    private LinearLayout linear_showcat1=null,linear_viewcat1=null;
    private ImageView btnScan;


    private GridView gridview,gridview2,gridview3,gridview4;
    private int[] icon = { R.drawable.icon_all,
            R.drawable.icon_classification_01, R.drawable.icon_classification_02, R.drawable.icon_classification_03,
            R.drawable.icon_classification_04, R.drawable.icon_classification_05};



    private List<ListItem> allcatlist = new ArrayList<>();
    private ShopListGridListAdapter shoplistcatAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_store_list);

        initView();
        initData();
        initList();
        initSelected();
        initLv3Data();
    }

    private void initgridview()
    {
        gridview = (GridView) findViewById(R.id.gridview);
        shoplistcatAd=new ShopListGridListAdapter(this,allcatlist);
        //配置适配器
        gridview.setAdapter(shoplistcatAd);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView adapterView, View view, int arg2, long arg3)
                                            {

                                                if(!mLv3FirstFlag){
                                                    String lv="";
                                                    if(arg2>0)
                                                    {
                                                        lv=allcatlist.get(arg2).getHeadline();
                                                    }
                                                    requestStoreCateList(mLv1Position, ""+mLv2Position, lv, mOrderBy, 1);
                                                }
                                                mLv3FirstFlag = false;
                                                linear_viewcat1.setVisibility(View.GONE);
                                            }
                                        }
        );
    }

    private void initLv3Data(){
        mRecyclerView2 = (RecyclerView) findViewById(R.id.rv_list2);
        mRecyclerView2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        mAdapter2 = new Lv3MoreAdapter(R.layout.list_item_lv3, mLev3List);
        mRecyclerView2.setAdapter(mAdapter2);

        mAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tabLayout2.getTabAt(position).select();
                requestStoreCateList(mLv1Position, ""+mLv2Position, ""+(position+1), mOrderBy, mNextRequestPage);
                llLv3.setVisibility(View.GONE);
            }
        });
    }

    private void initList(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

       mAdapter = new XsStoreListAdapter(this, R.layout.list_item_xs_store_list, mStoreList);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestStoreCateList(mLv1Position, ""+mLv2Position, "1", mOrderBy, mNextRequestPage);
                return;

            }
        }, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(XsStoreListActivity.this, XsStoreDetailActivity2.class);
                intent.putExtra("custom_name", mStoreList.get(position).custom_name);
                intent.putExtra("sale_custom_code", mStoreList.get(position).custom_code);
                startActivity(intent);
            }
        });
    }

    private void initSelected(){
        tvLv1More = (TextView) findViewById(R.id.tv_lv1_more);
        tvLv2More = (TextView) findViewById(R.id.tv_lv2_more);
        radio1 = (TextView) findViewById(R.id.radio1);
        radio2 = (TextView) findViewById(R.id.radio2);
        radio3 = (TextView) findViewById(R.id.radio3);

        tvLv1More.setOnClickListener(this);
        tvLv2More.setOnClickListener(this);
        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
        radio3.setOnClickListener(this);
    }

    private void initView(){
        btnBack = (LinearLayout) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        llLv3 = (LinearLayout) findViewById(R.id.ll_lv3);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvAddress.setText(S.getShare(getApplicationContext(), "address_naver", ""));

        tabLayout1 = (TabLayout) findViewById(R.id.tab_layout1);
        tabLayout2 = (TabLayout) findViewById(R.id.tab_layout2);

        btnMap = (LinearLayout) findViewById(R.id.btn_map);
        btnSearch = (LinearLayout) findViewById(R.id.btn_serch);
        btnMap.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        linear_showcat1=(LinearLayout) findViewById(R.id.linear_showcat1);
        linear_showcat1.setOnClickListener(this);

        linear_viewcat1=(LinearLayout) findViewById(R.id.linear_viewcat1);
        linear_viewcat1.setOnClickListener(this);

        btnScan = (ImageView) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(this);
    }

    private void initData(){
        mLv1Position = getIntent().getStringExtra("lv1");
        if(getIntent().getStringExtra("lv2") != null){
            mLv2Position = Integer.parseInt(getIntent().getStringExtra("lv2"));
        }
        if(getIntent().getStringExtra("lv3") != null){
            mLv3Position = Integer.parseInt(getIntent().getStringExtra("lv3"));
        }
        if(mLv1Position == null || mLv1Position.isEmpty()){
            mLv1Position = "1";
            btnBack.setVisibility(View.GONE);
        }else{
            btnBack.setVisibility(View.VISIBLE);
        }

        tabLayout1.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(!mLv2FirstFlag){
                    if(mLv2Position != tab.getPosition()+1){
                        mLv2Position = tab.getPosition()+1;
                        requestStoreCateList(mLv1Position, ""+mLv2Position, "1", mOrderBy, 1);
                    }
                }
                mLv2FirstFlag = false;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout2.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(!mLv3FirstFlag){
                    if(mLv3Position != tab.getPosition()+1){
                        mLv3Position = tab.getPosition()+1;
                        requestStoreCateList(mLv1Position, ""+mLv2Position, ""+mLv3Position, mOrderBy, 1);
                    }
                }
                mLv3FirstFlag = false;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        requestStoreCateList(mLv1Position, ""+mLv2Position, ""+mLv3Position, "1", 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            mLv1Position = data.getStringExtra("lev1");
            mLv2Position = Integer.parseInt(data.getStringExtra("lev2"));
            requestStoreCateList(mLv1Position, ""+mLv2Position, "1", mOrderBy, 1);
        }else if(requestCode == 1000 && resultCode == RESULT_OK){
            if(requestCode == 1000) {
                if (resultCode == RESULT_OK) {
                    String result = data.getStringExtra("result");
                    AppConfig.address = result;
                    tvAddress.setText(result);

                }
            }
        }
       else if(requestCode == 0) {
            if (resultCode == RESULT_OK && data != null) {
//                String result = data.getStringExtra("resultSetting");
                CaptureUtil.handleResultScaning(XsStoreListActivity.this,
                        data.getStringExtra("result"), "");


            }
            // 수행을 제대로 하지 못한 경우
            else if (resultCode == RESULT_CANCELED) {

            }
        }  else if(requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                //AddressTitle.setText(result);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_lv1_more:
                Intent intent = new Intent(XsStoreListActivity.this, Lv1MoreActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,1);
                break;
            case R.id.tv_lv2_more:
                if(!llLv3.isShown()){
                    category3List(""+mLv1Position, ""+mLv2Position);
                }else{
                    llLv3.setVisibility(View.GONE);
                }
                break;
            case R.id.radio1:
                radio1.setBackgroundResource(R.drawable.new_shop_shape1);
                radio2.setBackgroundResource(R.drawable.btn_select_off);
                radio3.setBackgroundResource(R.drawable.btn_select_off);

                radio1.setTextColor(Color.parseColor("#ffffff"));
                radio2.setTextColor(Color.parseColor("#a0a0a0"));
                radio3.setTextColor(Color.parseColor("#a0a0a0"));

                mOrderBy = "1";
                requestStoreCateList(mLv1Position, ""+mLv2Position, ""+mLv3Position, mOrderBy, 1);
                break;
            case R.id.radio2:
                radio1.setBackgroundResource(R.drawable.btn_select_off);
                radio2.setBackgroundResource(R.drawable.new_shop_shape1);
                radio3.setBackgroundResource(R.drawable.btn_select_off);

                radio1.setTextColor(Color.parseColor("#a0a0a0"));
                radio2.setTextColor(Color.parseColor("#21c043"));
                radio3.setTextColor(Color.parseColor("#a0a0a0"));

                mOrderBy = "2";
                requestStoreCateList(mLv1Position, ""+mLv2Position, ""+mLv3Position, mOrderBy, 1);
                break;
            case R.id.radio3:
                radio1.setBackgroundResource(R.drawable.btn_select_off);
                radio2.setBackgroundResource(R.drawable.btn_select_off);
                radio3.setBackgroundResource(R.drawable.new_shop_shape1);

                radio1.setTextColor(Color.parseColor("#a0a0a0"));
                radio2.setTextColor(Color.parseColor("#a0a0a0"));
                radio3.setTextColor(Color.parseColor("#21c043"));

                mOrderBy = "3";
                requestStoreCateList(mLv1Position, ""+mLv2Position, ""+mLv3Position, mOrderBy, 1);
                break;
            case R.id.btn_serch:
                Intent intentSearch = new Intent(XsStoreListActivity.this, SmSeachActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.btn_map:
                Intent intentMap = new Intent(XsStoreListActivity.this, com.rs.mobile.wportal.activity.NaverMap.Activity_NaverMap_Main.class);
                startActivityForResult(intentMap, 1000);
                break;
            case R.id.linear_showcat1:
                if(linear_viewcat1.getVisibility()==View.VISIBLE)
                {
                    linear_viewcat1.setVisibility(View.GONE);
                }
                else {
                    initgridview();
                    linear_viewcat1.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.linear_viewcat1:
                linear_viewcat1.setVisibility(View.GONE);
                break;
            case R.id.btnScan:
                Intent intentScan = new Intent(XsStoreListActivity.this, CaptureActivity.class);
                startActivityForResult(intentScan, 0);
                break;
        }
    }

    private void requestStoreCateList(String customLv1, final String customLv2, final String customLv3, String orderBy, final int pg) {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("div_code", S.get(XsStoreListActivity.this, C.KEY_JSON_DIV_CODE));
        params.put("custom_code", S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE));
        params.put("token", S.get(XsStoreListActivity.this, C.KEY_JSON_TOKEN));
        params.put("pg", ""+pg);
        params.put("pagesize", "5");
        params.put("custom_lev1", customLv1);
        params.put("custom_lev2", customLv2);
        params.put("custom_lev3", customLv3);
        params.put("latitude", ""+AppConfig.latitude);
        params.put("longitude", ""+AppConfig.longitude);
        params.put("order_by", orderBy);
        Log.i("123123", "pg="+pg);
        OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreListActivity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                StoreCateListEntity entity = GsonUtils.changeGsonToBean(responseDescription, StoreCateListEntity.class);
                Log.i("123123", "responseDescription="+responseDescription);
                if("1".equals(entity.status)){
                    if(mTitles1.size()<=0){
                        for(int i=0;i<entity.lev2s.size();i++){
                            mTitles1.add(entity.lev2s.get(i).lev_name);
                            tabLayout1.addTab(tabLayout1.newTab().setText(mTitles1.get(i)));
                        }
                    }

                    //获取弹出分类列表
//                    if(Integer.parseInt(customLv2) != mPreLv2Position){
//                        mPreLv2Position = Integer.parseInt(customLv2);
//
//                        mTitles2.clear();
//                        tabLayout2.removeAllTabs();
//
//
//                        allcatlist.clear();
//                        ListItem l2=new ListItem();
//                        l2.custom_name="全部";
//                        l2.setHeadline("");
//                        allcatlist.add(l2);
//
//                        for(int i=0;i<entity.lev3s.size();i++){
//                            mTitles2.add(entity.lev3s.get(i).lev_name);
//                            tabLayout2.addTab(tabLayout2.newTab().setText(mTitles2.get(i)));
//
//                            ListItem l=new ListItem();
//                            l.custom_name=entity.lev3s.get(i).lev_name;
//                            l.setUrl(entity.lev3s.get(i).image_url);
//                            l.setHeadline(entity.lev3s.get(i).lev);
//                            allcatlist.add(l);
//                        }
//                        initgridview();
//                    }
                    Log.i("123123", "mLv2Position="+mLv2Position);
                    Log.i("123123", "mLv3Position="+mLv3Position);
                    tabLayout1.getTabAt(mLv2Position-1).select();
                   // tabLayout2.getTabAt(mLv3Position-1).select();
                    if(pg == 1){
                        mStoreList = entity.storelist;
                        mNextRequestPage = 2;
                        mAdapter.setNewData(mStoreList);
                      //  mAdapter.loadMoreEnd(true);
                    }else{
                        if(entity.storelist != null && entity.storelist.size() > 0){
                            mNextRequestPage++;
                            mStoreList.addAll(entity.storelist);
                            mAdapter.loadMoreComplete();
                            mAdapter.addData(entity.storelist);
                        }else{
                            mAdapter.loadMoreComplete();
                            mAdapter.loadMoreEnd(true);
                        }
                    }
                }else{
 //                   allcatlist.clear();
//                    ListItem l2=new ListItem();
//                    l2.custom_name="全部";
//                    l2.setHeadline("");
//                    allcatlist.add(l2);
//                    initgridview();
                    mAdapter.loadMoreComplete();
                    mAdapter.loadMoreEnd(true);
                    Toast.makeText(XsStoreListActivity.this, entity.msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                mAdapter.loadMoreComplete();
                mAdapter.loadMoreEnd(true);
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
                mAdapter.loadMoreComplete();
                mAdapter.loadMoreEnd(true);
            }
        }, Constant.XS_BASE_URL+"StoreCate/requestStoreCateList", GsonUtils.createGsonString(params));
    }

    private void category3List(String customLev1, String customLev2){
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_lev1", customLev1);
        params.put("custom_lev2", customLev2);

        OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreListActivity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                Category3ListEntity entity = GsonUtils.changeGsonToBean(responseDescription, Category3ListEntity.class);

                if("1".equals(entity.status)){
                    mLev3List = entity.lev3s;
                    mAdapter2.setNewData(mLev3List);
                    llLv3.setVisibility(View.VISIBLE);
                }else{
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL + "StoreCate/GetCategory3List", GsonUtils.createGsonString(params));
    }

}
