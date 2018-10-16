package com.rs.mobile.common.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.R;
import com.rs.mobile.common.entity.MemberShipEntity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.EncryptUtils;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.view.ClearEditText;
import com.rs.mobile.common.view.StateButton;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class Register3Activity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView tvTitle;
    private EditText etText1, etText2, etText3, etText4, etText5, etText6, etText7, etText8, etText9, etText10, etText11, etText12, etText13, etText14, etText15;
    private Spinner spinner;
    private StateButton btnNext;

    private String mTel, mAuthNum;
    private int mRegFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        initView();
    }

    private void initView(){
        mTel = getIntent().getStringExtra("TEL");
        mAuthNum = getIntent().getStringExtra("AuthNum");
        mRegFlag = getIntent().getIntExtra("RegFlag",0);

        btnBack = (ImageView) findViewById(R.id.btn_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        if(mRegFlag == 2){
            tvTitle.setText("사업자 회원가입");
        }else if(mRegFlag == 3){
            tvTitle.setText("생산자 회원가입");
        }else if(mRegFlag == 4){
            tvTitle.setText("가맹점 회원가입");
        }else if(mRegFlag == 6){
            tvTitle.setText("상품관리자 회원가입");
        }else if(mRegFlag == 52){
            tvTitle.setText("단체 사업자 회원가입");
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etText1 = (EditText) findViewById(R.id.et_text1);
        etText2 = (EditText) findViewById(R.id.et_text2);
        etText3 = (EditText) findViewById(R.id.et_text3);
        etText4 = (EditText) findViewById(R.id.et_text4);
        etText5 = (EditText) findViewById(R.id.et_text5);
        etText6 = (EditText) findViewById(R.id.et_text6);
        etText7 = (EditText) findViewById(R.id.et_text7);
        etText8 = (EditText) findViewById(R.id.et_text8);
        etText9 = (EditText) findViewById(R.id.et_text9);
        etText10 = (EditText) findViewById(R.id.et_text10);
        etText11 = (EditText) findViewById(R.id.et_text11);
        etText12 = (EditText) findViewById(R.id.et_text12);
        etText13 = (EditText) findViewById(R.id.et_text13);
        etText14 = (EditText) findViewById(R.id.et_text14);
        etText15 = (EditText) findViewById(R.id.et_text15);
        spinner = (Spinner) findViewById(R.id.spinner1);

        String[] mItems = {"Item1", "Item2", "Item3", "Item4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnNext = (StateButton) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register(){
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("memid", mTel.replace("-", ""));
        params.put("AuthNum", mAuthNum);
        params.put("mempwd", EncryptUtils.SHA512(etText1.getText().toString()));
        if(mRegFlag == 2){  //企业
            params.put("business_type", "2");
        }else if(mRegFlag == 3){
            params.put("business_type", "5");
        }else if(mRegFlag == 4){
            params.put("business_type", "6");
        }else if(mRegFlag == 6){
            params.put("business_type", "8");
        }else if(mRegFlag == 53){
            params.put("business_type", "4");
        }
        params.put("nickname", etText2.getText().toString());
        params.put("email", etText3.getText().toString());
        params.put("custom_name", etText4.getText().toString());
        params.put("top_zip_code", etText5.getText().toString());
        params.put("top_addr_head", etText6.getText().toString());
        params.put("top_addr_detail", etText7.getText().toString());

        params.put("comp_class", (String)spinner.getSelectedItem());
        params.put("comp_type", etText8.getText().toString());
        params.put("company_num", etText9.getText().toString());
        params.put("zip_code", etText10.getText().toString());
        params.put("kor_addr", etText11.getText().toString());
        params.put("kor_addr_detail", etText12.getText().toString());
        params.put("telephon", etText13.getText().toString());

        OkHttpHelper okHttpHelper = new OkHttpHelper(Register3Activity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                MemberShipEntity entity = GsonUtils.changeGsonToBean(responseDescription, MemberShipEntity.class);
                if("1".equals(entity.status)){
                    Toast.makeText(Register3Activity.this, entity.msg, Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(Register3Activity.this, entity.msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://member.gigawon.co.kr:8808/api/Login/joinMember", GsonUtils.createGsonString(params));
    }
}
