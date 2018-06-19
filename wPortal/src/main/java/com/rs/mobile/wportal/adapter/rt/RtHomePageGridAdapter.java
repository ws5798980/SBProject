package com.rs.mobile.wportal.adapter.rt;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.rt.RtSellerListActivity;
import com.rs.mobile.wportal.biz.rt.SellerTypeCondition;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class RtHomePageGridAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private JSONArray items;
	private int width;
	private LayoutParams params;

	public RtHomePageGridAdapter(Context context ,JSONArray items, int width) {
		this.mContext = context;
		this.items = items;
		this.width = width;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		params = new LayoutParams(width, width);
	}

	@Override
	public int getCount() {
		return items == null ? 0 : items.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return items.getJSONObject(position);
		} catch (Exception e) {
			L.e(e);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(
					R.layout.layout_rt_sellertype_grid_item, null);
		}
		WImageView iv_sellertype = (WImageView) convertView
				.findViewById(R.id.iv_sellertype);
		TextView tv_sellertype = (TextView) convertView
				.findViewById(R.id.tv_sellertype);
		
		final JSONObject item = (JSONObject)(getItem(position));
		
		String subCode = null;
		try {
			ImageUtil.drawIamge(iv_sellertype, Uri.parse(item.getString("imgUrl")), params);
			tv_sellertype.setText(item.getString("displayName"));
			subCode = item.getString("subCode");
		} catch (Exception e) {
			L.e(e);
		}
		final String finalSubCode = subCode;
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				try {
				
					Intent i = new Intent(mContext , RtSellerListActivity.class);
					SellerTypeCondition sellerTypeCondition = new SellerTypeCondition();
					sellerTypeCondition.setDivCode(Integer.parseInt(C.DIV_CODE));
					sellerTypeCondition.setCategoryCode(finalSubCode == null ? "" : finalSubCode);
					
					if (item.has("displayName"))
						sellerTypeCondition.setDisplayName(item.getString("displayName"));
					
					i.putExtra(C.EXTRA_KEY_SELLERTYPE_CONDITION, sellerTypeCondition);
					mContext.startActivity(i);
				
				} catch (Exception e) {
					
					L.e(e);
					
				}
			}
		});
		return convertView;
	}

}
