package com.rs.mobile.wportal.fragment.xsgr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import okhttp3.Request;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.ReeditActivity;
import com.rs.mobile.wportal.adapter.xsgr.CommodityItemAdapter;
import com.rs.mobile.wportal.biz.xsgr.CommodityList;
import com.rs.mobile.wportal.fragment.BaseFragment;
import com.rs.mobile.wportal.view.DividerItemDecoration;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyCommodityFragment extends BaseFragment {

    private View rootView;
    RecyclerView recyclerView;
    CommodityItemAdapter adapter;
    private List<CommodityList.DataBean> list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int mNextRequestPage = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_commodity_item, container, false);
        list = new ArrayList<>();

        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //添加空视图
        recyclerView = (RecyclerView) rootView.findViewById(R.id.swrecycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.HORIZONTAL, R.drawable.divide_bg));
        adapter = new CommodityItemAdapter(getContext(), R.layout.item_commodity_new, list);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEmptyView(emptyView);
        adapter.disableLoadMoreIfNotFullPage();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//adapter.getViewByPosition(recyclerView, position, R.id.layout_include)
                if (view.getId() == R.id.edit_goods) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("groupId",list.get(position).getGroupId());
                    intent.putExtras(bundle);
                    intent.setClass(getContext(), ReeditActivity.class);
                    startActivity(intent);
                }else if (view.getId() == R.id.get_shelves){
                    D.showDialog(getContext(), -1, "提示", "確定下架此商品？", "确定", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            D.alertDialog.dismiss();
                            changeProductsellstate(list.get(position).getGroupId(),position);

                        }
                    });
                }
            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_ly);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                mNextRequestPage = 2;
                requestStoreCateList(1,"0");
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestStoreCateList(mNextRequestPage,"0");

            }
        });

    }

    private void changeProductsellstate(String groupId,int position) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
        params.put("selling", "0");
        params.put("groupId", groupId);

        OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                CommodityList entity = GsonUtils.changeGsonToBean(responseDescription, CommodityList.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(entity.getStatus())) {
                    Toast.makeText(getContext(), entity.getMessage(), Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                } else {
                    Toast.makeText(getContext(), entity.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                Log.e("responseDescription456",responseDescription);
//                Log.e("JSONObject",data.toString());
                Log.e("flag145",flag);

            }

            @Override
            public void onNetworkError(Request request, IOException e) {

            }
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_CHANGESTATE, GsonUtils.createGsonString(params));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // recyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.swrecycler_view);
        requestStoreCateList(1,"0");
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void requestStoreCateList(final int pg, String categoryId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
        params.put("selling", 1);
        params.put("CategoryId", categoryId);
        params.put("page", pg);
        params.put("pageSize", 10);

        OkHttpHelper okHttpHelper = new OkHttpHelper(getContext(),false);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                CommodityList entity = GsonUtils.changeGsonToBean(responseDescription, CommodityList.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(entity.getStatus())) {
//                    if (list.size() <= 0) {
//                        list = entity.getData();
//                    }
                    if (pg == 1) {
                        list = entity.getData();
                        mNextRequestPage = 2;
                        adapter.setNewData(list);
                        swipeRefreshLayout.setRefreshing(false);
                        //  mAdapter.loadMoreEnd(true);
                    } else {
                        if (entity.getData() != null && entity.getData().size() > 0) {
                            mNextRequestPage++;
                            list.addAll(entity.getData());
                            adapter.loadMoreComplete();
                            adapter.addData(entity.getData());
                        } else {
                            adapter.loadMoreComplete();
                            adapter.loadMoreEnd(true);
                        }
                    }
                } else {

                    adapter.loadMoreComplete();
                    adapter.loadMoreEnd(true);
                    Toast.makeText(getContext(), entity.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                Log.e("responseDescription456",responseDescription);
//                Log.e("JSONObject",data.toString());
                Log.e("flag145",flag);
                adapter.loadMoreComplete();
                adapter.loadMoreEnd(true);
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
                adapter.loadMoreComplete();
                adapter.loadMoreEnd(true);
            }
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_ON, GsonUtils.createGsonString(params));
    }

}
