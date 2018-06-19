package com.rs.mobile.wportal.persnal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.umeng.socialize.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class PersnalReference_Serche extends BaseActivity {

    private TextView tv_matching_btn, tv_content_title, tv_enterprise_agree, tv_matting_agree, matting_agree;
    private EditText et_edit_reference;
    private LinearLayout ll_close_btn;

    private String data_sellertype = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnal_reference__serche);

        ll_close_btn = (LinearLayout)findViewById(R.id.close_btn);
        ll_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_edit_reference = (EditText)findViewById(R.id.edit_reference);
        tv_content_title = (TextView)findViewById(R.id.content_title);
        tv_matching_btn = (TextView)findViewById(R.id.matching_btn);
        tv_enterprise_agree = (TextView)findViewById(R.id.enterprise_agree);
        tv_matting_agree = (TextView)findViewById(R.id.matting_agree);
        tv_matting_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OKHttp
                try {

                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json;Charset=UTF-8");

                    JSONObject j1 = new JSONObject();
                    try {

                        /*
                        String lang_type					선택 언어
                        String parent_id					추천인 CODE		BCM100T.custom_code
                        String custom_code					회원 CODE		bcm100t.custom_code
                        String seller_typ					추천인의 등급		RS_Selleer.seller_type     121:의사, 122=학생  123=웨이상
                         */
                        j1.put(C.RS_KEY_HOSPITAL_LANGTYPE, "chn"); //memid
                        j1.put(C.RS_KEY_HOSPITAL_CUSTOM_CODE, S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, "")); //mempwd
                        j1.put("parent_id", et_edit_reference.getText());
                        j1.put("seller_type",data_sellertype);
                    } catch (JSONException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    OkHttpHelper helper = new OkHttpHelper(
                            PersnalReference_Serche.this);
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
                            /*String status			처리결과     1=success   2=fail
                            String flag			처리결과 코드     1501=success   1101=nodata   1203=error
                            String msg			처리결과 메시지*/
                            try {
                                if(data.getString("status").compareTo("1") == 0)
                                {
                                    S.setShare(getApplicationContext(), C.KEY_REQUEST_PARENT_ID, et_edit_reference.getText().toString());
                                }
                                t(data.getString("msg"));


                                Message msg;
                                msg = mHandler.obtainMessage(2);
                                mHandler.sendMessage(msg);

                            } catch (JSONException ex) {
                                Log.d(ex.toString());
                            }
                        }

                        @Override
                        public void onBizFailure(
                                String responseDescription,
                                JSONObject data, String responseCode) {
                            Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
                            // TODO Auto-generated method stub

                        }
                    }, Constant.BASE_REFERENCE_URL + Constant.PERSONAL_REFERENCE_MATTING_INPUT, headers, j1.toString());
                } catch (Exception e) {

                    L.e(e);

                }
            }
        });



        //내가 이미 사업자로 가입이 되어 있다면
        if((S.getShare(getApplicationContext(),C.KEY_REQUEST_CUSTOME_LEVEL1,"").compareTo("121") == 0) ||
                (S.getShare(getApplicationContext(),C.KEY_REQUEST_CUSTOME_LEVEL1,"").compareTo("122") == 0) ||
                (S.getShare(getApplicationContext(), C.KEY_REQUEST_CUSTOME_LEVEL1, "").compareTo("123") == 0))
        {
            //이미 전 단계에서 체크해서 이쪽으로 들어올 일이 없음
            /*tv_matching_btn.setBackgroundColor(Color.GRAY);
            tv_content_title.setText(R.string.mk_enterprise_account_go);
            tv_content_title.setTextColor(Color.RED);
            tv_matting_agree.setVisibility(View.INVISIBLE);
            tv_enterprise_agree.setVisibility(View.VISIBLE);
            tv_enterprise_agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //사업자 등록 화면으로 이동
                    Intent intent_ = new Intent(getApplicationContext(), Activity_rs_Agency_Create.class);
                    startActivity(intent_);
                }
            });
            */

        } else {  //아직 가입이 되어 있지 않다면  번호 : 70

            tv_matching_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (et_edit_reference.getText().toString() == "") {
                        t(getString(R.string.mk_reference_content));
                        return;
                    }

                    //OKHttp
                    try {

                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json;Charset=UTF-8");

                        JSONObject j1 = new JSONObject();
                        try {

                            j1.put(C.RS_KEY_HOSPITAL_LANGTYPE, "chn"); //memid
                            j1.put(C.RS_KEY_HOSPITAL_CUSTOM_CODE, S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, "")); //mempwd
                            j1.put("parent_id", et_edit_reference.getText());
                        } catch (JSONException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        OkHttpHelper helper = new OkHttpHelper(
                                PersnalReference_Serche.this);
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
                                    if (data.getString("isParent").compareTo("1") == 0) {
                                        JSONObject data_referenct = data.optJSONObject("data");
                                        //seller_type, custom_code, custom_name, custom_id, mobilepho
                                        /*S.setShare(getApplicationContext(), "seller_type", data_referenct.getString("seller_type"));
                                        S.setShare(getApplicationContext(), "custom_code", data_referenct.getString("custom_code"));
                                        S.setShare(getApplicationContext(), "custom_name", data_referenct.getString("custom_name"));
                                        S.setShare(getApplicationContext(), "custom_id", data_referenct.getString("custom_id"));
                                        S.setShare(getApplicationContext(), "mobilepho", data_referenct.getString("mobilepho"));*/

                                        Bundle bundle = new Bundle();
                                        bundle.putString("refer_custom_code", et_edit_reference.getText().toString());
                                        bundle.putString("seller_type", data_referenct.getString("seller_type"));
                                        bundle.putString("seller_custom_code", data_referenct.getString("custom_code"));
                                        bundle.putString("seller_custom_name", data_referenct.getString("custom_name"));
                                        bundle.putString("seller_custom_id", data_referenct.getString("custom_id"));
                                        bundle.putString("seller_mobilepho", data_referenct.getString("mobilepho"));
                                        data_sellertype = data_referenct.getString("seller_type");
                                        Message msg;
                                        msg = mHandler.obtainMessage(1);
                                        msg.setData(bundle);
                                        mHandler.sendMessage(msg);


                                    } else {
                                        t("找不到推荐人。");
                                    }
                                } catch (JSONException ex) {
                                    Log.d(ex.toString());
                                }
                            }

                            @Override
                            public void onBizFailure(
                                    String responseDescription,
                                    JSONObject data, String responseCode) {
                                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
                                // TODO Auto-generated method stub

                            }
                        }, Constant.BASE_REFERENCE_URL + Constant.PERSONAL_REFERENCE_CHECK_REFERENCE_URL, headers, j1.toString());
                    } catch (Exception e) {

                        L.e(e);

                    }
                } //onClick
            });
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(resultCode)
        {
            case 1000: {
                tv_matting_agree.setVisibility(View.VISIBLE);
                break;
            }
        }

    }

    public Handler mHandler = new Handler(){

         public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    Intent intent_ = new Intent(getApplicationContext(), PersnalReference_Detail.class);

                    //intent_.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);   // 이거 안해주면 안됨
                    intent_.putExtra("refer_custom_code", et_edit_reference.getText());
                    intent_.putExtra("seller_type", bundle.getString("seller_type"));
                    intent_.putExtra("seller_custom_code", bundle.getString("seller_custom_code"));
                    intent_.putExtra("seller_custom_name", bundle.getString("seller_custom_name"));
                    intent_.putExtra("custom_id", bundle.getString("seller_custom_id"));
                    intent_.putExtra("seller_mobilepho", bundle.getString("seller_mobilepho"));
                    startActivityForResult(intent_, 1000);
                    break;
                case 2:
                    setResult(10001);
                    finish();
                    break;
                case 3:
                 break;
            }
         };
    };

}
