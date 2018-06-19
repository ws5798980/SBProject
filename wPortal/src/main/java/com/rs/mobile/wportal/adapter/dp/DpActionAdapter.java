package com.rs.mobile.wportal.adapter.dp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.activity.dp.DpActionDetailActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class DpActionAdapter extends BaseAdapter {
	public DpActionAdapter(JSONArray arr, Context context) {
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
			return arr.getJSONObject(position);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		DpActionViewHolder dpActionViewHolder = null;
		if (convertView == null) {
			dpActionViewHolder = new DpActionViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.dp_list_item_action, parent, false);
			dpActionViewHolder.img = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.img);
			convertView.setTag(dpActionViewHolder);

		} else {
			dpActionViewHolder = (DpActionViewHolder) convertView.getTag();
		}
		try {
			final JSONObject object = arr.getJSONObject(position);
			final String activity_id = object.getString("activity_id");
			ImageUtil.drawImageFromUri(object.getString("image_url"), dpActionViewHolder.img);
			dpActionViewHolder.img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString("activity_Id", activity_id);
					PageUtil.jumpTo(context, DpActionDetailActivity.class, bundle);
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}

	public static class DpActionViewHolder {
		public WImageView img;
	}

}
