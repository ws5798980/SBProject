package com.rs.mobile.wportal.activity.xsgr;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.NaverMap.CustomDialogListener;

public class custom_dialog extends Dialog implements View.OnClickListener {

    private ImageView iv_res_img_compass, iv_res_img_location;

    private static CustomDialogListener customDL;



    public custom_dialog(Context context) {
        super(context);
        init();
    }

    public custom_dialog(Context context, int theme)
    {
        super(context, theme);
        init();
    }

    private void init()
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_dialog);
        iv_res_img_compass = (ImageView)findViewById(R.id.res_img_compass);
        iv_res_img_compass.setOnClickListener(this);
        iv_res_img_location = (ImageView)findViewById(R.id.res_img_location);
        iv_res_img_location.setOnClickListener(this);

        //customDL = null;
    }

    public void setCustomDL(CustomDialogListener customDLL) {
        this.customDL = customDLL;
    }

    @Override
    public void onClick(View v) {
        try {
            this.customDL.onDialogBtnClickListener(v);
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
    }
}
