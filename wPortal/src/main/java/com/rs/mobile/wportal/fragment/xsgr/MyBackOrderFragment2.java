package com.rs.mobile.wportal.fragment.xsgr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.OrderOneAdapter;
import com.rs.mobile.wportal.fragment.BaseFragment;

public class MyBackOrderFragment2 extends BaseFragment {

    private OrderOneAdapter adapter;
    private View rootView;
    RecyclerView recyclerView;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_backorder_done, container, false);

        initView(rootView);



        return rootView;
    }

    private void initView(View rootView) {
        recyclerView= (RecyclerView) rootView.findViewById(R.id.listview_order_new);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//
//        adapter=new OrderOneAdapter(getContext());
//        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void lazyLoad() {

    }
}
