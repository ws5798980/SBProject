package com.rs.mobile.wportal.activity.xsgr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.CommodityItemAdapter;
import com.rs.mobile.wportal.biz.xsgr.AddressSearch;
import com.rs.mobile.wportal.biz.xsgr.CommodityList;
import com.rs.mobile.wportal.biz.xsgr.JsonBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class LocationChangeActivity extends BaseActivity {

    private RelativeLayout locationNo;
    private RelativeLayout locationName;
    private EditText editText;
    private LinearLayout back;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private TextView textNo, textLocation, editLocation;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private PopupWindow popupWindow;
    private WindowManager.LayoutParams params;
    private boolean mIsShowing = false;
    private MyPopupEditAdapter popAdapter;
    private List<AddressSearch.DataBean.PostBean.ItemlistBean.ItemBean> list = new ArrayList<>();
    private int mNextRequestPage = 2;
    private String zipCode, addr, addrDetail;
    private TextView totalNum,save;
    private LinearLayout itemNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_change);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        zipCode = bundle.getString("zipCode");
        addr = bundle.getString("addr");
        addrDetail = bundle.getString("addrDetail");
        locationNo = (RelativeLayout) findViewById(R.id.tv_location_no);
        locationName = (RelativeLayout) findViewById(R.id.layout_location_name);
        back = (LinearLayout) findViewById(R.id.close_btn);
        textNo = (TextView) findViewById(R.id.tv_name);
        textLocation = (TextView) findViewById(R.id.location_name);
        editText = (EditText) findViewById(R.id.edt_location_text);
        editLocation = (TextView) findViewById(R.id.title_edit_view);
        save = (TextView) findViewById(R.id.save_up);
        params = getWindow().getAttributes();
        textNo.setText(zipCode + "");
        textLocation.setText(addr);
        editText.setText(addrDetail);
    }

    private void initListener() {
//        locationName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPickerView();
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("textNo", textNo.getText().toString().trim());
//                intent.putExtra("textLocation", textLocation.getText().toString().trim());
//                intent.putExtra("detailLocation", editText.getText().toString().trim());
//                setResult(RESULT_OK, intent);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("textNo", textNo.getText().toString().trim());
                intent.putExtra("textLocation", textLocation.getText().toString().trim());
                intent.putExtra("detailLocation", editText.getText().toString().trim());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
    }


    public void popup() {
        if (popupWindow == null) {
            initPopup();
        }
        if (!mIsShowing) {
            params.alpha = 0.3f;
            getWindow().setAttributes(params);
            popupWindow.showAtLocation(findViewById(R.id.location_change), Gravity.BOTTOM, 0, 0);
            mIsShowing = true;
        }
    }

    private void initPopup() {
        View emptyView = LayoutInflater.from(this).inflate(R.layout.pop_layout_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        View pop = View.inflate(this, R.layout.mypop_change_location, null);
        popupWindow = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//        lp.alpha = 0.7f;
//        this.getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                dismiss();
//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 1f;
//                getWindow().setAttributes(lp);
//                mIsShowing = false;
            }
        });
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        ImageView close = (ImageView) pop.findViewById(R.id.title_edit_view);
        final EditText searchText = (EditText) pop.findViewById(R.id.search_location);
        Button search = (Button) pop.findViewById(R.id.button_get);
        itemNum = (LinearLayout) pop.findViewById(R.id.item_num);
        totalNum = (TextView) pop.findViewById(R.id.result_num);
        RecyclerView popRecycler = (RecyclerView) pop.findViewById(R.id.poprecycler_view);
        popRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        popAdapter = new MyPopupEditAdapter(R.layout.popup_recycler_item_change, list);
        popAdapter.bindToRecyclerView(popRecycler);
        popAdapter.setEmptyView(emptyView);
        popAdapter.disableLoadMoreIfNotFullPage();
        popAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestData(mNextRequestPage, searchText.getText().toString().trim());

            }
        });
        popAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.choose_dine) {
                    textNo.setText(list.get(position).getPostcd().getCdatasection());
                    textLocation.setText(list.get(position).getAddrjibun().getCdatasection());
                    popupWindow.dismiss();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                requestData(1, searchText.getText().toString().trim());
            }
        });
        mIsShowing = false;
    }

    private void requestData(final int page, String str) {
        HashMap<String, Object> params = new HashMap<>();
//        params.put("lang_type", "kor");
//        params.put("token", S.getShare(this, C.KEY_JSON_TOKEN, ""));
//        params.put("custom_code", S.getShare(this, C.KEY_JSON_CUSTOM_CODE, ""));
//        params.put("selling", 1);
        params.put("pg", page);
        params.put("psize", 10);
        params.put("key", str);
        OkHttpHelper okHttpHelper = new OkHttpHelper(this, true);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                AddressSearch entity = GsonUtils.changeGsonToBean(responseDescription, AddressSearch.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(entity.getStatus())) {

                    if (page == 1) {
                        list = entity.getData().getPost().getItemlist().getItem();
                        mNextRequestPage = 2;
                        popAdapter.setNewData(list);
                        itemNum.setVisibility(View.VISIBLE);
                        totalNum.setText(list.size() + "");
                        //  mAdapter.loadMoreEnd(true);
                    } else {
                        if (entity.getData() != null && entity.getData().getPost().getItemlist().getItem().size() > 0) {
                            mNextRequestPage++;
                            list.addAll(entity.getData().getPost().getItemlist().getItem());
                            popAdapter.loadMoreComplete();
                            popAdapter.addData(entity.getData().getPost().getItemlist().getItem());
                            totalNum.setText(list.size() + "");
                            itemNum.setVisibility(View.VISIBLE);
                        } else {
                            popAdapter.loadMoreComplete();
                            popAdapter.loadMoreEnd(true);
                            totalNum.setText(list.size() + "");
                            itemNum.setVisibility(View.VISIBLE);
                        }
                    }
                } else {

                    popAdapter.loadMoreComplete();
                    popAdapter.loadMoreEnd(true);
                    itemNum.setVisibility(View.GONE);
                    Toast.makeText(LocationChangeActivity.this, entity.getMessage() + "", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                Log.e("responseDescription456", responseDescription);
//                Log.e("JSONObject",data.toString());
                Log.e("flag145", flag);
                popAdapter.loadMoreComplete();
                popAdapter.loadMoreEnd(true);
                itemNum.setVisibility(View.GONE);
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
                popAdapter.loadMoreComplete();
                popAdapter.loadMoreEnd(true);
                itemNum.setVisibility(View.GONE);
            }
        }, Constant.XSGR_TEST_URL + "common/GetKorAddressList", GsonUtils.createGsonString(params));
    }


    public void dismiss() {
        if (popupWindow != null && mIsShowing) {
            popupWindow.dismiss();
            mIsShowing = false;
            params.alpha = 1f;
            getWindow().setAttributes(params);
        }
    }

    class MyPopupEditAdapter extends BaseQuickAdapter<AddressSearch.DataBean.PostBean.ItemlistBean.ItemBean, BaseViewHolder> {

        public MyPopupEditAdapter(int layoutResId, @Nullable List<AddressSearch.DataBean.PostBean.ItemlistBean.ItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AddressSearch.DataBean.PostBean.ItemlistBean.ItemBean item) {
            helper.setText(R.id.position_content, item.getAddrjibun().getCdatasection());
            helper.setText(R.id.youbian_content, item.getPostcd().getCdatasection());
            helper.addOnClickListener(R.id.choose_dine);
        }
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);

                Toast.makeText(LocationChangeActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Toast.makeText(LocationChangeActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(LocationChangeActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);

        }

//        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    private class GetJsonDataUtil {


        public String getJson(Context context, String fileName) {

            StringBuilder stringBuilder = new StringBuilder();
            try {
                AssetManager assetManager = context.getAssets();
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        assetManager.open(fileName)));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent();
//        intent.putExtra("textNo", textNo.getText().toString().trim());
//        intent.putExtra("textLocation", textLocation.getText().toString().trim());
//        intent.putExtra("detailLocation", editText.getText().toString().trim());
//        setResult(RESULT_OK, intent);
        finish();
    }
}
