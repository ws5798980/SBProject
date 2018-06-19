package com.rs.mobile.common.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.L;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridListAdapter extends BaseAdapter{

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

	public GridListAdapter(Context context, Object listItems) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		
		this.listItems = listItems;

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
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		try {
		
			if (convertView == null) {

				convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.common.R.layout.list_item_location, parent, false);
				
			}
			
			TextView text_view = (TextView)convertView.findViewById(com.rs.mobile.common.R.id.text_view);

			JSONObject item = (JSONObject)getItem(position);
			
			text_view.setText(item.getString("cityName"));

		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}

}
