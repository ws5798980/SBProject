package com.rs.mobile.wportal.adapter.ht;

import java.util.List;

import com.rs.mobile.wportal.biz.ht.City;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HtCityAdapter extends BaseAdapter {
	public HtCityAdapter(List<City> list, Activity context) {
		super();
		this.list = list;
		this.context = context;
	}

	private List<City> list;
	private Activity context;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CityViewHolder vh;
		if (convertView == null) {
			vh = new CityViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.ht_list_item_city, parent, false);
			vh.tv_city = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_city);
			convertView.setTag(vh);
		} else {
			vh = (CityViewHolder) convertView.getTag();
		}
		final City c = list.get(position);
		vh.tv_city.setText(c.getCityname());
		vh.tv_city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent result = new Intent();
				result.putExtra("cityName", c.getCityname());
				result.putExtra("zipcode", c.getZipCode());
				context.setResult(Activity.RESULT_OK, result);
				context.finish();
			}
		});
		return convertView;
	}

	static class CityViewHolder {
		public TextView tv_city;
	}

}
