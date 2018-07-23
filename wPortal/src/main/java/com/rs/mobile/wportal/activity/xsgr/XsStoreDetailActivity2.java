package com.rs.mobile.wportal.activity.xsgr;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.XListView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.MyEvaluateActivity;
import com.rs.mobile.wportal.activity.sm.SmGoodsDetailActivity;
import com.rs.mobile.wportal.activity.sm.SmSeachActivity;
import com.rs.mobile.wportal.activity.sm.SmShoppingCart;
import com.rs.mobile.wportal.adapter.ShoppingCartParentAdapter;
import com.rs.mobile.wportal.adapter.rt.MenuCategoryListAdapter;
import com.rs.mobile.wportal.adapter.rt.MenuListAdapter;
import com.rs.mobile.wportal.adapter.rt.ShopCartListAdapter;
import com.rs.mobile.wportal.adapter.sm.ElvuateAdapter;
import com.rs.mobile.wportal.adapter.xsgr.CommentAdapter;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreCommentAdapter;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreDetailMenuAdapter3;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreDetailMenuAdapter4;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreDetailMenuAdapter5;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.wportal.biz.ShoppingCartParent;
import com.rs.mobile.wportal.entity.BaseEntity;
import com.rs.mobile.wportal.entity.CommentBean;
import com.rs.mobile.wportal.entity.StoreDetailEntity;
import com.rs.mobile.wportal.entity.StoreItemDetailEntity;
import com.rs.mobile.wportal.entity.StoreMenuListEntity1;
import com.rs.mobile.wportal.fragment.xsgr.XsInfoFragment;
import com.rs.mobile.wportal.fragment.xsgr.XsMenuFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Request;

public class XsStoreDetailActivity2 extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout btnBack;
    private TextView tvTitle;
    private ImageView ivCustomImg, top_img_allbk, iv_custom_phone;
    private TextView tvScore, tvReviews, tvBossComments, tvDistance;
    private RatingBar ratingBar;
    private LinearLayout llCall;

    private LinearLayout llTab1, llTab2;
    private Fragment menuFragment, infoFragment;
    private Fragment[] fragments;
    private LinearLayout[] linearLayouts;
    private TextView[] textViews;
    private int mCurrIndex = 0;
    private int mIndex = 0;
    private String mTel = "";

    private String shopname = "";

    private RecyclerView mRecyclerView;
    // private XsStoreDetailMenuAdapter2 mAdapter;
    private XsStoreDetailMenuAdapter3 mAdapter;

    private XsStoreDetailMenuAdapter4 mAdapter4;

    private XsStoreDetailMenuAdapter5 mAdapter5;

    private StoreItemDetailEntity mData;
    public String mSaleCustomCode;
    private int mNextRequestPage = 1;
    private TabLayout tabLayout1, tabLayout2;

    private MenuCategoryListAdapter menuCategoryListAdapter;

    private MenuListAdapter menuListAdapter;

    private ShopCartListAdapter cartListAdapter;

    private PopupWindow menu;

    private Spinner sp_select1, sp_select2;

    //메뉴 갯수
    private int menuCount = 0;

    //총 금액
    private double totalPrice;

    private ImageView cart_icon, search_icon;

    private TextView cart_count_text_view;

    private TextView cart_state_text_view;

    private TextView pay_btn;

    private LinearLayout cart_area;

    private TextView cart_count_text_view_2;

    private TextView delete_all_btn, tv_shopmame, tv_sale_m, pop_need_money_tv;

    private ListView cart_list_view;
    private StoreMenuListEntity1 entity;
    private List<StoreMenuListEntity1.plistinfo> allitemlist = new ArrayList<>();
    private LinearLayout line_img_bak;

    private StoreMenuListEntity1 entity2;

    private Dialog dialog;
    private boolean isdialog = false;

    private Spinner rv_pop_list, rv_pop_list2;
    private LinearLayout linear_itemlist1, linear_itemlist2;


    private StoreMenuListEntity1.foodFlavor selectfoodFlavor;
    private StoreMenuListEntity1.foodSpec selectfoodSpec;
    private StoreMenuListEntity1.plistinfo selectplistinfo;

    private List<StoreMenuListEntity1> allmyitem = new ArrayList<>();

    private boolean isfrist = true;

    private RelativeLayout bnt_cart1;

    private int carcount = 0;
    TabLayout.Tab tab1, tab2, tab3;

    private RecyclerView commentlist1;
    private String item_code;

    private int pageIndex;
    private boolean isfavorites = false;
    private int pageSize;

    private int TotalCount;
    private ImageView iconImg;

    private JSONArray array;
    private List<Map<String, Object>> listdata;
    private String div_code = "2";
    private String commentStatus = "0", has_imgs = "false";
    private FrameLayout content, content2, content3;

    private LinearLayout line_callphone, line_shopmaps;
    private TextView tv_shopname, tv_licenseno, tv_address, tv_shopphone, tv_origin;
    private boolean islogin = false;
    private String item_level1 = "0";
    private JSONObject shopjsonObject = null;

    private String shop_latitude, shop_longitude;


    private String FoodSpecCode = "";
    private   XsStoreCommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_store_detail);
        initView();
        initData();

        UtilClear.CheckLogin(XsStoreDetailActivity2.this, new UtilCheckLogin.CheckListener() {
            @Override
            public void onDoNext() {
                islogin = true;
                getMyshoppingCartList1();
            }
        }, new UtilCheckLogin.CheckError() {
            @Override
            public void onError() {
            }
        }, false);

        initShopInfoData();

    }

    private void initView() {
        commentlist1 = (RecyclerView) findViewById(R.id.commentlist);
        commentAdapter=new XsStoreCommentAdapter(XsStoreDetailActivity2.this,R.layout.item_comment_detail,new ArrayList<CommentBean.ShopAssessDataBean>());
        View emptyView = LayoutInflater.from(XsStoreDetailActivity2.this).inflate(R.layout.layout_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //添加空视图
        commentAdapter.setEmptyView(emptyView);
        commentlist1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        commentlist1.setAdapter(commentAdapter);

        iconImg = (ImageView) findViewById(R.id.img_favorites);
        btnBack = (LinearLayout) findViewById(R.id.btn_back);
        // tvTitle = (TextView) findViewById(R.id.tv_title);
        btnBack.setOnClickListener(this);

        ivCustomImg = (ImageView) findViewById(R.id.iv_custom_img);
//        iv_custom_phone = (ImageView) findViewById(R.id.iv_custom_phone);

        tvScore = (TextView) findViewById(R.id.tv_score);

//        tvReviews = (TextView) findViewById(R.id.tv_reviews);
//        tvBossComments = (TextView) findViewById(R.id.tv_boss_comments);
//        tvDistance = (TextView) findViewById(R.id.tv_distance);

        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        search_icon = (ImageView) findViewById(R.id.search_icon);
        search_icon.setOnClickListener(this);

        tabLayout1 = (TabLayout) findViewById(R.id.tab_layout1);
        tabLayout2 = (TabLayout) findViewById(R.id.tab_layout2);

        tv_shopmame = (TextView) findViewById(R.id.tv_title);

        bnt_cart1 = (RelativeLayout) findViewById(R.id.bnt_cart1);
        bnt_cart1.setOnClickListener(this);




        content = (FrameLayout) findViewById(R.id.content);
        content2 = (FrameLayout) findViewById(R.id.content2);
        content3 = (FrameLayout) findViewById(R.id.content3);

        line_callphone = (LinearLayout) findViewById(R.id.line_callphone);
        line_shopmaps = (LinearLayout) findViewById(R.id.line_shopmaps);


        tv_shopname = (TextView) findViewById(R.id.tv_shopname);
        tv_licenseno = (TextView) findViewById(R.id.tv_licenseno);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_shopphone = (TextView) findViewById(R.id.tv_shopphone);
        tv_origin = (TextView) findViewById(R.id.tv_origin);


//        tv_sale_m= (TextView) findViewById(R.id.tv_sale_m);
//        line_img_bak=(LinearLayout) findViewById(R.id.line_img_bak);
//        cart_icon=(ImageView) findViewById(R.id.cart_icon);
//        cart_area=(LinearLayout) findViewById(R.id.cart_area);

        cart_count_text_view = (TextView) findViewById(R.id.cart_count_text_view);
//        cart_state_text_view=(TextView)findViewById(R.id.cart_state_text_view);
//        cart_list_view = (ListView)findViewById(R.id.cart_list_view);
//        delete_all_btn=(TextView)findViewById(R.id.delete_all_btn);

        tab1 = tabLayout1.newTab().setText("메뉴");
        tab2 = tabLayout1.newTab().setText("리뷰");
        tab3 = tabLayout1.newTab().setText("정보");

        tabLayout1.addTab(tab1);
        tabLayout1.addTab(tab2);
        tabLayout1.addTab(tab3);
        tabLayout1.getTabAt(0).select();

        tabLayout1.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                content.setVisibility(View.GONE);
                content3.setVisibility(View.GONE);
                content2.setVisibility(View.GONE);
                if (tab.getPosition() == 0) {
                    content.setVisibility(View.VISIBLE);
                    tabLayout2.setVisibility(View.VISIBLE);
                } else if (tab.getPosition() == 1) {
                    content2.setVisibility(View.VISIBLE);
                    tabLayout2.setVisibility(View.GONE);
                    initCommentData();
                } else if (tab.getPosition() == 2) {
                    content3.setVisibility(View.VISIBLE);
                    tabLayout2.setVisibility(View.GONE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        iconImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilClear.CheckLogin(XsStoreDetailActivity2.this, new UtilCheckLogin.CheckListener() {
                    @Override
                    public void onDoNext() {
                        requestStoreFavorites(S.get(XsStoreDetailActivity2.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, isfavorites ? "0" : "1");
                    }
                }, new UtilCheckLogin.CheckError() {
                    @Override
                    public void onError() {

                    }
                });

            }
        });


        line_callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mTel = shopjsonObject.getString("telephon");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mTel));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        });

        line_shopmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(XsStoreDetailActivity2.this, com.rs.mobile.wportal.activity.NaverMap.Activity_NaverMap_Main.class);
                intent2.putExtra("wiche", "compass");
                intent2.putExtra("longitude", shop_longitude);
                intent2.putExtra("latitude", shop_latitude);
                intent2.putExtra("shopname", shopname);
                startActivityForResult(intent2, 1001);
            }
        });

        //清空购物车
//        delete_all_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                allmyitem.clear();
//                cartListAdapter = new ShopCartListAdapter(XsStoreDetailActivity2.this, allmyitem, new ShopCartListAdapter.MenuChangeListener() {
//
//                    @Override
//                    public void onChange(boolean add) {
//                        // TODO Auto-generated method stub
//                        try {
//
//                            allmyitem=cartListAdapter.getListItems();//获取已更新的数据列表
//                            menuCount = 0;
//                            totalPrice = 0;
//                            cart_count_text_view.setText("0");
//                            cart_state_text_view.setText("0.00");
//                        } catch (Exception e) {
//
//                            L.e(e);
//
//                        }
//
//                    }
//                });
//                cart_list_view.setAdapter(cartListAdapter);
//                cart_area.setVisibility(View.GONE);
//            }
//        });

        //购物车图标点击
//        cart_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(cart_area.getVisibility()==View.VISIBLE)
//                {
//                    cart_area.setVisibility(View.GONE);
//                }
//                else
//                {
//                    cart_area.setVisibility(View.VISIBLE);
//
//                    cartListAdapter = new ShopCartListAdapter(XsStoreDetailActivity2.this, allmyitem, new ShopCartListAdapter.MenuChangeListener() {
//
//                        @Override
//                        public void onChange(boolean add) {
//                            // TODO Auto-generated method stub
//                            try {
//                                allmyitem=cartListAdapter.getListItems();//获取已更新的数据列表
//                                menuCount = 0;
//                                totalPrice = 0;
//                                reCountCardata();
//                            } catch (Exception e) {
//
//                                L.e(e);
//
//                            }
//
//                        }
//                    });
//
//                    cart_list_view.setAdapter(cartListAdapter);
//
//                }
//            }
//        });


//        top_img_allbk=(ImageView) findViewById(R.id.top_img_allbk);

    }

    private void reCountCardata() {
        //       tvScore.setText("11111111");


        totalPrice = 0;
        // cart_state_text_view;
        int count = 0;
        for (int i = 0; i < allmyitem.size(); i++) {
            count = count + allmyitem.get(i).num;
            if (allmyitem.get(i).selectplistinfo.isSingle.equals("1")) {
                totalPrice = totalPrice + (Double.parseDouble(allmyitem.get(i).selectplistinfo.item_p) * count);

            } else {
                totalPrice = totalPrice + (Double.parseDouble(allmyitem.get(i).selectfoodSpec.item_p) * count);
            }
        }

        cart_count_text_view.setText(count + "");
        cart_state_text_view.setText(totalPrice + "");

    }

    private void initData() {
        mSaleCustomCode = getIntent().getStringExtra("sale_custom_code");
        //  mSaleCustomCode="01071390009abcde";
        requestStoreItemDetail(S.get(XsStoreDetailActivity2.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, "" + AppConfig.latitude, "" + AppConfig.longitude, "1", "10");


        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        mAdapter = new XsStoreDetailMenuAdapter3(this, R.layout.layout_rt_menulist_item2, mData != null ? mData.Storeitems : null);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestStoreItemDetail2(S.get(XsStoreDetailActivity2.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, "" + AppConfig.latitude, "" + AppConfig.longitude, mNextRequestPage, "10");
            }
        }, mRecyclerView);


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showCustomDialog(XsStoreDetailActivity2.this, R.layout.activity_shopdetail_menu_pop2, R.style.dialog_holo, true, allitemlist.get(position));
            }
        });


        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, final View view, int position) {

                selectplistinfo = allitemlist.get(position);

                UtilClear.CheckLogin(XsStoreDetailActivity2.this, new UtilCheckLogin.CheckListener() {
                    @Override
                    public void onDoNext() {
                        addToShopcart(selectplistinfo.item_code, "1", mSaleCustomCode);
//                        ImageView img=(ImageView)view;
//                        img.setImageResource(R.drawable.icon_selected);

                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });

           /*
           if(allitemlist.get(position).isSingle.equals("0") && view.getId()==R.id.bnt_select)
           {
              showCustomDialog(XsStoreDetailActivity2.this, R.layout.activity_shopdetail_menu_pop, R.style.dialog, true,allitemlist.get(position));
           }
           else
           {
               int ppp=0;
               for(int i=0;i<allmyitem.size();i++)
               {
                   if(allmyitem.get(i).selectplistinfo.item_code.equals(selectplistinfo.item_code))
                   {
                       allmyitem.get(i).num++;
                       ppp=1;
                   }
               }
               if(ppp==0) {
                   StoreMenuListEntity1 s1 = new StoreMenuListEntity1();
                   s1.selectplistinfo = selectplistinfo;
                   s1.num = 1;
                   allmyitem.add(s1);
               }
               reCountCardata();

           }

          */


            }

        });


        requestStoreItemDetail2(S.get(XsStoreDetailActivity2.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, "" + AppConfig.latitude, "" + AppConfig.longitude, 1, "10");



        pageIndex = 1;
        pageSize = 20;

        listdata = new ArrayList<Map<String, Object>>();
        try {
            array = new JSONArray("[]");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 自定义对话框布局
     *
     * @param context     :上下文
     * @param layout      ：显示布局
     * @param windowStyle ：windows显示的样式
     */
    public void showCustomDialog(final Context context, int layout, int windowStyle, boolean cancelable, final StoreMenuListEntity1.plistinfo pinfo) {

        dialog = new Dialog(context, windowStyle);
        // 装载布局
        dialog.setContentView(layout);
        dialog.setCancelable(cancelable);


        TextView title_text_view = (TextView) dialog.findViewById(R.id.title_text_view);
        title_text_view.setText(pinfo.item_name);

        pop_need_money_tv = (TextView) dialog.findViewById(R.id.pop_need_money_tv);

        ImageView iv_itemlogo_pop = (ImageView) dialog.findViewById(R.id.iv_itemlogo_pop);
        TextView tv_itemname_pop = (TextView) dialog.findViewById(R.id.tv_itemname_pop);

        Glide.with(XsStoreDetailActivity2.this).load(pinfo.image_url).into((ImageView) iv_itemlogo_pop);
        tv_itemname_pop.setText(pinfo.item_name);

        pop_need_money_tv.setText(pinfo.item_p + "원");
        FoodSpecCode = pinfo.item_code;
        TextView tv_itemp_pop = (TextView) dialog.findViewById(R.id.tv_itemp_pop);
        tv_itemp_pop.setText(pinfo.item_p + "원");


        TextView tv_itemremark = (TextView) dialog.findViewById(R.id.tv_itemremark);
        tv_itemremark.setText(pinfo.ITEM_DETAILS);

        TextView tv_itemsynopsis = (TextView) dialog.findViewById(R.id.tv_itemsynopsis);
        tv_itemsynopsis.setText(pinfo.Remark);

        ImageView ll_close = (ImageView) dialog.findViewById(R.id.right_navigation_img);
        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    isdialog = false;
                }
            }
        });


        final EditText et_num = (EditText) dialog.findViewById(R.id.et_num);
        et_num.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        et_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_num.selectAll();
            }
        });
        et_num.addTextChangedListener(new TextWatcher() {

            private int num;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    et_num.setText("1");
                    et_num.selectAll();
                }
                if (s.length() > 0) {
                    num = Integer.parseInt(s.toString());
                    if (num == 0) {
                        et_num.setText("1");
                        et_num.setSelection(et_num.length());
                    }
                }
            }
        });


        TextView bnt_select = (TextView) dialog.findViewById(R.id.bnt_select);
        bnt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectplistinfo = pinfo;
                UtilClear.CheckLogin(XsStoreDetailActivity2.this, new UtilCheckLogin.CheckListener() {
                    @Override
                    public void onDoNext() {
                        addToShopcart2(FoodSpecCode, et_num.getText().length() == 0 ? "1" : et_num.getText().toString(), mSaleCustomCode);
                    }
                }, new UtilCheckLogin.CheckError() {

                    @Override
                    public void onError() {
                    }
                });

                // TODO Auto-generated method stub
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    isdialog = false;
                }
            }
        });

        if (pinfo.isSingle.compareTo("1") != 0) {
            requestStoreSmallItemDetail(pinfo.GroupId, dialog);
        }

        dialog.show();

        isdialog = true;

    }

    //评论列表
    public void initShopInfoData() {

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("div_code", div_code);
        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(XsStoreDetailActivity2.this, C.KEY_JSON_TOKEN, ""));
        param.put("sale_custom_code", mSaleCustomCode);
        param.put("userId", S.getShare(XsStoreDetailActivity2.this, C.KEY_REQUEST_MEMBER_ID, ""));

        OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreDetailActivity2.this);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                // TODO Auto-generated method stub
                try {


                    shopjsonObject = data;
                    // JSONObject jsonObject = data.getJSONObject(C.KEY_JSON_DATA);
                    //String string = data.getString("total");

                    tv_address.setText(shopjsonObject.getString("addr"));
                    tv_shopphone.setText(shopjsonObject.getString("telephon"));
                    tv_origin.setText(shopjsonObject.getString("country_origin"));
                    tv_licenseno.setText(shopjsonObject.getString("company_num"));

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XS_BASE_URL + "StoreCate/requestStoreInfo", param);

    }


    //评论列表
    public void initCommentData() {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("lang_type", "kor");
        param.put("sale_custom_code", mSaleCustomCode);
        param.put("pg", "1");
        param.put("pagesize", "100");
        param.put("orderby", "2");
        OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreDetailActivity2.this);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                CommentBean bean=GsonUtils.changeGsonToBean(responseDescription, CommentBean.class);
                commentAdapter.setNewData(bean.getShopAssessData());
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XS_BASE_URL +"Assess/requestStoreAssessList", param);

    }

    public int getwindowswidth() {

        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 窗口的宽度
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    public int getwindowsheight() {

        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 窗口的宽度
        int screenheight = dm.heightPixels;
        return screenheight;
    }

    private int getimgheight() {

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), com.rs.mobile.wportal.R.drawable.img_logo);
        int height = bitmap.getHeight();
        return height + StringUtil.dip2px(XsStoreDetailActivity2.this, 20);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tab1:
                mCurrIndex = 0;
                break;
            case R.id.tab2:
                mCurrIndex = 1;
                break;
            case R.id.bnt_cart1:
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 1);
                bundle.putString("sale_custom_code", mSaleCustomCode);
                PageUtil.jumpTo(XsStoreDetailActivity2.this,
                        SmShoppingCart.class, bundle);
                break;
            case R.id.search_icon:
                Intent intentSearch = new Intent(XsStoreDetailActivity2.this, SmSeachActivity.class);
                startActivity(intentSearch);
                break;

        }

    }

    private void requestStoreItemDetail(String customCode, String saleCustomCode, String latitude, String longitude, String pg, String pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", customCode);
        params.put("sale_custom_code", saleCustomCode);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("pg", pg);
        params.put("pagesize", pageSize);

        OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreDetailActivity2.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                StoreItemDetailEntity entity = GsonUtils.changeGsonToBean(responseDescription, StoreItemDetailEntity.class);
                if ("1".equals(entity.status)) {

                    try {


                        Glide.with(XsStoreDetailActivity2.this).load(entity.shop_thumnail_image).into(ivCustomImg);


                        if (entity.score != null & !entity.score.equals("")) {
                            tvScore.setText(entity.score);
                        }

//                        if(entity.cnt!=null & !entity.cnt.equals("")) {
//                            tvReviews.setText(entity.cnt);
//                        }
//                        tvBossComments.setText(entity.sale_custom_cnt);
//                        tvDistance.setText(entity.distance + "km");
//

                        if (entity.score != null && !entity.score.isEmpty()) {
                            ratingBar.setRating(Float.parseFloat(entity.score));
                        } else {
                            ratingBar.setRating(0);
                        }
                        //  ((XsMenuFragment)menuFragment).sendData(entity);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                }

                requestStoreDetail(S.get(XsStoreDetailActivity2.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, "37.434668", "122.160742");
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL + "StoreCate/requestStoreItemDetail", GsonUtils.createGsonString(params));
    }


    private void requestStoreFavorites(String customCode, String saleCustomCode, final String favorites) {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", customCode);
        params.put("sale_custom_code", saleCustomCode);
        params.put("token", S.get(XsStoreDetailActivity2.this, C.KEY_JSON_TOKEN));
        params.put("favorites", favorites);

        OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreDetailActivity2.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                BaseEntity entity = GsonUtils.changeGsonToBean(responseDescription, BaseEntity.class);
                if ("1".equals(entity.status)) {


                    if ("1".equals(favorites)) {
                        Toast.makeText(XsStoreDetailActivity2.this, "찜하기가 완료되었습니다", Toast.LENGTH_LONG).show();
                        isfavorites = true;
                        iconImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_collected_store));
                    } else {
                        Toast.makeText(XsStoreDetailActivity2.this, "찜하기가 취소되었습니다", Toast.LENGTH_LONG).show();
                        isfavorites = false;
                        iconImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_uncollected_store));
                    }
                } else {
                    Toast.makeText(XsStoreDetailActivity2.this, entity.msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL + "MyInfo/FavoritesSet", GsonUtils.createGsonString(params));
    }


    private void requestStoreDetail(String customCode, String saleCustom, String latitude, String longitude) {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", customCode);
        params.put("sale_custom_code", saleCustom);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreDetailActivity2.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                StoreDetailEntity entity = GsonUtils.changeGsonToBean(responseDescription, StoreDetailEntity.class);
                mTel = entity.telephon;
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL + "StoreCate/requestStoreDetail", GsonUtils.createGsonString(params));
    }


    public interface CallBackValue1 {
        public void sendData(StoreItemDetailEntity entity);
    }


    private void requestStoreItemDetail2(String customCode, String saleCustomCode, String latitude, String longitude, final int pg, String pageSize) {

        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", customCode);
        // params.put("sale_custom_code", "01071390009abcde");
        params.put("sale_custom_code", mSaleCustomCode);
        params.put("item_level1", item_level1);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("pg", "" + pg);
        params.put("pagesize", pageSize);

        if (pg == 1) {
            allitemlist.clear();
        }
        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {


                entity = GsonUtils.changeGsonToBean(responseDescription, StoreMenuListEntity1.class);
                tv_shopmame.setText(entity.data.StoreInfo.custom_name);
                tv_shopname.setText(entity.data.StoreInfo.custom_name);
                if (entity.data.StoreInfo.favorites) {
                    iconImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_collected_store));
                } else {
                    iconImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_uncollected_store));
                }
                isfavorites = entity.data.StoreInfo.favorites;
                shopname = entity.data.StoreInfo.custom_name;
                shop_latitude = entity.data.StoreInfo.shop_latitude;
                shop_longitude = entity.data.StoreInfo.shop_longitude;

                //  tv_sale_m.setText("주문수 "+entity.data.StoreInfo.sale_cnt);
                //  mTel=entity.data.StoreInfo;

                if ("1".equals(entity.status)) {


                    if (isfrist && entity.data.category != null && entity.data.category.size() > 0) {
                        for (StoreMenuListEntity1.categoryinfo b : entity.data.category) {
                            tabLayout2.addTab(tabLayout2.newTab().setText(b.level_name));
                        }
                        isfrist = false;

                        tabLayout2.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {

                                allitemlist.clear();
                                mAdapter.setNewData(allitemlist);
                                mNextRequestPage = 1;
                                item_level1 = entity.data.category.get(tab.getPosition()).id;
                                requestStoreItemDetail2(S.get(XsStoreDetailActivity2.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, "" + AppConfig.latitude, "" + AppConfig.longitude, mNextRequestPage, "10");

                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });

                    }

                    try {
                        if (pg == 1) allitemlist.clear();


                        if (entity.data.plist != null && entity.data.plist.size() > 0) {
                            allitemlist.addAll(entity.data.plist);
                            mNextRequestPage++;
                            mAdapter.loadMoreComplete();
                            mAdapter.addData(entity.data.plist);
                            if (entity.data.plist.size() < 10) {
                                mAdapter.loadMoreEnd(true);
                            }

                        } else {
                            mAdapter.loadMoreComplete();
                            mAdapter.loadMoreEnd(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    mAdapter.loadMoreComplete();
                    mAdapter.loadMoreEnd(true);
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://mall.gigawon.co.kr:8800/api/StoreCate/requestStoreInfoProductList", GsonUtils.createGsonString(params));
    }

    private void requestStoreSmallItemDetail(String Groupid, final Dialog dialog) {

        linear_itemlist1 = (LinearLayout) dialog.findViewById(R.id.linear_select1);
        linear_itemlist2 = (LinearLayout) dialog.findViewById(R.id.linear_select2);

        HashMap<String, String> params = new HashMap<>();
        params.put("Groupid", Groupid);
        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {


                entity2 = GsonUtils.changeGsonToBean(responseDescription, StoreMenuListEntity1.class);

                if ("1".equals(entity2.status)) {
                    try {
                        if (entity2.data.FoodSpec != null && entity2.data.FoodSpec.size() > 0) {
                            linear_itemlist1.setVisibility(View.VISIBLE);

                            List<String> list = new ArrayList<String>();

                            for (int i = 0; i < entity2.data.FoodSpec.size(); i++) {
                                list.add(entity2.data.FoodSpec.get(i).item_name);
                            }


                            ArrayAdapter spadapter = new ArrayAdapter(getApplicationContext(), R.layout.custom_spiner_text_item, list);

                            spadapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
                            sp_select1 = (Spinner) dialog.findViewById(R.id.sp_select11);
                            sp_select1.setAdapter(spadapter);

                            pop_need_money_tv.setText(entity2.data.FoodSpec.get(0).item_p);
                            FoodSpecCode = entity2.data.FoodSpec.get(0).item_code;

                            sp_select1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    pop_need_money_tv.setText(entity2.data.FoodSpec.get(position).item_p);
                                    FoodSpecCode = entity2.data.FoodSpec.get(position).item_code;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
//                            selectfoodSpec = entity2.data.FoodSpec.get(0);
//
//                            linear_itemlist1.setVisibility(View.VISIBLE);
//
//
//                            mAdapter4 = new XsStoreDetailMenuAdapter4(XsStoreDetailActivity2.this, R.layout.layout_rt_menulist_item3, entity2.data.FoodSpec != null ? entity2.data.FoodSpec : null);
//
//                            rv_pop_list.setAdapter(mAdapter4);
//                            mAdapter4.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//                                @Override
//                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//
//                                    selectfoodSpec = entity2.data.FoodSpec.get(position);
//
//
//                                    pop_need_money_tv.setText(entity2.data.FoodSpec.get(position).item_p);
//                                    for (int i = 0; i < rv_pop_list.getChildCount(); i++) {
//                                        View v = rv_pop_list.getChildAt(i);
//                                        TextView v1 = (TextView) v.findViewById(R.id.tv_listitemname);
//                                        v1.setBackgroundResource(R.drawable.new_shop_shape5);
//                                        v1.setTextColor(Color.parseColor("#b3b3b3"));
//                                    }
//
//                                    TextView tv_listitemname = (TextView) view;
//                                    tv_listitemname.setBackgroundResource(R.drawable.new_shop_shape4);
//                                    tv_listitemname.setTextColor(Color.parseColor("#ffffff"));
//
//                                }
//                            });

                            //设置默认值为第一个

                        } else {
                            linear_itemlist1.setVisibility(View.GONE);
                        }

                        if (entity2.data.FoodFlavor != null && entity2.data.FoodFlavor.size() > 0) {
                            linear_itemlist2.setVisibility(View.VISIBLE);


                            sp_select2 = (Spinner) dialog.findViewById(R.id.sp_select12);


                            List<String> list = new ArrayList<String>();

                            for (int i = 0; i < entity2.data.FoodFlavor.size(); i++) {
                                list.add(entity2.data.FoodFlavor.get(i).flavorName);
                            }


                            ArrayAdapter spadapter = new ArrayAdapter(getApplicationContext(), R.layout.custom_spiner_text_item, list);

                            spadapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
                            sp_select2.setAdapter(spadapter);

//                            selectfoodFlavor = entity2.data.FoodFlavor.get(0);
//                            rv_pop_list2.setLayoutManager(new GridLayoutManager(XsStoreDetailActivity2.this, 3));
//                            mAdapter5 = new XsStoreDetailMenuAdapter5(XsStoreDetailActivity2.this, R.layout.layout_rt_menulist_item3, entity2.data.FoodFlavor != null ? entity2.data.FoodFlavor : null);
//                            rv_pop_list2.setHasFixedSize(true);
//                            rv_pop_list2.setAdapter(mAdapter5);
//                            mAdapter5.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//                                @Override
//                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                                    selectfoodFlavor = entity2.data.FoodFlavor.get(position);
//                                    for (int i = 0; i < rv_pop_list2.getChildCount(); i++) {
//                                        View v = rv_pop_list2.getChildAt(i);
//                                        TextView v1 = (TextView) v.findViewById(R.id.tv_listitemname);
//                                        v1.setBackgroundResource(R.drawable.new_shop_shape5);
//                                        v1.setTextColor(Color.parseColor("#b3b3b3"));
//                                    }
//
//                                    TextView tv_listitemname = (TextView) view;
//                                    tv_listitemname.setBackgroundResource(R.drawable.new_shop_shape4);
//                                    tv_listitemname.setTextColor(Color.parseColor("#ffffff"));


                        } else {
                            linear_itemlist2.setVisibility(View.GONE);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL + "/StoreCate/requestStoreDetailByGroupid", GsonUtils.createGsonString(params));
    }


    private void requestStoreItemDetail(String customCode, String saleCustomCode, String latitude, String longitude, int pg, String pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", customCode);
        params.put("sale_custom_code", saleCustomCode);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("pg", "" + pg);
        params.put("pagesize", pageSize);

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {


                StoreItemDetailEntity entity = GsonUtils.changeGsonToBean(responseDescription, StoreItemDetailEntity.class);
                if ("1".equals(entity.status)) {
                    try {
                        if (entity.Storeitems != null && entity.Storeitems.size() > 0) {
                            mNextRequestPage++;
                            mAdapter.loadMoreComplete();
                            //  mAdapter.addData(entity.Storeitems);
                            //  mAdapter.loadMoreEnd(true);
                        } else {
                            mAdapter.loadMoreEnd(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    mAdapter.loadMoreComplete();
                    mAdapter.loadMoreEnd(true);
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL + "StoreCate/requestStoreItemDetail", GsonUtils.createGsonString(params));
    }


    public void addToShopcart2(String item_code, String item_quantity, String sale_custom_code) {
        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
            params.put(C.KEY_JSON_FM_ITEM_QUANTITI, item_quantity);
            params.put(C.KEY_JSON_TOKEN, S.get(XsStoreDetailActivity2.this, C.KEY_JSON_TOKEN));
            params.put("div_code", "2");
            params.put("sale_custom_code", sale_custom_code);
            OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreDetailActivity2.this);
            okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {

                }

                @Override
                public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                    try {
                        if (data.getString("status").equals("1")) {
                            carcount++;
                            cart_count_text_view.setText(carcount + "");
                            Bundle bundle = new Bundle();
                            bundle.putInt("flag", 1);
                            bundle.putString("sale_custom_code", mSaleCustomCode);
                            PageUtil.jumpTo(XsStoreDetailActivity2.this,
                                    SmShoppingCart.class, bundle);

                        }
                    } catch (Exception e) {
                        L.e(e);
                    }
                }

                @Override
                public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                }
            }, Constant.SM_BASE_URL + Constant.ADD_USER_SHOPCART, params);
        } catch (Exception e) {
            L.e(e);
        }
    }

    public void addToShopcart(String item_code, String item_quantity, String sale_custom_code) {
        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
            params.put(C.KEY_JSON_FM_ITEM_QUANTITI, item_quantity);
            params.put(C.KEY_JSON_TOKEN, S.get(XsStoreDetailActivity2.this, C.KEY_JSON_TOKEN));
            params.put("div_code", "2");
            params.put("sale_custom_code", sale_custom_code);
            OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreDetailActivity2.this);
            okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {

                }

                @Override
                public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                    try {
                        if (data.getString("status").equals("1")) {
                            carcount++;
                            cart_count_text_view.setText(carcount + "");

                        }
                    } catch (Exception e) {
                        L.e(e);
                    }
                }

                @Override
                public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                }
            }, Constant.SM_BASE_URL + Constant.ADD_USER_SHOPCART, params);
        } catch (Exception e) {
            L.e(e);
        }
    }

    private void getMyshoppingCartList1() {
        try {

            HashMap<String, String> params = new HashMap<String, String>();
//			params.put(C.KEY_JSON_TOKEN, S.getShare(SmShoppingCart.this, C.KEY_JSON_TOKEN, ""));
            params.put("appType", "1");
            params.put("ShopId", mSaleCustomCode);
            params.put("token", S.get(XsStoreDetailActivity2.this, C.KEY_JSON_TOKEN));
            OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreDetailActivity2.this);
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

                    ShoppingCart shoppingCart;

                    try {

                        JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);

                        JSONArray itemArr;

                        JSONObject object;

                        itemArr = new JSONObject(arr.get(0).toString()).getJSONArray("items");
                        carcount = itemArr.length();

                        cart_count_text_view.setText(carcount + "");

                    } catch (Exception e) {
                        L.e(e);
                    }
                }

                @Override
                public void onBizFailure(String responseDescription, JSONObject data, String flag) {

                }
            }, Constant.SM_BASE_URL + "/FreshMart/User/GetUserShopCartOfListByShopId", params);

        } catch (Exception e) {

            L.e(e);

        }
    }


}
