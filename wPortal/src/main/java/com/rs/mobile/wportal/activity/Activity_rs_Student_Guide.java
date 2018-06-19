package com.rs.mobile.wportal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.rs.mobile.common.L;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.R;

public class Activity_rs_Student_Guide extends AppCompatActivity {

    private LinearLayout ll_student_apply, ll_student_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rs__student__guide);

        ll_student_back = (LinearLayout)findViewById(R.id.student_back);
        ll_student_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll_student_apply = (LinearLayout)findViewById(R.id.astudent_apply);
        ll_student_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent i = new Intent(getApplicationContext(), Activity_rs_Student_Create.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    UiUtil.startActivity(getApplicationContext(), i, UiUtil.TYPE_BUTTON, ll_student_apply);
                } catch (Exception e) {
                    L.e(e);
                }
            }
        });

    }
}
