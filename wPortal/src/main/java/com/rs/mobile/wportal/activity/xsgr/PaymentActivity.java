package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.LoginActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.PaymentUtil;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.common.view.StateButton;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;
import com.rs.mobile.wportal.activity.sm.SmConfirmActivity;
import com.rs.mobile.wportal.adapter.xsgr.CustomListAdapter;
import com.rs.mobile.wportal.entity.ListItem;
import com.rs.mobile.wportal.persnal.SettingActivity;
import com.rs.mobile.wportal.sm.SMUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

public class PaymentActivity extends AppCompatActivity {
    private EditText etMoney;
    private StateButton btnNext;

    private String scanCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initView();
    }

    private void initView(){
        etMoney = (EditText) findViewById(R.id.et_money);
        btnNext = (StateButton) findViewById(R.id.btn_next);

        Intent intent = getIntent();
        scanCode = intent.getExtras().getString("code");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etMoney.getText().toString().isEmpty()){
                   // Toast.makeText(PaymentActivity.this, etMoney.getText().toString(), Toast.LENGTH_SHORT).show();
                    //showKeyBoard_gigapay(final Context context, final String custom_code, final String password, final String store_custom_code, final String amount, final OnPaymentResultListener listener) {
                   // S.set(LoginActivity.this, C.KEY_JSON_CUSTOM_CODE, entity.custom_code);
                    Toast.makeText(PaymentActivity.this, S.getShare(PaymentActivity.this, C.KEY_JSON_CUSTOM_CODE, ""), Toast.LENGTH_LONG).show();
                    PaymentUtil.showKeyBoard_gigapay(PaymentActivity.this, S.getShare(PaymentActivity.this, C.KEY_JSON_CUSTOM_CODE, ""),scanCode, etMoney.getText().toString(), new OnPaymentResultListener() {

                                @Override
                                public void onResult(JSONObject data) {
                                    // stub
                                    String status;
                                    try {
                                        status = data.get("status").toString();

                                        if (status.equals("1")) {
                                            JSONObject data1 = data.getJSONObject("data");
                                            //String order_no1 = data1.get("order_no").toString();
                                            //t(getString(R.string.common_text005));

                                            //SMUtil.smConfirmStatus_giga(order_no1, PaymentActivity.this);

                                        } else {

                                            T.showToast(PaymentActivity.this, data.get("msg").toString());

                                            // method
                                            // stub
                                            if (status.equals("1101")) { // wrong
                                                // pw
                                                /*showDialogTip(data.get("msg").toString(),
                                                        getString(R.string.common_text020));*/
                                                Toast.makeText(getApplicationContext(), data.get("msg").toString(), Toast.LENGTH_LONG).show();
                                            } else if (status.equals("1711")) { // not
                                                // enough
                                                // money
                                                /*showDialogTip(data.get("msg").toString(),
                                                        getString(R.string.common_text021));*/
                                                Toast.makeText(getApplicationContext(), data.get("msg").toString(), Toast.LENGTH_LONG).show();
                                            } else { //
                                                finish();
                                                PageUtil.jumpToWithFlag(PaymentActivity.this,
                                                        MyOrderActivity.class);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        // block
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFail(String msg) {
                                    // stub

                                    PageUtil.jumpToWithFlag(PaymentActivity.this, MyOrderActivity.class);
                                    finish();

                                }
                            });
                }
            }
        });
    }
}
