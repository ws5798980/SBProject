package com.rs.mobile.wportal.fragment.xsgr;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.SmGoodsDetailActivity;
import com.rs.mobile.wportal.activity.xsgr.XsStoreDetailActivity;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreDetailMenuAdapter;
import com.rs.mobile.wportal.entity.StoreItemDetailEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class XsMenuFragment extends Fragment implements XsStoreDetailActivity.CallBackValue1{
    private View rootView;
    private RecyclerView mRecyclerView;
    private XsStoreDetailMenuAdapter mAdapter;

    private StoreItemDetailEntity mData;
    private String mSaleCustomCode;
    private int mNextRequestPage = 2;


    private JSONArray gridArr;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_xs_menu, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSaleCustomCode = getActivity().getIntent().getStringExtra("sale_custom_code");

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));



        mAdapter = new XsStoreDetailMenuAdapter(getActivity(), R.layout.layout_rt_menulist_item, mData!=null?mData.Storeitems:null);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestStoreItemDetail(S.get(getActivity(), C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, ""+AppConfig.latitude, ""+AppConfig.longitude, mNextRequestPage, "10");
            }
        }, mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.disableLoadMoreIfNotFullPage();

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    Intent intent = new Intent(getActivity(), SmGoodsDetailActivity.class);
                    Bundle bundle = new Bundle();
//                    String item_code = gridArr.getJSONObject(position).get(C.KEY_JSON_FM_ITEM_CODE).toString();
//                    bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
                    bundle.putString("SaleCustomCode", mData.custom_code);
                    bundle.putString("ItemCode", mData.Storeitems.get(position).item_code);
                    bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
                    intent.putExtras(bundle);
//                    intent.putExtra("SaleCustomCode", mData.custom_code);
//                    intent.putExtra("ItemCode", mData.Storeitems.get(position).item_code);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestStoreItemDetail(String customCode, String saleCustomCode, String latitude, String longitude, int pg, String pageSize){
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", customCode);
        params.put("sale_custom_code", saleCustomCode);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("pg", ""+pg);
        params.put("pagesize", pageSize);

        OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                StoreItemDetailEntity entity = GsonUtils.changeGsonToBean(responseDescription, StoreItemDetailEntity.class);
                if("1".equals(entity.status)){
                    try {
                        if(entity.Storeitems!=null && entity.Storeitems.size()>0){
                            mNextRequestPage++;
                            mAdapter.loadMoreComplete();
                            mAdapter.addData(entity.Storeitems);
                            mAdapter.loadMoreEnd(true);
                        }else{
                            mAdapter.loadMoreEnd(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{

                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL+"StoreCate/requestStoreItemDetail", GsonUtils.createGsonString(params));
    }

    @Override
    public void sendData(StoreItemDetailEntity entity) {
        mData = entity;
        mAdapter.setNewData(mData.Storeitems);
    }
}
