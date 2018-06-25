package com.rs.mobile.wportal.fragment.xsgr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.CommodityItemAdapter;
import com.rs.mobile.wportal.adapter.xsgr.OrderOneAdapter;
import com.rs.mobile.wportal.fragment.BaseFragment;
import com.rs.mobile.wportal.view.DividerItemDecoration;

public class MyCommodityFragment extends BaseFragment {

    private View rootView;
    RecyclerView recyclerView;
    CommodityItemAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_commodity_item, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        recyclerView= (RecyclerView) rootView.findViewById(R.id.swrecycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.HORIZONTAL, R.drawable.divide_bg));
        adapter=new CommodityItemAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       // recyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.swrecycler_view);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
