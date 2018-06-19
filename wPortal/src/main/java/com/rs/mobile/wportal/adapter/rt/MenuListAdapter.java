package com.rs.mobile.wportal.adapter.rt;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.activity.rt.RtMainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter {
	
	private MenuChangeListener menuChangeListener;
	
	private Context mContext;
	
	private LayoutInflater mLayoutInflater;
	
	private JSONArray arr;
	
//	private int[] countList;
	
//	private ArrayList<Integer> countList;
	
	private RelativeLayout.LayoutParams params;
	
	public MenuListAdapter(Context context, JSONArray arr, MenuChangeListener menuChangeListener){
		
		this.mContext = context;
		
		this.arr = arr;
		
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.params = new RelativeLayout.LayoutParams(RtMainActivity.displayWidth/6, RtMainActivity.displayWidth/6);
		
		this.menuChangeListener = menuChangeListener;
		
//		this.countList = new ArrayList<>();
//		
//		for (int i = 0; i < arr.length(); i++) {
//			
//			countList.add(0);
//			
//		}
		
	}
	
	public JSONArray getListItems() {
		
		if (arr == null) arr = new JSONArray();
		
		return arr;
		
	}
	
	public void setListItems(JSONArray arr) {
		
		this.arr = arr;
		
	}
	
//	public ArrayList<Integer> getCountList() {
//		
//		return countList;
//		
//	}
//	
//	public void setCountList(ArrayList<Integer> countList) {
//		
//		this.countList = countList;
//		
//		notifyDataSetChanged();
//		
//	}
	
	public void removeAllCart() {
		
//		this.countList = new ArrayList<>(arr.length());
//		
//		for (int i = 0; i < arr.length(); i++) {
//			
//			countList.add(0);
//			
//		}
		
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
		
		
		try {
			
			final ViewHolder viewHolder;
			
			if(convertView == null){
				
				viewHolder = new ViewHolder();
				
				convertView = mLayoutInflater.inflate(com.rs.mobile.wportal.R.layout.layout_rt_menulist_item, null);
	
				viewHolder.typeTextView = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.type_text_view);
	
				viewHolder.contentArea = (RelativeLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.content_area);
	
				viewHolder.thumbnail = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.thumbnail);
				
				viewHolder.name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.name);
				
				viewHolder.price = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.price);
				
				viewHolder.minus_btn = (LinearLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.minus_btn);
				
				viewHolder.count_text_view = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.count_text_view);
				
				viewHolder.plus_btn = (LinearLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.plus_btn);
				
	
				convertView.setTag(viewHolder);
				
			} else {
				
				viewHolder = (ViewHolder) convertView.getTag();
				
			}
			
			viewHolder.thumbnail.setRounding(true);
			
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
							
						}

						menuChangeListener.onChange(false);
						
						notifyDataSetChanged();
					
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
						
						viewHolder.count_text_view.setVisibility(View.VISIBLE);
						
						viewHolder.minus_btn.setVisibility(View.VISIBLE);
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			try {
	
				if (item.getString("type").equals("menu")) {
					
					viewHolder.typeTextView.setVisibility(View.GONE);
					
					viewHolder.contentArea.setVisibility(View.VISIBLE);
					
					ImageUtil.drawImageViewBuFullUrl(mContext, viewHolder.thumbnail, item, "imgUrl", "ver", params);
					
					viewHolder.name.setText(item.getString("itemName"));
					
					viewHolder.price.setText(item.getString("itemAmount"));
					
					viewHolder.count_text_view.setText("" + item.getInt("count"));
					
					if (item.getInt("count") < 1) {
						
						viewHolder.count_text_view.setVisibility(View.GONE);
						
						viewHolder.minus_btn.setVisibility(View.GONE);
						
					} else {
						
						viewHolder.count_text_view.setVisibility(View.VISIBLE);
						
						viewHolder.minus_btn.setVisibility(View.VISIBLE);
						
					}
					
				} else {
					
					viewHolder.typeTextView.setVisibility(View.VISIBLE);
					
					viewHolder.contentArea.setVisibility(View.GONE);
				
					viewHolder.typeTextView.setText(item.getString("groupName"));
					
				}
				
			} catch (Exception e) {
				
				L.e(e);
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}
	
	private class ViewHolder {
		
		public TextView typeTextView;
		
		public RelativeLayout contentArea;
		
		public WImageView thumbnail;
		
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
