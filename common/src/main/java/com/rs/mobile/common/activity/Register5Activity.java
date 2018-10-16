package com.rs.mobile.common.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.R;
import com.rs.mobile.common.entity.MemberShipEntity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.EncryptUtils;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.view.StateButton;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class Register5Activity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView tvTitle;

    private EditText etText1, etText2, etText3, etText4, etText5, etText6, etText7;
    private StateButton btnNext;

    private String mTel, mAuthNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register5);

        initView();
    }

    private void initView(){
        mTel = getIntent().getStringExtra("TEL");
        mAuthNum = getIntent().getStringExtra("AuthNum");

        btnBack = (ImageView) findViewById(R.id.btn_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("단체 회원가입");
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
        params.put("business_type", "4");
        params.put("nickname", etText2.getText().toString());
        params.put("email", etText3.getText().toString());
        params.put("custom_name", etText4.getText().toString());
        params.put("top_zip_code", etText5.getText().toString());
        params.put("top_addr_head", etText6.getText().toString());
        params.put("top_addr_detail", etText7.getText().toString());

        params.put("comp_class", "");
        params.put("comp_type", "");
        params.put("company_num", "");
        params.put("zip_code", "");
        params.put("kor_addr", "");
        params.put("kor_addr_detail", "");
        params.put("telephon", "");

        OkHttpHelper okHttpHelper = new OkHttpHelper(Register5Activity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                MemberShipEntity entity = GsonUtils.changeGsonToBean(responseDescription, MemberShipEntity.class);
                if("1".equals(entity.status)){
                    Toast.makeText(Register5Activity.this, entity.msg, Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(Register5Activity.this, entity.msg, Toast.LENGTH_LONG).show();
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
