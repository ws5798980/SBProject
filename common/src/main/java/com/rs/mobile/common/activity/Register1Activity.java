package com.rs.mobile.common.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.mobile.common.C;
import com.rs.mobile.common.R;
import com.rs.mobile.common.S;
import com.rs.mobile.common.entity.LoginEntity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.common.view.ClearEditText;
import com.rs.mobile.common.view.StateButton;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;

public class Register1Activity extends AppCompatActivity {
    private ClearEditText etTel, etCode;
    private ImageView ivTelCheck, ivCodeCheck;
    private StateButton btnGetCode, btnNext;
    private LinearLayout llCode;
    private ImageView btnBack;
    private TextView tvTitle;
    private TextView btnRegetCode;
    private ClearEditText et_tel;
    private TextView sb_btn_reget_code;

    private int mRegFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        initView();
        initListener();
    }

    private void initView(){
        mRegFlag = getIntent().getIntExtra("RegFlag",0);

        btnBack = (ImageView) findViewById(R.id.btn_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        if(mRegFlag == 1){
            tvTitle.setText("개인 회원가입");
        }else if(mRegFlag == 2){
            tvTitle.setText("사업자 회원가입");
        }else if(mRegFlag == 3){
            tvTitle.setText("생산자 회원가입");
        }else if(mRegFlag == 4){
            tvTitle.setText("가맹점 회원가입");
        }else if(mRegFlag == 6){
            tvTitle.setText("상품관리자 회원가입");
        }else if(mRegFlag == 51){
            tvTitle.setText("단체 개인 회원가입");
        }else if(mRegFlag == 52){
            tvTitle.setText("단체 사업자 회원가입");
        }else if(mRegFlag == 53){
            tvTitle.setText("단체 회원가입");
        }


        etTel = (ClearEditText) findViewById(R.id.et_tel);
        etCode = (ClearEditText) findViewById(R.id.et_code);
        ivTelCheck = (ImageView) findViewById(R.id.iv_tel_check);
        ivCodeCheck = (ImageView) findViewById(R.id.iv_code_check);
        btnGetCode = (StateButton) findViewById(R.id.btn_get_code);
        btnNext = (StateButton) findViewById(R.id.btn_next);
        btnRegetCode = (TextView) findViewById(R.id.btn_reget_code);
        llCode = (LinearLayout) findViewById(R.id.ll_code);
        et_tel = (ClearEditText) findViewById(R.id.et_tel);
        sb_btn_reget_code = (TextView) findViewById(R.id.btn_reget_code);

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGetCode.setVisibility(View.GONE);
                llCode.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                btnRegetCode.setVisibility(View.VISIBLE);

                OkHttpHelper okHttpHelper = new OkHttpHelper(Register1Activity.this);
                okHttpHelper.addGetRequest(new OkHttpHelper.CallbackLogic() {
                    @Override
                    public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                        //Toast.makeText(getApplicationContext(), responseDescription.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                        //Toast.makeText(getApplicationContext(), responseDescription.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNetworkError(Request request, IOException e) {
                        //Toast.makeText(getApplicationContext(), request.toString(), Toast.LENGTH_LONG).show();
                    }
                }, "http://sms.gigawon.co.kr:8082/api/SSonda/" + et_tel.getText().toString());
                //Toast.makeText(getApplicationContext(), "http://sms.gigawon.co.kr:8082/api/SSonda/" + et_tel.getText().toString(), Toast.LENGTH_LONG).show();

            }
        });

        sb_btn_reget_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpHelper okHttpHelper = new OkHttpHelper(Register1Activity.this);
                okHttpHelper.addGetRequest(new OkHttpHelper.CallbackLogic() {
                    @Override
                    public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                    }

                    @Override
                    public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                    }

                    @Override
                    public void onNetworkError(Request request, IOException e) {
                    }
                }, "http://sms.gigawon.co.kr:8082/api/SSonda/" + et_tel.getText().toString());
            }
        });



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mRegFlag == 1){
                    Intent intent = new Intent(Register1Activity.this, Register2Activity.class);
                    intent.putExtra("TEL", etTel.getText().toString());
                    intent.putExtra("AuthNum", etCode.getText().toString());
                    intent.putExtra("RegFlag", mRegFlag);
                    startActivity(intent);
                }else if(mRegFlag == 2){
                    Intent intent = new Intent(Register1Activity.this, Register3Activity.class);
                    intent.putExtra("TEL", etTel.getText().toString());
                    intent.putExtra("AuthNum", etCode.getText().toString());
                    intent.putExtra("RegFlag", mRegFlag);
                    startActivity(intent);
                }else if(mRegFlag == 3){
                    Intent intent = new Intent(Register1Activity.this, Register3Activity.class);
                    intent.putExtra("TEL", etTel.getText().toString());
                    intent.putExtra("AuthNum", etCode.getText().toString());
                    intent.putExtra("RegFlag", mRegFlag);
                    startActivity(intent);
                }else if(mRegFlag == 4){
                    Intent intent = new Intent(Register1Activity.this, Register3Activity.class);
                    intent.putExtra("TEL", etTel.getText().toString());
                    intent.putExtra("AuthNum", etCode.getText().toString());
                    intent.putExtra("RegFlag", mRegFlag);
                    startActivity(intent);
                }else if(mRegFlag == 6){
                    Intent intent = new Intent(Register1Activity.this, Register3Activity.class);
                    intent.putExtra("TEL", etTel.getText().toString());
                    intent.putExtra("AuthNum", etCode.getText().toString());
                    intent.putExtra("RegFlag", mRegFlag);
                    startActivity(intent);
                }else if(mRegFlag == 51){
                    Intent intent = new Intent(Register1Activity.this, Register4Activity.class);
                    intent.putExtra("TEL", etTel.getText().toString());
                    intent.putExtra("RegFlag", mRegFlag);
                    startActivity(intent);
                }else if(mRegFlag == 52){
                    Intent intent = new Intent(Register1Activity.this, Register3Activity.class);
                    intent.putExtra("TEL", etTel.getText().toString());
                    intent.putExtra("AuthNum", etCode.getText().toString());
                    intent.putExtra("RegFlag", mRegFlag);
                    startActivity(intent);
                }else if(mRegFlag == 53){
                    Intent intent = new Intent(Register1Activity.this, Register5Activity.class);
                    intent.putExtra("TEL", etTel.getText().toString());
                    intent.putExtra("AuthNum", etCode.getText().toString());
                    intent.putExtra("RegFlag", mRegFlag);
                    startActivity(intent);
                }

                finish();
            }
        });
        btnRegetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnGetCode.setClickable(false);
        btnGetCode.setEnabled(false);
    }

    private void initListener(){
        etTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s == null || s.length() == 0) return;
//                StringBuilder sb = new StringBuilder();
//                for (int i = 0; i < s.length(); i++) {
//                    if (i != 3 && i != 8 && s.charAt(i) == '-') {
//                        continue;
//                    } else {
//                        sb.append(s.charAt(i));
//                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != '-') {
//                            sb.insert(sb.length() - 1, '-');
//                        }
//                    }
//                }
//                if (!sb.toString().equals(s.toString())) {
//                    int index = start + 1;
//                    if (sb.charAt(start) == '-') {
//                        if (before == 0) {
//                            index++;
//                        } else {
//                            index--;
//                        }
//                    } else {
//                        if (before == 1) {
//                            index--;
//                        }
//                    }
//                    etTel.setText(sb.toString());
//                    etTel.setSelection(index);
//                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if(etTel.length() == 11){
//                    ivTelCheck.setVisibility(View.VISIBLE);
//                    btnGetCode.setClickable(true);
//                    btnGetCode.setEnabled(true);
//                }else{
//                    ivTelCheck.setVisibility(View.GONE);
//                }
                btnGetCode.setClickable(true);
                btnGetCode.setEnabled(true);
            }
        });

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if(etCode.length() == 4){
//                    ivCodeCheck.setVisibility(View.VISIBLE);
//                }else{
//                    ivCodeCheck.setVisibility(View.GONE);
//                }
            }
        });
    }


}
