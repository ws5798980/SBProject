package com.rs.mobile.wportal.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JSONArrayListAdapter extends BaseAdapter {
	
	public static final int TYPE_SINGLE_CHOICE_NO_RADIO_BUTTON = 0;
	
	private JSONArray arr;
	
	private int type = TYPE_SINGLE_CHOICE_NO_RADIO_BUTTON;
	
	public JSONArrayListAdapter(int type, JSONArray arr) {
		
		this.type = type;
		
		this.arr = arr;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.length();
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		try {
		
			return arr.get(position);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return null;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		try {
			
			if (convertView == null) {
				
				Context context = parent.getContext();
				
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				if (type == TYPE_SINGLE_CHOICE_NO_RADIO_BUTTON) {
					
					convertView = inflater.inflate(com.rs.mobile.wportal.R.layout.list_item_single_choice_no_radio_button, parent, false);
					
				} else {
					
					convertView = inflater.inflate(com.rs.mobile.wportal.R.layout.list_item_single_choice_no_radio_button, parent, false);
					
				}

			}
			
			JSONObject item = new JSONObject(getItem(position).toString());
			
			if (type == TYPE_SINGLE_CHOICE_NO_RADIO_BUTTON) {
				
				TextView textVIew = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.text_view);
				
				textVIew.setText(item.getString(C.KEY_JSON_NAME));
				
			} else {
				
				TextView textVIew = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.text_view);
				
				textVIew.setText(item.getString(C.KEY_JSON_NAME));
				
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}
	
}
