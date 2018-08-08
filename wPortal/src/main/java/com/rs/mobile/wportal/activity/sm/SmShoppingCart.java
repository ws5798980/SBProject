package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.XsStoreListActivity;
import com.rs.mobile.wportal.adapter.ShoppingCartParentAdapter;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.wportal.biz.ShoppingCartParent;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.entity.StoreCateListEntity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Request;

public class SmShoppingCart extends BaseActivity {

    private ListView listview;

    private static List<ShoppingCart> list;

    private List<ShoppingCartParent> listParent;

    private LinearLayout linear_back, linear_exchange;

    private CheckBox checkBox;

    private TextView textview;

    private TextView textview001, textView_complete, textView_edit, textView_002, textView_add, textView_delete;

    private ShoppingCartParentAdapter adapter;

    private LinearLayout linear_edit;

    public String Goods = "goods";

    public static SmShoppingCart smShoppingCart;
    public int mflag = 0;
    public String sale_custom_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {

            setContentView(com.rs.mobile.wportal.R.layout.activity_sm_shoppingcart);

            mflag = getIntent().getIntExtra("flag", 0);
            sale_custom_code = getIntent().getStringExtra("sale_custom_code");

            smShoppingCart = this;
            initView();

        } catch (Exception e) {

            L.e(e);

        }

    }

    @Override
    protected void onPause() {

        // TODO Auto-generated method stub
        super.onPause();

    }

    @Override
    protected void onResume() {

        // TODO Auto-generated method stub
        super.onResume();
        initData();
        textView_002.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + "0");
        checkBox.setChecked(false);
    }

    private Handler handler = new Handler() {

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            try {
                float price = getTotalPrice();
                if (msg.what == 10) { // 更改选中商品的总价格

                    if (price > 0) {
                        String priceStr = "" + price;
                        textView_002.setText(StringUtil.formatTosepara(price));
                    } else {
                        textView_002.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + "0");
                    }
                } else if (msg.what == 11) {

                    if (price > 0) {
                        String priceStr = "" + price;

                        textView_002.setText(StringUtil.formatTosepara(price));
                    } else {
                        textView_002.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + "0");
                    }

                    boolean isChecked = true;

                    for (int i = 0; i < list.size(); i++) {

                        if (list.get(i).isChoosed() == false) {

                            isChecked = false;

                            break;

                        }

                    }
                    if (list.size() == 0) {
                        isChecked = false;
                        textView_complete.setVisibility(View.GONE);
                        textview001.setVisibility(View.VISIBLE);
                        textView_002.setVisibility(View.VISIBLE);
                        linear_edit.setVisibility(View.GONE);
                        textView_edit.setVisibility(View.VISIBLE);
                        textview.setVisibility(View.VISIBLE);
                    }
                    checkBox.setChecked(isChecked);

                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {

                L.e(e);

            }
        }
    };

    private void initData() {
        if (mflag == 1) {
            getMyshoppingCartList2();
        } else {
            getMyshoppingCartList();
        }
    }

    private void initView() {

        try {
            boolean closeFlag = getIntent().getBooleanExtra("closeFlag", false);

            linear_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);
            if (closeFlag) {
                linear_back.setVisibility(View.GONE);
            }
            linear_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    // TODO Auto-generated method stub
                    finish();
                }
            });

            linear_exchange = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.linear_exchange);
            linear_exchange.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    // TODO Auto-generated method stub
                    t("服务准备中");
                }
            });
            listview = (ListView) findViewById(com.rs.mobile.wportal.R.id.listview);
            listview.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                    // TODO Auto-generated method stub

//                    Bundle bundle = new Bundle();
//                    bundle.putString(C.KEY_JSON_FM_ITEM_CODE, list.get(position).getId());
//                    bundle.putString(C.KEY_DIV_CODE, list.get(position).getDiv_code());
//                    PageUtil.jumpTo(SmShoppingCart.this, SmGoodsDetailActivity.class, bundle);

                }
            });
            checkBox = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.checkbox);
            checkBox.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    List<Integer> nums = new ArrayList<Integer>();// 列表中商品数量

                    for (int i = 0; i < list.size(); i++) {
                        int num = list.get(i).getNum();
                        float price = list.get(i).getprice();
                        nums.add(num);
                    }
                    if (checkBox.isChecked()) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setChoosed(true);
                        }
                        for (int j = 0; j < listParent.size(); j++) {
                            listParent.get(j).setChoosed_parent(true);
                        }
                        adapter.notifyDataSetChanged();

                        textView_002.setText(StringUtil.formatTosepara(getTotalPrice()));
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setChoosed(false);
                        }
                        for (int j = 0; j < listParent.size(); j++) {
                            listParent.get(j).setChoosed_parent(false);
                        }
                        adapter.notifyDataSetChanged();
                        textView_002.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + "0");
                    }
                }
            });

            textView_002 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_totoal002);

            textview001 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_totoal001);

            textview = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_to_buy);
            textview.setOnClickListener(new OnClickListener() {

                @SuppressWarnings("unchecked")
                @Override
                public void onClick(View v) {

                    // TODO Auto-generatet
                    ArrayList<ShoppingCart> list001 = new ArrayList<ShoppingCart>();
                    ArrayList<ShoppingCart> list002 = new ArrayList<ShoppingCart>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isChoosed()) {
                            list001.add(list.get(i));
                        }
                    }
                    Bundle bundle = new Bundle();
                    L.d("shop" + list001.toString());
                    if (list001.size() == 0) {
                        t(getResources().getString(com.rs.mobile.wportal.R.string.sm_text_plz_choosegoods));
                        return;
                    }
                    bundle.putParcelableArrayList(Goods, list001);
                    bundle.putParcelableArrayList("attachment", list002);
                    bundle.putString("total", textView_002.getText().toString());
                    bundle.putString("onCartProcess", "true");
                    PageUtil.jumpTo(SmShoppingCart.this, SmConfirmActivity.class, bundle);

                }
            });
            textView_add = (TextView) findViewById(com.rs.mobile.wportal.R.id.add_tofavorate);
            textView_add.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    // 1 // t(" merry chrismas");
                    ArrayList<ShoppingCart> list001 = new ArrayList<ShoppingCart>();

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isChoosed()) {
                            list001.add(list.get(i));
                        }
                    }
                    HashMap<String, String> params;
                    if (list001.size() == 0 || list001 == null) {
                        t(getResources().getString(com.rs.mobile.wportal.R.string.sm_text_plz_choose_good));
                    } else {
                        for (int j = 0; j < list001.size(); j++) {
                            params = new HashMap<>();
                            params.put(C.KEY_JSON_FM_ITEM_CODE, list001.get(j).getId().toString());
                            params.put("div_code", list001.get(j).getDiv_code());
                            params.put("sale_custom_code", list001.get(j).sale_custom_code);
                            params.put("token", S.get(SmShoppingCart.this, C.KEY_JSON_TOKEN));
                            addFavourate(params);
                        }
                    }
                }
            });

            textView_complete = (TextView) findViewById(com.rs.mobile.wportal.R.id.texttop_complete);
            textView_complete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    // TODO Auto-generated method stub
                    textView_complete.setVisibility(View.GONE);
                    textview001.setVisibility(View.VISIBLE);
                    textView_002.setVisibility(View.VISIBLE);
                    linear_edit.setVisibility(View.GONE);
                    textView_edit.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                }
            });

            textView_edit = (TextView) findViewById(com.rs.mobile.wportal.R.id.texttop_edit);
            textView_edit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    // TODO Auto-generated method stub
                    textview001.setVisibility(View.GONE);
                    textView_002.setVisibility(View.GONE);
                    linear_edit.setVisibility(View.VISIBLE);
                    textView_complete.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.GONE);
                    textView_edit.setVisibility(View.GONE);
                }
            });

            linear_edit = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.linear_edit);
            textView_delete = (TextView) findViewById(com.rs.mobile.wportal.R.id.delete);
            textView_delete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    ArrayList<ShoppingCart> list001 = new ArrayList<ShoppingCart>();

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isChoosed()) {
                            list001.add(list.get(i));
                        }
                    }
                    HashMap<String, String> params;
                    if (list001.size() == 0 || list001 == null) {
                        t(getResources().getString(com.rs.mobile.wportal.R.string.sm_text_plz_choose_good));
                    } else {
                        D.showDialog(SmShoppingCart.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
                                getString(com.rs.mobile.wportal.R.string.common_text002), getString(com.rs.mobile.wportal.R.string.common_text003),
                                new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        D.alertDialog.dismiss();
                                        deleteShopCart();
                                    }
                                }, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        D.alertDialog.dismiss();
                                    }
                                });
                    }
                }
            });

        } catch (Exception e) {

            L.e(e);

        }

    }

    private void getMyshoppingCartList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", S.get(SmShoppingCart.this, C.KEY_JSON_TOKEN));

        OkHttpHelper okHttpHelper = new OkHttpHelper(SmShoppingCart.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                Log.i("xyz", responseDescription);
                String item_name, image_url, item_code, stock_unit, div_code, sale_custom_code;
                int item_quantity;
                float item_price;

                list = new ArrayList<ShoppingCart>();
                listParent = new ArrayList<>();
                ShoppingCart shoppingCart;

                try {

                    JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);

                    JSONArray itemArr;

                    JSONObject object;

                    for (int j = 0; j < arr.length(); j++) {
                        hideNoData();
                        ShoppingCartParent shoppingCartParent;
                        List<ShoppingCart> shoppingCarts = new ArrayList<>();
                        String div_code1 = new JSONObject(arr.get(j).toString()).getString("div_code");
                        String div_name1 = new JSONObject(arr.get(j).toString()).getString("div_name");
                        itemArr = new JSONObject(arr.get(j).toString()).getJSONArray("items");

                        for (int i = 0; i < itemArr.length(); i++) {

                            object = new JSONObject(itemArr.get(i).toString());

                            item_name = object.getString("item_name");

                            item_code = object.get("item_code").toString();

                            stock_unit = object.getString("stock_unit");

                            item_quantity = object.getInt("item_quantity");

                            item_price = Float.parseFloat(object.get("item_price").toString());

                            image_url = object.get("image_url").toString();

                            div_code = object.getString("div_code");

                            sale_custom_code = object.getString("sale_custom_code");

                            shoppingCart = new ShoppingCart(item_code, item_name, item_price, image_url,
                                    item_quantity, false, stock_unit, div_code, sale_custom_code);

                            list.add(shoppingCart);
                            shoppingCarts.add(shoppingCart);

                        }
                        shoppingCartParent = new ShoppingCartParent(div_code1, div_name1, false, shoppingCarts);
                        listParent.add(shoppingCartParent);
                    }

                    adapter = new ShoppingCartParentAdapter(listParent, handler, SmShoppingCart.this, 6);

                    listview.setAdapter(adapter);
                    checkStatus();
                    textView_002.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + "0");


                } catch (Exception e) {
                    L.e(e);

                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.SM_BASE_URL + "/FreshMart/User/GetUserCartOfList", params);
    }

    //某个商家的单独购物车
    private void getMyshoppingCartList2() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", S.get(SmShoppingCart.this, C.KEY_JSON_TOKEN));
        params.put("appType", "1");
        params.put("ShopId", sale_custom_code);
        OkHttpHelper okHttpHelper = new OkHttpHelper(SmShoppingCart.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                String item_name, image_url, item_code, stock_unit, div_code, sale_custom_code;
                int item_quantity;
                float item_price;

                list = new ArrayList<ShoppingCart>();
                listParent = new ArrayList<>();
                ShoppingCart shoppingCart;

                try {

                    JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);

                    JSONArray itemArr;

                    JSONObject object;

                    for (int j = 0; j < arr.length(); j++) {
                        hideNoData();
                        ShoppingCartParent shoppingCartParent;
                        List<ShoppingCart> shoppingCarts = new ArrayList<>();
                        String div_code1 = new JSONObject(arr.get(j).toString()).getString("div_code");
                        String div_name1 = new JSONObject(arr.get(j).toString()).getString("div_name");
                        itemArr = new JSONObject(arr.get(j).toString()).getJSONArray("items");

                        for (int i = 0; i < itemArr.length(); i++) {

                            object = new JSONObject(itemArr.get(i).toString());

                            item_name = object.getString("item_name");

                            item_code = object.get("item_code").toString();

                            stock_unit = object.getString("stock_unit");

                            item_quantity = object.getInt("item_quantity");

                            item_price = Float.parseFloat(object.get("item_price").toString());

                            image_url = object.get("image_url").toString();

                            div_code = object.getString("div_code");

                            sale_custom_code = object.getString("sale_custom_code");

                            shoppingCart = new ShoppingCart(item_code, item_name, item_price, image_url,
                                    item_quantity, false, stock_unit, div_code, sale_custom_code);

                            list.add(shoppingCart);
                            shoppingCarts.add(shoppingCart);

                        }
                        shoppingCartParent = new ShoppingCartParent(div_code1, div_name1, false, shoppingCarts);
                        listParent.add(shoppingCartParent);
                    }

                    adapter = new ShoppingCartParentAdapter(listParent, handler, SmShoppingCart.this, 6);

                    listview.setAdapter(adapter);
                    checkStatus();
                    textView_002.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + "0");

                    List<Integer> nums = new ArrayList<Integer>();// 列表中商品数量

                    for (int i = 0; i < list.size(); i++) {
                        int num = list.get(i).getNum();
                        float price = list.get(i).getprice();
                        nums.add(num);
                    }
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChoosed(true);
                    }
                    for (int j = 0; j < listParent.size(); j++) {
                        listParent.get(j).setChoosed_parent(true);
                    }
                    adapter.notifyDataSetChanged();
                    textView_002.setText(StringUtil.formatTosepara(getTotalPrice()));

                    checkBox.setChecked(true);


                } catch (Exception e) {
                    L.e(e);

                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.SM_BASE_URL + "/FreshMart/User/GetUserShopCartOfListByShopId", params);
    }


    private void getMyshoppingCartList1() {

        try {

            HashMap<String, String> params = new HashMap<String, String>();
//			params.put(C.KEY_JSON_TOKEN, S.getShare(SmShoppingCart.this, C.KEY_JSON_TOKEN, ""));
//			params.put("appType", "6");
            params.put("token", S.get(SmShoppingCart.this, C.KEY_JSON_TOKEN));
            OkHttpHelper okHttpHelper = new OkHttpHelper(SmShoppingCart.this);
            okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                    // TODO Auto-generated method stub
                    String item_name, image_url, item_code, stock_unit, div_code, sale_custom_code;
                    int item_quantity;
                    float item_price;

                    list = new ArrayList<ShoppingCart>();
                    listParent = new ArrayList<>();
                    ShoppingCart shoppingCart;

                    try {

                        JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);

                        JSONArray itemArr;

                        JSONObject object;

                        for (int j = 0; j < arr.length(); j++) {
                            hideNoData();
                            ShoppingCartParent shoppingCartParent;
                            List<ShoppingCart> shoppingCarts = new ArrayList<>();
                            String div_code1 = new JSONObject(arr.get(j).toString()).getString("div_code");
                            String div_name1 = new JSONObject(arr.get(j).toString()).getString("div_name");
                            itemArr = new JSONObject(arr.get(j).toString()).getJSONArray("items");

                            for (int i = 0; i < itemArr.length(); i++) {

                                object = new JSONObject(itemArr.get(i).toString());

                                item_name = object.getString("item_name");

                                item_code = object.get("item_code").toString();

                                stock_unit = object.getString("stock_unit");

                                item_quantity = object.getInt("item_quantity");

                                item_price = Float.parseFloat(object.get("item_price").toString());

                                image_url = object.get("image_url").toString();

                                div_code = object.getString("div_code");

                                sale_custom_code = object.getString("sale_custom_code");

                                shoppingCart = new ShoppingCart(item_code, item_name, item_price, image_url,
                                        item_quantity, false, stock_unit, div_code, sale_custom_code);

                                list.add(shoppingCart);
                                shoppingCarts.add(shoppingCart);

                            }
                            shoppingCartParent = new ShoppingCartParent(div_code1, div_name1, false, shoppingCarts);
                            listParent.add(shoppingCartParent);
                        }

                        adapter = new ShoppingCartParentAdapter(listParent, handler, SmShoppingCart.this, 6);

                        listview.setAdapter(adapter);
                        checkStatus();
                        textView_002.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + "0");

                    } catch (Exception e) {
                        L.e(e);
                    }
                }

                @Override
                public void onBizFailure(String responseDescription, JSONObject data, String flag) {

                }
            }, Constant.SM_BASE_URL + Constant.GET_USER_SHOPINGCARTLIST, params);

        } catch (Exception e) {

            L.e(e);

        }
    }

    public static void upDateShopcart(Context context) {
        JSONArray arr = new JSONArray();

        try {

            for (int i = 0; i < list.size(); i++) {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("item_code", list.get(i).getId());

                jsonObject.put("item_quantity", "" + list.get(i).getNum());

                jsonObject.put("div_code", list.get(i).getDiv_code());

                jsonObject.put("sale_custom_code", list.get(i).sale_custom_code);

                arr.put(jsonObject);

            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("arrCardModel", arr.toString());
            params.put(C.KEY_JSON_TOKEN, S.get(context, C.KEY_JSON_TOKEN));
            OkHttpHelper okHttpHelper = new OkHttpHelper(context);
            okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                    // TODO Auto-generated method stub

                }
            }, Constant.SM_BASE_URL + Constant.UPDATE_USERSHOPCART, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            L.e(e);
        }
    }

    public void deleteShopCart() {
        String p = "";
        String d = "";
        String sale_custom_code = "";
        boolean firstFlag = true;
        try {
            if (list.size() == 0) {
                t(getString(com.rs.mobile.wportal.R.string.dp_text_034));
                return;
            }

            for (int i = 0; i < list.size(); i++) {

                if (list.get(i).isChoosed() == true) {

                    if (firstFlag) {
                        firstFlag = false;
                        p = p + list.get(i).getId();
                        d = d + list.get(i).getDiv_code();
                        sale_custom_code = list.get(i).sale_custom_code;
                    } else {

                        p = p + "," + list.get(i).getId();
                        d = d + "," + list.get(i).getDiv_code();
                        sale_custom_code = sale_custom_code + "," + list.get(i).sale_custom_code;
                    }

                }

            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("arrItemCode", p);
            params.put("arrDivCode", d);
            params.put("arrsale_custom_code", sale_custom_code);
            params.put(C.KEY_JSON_TOKEN, S.get(SmShoppingCart.this, C.KEY_JSON_TOKEN));
            OkHttpHelper okHttpHelper = new OkHttpHelper(SmShoppingCart.this);

            okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {

                }

                @Override
                public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                    initData();

                    try {
                        t(data.getString("message"));
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                @Override
                public void onBizFailure(String responseDescription, JSONObject data, String flag) {

                }
            }, Constant.SM_BASE_URL + Constant.DELUSERSHOPCART, params);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            L.e(e);
        }
    }

    private void addFavourate(Map<String, String> params) {
        try {
            OkHttpHelper okHttpHelper = new OkHttpHelper(SmShoppingCart.this);
            okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {

                }

                @Override
                public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                    try {
                        t(data.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                }
            }, Constant.SM_BASE_URL + Constant.ADD_USER_FAVORITES, params);

        } catch (Exception e) {

            L.e(e);

        }

    }

    public float getTotalPrice() {

        ShoppingCart bean = null;
        float totalPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            bean = list.get(i);
            // BigDecimal bigDecimal=new bi
            if (bean.isChoosed()) {
                totalPrice += bean.getNum() * bean.getprice();
            }
        }
        return (float) (Math.round(totalPrice * 100)) / 100;
    }

    private void checkStatus() {
        if (list.size() == 0) {

            OnClickListener nogoods_ShoppingCart_Listener = new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            };

            showNoData(com.rs.mobile.wportal.R.drawable.icon_nogoods_shoppingcart, getResources().getString(R.string.no_cart), nogoods_ShoppingCart_Listener);
            textView_complete.setVisibility(View.GONE);
            textview001.setVisibility(View.VISIBLE);
            textView_002.setVisibility(View.VISIBLE);
            linear_edit.setVisibility(View.GONE);
            textView_edit.setVisibility(View.VISIBLE);
            textview.setVisibility(View.VISIBLE);
            checkBox.setChecked(false);
            textView_002.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + "0");
        }
    }
}
