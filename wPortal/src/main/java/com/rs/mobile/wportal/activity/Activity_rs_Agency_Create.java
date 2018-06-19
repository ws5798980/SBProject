package com.rs.mobile.wportal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rs.mobile.common.L;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.R;

public class Activity_rs_Agency_Create extends Activity {

    private LinearLayout ll_hospital_btn, ll_student_btn, ll_weisang_btn, agency_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rs_agency__create);


/*        if(!Agency_CheckLogin())
        {
            finish();
            PageUtil.jumpTo(Activity_rs_Agency_Create.this, LoginActivity.class);
        }
*/

        agency_back = (LinearLayout) findViewById(R.id.agency_back);
        agency_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {

                }
            }
        });

        ll_hospital_btn = (LinearLayout)findViewById(R.id.Hospital_docter);

        ll_hospital_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {

                    Intent i = new Intent(getApplicationContext(), Activity_rs_Hospital_Guide.class);
                    //i.putExtra("code", 7);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    UiUtil.startActivity(getApplicationContext(), i, UiUtil.TYPE_BUTTON, ll_hospital_btn);

                } catch (Exception e) {

                    L.e(e);

                }
            }
        });

        ll_student_btn = (LinearLayout)findViewById(R.id.student_apply);
        ll_student_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent i = new Intent(getApplicationContext(), Activity_rs_Student_Guide.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    UiUtil.startActivity(getApplicationContext(), i, UiUtil.TYPE_BUTTON, ll_student_btn);
                } catch (Exception e) {
                    L.e(e);
                }
            }
        });

        ll_weisang_btn = (LinearLayout)findViewById(R.id.weisang_apply);
        ll_weisang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent i = new Intent(getApplicationContext(), Activity_rs_Weisang_Guide.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    UiUtil.startActivity(getApplicationContext(), i, UiUtil.TYPE_BUTTON, ll_weisang_btn);
                } catch (Exception e) {
                    L.e(e);
                }
            }
        });
    }

    private Boolean Agency_CheckLogin() {
        try {
            if (UiUtil.checkLogin(Activity_rs_Agency_Create.this)) {
                return true;
            }
        } catch (Exception e) {

            L.e(e);

        }
        return false;
    }
}
