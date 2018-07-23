package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.LoginActivity;
import com.rs.mobile.common.entity.LoginEntity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.EncryptUtils;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.SmSearchResultActivity;
import com.rs.mobile.wportal.adapter.sm.SmHzListViewAdapter;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreListAdapter;
import com.rs.mobile.wportal.entity.StoreCateListEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class XsShopListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private XsStoreListAdapter mAdapter;
    private LinearLayout btnBack;
    private TextView tvTitle;

    private Map<String, String> params = new HashMap<String, String>();
    private String keyWord;
    int pagesize, pageindex;
    private List<StoreCateListEntity.Store> mStoreList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_shop_list);

        initView();
        initData();
    }

    private void initView(){

        btnBack = (LinearLayout) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new XsStoreListAdapter(this, R.layout.list_item_xs_store_list, mStoreList);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(XsShopListActivity.this, XsStoreDetailActivity2.class);
                intent.putExtra("custom_name", mStoreList.get(position).custom_name);
                intent.putExtra("sale_custom_code", mStoreList.get(position).custom_code);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        keyWord = getIntent().getStringExtra("keyWord");
        initParams(keyWord, pageindex + "", pagesize + "");
        OkHttpHelper okHttpHelper = new OkHttpHelper(XsShopListActivity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                StoreCateListEntity entity = GsonUtils.changeGsonToBean(responseDescription, StoreCateListEntity.class);
                mStoreList = entity.storelist;
                mAdapter.setNewData(mStoreList);
                mAdapter.loadMoreEnd(true);
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {

            }
        }, Constant.XS_BASE_URL+"StoreCate/requestSearchWordStoreList", params);
    }

    private Map<String, String> initParams(String searchword, String pg, String pageSize) {
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", S.get(XsShopListActivity.this, C.KEY_JSON_CUSTOM_CODE));
        params.put("token", S.get(XsShopListActivity.this, C.KEY_JSON_TOKEN));
        params.put("searchword", searchword);
        params.put("latitude", "37.434668");
        params.put("longitude", "122.160742");
        params.put("pg", pg);
        params.put("PageSize", pageSize);

        return params;
    }
}
