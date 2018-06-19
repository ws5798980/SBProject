package com.rs.mobile.wportal.activity.ht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.ht.HtHotelAdapter;
import com.rs.mobile.wportal.biz.ht.Hotel;
import com.rs.mobile.wportal.A;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class HtHotelActivity extends BaseActivity implements OnRefreshListener2<ListView> {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView text_city;
	private PullToRefreshListView lv_hotel;
	private Context context = HtHotelActivity.this;
	private String Zip_Code = "410000";
	private String sCity = "长沙市";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_hotel);
		initToolbar();
		initView();
		initData();

	}

	private void initToolbar() {
		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
			text_city = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_city);
			text_city.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(context, HtCityActivity.class);

					i.putExtra("type", C.TYPE_PORTAL);

					Location location = A.getLocation();

					if (location != null) {

						i.putExtra("lon", "" + location.getLongitude());

						i.putExtra("lat", "" + location.getLatitude());

						i.putExtra("cityName", text_city.getText().toString());

						i.putExtra("zipcode", Zip_Code);

					} else {

						// 기본 위치 설정
						i.putExtra("lon", "" + "113.027417");

						i.putExtra("lat", "" + "28.184747");

						i.putExtra("cityName", text_city.getText().toString());

						i.putExtra("zipcode", Zip_Code);
					}

					startActivityForResult(i, 1000);
				}
			});
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
		lv_hotel = (PullToRefreshListView) findViewById(com.rs.mobile.wportal.R.id.lv_hotel);
		lv_hotel.setOnRefreshListener(this);
		lv_hotel.setMode(Mode.PULL_FROM_START);
	}

	private void initData() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		HashMap<String, String> params = new HashMap<>();
		params.put("sCity", sCity);
		params.put("Zip_Code", Zip_Code);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				lv_hotel.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {

					lv_hotel.onRefreshComplete();
					if (data.get("status").toString().equals("1")) {
						JSONArray obj = data.getJSONArray("data");
						List<Hotel> arr = new ArrayList<>();
						for (int i = 0; i < obj.length(); i++) {
							JSONObject o = obj.getJSONObject(i);
							Hotel h = new Hotel(o.get("hotelinfoid").toString(), o.getString("hotelname"),
									o.getString("district"), o.getString("photeurl"), o.getString("hotellevel"),
									o.getString("hotellev"), Float.parseFloat(o.get("nomemeberprice").toString()),
									Float.parseFloat(o.get("total_score").toString()),
									Integer.parseInt(o.get("rated_count").toString()));
							arr.add(h);
						}
						HtHotelAdapter adapter = new HtHotelAdapter(arr, context);
						lv_hotel.setAdapter(adapter);
					} else {
						t(data.get("msg").toString());
					}

				} catch (Exception e) {
					// TODO: handle exception
					L.e(e);
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				lv_hotel.onRefreshComplete();
			}
		}, Constant.BASE_URL_HT + Constant.HT_MAIN_GETDATA, params);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		initData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == Activity.RESULT_OK) {
			switch (arg0) {
			case 1000:
				text_city.setText(arg2.getStringExtra("cityName"));
				sCity = arg2.getStringExtra("cityName");
				Zip_Code = arg2.getStringExtra("zipcode");
				initData();
				break;

			default:
				break;
			}
		}
	}
}
