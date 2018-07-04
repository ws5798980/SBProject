package com.rs.mobile.wportal.fragment.xsgr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.DateDayAdapter;
import com.rs.mobile.wportal.adapter.xsgr.OrderOneAdapter;
import com.rs.mobile.wportal.entity.DateDataBean;
import com.rs.mobile.wportal.fragment.BaseFragment;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class MyDateMonthFragment extends BaseFragment {

    private DateDayAdapter adapter;
    private View rootView;
    RecyclerView recyclerView;
    List list;
    int page = 0;
    int size = 5;
    DateDataBean bean;
    View header;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_data_day, container, false);

        initView(rootView);
        isPrepared = true;
        lazyLoad();
        return rootView;
    }

    private void initView(View rootView) {
        bean = new DateDataBean();
        list = new ArrayList();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listview_date_today);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new DateDayAdapter(getContext(), R.layout.item_date_today, list);
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //添加空视图
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEmptyView(emptyView);
        adapter.disableLoadMoreIfNotFullPage();


        recyclerView.setAdapter(adapter);

        header = LayoutInflater.from(getContext()).inflate(R.layout.header_order_day, recyclerView, false);


        adapter.setHeaderView(header);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initShopInfoData();
            }
        });
    }

    public void initShopInfoData() {

        HashMap<String, String> param = new HashMap<String, String>();

        param.put("lang_type", AppConfig.LANG_TYPE);
//        param.put("token", S.getShare(getContext(), C.KEY_JSON_TOKEN, ""));
//        param.put("custom_code", S.getShare(getContext(), C.KEY_JSON_CUSTOM_CODE, ""));
        param.put("custom_code", "01071390103abcde");
        param.put("token", "186743935020f829f883e9fe-c8cf-4f60-9ed2-bd645cb1c118");
        param.put("pg", (page + 1) + "");
        param.put("pagesize", "" + size);
        param.put("orderclassify", "1");
        param.put("periodclassify", "3");
        OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                bean = GsonUtils.changeGsonToBean(responseDescription, DateDataBean.class);
                list.addAll(bean.getData());
                page += 1;
                ((TextView) header.findViewById(R.id.tv_price)).setText(bean.getSale_order_o());
                ((TextView) header.findViewById(R.id.tv_num)).setText(bean.getSale_cnt());
                ((TextView) header.findViewById(R.id.tv_priceback)).setText(bean.getReturn_order_o());
                ((TextView) header.findViewById(R.id.tv_priceback)).setText(bean.getReturn_cnt());
                ((TextView) header.findViewById(R.id.textview_data)).setText("本月数据");
                ((TextView) header.findViewById(R.id.textview_order)).setText("本月订单");
                adapter.setNewData(list);
                adapter.loadMoreComplete();
                if (bean.getData().size() < size) {
                    adapter.loadMoreEnd();
                }
//                if (swipeRefreshLayout.isRefreshing()) {
//                    swipeRefreshLayout.setRefreshing(false);
//                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XS_BASE_URL + "AppSM/requestOrderSalesReport", param);

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        list.clear();
        adapter.setNewData(list);
        page = 0;
        initShopInfoData();

    }
}
