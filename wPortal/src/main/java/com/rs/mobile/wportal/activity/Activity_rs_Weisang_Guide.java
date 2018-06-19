package com.rs.mobile.wportal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rs.mobile.common.L;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.R;

public class Activity_rs_Weisang_Guide extends AppCompatActivity {

    private ImageView iv_weisang_back;
    private LinearLayout ll_weisang_apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rs__weisang__guide);

        iv_weisang_back = (ImageView)findViewById(R.id.weisang_back);
        iv_weisang_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll_weisang_apply = (LinearLayout)findViewById(R.id.weisang_apply);
        ll_weisang_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(getApplicationContext(), Activity_rs_Weisang_Create.class);
                    //i.putExtra("code", 7);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    UiUtil.startActivity(getApplicationContext(), i, UiUtil.TYPE_BUTTON, ll_weisang_apply);
                } catch (Exception e) {

                    L.e(e);

                }
            }
        });
    }
}
