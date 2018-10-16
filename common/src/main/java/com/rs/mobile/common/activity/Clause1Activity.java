package com.rs.mobile.common.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rs.mobile.common.R;;

public class Clause1Activity extends AppCompatActivity {
    private TextView tvNext;
    private ImageButton btnClose,btnAllCheck,btnCheck1,btnCheck2,btnCheck3,btnNext;
    private TextView text1, text2, text3;
    private boolean mAllCheck,mCheck1,mCheck2,mCheck3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clause1);

        initView();
    }

    private void initView(){
        tvNext = (TextView) findViewById(R.id.tv_next);
        btnClose = (ImageButton) findViewById(R.id.btn_close);
        btnAllCheck = (ImageButton) findViewById(R.id.btn_all_check);
        btnCheck1 = (ImageButton) findViewById(R.id.btn_check1);
        btnCheck2 = (ImageButton) findViewById(R.id.btn_check2);
        btnCheck3 = (ImageButton) findViewById(R.id.btn_check3);
        btnNext = (ImageButton) findViewById(R.id.btn_next);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Clause1Activity.this, kfme_WebBrowser.class);
                intent.putExtra("url", "http://www.gigawon.co.kr:1314/CS/CS10");
                startActivity(intent);
            }
        });
        text2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Clause1Activity.this, kfme_WebBrowser.class);
                intent.putExtra("url", "http://www.gigawon.co.kr:1314/CS/CS20");
                startActivity(intent);
            }
        });
        text3.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Clause1Activity.this, kfme_WebBrowser.class);
                intent.putExtra("url", "http://www.gigawon.co.kr:1314/CS/CS30");
                startActivity(intent);
            }
        });

        final int regFlag = getIntent().getIntExtra("RegFlag", 0);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAllCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAllCheck){
                    mAllCheck = false;
                    mCheck1 = false;
                    mCheck2 = false;
                    mCheck3 = false;
                    btnAllCheck.setBackgroundResource(R.drawable.btn_all_ok_default);
                    btnCheck1.setBackgroundResource(R.drawable.icon_check_default);
                    btnCheck2.setBackgroundResource(R.drawable.icon_check_default);
                    btnCheck3.setBackgroundResource(R.drawable.icon_check_default);
                }else{
                    mAllCheck = true;
                    mCheck1 = true;
                    mCheck2 = true;
                    mCheck3 = true;
                    btnAllCheck.setBackgroundResource(R.drawable.btn_all_ok_activation);
                    btnCheck1.setBackgroundResource(R.drawable.icon_check_activation);
                    btnCheck2.setBackgroundResource(R.drawable.icon_check_activation);
                    btnCheck3.setBackgroundResource(R.drawable.icon_check_activation);
                }
                allActivation();
            }
        });
        btnCheck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheck2){
                    mCheck2 = false;
                    btnCheck2.setBackgroundResource(R.drawable.icon_check_default);
                }else{
                    mCheck2 = true;
                    btnCheck2.setBackgroundResource(R.drawable.icon_check_activation);
                }
                allActivation();
            }
        });
        btnCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheck2){
                    mCheck2 = false;
                    btnCheck2.setBackgroundResource(R.drawable.icon_check_default);
                }else{
                    mCheck2 = true;
                    btnCheck2.setBackgroundResource(R.drawable.icon_check_activation);
                }
                allActivation();
            }
        });
        btnCheck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheck3){
                    mCheck3 = false;
                    btnCheck3.setBackgroundResource(R.drawable.icon_check_default);
                }else{
                    mCheck3 = true;
                    btnCheck3.setBackgroundResource(R.drawable.icon_check_activation);
                }
                allActivation();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regFlag == 53){
                    Intent intent = new Intent(Clause1Activity.this, SelectGroupActivity.class);
                    intent.putExtra("RegFlag", regFlag);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Clause1Activity.this, Register1Activity.class);
                    intent.putExtra("RegFlag", regFlag);
                    startActivity(intent);
                }
                finish();
            }
        });
        btnNext.setClickable(false);
    }

    private void allActivation(){
        if(mCheck1 && mCheck2 && mCheck3){
            mAllCheck = true;
            btnAllCheck.setBackgroundResource(R.drawable.btn_all_ok_activation);
            tvNext.setTextColor(getResources().getColor(R.color.white));
            btnNext.setBackgroundResource(R.drawable.btn_next_activation);
            btnNext.setClickable(true);
        }else{
            mAllCheck = false;
            btnAllCheck.setBackgroundResource(R.drawable.btn_all_ok_default);
            tvNext.setTextColor(getResources().getColor(R.color.greywhite));
            btnNext.setBackgroundResource(R.drawable.btn_next_default);
            btnNext.setClickable(false);
        }
    }
}
