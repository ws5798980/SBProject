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

public class Activity_rs_Student_Create extends Activity {

    private ImageView iv_student_back;
    private EditText et_editStudent_ID, et_editStudent_Name, et_editStudent_Department, et_editStudent_person_name, et_editStudent_hp_no, et_editStudent_IDNumber, et_editStudent_Address;
    private LinearLayout apply_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rs__student__create);

        //Initialization Input Text
        et_editStudent_ID = (EditText)findViewById(R.id.editStudent_ID);
        et_editStudent_ID.setText(S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID,"").substring(0,11));

        iv_student_back = (ImageView)findViewById(R.id.student_back);
        iv_student_back.setOnClickListener(new View.OnClickListener() {
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
        apply_btn = (LinearLayout) findViewById(R.id.apply_btn_student);
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json;Charset=UTF-8");
                HashMap<String, String> params = new HashMap<String, String>();

                //editStudent_ID, editStudentName, editStudent_Department, editStudent_name, editStudent_hp_no, editStudent_IDNumber, editStudent_Address
                et_editStudent_ID = (EditText)findViewById(R.id.editStudent_ID);
                et_editStudent_Name = (EditText)findViewById(R.id.editStudentName);
                et_editStudent_Department = (EditText)findViewById(R.id.editStudent_Department);
                et_editStudent_person_name = (EditText)findViewById(R.id.editStudent_person_name);
                et_editStudent_hp_no = (EditText)findViewById(R.id.editStudent_hp_no);
                et_editStudent_IDNumber = (EditText)findViewById(R.id.editStudent_IDNumber);
                et_editStudent_Address = (EditText)findViewById(R.id.editStudent_Address);

                if((et_editStudent_ID.getText().length() == 0) || (et_editStudent_Name.getText().length() == 0)
                        || (et_editStudent_Department.getText().length() == 0) || (et_editStudent_person_name.getText().length() == 0)
                        || (et_editStudent_hp_no.getText().length() == 0) || (et_editStudent_IDNumber.getText().length() == 0))
                {
                    Toast.makeText(getApplicationContext(), "No Edit Value Null", Toast.LENGTH_LONG).show();
                }

                JSONObject j1=new JSONObject();
                try {
                    j1.put(C.RS_KEY_STUDENT_LANGTYPE, "chn");
                    j1.put(C.RS_KEY_STUDENT_CUSTOM_CODE, S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID,""));
                    j1.put(C.RS_KEY_STUDENT_CUSTOM_ID, et_editStudent_ID.getText().toString().substring(0,11));
                    j1.put(C.RS_KEY_STUDENT_SELLER_TYPE,"122");
                    j1.put(C.RS_KEY_STUDENT_SCHOOL_NAME, et_editStudent_Name.getText().toString().trim());
                    j1.put(C.RS_KEY_STUDENT_DEPARTMENT, et_editStudent_Department.getText().toString().trim());
                    j1.put(C.RS_KEY_STUDENT_NAME, et_editStudent_person_name.getText().toString().trim());
                    j1.put(C.RS_KEY_STUDENT_HP_NO, et_editStudent_hp_no.getText().toString().trim());
                    j1.put(C.RS_KEY_STUDENT_IDNUMBER, et_editStudent_IDNumber.getText().toString().trim());
                    j1.put(C.RS_KEY_STUDENT_ADDRESS, et_editStudent_Address.getText().toString().trim());
                    params.put("", j1.toString());

                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                OkHttpHelper helper = new OkHttpHelper(
                        Activity_rs_Student_Create.this);
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
                        Log.d("rsapp", data.toString());
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
                }, C.BASE_URL_APISELLER + C.RS_CREATE_APISELLER_STUDENT, headers, j1.toString());
            }
        });
    }
}
