
package com.rs.mobile.wportal.adapter.dp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.image.ImageUtil;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DpViewPagerAdapter1 extends PagerAdapter {

	private Context context;

	private JSONArray arr;

	private int count;

	private float ratio;

	private String item_code;

	public DpViewPagerAdapter1(Context context, JSONObject json) {
		// TODO Auto-generated constructor stub
		this.context = context;
		try {
			this.arr = json.getJSONArray("images");
			this.count = arr.length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
	}

	public DpViewPagerAdapter1(Context context, JSONObject json, float ratio) {
		// TODO Auto-generated constructor stub
		this.context = context;
		try {
			this.arr = json.getJSONArray("images");
			this.count = arr.length();
			this.ratio = ratio;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			L.e(e);
		}
	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		View view = null;
		view = LayoutInflater.from(context).inflate(com.rs.mobile.wportal.R.layout.layout_cviewpager_item, null);
		// ImageView imageView = (ImageView)
		// view.findViewById(R.id.cviewpager_item_img);

		WImageView wImageView = (WImageView) view.findViewById(com.rs.mobile.wportal.R.id.cviewpager_item_freimg);
		WImageView wImageView2 = (WImageView) view.findViewById(com.rs.mobile.wportal.R.id.cviewpager_item_freimg1);
		wImageView.setVisibility(view.GONE);
		wImageView2.setVisibility(view.VISIBLE);
		if (ratio != 0) {
			wImageView2.setAspectRatio(ratio);

		}

		try {

			final JSONObject jsonObject = new JSONObject(arr.get(position).toString());
           
			ImageUtil.drawImageView1(context, wImageView2, jsonObject, C.KEY_JSON_URL, C.KEY_JSON_VERSION);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		container.addView(view);
		return view;

	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		// TODO Auto-generated method stub
		container.removeView((View) object);
	}

}
