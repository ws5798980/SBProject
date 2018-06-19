package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import okhttp3.Request;

/**
 * 
 * @author ZhaoYun
 * @date 2017-3-13
 */
public class RtReserveSelectActivity extends BaseActivity implements OnClickListener {

	// title
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private TextView reservationStatusTableBtn;

	// content
	private LinearLayout ll_eat_time;
	private TextView tv_eat_time;
	private LinearLayout ll_eat_nop;
	private EditText tv_eat_nop;
	private LinearLayout ll_eat_needbox;
	private CheckBox cb_eat_needbox;
	private LinearLayout ll_eat_contactname;
	private EditText tv_eat_contactname;
	private LinearLayout ll_eat_contactphone;
	private EditText tv_eat_contactphone;
	private LinearLayout ll_eat_remark;
	private EditText et_eat_remark;
	private LinearLayout minus_btn;
	private LinearLayout plus_btn;

	// footer
	private RelativeLayout rl_appointment;
	private RelativeLayout rl_ordernow;

	private TextView tv_appointment;

	private TextView tv_ordernow;

	// 데이터
	private String cartArr;

	private String restaurantCode;

	private Receiver receiver;

	// 0 : 같이 먹기
	private String from = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_orderselect);

		receiver = new Receiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(RtMainActivity.ACTION_RECEIVE_RT_FINISH);
		registerReceiver(receiver, filter);

		restaurantCode = getIntent().getStringExtra("restaurantCode");

		cartArr = getIntent().getStringExtra("cartArr");

		from = getIntent().getStringExtra("from");

		initToolbar();
		initViews();
		initDatas();
		initListeners();
	}

	@Override
	protected void onDestroy() {

		unregisterReceiver(receiver);

		super.onDestroy();
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
			tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
			reservationStatusTableBtn = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_right1);
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initViews() {

		reservationStatusTableBtn.setText(getString(com.rs.mobile.wportal.R.string.rt_reservation_status_table));
		reservationStatusTableBtn.setVisibility(View.VISIBLE);
		reservationStatusTableBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * 예약 현황 표 주소 https://foodapi.shelongwang.com:8843/DinnerTable/
				 * ReserveTableList?customCode=JA01&searchDate=2017-05-02
				 */
				String url = Constant.BASE_URL_RT + Constant.RT_RESERVE_STATUS_TABLE + "?customCode=" + restaurantCode
						+ "&searchDate="
						+ tv_eat_time.getText().toString().replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");

				Intent i = new Intent(RtReserveSelectActivity.this, WebActivity.class);

				i.putExtra(C.KEY_INTENT_URL, url);

				startActivity(i);

			}
		});

		ll_eat_time = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_eat_time);

		ll_eat_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showResolvePicker();

			}
		});

		tv_eat_time = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_eat_time);

		Date now = new Date(System.currentTimeMillis() + (1000 * 60 * 60));

		String y = "" + (now.getYear() + 1900);
		String m = "" + (now.getMonth() + 1);
		String d = "" + now.getDate();
		String h = "" + now.getHours();
		String M = "" + now.getMinutes();

		m = m.length() < 2 ? "0" + m : m;
		d = d.length() < 2 ? "0" + d : d;
		h = h.length() < 2 ? "0" + h : h;
		M = M.length() < 2 ? "0" + M : M;
		tv_eat_time.setText(y + "-" + m + "-" + d + " " + h + ":" + M);

		ll_eat_nop = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_eat_nop);

		tv_eat_nop = (EditText) findViewById(com.rs.mobile.wportal.R.id.tv_eat_nop);
		tv_eat_nop.setText("1");
		ll_eat_needbox = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_eat_needbox);
		cb_eat_needbox = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.cb_eat_needbox);
		ll_eat_contactname = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_eat_contactname);
		tv_eat_contactname = (EditText) findViewById(com.rs.mobile.wportal.R.id.tv_eat_contactname);
		tv_eat_contactname.setText(S.get(RtReserveSelectActivity.this, C.KEY_SHARED_KNICK_NAME));
		ll_eat_contactphone = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_eat_contactphone);
		tv_eat_contactphone = (EditText) findViewById(com.rs.mobile.wportal.R.id.tv_eat_contactphone);
		if(!S.getShare(RtReserveSelectActivity.this, C.KEY_JSON_TOKEN, "").equals(""))
			tv_eat_contactphone.setText(S.getShare(RtReserveSelectActivity.this, C.KEY_JSON_TOKEN, "").substring(0, 11));
		else
			tv_eat_contactphone.setText(S.getShare(RtReserveSelectActivity.this, C.KEY_SHARED_PHONE_NUMBER, ""));
		ll_eat_remark = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_eat_remark);
		et_eat_remark = (EditText) findViewById(com.rs.mobile.wportal.R.id.et_eat_remark);
		rl_appointment = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rl_appointment);
		rl_ordernow = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rl_ordernow);

		tv_appointment = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_appointment);

		tv_ordernow = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_ordernow);

		if (from != null && from.equals("0")) {
			// 같이 먹기
			rl_appointment.setVisibility(View.GONE);

			tv_ordernow.setText(getString(com.rs.mobile.wportal.R.string.next));

			tv_eat_nop.setText("2");

		} else if (cartArr == null || cartArr.equals("")) {

			rl_appointment.setVisibility(View.VISIBLE);
			// tv_appointment.setText("预约确认");

		} else {

			rl_appointment.setVisibility(View.GONE);

		}

		minus_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.minus_btn);

		plus_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.plus_btn);

		minus_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {

					int count = Integer.parseInt(tv_eat_nop.getText().toString());

					if (from != null && from.equals("0")) {
						// 같이 먹기
						if (count > 2) {

							count = count - 1;

						} else {

							t(getString(com.rs.mobile.wportal.R.string.rt_reservation_msg_01));

						}

					} else {

						if (count > 1) {

							count = count - 1;

						}

					}

					tv_eat_nop.setText("" + count);

				} catch (Exception e) {

					L.e(e);

				}

			}
		});

		plus_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {

					int count = Integer.parseInt(tv_eat_nop.getText().toString());

					count = count + 1;

					tv_eat_nop.setText("" + count);

				} catch (Exception e) {

					L.e(e);

				}

			}
		});

	}

	private void initDatas() {
		tv_title.setText(getString(com.rs.mobile.wportal.R.string.common_text058));
	}

	private void initListeners() {
		iv_back.setOnClickListener(this);
		rl_appointment.setOnClickListener(this);
		rl_ordernow.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case com.rs.mobile.wportal.R.id.iv_back:
			onBack();
			break;

		case com.rs.mobile.wportal.R.id.rl_appointment:

			showDialog(getString(com.rs.mobile.wportal.R.string.rt_reserve_complete_title), getString(com.rs.mobile.wportal.R.string.rt_reserve_complete_msg),
					getString(com.rs.mobile.wportal.R.string.ok), new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							reserve(false);

						}

					}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							D.alertDialog.dismiss();
						}
					});
			break;

		case com.rs.mobile.wportal.R.id.rl_ordernow:

			showDialog(
					getString(cartArr != null && !cartArr.equals("") ? com.rs.mobile.wportal.R.string.rt_reserve_menu_complete_title
							: com.rs.mobile.wportal.R.string.rt_choice_reserve_complete_title),
					getString(cartArr != null && !cartArr.equals("") ? com.rs.mobile.wportal.R.string.rt_reserve_menu_complete_msg
							: com.rs.mobile.wportal.R.string.rt_choice_menu_complete_msg),
					getString(cartArr != null && !cartArr.equals("") ? com.rs.mobile.wportal.R.string.ok : com.rs.mobile.wportal.R.string.ok),
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							D.alertDialog.dismiss();

							reserve(true);

						}

					}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							D.alertDialog.dismiss();
						}
					});
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		onBack();
	}

	private void onBack() {
		finish();
	}

	public void showResolvePicker() {

		try {

			final View dialogView = View.inflate(RtReserveSelectActivity.this, com.rs.mobile.wportal.R.layout.date_time_picker, null);
			final AlertDialog alertDialog = new AlertDialog.Builder(RtReserveSelectActivity.this).create();
			final DatePicker datePicker = (DatePicker) dialogView.findViewById(com.rs.mobile.wportal.R.id.date_picker);
			final TimePicker timePicker = (TimePicker) dialogView.findViewById(com.rs.mobile.wportal.R.id.time_picker);

			try {

				// 이전 버전에서 적용되지 않는다
				if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
					datePicker.setMinDate(new Date().getTime() - 10000);

			} catch (Exception e) {

				L.e(e);

			}

			dialogView.findViewById(com.rs.mobile.wportal.R.id.date_btn).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {

					datePicker.setVisibility(View.VISIBLE);

					timePicker.setVisibility(View.GONE);

				}
			});

			dialogView.findViewById(com.rs.mobile.wportal.R.id.time_btn).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {

					datePicker.setVisibility(View.GONE);

					timePicker.setVisibility(View.VISIBLE);

				}
			});

			dialogView.findViewById(com.rs.mobile.wportal.R.id.set_btn).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {

					DatePicker datePicker = (DatePicker) dialogView.findViewById(com.rs.mobile.wportal.R.id.date_picker);
					TimePicker timePicker = (TimePicker) dialogView.findViewById(com.rs.mobile.wportal.R.id.time_picker);

					Date now = new Date(System.currentTimeMillis() + (1000 * 60 * 60));

					Date timePickerDate = new Date(datePicker.getYear() - 1900, datePicker.getMonth(),
							datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());

					if (now.compareTo(timePickerDate) > 0) {

						t(getString(com.rs.mobile.wportal.R.string.common_text059));

						return;
					}

					String y = "" + datePicker.getYear();
					String m = "" + (datePicker.getMonth() + 1);
					String d = "" + datePicker.getDayOfMonth();
					String h = "" + timePicker.getCurrentHour();
					String M = "" + timePicker.getCurrentMinute();

					m = m.length() < 2 ? "0" + m : m;
					d = d.length() < 2 ? "0" + d : d;
					h = h.length() < 2 ? "0" + h : h;
					M = M.length() < 2 ? "0" + M : M;
					tv_eat_time.setText(y + "-" + m + "-" + d + " " + h + ":" + M);

					// time = calendar.getTimeInMillis();
					alertDialog.dismiss();
				}
			});
			alertDialog.setView(dialogView);
			alertDialog.show();
		} catch (Exception e) {

			L.e(e);

		}

	}

	// public void checkReserve(final boolean isOrder) {
	//
	// try {
	//
	// if (tv_eat_time.getText() == null ||
	// tv_eat_time.getText().toString().equals("")
	// || tv_eat_nop.getText() == null ||
	// tv_eat_nop.getText().toString().equals("")
	// || tv_eat_contactname.getText() == null ||
	// tv_eat_contactname.getText().toString().equals("")
	// || tv_eat_contactphone.getText() == null ||
	// tv_eat_contactphone.getText().toString().equals("")) {
	//
	// t("请输入内容");
	//
	// return;
	//
	// }
	//
	// OkHttpHelper helper = new OkHttpHelper(RtReserveSelectActivity.this);
	//
	// HashMap<String, String> params = new HashMap<String, String>();
	// params.put("", "");
	//
	// helper.addPostRequest(new CallbackLogic() {
	//
	// @Override
	// public void onNetworkError(Request request, IOException e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onBizSuccess(String responseDescription, JSONObject data,
	// final String all_data) {
	// // TODO Auto-generated method stub
	//
	// try {
	//
	// L.d(all_data);
	//
	// String status = data.getString("status");
	//
	// if (status != null && status.equals("2")) {
	//
	// //예약 불가
	//
	// t(getString(R.string.rt_msg_impossible_reserve));
	//
	// } else if (status != null && status.equals("1")) {
	//
	// //예약 가능
	//
	// reserve(isOrder);
	//
	// } else {
	//
	// //오류
	//
	// t(data.getString("msg"));
	//
	// }
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// }
	//
	// }
	//
	// @Override
	// public void onBizFailure(String responseDescription, JSONObject data,
	// String responseCode) {
	// // TODO Auto-generated method stub
	//
	// }
	// }, Constant.BASE_URL_RT + Constant.RT_GET_RESERVE_POSSIBLE +
	// "?reserveDate=" + tv_eat_time.getText().toString().replaceAll(" ",
	// "").replaceAll("-", "").replaceAll(":", "")
	// + "&memberId=" + S.getShare(RtReserveSelectActivity.this,
	// C.KEY_REQUEST_MEMBER_ID, "")
	// + "&restaurantCode=" + restaurantCode
	// + "&membersCount=" + tv_eat_nop.getText().toString()
	// + "&token=" + S.getShare(RtReserveSelectActivity.this, C.KEY_JSON_TOKEN,
	// "")
	// , params);
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// }
	//
	// }

	/**
	 * 예약
	 * 
	 * @param isOrder
	 */
	public void reserve(final boolean isOrder) {

		try {

			if (tv_eat_time.getText() == null || tv_eat_time.getText().toString().equals("")
					|| tv_eat_nop.getText() == null || tv_eat_nop.getText().toString().equals("")
					|| tv_eat_contactname.getText() == null || tv_eat_contactname.getText().toString().equals("")
					|| tv_eat_contactphone.getText() == null || tv_eat_contactphone.getText().toString().equals("")) {

				t(getString(com.rs.mobile.wportal.R.string.common_text060));

				return;

			}

			OkHttpHelper helper = new OkHttpHelper(RtReserveSelectActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("", "");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(all_data);

						String status = data.getString("status");

						t(data.getString("msg"));

						if (status != null && status.equals("1")) {

							data = data.getJSONObject("data");

							String reserveID = data.getString("reserveID");

							if (isOrder) {

								// 같이먹기
								if (from != null && from.equals("0")) {

									Intent i = new Intent(RtReserveSelectActivity.this, RtReserveDetailActivity.class);
									i.putExtra("reserveID", reserveID);
									i.putExtra(C.EXTRA_KEY_RESERVESTATUS, "R");
									i.putExtra("restaurantCode", restaurantCode);
									i.putExtra("from", from);
									startActivity(i);

									finish();

								} else if (cartArr == null || cartArr.equals("")) {

									Intent i = new Intent(RtReserveSelectActivity.this, RtMenuListActivity.class);
									i.putExtra("reserveID", reserveID);
									i.putExtra("restaurantCode", restaurantCode);
									startActivity(i);

									finish();

								} else {

									// 결제
									addOrder(reserveID, cartArr);

									return;

								}

							} else {

								sendBroadcast(new Intent(RtMainActivity.ACTION_RECEIVE_RT_FINISH));

								Intent reserveDetailIntent = new Intent(RtReserveSelectActivity.this,
										RtReserveDetailActivity.class);
								reserveDetailIntent.putExtra(C.EXTRA_KEY_MEMBERID,
										S.getShare(RtReserveSelectActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));// TODO
																												// 这里要在流程通畅后填入实时获取的memberId
								reserveDetailIntent.putExtra(C.EXTRA_KEY_RESERVEID, reserveID);
								reserveDetailIntent.putExtra(C.EXTRA_KEY_ORDERNUM, "");
								reserveDetailIntent.putExtra(C.EXTRA_KEY_RESERVESTATUS, "R");
								startActivity(reserveDetailIntent);

								// 예약완료
								finish();

							}

						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_RT + Constant.RT_ADD_RESERVE + "?saleCustomCode=" + restaurantCode + "&customCode="
					+ S.getShare(RtReserveSelectActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&reserveDate="
					+ tv_eat_time.getText().toString().replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "")
					+ "&personCount=" + tv_eat_nop.getText().toString() + "&userName="
					+ tv_eat_contactname.getText().toString() + "&phoneNumber="
					+ tv_eat_contactphone.getText().toString() + "&roomUse="
					+ (cb_eat_needbox.isChecked() == true ? "1" : "0") + "&description="
					+ et_eat_remark.getText().toString() + "&token="
					+ S.getShare(RtReserveSelectActivity.this, C.KEY_JSON_TOKEN, "") + "&groupYN="
					+ ((from != null && from.equals("0")) ? "Y" : "N"), params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void addOrder(final String reserveID, String arr) {

		try {

			if (arr == null || arr.length() == 0) {

				return;

			}

			OkHttpHelper helper = new OkHttpHelper(RtReserveSelectActivity.this);

			HashMap<String, String> headers = new HashMap<String, String>();

			headers.put("Content-Type", "application/json;Charset=UTF-8");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(all_data);

						String status = data.getString("status");

						if (status != null && status.equals("1")) {

							finish();

							Intent i = new Intent(RtReserveSelectActivity.this, OrderDetailActivity.class);
							i.putExtra("title", getString(com.rs.mobile.wportal.R.string.order_detail_from_list));
							i.putExtra("payText", getString(com.rs.mobile.wportal.R.string.payment_from_list));
							i.putExtra("orderNumber", data.getString("data"));
							i.putExtra("reserveID", reserveID);
							startActivity(i);

						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_RT + Constant.RT_ADD_ORDER + "?divCode=" + C.DIV_CODE + "&reserveID=" + reserveID
					+ "&restaurantCode=" + restaurantCode + "&userID="
					+ S.getShare(RtReserveSelectActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&token="
					+ S.getShare(RtReserveSelectActivity.this, C.KEY_JSON_TOKEN, ""), headers, arr);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public class Receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			try {

				if (intent.getAction().equals(RtMainActivity.ACTION_RECEIVE_RT_FINISH)) {

					finish();

				}

			} catch (Exception e) {

				L.e(e);

			}

		}

	}

}
