package com.rs.mobile.wportal.activity.ht;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import okhttp3.Request;

public class HtConcelOrderActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private TextView text_amout;
	private TextView text_money;
	private RadioGroup rg;
	private EditText text_send_reason;
	private TextView text_cancel;
	private String orderId;
	private String order_amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_cancelorder);
		initToolbar();
		initView();
		initData();

	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
			tv_title.setText(getString(com.rs.mobile.wportal.R.string.common_text014));
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initData() {

		orderId = getIntent().getStringExtra("orderId");
		getCancelMoney();
		order_amount = getIntent().getStringExtra("order_amount");

	}

	private void initView() {
		text_amout = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_amout);
		text_money = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_money);
		rg = (RadioGroup) findViewById(com.rs.mobile.wportal.R.id.rg);

		text_send_reason = (EditText) findViewById(com.rs.mobile.wportal.R.id.text_send_reason);
		text_cancel = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_cancel);
		text_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				commitReasom();
			}
		});
	}

	private void commitReasom() {
		RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
		HashMap<String, String> paramsKeyValue = new HashMap<>();
		paramsKeyValue.put("orderId", orderId);
		paramsKeyValue.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		paramsKeyValue.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
		paramsKeyValue.put("cancelCause", rb.getText().toString());
		paramsKeyValue.put("cancelContext", text_send_reason.getText().toString());
		OkHttpHelper okHttpHelper = new OkHttpHelper(HtConcelOrderActivity.this);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					t(data.getString("msg"));
					if (data.get("status").toString().equals("1")) {
						finish();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_HT + Constant.HT_ADD_CANCEL, paramsKeyValue);
	}

	private void getCancelMoney() {
		HashMap<String, String> params = new HashMap<>();
		params.put("orderId", orderId);
		params.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		OkHttpHelper okHttpHelper = new OkHttpHelper(this);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = data.getJSONObject("data");
					text_amout.setText(jsonObject.getString("refund_amount"));
					text_money.setText(jsonObject.getString("Detailed"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_HT + Constant.HT_GetCancelMoney, params);

	}
}
