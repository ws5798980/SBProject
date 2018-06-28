package com.rs.mobile.wportal.fragment.xsgr;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.rs.mobile.common.D;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.OrderOneAdapter;
import com.rs.mobile.wportal.biz.xsgr.CommodityList;
import com.rs.mobile.wportal.fragment.BaseFragment;
import com.rs.mobile.wportal.view.DividerItemDecoration;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class MyCommodityFragment2 extends BaseFragment {

    private MyCommodityAdapter adapter;
    private View rootView;
    RecyclerView recyclerView;
    private List<CommodityList.DataBean> list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int mNextRequestPage = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_commodity_item2, container, false);
        list = new ArrayList<>();
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
//        recyclerView = (RecyclerView) rootView.findViewById(R.id.swrecycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        adapter = new MyCommodityAdapter(R.layout.item_rv_swipemenu, null);
//        recyclerView.setAdapter(adapter);

        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //添加空视图
        recyclerView = (RecyclerView) rootView.findViewById(R.id.swrecycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.HORIZONTAL, R.drawable.divide_bg));
        adapter = new MyCommodityAdapter(getContext(), R.layout.item_rv_swipemenu, list);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEmptyView(emptyView);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//adapter.getViewByPosition(recyclerView, position, R.id.layout_include)
                if (view.getId() == R.id.get_shelves) {
                    D.showDialog(getContext(), -1, "提示", "確定上架此商品？", "确定", new View.OnClickListener() {

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

    private void requestStoreCateList(final int pg, String categoryId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
        params.put("selling", "0");
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
                        adapter.disableLoadMoreIfNotFullPage();
                        swipeRefreshLayout.setRefreshing(false);
                        //  mAdapter.loadMoreEnd(true);
                    } else {
                        if (entity.getData() != null && entity.getData().size() > 0) {
                            mNextRequestPage++;
                            Log.e("mNextRequestPage","------"+mNextRequestPage);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        requestStoreCateList(1,"0");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void lazyLoad() {

    }

    public class MyCommodityAdapter extends BaseQuickAdapter<CommodityList.DataBean, BaseViewHolder> {
        private Context context;

        public MyCommodityAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<CommodityList.DataBean> data) {
            super(layoutResId, data);
            this.context = context;
        }

        @Override
        protected void convert(final BaseViewHolder helper, CommodityList.DataBean item) {

            helper.getView(R.id.right).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    D.showDialog(getContext(), -1, "提示", "確定刪除此商品？", "确定", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            D.alertDialog.dismiss();
                            delProduct(item.getGroupId(),helper.getAdapterPosition());
                            EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);
                            easySwipeMenuLayout.resetStatus();
                        }
                    });


                }
            });
            helper.getView(R.id.right_menu_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    D.showDialog(getContext(), -1, "提示", "確定刪除此商品？", "确定", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            D.alertDialog.dismiss();
                            delProduct(item.getGroupId(),helper.getAdapterPosition());
                            EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);
                            easySwipeMenuLayout.resetStatus();
                        }
                    });

                }
            });

            helper.addOnClickListener(R.id.get_shelves);
//            helper.addOnClickListener(R.id.right_menu_2);
//            helper.addOnClickListener(R.id.right);
            helper.setText(R.id.commodity_name,item.getItem_name());
            helper.setText(R.id.commodity_num,item.getANum());
            helper.setText(R.id.commodity_price,item.getItem_p());
            Glide.with(context).load(item.getImage_url()).into((ImageView) helper.getView(R.id.image_title));

        }

    }
    private void changeProductsellstate(String groupId,int position) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
        params.put("selling", "1");
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

    private void delProduct(String groupId,int position) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
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
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_DELPRODUCT, GsonUtils.createGsonString(params));
    }
}
