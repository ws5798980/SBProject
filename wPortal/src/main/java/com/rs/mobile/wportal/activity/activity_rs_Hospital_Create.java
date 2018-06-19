package com.rs.mobile.wportal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.LoginActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.wportal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class activity_rs_Hospital_Create extends Activity {

    private LinearLayout ll_backbtn, apply_btn;
    private EditText et_editID, et_editHospitalName, et_editHospital_PhoneNum, et_editName, et_editPhoneNum, et_editHospital_Address, et_editHospital_auth_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rs__hospital__create);

        //Initialization Input Text
        et_editID = (EditText)findViewById(R.id.editID);
        et_editID.setText(S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID,"").substring(0,11));

        if(et_editID.getText().length() == 0)
        {
            PageUtil.jumpTo(getApplicationContext(), LoginActivity.class, null);
            finish();
            return;
        }

        ll_backbtn = (LinearLayout)findViewById(R.id.hospital_back);
        ll_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {

                }
            }
        });

        //Apply Data Server
        Create_Apply_Data_Send();
    }

    private void Create_Apply_Data_Send()
    {
        apply_btn = (LinearLayout) findViewById(R.id.apply_btn);
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json;Charset=UTF-8");
                HashMap<String, String> params = new HashMap<String, String>();

                //et_editHospitalName, et_editHospital_PhoneNum, et_editName, et_editPhoneNum, et_editHospital_Address, et_editHospital_auth_num
                et_editID = (EditText)findViewById(R.id.editID);
                et_editHospitalName = (EditText)findViewById(R.id.editHospitalName);
                et_editHospital_PhoneNum = (EditText)findViewById(R.id.editHospital_PhoneNum);
                et_editName = (EditText)findViewById(R.id.editName);
                et_editPhoneNum = (EditText)findViewById(R.id.editPhoneNum);
                et_editHospital_Address = (EditText)findViewById(R.id.editHospital_Address);
                et_editHospital_auth_num = (EditText)findViewById(R.id.editHospital_auth_num);

                if((et_editHospitalName.getText().length() == 0) || (et_editHospital_PhoneNum.getText().length() == 0)
                        || (et_editName.getText().length() == 0) || (et_editPhoneNum.getText().length() == 0)
                        || (et_editPhoneNum.getText().length() == 0) || (et_editHospital_Address.getText().length() == 0)
                        || (et_editHospital_auth_num.getText().length() == 0) || (et_editID.getText().length() == 0))
                {
                    Toast.makeText(getApplicationContext(), "No Edit Value Null", Toast.LENGTH_LONG).show();
                }

                JSONObject j1=new JSONObject();
                try {
                    j1.put(C.RS_KEY_HOSPITAL_LANGTYPE, "chn");
                    j1.put(C.RS_KEY_HOSPITAL_CUSTOM_CODE, S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID,""));
                    j1.put(C.RS_KEY_HOSPITAL_CUSTOM_ID, et_editID.getText().toString().substring(0,11));
                    j1.put(C.RS_KEY_HOSPITAL_SELLER_TYPE,"121");
                    j1.put(C.RS_KEY_HOSPITAL_NAME, et_editHospitalName.getText().toString().trim());
                    j1.put(C.RS_KEY_HOSPITAL_PERSON_NAME, et_editName.getText().toString().trim());
                    j1.put(C.RS_KEY_HOSPITAL_TEL_NO, et_editHospital_PhoneNum.getText().toString().trim());
                    j1.put(C.RS_KEY_HOSPITAL_HP_NO, et_editPhoneNum.getText().toString().trim());
                    j1.put(C.RS_KEY_HOSPITAL_IDNUMBER, et_editHospital_auth_num.getText().toString().trim());
                    j1.put(C.RS_KEY_HOSPITAL_ADDRESS, et_editHospital_Address.getText().toString().trim());
                    params.put("", j1.toString());

                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                OkHttpHelper helper = new OkHttpHelper(
                        activity_rs_Hospital_Create.this);
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
                }, C.BASE_URL_APISELLER + C.RS_CREATE_APISELLER_DOCTOR, headers, j1.toString());
            }
        });
    }
}
