package com.rs.mobile.wportal.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.view.StateButton;
import com.rs.mobile.wportal.A;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.SmMainActivity;
import com.rs.mobile.wportal.activity.xsgr.kfme_WebBrowser;

public class ClauseActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btnAllCheck, btnCheck1, btnCheck2, btnCheck3;
    private StateButton btnNext;
    private boolean mAllCheck, mCheck1, mCheck2, mCheck3;
    private TextView tv_btn_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clause);

        initView();
    }

    private void initView(){
        btnAllCheck = (ImageButton) findViewById(R.id.btn_all_check);
        btnCheck1 = (ImageButton) findViewById(R.id.btn_check1);
        btnCheck2 = (ImageButton) findViewById(R.id.btn_check2);
        btnCheck3 = (ImageButton) findViewById(R.id.btn_check3);
        btnNext = (StateButton) findViewById(R.id.btn_next);
        tv_btn_location = (TextView) findViewById(R.id.btn_location);

        btnAllCheck.setOnClickListener(this);
        btnCheck1.setOnClickListener(this);
        btnCheck2.setOnClickListener(this);
        btnCheck3.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tv_btn_location.setOnClickListener(this);

        allActivation();
        mAllCheck = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_all_check:
                if(mAllCheck){
                    mAllCheck = false;
                    mCheck1 = false;
                    mCheck2 = false;
                    mCheck3 = false;
                    btnAllCheck.setBackgroundResource(R.drawable.icon_select);
                    btnCheck1.setBackgroundResource(R.drawable.icon_select);
                    btnCheck2.setBackgroundResource(R.drawable.icon_select);
                    btnCheck3.setBackgroundResource(R.drawable.icon_select);
                }else{
                    mAllCheck = true;
                    mCheck1 = true;
                    mCheck2 = true;
                    mCheck3 = true;
                    btnAllCheck.setBackgroundResource(R.drawable.icon_selected);
                    btnCheck1.setBackgroundResource(R.drawable.icon_selected);
                    btnCheck2.setBackgroundResource(R.drawable.icon_selected);
                    btnCheck3.setBackgroundResource(R.drawable.icon_selected);
                }
                allActivation();
                break;
            case R.id.btn_check1:
                if(mCheck1){
                    mCheck1 = false;
                    btnCheck1.setBackgroundResource(R.drawable.icon_select);
                }else{
                    mCheck1 = true;
                    btnCheck1.setBackgroundResource(R.drawable.icon_selected);
                }
                allActivation();
                break;
            case R.id.btn_check2:
                if(mCheck2){
                    mCheck2 = false;
                    btnCheck2.setBackgroundResource(R.drawable.icon_select);
                }else{
                    mCheck2 = true;
                    btnCheck2.setBackgroundResource(R.drawable.icon_selected);
                }
                allActivation();
                break;
            case R.id.btn_check3:
                if(mCheck3){
                    mCheck3 = false;
                    btnCheck3.setBackgroundResource(R.drawable.icon_select);
                }else{
                    mCheck3 = true;
                    btnCheck3.setBackgroundResource(R.drawable.icon_selected);
                }
                allActivation();
                break;
            case R.id.btn_next:
                Intent intent = new Intent(ClauseActivity.this, SmMainActivity.class);
                startActivity(intent);
                A.sharedConfig.writeData("FirstFlag", false);
                finish();
                break;
            case R.id.btn_location:
                Intent intent_web = new Intent(ClauseActivity.this, kfme_WebBrowser.class);
                intent_web.putExtra("url", "http://www."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":1314/CS2/CS10");
                startActivity(intent_web);

                break;
        }
    }

    private void allActivation(){
        /*if(mCheck1 && mCheck2 && mCheck3){
            mAllCheck = true;
            btnAllCheck.setBackgroundResource(R.drawable.icon_selected);

        }else{
            mAllCheck = false;
            btnAllCheck.setBackgroundResource(R.drawable.icon_select);
        }
        if(mCheck1 && mCheck2){
            btnNext.setVisibility(View.VISIBLE);
        }else{
            btnNext.setVisibility(View.GONE);
        }*/
        mAllCheck = true;
        btnNext.setVisibility(View.VISIBLE);
    }
}
