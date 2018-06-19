package com.rs.mobile.wportal.adapter.rt;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.L;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuCategoryListAdapter extends BaseAdapter {
	
	private Context mContext;
	
	private LayoutInflater mLayoutInflater;
	
	private JSONArray arr;
	
	private int selectedPosition = 0;
	
	public void setSelectedPosition(int selectedPosition) {
		
		if (selectedPosition != this.selectedPosition) {
			
			this.selectedPosition = selectedPosition;
			
			notifyDataSetChanged();
			
		}
		
	}
	
	public MenuCategoryListAdapter(Context context, JSONArray arr){
		
		this.mContext = context;
		
		this.arr = arr;
		
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		
		if(convertView == null){
			
			viewHolder = new ViewHolder();
			
			convertView = mLayoutInflater.inflate(com.rs.mobile.wportal.R.layout.layout_rt_menu_category_list_item, null);

			viewHolder.titleTextView = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.title_text_view);

			convertView.setTag(viewHolder);
			
		} else {
			
			viewHolder = (ViewHolder) convertView.getTag();
			
		}
		
		//Setting values
		JSONObject item = (JSONObject) getItem(position);
		
		try {

			viewHolder.titleTextView.setText(item.getString("groupName"));
			
			
			if (position == selectedPosition) {
				
				convertView.setBackgroundColor(Color.parseColor("#ffffff"));
				
			} else {
				
				convertView.setBackgroundColor(Color.parseColor("#f2f2f2"));
				
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}
	
	private class ViewHolder {
		
		public TextView titleTextView;
		
	}
	
}
