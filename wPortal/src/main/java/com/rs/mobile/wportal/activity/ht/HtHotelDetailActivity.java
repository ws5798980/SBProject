package com.rs.mobile.wportal.activity.ht;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.astuetz.PagerSlidingTabStrip;
import com.handmark.pulltorefresh.library.ObservableScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.ht.HtRoomAdapter;
import com.rs.mobile.wportal.biz.ht.HotelRate;
import com.rs.mobile.wportal.biz.ht.HotelService;
import com.rs.mobile.wportal.biz.ht.HtRoom;
import com.rs.mobile.wportal.fragment.ht.HotelEvaluateFragment;
import com.rs.mobile.wportal.fragment.ht.HotelServiceFragment;
import com.rs.mobile.wportal.view.ht.DayPickerView;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.DateString;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.adapter.ht.HtMyFragmentPageAdapter;
import com.rs.mobile.wportal.view.CustomViewPager;
import com.rs.mobile.wportal.view.ht.DatePickerController;
import com.rs.mobile.wportal.view.ht.SimpleMonthAdapter.CalendarDay;
import com.rs.mobile.wportal.view.ht.SimpleMonthAdapter.SelectedDays;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class HtHotelDetailActivity extends BaseActivity
		implements OnRefreshListener2<ObservableScrollView>, DatePickerController {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	public TextView text_time;
	public TextView tv_checkin;
	public TextView tv_checkout;
	private WImageView img;
	private TextView text_address;
	private ImageView img_map;
	private PagerSlidingTabStrip tabs;
	private CustomViewPager viewpager_fragment;
	private ListView lv;
	private HtHotelDetailActivity context = HtHotelDetailActivity.this;
	private String HotelInfoID;
	private double Latitude;
	private double Longitude;
	private PullToRefreshScrollView sv_hotel;
	public String hotelname, hotelImg;
	protected boolean firstLoad;
	private String ArriveTime;
	private String LeaveTime;
	private boolean timeFlag = false;
	private Calendar c = Calendar.getInstance();
	private AlertDialog alertDialog1;
	private DayPickerView.DataModel dataModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_hotel_detail);
		firstLoad = true;
		HotelInfoID = getIntent().getStringExtra("HotelInfoID");
		hotelname = getIntent().getStringExtra("hotelname");
		Date d = new Date();
		Calendar c1 = new GregorianCalendar();
		c1.setTime(d);
		ArriveTime = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());
		c1.add(Calendar.DATE, 1);
		d = c1.getTime();

		LeaveTime = new SimpleDateFormat("yyyy-MM-dd").format(d);

		initToolbar();
		initView();
		initData();
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
		tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
		tv_title.setText(hotelname);
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
		text_time = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_time);
		text_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTimePicker1();
			}
		});
		tv_checkin = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_checkin);
		tv_checkout = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_checkout);
		img = (WImageView) findViewById(com.rs.mobile.wportal.R.id.img);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("HotelInfoID", HotelInfoID);
				PageUtil.jumpTo(context, HtHotelAlbumActivity.class, bundle);
			}
		});
		text_address = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_address);
		img_map = (ImageView) findViewById(com.rs.mobile.wportal.R.id.img_map);
		img_map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub

				try {

					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putDouble("location_lat", Latitude);
					bundle.putDouble("location_lng", Longitude);
					bundle.putString("location_name", hotelname);
					intent.setClassName("cn.ycapp.im", "cn.ycapp.im.ui.activity.AMAPLocationActivity");
					intent.putExtras(bundle);
					startActivity(intent);

				} catch (Exception e) {

					L.e(e);

				}

			}
		});
		tabs = (PagerSlidingTabStrip) findViewById(com.rs.mobile.wportal.R.id.tabs);
		lv = (ListView) findViewById(com.rs.mobile.wportal.R.id.lv);
		sv_hotel = (PullToRefreshScrollView) findViewById(com.rs.mobile.wportal.R.id.sv_hotel);
		sv_hotel.setMode(Mode.PULL_FROM_START);
		sv_hotel.setOnRefreshListener(this);
		// lv.addOnLayoutChangeListener(new OnLayoutChangeListener() {
		//
		// @Override
		// public void onLayoutChange(View v, int left, int top, int right, int
		// bottom, int oldLeft, int oldTop, int oldRight,
		// int oldBottom) {
		// // TODO Auto-generated method stub
		// sv_hotel.scrollTo(0, 0);
		// }
		// });
		viewpager_fragment = (CustomViewPager) findViewById(com.rs.mobile.wportal.R.id.viewpager_fragment);

	}

	private void initData() {
		tv_checkin.setText(ArriveTime);
		tv_checkout.setText(LeaveTime);
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		HashMap<String, String> params = new HashMap<>();
		params.put("HotelInfoID", HotelInfoID);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				sv_hotel.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				try {

					sv_hotel.onRefreshComplete();

					String status = data.get("status").toString();

					if (status.equals("1")) {

						JSONObject obj = data.getJSONObject("data");
						JSONObject HotelInfo = obj.getJSONObject("HotelInfo");
						text_address.setText(HotelInfo.getString("Address"));
						hotelImg = HotelInfo.getString("PhoteURL");
						ImageUtil.drawImageFromUri(HotelInfo.getString("PhoteURL"), img);

						Latitude = Double.parseDouble(HotelInfo.get("Latitude").toString().equals("") ? "0"
								: HotelInfo.get("Latitude").toString());
						Longitude = Double.parseDouble(HotelInfo.get("Longitude").toString().equals("") ? "0"
								: HotelInfo.get("Longitude").toString());

						JSONObject Rated = obj.getJSONObject("Rated");

						HotelRate h = new HotelRate(Rated.getString("userName"), Rated.getString("ratedName"),
								Float.parseFloat((Rated.get("total_score").toString() == null
										|| Rated.get("total_score").toString().equals("")) ? "0"
												: Rated.get("total_score").toString()),
								Rated.getString("ratedContext"));

						HotelEvaluateFragment f1 = new HotelEvaluateFragment(HotelInfoID, h);

						JSONArray ServiceInfo = obj.getJSONArray("ServiceInfo");

						List<HotelService> listData = new ArrayList<>();

						for (int i = 0; i < ServiceInfo.length(); i++) {
							JSONObject j = ServiceInfo.getJSONObject(i);
							HotelService hs = new HotelService(j.getString("SeqID"), j.getString("ServiceDetailName"),
									j.getString("ImageURL"));
							listData.add(hs);
						}
						// t(listData.toString());
						HotelServiceFragment f2 = new HotelServiceFragment(listData, HotelInfoID);
						List<Fragment> fList = new ArrayList<>();
						fList.add(f1);
						fList.add(f2);
						String[] s = { getString(com.rs.mobile.wportal.R.string.ht_text_evaluate_list),
								getString(com.rs.mobile.wportal.R.string.ht_text_hotel_detail) };
						HtMyFragmentPageAdapter adapter = new HtMyFragmentPageAdapter(getSupportFragmentManager(),
								fList, s);
						// t(ServiceInfo+"");
						viewpager_fragment.setAdapter(adapter);

						if (firstLoad) {
							firstLoad = false;
							tabs.setViewPager(viewpager_fragment);

							tabs.setTextSize(StringUtil.dip2px(getApplicationContext(), 14));
							tabs.setDividerColor(0x00000000);
							tabs.setDividerPadding(StringUtil.dip2px(getApplicationContext(), 0));
							tabs.setTabPaddingLeftRight(StringUtil.dip2px(getApplicationContext(), 0));
							tabs.setIndicatorColorResource(com.rs.mobile.wportal.R.color.textcolor_ht_tabhost_tabspec_selected);
							tabs.setIndicatorHeight(StringUtil.dip2px(getApplicationContext(), 2));
							tabs.setSelectedTextColor(ContextCompat.getColor(getApplicationContext(),
									com.rs.mobile.wportal.R.color.textcolor_ht_tabhost_tabspec_selected));
						}
						JSONArray RoomTypeData = obj.getJSONArray("RoomTypeData");
						List<HtRoom> listRoom = new ArrayList<>();
						for (int i = 0; i < RoomTypeData.length(); i++) {
							JSONObject j1 = RoomTypeData.getJSONObject(i);
							HtRoom hr = new HtRoom(j1.getString("RoomTypeID"), j1.getString("RoomTypeName"),
									Float.parseFloat(j1.get("NoMemeberPrice").toString()),
									Float.parseFloat(j1.get("MemeberPrice").toString()), j1.getString("RoomTypeImg"),
									j1.getString("Remark"));
							listRoom.add(hr);
						}
						final HtRoomAdapter adapter2 = new HtRoomAdapter(listRoom, context, HotelInfoID);

						new Handler().postDelayed(new Runnable() {
							public void run() {
								// execute the task

								lv.setAdapter(adapter2);
								setListViewHeight1(lv);

								// sv_hotel.getRefreshableView().scrollTo(0, 0);
							}
						}, 100);

					} else {
						t(data.getString("msg"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e("", e.toString());
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv_hotel.onRefreshComplete();
			}
		}, Constant.BASE_URL_HT + Constant.HT_GET_HOTEL_DETAIL, params);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		// TODO Auto-generated method stub
		initData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		// TODO Auto-generated method stub

	}

	private void setListViewHeight1(ListView listView) {

		int totalHeight = 0;
		ListAdapter listAdapter = listView.getAdapter();
		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItemView = listAdapter.getView(i, null, listView);
			int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.EXACTLY);
			listItemView.measure(desiredWidth, 0);
			totalHeight += listItemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);

	}

	public long dateToStamp(String s) {
		String res = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleDateFormat.parse(s + " " + "00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long ts = date.getTime();
		return ts;
	}

	private void showTimePicker1() {

		try {

			final View dialogView = View.inflate(HtHotelDetailActivity.this, com.rs.mobile.wportal.R.layout.ht_date_picker, null);
			alertDialog1 = new AlertDialog.Builder(HtHotelDetailActivity.this).create();
			final DayPickerView datePicker = (DayPickerView) dialogView.findViewById(com.rs.mobile.wportal.R.id.date_picker);
			dataModel = new DayPickerView.DataModel();
			dataModel.yearStart = Calendar.getInstance().get(Calendar.YEAR);
			dataModel.monthCount = 12;
			dataModel.defTag = "";
			String[] alList = ArriveTime.split("-");
			String[] leStrings = LeaveTime.split("-");
			CalendarDay sCalendarDay = new CalendarDay(Integer.parseInt(alList[0]), Integer.parseInt(alList[1]) - 1,
					Integer.parseInt(alList[2]));
			CalendarDay eCalendarDay = new CalendarDay(Integer.parseInt(leStrings[0]),
					Integer.parseInt(leStrings[1]) - 1, Integer.parseInt(leStrings[2]));

			SelectedDays<CalendarDay> selectedDays = new SelectedDays<>();
			selectedDays.setFirst(sCalendarDay);
			selectedDays.setLast(eCalendarDay);
			dataModel.selectedDays = selectedDays;
			datePicker.setParameter(dataModel, this);

			alertDialog1.setView(dialogView);
			alertDialog1.show();

		} catch (Exception e) {

			L.e(e);

		}

	}

	@Override
	public void onDayOfMonthSelected(CalendarDay calendarDay) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDateRangeSelected(List<CalendarDay> selectedDays) {
		// TODO Auto-generated method stub
		CalendarDay sCalendarDay = selectedDays.get(0);
		CalendarDay eCalendarDay = selectedDays.get(selectedDays.size() - 1);
		ArriveTime = sCalendarDay.year + "-" + (sCalendarDay.month + 1) + "-" + sCalendarDay.day;
		LeaveTime = eCalendarDay.year + "-" + (eCalendarDay.month + 1) + "-" + eCalendarDay.day;
		tv_checkin.setText(ArriveTime);
		tv_checkout.setText(LeaveTime);
		try {
			text_time.setText(getString(com.rs.mobile.wportal.R.string.ht_text_092) + DateString.daysBetween(ArriveTime, LeaveTime)
					+ getString(com.rs.mobile.wportal.R.string.ht_text_093));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alertDialog1.dismiss();
	}

	@Override
	public void alertSelectedFail(FailEven even) {
		// TODO Auto-generated method stub
		t(even.toString());
	}

}
