package com.rs.mobile.wportal.fragment.xsgr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.XsBackOrderDetailActivity;
import com.rs.mobile.wportal.adapter.xsgr.BackOrderTwoAdapter;
import com.rs.mobile.wportal.entity.BaseEntity;
import com.rs.mobile.wportal.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MyBackOrderFragment2 extends BaseFragment {


    private View rootView;
    RecyclerView recyclerView;
    List list;
    BackOrderTwoAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_backorder_done, container, false);

        initView(rootView);


        return rootView;
    }

    private void initView(View rootView) {

        list = new ArrayList();
        for (int i = 0; i < 5; i++) {
            list.add(new BaseEntity(i + ""));
        }


        recyclerView = (RecyclerView) rootView.findViewById(R.id.listview_order_new);

        adapter = new BackOrderTwoAdapter(getContext(), R.layout.item_backorder_done, list);
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, null);
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

                        Intent intent = new Intent(getContext(), XsBackOrderDetailActivity.class);
                        startActivity(intent);

                        break;
                }


            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                for (int i = 6; i < 10; i++) {
                    list.add(new BaseEntity(i + ""));
                }
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewData(list);
                        adapter.loadMoreComplete();


                    }
                }, 1000);


            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void lazyLoad() {

    }
}
