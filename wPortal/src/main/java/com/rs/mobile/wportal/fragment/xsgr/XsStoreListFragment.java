package com.rs.mobile.wportal.fragment.xsgr;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.XsStoreDetailActivity;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreListAdapter;
import com.rs.mobile.wportal.entity.StoreListItem;

import java.util.ArrayList;
import java.util.List;

public class XsStoreListFragment extends Fragment {
    private View rootView;
    private RecyclerView mRecyclerView;
    private XsStoreListAdapter mAdapter;

    private List<StoreListItem> mDataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_xs_store_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDataList = new ArrayList<>();
        StoreListItem item1 = new StoreListItem();
        item1.imgRes = R.drawable.product_circle01;
        item1.ratingBar = 4L;
        item1.productNm = "화덕애 한판";
        item1.reviews = "최근리뷰 0";
        item1.bossComments = "사장님댓글 0";
        item1.distance = "1.5km";
        item1.address = "인천시 부평구 부평5동";
        StoreListItem item2 = new StoreListItem();
        item2.imgRes = R.drawable.product_circle02;
        item2.ratingBar = 4L;
        item2.productNm = "화덕애 한판";
        item2.reviews = "최근리뷰 0";
        item2.bossComments = "사장님댓글 0";
        item2.distance = "2.5km";
        item2.address = "인천시 부평구 부평5동";
        mDataList.add(item1);
        mDataList.add(item2);

        mAdapter = new XsStoreListAdapter(getActivity(), R.layout.list_item_xs_store_list, mDataList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), XsStoreDetailActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
