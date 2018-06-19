
package com.rs.mobile.common.adapter;

import java.util.ArrayList;
import java.util.Map;

import com.rs.mobile.common.C;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PointsAdapter extends BaseAdapter {

	private ArrayList<Map<String, Object>> listData;
	
	//적립
	private int P_COLOR = Color.parseColor("#f33a48");
	
	//사용
//	private int M_COLOR = Color.parseColor("#2e48d4");
	private int M_COLOR = Color.parseColor("#02a9ef");
	
	//0
	private int Z_COLOR = Color.parseColor("#333333");

	public PointsAdapter(ArrayList<Map<String, Object>> listData) {
		super();
		this.listData = listData;
	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.common.R.layout.list_item_point, parent, false);

		}
		TextView tv_content = (TextView) convertView.findViewById(com.rs.mobile.common.R.id.tv_content);
		TextView tv_time = (TextView) convertView.findViewById(com.rs.mobile.common.R.id.tv_time);
		TextView tv_point = (TextView) convertView.findViewById(com.rs.mobile.common.R.id.tv_point);
		tv_content.setText(listData.get(position).get(C.KEY_JSON_FM_POINT_CONTENT).toString());
		tv_time.setText(listData.get(position).get(C.KEY_JSON_FM_POINT_TIME).toString());
		
		String point = listData.get(position).get(C.KEY_JSON_FM_POINT_NUMBER).toString();
		
		tv_point.setText(point);
		
		if (point != null && point.contains("+")) {
			
			tv_point.setTextColor(P_COLOR);
			
		} else if (point != null && point.contains("-")) {
			
			tv_point.setTextColor(M_COLOR);
			
		} else {
			
			tv_point.setTextColor(Z_COLOR);
			
		}
		
		return convertView;
	}

}
