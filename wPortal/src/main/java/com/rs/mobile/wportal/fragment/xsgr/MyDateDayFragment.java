package com.rs.mobile.wportal.fragment.xsgr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.DateDayAdapter;
import com.rs.mobile.wportal.adapter.xsgr.OrderOneAdapter;
import com.rs.mobile.wportal.fragment.BaseFragment;

public class MyDateDayFragment extends BaseFragment {

    private DateDayAdapter adapter;
    private View rootView;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_data_day, container, false);

        initView(rootView);


        return rootView;
    }


    private void initView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listview_date_today);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new DateDayAdapter(getContext());
        recyclerView.setAdapter(adapter);

        View header = LayoutInflater.from(getContext()).inflate(R.layout.header_order_day, recyclerView, false);
        adapter.setHeaderView(header);
    }


    @Override
    protected void lazyLoad() {

    }
}
