package com.rs.mobile.wportal.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.wportal.view.MenuButtonView;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridListAdapter extends BaseAdapter{

	private int type = 0;
	
	public static final int TYPE_CATEGORY = 0;
	
	public static final int TYPE_SEARCH_HISTORY = 1;
	
	public static final int TYPE_SEARCH_RANKING = 2;
	
	public static final int TYPE_TYPE_TICKET_TIME = 3;
	
	public static final int TYPE_TYPE_TICKET_PRICE = 4;
	
	public static final int TYPE_LOCATION_CITY = 5;
	
	public static final int TYPE_RT_MENU = 6;
	
	private Object listItems;
	
	private Context context;
	
	private int selectedPosition = 0;
	
	public ArrayList<String> selectedPositions = new ArrayList<String>();
	
	public void setSelectedPositions(String position) {
		
		if (selectedPositions.contains(position)) {
			
			selectedPositions.remove(position);
			
		} else {
			
			selectedPositions.add(position);
			
		}
		
		notifyDataSetChanged();
		
	}
	
	public void setSelectedPosition(int position) {
		
		selectedPosition = position;
		
		notifyDataSetChanged();
		
	}

	public GridListAdapter(Context context, Object listItems, int type) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		
		this.listItems = listItems;
		
		this.type = type;

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		if (type == TYPE_SEARCH_HISTORY) {

			return ((String[])listItems).length;
					
		} else {
			
			return ((JSONArray)listItems).length();
			
		}
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		try {
		
			if (type == TYPE_SEARCH_HISTORY) {

				return ((String[])listItems)[position];
						
			} else {
				
				return ((JSONArray)listItems).get(position);
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		try {
		
			if (convertView == null) {

				if (type == TYPE_CATEGORY) {
				
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_kr_menu, parent, false);
				
				} else if (type == TYPE_SEARCH_HISTORY) {
					
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_kr_history, parent, false);
					
				} else if (type == TYPE_SEARCH_RANKING) {
					
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_kr_ranking, parent, false);
					
				} else if (type == TYPE_TYPE_TICKET_TIME || type == TYPE_TYPE_TICKET_PRICE) {
					
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_kr_concert, parent, false);
					
				} else if (type == TYPE_LOCATION_CITY) {
					
					convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_location, parent, false);
					
				} else if (type == TYPE_RT_MENU) {
					
					convertView = new MenuButtonView(parent.getContext());
					
				}
				
			}
			
			if (type == TYPE_CATEGORY) {
				
				TextView titleTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
				
				LinearLayout underline = (LinearLayout)convertView.findViewById(com.rs.mobile.wportal.R.id.under_line);
				
				JSONObject item = (JSONObject) getItem(position);
				
				underline.setVisibility(View.GONE);
				
				titleTextView.setText(item.getString(C.KEY_JSON_DISPLAY_NAME));
			
			} else if (type == TYPE_SEARCH_HISTORY) {
				
				TextView titleTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
				
				String item = (String) getItem(position);
				
				titleTextView.setText(item);
				
			} else if (type == TYPE_SEARCH_RANKING) {
				
				TextView titleTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.title_text_view);
				
				TextView countTextView = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.count_text_view);
				
				if (position == 0) {
					
					countTextView.setBackgroundColor(Color.parseColor("#f04343"));
					
				} else if (position == 1) {
					
					countTextView.setBackgroundColor(Color.parseColor("#fc8242"));
					
				} else if (position == 2) {
					
					countTextView.setBackgroundColor(Color.parseColor("#3cbdf1"));
					
				} else {
					
					countTextView.setBackgroundColor(Color.parseColor("#999999"));
					
				}
				
				JSONObject item = (JSONObject)getItem(position);
				
				titleTextView.setText(item.getString(C.KEY_JSON_TITLE));
				
				countTextView.setText(item.getString(C.KEY_JSON_UNIQUE_ID));
				
			} else if (type == TYPE_TYPE_TICKET_TIME) {

				TextView text_view_001 = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.text_view_001);
				
				TextView text_view_002 = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.text_view_002);
				
				if (position == selectedPosition) {
				
					convertView.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_yellow_s_round);
				
					text_view_001.setTextColor(Color.parseColor("#ffffff"));
					
					text_view_002.setTextColor(Color.parseColor("#ffffff"));
					
				} else {
					
					convertView.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_gray_round_empty);
					
					text_view_001.setTextColor(Color.parseColor("#666666"));
					
					text_view_002.setTextColor(Color.parseColor("#666666"));
					
				}
				
				
				JSONObject item = (JSONObject)getItem(position);
				
				text_view_001.setText(item.getString("date"));
				
				text_view_002.setText(item.getString("time"));
				
			} else if (type == TYPE_TYPE_TICKET_PRICE) {
				
				TextView text_view_001 = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.text_view_001);
				
				TextView text_view_002 = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.text_view_002);

				if (selectedPositions.contains(position + "")) {
					
					convertView.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_yellow_s_round);
				
					text_view_001.setTextColor(Color.parseColor("#ffffff"));
					
					text_view_002.setTextColor(Color.parseColor("#ffffff"));
					
				} else {
					
					convertView.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_gray_round_empty);
					
					text_view_001.setTextColor(Color.parseColor("#666666"));
					
					text_view_002.setTextColor(Color.parseColor("#666666"));
					
				}

				JSONObject item = (JSONObject)getItem(position);
				
				text_view_001.setText(item.getString("name"));
				
				text_view_002.setText(item.getString("price"));
				
			} else if (type == TYPE_LOCATION_CITY) {

				TextView text_view = (TextView)convertView.findViewById(com.rs.mobile.wportal.R.id.text_view);

				JSONObject item = (JSONObject)getItem(position);
				
				text_view.setText(item.getString("cityName"));
				
			} else if (type == TYPE_RT_MENU) {

				JSONObject item = (JSONObject)getItem(position);
				
				((MenuButtonView)convertView).setIcon(Uri.parse(item.getString(C.KEY_JSON_IMAGE_URL)));
				
				((MenuButtonView)convertView).setText(item.getString(C.KEY_JSON_DISPLAY_NAME));
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}

}
