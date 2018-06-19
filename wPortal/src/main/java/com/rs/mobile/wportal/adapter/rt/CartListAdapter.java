package com.rs.mobile.wportal.adapter.rt;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.L;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CartListAdapter extends BaseAdapter {
	
	private MenuChangeListener menuChangeListener;
	
	private Context mContext;
	
	private LayoutInflater mLayoutInflater;
	
	private JSONArray arr;
	
	public CartListAdapter(Context context, JSONArray arr, MenuChangeListener menuChangeListener){
		
		this.mContext = context;
		
		this.arr = arr;
		
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.menuChangeListener = menuChangeListener;
		
	}
	
	public JSONArray getListItems() {
		
		if (arr == null) arr = new JSONArray();
		
		return arr;
		
	}
	
	public void setListItems(JSONArray arr) {
		
		this.arr = arr;
		
	}
	
	public void removeAllCart() {
		
		try {
			
			for (int i = 0; i < arr.length(); i++) {
			
				JSONObject item = arr.getJSONObject(i);
				
				item.put("count", 0);
				
				arr.put(i, item);
				
			}
			
			notifyDataSetChanged();
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
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
			
			convertView = mLayoutInflater.inflate(com.rs.mobile.wportal.R.layout.layout_rt_cart_list_item, null);

			viewHolder.name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.name);
			
			viewHolder.price = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.price);
			
			viewHolder.minus_btn = (LinearLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.minus_btn);
			
			viewHolder.count_text_view = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.count_text_view);
			
			viewHolder.plus_btn = (LinearLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.plus_btn);

			convertView.setTag(viewHolder);
			
		} else {
			
			viewHolder = (ViewHolder) convertView.getTag();
			
		}
		
		//Setting values
		final JSONObject item = (JSONObject) getItem(position);
		
		viewHolder.minus_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {

					int count = item.getInt("count");
					
					if (count > 0) {
						
						count = count - 1;
						
						item.put("count", count);
						
						arr.put(position, item);
						
						menuChangeListener.onChange(false);
						
						notifyDataSetChanged();
						
					} 

				} catch (Exception e) {
					
					L.e(e);
					
				}
				
			}
		});
		
		viewHolder.plus_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {

					int count = item.getInt("count") + 1;

					item.put("count", count);
					
					arr.put(position, item);
					
					menuChangeListener.onChange(true);
					
					notifyDataSetChanged();
				
				} catch (Exception e) {
					
					L.e(e);
					
				}
				
			}
		});
		
		try {

			viewHolder.name.setText(item.getString("itemName"));
			
			viewHolder.price.setText(item.getString("itemAmount"));

			viewHolder.count_text_view.setText("" + item.getInt("count"));
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}
	
	private class ViewHolder {

		public TextView name;
		
		public TextView price;
		
		public LinearLayout minus_btn;
		
		public TextView count_text_view;
		
		public LinearLayout plus_btn;
		
	}
	
	public interface MenuChangeListener {
		
		void onChange(boolean add);
		
	}
	
}
