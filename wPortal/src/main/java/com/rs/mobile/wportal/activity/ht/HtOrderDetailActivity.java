package com.rs.mobile.wportal.activity.ht;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.ht.HtQrAdapter;
import com.rs.mobile.wportal.biz.ht.HotelQr;
import com.rs.mobile.wportal.view.TimerTextView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class HtOrderDetailActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private TimerTextView text_time;
	private LinearLayout line_nopay, line_leaveHotel;
	private TextView text_pay, text_evaluate;
	private TextView text_hotelname;
	private TextView text_hotel_position;
	private LinearLayout text_check_position;
	private LinearLayout text_check_telephone;
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private TextView text4;
	private TextView text5;
	private TextView text6;
	private TextView text7;
	private TextView text8;
	private TextView text9;
	private TextView text10;
	private TextView text_action;
	View line001;
	private View line002;
	private ImageView r2;
	private String orderId;
	private boolean payment;
	private String orderStatus;
	private String imgUrl;
	private String bedType, arriveTime, leaveTime, HotelInfoID;
	protected String order_amount;
	protected String hotelName;
	private TextView tv_1;
	private TextView tv_2;
	private LinearLayout line_sucessess;
	private TextView time_out;
	private ListView lv;
	protected String reserveTime;
	protected double Latitude;
	protected double Longitude;
	protected String hotelPhone;
	private TextView text_in;
	protected String point_amount;
	protected String real_amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_order_detail);
		orderId = getIntent().getStringExtra("orderId");

		initToolbar();
		initView();
		initData();
		// new Thread(new Runnable() {
		// public void run() {
		//
		// }
		// },2000).start();
	}

	private void initView() {
		// TODO Auto-generated method stub
		line_nopay = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_nopay);
		line_leaveHotel = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_leaveHotel);
		line_sucessess = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_sucessess);
		text_evaluate = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_evaluate);
		text_time = (TimerTextView) findViewById(com.rs.mobile.wportal.R.id.text_time);
		text_pay = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_pay);
		text_hotelname = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_hotelname);
		text_hotel_position = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_hotel_position);
		text_check_position = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.text_check_position);
		text_check_telephone = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.text_check_telephone);
		text1 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text1);
		text2 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text2);
		text3 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text3);
		text4 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text4);
		text5 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text5);
		text6 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text6);
		text7 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text7);
		text8 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text8);
		text9 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text9);
		text10 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text10);
		text_in = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_in);
		text_action = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_action);
		tv_1 = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_1);
		tv_2 = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_2);
		time_out = (TextView) findViewById(com.rs.mobile.wportal.R.id.time_out);
		lv = (ListView) findViewById(com.rs.mobile.wportal.R.id.lv);
		line001 = (View) findViewById(com.rs.mobile.wportal.R.id.line001);
		line002 = (View) findViewById(com.rs.mobile.wportal.R.id.line002);
		r2 = (ImageView) findViewById(com.rs.mobile.wportal.R.id.r2);

	}

	private void changColor() {
		line001.setBackgroundColor(ContextCompat.getColor(this, com.rs.mobile.wportal.R.color.textcolor_ht_tabhost_tabspec_selected));
		line002.setBackgroundColor(ContextCompat.getColor(this, com.rs.mobile.wportal.R.color.textcolor_ht_tabhost_tabspec_selected));
		r2.setBackground(ContextCompat.getDrawable(this, com.rs.mobile.wportal.R.drawable.icon_yuan_l));
	}

	private void initData() {
		HashMap<String, String> params = new HashMap<>();
		params.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		params.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
		params.put("orderId", orderId);
		OkHttpHelper okHttpHelper = new OkHttpHelper(HtOrderDetailActivity.this);
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
						final String currentTime = obj.getString("currentTime");
						final String overTime = obj.getString("overTime");
						orderStatus = obj.getString("orderStatus");
						imgUrl = obj.getString("imgurl");
						bedType = obj.getString("roomTypeName");
						arriveTime = obj.getString("arriveTime");
						leaveTime = obj.getString("leaveTime");
						order_amount = obj.get("order_amount").toString();
						HotelInfoID = obj.get("hotelInfoID").toString();
						hotelName = obj.get("hotelName").toString();
						reserveTime = obj.getString("reserveTime");
						Longitude = Double.parseDouble(
								obj.optString("Longitude", "0").equals("") ? "0" : obj.optString("Longitude", "0"));
						Latitude = Double.parseDouble(
								obj.optString("Latitude", "0").equals("") ? "0" : obj.optString("Latitude", "0"));
						hotelPhone = obj.getString("hotelPhone");
						real_amount = obj.get("real_amount").toString();
						point_amount = obj.get("point_amount").toString();
						text_in.setText(obj.getString("GuestName"));
						JSONArray arr = obj.getJSONArray("roomInfo");
						text_check_telephone.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + hotelPhone));
								if (ActivityCompat.checkSelfPermission(HtOrderDetailActivity.this,
										Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
									return;
								}
								startActivity(intent);

							}

						});
						text_check_position.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								try {

									Intent intent = new Intent();
									Bundle bundle = new Bundle();
									bundle.putDouble("location_lat", Latitude);
									bundle.putDouble("location_lng", Longitude);
									bundle.putString("location_name", hotelName);
									intent.setClassName("cn.ycapp.im", "cn.ycapp.im.ui.activity.AMAPLocationActivity");
									intent.putExtras(bundle);
									startActivity(intent);

								} catch (Exception e) {

									L.e(e);

								}

							}
						});
						switch (orderStatus) {
						case "0":
							tv_title.setText(getString(com.rs.mobile.wportal.R.string.common_text023));
							line_nopay.setVisibility(View.VISIBLE);
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date1, date2;
							try {
								date1 = dateFormat.parse(currentTime);
								date2 = dateFormat.parse(overTime);
								long time = date2.getTime() - date1.getTime();
								if (!text_time.isRun() && time > 0) {
									text_time.setTimes(time);
									text_time.setDetail("");
									text_time.start(new TimerTextView.OnTimerOverListener() {

										@Override
										public void onOver() {
											// TODO Auto-generated method stub

										}
									});
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							text_pay.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Bundle bundle = new Bundle();

									bundle.putString("order_no", orderId);
									bundle.putString("order_amount", order_amount);
									bundle.putString("real_amount", real_amount);
									bundle.putString("order_Point", point_amount);
									bundle.putString("currentTime", currentTime);
									bundle.putString("overTime", overTime);
									bundle.putString("imgUrl", imgUrl);
									bundle.putString("bedType", bedType);
									bundle.putString("arriveTime", arriveTime);
									bundle.putString("leaveTime", leaveTime);
									bundle.putString("timeContent",
											arriveTime + getString(com.rs.mobile.wportal.R.string.ht_text_077) + leaveTime);

									bundle.putString("hotelName", hotelName);
									PageUtil.jumpTo(HtOrderDetailActivity.this, HtPaymentActivity.class, bundle);
									finish();
								}
							});
							text_action.setText(getString(com.rs.mobile.wportal.R.string.common_text014));
							text_action.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Bundle bundle = new Bundle();
									bundle.putString("orderId", orderId);
									bundle.putString("order_amount", order_amount);
									PageUtil.jumpTo(HtOrderDetailActivity.this, HtConcelOrderActivity.class, bundle);

									finish();
								}
							});
							text_action.setVisibility(View.VISIBLE);
							break;
						case "1":
							tv_title.setText(getString(com.rs.mobile.wportal.R.string.common_text005));
							line_sucessess.setVisibility(View.VISIBLE);
							text_action.setText(getString(com.rs.mobile.wportal.R.string.common_text014));
							text_action.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Bundle bundle = new Bundle();
									bundle.putString("orderId", orderId);
									bundle.putString("order_amount", order_amount);
									PageUtil.jumpTo(HtOrderDetailActivity.this, HtConcelOrderActivity.class, bundle);

									finish();
								}
							});
							text_action.setVisibility(View.VISIBLE);
							break;
						case "2":
							tv_title.setText(getString(com.rs.mobile.wportal.R.string.common_text024));
							time_out.setVisibility(View.VISIBLE);
							text_action.setText(getString(com.rs.mobile.wportal.R.string.common_text025));
							text_action.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									deleOrder();
								}
							});
							text_action.setVisibility(View.VISIBLE);
							break;
						case "3":
							tv_title.setText("订单详情");
							time_out.setVisibility(View.VISIBLE);
							time_out.setText(getString(com.rs.mobile.wportal.R.string.common_text026));
							text_action.setText(getString(com.rs.mobile.wportal.R.string.common_text025));
							text_action.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									deleOrder();
								}
							});
							text_action.setVisibility(View.VISIBLE);
							break;
						case "4":
							tv_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_check_in));
							text_action.setVisibility(View.GONE);
							;
							line_sucessess.setVisibility(View.VISIBLE);
							changColor();
							lv.setVisibility(View.VISIBLE);
							tv_1.setText(reserveTime);
							List<HotelQr> listData = new ArrayList<>();
							for (int i = 0; i < arr.length(); i++) {
								JSONObject j = arr.getJSONObject(i);
								HotelQr h = new HotelQr(HotelInfoID, j.getString("RoomNo"));
								listData.add(h);
							}
							HtQrAdapter adapter = new HtQrAdapter(listData, HtOrderDetailActivity.this);
							lv.setAdapter(adapter);
							setListViewHeight(lv);

							break;

						case "6":
							tv_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_101));
							time_out.setVisibility(View.VISIBLE);
							time_out.setText(getString(com.rs.mobile.wportal.R.string.ht_text_101));

							break;
						case "8":
							tv_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_check_in));
							line_sucessess.setVisibility(View.VISIBLE);
							changColor();
							lv.setVisibility(View.VISIBLE);
							tv_1.setText(reserveTime);
							List<HotelQr> listData1 = new ArrayList<>();
							for (int i = 0; i < arr.length(); i++) {
								JSONObject j = arr.getJSONObject(i);
								HotelQr h = new HotelQr(HotelInfoID, j.getString("RoomNo"));
								listData1.add(h);
							}
							HtQrAdapter adapter1 = new HtQrAdapter(listData1, HtOrderDetailActivity.this);
							lv.setAdapter(adapter1);
							setListViewHeight(lv);
							text_action.setText(getString(com.rs.mobile.wportal.R.string.common_text014));
							text_action.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Bundle bundle = new Bundle();
									bundle.putString("orderId", orderId);
									bundle.putString("order_amount", order_amount);
									PageUtil.jumpTo(HtOrderDetailActivity.this, HtConcelOrderActivity.class, bundle);

									finish();
								}
							});
							text_action.setVisibility(View.VISIBLE);
							break;
						case "9":
							tv_title.setText(getString(com.rs.mobile.wportal.R.string.common_text027));
							time_out.setVisibility(View.VISIBLE);
							time_out.setText(getString(com.rs.mobile.wportal.R.string.ht_text_102));

							break;
						case "10":
							tv_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_103));
							line_leaveHotel.setVisibility(View.VISIBLE);
							text_evaluate.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Bundle bundle = new Bundle();
									bundle.putString("orderId", orderId);
									bundle.putString("imgUrl", imgUrl);
									bundle.putString("bedType", bedType);
									bundle.putString("arriveTime", arriveTime);
									bundle.putString("leaveTime", leaveTime);
									bundle.putString("order_amount", order_amount);
									bundle.putString("HotelInfoID", HotelInfoID);
									bundle.putString("hotelName", hotelName);
									;
									PageUtil.jumpTo(HtOrderDetailActivity.this, HtEvaluateActivity.class, bundle);
									finish();
								}
							});
							break;
						case "11":
							tv_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_104));
							time_out.setVisibility(View.VISIBLE);
							time_out.setText(getString(com.rs.mobile.wportal.R.string.ht_text_104));
							break;
						case "5":
							tv_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_103));
							line_leaveHotel.setVisibility(View.VISIBLE);
							text_evaluate.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Bundle bundle = new Bundle();
									bundle.putString("orderId", orderId);
									bundle.putString("imgUrl", imgUrl);
									bundle.putString("bedType", bedType);
									bundle.putString("arriveTime", arriveTime);
									bundle.putString("leaveTime", leaveTime);
									bundle.putString("order_amount", order_amount);
									bundle.putString("HotelInfoID", HotelInfoID);
									bundle.putString("hotelName", hotelName);

									PageUtil.jumpTo(HtOrderDetailActivity.this, HtEvaluateActivity.class, bundle);
									finish();
								}
							});
							break;

						default:
							tv_title.setText("订单详情");
							break;
						}

						text_hotelname.setText(obj.getString("hotelName"));
						text_hotel_position.setText(obj.getString("address"));
						text1.setText(
								obj.getString("arriveTime") + getString(com.rs.mobile.wportal.R.string.ht_text_077) + obj.get("leaveTime"));
						text2.setText(
								obj.get("roomCount") + getString(com.rs.mobile.wportal.R.string.ht_text_087) + "," + obj.get("roomTypeName"));
						text3.setText(obj.getString("userName"));
						text4.setText(obj.getString("phonenum"));
						text5.setText(obj.getString("RetainTime"));
						text6.setText(obj.getString("orderId"));
						text7.setText(obj.getString("reserveTime"));
						text8.setText(obj.get("roomCount").toString());
						text9.setText(obj.getString("GuestPhone"));
						text10.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + obj.get("order_amount"));

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

			setSupportActionBar(toolbar);
			tv_title.setText("订单详情");
		} catch (Exception e) {

			L.e(e);

		}
	}

	private void deleOrder() {
		HashMap<String, String> paramsKeyValue = new HashMap<>();
		paramsKeyValue.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		paramsKeyValue.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
		paramsKeyValue.put("orderId", orderId);
		OkHttpHelper okHttpHelper = new OkHttpHelper(HtOrderDetailActivity.this);
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
		}, Constant.BASE_URL_HT + Constant.HT_DELE_ORDER, paramsKeyValue);

	}
}
