package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.dp.DpActionAdapter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class DpActionActivity extends BaseActivity {


	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private LinearLayout horizontal_scroll_area;
	private ListView lv;
	private String in_progress;
	private int current_page;
	private int page_size;
	private TextView textView1;
	private TextView textView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dp_action);
		initToolbar();
		initView();
		in_progress = "1";
		current_page = 1;
		page_size = 20;
		initData();
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(R.id.toolbar);
			iv_back = (LinearLayout) findViewById(R.id.iv_back);
			tv_title = (TextView) findViewById(R.id.tv_title);
			tv_title.setText(getResources().getString(R.string.dp_text_btn2));
			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {
		horizontal_scroll_area = (LinearLayout) findViewById(R.id.horizontal_scroll_area);
		lv = (ListView) findViewById(R.id.lv);
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				in_progress = "1";
				initData();
			}
		});
		textView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				in_progress = "0";
				initData();
			}
		});
	}

	private void initData() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(DpActionActivity.this);
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;Charset=UTF-8");
		JSONObject obj = new JSONObject();

		try {
			obj.put("lang_type", "chn");
			obj.put("memid", S.get(getApplicationContext(), C.KEY_SHARED_PHONE_NUMBER, ""));
			obj.put("mall_home_id", S.get(getApplicationContext(), C.KEY_SHARED_PHONE_NUMBER, ""));
			obj.put("mem_grade", "");
			obj.put("mall_grade", "");
			obj.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
			obj.put("ssoId", "");
			obj.put("regiKey", "");
			obj.put("div_code", C.DIV_CODE);
			obj.put("in_progress", in_progress);
			obj.put("current_page", current_page + "");
			obj.put("page_size", page_size + "");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		okHttpHelper.addPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					JSONArray array = data.getJSONArray("listActivity");
					lv.setAdapter(new DpActionAdapter(array, DpActionActivity.this));

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_DP + Constant.DP_LISTACTIVITY, headers, obj.toString());
	}
}
