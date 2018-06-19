package com.rs.mobile.wportal.persnal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rs.mobile.wportal.R;

public class PersnalReference_Detail extends Activity {

    private TextView tv_gubun, tv_names, tv_telphone;

    private TextView tv_Agree_btn, tv_Cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnal_reference__detail);

        Intent intent = getIntent();
        String getExt_seller_type = intent.getExtras().getString("seller_type");
        String getExt_custom_code = intent.getExtras().getString("seller_custom_code");
        String getExt_custom_name = intent.getExtras().getString("seller_custom_name");
        String getExt_custom_id = intent.getExtras().getString("seller_custom_id");
        String getExt_mobilepho = intent.getExtras().getString("seller_mobilepho");

        /*intent_.putExtra("refer_custom_code", et_edit_reference.getText());
        intent_.putExtra("seller_type", data_referenct.getString("seller_type"));
        intent_.putExtra("custom_code", data_referenct.getString("custom_code"));
        intent_.putExtra("custom_name", data_referenct.getString("custom_name"));
        intent_.putExtra("custom_id", data_referenct.getString("custom_id"));
        intent_.putExtra("mobilepho", data_referenct.getString("mobilepho"));*/

        tv_gubun = (TextView)findViewById(R.id.gubun);
        tv_names = (TextView)findViewById(R.id.names);
        tv_telphone = (TextView)findViewById(R.id.telephone);

        tv_gubun.setText(getExt_seller_type);
        tv_names.setText(getExt_custom_name);
        tv_telphone.setText(getExt_mobilepho);

        tv_Agree_btn = (TextView)findViewById(R.id.Agree_btn);
        tv_Agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1000);
                finish();
            }
        });
        tv_Cancel_btn = (TextView)findViewById(R.id.Cancel_btn);
        tv_Cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
