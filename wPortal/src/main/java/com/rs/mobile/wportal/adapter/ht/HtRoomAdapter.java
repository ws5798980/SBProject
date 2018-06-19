package com.rs.mobile.wportal.adapter.ht;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.ht.HtFillOrderActivity;
import com.rs.mobile.wportal.activity.ht.HtHotelDetailActivity;
import com.rs.mobile.wportal.biz.ht.HtRoom;
import com.rs.mobile.wportal.view.CustomViewPager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class HtRoomAdapter extends BaseAdapter {
	public HtRoomAdapter(List<HtRoom> data, HtHotelDetailActivity context, String HotelInfoID) {
		super();
		this.data = data;
		this.context = context;
		this.HotelInfoID = HotelInfoID;
	}

	private List<HtRoom> data;
	private HtHotelDetailActivity context;
	private String HotelInfoID;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HtRoomViewHolder vh;
		if (convertView == null) {
			vh = new HtRoomViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ht_list_item_hotel, parent, false);
			vh.img_hotel = (WImageView) convertView.findViewById(R.id.img_hotel);
			vh.name_room = (TextView) convertView.findViewById(R.id.name_room);
			vh.text_price = (TextView) convertView.findViewById(R.id.text_price);
			vh.text_button = (LinearLayout) convertView.findViewById(R.id.text_button);
			convertView.setTag(vh);
		} else {
			vh = (HtRoomViewHolder) convertView.getTag();
		}
		final HtRoom h = data.get(position);
		ImageUtil.drawImageFromUri(h.getRoomTypeImg(), vh.img_hotel);
		vh.name_room.setText(h.getRoomTypeName());
		vh.text_price.setText(context.getResources().getString(R.string.rmb) + h.getMemeberPrice());
		vh.text_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("ArriveTime", context.tv_checkin.getText().toString());
				bundle.putString("LeaveTime", context.tv_checkout.getText().toString());
				bundle.putString("HotelInfoID", HotelInfoID);
				bundle.putString("Time", context.text_time.getText().toString());
				bundle.putString("HotelName", context.hotelname);
				bundle.putString("RoomType", h.getRoomTypeName());
				bundle.putString("RoomTypeID", h.getRoomTypeID());
				bundle.putString("room_price", h.getMemeberPrice() + "");
				bundle.putString("imgUrl", context.hotelImg);
				PageUtil.jumpTo(context, HtFillOrderActivity.class, bundle);

			}
		});
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showRoom(h.getRoomTypeID());
			}
		});
		return convertView;
	}

	static class HtRoomViewHolder {
		WImageView img_hotel;
		TextView name_room;
		TextView text_price;
		LinearLayout text_button;

	}

	private void showRoom(String RoomTypeID) {
		HashMap<String, String> params = new HashMap<>();
		params.put("HotelInfoID", HotelInfoID);
		params.put("RoomTypeID", RoomTypeID);
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		okHttpHelper.addPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					String status = data.get("status").toString();
					JSONObject obj = data.getJSONObject("data");
					if (status.equals("1")) {
						final View dialogView = View.inflate(context, R.layout.ht_dialog_room, null);
						final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
						TextView text_room, internet, window, area, floor, num, bedtype, Roomfacilities, Shower,
								Fooddrinks, Media;
						ImageView close_btn;
						CustomViewPager vp_room;
						close_btn = (ImageView) dialogView.findViewById(R.id.close_btn);
						close_btn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								alertDialog.dismiss();
							}
						});
						alertDialog.setCancelable(true);
						alertDialog.onBackPressed();
						vp_room = (CustomViewPager) dialogView.findViewById(R.id.vp_room);
						vp_room.setAdapter(new HtViewPagerAdapter(context, obj, (float) 1.75));
						text_room = (TextView) dialogView.findViewById(R.id.text_room);
						internet = (TextView) dialogView.findViewById(R.id.internet);
						window = (TextView) dialogView.findViewById(R.id.window);
						area = (TextView) dialogView.findViewById(R.id.area);
						floor = (TextView) dialogView.findViewById(R.id.floor);
						num = (TextView) dialogView.findViewById(R.id.num);
						bedtype = (TextView) dialogView.findViewById(R.id.bedtype);
						Roomfacilities = (TextView) dialogView.findViewById(R.id.Roomfacilities);
						Shower = (TextView) dialogView.findViewById(R.id.Shower);
						Fooddrinks = (TextView) dialogView.findViewById(R.id.Fooddrinks);
						Media = (TextView) dialogView.findViewById(R.id.Media);
						JSONObject RoomTypeInfo = obj.getJSONArray("RoomTypeInfo").getJSONObject(0);
						text_room.setText(RoomTypeInfo.getString("roomtypename"));
						internet.setText("WiFi");
						window.setText(context.getString(R.string.common_text090));
						area.setText(RoomTypeInfo.getString("area") + context.getString(R.string.ht_text_m2));
						floor.setText(RoomTypeInfo.getString("floor") + "F");
						num.setText(
								RoomTypeInfo.getString("availableNum") + context.getString(R.string.ht_text_People));
						bedtype.setText(RoomTypeInfo.getString("bedType"));
						JSONArray arrRoomfacilities = obj.getJSONArray("Roomfacilities");
						String s1 = "";
						for (int i = 0; i < arrRoomfacilities.length(); i++) {
							if (i == 0) {
								s1 = s1 + arrRoomfacilities.getJSONObject(i).getString("ServiceDetailName");
							} else {
								s1 = s1 + "," + arrRoomfacilities.getJSONObject(i).getString("ServiceDetailName");
							}
						}
						Roomfacilities.setText(s1);
						JSONArray arrShower = obj.getJSONArray("Shower");
						String s2 = "";
						for (int i = 0; i < arrShower.length(); i++) {
							if (i == 0) {
								s2 = s2 + arrShower.getJSONObject(i).getString("ServiceDetailName");
							} else {
								s2 = s2 + "," + arrShower.getJSONObject(i).getString("ServiceDetailName");
							}
						}
						Shower.setText(s2);
						JSONArray arrFooddrinks = obj.getJSONArray("Fooddrinks");
						String s3 = "";
						for (int i = 0; i < arrFooddrinks.length(); i++) {
							if (i == 0) {
								s3 = s3 + arrFooddrinks.getJSONObject(i).getString("ServiceDetailName");
							} else {
								s3 = s3 + "," + arrFooddrinks.getJSONObject(i).getString("ServiceDetailName");
							}
						}
						Fooddrinks.setText(s3);
						JSONArray arrMedia = obj.getJSONArray("Media");
						String s4 = "";
						for (int i = 0; i < arrMedia.length(); i++) {
							if (i == 0) {
								s4 = s4 + arrMedia.getJSONObject(i).getString("ServiceDetailName");
							} else {
								s4 = s4 + "," + arrMedia.getJSONObject(i).getString("ServiceDetailName");
							}
						}
						Media.setText(s4);
						alertDialog.setView(dialogView);
						alertDialog.show();
					} else {
						T.showToast(context, data.getString("msg"));
					}
				} catch (Exception e) {
					// TODO: handle exception
					L.e(e);
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_HT + Constant.HT_GET_ROOM_DETAIL, params);
	}

}
