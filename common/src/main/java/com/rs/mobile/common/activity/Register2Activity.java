package com.rs.mobile.common.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.rs.mobile.common.view.ClearEditText;
import com.rs.mobile.common.view.StateButton;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class Register2Activity extends AppCompatActivity {
    private ClearEditText etNickname, etEmail, etPwd, etCustomerNm, etAddress1, etAddress2;
    private EditText etZipCode;
    private ImageView ivNicknameCheck, ivEmailChek, ivPwdCheck;
    private StateButton btnNext;
    private ImageView btnBack;
    private TextView tvTitle;
    private StateButton btnGetAddress;

    private String mTel, mAuthNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        initView();
        initData();
        initListener();
    }

    private void initView(){
        btnBack = (ImageView) findViewById(R.id.btn_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("개인 회원가입");

        etNickname = (ClearEditText) findViewById(R.id.et_nickname);
        etEmail = (ClearEditText) findViewById(R.id.et_email);
        etPwd = (ClearEditText) findViewById(R.id.et_pwd);
        etCustomerNm = (ClearEditText) findViewById(R.id.et_customer_nm);
        etZipCode = (EditText) findViewById(R.id.et_zip_cd);
        etAddress1 = (ClearEditText) findViewById(R.id.et_address1);
        etAddress2 = (ClearEditText) findViewById(R.id.et_address2);
        ivNicknameCheck = (ImageView) findViewById(R.id.iv_nickname_check);
        ivEmailChek = (ImageView) findViewById(R.id.iv_email_check);
        ivPwdCheck = (ImageView) findViewById(R.id.iv_pwd_check);
        btnNext = (StateButton) findViewById(R.id.btn_next);
        btnGetAddress = (StateButton) findViewById(R.id.btn_get_address);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnGetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register2Activity.this, kfme_post_search.class);
                startActivityForResult(intent, 100);
            }
        });
        btnNext.setClickable(true);
        btnNext.setEnabled(true);
    }

    private void initData(){
        mTel = getIntent().getStringExtra("TEL");
        mAuthNum = getIntent().getStringExtra("AuthNum");
    }

    private void initListener(){
        etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if(etNickname.getText().toString().length() >= 1 && etNickname.getText().toString().length() <= 10){
//                    ivNicknameCheck.setVisibility(View.VISIBLE);
//                }else{
//                    ivNicknameCheck.setVisibility(View.GONE);
//                }
//                setClickable();
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if (etEmail.getText().toString().matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$") && editable.length() > 0){
//                    ivEmailChek.setVisibility(View.VISIBLE);
//                }else{
//                    ivEmailChek.setVisibility(View.GONE);
//                }
//                setClickable();
            }
        });

        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if(etPwd.getText().toString().length() >= 8 && etPwd.getText().toString().length() <= 12){
//                    ivPwdCheck.setVisibility(View.VISIBLE);
//                }else{
//                    ivPwdCheck.setVisibility(View.GONE);
//                }
//                setClickable();
            }
        });
    }

    private void setClickable(){
        if(ivNicknameCheck.isShown() && ivEmailChek.isShown() && ivPwdCheck.isShown()){
            btnNext.setClickable(true);
            btnNext.setEnabled(true);
        }else{
            btnNext.setClickable(false);
            btnNext.setEnabled(false);
        }
    }

    private void register(){
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("memid", mTel.replace("-", ""));
        params.put("AuthNum", mAuthNum);
        params.put("mempwd", EncryptUtils.SHA512(etPwd.getText().toString()));
        params.put("business_type", "1");
        params.put("nickname", etNickname.getText().toString());
        params.put("email", etEmail.getText().toString());
        params.put("custom_name", etCustomerNm.getText().toString());
        params.put("top_zip_code", etZipCode.getText().toString());
        params.put("top_addr_head", etAddress1.getText().toString());
        params.put("top_addr_detail", etAddress2.getText().toString());

        OkHttpHelper okHttpHelper = new OkHttpHelper(Register2Activity.this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                MemberShipEntity entity = GsonUtils.changeGsonToBean(responseDescription, MemberShipEntity.class);
                if("1".equals(entity.status)){
                    Toast.makeText(Register2Activity.this, entity.msg, Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(Register2Activity.this, entity.msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, "http://member.gigawon.cn:8808/api/Login/joinMember", GsonUtils.createGsonString(params));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK){
            String postdata = data.getExtras().getString("postaddr");
            String[] results = postdata.split("\\|");
            etZipCode.setText(results[0]);
            etAddress1.setText(results[1]);
        }
    }
}
