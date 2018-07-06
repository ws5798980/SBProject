package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.BackOrderOneAdapter;
import com.rs.mobile.wportal.biz.xsgr.BackOrderBean;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class BackOrderActivity extends BaseActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    BackOrderBean bean;
    RecyclerView recyclerView;
    List<BackOrderBean.DataBean> list;
    BackOrderOneAdapter adapter;
    int page = 0;
    int size = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_my_orderback);

        initView();

        initData();


        setListener();

    }

    private void initData() {
        initShopInfoData();
    }

    private void setListener() {

    }

    private void initView() {
        LinearLayout close_btn = (LinearLayout) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = new ArrayList();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        adapter = new BackOrderOneAdapter(this, R.layout.item_backorder_doing, list);

        View emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //添加空视图
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEmptyView(emptyView);
        adapter.disableLoadMoreIfNotFullPage();


        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.bt_check:
                        Intent intent = new Intent(BackOrderActivity.this, XsBackOrderDetailActivity.class);
                        intent.putExtra("item",list.get(position));
                        startActivity(intent);
                        break;
                }


            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initShopInfoData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(BackOrderActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                page = 0;
                initShopInfoData();
            }
        });

    }


    public void initShopInfoData() {

        HashMap<String, String> param = new HashMap<String, String>();

        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(BackOrderActivity.this, C.KEY_JSON_TOKEN, ""));
        param.put("custom_code", S.getShare(BackOrderActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
//        param.put("custom_code", "01071390103abcde");
//        param.put("token", "186743935020f829f883e9fe-c8cf-4f60-9ed2-bd645cb1c118");
        param.put("pg", (page + 1) + "");
        param.put("pagesize", "" + size);
        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                bean = GsonUtils.changeGsonToBean(responseDescription, BackOrderBean.class);
                list.addAll(bean.getData());
                page = page + 1;
                adapter.setNewData(list);
                adapter.loadMoreComplete();
                if (bean.getData().size() < size) {
                    adapter.loadMoreEnd();
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XS_BASE_URL + "AppSM/requestCancelReturn", param);

    }


}
