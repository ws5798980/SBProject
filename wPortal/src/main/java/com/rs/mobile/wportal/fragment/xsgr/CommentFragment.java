package com.rs.mobile.wportal.fragment.xsgr;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.CommentAdapter;
import com.rs.mobile.wportal.entity.BaseEntity;
import com.rs.mobile.wportal.entity.CommentBean;
import com.rs.mobile.wportal.fragment.BaseFragment;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class CommentFragment extends BaseFragment {

    private View rootView;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    List<CommentBean.ShopAssessDataBean> list;
    CommentBean bean;
    int page = 0;
    int size = 5;
    Dialog mDialog;
    int type = 1;

    public void setType(int type) {
        this.type = type;
    }

//    public void setdata(CommentBean bean) {
//        list.clear();
//        list.addAll(bean.getShopAssessData());
//        commentAdapter.setNewData(list);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comment, container, false);
        initView(rootView);
        isPrepared = true;
        lazyLoad();
        return rootView;
    }

    private void initView(View rootView) {
        list = new ArrayList();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.listview_comment);

        commentAdapter = new CommentAdapter(getContext(), R.layout.item_comment, list);
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //添加空视图
        commentAdapter.bindToRecyclerView(recyclerView);
        commentAdapter.setEmptyView(emptyView);
        commentAdapter.disableLoadMoreIfNotFullPage();


        commentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initShopInfoData(size, page,true);
            }
        });

        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                showDialog(position);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(commentAdapter);


    }


    public void initShopInfoData(final int size, int pagep, final boolean add) {
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(getContext(), C.KEY_JSON_TOKEN, ""));
        param.put("custom_code", S.getShare(getContext(), C.KEY_JSON_CUSTOM_CODE, ""));
//        param.put("custom_code", "010530117822fbe4");
//        param.put("token", "186743935020f829f883e9fe-c8cf-4f60-9ed2-bd645cb1c118");
        param.put("pg", (pagep+1) + "");
        param.put("pagesize", "" + size);
        param.put("view_gbn", type + "");
        OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                bean = GsonUtils.changeGsonToBean(responseDescription, CommentBean.class);

                list.addAll(bean.getShopAssessData());
                if (add){
                    page = page + 1;
                }
                commentAdapter.setNewData(list);
                commentAdapter.loadMoreComplete();
                if (bean.getShopAssessData().size() < size) {
                    commentAdapter.loadMoreEnd();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }
        }, Constant.XS_BASE_URL + "AppSM/requestAssessList", param);

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        list.clear();
        commentAdapter.setNewData(list);
        page = 0;
        initShopInfoData(size, page,true);
    }


    protected void showDialog(final int position) {
        mDialog = new Dialog(getContext());

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View dialogView = inflater.inflate(R.layout.dialog_comment, null);
        final EditText editText = (EditText) dialogView.findViewById(R.id.edit_comment);

        Button okbt = (Button) dialogView.findViewById(R.id.bt_ok);
        okbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment;
                if (editText.getText().length() == 0) {
                    comment = editText.getHint().toString();
                } else {
                    comment = editText.getText().toString();
                }

                comment(comment, list.get(position).getId());

            }
        });

        Button closebt = (Button) dialogView.findViewById(R.id.bt_close);

        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mDialog.setContentView(dialogView);
        mDialog.show();

    }


    public void comment(String comment, String assess_id) {
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(getContext(), C.KEY_JSON_TOKEN, ""));
        param.put("custom_code", S.getShare(getContext(), C.KEY_JSON_CUSTOM_CODE, ""));
//        param.put("custom_code", "010530117822fbe4");
//        param.put("token", "186743935020f829f883e9fe-c8cf-4f60-9ed2-bd645cb1c118");

        param.put("assess_id", "" + assess_id);
        param.put("sale_content", comment);
        OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                try {
                    JSONObject jsonObject = new JSONObject(responseDescription);
                    if ("1".equals(jsonObject.getString("status"))) {
                        int i = list.size();
                        list.clear();
                        initShopInfoData(i, 0,false);
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }
        }, Constant.XS_BASE_URL + "AppSM/requestSaleAssessUpdIns", param);

    }

}
