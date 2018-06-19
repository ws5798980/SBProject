package com.rs.mobile.wportal.adapter.rt;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.L;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.activity.rt.RtSellerDetailActivity;
import com.rs.mobile.wportal.adapter.AbstractPagerListAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class RtHomePageADAdapter extends AbstractPagerListAdapter<JSONArray> {

	private int height;
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private LayoutParams params;

	public RtHomePageADAdapter(Context context, JSONArray data, int height) {
		super(data);
		this.mContext = context;
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
	}

	@Override
	public View newView(int position) {
		
		LinearLayout linearLayout = (LinearLayout) mLayoutInflater.inflate(com.rs.mobile.wportal.R.layout.layout_rt_sellerad_pager_item, null);
		
		try {
		
			WImageView iv_sellerad = (WImageView) linearLayout.findViewById(com.rs.mobile.wportal.R.id.iv_sellerad);
	
			JSONObject item = mData.getJSONObject(position);
			
			ImageUtil.drawIamge(iv_sellerad, Uri.parse(item.getString("imgUrl")), params);
			
			iv_sellerad.setRounding(false);
			
			final JSONObject finalItem = item;
			
			if(finalItem != null && !TextUtils.isEmpty(finalItem.getString("restaurantCode"))){
				final String restaurantCode = finalItem.getString("restaurantCode");
				linearLayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent sellerDetailIntent = new Intent(mContext , RtSellerDetailActivity.class);
						sellerDetailIntent.putExtra("restaurantCode", restaurantCode);
						mContext.startActivity(sellerDetailIntent);
					}
				});
			}
		} catch (Exception e) {
			L.e(e);
		}
		return linearLayout;
	}
}