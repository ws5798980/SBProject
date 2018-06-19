package com.rs.mobile.wportal.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.view.WImageView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VpAdapter extends PagerAdapter{
//    private List<View> viewList;
    private Context context;
    private JSONArray arr;
    
	
	
	public VpAdapter(Context context, JSONArray arr) {
		super();
		this.context = context;
		this.arr = arr;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.length();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		
		container.removeView((View) object);
		
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View view=null;
		view=LayoutInflater.from(context).inflate(com.rs.mobile.wportal.R.layout.layout_intro_image_view, null);
		
		WImageView wImageView2 = (WImageView) view.findViewById(com.rs.mobile.wportal.R.id.image_view);
		
		
try {
			
			
			final JSONObject jsonObject = new JSONObject(arr.get(position).toString());
			ImageUtil.drawImageFromUri(jsonObject.getString("imgUrl"), wImageView2);
			
			
			
			
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      container.addView(view);
		return view;
		
	}
}
