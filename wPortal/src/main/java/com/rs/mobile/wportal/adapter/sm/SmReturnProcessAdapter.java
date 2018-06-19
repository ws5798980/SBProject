
package com.rs.mobile.wportal.adapter.sm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.L;
import com.rs.mobile.wportal.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SmReturnProcessAdapter extends BaseAdapter {

	public SmReturnProcessAdapter(JSONArray arr, Context context) {
		super();
		this.arr = arr;
		this.context = context;
	}

	private JSONArray arr;

	private Context context;

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

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sm_return_detail, parent,
					false);
		}
		LinearLayout line_bg = (LinearLayout) convertView.findViewById(R.id.line_bg);
		TextView text_time = (TextView) convertView.findViewById(R.id.text_time);
		TextView text_status = (TextView) convertView.findViewById(R.id.text_status);
		View line_view = (View) convertView.findViewById(R.id.line_view);
		TextView text_detail = (TextView) convertView.findViewById(R.id.text_detail);
		try {
			JSONObject js = arr.getJSONObject(position);
			String operateType = js.get("OperateType").toString();
			line_view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
			text_time.setText(js.get("OperateTime").toString());
			text_status.setText(js.get("StatusMsg").toString());
			text_detail.setText(js.getString("OperateContent"));
			if (operateType.equals("1")) {
				line_bg.setBackground(ContextCompat.getDrawable(context, R.drawable.ajbg));
				text_detail.setTextColor(ContextCompat.getColor(context, R.color.white));
				text_status.setTextColor(ContextCompat.getColor(context, R.color.white));

			} else if (operateType.equals("2")) {
				line_bg.setBackground(ContextCompat.getDrawable(context, R.drawable.mabg));
				text_detail.setTextColor(ContextCompat.getColor(context, R.color.black));
				text_status.setTextColor(ContextCompat.getColor(context, R.color.black));
			} else {
				line_bg.setBackground(ContextCompat.getDrawable(context, R.drawable.hjbg));
				text_detail.setTextColor(ContextCompat.getColor(context, R.color.white));
				text_status.setTextColor(ContextCompat.getColor(context, R.color.white));
			}
		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}

		return convertView;
	}

}
