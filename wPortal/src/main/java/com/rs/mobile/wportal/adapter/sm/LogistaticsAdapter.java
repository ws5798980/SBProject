
package com.rs.mobile.wportal.adapter.sm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LogistaticsAdapter extends BaseAdapter {

	private JSONArray arr;

	private Context context;

	private ImageView img_circle;

	private TextView text_time, text_detail;

	public LogistaticsAdapter(JSONArray arr, Context context) {
		super();
		this.arr = arr;
		this.context = context;
	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return arr.length();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		try {
			return arr.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_fm_exp_follow, parent, false);
		}
		img_circle = (ImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.img_circle);
		text_time = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_time);
		text_detail = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_detail);
		try {
			if (position == 0) {
				img_circle.setBackgroundResource(com.rs.mobile.wportal.R.drawable.yuan);
				text_detail.setTextColor(context.getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
				text_time.setTextColor(context.getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
			} else {
				img_circle.setBackgroundResource(com.rs.mobile.wportal.R.drawable.img_garydot);
				text_detail.setTextColor(context.getResources().getColor(com.rs.mobile.wportal.R.color.black));
				text_time.setTextColor(context.getResources().getColor(com.rs.mobile.wportal.R.color.hintblack));
			}
			text_time.setText(new JSONObject(getItem(position).toString()).get(C.KEY_JSON_FM_EXP_Date).toString());
			text_detail.setText(new JSONObject(getItem(position).toString()).get(C.KEY_JSON_FM_EXP_LOCATION).toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return convertView;
	}

}
