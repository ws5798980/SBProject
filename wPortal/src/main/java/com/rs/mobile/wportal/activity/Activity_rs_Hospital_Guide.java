package com.rs.mobile.wportal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rs.mobile.common.L;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.R;

public class Activity_rs_Hospital_Guide extends Activity {

    private LinearLayout apply_btn,hospital_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rs__hospital__guide);

        hospital_back = (LinearLayout)findViewById(R.id.hospital_back);
        hospital_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        apply_btn = (LinearLayout) findViewById(R.id.apply);
        apply_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {

                    Intent i = new Intent(getApplicationContext(), activity_rs_Hospital_Create.class);
                    //i.putExtra("code", 7);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    UiUtil.startActivity(getApplicationContext(), i, UiUtil.TYPE_BUTTON, apply_btn);

                } catch (Exception e) {

                    L.e(e);

                }
            }
        });
    }
}
