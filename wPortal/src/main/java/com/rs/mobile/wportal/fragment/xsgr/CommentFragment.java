package com.rs.mobile.wportal.fragment.xsgr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.CommentAdapter;
import com.rs.mobile.wportal.entity.BaseEntity;
import com.rs.mobile.wportal.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends BaseFragment {

    private View rootView;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    List list;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comment, container, false);
        initView(rootView);


        return rootView;
    }

    private void initView(View rootView) {
        list = new ArrayList();
        for (int i = 0; i < 5; i++) {
            list.add(new BaseEntity(i + ""));
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.listview_comment);

        commentAdapter = new CommentAdapter(getContext(), R.layout.item_comment, list);
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //添加空视图
        commentAdapter.bindToRecyclerView(recyclerView);
        commentAdapter.setEmptyView(emptyView);
        commentAdapter.disableLoadMoreIfNotFullPage();



        commentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                for (int i = 6; i < 10; i++) {
                    list.add(new BaseEntity(i + ""));
                }
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        commentAdapter.setNewData(list);
                        commentAdapter.loadMoreComplete();

                    }
                }, 1000);


            }
        });



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(commentAdapter);


    }

    @Override
    protected void lazyLoad() {

    }
}
