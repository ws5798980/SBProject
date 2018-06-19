package com.rs.mobile.wportal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.wportal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class Activity_rs_Weisang_Create extends Activity {

    private ImageView iv_weisang_back;
    private EditText et_editWeisang_ID, et_editWeisang_Name, et_editWeisang_PhoneNumber;
    private LinearLayout ll_apply_btn_weisang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rs__weisang__create);

        //Initialization Input Text
        et_editWeisang_ID = (EditText)findViewById(R.id.editWeiSang_ID);
        et_editWeisang_ID.setText(S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID,""));

        iv_weisang_back = (ImageView)findViewById(R.id.weisang_back);
        iv_weisang_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Apply Data Server
        Create_Apply_Data_Send();
    }

    private void Create_Apply_Data_Send()
    {
        ll_apply_btn_weisang = (LinearLayout) findViewById(R.id.apply_btn_weisang);
        ll_apply_btn_weisang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json;Charset=UTF-8");
                HashMap<String, String> params = new HashMap<String, String>();

                /*
                    editWeiSang_ID
                    editWeisang_Name
                    editWeiSang_PhoneNumber
                 */
                et_editWeisang_ID = (EditText)findViewById(R.id.editWeiSang_ID);
                et_editWeisang_Name = (EditText)findViewById(R.id.editWeisang_Name);
                et_editWeisang_PhoneNumber = (EditText)findViewById(R.id.editWeiSang_PhoneNumber);

                if((et_editWeisang_ID.getText().length() == 0) || (et_editWeisang_Name.getText().length() == 0)
                        || (et_editWeisang_PhoneNumber.getText().length() == 0))
                {
                    Toast.makeText(getApplicationContext(), "No Edit Value Null", Toast.LENGTH_LONG).show();
                }

                JSONObject j1=new JSONObject();
                try {
                    j1.put(C.RS_KEY_WEISANG_LANGTYPE, "chn");
                    j1.put(C.RS_KEY_WEISANG_CUSTOM_CODE, S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID,""));
                    j1.put(C.RS_KEY_WEISANG_CUSTOM_ID, et_editWeisang_ID.getText().toString().substring(0,11));
                    j1.put(C.RS_KEY_WEISANG_SELLER_TYPE,"123");
                    j1.put(C.RS_KEY_WEISANG_NAME, et_editWeisang_Name.getText().toString().trim());
                    j1.put(C.RS_KEY_WEISANG_HP_NO, et_editWeisang_PhoneNumber.getText().toString().trim());
                    params.put("", j1.toString());

                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                OkHttpHelper helper = new OkHttpHelper(
                        Activity_rs_Weisang_Create.this);
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
                        try {
                            if (data.getInt("status") == 1)
                                Util.Debug_Toast_Message(getApplicationContext(), getApplicationContext().getString(R.string.rs_Command_Member_OK));
                            Log.d("rsapp", data.toString());
                            PageUtil.jumpTo(getApplicationContext(), MainActivity.class, null);
                        } catch (JSONException ex) {
                            Log.d("rsapp", data.toString());
                            Util.Debug_Toast_Message(getApplicationContext(), getApplicationContext().getString(R.string.rs_Command_Member_NO));
                        }
                    }
                    @Override
                    public void onBizFailure(
                            String responseDescription,
                            JSONObject data, String responseCode) {
                        Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
                        // TODO Auto-generated method stub

                    }
                }, C.BASE_URL_APISELLER + C.RS_CREATE_APISELLER_WEISANG, headers, j1.toString());
            }
        });
    }
}
