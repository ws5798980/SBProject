package com.rs.mobile.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.R;
import com.rs.mobile.common.adapter.GroupAdapter;
import com.rs.mobile.common.entity.SelectGroupItem;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.view.RecycleViewDivider;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class SelectGroupActivity extends BaseActivity {
    private Spinner spinner;
    private RecyclerView mRecyclerView;
    private GroupAdapter mAdapter;
    private ImageView ivSearch;
    private EditText etWord;

    private List<SelectGroupItem.data> mDataList = new ArrayList<>();
    private int mNextRequestPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);

        initView();
    }

    private void initView(){
        spinner = (Spinner) findViewById(R.id.spinner);

        String[] mItems = {"단체명", "단체대표"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        etWord = (EditText) findViewById(R.id.et_word);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNextRequestPage = 1;
                requestGroupList(etWord.getText().toString(), ""+mNextRequestPage, "4");
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration( new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(SelectGroupActivity.this, LinearLayoutManager.VERTICAL));

        mAdapter = new GroupAdapter(R.layout.item_group, mDataList);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestGroupList(etWord.getText().toString(), ""+mNextRequestPage, "4");
            }
        }, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SelectGroupActivity.this, Register1Activity.class);
                intent.putExtra("RegFlag", 53);
                startActivity(intent);
                finish();
            }
        });
    }

    private void requestGroupList(String sWord, final String pg, String pageSize){
        final HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("s_word", sWord);
        params.put("pg", pg);
        params.put("pagesize", pageSize);

        OkHttpHelper okHttpHelper = new OkHttpHelper(SelectGroupActivity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                SelectGroupItem entity = GsonUtils.changeGsonToBean(responseDescription, SelectGroupItem.class);
                if(entity.data != null && entity.data.size() > 0){
                    mAdapter.loadMoreComplete();
                    if(mNextRequestPage == 1) {
                        mAdapter.setNewData(entity.data);
                    }else{
                        mAdapter.addData(entity.data);
                    }
                    mNextRequestPage++;
                }else{
                    mAdapter.loadMoreEnd();
                    mNextRequestPage = 1;
                    Toast.makeText(SelectGroupActivity.this, entity.msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://member."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8808/api/Group/requestGroupList", GsonUtils.createGsonString(params));
    }
}
