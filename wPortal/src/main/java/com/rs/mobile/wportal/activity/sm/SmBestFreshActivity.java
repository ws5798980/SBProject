
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.BestFreshAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import okhttp3.Request;

public class SmBestFreshActivity extends BaseActivity {

	private WImageView good_img;

	private ListView listView;

	private String ad_id;

	private View headView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_bestfresh);

		ad_id = getIntent().getStringExtra(C.KEY_JSON_FM_AD_ID);

		initView();

		initData();

	}

	private void initData() {

		getBestFresh(ad_id);
	}

	private void initView() {

		try {

			LinearLayout close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);
			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					finish();
				}
			});
			headView = LayoutInflater.from(SmBestFreshActivity.this).inflate(com.rs.mobile.wportal.R.layout.activity_listview_head_img, null);

			good_img = (WImageView) headView.findViewById(com.rs.mobile.wportal.R.id.good_img);
			listView = (ListView) findViewById(com.rs.mobile.wportal.R.id.listView);
			listView.addHeaderView(headView);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void getBestFresh(String ad_id) {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_AD_ID, ad_id);
			params.put("div_code", C.DIV_CODE);
			OkHttpHelper okHttpHelper = new OkHttpHelper(SmBestFreshActivity.this);
			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

					try {

						JSONObject obj = data.getJSONObject(C.KEY_JSON_DATA);

						ImageUtil.drawImageFromUri(obj.getString(C.KEY_JSON_FM_BANNER_URL), good_img);

						JSONArray arr = obj.getJSONArray(C.KEY_JSON_LIST);

						listView.setAdapter(new BestFreshAdapter(arr, SmBestFreshActivity.this));

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.GET_BEST_FRESH_OFLIST, params);

		} catch (Exception e) {

			L.e(e);

		}
	}
}
