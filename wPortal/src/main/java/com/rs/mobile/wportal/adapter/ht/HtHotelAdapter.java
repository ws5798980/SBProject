package com.rs.mobile.wportal.adapter.ht;

import java.util.List;

import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.biz.ht.Hotel;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.activity.ht.HtHotelDetailActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HtHotelAdapter extends BaseAdapter {
	public HtHotelAdapter(List<Hotel> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	private List<Hotel> list;
	private Context context;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.ht_listitem_hotel, parent, false);
			vh.district = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.district);
			vh.hotellev = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.hotellev);
			vh.text_evluate = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_evluate);
			vh.text_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
			vh.text_price = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_price);
			vh.text_score = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_score);
			vh.img_hotel = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.img_hotel);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		final Hotel data = list.get(position);
		vh.district.setText(data.getDistrict());
		vh.hotellev.setText(data.getHotellev());
		vh.text_evluate.setText(data.getRated_count() + context.getString(com.rs.mobile.wportal.R.string.common_text083));
		vh.text_name.setText(data.getHotelname());
		vh.text_price.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.rmb) + data.getNomemeberprice());
		vh.text_score.setText(data.getTotal_score() + context.getResources().getString(com.rs.mobile.wportal.R.string.ht_text_fen));

		ImageUtil.drawImageFromUri(data.getPhoteurl(), vh.img_hotel);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("HotelInfoID", data.getHotelinfoid());
				bundle.putString("hotelname", data.getHotelname());
				PageUtil.jumpTo(context, HtHotelDetailActivity.class, bundle);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		public WImageView img_hotel;
		public TextView text_name;
		public TextView text_score;
		public TextView text_evluate;
		public TextView text_price;
		public TextView hotellev;
		public TextView district;
	}

}
