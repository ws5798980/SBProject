package com.rs.mobile.wportal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.util.NetUtils;
import com.rs.mobile.wportal.A;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.SmMainActivity;
import com.rs.mobile.wportal.activity.xsgr.kfmemain;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConfig.CHOOSE.equals("CN")){
            setTheme(R.style.AppIntro);
        }else {
            setTheme(R.style.AppIntro_KO);
        }


        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView(){
        final boolean firstFlag = A.sharedConfig.readBoolean("FirstFlag", true);
//        if(!NetUtils.isNetworkAvalible(SplashActivity.this)){
//            TextView msg = new TextView(this);
//            msg.setText("当前没有可以使用的网络，请检查网络！");
//            new AlertDialog.Builder(this).setTitle("网络状态提示").setView(msg).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                public void onClick(DialogInterface dialog, int whichButton) {
//                    if(firstFlag){
//                        Intent intent = new Intent(SplashActivity.this, ClauseActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        //Intent intent = new Intent(SplashActivity.this, SmMainActivity.class);
//                        Intent intent = new Intent(SplashActivity.this, SmMainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            }).create().show();
//        }else{

        new Handler().postDelayed(new Runnable(){
            public void run() {

                if(firstFlag){
                    Intent intent = new Intent(SplashActivity.this, ClauseActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //Intent intent = new Intent(SplashActivity.this, SmMainActivity.class);
                    Intent intent = new Intent(SplashActivity.this, SmMainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);
//        }
    }
}
