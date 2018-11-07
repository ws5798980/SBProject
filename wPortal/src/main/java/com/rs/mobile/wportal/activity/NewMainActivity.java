package com.rs.mobile.wportal.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.view.RecycleViewDivider;
import com.rs.mobile.wportal.CaptureUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.NaverMap.CustomDialogListener;
import com.rs.mobile.wportal.activity.sm.SmNew_shopcatList;
import com.rs.mobile.wportal.activity.sm.SmSeachActivity;
import com.rs.mobile.wportal.activity.xsgr.NewWaimaiActivity;
import com.rs.mobile.wportal.activity.xsgr.XsStoreDetailActivity2;
import com.rs.mobile.wportal.activity.xsgr.XsStoreListActivity;
import com.rs.mobile.wportal.activity.xsgr.custom_dialog;
import com.rs.mobile.wportal.activity.xsgr.kfmemain;
import com.rs.mobile.wportal.adapter.xsgr.NewshopAdapter;
import com.rs.mobile.wportal.adapter.xsgr.XsIndexAdapter1;
import com.rs.mobile.wportal.adapter.xsgr.XsIndexAdapter2;
import com.rs.mobile.wportal.biz.xsgr.BotBannerBeans;
import com.rs.mobile.wportal.biz.xsgr.NavarMapBean;
import com.rs.mobile.wportal.biz.xsgr.NewShopBean;
import com.rs.mobile.wportal.biz.xsgr.TopBannerBeans;
import com.rs.mobile.wportal.entity.XsStoreDetailMenuItem;
import com.rs.mobile.wportal.network.HttpConnection;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class NewMainActivity extends BaseActivity {
    private ImageView btnSearch;
    private TextView AddressTitle;
    XsStoreDetailMenuItem entity;
    NewShopBean newShopBeans;
    TopBannerBeans topBannerBeans;
    BotBannerBeans botBannerBeans;
    RollPagerView rollPagerView;
    TestNomalAdapter topImgAdapter;
    RecyclerView recyclerView, list_newshop;
    XsIndexAdapter2 adapter;
    ImageView botbannerImg;
    NewshopAdapter newshopAdapter;
    LocationManager manager;
    private final String TAG = "MainActivity";
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;

    public custom_dialog cdialog;

    Double latitude = 0.0;
    Double longitude = 0.0;

    public Activity thisActivity;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                S.setShare(getApplicationContext(), "address_naver", msg.obj.toString());
                AddressTitle.setText(msg.obj.toString());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newmainactivity);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog().build());
        thisActivity = this;
        initView();
        initData();
    }

    private void initData() {
        requestStoreItemDetail();
        requestStoreItemDetail1();
        requestbotbanner();
        requestnewshop();
    }

    private void initView() {
        list_newshop = (RecyclerView) findViewById(R.id.list_newshop);
        list_newshop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        btnSearch = (ImageView) findViewById(R.id.btn_serch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearch = new Intent(NewMainActivity.this, SmSeachActivity.class);
                startActivity(intentSearch);
            }
        });

        AddressTitle = (TextView) findViewById(R.id._addresstitle);

        AddressTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdialog.show();
            }
        });

        AddressTitle = (TextView) findViewById(R.id._addresstitle);

        if (S.getShare(getApplicationContext(), "address_naver", "").isEmpty()) {
            AddressTitle.setText(getResources().getString(R.string.search_no_result2));
        } else {
            AddressTitle.setText(S.getShare(getApplicationContext(), "address_naver", ""));
        }

        if (S.getShare(getApplicationContext(), "address_naver", "").isEmpty()) {
            currentLocationAddress();
        }

        botbannerImg = (ImageView) findViewById(R.id.botbannerImg);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(NewMainActivity.this, 3));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.greywhite2)));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL, 1, getResources().getColor(R.color.greywhite2)));
        rollPagerView = (RollPagerView) findViewById(R.id.viewPager);
        topImgAdapter = new TestNomalAdapter(rollPagerView);
        rollPagerView.setAdapter(topImgAdapter);
        rollPagerView.pause();
        cdialog = new custom_dialog(this);
        cdialog.setCustomDL(new CustomDialogListener() {
            @Override
            public void onDialogBtnClickListener(View v) {
                switch (v.getId()) {
                    case R.id.res_img_compass:
                        //Toast.makeText(getApplicationContext(),"compass", Toast.LENGTH_LONG).show();
                        /*Intent intent1 = new Intent(thisActivity, com.rs.mobile.wportal.activity.NaverMap.Activity_NaverMap_Main.class);
                        intent1.putExtra("wiche", "compass");
                        startActivityForResult(intent1, 1000);*/
                        currentLocationAddress();
                        //MainInitialization();
                        break;
                    case R.id.res_img_location:
                        //Toast.makeText(getApplicationContext(), "location", Toast.LENGTH_LONG).show();
//                        Intent intent2 = new Intent(thisActivity, com.rs.mobile.wportal.activity.NaverMap.Activity_NaverMap_Main.class);

                        Intent intent2 = new Intent(thisActivity, BaiduMapActivity.class);

                        intent2.putExtra("wiche", "location");
                        startActivityForResult(intent2, 1001);
                        break;
                }
                cdialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 수행을 제대로 한 경우-----
        if (requestCode == 0) {
            if (resultCode == RESULT_OK && data != null) {
//                String result = data.getStringExtra("resultSetting");
                CaptureUtil.handleResultScaning(NewMainActivity.this,
                        data.getStringExtra("result"), "");


            }
            // 수행을 제대로 하지 못한 경우
            else if (resultCode == RESULT_CANCELED) {

            }
        } else if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                AppConfig.address = result;
                AddressTitle.setText(result);

            }
        } else if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                AddressTitle.setText(result);
            }
        }
        //--------
    }


    private class TestNomalAdapter extends LoopPagerAdapter {
        boolean isload = false;
        List<TopBannerBeans.DataBean.TopBannerBean> lists;
        private int[] imgs = {
                R.drawable.banner01,
        };

        public TestNomalAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        public void setImgs(List<TopBannerBeans.DataBean.TopBannerBean> lists) {
            this.lists = lists;
            isload = true;
            notifyDataSetChanged();
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if (isload) {
                Picasso.get().load(lists.get(position).getAd_image()).into(view);
//                Glide.with(NewMainActivity.this).load(lists.get(position).getAd_image()).into(view);

            } else {
                view.setImageResource(imgs[position]);
            }
            return view;
        }

        @Override
        public int getRealCount() {
            if (isload) {
                return lists.size();
            }
            return imgs.length;
        }
    }

    private void requestStoreItemDetail1() {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);

        String custom_code = S.get(NewMainActivity.this, C.KEY_JSON_CUSTOM_CODE);
        String token = S.get(NewMainActivity.this, C.KEY_JSON_TOKEN);
        if (custom_code != null && !custom_code.equals("") && token != null && !token.equals("")) {
            params.put("user_id", custom_code);
            params.put("token", token);
        } else {
            params.put("custom_code", "");
            params.put("token", "");
        }

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                entity = GsonUtils.changeGsonToBean(responseDescription, XsStoreDetailMenuItem.class);
                final List<XsStoreDetailMenuItem.datafav> lists = entity.dataFav;
                for (int i = 0; i < entity.data.size(); i++) {
                    lists.add(entity.data.get(i).toStoreitem());
                }
                adapter = new XsIndexAdapter2(NewMainActivity.this, R.layout.layout_index_bigitem2, lists);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        String catid = lists.get(position).level1;
                        if (catid.equals("1")) {
                            Intent intent = new Intent(NewMainActivity.this, NewWaimaiActivity.class);
                            intent.putExtra("lv1", catid);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(NewMainActivity.this, XsStoreListActivity.class);
                            intent.putExtra("lv1", catid);
                            startActivity(intent);
                        }

                    }
                });
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://mall." + (AppConfig.CHOOSE.equals("CN") ? "gigawon.cn" : "gigawon.co.kr") + ":8800/api/StoreCate/requestStoreCate1FavList", GsonUtils.createGsonString(params));
    }


    private void requestStoreItemDetail() {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                topBannerBeans = GsonUtils.changeGsonToBean(responseDescription, TopBannerBeans.class);
                List<TopBannerBeans.DataBean.TopBannerBean> beans = new ArrayList<>();
                if (topBannerBeans.getData() != null) {
                    if (topBannerBeans.getData().getTopBanner() != null && topBannerBeans.getData().getTopBanner().size() != 0) {
                        rollPagerView.resume();
                        topImgAdapter.setImgs(topBannerBeans.getData().getTopBanner());
                    }
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://mall." + (AppConfig.CHOOSE.equals("CN") ? "gigawon.cn" : "gigawon.co.kr") + ":8800/api/StoreCate/requestTopBanner", GsonUtils.createGsonString(params));
    }

    private void requestbotbanner() {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                botBannerBeans = GsonUtils.changeGsonToBean(responseDescription, BotBannerBeans.class);
                if (botBannerBeans.getData() != null && botBannerBeans.getData().getBotBanner() != null && botBannerBeans.getData().getBotBanner().size() != 0) {
                    Glide.with(NewMainActivity.this).load(botBannerBeans.getData().getBotBanner().get(0).getAd_image()).into(botbannerImg);
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://mall." + (AppConfig.CHOOSE.equals("CN") ? "gigawon.cn" : "gigawon.co.kr") + ":8800/api/StoreCate/requestBotBanner", GsonUtils.createGsonString(params));
    }

    private void requestnewshop() {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                newShopBeans = GsonUtils.changeGsonToBean(responseDescription, NewShopBean.class);
                if (newShopBeans != null && newShopBeans.getStatus().equals("1")) {
                    newshopAdapter = new NewshopAdapter(NewMainActivity.this, R.layout.item_newshop, newShopBeans.getData().getNewStore());
                    list_newshop.setAdapter(newshopAdapter);
                    newshopAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(NewMainActivity.this, XsStoreDetailActivity2.class);
                            intent.putExtra("custom_name", newShopBeans.getData().getNewStore().get(position).getCUSTOM_NAME());
                            intent.putExtra("sale_custom_code", newShopBeans.getData().getNewStore().get(position).getCUSTOM_CODE());
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
        }, "http://mall." + (AppConfig.CHOOSE.equals("CN") ? "gigawon.cn" : "gigawon.co.kr") + ":8800/api/StoreCate/requestNewStoreList", GsonUtils.createGsonString(params));
    }

    //GPS Start
    public String currentLocationAddress() {
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        mLocationClient.registerLocationListener(mBDLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setPriority(LocationClientOption.NetWorkFirst);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
//        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//
//        long minTime = 1000;
//        float minDistance = 1;
//
//        if (ActivityCompat.checkSelfPermission(this.thisActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(thisActivity, "Don't have permissions.", Toast.LENGTH_LONG).show();
//            return "noPermission";
//        }
////        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mLocationListener);
//        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, mLocationListener);
        return "";
    }


    //GPS Stop
    public void stopLocationService() {
        if (ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(thisActivity, "Don't have permissions.", Toast.LENGTH_LONG).show();
        }
    }

    //Mobile Phone GPS Get Value <-> Namver API Address Searche
    private HttpConnection httpConn = HttpConnection.getInstance();
    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            S.setShare(getApplicationContext(), "pointX", latitude.toString());
            S.setShare(getApplicationContext(), "pointY", longitude.toString());
            String ret_value;

            //ret_value = httpConn.NaverAPIAddressToLatlng("https://openapi.naver.com/v1/map/geocode", tv_serche_text.getText().toString(),"UTF-8,","4chFLI5miI3dKcMQ6paO","fLSoYJawMm");

            ret_value = httpConn.NaverAPILatingToAddress("https://openapi.naver.com/v1/map/reversegeocode", Double.toString(latitude), Double.toString(longitude), "UTF-8", "4chFLI5miI3dKcMQ6paO", "fLSoYJawMm");
            //{    "errorMessage": "검색 결과가 없습니다.",    "errorCode": "MP03"}
            try {
                String errorMessage = "";
                //JSONArray array = new JSONArray(ret_value);
                //for(int i = 0; i<array.length(); i++){
                JSONObject obj = new JSONObject(ret_value);
                if (obj.getString("errorCode") != "0") {
                    errorMessage = obj.getString("errorMessage");
                    Log.i("ErrorMessage : ", errorMessage);
                } else {

                }
                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = errorMessage;
                mHandler.sendMessage(msg);
                //}
            } catch (JSONException ex) {

            }

            stopLocationService();
        }

        public void onProviderDisabled(String provider) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                S.setShare(getApplicationContext(), "pointX", latitude + "");
                S.setShare(getApplicationContext(), "pointY", longitude + "");
                String ret_value;

                //ret_value = httpConn.NaverAPIAddressToLatlng("https://openapi.naver.com/v1/map/geocode", tv_serche_text.getText().toString(),"UTF-8,","4chFLI5miI3dKcMQ6paO","fLSoYJawMm");
//                ret_value = httpConn.NaverAPILatingToAddress("https://openapi.naver.com/v1/map/reversegeocode", Double.toString(longitude), Double.toString(latitude), "UTF-8", "4chFLI5miI3dKcMQ6paO", "fLSoYJawMm");

                ret_value = httpConn.NaverAPILatingToAddress("https://openapi.naver.com/v1/map/reversegeocode", Double.toString(longitude), Double.toString(latitude), "UTF-8", "4chFLI5miI3dKcMQ6paO", "fLSoYJawMm");
                //{    "errorMessage": "검색 결과가 없습니다.",    "errorCode": "MP03"}
                try {
                    String errorMessage = "";
                    //JSONArray array = new JSONArray(ret_value);
                    //for(int i = 0; i<array.length(); i++){
                    JSONObject obj = new JSONObject(ret_value);

                    if (obj.has("errorCode")) {
                        errorMessage = obj.getString("errorMessage");
                        Log.i("ErrorMessage : ", errorMessage);
                    } else {
                        NavarMapBean bean = GsonUtils.changeGsonToBean(obj.toString(), NavarMapBean.class);
                        errorMessage = bean.getResult().getItems().get(0).getAddress();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = 0;
                    msg.obj = errorMessage;
                    mHandler.sendMessage(msg);
                    //}
                } catch (JSONException ex) {

                }


                if (mLocationClient.isStarted()) {

                    mLocationClient.stop();
                }
            }
        }
    }

}
