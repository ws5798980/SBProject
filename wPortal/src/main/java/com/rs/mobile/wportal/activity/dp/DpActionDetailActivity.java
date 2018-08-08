package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.S;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class DpActionDetailActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private WImageView img;
	private String activity_Id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dp_action_detail);
		initToolbar();
		img = (WImageView) findViewById(R.id.img);
		activity_Id = getIntent().getStringExtra("activity_Id");
		initData();
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(R.id.toolbar);
			iv_back = (LinearLayout) findViewById(R.id.iv_back);
			tv_title = (TextView) findViewById(R.id.tv_title);
			tv_title.setText(getResources().getString(R.string.dp_text_032));
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

	private void initData() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(DpActionDetailActivity.this);
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;Charset=UTF-8");
		JSONObject obj = new JSONObject();
		try {
			obj.put("lang_type", AppConfig.LANG_TYPE);
			obj.put("memid", S.get(getApplicationContext(), C.KEY_SHARED_PHONE_NUMBER, ""));
			obj.put("mall_home_id", S.get(getApplicationContext(), C.KEY_SHARED_PHONE_NUMBER, ""));
			obj.put("mem_grade", "");
			obj.put("mall_grade", "");
			obj.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
			obj.put("ssoId", "");
			obj.put("regiKey", "");
			obj.put("div_code", C.DIV_CODE);
			obj.put("activity_Id", activity_Id);

		} catch (Exception e) {
			// TODO: handle exception
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
					String image_url = data.getString("image_url");
					ImageUtil.drawImageFromUri(image_url, img);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_DP + Constant.DP_ACTIVITYDETAIL, headers, obj.toString());

	}
}
