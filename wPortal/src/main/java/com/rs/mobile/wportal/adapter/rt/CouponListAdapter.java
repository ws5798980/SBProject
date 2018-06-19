package com.rs.mobile.wportal.adapter.rt;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.L;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CouponListAdapter extends BaseAdapter {

	private Context mContext;
	
	private LayoutInflater mLayoutInflater;
	
	private JSONArray arr;
	
	private int selectedId = -1;
	
	public CouponListAdapter(Context context, JSONArray arr){
		
		this.mContext = context;
		
		this.arr = arr;
		
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(int selectedId) {
		
		this.selectedId = selectedId;
		
		notifyDataSetChanged();
	}

	public JSONArray getListItems() {
		
		if (arr == null) arr = new JSONArray();
		
		return arr;
		
	}
	
	public void setListItems(JSONArray arr) {
		
		this.arr = arr;
		
	}
	
	@Override
	public int getCount() {
		return arr == null ? 0 : arr.length();
	}

	@Override
	public Object getItem(int position) {
		
		try {
		
			return arr == null ? null : arr.getJSONObject(position);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return null;
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final ViewHolder viewHolder;
		
		if(convertView == null){
			
			viewHolder = new ViewHolder();
			
			convertView = mLayoutInflater.inflate(com.rs.mobile.wportal.R.layout.list_item_rt_coupon, null);

			viewHolder.check_box = (ImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.check_box);
			
			viewHolder.coupon_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.coupon_name);

			viewHolder.coupon_time = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.coupon_time);
			
			viewHolder.text_money = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_money);

			viewHolder.text_requre = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_requre);

			convertView.setTag(viewHolder);
			
		} else {
			
			viewHolder = (ViewHolder) convertView.getTag();
			
		}
		
		//Setting values
		final JSONObject item = (JSONObject) getItem(position);
		
		try {
			
			if (selectedId == position) {
				
				viewHolder.check_box.setImageResource(com.rs.mobile.wportal.R.drawable.icon_orange);
				
			} else {
				
				viewHolder.check_box.setImageResource(com.rs.mobile.wportal.R.drawable.icon_kllj);
				
			}

			viewHolder.coupon_name.setText(item.getString("name"));
			
			viewHolder.coupon_time.setText(mContext.getString(com.rs.mobile.wportal.R.string.rt_coupon_use_time) + " " + item.getString("startDate") + "~" + item.getString("endDate"));

			viewHolder.text_money.setText(item.getString("dcAmount"));
			
			viewHolder.text_requre.setText(mContext.getString(com.rs.mobile.wportal.R.string.rt_coupon_use_msg_01) + item.getString("overAmount") + mContext.getString(com.rs.mobile.wportal.R.string.rt_coupon_use_msg_02));
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}
	
	private class ViewHolder {

		public ImageView check_box;
		
		public TextView coupon_name;
		
		public TextView coupon_time;
		
		public TextView text_money;
		
		public TextView text_requre;
		
	}

}
