package com.rs.mobile.wportal.activity.market.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.zxing.WriterException;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.EncodingHandler;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.market.MarketMainActivity;
import com.rs.mobile.wportal.activity.market.MarketPayActivity;
import com.rs.mobile.wportal.activity.market.util.CommonAdapter;
import com.rs.mobile.wportal.activity.market.util.ListBeen;
import com.rs.mobile.wportal.activity.market.util.ViewHolder;
import com.rs.mobile.wportal.activity.market.view.SwipeMenuLayout;
import com.rs.mobile.common.activity.CaptureActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 线下超市
 */
public class F01_market extends Fragment {

    private View parentView = null;
    private ImageView mk_f01_qr, mk_f01_banner, mk_topay, mk_toqr;
    private ListView listView;
    private TextView textType ;
    private List<ListBeen> lists;
    private CommonAdapter<ListBeen> adapt;
    private MarketMainFragmentActivity activity = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        parentView = inflater.inflate(com.rs.mobile.wportal.R.layout.activity_f01_market, container,
                false);
        activity = (MarketMainFragmentActivity) getActivity();
        init();
        return parentView;
    }

    @SuppressLint({"SetJavaScriptEnabled", "InlinedApi"})
    public void init() {
        mk_f01_qr = (ImageView) parentView.findViewById(com.rs.mobile.wportal.R.id.mk_f01_qr);
        mk_f01_banner = (ImageView) parentView.findViewById(com.rs.mobile.wportal.R.id.mk_f01_banner);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, getActivity().getResources()
                .getDisplayMetrics().widthPixels / 5);
        mk_f01_banner.setLayoutParams(params);
        listView = (ListView) parentView.findViewById(com.rs.mobile.wportal.R.id.listView_car);
        mk_topay = (ImageView) parentView.findViewById(com.rs.mobile.wportal.R.id.mk_topay);
        textType = (TextView) parentView.findViewById(com.rs.mobile.wportal.R.id.textType);
        mk_toqr = (ImageView) parentView.findViewById(com.rs.mobile.wportal.R.id.mk_toqr);
        lists = new ArrayList<ListBeen>();
        try {
            mk_f01_qr
                    .setImageBitmap(EncodingHandler.createQRCode(
                            "1," + S.getShare(getActivity(), C.KEY_REQUEST_MEMBER_ID,
                                    ""),
                            getActivity().getResources().getDisplayMetrics().widthPixels / 3 * 2));
        } catch (WriterException e) {
            e.printStackTrace();
        }

        //支付
        mk_topay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                setOrder();
            }
        });

        // 扫码
        mk_toqr.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(),
                        CaptureActivity.class), 2000);
            }
        });
        adapt = new CommonAdapter<ListBeen>(getActivity(), lists,
                com.rs.mobile.wportal.R.layout.mk_item) {

            @Override
            public void convert(final ViewHolder holder,
                                final ListBeen listBeen, final int position,
                                View convertView) {
                holder.setText(com.rs.mobile.wportal.R.id.mk_goods_name, listBeen.getName());
                holder.setText(com.rs.mobile.wportal.R.id.mk_goods_price, "¥" + listBeen.getPrice());
                holder.setText(com.rs.mobile.wportal.R.id.mk_goods_num, listBeen.getQuantity() + "");
                // if(!listBeen.getUnitPrice().equals("") &&
                // !listBeen.getUnitQuantity().equals("") )
                holder.setText(com.rs.mobile.wportal.R.id.mk_goods_desc, listBeen.getUnitPrice()
                        + " " + listBeen.getUnitQuantity());
                holder.setText(com.rs.mobile.wportal.R.id.mk_goods_stock,
                        "库存：" + listBeen.getStockQuantity());
                if (listBeen.getLastStatus() == 1) {
                    holder.setVisible(com.rs.mobile.wportal.R.id.mk_goods_enough, true);
                    // holder.setVisible(R.id.mk_bg_enough, true);
                } else {
                    holder.setVisible(com.rs.mobile.wportal.R.id.mk_goods_enough, false);
                    // holder.setVisible(R.id.mk_bg_enough, false);
                }

                WImageView img = holder.getView(com.rs.mobile.wportal.R.id.mk_goods_img);
                ImageUtil.drawImageFromUri(listBeen.getImgUrl(), img);
                // 减少量
                holder.getView(com.rs.mobile.wportal.R.id.mk_btn_min).setOnClickListener(
                        new OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                if (listBeen.getQuantity() <= 1) {
                                    T.showToast(getActivity(), "无法再减少商品数量");
                                } else {
                                    modifyNum(listBeen.getCartOfDetailId(),
                                            (listBeen.getQuantity() - 1) + "");
                                }
                            }
                        });
                // 增加量
                holder.getView(com.rs.mobile.wportal.R.id.mk_btn_add).setOnClickListener(
                        new OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                modifyNum(listBeen.getCartOfDetailId(),
                                        (listBeen.getQuantity() + 1) + "");
                            }
                        });

                final SwipeMenuLayout swipeMenuLayout = holder
                        .getView(com.rs.mobile.wportal.R.id.swipeMenuLayout);
                // 可以根据自己需求设置一些选项(这里设置了IOS阻塞效果以及item的依次左滑、右滑菜单)
                swipeMenuLayout.setIos(true).setLeftSwipe(true);
                holder.setOnClickListener(com.rs.mobile.wportal.R.id.btn_delete,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteGoods(listBeen.getCartOfDetailId(),
                                        position);
                                // 在ListView里，点击侧滑菜单上的选项时，如果想让侧滑菜单同时关闭，调用这句话
                                swipeMenuLayout.quickClose();
                            }
                        });
            }
        };
        listView.setAdapter(adapt);
        rechange();
    }

    /**
     * 判断是否入场
     */
    public void rechange() {
        if (S.getShare(getActivity(), "tickets", "").equals("")) {
            parentView.findViewById(com.rs.mobile.wportal.R.id.line_qr_in)
                    .setVisibility(View.VISIBLE);
            parentView.findViewById(com.rs.mobile.wportal.R.id.line_qr_out).setVisibility(View.GONE);
            setQrCode();
        } else {
            parentView.findViewById(com.rs.mobile.wportal.R.id.line_qr_in).setVisibility(View.GONE);
            parentView.findViewById(com.rs.mobile.wportal.R.id.line_qr_out).setVisibility(
                    View.VISIBLE);
            getcarList();
        }
        activity.setEscMarket();
    }

    /**
     * 扫描进场操作
     */
    private void setQrCode() {
        OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity(), false);
        HashMap<String, String> params = new HashMap<>();
        params.put("token", S.getShare(getActivity(), C.KEY_JSON_TOKEN, ""));
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription,
                                     JSONObject data, String flag) {

                try {

                    if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
                        JSONObject jb = data.optJSONObject("ticketsInfo");
                        S.setShare(getActivity(), "tickets",
                                jb.optString("tickets"));
                        rechange();

                    } else if (data.getInt(C.KEY_JSON_FM_STATUS) == -36867) {
                        setQrCode();
                    } else {
                        T.showToast(getActivity(), data.optString("message"));
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onBizFailure(String responseDescription,
                                     JSONObject data, String flag) {
                setQrCode();

            }
        }, Constant.SM_BASE_URL + Constant.MK_GETTICKETS, params);
    }

    // 删除商品
    private void deleteGoods(String cartOfDetailId, final int position) {
        OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
        HashMap<String, String> params = new HashMap<>();
        params.put("tickets", S.getShare(getActivity(), "tickets", ""));
        params.put("cartOfDetailId", cartOfDetailId);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription,
                                     JSONObject data, String flag) {

                try {

                    if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
                        // 删除操作
                        lists.remove(position);
                        textType.setText(lists.size() + "");
                        adapt.notifyDataSetChanged();
                        if (lists.size() < 1) {
                            mk_topay.setVisibility(View.GONE);
                            parentView.findViewById(com.rs.mobile.wportal.R.id.mk_car_nothing)
                                    .setVisibility(View.VISIBLE);
                            parentView.findViewById(com.rs.mobile.wportal.R.id.line_carlist)
                                    .setVisibility(View.GONE);
                        }

                    } else if (data.getInt(C.KEY_JSON_FM_STATUS) == -9004) {
                        D.showAlertDialog(getActivity(), -1, "提示",
                                data.optString("message"), "重新扫码",
                                new OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        parentView
                                                .findViewById(com.rs.mobile.wportal.R.id.line_qr_in)
                                                .setVisibility(View.VISIBLE);
                                        parentView.findViewById(
                                                com.rs.mobile.wportal.R.id.line_qr_out)
                                                .setVisibility(View.GONE);
                                    }
                                });
                    } else {
                        T.showToast(getActivity(), data.optString("message"));
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onBizFailure(String responseDescription,
                                     JSONObject data, String flag) {

            }
        }, Constant.SM_BASE_URL + Constant.MK_DeleteOfflineCarts, params);

    }

    // 加减商品
    private void modifyNum(String cartOfDetailId, String quantity) {
        OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
        HashMap<String, String> params = new HashMap<>();
        params.put("tickets", S.getShare(getActivity(), "tickets", ""));
        params.put("cartOfDetailId", cartOfDetailId);
        params.put("quantity", quantity);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription,
                                     JSONObject data, String flag) {

                try {

                    if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
                        getcarList();
                    } else if (data.getInt(C.KEY_JSON_FM_STATUS) == -9004) {
                        D.showAlertDialog(getActivity(), -1, "提示",
                                data.optString("message"), "重新扫码",
                                new OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        parentView
                                                .findViewById(com.rs.mobile.wportal.R.id.line_qr_in)
                                                .setVisibility(View.VISIBLE);
                                        parentView.findViewById(
                                                com.rs.mobile.wportal.R.id.line_qr_out)
                                                .setVisibility(View.GONE);
                                    }
                                });
                    } else {
                        T.showToast(getActivity(), data.optString("message"));
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onBizFailure(String responseDescription,
                                     JSONObject data, String flag) {

            }
        }, Constant.SM_BASE_URL + Constant.MK_ModifyOfflineCarts, params);

    }

    // 回调扫描
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 2000) {
                String barCodeStr = data.getStringExtra("result");

                OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
                HashMap<String, String> params = new HashMap<>();
                params.put("tickets", S.getShare(getActivity(), "tickets", ""));
                params.put("barCodeStr", barCodeStr);
                okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

                    @Override
                    public void onNetworkError(Request request, IOException e) {

                    }

                    @Override
                    public void onBizSuccess(String responseDescription,
                                             JSONObject data, String flag) {

                        try {

                            if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
                                getcarList();
                            } else if (data.getInt(C.KEY_JSON_FM_STATUS) == -9004) {
                                D.showAlertDialog(getActivity(), -1, "提示",
                                        data.optString("message"), "重新扫码",
                                        new OnClickListener() {

                                            @Override
                                            public void onClick(View arg0) {
                                                parentView.findViewById(
                                                        com.rs.mobile.wportal.R.id.line_qr_in)
                                                        .setVisibility(
                                                                View.VISIBLE);
                                                parentView.findViewById(
                                                        com.rs.mobile.wportal.R.id.line_qr_out)
                                                        .setVisibility(
                                                                View.GONE);
                                            }
                                        });
                            } else if (data.getInt(C.KEY_JSON_FM_STATUS) == -36865) {
                                T.showToast(getActivity(),
                                        data.optString("message"));
                            } else {
                                T.showToast(getActivity(),
                                        data.optString("message"));
                            }

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onBizFailure(String responseDescription,
                                             JSONObject data, String flag) {

                    }
                }, Constant.SM_BASE_URL + Constant.MK_AddOfflineCarts, params);

            }

        }

    }

    // 获取购物车列表
    private void getcarList() {
        try {
            lists.clear();
            OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
            HashMap<String, String> params = new HashMap<>();
            params.put("tickets", S.getShare(getActivity(), "tickets", ""));
            okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {

                }

                @Override
                public void onBizSuccess(String responseDescription,
                                         JSONObject data, String flag) {

                    try {

                        if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {

                            JSONArray list = data.getJSONArray("data");
                            if (list != null && list.length() > 0) {
                                for (int i = 0; i < list.length(); i++) {
                                    lists.add(ListBeen.getCarList(list
                                            .optJSONObject(i)));
                                }
                                mk_topay.setVisibility(View.VISIBLE);
                                parentView.findViewById(com.rs.mobile.wportal.R.id.mk_car_nothing)
                                        .setVisibility(View.GONE);
                                parentView.findViewById(com.rs.mobile.wportal.R.id.line_carlist)
                                        .setVisibility(View.VISIBLE);
                                textType.setText(lists.size() + "");
                                adapt.setDatas(lists);
                            } else {// 购物车为空
                                mk_topay.setVisibility(View.GONE);
                                parentView.findViewById(com.rs.mobile.wportal.R.id.mk_car_nothing)
                                        .setVisibility(View.VISIBLE);
                                parentView.findViewById(com.rs.mobile.wportal.R.id.line_carlist)
                                        .setVisibility(View.GONE);
                            }
                        } else if (data.getInt(C.KEY_JSON_FM_STATUS) == -9004) {
                            D.showAlertDialog(getActivity(), -1, "提示",
                                    data.optString("message"), "重新扫码",
                                    new OnClickListener() {

                                        @Override
                                        public void onClick(View arg0) {
                                            parentView
                                                    .findViewById(com.rs.mobile.wportal.R.id.line_qr_in)
                                                    .setVisibility(View.VISIBLE);
                                            parentView.findViewById(
                                                    com.rs.mobile.wportal.R.id.line_qr_out)
                                                    .setVisibility(View.GONE);

                                        }
                                    });
                        } else {
                            T.showToast(getActivity(), data.optString("message"));
                        }

                    } catch (Exception e) {
                        D.showDialog(getActivity(), -1, "错误1", e.getMessage(), "确定", new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                    }
                }

                @Override
                public void onBizFailure(String responseDescription,
                                         JSONObject data, String flag) {

                }
            }, Constant.SM_BASE_URL + Constant.MK_CARTSLIST, params);

        } catch (Exception e) {
            D.showDialog(getActivity(), -1, "错误1", e.getMessage(), "确定", new OnClickListener() {

                @Override
                public void onClick(View v) {
                    D.alertDialog.dismiss();
                }
            });
        }
    }

    // 下单
    private void setOrder() {
        OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
        HashMap<String, String> params = new HashMap<>();
        params.put("tickets", S.getShare(getActivity(), "tickets", ""));
        params.put("token", S.getShare(getActivity(), C.KEY_JSON_TOKEN, ""));
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription,
                                     JSONObject data, String flag) {

                try {

                    if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
                        final String amount = data.optString("amount");
                        String order_code = data.optString("order_code");

                        Intent intent = new Intent(getActivity(),
                                MarketPayActivity.class);
                        intent.putExtra("amount", amount);
                        intent.putExtra("order_code", order_code);
                        getActivity().startActivity(intent);
                        getActivity().overridePendingTransition(
                                com.rs.mobile.wportal.R.anim.activity_open, 0);

                    } else if (data.getInt(C.KEY_JSON_FM_STATUS) == -9004) {
                        D.showAlertDialog(getActivity(), -1, "提示",
                                data.optString("message"), "重新扫码",
                                new OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        startActivity(new Intent(getActivity(),
                                                MarketMainActivity.class));
                                    }
                                });
                    } else if (data.getInt(C.KEY_JSON_FM_STATUS) == -16387) {// 库存不足
                        T.showToast(getActivity(), data.optString("message"));
                        JSONObject msPar = data.optJSONObject("messageParam");
                        JSONArray errorData = msPar.getJSONArray("errorData");
                        if (errorData != null && errorData.length() > 0) {
                            for (int i = 0; i < errorData.length(); i++) {
                                String itemCode = errorData.optJSONObject(i)
                                        .optString("itemCode");
                                String stockQuantity = errorData.optJSONObject(
                                        i).optString("stockQuantity");
                                for (int j = 0; j < lists.size(); j++) {
                                    if (itemCode.equals(lists.get(j).getCode())) {
                                        lists.get(j).setLastStatus(1);
                                        lists.get(j).setStockQuantity(
                                                stockQuantity);
                                        break;
                                    }
                                }
                            }
                            adapt.setDatas(lists);
                        }

                    } else {
                        T.showToast(getActivity(), data.optString("message"));
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onBizFailure(String responseDescription,
                                     JSONObject data, String flag) {

            }
        }, Constant.SM_BASE_URL + Constant.MK_CreateOfflineCartsOrder, params);

    }

}
