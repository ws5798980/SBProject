package com.rs.mobile.wportal.adapter.dp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.R;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class DpbrandImageAdapter extends BaseAdapter{
    public DpbrandImageAdapter(JSONArray arr, Context context,int times) {
		super();
		this.arr = arr;
		this.context = context;
		this.times=times;
	}

	private JSONArray arr;
    private Context context;
    private int times;
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		if (convertView==null) {
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.dp_list_item_img,null);
			
		}
		WImageView img=(WImageView)convertView.findViewById(R.id.img);
		LinearLayout.LayoutParams parms = (LinearLayout.LayoutParams)img .getLayoutParams();
		parms.width = (int) (getwindowswidth((Activity)context) / times);
		img.setLayoutParams(parms);
		img.setAspectRatio((float) 0.95);
		try {
			JSONObject obj=arr.getJSONObject(position);
			ImageUtil.drawImageFromUri(obj.getString("image_url"), img);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return convertView;
	}
	public int getwindowswidth(Activity context) {

		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 窗口的宽度
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}
}
