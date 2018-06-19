package com.rs.mobile.wportal.persnal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.wportal.EncodingHandler;
import com.rs.mobile.wportal.R;

public class Persnal_QRCODE_POPUP extends Activity {

    private ImageView iv_imageView11;

    private Bitmap qrCodeBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnal__qrcode__popup);



        iv_imageView11 = (ImageView)findViewById(R.id.imageView11);

        try {
            qrCodeBitmap = EncodingHandler.createQRCode(S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""), dip2px(this, 400));
            iv_imageView11.setImageBitmap(qrCodeBitmap);
        } catch (Exception ex) {

        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 1.5f);
    }
}
