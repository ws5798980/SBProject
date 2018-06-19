package com.rs.mobile.wportal.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WListAdapter extends BaseAdapter {
	
	private int type = 0;
	
	public static final int TYPE_AFFILIATE = 0;
	
	public static final int TYPE_BOROUGH = 1;
	
	private Object listItems;
	
	private Context context;
	
	private int selectedPosition = 0;

	public void setSelectedPosition(int position) {
		
		selectedPosition = position;
		
		notifyDataSetChanged();
		
	}

	public WListAdapter(Context context, Object listItems, int type) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		
		this.listItems = listItems;
		
		this.type = type;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return ((JSONArray)listItems).length();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		try {
		
			return ((JSONArray)listItems).get(position);

		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		try {
			
			if (convertView == null) {

				if (type == TYPE_AFFILIATE) {
				
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_affiliate, parent, false);
				
				} else if (type == TYPE_BOROUGH) {
					
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_borough, parent, false);
					
				}  
				
			}
			
			if (type == TYPE_AFFILIATE) {
				
				JSONObject item = (JSONObject)getItem(position);
				
				TextView textView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.text_view);

				textView.setText(item.getString("divName"));
			
				if (item.getString("divCode").equals(C.DIV_CODE)) {
					
					textView.setTextColor(Color.parseColor("#f23a46"));
					
				} else {
					
					textView.setTextColor(Color.parseColor("#333333"));
					
				}
				
			} else if (type == TYPE_BOROUGH) {
				
				JSONObject item = (JSONObject)getItem(position);
				
				TextView textView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.text_view);

				textView.setText(item.getString("areaName"));

				if (position == selectedPosition) {
					
					textView.setBackgroundColor(Color.parseColor("#f2f2f2"));
					
				} else {
					
					textView.setBackgroundColor(Color.parseColor("#ffffff"));
					
				}

			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}

}