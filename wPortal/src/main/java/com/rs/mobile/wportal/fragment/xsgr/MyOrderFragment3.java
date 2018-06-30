package com.rs.mobile.wportal.fragment.xsgr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.OrderOneAdapter;
import com.rs.mobile.wportal.entity.OrderBean;
import com.rs.mobile.wportal.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MyOrderFragment3 extends BaseFragment {

    private OrderOneAdapter adapter1;
    private View rootView;
    RecyclerView recyclerView;
    private List<OrderBean.DataBean> list;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_order_done, container, false);
        list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            list.add(new OrderBean.DataBean());
        }

        initView(rootView);
        initdata();

        return rootView;
    }


    private void initdata() {

    }

    private void initView(View rootView) {


        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //添加空视图


        recyclerView = (RecyclerView) rootView.findViewById(R.id.listview_order_new);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter1 = new OrderOneAdapter(getContext(), R.layout.item_order_new, list);
        adapter1.bindToRecyclerView(recyclerView);
        adapter1.setEmptyView(emptyView);
        adapter1.disableLoadMoreIfNotFullPage();
        recyclerView.setAdapter(adapter1);
        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                if (adapter.getViewByPosition(recyclerView, position, R.id.layout_include).getVisibility() == View.VISIBLE) {
                    adapter.getViewByPosition(recyclerView, position, R.id.layout_include).setVisibility(View.GONE);
                    ((TextView) adapter.getViewByPosition(recyclerView, position, R.id.tv_order_new_pull)).setText(getResources().getString(R.string.orderopen));
                    ((ImageView) adapter.getViewByPosition(recyclerView, position, R.id.img_order_iocn)).setImageResource(R.drawable.icon_open_goods);
                } else {

                    adapter.getViewByPosition(recyclerView, position, R.id.layout_include).setVisibility(View.VISIBLE);
                    ((TextView) adapter.getViewByPosition(recyclerView, position, R.id.tv_order_new_pull)).setText(getResources().getString(R.string.orderclose));
                    ((ImageView) adapter.getViewByPosition(recyclerView, position, R.id.img_order_iocn)).setImageResource(R.drawable.icon_close_goods);

                }
            }

        });

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_ly);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                for (int i = 0; i < 5; i++) {
                    list.add(new OrderBean.DataBean());
                }
                adapter1.setEnableLoadMore(false);
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter1.setNewData(list);
                        adapter1.setEnableLoadMore(true);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);


            }
        });

        adapter1.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                for (int i = 6; i < 10; i++) {
                    list.add(new OrderBean.DataBean());
                }
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter1.setNewData(list);
                        adapter1.loadMoreComplete();

                    }
                }, 1000);


            }
        });

    }

    @Override
    protected void lazyLoad() {

    }
}
