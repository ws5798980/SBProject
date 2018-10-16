package com.rs.mobile.wportal.activity.xsgr;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.rt.RtMenuListActivity;
import com.rs.mobile.wportal.activity.sm.SmGoodsDetailActivity;
import com.rs.mobile.wportal.adapter.rt.CartListAdapter;
import com.rs.mobile.wportal.adapter.rt.MenuCategoryListAdapter;
import com.rs.mobile.wportal.adapter.rt.MenuListAdapter;
import com.rs.mobile.wportal.adapter.rt.ShopCartListAdapter;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreDetailMenuAdapter3;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreDetailMenuAdapter4;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreDetailMenuAdapter5;
import com.rs.mobile.wportal.entity.StoreDetailEntity;
import com.rs.mobile.wportal.entity.StoreItemDetailEntity;
import com.rs.mobile.wportal.entity.StoreMenuListEntity1;
import com.rs.mobile.wportal.fragment.xsgr.XsInfoFragment;
import com.rs.mobile.wportal.fragment.xsgr.XsMenuFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Request;

public class XsStoreDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout btnBack;
    private TextView tvTitle;
    private ImageView ivCustomImg,top_img_allbk,iv_custom_phone;
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


    private RecyclerView mRecyclerView;
   // private XsStoreDetailMenuAdapter2 mAdapter;
    private XsStoreDetailMenuAdapter3 mAdapter;

    private XsStoreDetailMenuAdapter4 mAdapter4;

    private XsStoreDetailMenuAdapter5  mAdapter5;

    private StoreItemDetailEntity mData;
    public String mSaleCustomCode;
    private int mNextRequestPage = 1;
    private TabLayout tabLayout1;

    private MenuCategoryListAdapter menuCategoryListAdapter;

    private MenuListAdapter menuListAdapter;

    private ShopCartListAdapter cartListAdapter;

    private PopupWindow menu;

    //메뉴 갯수
    private int menuCount = 0;

    //총 금액
    private double totalPrice;

    private ImageView cart_icon;

    private TextView cart_count_text_view;

    private TextView cart_state_text_view;

    private TextView pay_btn;

    private LinearLayout cart_area;

    private TextView cart_count_text_view_2;

    private TextView delete_all_btn,tv_shopmame,tv_sale_m,pop_need_money_tv;

    private ListView cart_list_view;
    private StoreMenuListEntity1 entity;
    private List<StoreMenuListEntity1.plistinfo> allitemlist=new ArrayList<>();
    private LinearLayout line_img_bak;

    private StoreMenuListEntity1 entity2;

    private Dialog dialog;
    private boolean isdialog = false;

    private RecyclerView rv_pop_list,rv_pop_list2;
    private LinearLayout linear_itemlist1,linear_itemlist2;

    private StoreMenuListEntity1.foodFlavor selectfoodFlavor;
    private StoreMenuListEntity1.foodSpec selectfoodSpec;
    private StoreMenuListEntity1.plistinfo selectplistinfo;

    private List<StoreMenuListEntity1> allmyitem=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_store_detail2);
        initView();
        initData();
    }

    private void initView(){


        btnBack = (LinearLayout) findViewById(R.id.btn_back);
     // tvTitle = (TextView) findViewById(R.id.tv_title);
        btnBack.setOnClickListener(this);
        ivCustomImg = (ImageView) findViewById(R.id.iv_custom_img);
        iv_custom_phone = (ImageView) findViewById(R.id.iv_custom_phone);
        tvScore = (TextView) findViewById(R.id.tv_score);
        tvReviews = (TextView) findViewById(R.id.tv_reviews);
        tvBossComments = (TextView) findViewById(R.id.tv_boss_comments);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        tabLayout1 = (TabLayout) findViewById(R.id.tab_layout1);
        tv_shopmame= (TextView) findViewById(R.id.tv_shopmame);
        tv_sale_m= (TextView) findViewById(R.id.tv_sale_m);
        line_img_bak=(LinearLayout) findViewById(R.id.line_img_bak);
        cart_icon=(ImageView) findViewById(R.id.cart_icon);
        cart_area=(LinearLayout) findViewById(R.id.cart_area);

        cart_count_text_view=(TextView)findViewById(R.id.cart_count_text_view);
        cart_state_text_view=(TextView)findViewById(R.id.cart_state_text_view);
        cart_list_view = (ListView)findViewById(R.id.cart_list_view);
        delete_all_btn=(TextView)findViewById(R.id.delete_all_btn);

        tabLayout1.addTab(tabLayout1.newTab().setText("点单"));
        tabLayout1.addTab(tabLayout1.newTab().setText("评价"));
        tabLayout1.addTab(tabLayout1.newTab().setText("商家"));
        tabLayout1.addTab(tabLayout1.newTab().setText("推广"));
        tabLayout1.getTabAt(0).select();
//        llCall = (LinearLayout) findViewById(R.id.ll_call);
        iv_custom_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mTel));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        });
        //清空购物车
        delete_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allmyitem.clear();
                cartListAdapter = new ShopCartListAdapter(XsStoreDetailActivity.this, allmyitem, new ShopCartListAdapter.MenuChangeListener() {

                    @Override
                    public void onChange(boolean add) {
                        // TODO Auto-generated method stub
                        try {

                            allmyitem=cartListAdapter.getListItems();//获取已更新的数据列表
                            menuCount = 0;
                            totalPrice = 0;
                            cart_count_text_view.setText("0");
                            cart_state_text_view.setText("0.00");
                        } catch (Exception e) {

                            L.e(e);

                        }

                    }
                });
                cart_list_view.setAdapter(cartListAdapter);
                cart_area.setVisibility(View.GONE);
            }
        });
        //购物车图标点击
        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart_area.getVisibility()==View.VISIBLE)
                {
                    cart_area.setVisibility(View.GONE);
                }
                else
                {
                    cart_area.setVisibility(View.VISIBLE);

                    cartListAdapter = new ShopCartListAdapter(XsStoreDetailActivity.this, allmyitem, new ShopCartListAdapter.MenuChangeListener() {

                        @Override
                        public void onChange(boolean add) {
                            // TODO Auto-generated method stub
                            try {
                                allmyitem=cartListAdapter.getListItems();//获取已更新的数据列表
                                menuCount = 0;
                                totalPrice = 0;
                                reCountCardata();
                            } catch (Exception e) {

                                L.e(e);

                            }

                        }
                    });

                    cart_list_view.setAdapter(cartListAdapter);

                }
            }
        });

        menuFragment = new XsMenuFragment();
        infoFragment = new XsInfoFragment();
        fragments = new Fragment[]{menuFragment, infoFragment};

        top_img_allbk=(ImageView) findViewById(R.id.top_img_allbk);

    }

    /**
     * 自定义对话框布局
     *
     * @param context     :上下文
     * @param layout      ：显示布局
     * @param windowStyle ：windows显示的样式
     */
    public void showCustomDialog(final Context context, int layout, int windowStyle, boolean cancelable,StoreMenuListEntity1.plistinfo pinfo) {
        dialog = new Dialog(context, windowStyle);
        // 装载布局
        dialog.setContentView(layout);
        dialog.setCancelable(cancelable);


        linear_itemlist1=(LinearLayout) dialog.findViewById(R.id.linear_itemlist1);
        linear_itemlist2=(LinearLayout) dialog.findViewById(R.id.linear_itemlist2);

        rv_pop_list=(RecyclerView) dialog.findViewById(R.id.rv_pop_list);
        rv_pop_list2=(RecyclerView) dialog.findViewById(R.id.rv_pop_list2);


        TextView title_text_view = (TextView) dialog.findViewById(R.id.title_text_view);
        title_text_view.setText(pinfo.item_name);

        pop_need_money_tv= (TextView) dialog.findViewById(R.id.pop_need_money_tv);

        ImageView  ll_close = (ImageView) dialog.findViewById(R.id.right_navigation_img);
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

        TextView bnt_select=(TextView) dialog.findViewById(R.id.bnt_select);
        bnt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int ppp=0;
                for(int i=0;i<allmyitem.size();i++)
                {
                    if(selectfoodFlavor==null)
                    {
                        if (allmyitem.get(i).selectplistinfo.isSingle.equals("0") && allmyitem.get(i).selectfoodSpec.item_code.equals(selectfoodSpec.item_code) && allmyitem.get(i).selectfoodFlavor.flavorName.equals(selectfoodFlavor.flavorName)) {
                            allmyitem.get(i).num++;
                            ppp = 1;
                        }
                    }
                    else if(selectfoodSpec!=null){
                        //规格相同的情况。+1
                        if (allmyitem.get(i).selectplistinfo.isSingle.equals("0") && allmyitem.get(i).selectfoodSpec.item_code.equals(selectfoodSpec.item_code)) {
                            allmyitem.get(i).num++;
                            ppp = 1;
                        }
                    }
                }
                if(ppp==0) {
                    StoreMenuListEntity1 s1=new StoreMenuListEntity1();
                    s1.selectplistinfo=selectplistinfo;
                    s1.selectfoodFlavor=selectfoodFlavor;
                    s1.selectfoodSpec=selectfoodSpec;
                    s1.num=1;
                    allmyitem.add(s1);
                }
                reCountCardata();

                // TODO Auto-generated method stub
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    isdialog = false;
                }
            }
        });

        dialog.show();
        isdialog = true;
        requestStoreSmallItemDetail(pinfo.GroupId);
    }

    private void reCountCardata()
    {
 //       tvScore.setText("11111111");


        totalPrice=0;
       // cart_state_text_view;
        int count=0;
        for(int i=0;i<allmyitem.size();i++)
        {
            count=count+allmyitem.get(i).num;
            if(allmyitem.get(i).selectplistinfo.isSingle.equals("1"))
            {
                totalPrice=totalPrice+(Double.parseDouble(allmyitem.get(i).selectplistinfo.item_p)*count);

            }
            else
            {
                totalPrice=totalPrice+(Double.parseDouble(allmyitem.get(i).selectfoodSpec.item_p)*count);
            }
        }

        cart_count_text_view.setText(count+"");
        cart_state_text_view.setText(totalPrice+"");



    }

    private void initData(){
       // tvTitle.setText(getIntent().getStringExtra("custom_name"));
        mSaleCustomCode = getIntent().getStringExtra("sale_custom_code");
        requestStoreItemDetail(S.get(XsStoreDetailActivity.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, ""+AppConfig.latitude, ""+AppConfig.longitude, "1", "10");


        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        mAdapter = new XsStoreDetailMenuAdapter3(this, R.layout.layout_rt_menulist_item2, mData!=null?mData.Storeitems:null);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestStoreItemDetail2(S.get(XsStoreDetailActivity.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, ""+AppConfig.latitude, ""+AppConfig.longitude, mNextRequestPage, "10");
            }
        }, mRecyclerView);



        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                try {
//                    Intent intent = new Intent(XsStoreDetailActivity.this, SmGoodsDetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("SaleCustomCode", mSaleCustomCode);
//                    bundle.putString("ItemCode",allitemlist.get(position).item_code);
//                    bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

           selectplistinfo=allitemlist.get(position);
           if(allitemlist.get(position).isSingle.equals("0") && view.getId()==R.id.bnt_select)
           {
              showCustomDialog(XsStoreDetailActivity.this, R.layout.activity_shopdetail_menu_pop, R.style.dialog, true,allitemlist.get(position));
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

            }
          });



       // mAdapter.disableLoadMoreIfNotFullPage();
       // requestStoreItemDetail(S.get(XsStoreDetailActivity.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, ""+AppConfig.latitude, ""+AppConfig.longitude, 1, "10");
        requestStoreItemDetail2(S.get(XsStoreDetailActivity.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, ""+AppConfig.latitude, ""+AppConfig.longitude, 1, "10");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.tab1:
                mCurrIndex = 0;
                break;
            case R.id.tab2:
                mCurrIndex = 1;
                break;
        }

//        if (mCurrIndex != mIndex) {
//            FragmentTransaction transaction = getFragmentManager()
//                    .beginTransaction();
//            transaction.replace(R.id.content, fragments[mCurrIndex]);
//            transaction.commit();
//            for (int i = 0; i < linearLayouts.length; i++) {
//                if (i == mCurrIndex) {
//                    linearLayouts[i].setBackgroundResource(R.drawable.edit_bg);
//                    textViews[i].setTextColor(Color.parseColor("#e60012"));
//                } else {
//                    linearLayouts[i].setBackgroundResource(R.drawable.edit_bg_f2f2f2);
//                    textViews[i].setTextColor(Color.parseColor("#000000"));
//                }
//            }
//            mIndex = mCurrIndex;
//        }
    }

    private void requestStoreItemDetail(String customCode, String saleCustomCode, String latitude, String longitude, String pg, String pageSize){
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", customCode);
        params.put("sale_custom_code", saleCustomCode);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("pg", pg);
        params.put("pagesize", pageSize);

        OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreDetailActivity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                StoreItemDetailEntity entity = GsonUtils.changeGsonToBean(responseDescription, StoreItemDetailEntity.class);
                if("1".equals(entity.status)){

                    try {



                        Glide.with(XsStoreDetailActivity.this).load(entity.shop_thumnail_image).transform(new CornersTransform(XsStoreDetailActivity.this,10)).into(ivCustomImg);

                        Glide.with(XsStoreDetailActivity.this)
                                .load(entity.shop_thumnail_image)
                                .dontAnimate()
                                // 设置高斯模糊
                                .bitmapTransform(new BlurTransformation(XsStoreDetailActivity.this, 5, 1))
                                .into(top_img_allbk);


                        if(entity.score!=null&!entity.score.equals("")) {
                            tvScore.setText(entity.score);
                        }
                        if(entity.cnt!=null & !entity.cnt.equals("")) {
                            tvReviews.setText(entity.cnt);
                        }
                        tvBossComments.setText(entity.sale_custom_cnt);
                        tvDistance.setText(entity.distance + "km");
                        if(entity.score != null && !entity.score.isEmpty()){
                            ratingBar.setRating(Float.parseFloat(entity.score));
                        }else{
                            ratingBar.setRating(0);
                        }
                      //  ((XsMenuFragment)menuFragment).sendData(entity);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{

                }

                requestStoreDetail(S.get(XsStoreDetailActivity.this, C.KEY_JSON_CUSTOM_CODE), mSaleCustomCode, "37.434668", "122.160742");
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL+"StoreCate/requestStoreItemDetail", GsonUtils.createGsonString(params));
    }


    private void requestStoreDetail(String customCode, String saleCustom, String latitude, String longitude){
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", customCode);
        params.put("sale_custom_code", saleCustom);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        OkHttpHelper okHttpHelper = new OkHttpHelper(XsStoreDetailActivity.this);
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
        }, Constant.XS_BASE_URL+"StoreCate/requestStoreDetail", GsonUtils.createGsonString(params));
    }

    public interface CallBackValue1{
        public void sendData(StoreItemDetailEntity entity);
    }


    private void requestStoreItemDetail2(String customCode, String saleCustomCode, String latitude, String longitude, int pg, String pageSize){

        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", customCode);
        params.put("sale_custom_code", saleCustomCode);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("pg", ""+pg);
        params.put("pagesize", pageSize);

        if(pg==1)
        {
            allitemlist.clear();
        }
        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                entity = GsonUtils.changeGsonToBean(responseDescription, StoreMenuListEntity1.class);
                tv_shopmame.setText(entity.data.StoreInfo.custom_name);
                tv_sale_m.setText(getResources().getString(R.string.frame_con11)+entity.data.StoreInfo.sale_cnt);
               // mTel=entity.data.StoreInfo.

                if("1".equals(entity.status)){
                    try {
                        if(entity.data.plist!=null && entity.data.plist.size()>0){
                            allitemlist.addAll(entity.data.plist);
                            mNextRequestPage++;
                            mAdapter.loadMoreComplete();
                            mAdapter.addData(entity.data.plist);
                            //  mAdapter.loadMoreEnd(true);

                        }else{
                            mAdapter.loadMoreEnd(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
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

    private void requestStoreSmallItemDetail(String Groupid){

        HashMap<String, String> params = new HashMap<>();
        params.put("Groupid", Groupid);
        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                entity2 = GsonUtils.changeGsonToBean(responseDescription, StoreMenuListEntity1.class);

                if("1".equals(entity2.status)) {
                    try {
                        if (entity2.data.FoodSpec != null && entity2.data.FoodSpec.size() > 0) {


                            selectfoodSpec=entity2.data.FoodSpec.get(0);

                            linear_itemlist1.setVisibility(View.VISIBLE);

                            rv_pop_list.setLayoutManager(new GridLayoutManager(XsStoreDetailActivity.this, 3));
                            mAdapter4 = new XsStoreDetailMenuAdapter4(XsStoreDetailActivity.this, R.layout.layout_rt_menulist_item3, entity2.data.FoodSpec!=null?entity2.data.FoodSpec:null);
                            rv_pop_list.setHasFixedSize(true);
                            rv_pop_list.setAdapter(mAdapter4);
                            mAdapter4.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                                    selectfoodSpec=entity2.data.FoodSpec.get(position);


                                   pop_need_money_tv.setText(entity2.data.FoodSpec.get(position).item_p);
                                   for(int i=0;i<rv_pop_list.getChildCount();i++)
                                   {
                                       View v=rv_pop_list.getChildAt(i);
                                       TextView v1=(TextView)v.findViewById(R.id.tv_listitemname);
                                       v1.setBackgroundResource(R.drawable.new_shop_shape5);
                                       v1.setTextColor(Color.parseColor("#b3b3b3"));
                                   }

                                    TextView tv_listitemname=(TextView)view;
                                    tv_listitemname.setBackgroundResource(R.drawable.new_shop_shape4);
                                    tv_listitemname.setTextColor(Color.parseColor("#ffffff"));

                                }
                            });

                            //设置默认值为第一个
                            pop_need_money_tv.setText(entity2.data.FoodSpec.get(0).item_p);


                        } else {
                            linear_itemlist1.setVisibility(View.GONE);
                        }

                        if (entity2.data.FoodFlavor != null && entity2.data.FoodFlavor.size() > 0) {
                            linear_itemlist2.setVisibility(View.VISIBLE);


                            selectfoodFlavor=entity2.data.FoodFlavor.get(0);
                            rv_pop_list2.setLayoutManager(new GridLayoutManager(XsStoreDetailActivity.this, 3));
                            mAdapter5 = new XsStoreDetailMenuAdapter5(XsStoreDetailActivity.this, R.layout.layout_rt_menulist_item3, entity2.data.FoodFlavor!=null?entity2.data.FoodFlavor:null);
                            rv_pop_list2.setHasFixedSize(true);
                            rv_pop_list2.setAdapter(mAdapter5);
                            mAdapter5.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    selectfoodFlavor=entity2.data.FoodFlavor.get(position);
                                    for(int i=0;i<rv_pop_list2.getChildCount();i++)
                                    {
                                        View v=rv_pop_list2.getChildAt(i);
                                        TextView v1=(TextView)v.findViewById(R.id.tv_listitemname);
                                        v1.setBackgroundResource(R.drawable.new_shop_shape5);
                                        v1.setTextColor(Color.parseColor("#b3b3b3"));
                                    }

                                    TextView tv_listitemname=(TextView)view;
                                    tv_listitemname.setBackgroundResource(R.drawable.new_shop_shape4);
                                    tv_listitemname.setTextColor(Color.parseColor("#ffffff"));

                                }
                            });


                        }
                        else
                        {
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
        }, "http://mall.gigawon.co.kr:8800/api/StoreCate/requestStoreDetailByGroupid", GsonUtils.createGsonString(params));
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

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {


                StoreItemDetailEntity entity = GsonUtils.changeGsonToBean(responseDescription, StoreItemDetailEntity.class);
                if("1".equals(entity.status)){
                    try {
                        if(entity.Storeitems!=null && entity.Storeitems.size()>0){
                            mNextRequestPage++;
                            mAdapter.loadMoreComplete();
                          //  mAdapter.addData(entity.Storeitems);
                          //  mAdapter.loadMoreEnd(true);
                        }else{
                            mAdapter.loadMoreEnd(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
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
        }, Constant.XS_BASE_URL+"StoreCate/requestStoreItemDetail", GsonUtils.createGsonString(params));
    }


    public void drawCart() {
        // TODO Auto-generated method stub

        try {

            menuCount = 0;

            cart_area.setVisibility(View.VISIBLE);

            cart_icon.setVisibility(View.GONE);

            cart_count_text_view.setVisibility(View.GONE);

            JSONArray orgArr = menuListAdapter.getListItems();

            JSONArray arr = new JSONArray();

            for (int i = 0; i < orgArr.length(); i++) {

                JSONObject item = orgArr.getJSONObject(i);

                if (item.getString("type").equals("menu")) {

                    int count = item.getInt("count");

                    if (count > 0) {

                        arr.put(item);

                        //총 갯수
                        menuCount = menuCount + count;

                    }

                }

            }

            cart_count_text_view_2.setText("" + menuCount);



        } catch (Exception e) {

            L.e(e);

        }

    }

    public static void addToShopcart(String item_code, String item_quantity, final Context context, String div_code, String sale_custom_code){
        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
            params.put(C.KEY_JSON_FM_ITEM_QUANTITI, item_quantity);
           // params.put(C.KEY_JSON_TOKEN, mToken);
            params.put("div_code", div_code);
            params.put("sale_custom_code", sale_custom_code);
            OkHttpHelper okHttpHelper = new OkHttpHelper(context);
            okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {

                }

                @Override
                public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                    try {
                        if(data.getString("status").equals("1") ){

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

    public  class CornersTransform extends BitmapTransformation {
        private float radius;

        public CornersTransform(Context context) {
            super(context);
            radius = 10;
        }

        public CornersTransform(Context context, float radius) {
            super(context);
            this.radius = radius;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return cornersCrop(pool, toTransform);
        }

        private Bitmap cornersCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

}
