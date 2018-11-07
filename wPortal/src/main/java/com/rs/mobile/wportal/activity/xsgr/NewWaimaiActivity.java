package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.view.RecycleViewDivider;
import com.rs.mobile.wportal.CaptureUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.NewMainActivity;
import com.rs.mobile.wportal.activity.sm.SmNew_shopcatList;
import com.rs.mobile.wportal.activity.sm.SmSeachActivity;
import com.rs.mobile.wportal.activity.sm.SmSeachLevelActivity;
import com.rs.mobile.wportal.adapter.xsgr.NewWaimaishop2Adapter;
import com.rs.mobile.wportal.adapter.xsgr.NewWaimaishopAdapter;
import com.rs.mobile.wportal.adapter.xsgr.NewshopAdapter;
import com.rs.mobile.wportal.adapter.xsgr.XsIndexAdapter2;
import com.rs.mobile.wportal.adapter.xsgr.XsIndexAdapter3;
import com.rs.mobile.wportal.biz.xsgr.BotBannerBeans;
import com.rs.mobile.wportal.biz.xsgr.NewShopBean;
import com.rs.mobile.wportal.biz.xsgr.NewShopWaimaiBean;
import com.rs.mobile.wportal.biz.xsgr.NewWaimaiShopBean;
import com.rs.mobile.wportal.biz.xsgr.TopBannerBeans;
import com.rs.mobile.wportal.entity.ListItem;
import com.rs.mobile.wportal.entity.StoreCateListEntity;
import com.rs.mobile.wportal.entity.XsStoreDetailMenuItem;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class NewWaimaiActivity extends BaseActivity {
    private ImageView btnSearch;
    XsStoreDetailMenuItem entity;
    NewWaimaiShopBean newShopBeans;
    TopBannerBeans topBannerBeans;
    BotBannerBeans botBannerBeans;
    RollPagerView rollPagerView;
    TestNomalAdapter topImgAdapter;
    RecyclerView recyclerView, list_newshop, list2;
    XsIndexAdapter3 adapter;
    ImageView botbannerImg;
    NewShopWaimaiBean bean;
    NewWaimaishopAdapter newshopAdapter;
    StoreCateListEntity entity2;
    NewWaimaishop2Adapter adapter2;
    LinearLayout layout_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newwaimaiactivity);

        initView();
        initData();
    }



    private void initData() {
        requestStoreCateList();
        requestStoreItemDetail();
        requestbotbanner();
        requestnewshop();
        requestBotnewshop();
    }

    private void initView() {
        layout_back = (LinearLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list_newshop = (RecyclerView) findViewById(R.id.list_newshop);
        list_newshop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        btnSearch = (ImageView) findViewById(R.id.btn_serch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearch = new Intent(NewWaimaiActivity.this, SmSeachLevelActivity.class);
                startActivity(intentSearch);
            }
        });

        botbannerImg = (ImageView) findViewById(R.id.botbannerImg);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(NewWaimaiActivity.this, 3));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.HORIZONTAL, 3, getResources().getColor(R.color.greywhite2)));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL, 3, getResources().getColor(R.color.greywhite2)));
        topImgAdapter = new TestNomalAdapter();
        rollPagerView = (RollPagerView) findViewById(R.id.viewPager);
        rollPagerView.setAdapter(topImgAdapter);

        rollPagerView.pause();

        list2 = (RecyclerView) findViewById(R.id.list2);
        list2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }


    private class TestNomalAdapter extends StaticPagerAdapter {
        boolean isload = false;
        List<TopBannerBeans.DataBean.TopBannerBean> lists;
        private int[] imgs = {
                R.drawable.banner01,
        };

        public void setImgs(List<TopBannerBeans.DataBean.TopBannerBean> lists) {
            this.lists = lists;
            isload = true;
            notifyDataSetChanged();
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            if (isload) {
//                Glide.with(NewWaimaiActivity.this).load(lists.get(position).getAd_image()).into(view);
                Picasso.get().load(lists.get(position).getAd_image()).into(view);
            } else {
                view.setImageResource(imgs[position]);
            }
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            if (isload) {
                return lists.size();
            }
            return imgs.length;
        }
    }

    //获取二给分类数据
    private void requestStoreCateList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("div_code", S.get(NewWaimaiActivity.this, C.KEY_JSON_DIV_CODE));
        params.put("custom_code", S.get(NewWaimaiActivity.this, C.KEY_JSON_CUSTOM_CODE));
        params.put("token", S.get(NewWaimaiActivity.this, C.KEY_JSON_TOKEN));
        params.put("pg", "1");
        params.put("pagesize", "5");
        params.put("custom_lev1", "1");
        params.put("custom_lev2", "");
        params.put("custom_lev3", "");
        params.put("latitude", "" + AppConfig.latitude);
        params.put("longitude", "" + AppConfig.longitude);
        params.put("order_by", "1");
        OkHttpHelper okHttpHelper = new OkHttpHelper(NewWaimaiActivity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                entity2 = GsonUtils.changeGsonToBean(responseDescription, StoreCateListEntity.class);
                adapter = new XsIndexAdapter3(NewWaimaiActivity.this, R.layout.layout_index_bigitem3, entity2.data);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        String lv = entity2.data.get(position).custom_lev2;
                        Intent intent = new Intent(NewWaimaiActivity.this, XsStoreListActivity.class);
                        intent.putExtra("lv1", "1");
                        intent.putExtra("lv2", lv);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {

            }

            @Override
            public void onNetworkError(Request request, IOException e) {

            }
        }, Constant.XS_BASE_URL + "/StoreCate/requestStoreCate2List", GsonUtils.createGsonString(params));
    }

    private void requestStoreItemDetail() {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("level1", "1");
        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                topBannerBeans = GsonUtils.changeGsonToBean(responseDescription, TopBannerBeans.class);
                List<TopBannerBeans.DataBean.TopBannerBean> beans = new ArrayList<>();
                if (topBannerBeans.getData() != null) {
                    if (topBannerBeans.getData().getTopBanner() != null && topBannerBeans.getData().getTopBanner().size() != 0) {
                        topImgAdapter.setImgs(topBannerBeans.getData().getTopBanner());
                        rollPagerView.resume();
                    }
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://mall." + (AppConfig.CHOOSE.equals("CN") ? "gigawon.cn" : "gigawon.co.kr") + ":8800/api/StoreCate/requestCateTopBanner", GsonUtils.createGsonString(params));
    }

    private void requestbotbanner() {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("level1", "1");
        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                botBannerBeans = GsonUtils.changeGsonToBean(responseDescription, BotBannerBeans.class);
                if (botBannerBeans.getData() != null && botBannerBeans.getData().getBotBanner() != null && botBannerBeans.getData().getBotBanner().size() != 0) {
                    Glide.with(NewWaimaiActivity.this).load(botBannerBeans.getData().getBotBanner().get(0).getAd_image()).into(botbannerImg);
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://mall." + (AppConfig.CHOOSE.equals("CN") ? "gigawon.cn" : "gigawon.co.kr") + ":8800/api/StoreCate/requestCateBotBanner", GsonUtils.createGsonString(params));
    }

    private void requestnewshop() {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("level1", "1");

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                newShopBeans = GsonUtils.changeGsonToBean(responseDescription, NewWaimaiShopBean.class);
                if (newShopBeans != null && newShopBeans.getStatus().equals("1")) {
                    newshopAdapter = new NewWaimaishopAdapter(NewWaimaiActivity.this, R.layout.item_newshop, newShopBeans.getData().getPopularBanner());
                    list_newshop.setAdapter(newshopAdapter);
                    newshopAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(NewWaimaiActivity.this, XsStoreDetailActivity2.class);
                            intent.putExtra("custom_name", newShopBeans.getData().getPopularBanner().get(position).getCUSTOM_NAME());
                            intent.putExtra("sale_custom_code", newShopBeans.getData().getPopularBanner().get(position).getCustom_code());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://mall." + (AppConfig.CHOOSE.equals("CN") ? "gigawon.cn" : "gigawon.co.kr") + ":8800/api/StoreCate/requestCatePopularBanner", GsonUtils.createGsonString(params));
    }

    private void requestBotnewshop() {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("level1", "1");
        params.put("pagesize", "10");
        params.put("latitude", S.getShare(getApplicationContext(), "pointX", "0") + "");
        params.put("longitude", S.getShare(getApplicationContext(), "pointY", "0") + "");


        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                bean = GsonUtils.changeGsonToBean(responseDescription, NewShopWaimaiBean.class);
                if (bean != null && bean.getStatus().equals("1")) {
                    adapter2 = new NewWaimaishop2Adapter(NewWaimaiActivity.this, R.layout.item_newwaimai_bot, bean.getData().getNewStore());
                    list2.setAdapter(adapter2);
                    adapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(NewWaimaiActivity.this, XsStoreDetailActivity2.class);
                            intent.putExtra("custom_name", bean.getData().getNewStore().get(position).getCUSTOM_NAME());
                            intent.putExtra("sale_custom_code", bean.getData().getNewStore().get(position).getCUSTOM_CODE());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://mall." + (AppConfig.CHOOSE.equals("CN") ? "gigawon.cn" : "gigawon.co.kr") + ":8800/api/StoreCate/requestCateNewStoreList", GsonUtils.createGsonString(params));
    }
}
