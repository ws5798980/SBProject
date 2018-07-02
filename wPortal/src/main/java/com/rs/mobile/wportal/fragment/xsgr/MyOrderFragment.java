package com.rs.mobile.wportal.fragment.xsgr;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.XsMyShopActivity;
import com.rs.mobile.wportal.adapter.xsgr.OrderOneAdapter;
import com.rs.mobile.wportal.entity.BaseEntity;
import com.rs.mobile.wportal.entity.MyShopInfoBean;
import com.rs.mobile.wportal.entity.OrderBean;
import com.rs.mobile.wportal.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class MyOrderFragment extends BaseFragment {


    private int size = 5;
    private OrderBean bean;
    private OrderOneAdapter adapter1;
    private View rootView;
    RecyclerView recyclerView;
    private List<OrderBean.DataBean> list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_order_doing, container, false);
        list = new ArrayList<>();
        initView(rootView);
        initdata();
        return rootView;
    }

    private void initdata() {
        initShopInfoData();
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
        recyclerView.setAdapter(adapter1);
        adapter1.setEmptyView(emptyView);
        adapter1.disableLoadMoreIfNotFullPage();
        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                if (view.getId() == R.id.layout_open) {
                    if (adapter.getViewByPosition(recyclerView, position, R.id.layout_include).getVisibility() == View.VISIBLE) {
                        adapter.getViewByPosition(recyclerView, position, R.id.layout_include).setVisibility(View.GONE);
                        ((TextView) adapter.getViewByPosition(recyclerView, position, R.id.tv_order_new_pull)).setText(getResources().getString(R.string.orderopen));
                        ((ImageView) adapter.getViewByPosition(recyclerView, position, R.id.img_order_iocn)).setImageResource(R.drawable.icon_open_goods);
                    } else {
                        adapter.getViewByPosition(recyclerView, position, R.id.layout_include).setVisibility(View.VISIBLE);
                        ((TextView) adapter.getViewByPosition(recyclerView, position, R.id.tv_order_new_pull)).setText(getResources().getString(R.string.orderclose));
                        ((ImageView) adapter.getViewByPosition(recyclerView, position, R.id.img_order_iocn)).setImageResource(R.drawable.icon_close_goods);

                    }
                } else if (view.getId() == R.id.button1_cancel) {
                    changeOrderStatus(list.get(position).getOrder_num(), "4");
                } else if (view.getId() == R.id.button_get) {
                    changeOrderStatus(list.get(position).getOrder_num(), "3");
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_ly);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reinitdata();
            }
        });

        adapter1.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page = page + 1;
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
        param.put("pg", page + "");
        param.put("pagesize", "" + size);
        param.put("orderclassify", "1");
        OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                bean = GsonUtils.changeGsonToBean(responseDescription, OrderBean.class);
                list.addAll(bean.getData());
                adapter1.setNewData(list);
                adapter1.loadMoreComplete();
                if (bean.getData().size() < size) {
                    adapter1.loadMoreEnd();
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XS_BASE_URL + "AppSM/requestNewOrderList", param);

    }

    public void reinitdata(){
        list.clear();
        page = 1;
        initShopInfoData();
    }
    public void changeOrderStatus(String ordernum, final String status) {

        HashMap<String, String> param = new HashMap<String, String>();

        param.put("lang_type", AppConfig.LANG_TYPE);
//        param.put("token", S.getShare(XsMyShopActivity.this, C.KEY_JSON_TOKEN, ""));
//        param.put("custom_code", S.getShare(XsMyShopActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
        param.put("custom_code", "01071390103abcde");
        param.put("token", "186743935020f829f883e9fe-c8cf-4f60-9ed2-bd645cb1c118");
        param.put("order_num", ordernum);
        param.put("status", status);
        OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                Log.i("xyz", responseDescription);

                try {
                    JSONObject jsonObject = new JSONObject(responseDescription);
                    if ("1".equals(jsonObject.getString("status"))) {

                        if (status.equals("3")) {
                            showDialog();
                        } else {
                            reinitdata();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XS_BASE_URL  + "AppSM/requestOrderTake", param);

    }


    protected void showDialog() {
        final Dialog mDialog = new Dialog(getContext());

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.layout_getorder, null);


        Button okbt = (Button) dialogView.findViewById(R.id.button_ok);
        okbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reinitdata();
                mDialog.dismiss();
            }
        });

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                reinitdata();
            }
        });

        mDialog.setContentView(dialogView);
        mDialog.show();

    }


    @Override
    protected void lazyLoad() {

    }
}
