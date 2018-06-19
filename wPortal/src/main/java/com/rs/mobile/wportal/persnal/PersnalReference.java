package com.rs.mobile.wportal.persnal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.EncodingHandler;
import com.rs.mobile.wportal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class PersnalReference extends Activity {

    private TextView btn_text;
    private ImageView btn_image, iv_qrcode_img;
    private LinearLayout ll_close_btn;

    public Bitmap qrCodeBitmap=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnal_reference);

        D.showProgressDialog(PersnalReference.this.getApplicationContext(), "", true);

        iv_qrcode_img = (ImageView)findViewById(R.id.qrcode_img);
        ll_close_btn = (LinearLayout)findViewById(R.id.close_btn);
        ll_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    try{

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;Charset=UTF-8");

        JSONObject j1=new JSONObject();
        try {

            j1.put(C.RS_KEY_HOSPITAL_LANGTYPE, "chn"); //memid
            j1.put(C.RS_KEY_HOSPITAL_CUSTOM_CODE, S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID,"")); //mempwd
            j1.put(C.KEY_REQUEST_PARENT_ID, S.getShare(getApplicationContext(), C.KEY_REQUEST_PARENT_ID,""));

        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        //나의 Custom_Code를 넣을것

        try {

            qrCodeBitmap = EncodingHandler.createQRCode(S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID,""), dip2px(this, 400));
            iv_qrcode_img.setImageBitmap(qrCodeBitmap);
            iv_qrcode_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    D.showProgressDialog(PersnalReference.this.getApplicationContext(), "", true);
                    Intent intent_ = new Intent(getApplicationContext(), Persnal_QRCODE_POPUP.class);
                    startActivity(intent_);
                }
            });
        } catch (Exception ex) {

        }

        OkHttpHelper helper = new OkHttpHelper(
                PersnalReference.this);
        helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request,
                                       IOException e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBizSuccess(
                    String responseDescription,
                    JSONObject data, String flag) {
                // TODO Auto-generated method stub
                try {
                    if (data.has("isParent")) {
                        S.setShare(getApplicationContext(), "isParent", data.getString("isParent"));
                        String aaa = data.getString("isParent");
                        //내가 추천인쪽으로 등록된 것 이 없다면.... 매칭 버튼
                        if (data.getString("isParent").compareTo("0") == 0) {
                            btn_text.setText(R.string.mk_matting_ready);
                        } else if(data.getString("isParent").compareTo("1") == 0){  // 이미 추천인쪽으로 등록된 것이 있다면...  상세보기
                            btn_text.setText(R.string.mk_reference_06);
                            if (data.getString("isParent").compareTo("1") == 0) {
                                JSONObject data_referenct = data.optJSONObject("data");
                                //seller_type, custom_code, custom_name, custom_id, mobilepho
                                S.setShare(getApplicationContext(), "seller_type", data_referenct.getString("seller_type"));
                                S.setShare(getApplicationContext(), "seller_custom_code", data_referenct.getString("custom_code"));
                                S.setShare(getApplicationContext(), "seller_custom_name", data_referenct.getString("custom_name"));
                                S.setShare(getApplicationContext(), "seller_custom_id", data_referenct.getString("custom_id"));
                                S.setShare(getApplicationContext(), "seller_mobilepho", data_referenct.getString("mobilepho"));
                            }
                        }
                    }
                } catch (JSONException e) {

                }
            }

            @Override
            public void onBizFailure(
                    String responseDescription,
                    JSONObject data, String responseCode) {
                //Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
                // TODO Auto-generated method stub

            }
        }, Constant.BASE_REFERENCE_URL+ Constant.PERSONAL_REFERENCE_CHECK_REFERENCE_URL, headers, j1.toString());
    } catch (Exception e) {

        L.e(e);

    }

        btn_text = (TextView)findViewById(R.id.btn_text);
        btn_text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /*Intent i = new Intent(PersnalReference.this,
                        PersnalReference_Serche.class);
                startActivityForResult(i, 1000);*/



                PassReferenceActivity();

            }
        });

        btn_image = (ImageView)findViewById(R.id.btn_img);
        btn_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /*Intent i = new Intent(PersnalReference.this,
                        PersnalReference_Serche.class);
                startActivityForResult(i, 1000);*/
                PassReferenceActivity();
            }
        });
    }

    private void PassReferenceActivity() {
        if(S.getShare(getApplicationContext(), "isParent", "").compareTo("0") == 0)
        {
            //Intent intent = new Intent();

            // TODO
            // 这里由于涉及到intent传递的数据不能太大的问题，所以如果需要，这里需要进行另外的处理，写入到内存或者写入到文件中
            /*intent.putExtra(PickBigImagesActivity.EXTRA_DATA, getAllImagesFromCurrentDirectory());
            intent.putExtra(PickBigImagesActivity.EXTRA_ALL_PICK_DATA, picklist);
            intent.putExtra(PickBigImagesActivity.EXTRA_CURRENT_PIC, position);
            intent.putExtra(PickBigImagesActivity.EXTRA_LAST_PIC, picNums - currentPicNums);
            intent.putExtra(PickBigImagesActivity.EXTRA_TOTAL_PIC, picNums);*/
            //startActivityForResult(intent, CODE_FOR_PIC_BIG);
            startActivityForResult(new Intent(PersnalReference.this,PersnalReference_Serche.class),10001);
        } else if(S.getShare(getApplicationContext(), "isParent", "").compareTo("1") == 0) {

            Intent intent_ = new Intent(PersnalReference.this.getBaseContext(), PersnalReference_Detail2.class);

            //intent_.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);   // 이거 안해주면 안됨
            intent_.putExtra("refer_custom_code", S.getShare(getApplicationContext(), "refer_custom_code",""));
            intent_.putExtra("seller_type", S.getShare(getApplicationContext(), "seller_type",""));
            intent_.putExtra("seller_custom_code", S.getShare(getApplicationContext(), "seller_custom_code",""));
            intent_.putExtra("seller_custom_name", S.getShare(getApplicationContext(), "seller_custom_name",""));
            intent_.putExtra("seller_custom_id", S.getShare(getApplicationContext(), "seller_custom_id",""));
            intent_.putExtra("seller_mobilepho", S.getShare(getApplicationContext(), "seller_mobilepho",""));
            startActivity(intent_);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(resultCode)
        {
            case 10001: {
                //tv_matting_agree.setVisibility(View.VISIBLE);
                finish();
                break;
            }
        }

    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 1.5f);
    }
}
