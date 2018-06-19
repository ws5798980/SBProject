package com.rs.mobile.wportal.activity.ht;

import java.io.IOException;
import java.util.ArrayList;
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
import com.rs.mobile.wportal.biz.ht.HtContact;
import com.rs.mobile.wportal.adapter.ht.HtSelectRoomAdapter;
import com.rs.mobile.wportal.biz.ht.DictTime;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class HtFillOrderActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title, text_ht_name, text_context, text_bedtype, text_roomNum, text_arriveTime, text_money,
			text_detail, text_confirm;
	private ListView lvContact;
	private String HotelInfoID, ArriveTime, LeaveTime, RoomTypeID, RetainTime, room_price, order_amount, Point_amount,
			real_amount, userName, Phonenum, Prepayment, Remark;

	private String[] num = new String[10];
	// { "1" + context.getString(R.string.ht_text_087),
	// "2" + context.getString(R.string.ht_text_087), "3" +
	// context.getString(R.string.ht_text_087),
	// "4" + context.getString(R.string.ht_text_087), "5" +
	// context.getString(R.string.ht_text_087),
	// "6" + context.getString(R.string.ht_text_087), "7" +
	// context.getString(R.string.ht_text_087),
	// "8" + context.getString(R.string.ht_text_087), "9" +
	// context.getString(R.string.ht_text_087),
	// "10" + context.getString(R.string.ht_text_087) };
	// private String[] time = { "18:00之前", "20:00之前", "22:00之前", "00:00之前",
	// "次日凌晨6:00之前" };
	private int RoomCount = 1;
	private HtSelectRoomAdapter htSelectAdapter;
	private List<HtContact> listData = new ArrayList<>();
	private String imgUrl;
	private List<DictTime> time = new ArrayList<>();
	private TextView point;
	private CheckBox check_box;
	private boolean usePoint = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_fillorder);
		initNum();
		initToolbar();
		initView();
		getData();
	}

	private void initNum() {
		for (int i = 0; i < 10; i++) {
			num[i] = (i + 1) + getString(com.rs.mobile.wportal.R.string.ht_text_087);
		}
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
			tv_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_088));
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {
		text_ht_name = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_ht_name);

		text_context = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_context);

		text_bedtype = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_bedtype);

		text_roomNum = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_roomNum);

		text_roomNum.setText(RoomCount + getString(com.rs.mobile.wportal.R.string.ht_text_087));

		text_roomNum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showRoomSelect();
			}
		});

		text_arriveTime = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_arriveTime);
		text_arriveTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTimeDialog();
			}
		});
		text_money = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_money);

		text_detail = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_detail);

		text_confirm = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_confirm);
		text_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (RoomCount == 0) {

				} else {
					Phonenum = "";
					userName = "";
					for (int i = 0; i < listData.size(); i++) {
						HtContact h = listData.get(i);
						if (!"".equals(h.getPhonenum()) && !"".equals(h.getUserName())) {
							if (i == 0) {
								Phonenum = Phonenum + h.getPhonenum();
								userName = userName + h.getUserName();
							} else {
								Phonenum = Phonenum + "," + h.getPhonenum();
								userName = userName + "," + h.getUserName();
							}
						} else {
							t(getString(com.rs.mobile.wportal.R.string.ht_text_089));
							return;
						}
					}
					if ("".equals(RetainTime) || RetainTime == null) {
						t(getString(com.rs.mobile.wportal.R.string.ht_text_090));
						return;
					}
					if (usePoint) {

						createOrder(real_amount);
					} else {
						Point_amount = "0";
						createOrder(order_amount);
					}

				}

			}
		});
		lvContact = (ListView) findViewById(com.rs.mobile.wportal.R.id.lvContact);
		HtContact h = new HtContact("", "");
		listData.add(h);
		htSelectAdapter = new HtSelectRoomAdapter(listData, HtFillOrderActivity.this);
		lvContact.setAdapter(htSelectAdapter);
		setListViewHeight(lvContact);
		point = (TextView) findViewById(com.rs.mobile.wportal.R.id.point);
		check_box = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.check_box);
	}

	private void getData() {
		text_ht_name.setText(getIntent().getStringExtra("HotelName"));
		ArriveTime = getIntent().getStringExtra("ArriveTime");
		LeaveTime = getIntent().getStringExtra("LeaveTime");
		text_context.setText(getString(com.rs.mobile.wportal.R.string.ht_text_check_in) + ArriveTime + getString(com.rs.mobile.wportal.R.string.ht_text_check_out)
				+ LeaveTime + getIntent().getStringExtra("Time"));
		text_bedtype.setText(getIntent().getStringExtra("RoomType"));
		RoomTypeID = getIntent().getStringExtra("RoomTypeID");
		HotelInfoID = getIntent().getStringExtra("HotelInfoID");
		room_price = getIntent().getStringExtra("room_price");
		imgUrl = getIntent().getStringExtra("imgUrl");
		getPreparTime();
		getPrepay(RoomCount);
	}

	private void showRoomSelect() {

		try {

			final View dialogView = View.inflate(HtFillOrderActivity.this, com.rs.mobile.wportal.R.layout.ht_dialog_room_num, null);
			final AlertDialog alertDialog = new AlertDialog.Builder(HtFillOrderActivity.this).create();
			TextView text_cancel = (TextView) dialogView.findViewById(com.rs.mobile.wportal.R.id.text_cancel);
			final ListView lv = (ListView) dialogView.findViewById(com.rs.mobile.wportal.R.id.lv);
			CustomAdapter1 customAdapter = new CustomAdapter1(num);
			lv.setAdapter(customAdapter);
			ViewGroup.LayoutParams params = lv.getLayoutParams();
			params.height = get_windows_height(HtFillOrderActivity.this) / 3;
			lv.setLayoutParams(params);

			text_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
				}
			});
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					// TextView roomNum=
					// (TextView)view.findViewById(R.id.roomNum);
					// ImageView img=(ImageView)view.findViewById(R.id.img);
					// roomNum.setTextColor(ContextCompat.getColor(HtFillOrderActivity.this,
					// R.color.textcolor_ht_tabhost_tabspec_selected));
					// img.setVisibility(View.VISIBLE);
					// for (int i = 0; i < lv.getAdapter().getCount(); i++) {
					// if (i!=position) {
					//
					// TextView roomNum1=
					// (TextView)parent.getChildAt(i).findViewById(R.id.roomNum);
					// ImageView
					// img1=(ImageView)parent.getChildAt(i).findViewById(R.id.img);
					// roomNum1.setTextColor(ContextCompat.getColor(HtFillOrderActivity.this,
					// R.color.textcolor_ht_666));
					// img1.setVisibility(View.GONE);
					// }
					// }
					listData.clear();
					RoomCount = position + 1;
					text_roomNum.setText(RoomCount + getString(com.rs.mobile.wportal.R.string.ht_text_087));
					for (int i = 0; i < RoomCount; i++) {
						HtContact htContact = new HtContact("", "");
						listData.add(htContact);
					}
					htSelectAdapter = new HtSelectRoomAdapter(listData, HtFillOrderActivity.this);
					lvContact.setAdapter(htSelectAdapter);
					setListViewHeight(lvContact);
					alertDialog.dismiss();
					getPrepay(RoomCount);
				}
			});
			Window dialogw = alertDialog.getWindow();
			dialogw.setGravity(Gravity.BOTTOM);
			alertDialog.setView(dialogView);
			alertDialog.show();
		} catch (Exception e) {

			L.e(e);

		}

	}

	private void showTimeDialog() {

		try {

			final View dialogView = View.inflate(HtFillOrderActivity.this, com.rs.mobile.wportal.R.layout.ht_dialog_room_num, null);
			final AlertDialog alertDialog = new AlertDialog.Builder(HtFillOrderActivity.this).create();
			TextView text_cancel = (TextView) dialogView.findViewById(com.rs.mobile.wportal.R.id.text_cancel);
			TextView text_title = (TextView) dialogView.findViewById(com.rs.mobile.wportal.R.id.text_title);
			text_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_026));
			final ListView lv = (ListView) dialogView.findViewById(com.rs.mobile.wportal.R.id.lv);
			CustomAdapter customAdapter = new CustomAdapter(time);
			lv.setAdapter(customAdapter);
			ViewGroup.LayoutParams params = lv.getLayoutParams();
			params.height = get_windows_height(HtFillOrderActivity.this) / 3;
			lv.setLayoutParams(params);

			text_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
				}
			});
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					RetainTime = time.get(position).getDictKey();
					text_arriveTime.setText(time.get(position).getDictValue());
					alertDialog.dismiss();
				}

			});
			Window dialogw = alertDialog.getWindow();
			dialogw.setGravity(Gravity.BOTTOM);
			alertDialog.setView(dialogView);
			alertDialog.show();
		} catch (Exception e) {

			L.e(e);

		}

	}

	class CustomAdapter extends BaseAdapter {
		public CustomAdapter(List<DictTime> num) {
			super();
			this.num = num;
		}

		private List<DictTime> num;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return num.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return num.get(position);
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
				convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.ht_list_item_room_num, parent,
						false);
			}
			TextView roomNum = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.roomNum);
			roomNum.setText(num.get(position).getDictValue());
			return convertView;
		}
	}

	class CustomAdapter1 extends BaseAdapter {
		public CustomAdapter1(String[] num) {
			super();
			this.num = num;
		}

		private String[] num;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return num.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return num[position];
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
				convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.ht_list_item_room_num, parent,
						false);
			}
			TextView roomNum = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.roomNum);
			roomNum.setText(num[position]);
			return convertView;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 1000) {
			if (arg2 != null) {
				int position = arg2.getIntExtra("position", 0);

				listData.get(position).setPhonenum(arg2.getStringExtra("phone"));
				htSelectAdapter.notifyDataSetChanged();
			}

		}
	}

	private void getPrepay(int RoomCount) {
		HashMap<String, String> params = new HashMap<>();
		params.put("HotelInfoID", HotelInfoID);
		params.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		params.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
		params.put("room_price", room_price);
		params.put("ArriveTime", ArriveTime);
		params.put("LeaveTime", LeaveTime);
		params.put("RoomCount", RoomCount + "");
		params.put("RoomTypeID", RoomTypeID);
		params.put("usePoint", "1");
		OkHttpHelper okHttpHelper = new OkHttpHelper(HtFillOrderActivity.this);
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
						JSONObject obj = new JSONObject(data.get("data").toString());
						order_amount = obj.get("order_amount").toString();
						real_amount = obj.get("real_amount").toString();
						Point_amount = obj.get("order_point").toString();
						point.setText(getString(com.rs.mobile.wportal.R.string.common_text011) + Point_amount);
						if (usePoint) {
							text_money.setText(getString(com.rs.mobile.wportal.R.string.rmb) + real_amount);
						} else {
							text_money.setText(getString(com.rs.mobile.wportal.R.string.rmb) + order_amount);
						}

						check_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
								// TODO Auto-generated method stub
								if (isChecked) {

									text_money.setText(getString(com.rs.mobile.wportal.R.string.rmb) + real_amount);
								} else {
									text_money.setText(getString(com.rs.mobile.wportal.R.string.rmb) + order_amount);
								}
								usePoint = isChecked;
							}
						});
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
		}, Constant.BASE_URL_HT + Constant.HT_GET_PREPAY, params);
	}

	private void createOrder(String money) {
		OkHttpHelper okHttpHelper = new OkHttpHelper(HtFillOrderActivity.this);
		HashMap<String, String> params = new HashMap<>();
		params.put("HotelInfoID", HotelInfoID);
		params.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		params.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
		params.put("userName", userName);
		params.put("Phonenum", Phonenum);
		params.put("ArriveTime", ArriveTime);
		params.put("LeaveTime", LeaveTime);
		params.put("order_amount", order_amount);
		params.put("Prepayment", money);
		params.put("Point_amount", Point_amount);
		params.put("RetainTime", RetainTime);
		params.put("RoomTypeID", RoomTypeID);
		params.put("RoomCount", RoomCount + "");
		params.put("Remark", "");

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
						JSONObject obj = new JSONObject(data.get("data").toString());
						String order_no = obj.getString("order_no");
						String order_amount = obj.getString("order_amount");
						String real_amount = obj.getString("real_amount");
						String order_Point = obj.getString("order_Point");
						String currentTime = obj.getString("currentTime");
						String overTime = obj.getString("overTime");
						Bundle bundle = new Bundle();
						bundle.putString("order_no", order_no);
						bundle.putString("order_amount", order_amount);

						bundle.putString("real_amount", real_amount);
						bundle.putString("order_Point", order_Point);
						bundle.putString("currentTime", currentTime);
						bundle.putString("overTime", overTime);
						bundle.putString("hotelName", text_ht_name.getText().toString());
						bundle.putString("bedType", text_bedtype.getText().toString());
						bundle.putString("timeContent", text_context.getText().toString());
						bundle.putString("imgUrl", imgUrl);
						PageUtil.jumpTo(HtFillOrderActivity.this, HtPaymentActivity.class, bundle);
						finish();
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
		}, Constant.BASE_URL_HT + Constant.HT_CREATE_ORDER, params);
	}

	private void getPreparTime() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(HtFillOrderActivity.this);
		HashMap<String, String> params = new HashMap<>();
		params.put("Arrivetime", ArriveTime);
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
						JSONArray array = data.getJSONArray("data");
						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonObject = array.getJSONObject(i);
							DictTime dictTime = new DictTime(jsonObject.get("SystemDictionaryID").toString(),
									jsonObject.getString("DictType"), jsonObject.getString("DictKey"),
									jsonObject.getString("DictValue"));
							time.add(dictTime);
						}
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
		}, Constant.BASE_URL_HT + Constant.HT_GetRetainTimeList, params);

	}
}
