package com.rs.mobile.wportal.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.wportal.R;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KrSelectSessionAdapter extends BaseAdapter{
    private JSONArray arr;
    private Context context;
    public KrSelectSessionAdapter(JSONArray arr, Context context) {
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
			return arr.get(position).toString();
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
		
		if (convertView==null) {
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_select_session, parent, false);
		}
		TextView text_start=(TextView)convertView.findViewById(R.id.text_start);
		TextView text_end=(TextView)convertView.findViewById(R.id.text_end);
		TextView text_hallType=(TextView)convertView.findViewById(R.id.text_hallType);
		TextView text_hallName=(TextView)convertView.findViewById(R.id.text_hallName);
		TextView text_count=(TextView)convertView.findViewById(R.id.text_count);
		TextView text_purchase=(TextView)convertView.findViewById(R.id.text_purchase);
		TextView text_discount=(TextView)convertView.findViewById(R.id.text_discount);
		try {
			JSONObject object=arr.getJSONObject(position);
			text_start.setText(object.getString("startDate"));
			text_end.setText(object.getString("endDate"));
			text_hallName.setText(object.getString("hallName"));
			text_hallType.setText(object.getString("hallType"));
			text_count.setText(object.getString("freeSeats"));
			text_purchase.setText(context.getResources().getString(R.string.rmb)+object.getString("originalPrice"));
			text_purchase.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
			text_discount.setText(context.getResources().getString(R.string.rmb)+object.getString("discountPrice"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return convertView;
	}

}
