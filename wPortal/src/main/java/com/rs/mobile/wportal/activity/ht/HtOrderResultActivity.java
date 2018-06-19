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
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class HtOrderResultActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	protected String roomTypeName;
	protected String arriveTime;
	protected String leaveTime;
	protected String hotelName;
	protected String phonenum;
	private String RetainTime;
	private String userName;
	private String roomCount;
	private String orderId;
	private TextView text0;
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private TextView text4;
	private TextView text5;
	private TextView text6;
	private TextView text_check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_order_result);
		orderId = getIntent().getStringExtra("orderId");
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
			tv_title.setText(getString(com.rs.mobile.wportal.R.string.common_text028));
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initData() {
		HashMap<String, String> params = new HashMap<>();
		params.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		params.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
		params.put("orderId", orderId);
		OkHttpHelper okHttpHelper = new OkHttpHelper(HtOrderResultActivity.this);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					if (data.get("status").toString().equals("1")) {
						final JSONObject obj = new JSONObject(data.get("data").toString());
						arriveTime = obj.getString("arriveTime");
						leaveTime = obj.getString("leaveTime");
						roomCount = obj.get("roomCount").toString();
						roomTypeName = obj.getString("roomTypeName");
						RetainTime = obj.getString("RetainTime");
						hotelName = obj.getString("hotelName");
						phonenum = obj.getString("GuestPhone");
						userName = obj.getString("GuestName");
						text0.setText(getString(com.rs.mobile.wportal.R.string.common_text029) + RetainTime + ","
								+ getString(com.rs.mobile.wportal.R.string.common_text030));
						text1.setText(hotelName);
						text2.setText(arriveTime + getString(com.rs.mobile.wportal.R.string.ht_text_077) + leaveTime);
						text3.setText(roomCount + getString(com.rs.mobile.wportal.R.string.ht_text_087) + "," + roomTypeName);
						text4.setText(userName);
						text5.setText(phonenum);
						text6.setText(RetainTime);

					} else {
						t(data.getString("msg"));
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
		}, Constant.BASE_URL_HT + Constant.HT_GET_ORDERDETAIL, params);
	}

	private void initView() {
		text0 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text0);
		text1 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text1);
		text2 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text2);
		text3 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text3);
		text4 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text4);
		text5 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text5);
		text6 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text6);
		text_check = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_check);
		text_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("orderId", orderId);
				PageUtil.jumpTo(HtOrderResultActivity.this, HtOrderDetailActivity.class, bundle);
			}
		});
	}
}
