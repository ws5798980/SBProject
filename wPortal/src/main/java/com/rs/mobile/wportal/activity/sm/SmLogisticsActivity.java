
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.adapter.sm.LogistaticsAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class SmLogisticsActivity extends BaseActivity {

	private LinearLayout close_btn;

	private WImageView image_goods;

	private TextView text_state, text_code, text_count;

	private String phone;

	private ListView listView;

	private RelativeLayout rela_call;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_logistics);
		initView();
		initData();
	}

	private void initView() {

		close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);
		close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				finish();
			}
		});
		image_goods = (WImageView) findViewById(com.rs.mobile.wportal.R.id.image_goods);
		text_state = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_state);
		text_code = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_code);
		text_count = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_count);
		listView = (ListView) findViewById(com.rs.mobile.wportal.R.id.listView);
		rela_call = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela_call);
		rela_call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				PageUtil.callDial(SmLogisticsActivity.this, phone);
			}
		});
	}

	private void initData() {

		Map<String, String> params = new HashMap<String, String>();
		params.put(C.KEY_JSON_FM_ITEM_CODE, "1");
		OkHttpHelper okHttpHelper = new OkHttpHelper(SmLogisticsActivity.this);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				L.d(data.toString());
				jsonParsing(data);
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.GET_GOODSEXPRESSOF_LIST, params);
	}

	private void jsonParsing(JSONObject data) {

		try {
			JSONObject object = data.getJSONObject(C.KEY_JSON_DATA);
			phone = object.get(C.KEY_JSON_FM_EXP_MOBILE).toString();
			ImageUtil.drawImageFromUri(object.getString(C.KEY_JSON_FM_ITEM_URL), image_goods);
			text_state.setText(object.getString(C.KEY_JSON_FM_STATUS));
			text_code.setText(object.get(C.KEY_JSON_FM_BILL).toString());
			text_count.setText(object.get(C.KEY_JSON_FM_COUNT).toString());
			JSONArray jsonArray = object.getJSONArray(C.KEY_JSON_FM_EXP_FOLLOW);
			listView.setAdapter(new LogistaticsAdapter(jsonArray, SmLogisticsActivity.this));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
