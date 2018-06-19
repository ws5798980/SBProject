package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.entity.LoginEntity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.ClassifyMainAdapter;
import com.rs.mobile.wportal.adapter.xsgr.ClassifyMoreAdapter;
import com.rs.mobile.wportal.entity.Category1And2ListEntity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class Lv1MoreActivity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView tvTitle;
    private ListView mainlist;
    private ListView morelist;
    private List<Category1And2ListEntity.lev1> ClassifyMain;
    private List<Category1And2ListEntity.lev2> ClassifyMore;
    private ClassifyMainAdapter mainAdapter;
    private ClassifyMoreAdapter moreAdapter;
    private int count = 1;
    private int get_id;
    private int a = -1;
    private int main_postion = -1;
    private int list_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv1_more);

        initdata();
        initview();
    }

    private void initdata() {
        category1And2List("1");
    }

    private void initview() {
        btnBack = (ImageView) findViewById(R.id.btn_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mainlist = (ListView) findViewById(R.id.classify_mainlist);
        morelist = (ListView) findViewById(R.id.classify_morelist);
        ClassifyMain = new ArrayList<>();
        ClassifyMore = new ArrayList<>();

        mainAdapter = new ClassifyMainAdapter(Lv1MoreActivity.this,ClassifyMain,get_id);
        mainAdapter.setSelectItem(get_id);
        mainlist.setAdapter(mainAdapter);
        mainlist.setSelection(get_id - 1);
        mainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                initAdapter(ClassifyMore);
                mainAdapter.setSelectItem(position);
                mainAdapter.notifyDataSetChanged();
                a = position * 100;
                main_postion = position;
                category1And2List(ClassifyMain.get(position).lev);
            }
        });
        mainlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        initAdapter(ClassifyMore);

        morelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                moreAdapter.setSelectItem(position);
                moreAdapter.notifyDataSetChanged();
                if (a == -1){
                    a = get_id * 100;
                }
                if (main_postion ==  -1){
                    main_postion = get_id;
                }
                list_id = a + position;


                Intent intent = new Intent();
                intent.putExtra("lev1", ClassifyMore.get(position).lev1);
                intent.putExtra("lev2", ClassifyMore.get(position).lev);
                setResult(1, intent);
                finish();
            }
        });
    }

    private void initAdapter(List<Category1And2ListEntity.lev2> array) {
        moreAdapter = new ClassifyMoreAdapter(this, array);
        morelist.setAdapter(moreAdapter);
        moreAdapter.notifyDataSetChanged();
    }

    private void category1And2List(String customLev1){
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_lev1", customLev1);

        OkHttpHelper okHttpHelper = new OkHttpHelper(Lv1MoreActivity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                Category1And2ListEntity entity = GsonUtils.changeGsonToBean(responseDescription, Category1And2ListEntity.class);
                if("1".equals(entity.status)){
                    if(count > 0){
                        ClassifyMain = entity.lev1s;
                        mainAdapter.setData(ClassifyMain);
                        count--;
                    }
                    ClassifyMore = entity.lev2s;
                    moreAdapter.setData(ClassifyMore);
                }

            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL+"StoreCate/GetCategory1And2List", GsonUtils.createGsonString(params));
    }
}
