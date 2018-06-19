package com.rs.mobile.wportal.activity.ht;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.view.MenuButtonView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class HtHotelntroduceActivity extends BaseActivity {

	private Toolbar toolbar;

	private LinearLayout iv_back;

	private TextView tv_title;

	private TextView hotel_name;

	private TextView hotel_description;

	private LinearLayout callBtn;

	private GridView serviceInfoGridView;

	private JSONObject mainData, obj;

	private TextView text_airport;

	private TextView text_railway;

	private TextView text_subway;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_hotel_introduce);

		initToolbar();

		initView();

		initData();

	}

	private void initToolbar() {

		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);

		iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);

		tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);

		tv_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_094));

		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		setSupportActionBar(toolbar);
	}

	private void initView() {

		hotel_name = (TextView) findViewById(com.rs.mobile.wportal.R.id.hotel_name_text_view);

		hotel_description = (TextView) findViewById(com.rs.mobile.wportal.R.id.hotel_description_text_view);

		callBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.call_btn);

		serviceInfoGridView = (GridView) findViewById(com.rs.mobile.wportal.R.id.service_grid_view);

		text_airport = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_airport);

		text_railway = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_railway);

		text_subway = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_subway);
	}

	private void initData() {

		OkHttpHelper okHttpHelper = new OkHttpHelper(this);

		HashMap<String, String> params = new HashMap<String, String>();

		params.put("HotelInfoID", getIntent().getStringExtra("HotelInfoID"));

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

					String status = data.getString("status");

					if (status.equals("1")) {

						obj = data.getJSONObject("data");

						mainData = obj.getJSONArray("HotelDetail").getJSONObject(0);
						hotel_name.setText(mainData.getString("HotelName"));

						hotel_description.setText(mainData.getString("Descriptions"));

						text_airport.setText(mainData.getString("Airport"));
						text_railway.setText(mainData.getString("TrainStation"));
						text_subway.setText(mainData.getString("SubWayStation"));
						callBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								// TODO Auto-generated method stub

								Intent intent;
								try {
									intent = new Intent(Intent.ACTION_CALL,
											Uri.parse("tel:" + mainData.getString("Fac")));
									if (ActivityCompat.checkSelfPermission(HtHotelntroduceActivity.this,
											Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
										return;
									}
									startActivity(intent);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});
						serviceInfoGridView.setAdapter(new GridAdapter(obj.getJSONArray("ServiceInfo")));

						UiUtil.setGridViewHeight(serviceInfoGridView, 4);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e(e);
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_HT + Constant.HT_GET_HOTEL_INTRODUCE, params);

	}

	public class GridAdapter extends BaseAdapter {

		private JSONArray arr;

		public GridAdapter(JSONArray arr) {
			super();
			this.arr = arr;
		}

		@Override
		public int getCount() {

			// TODO Auto-generated method stub

			if (arr != null)
				return arr.length();
			return 0;
		}

		@Override
		public Object getItem(int position) {

			// TODO Auto-generated method stub
			try {

				if (arr != null)
					return arr.getJSONObject(position);

			} catch (Exception e) {

				L.e(e);

			}

			return null;
		}

		@Override
		public long getItemId(int position) {

			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// TODO Auto-generated method stub
			if (convertView == null) {

				convertView = new MenuButtonView(HtHotelntroduceActivity.this);

			}

			try {

				JSONObject item = (JSONObject) getItem(position);

				((MenuButtonView) convertView).setIcon(Uri.parse((item.getString("ImageURL"))));

				((MenuButtonView) convertView).setIconParam(get_windows_width(HtHotelntroduceActivity.this) / 12,
						get_windows_width(HtHotelntroduceActivity.this) / 12);
				((MenuButtonView) convertView).set_lines(2);
				((MenuButtonView) convertView).setText(item.getString("ServiceDetailName"));

			} catch (Exception e) {

				L.e(e);

			}

			return convertView;
		}

	}

}