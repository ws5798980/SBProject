package com.rs.mobile.wportal.activity.ht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.adapter.ht.HtCityAdapter;
import com.rs.mobile.wportal.biz.ht.City;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class HtCityActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView text_city_choosed;
	private GridView gv;
	private Activity context = HtCityActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_city);
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
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initView() {
		text_city_choosed = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_city_choosed);
		text_city_choosed.setText(getIntent().getStringExtra("cityName"));

		text_city_choosed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		gv = (GridView) findViewById(com.rs.mobile.wportal.R.id.gv);

	}

	private void initData() {
		OkHttpHelper helper = new OkHttpHelper(this);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");

		helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data, final String all_data) {
				// TODO Auto-generated method stub

				try {

					// JSONObject divInfo = data.getJSONObject("divInfo");

					JSONArray arr = data.getJSONArray("availableCityList");

					List<City> list = new ArrayList<>();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject j = arr.getJSONObject(i);
						City c = new City(j.getString("cityName"), j.getString("cityZipcode"));
						list.add(c);
					}
					HtCityAdapter adapter = new HtCityAdapter(list, context);
					gv.setAdapter(adapter);

				} catch (Exception e) {

					L.e(e);

				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub

			}
		}, C.BASE_URL + C.LOCATION_GET_MY_LOCATION + getIntent().getStringExtra("type") + "&lon="
				+ getIntent().getStringExtra("lon") + "&lat=" + getIntent().getStringExtra("lat"), params);

	}

}