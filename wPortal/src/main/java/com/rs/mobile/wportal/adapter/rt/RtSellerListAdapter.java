package com.rs.mobile.wportal.adapter.rt;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.L;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.activity.rt.RtMainActivity;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RtSellerListAdapter extends BaseAdapter {
	
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private JSONArray arr;
	private RelativeLayout.LayoutParams params;
	
	public RtSellerListAdapter(Context context, JSONArray arr){
		this.mContext = context;
		this.arr = arr;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		params = new RelativeLayout.LayoutParams(RtMainActivity.displayWidth/4, RtMainActivity.displayWidth/4);
	}
	
	public JSONArray getListItems() {
		
		if (arr == null) arr = new JSONArray();
		
		return arr;
		
	}
	
	public void setListItems(JSONArray arr) {
		
		this.arr = arr;
		
	}
	
	@Override
	public int getCount() {
		return arr == null ? 0 : arr.length();
	}

	@Override
	public Object getItem(int position) {
		
		try {
		
			return arr == null ? null : arr.getJSONObject(position);
		
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
		
		SellerItemViewHolder sellerItemViewHolder;
		
		if(convertView == null){
			
			sellerItemViewHolder = new SellerItemViewHolder();
			convertView = mLayoutInflater.inflate(com.rs.mobile.wportal.R.layout.layout_rt_sellerlist_item, null);
			sellerItemViewHolder.thumbnail = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.iv_thumbnail);
			sellerItemViewHolder.name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_sellername);
			sellerItemViewHolder.rating = (RatingBar) convertView.findViewById(com.rs.mobile.wportal.R.id.rb_sellerrating);
			sellerItemViewHolder.price = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_price);
			sellerItemViewHolder.foodType = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_foodType);
			sellerItemViewHolder.distance = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_distance);
			sellerItemViewHolder.count = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_count);
			convertView.setTag(sellerItemViewHolder);
			
		} else {
			
			sellerItemViewHolder = (SellerItemViewHolder) convertView.getTag();
			
		}
		
		//Setting values
		JSONObject item = (JSONObject) getItem(position);
		
		try {
		
			ImageUtil.drawIamge(sellerItemViewHolder.thumbnail, Uri.parse(item.getString("shopThumImage")), params);
			sellerItemViewHolder.name.setText(item.getString("restaurantName"));
			
			String averageRate = item.getString("averageRate");
			if (averageRate != null && !averageRate.equals("")){
				sellerItemViewHolder.rating.setRating(Float.parseFloat(averageRate));
			}
			sellerItemViewHolder.price.setText(mContext.getString(com.rs.mobile.wportal.R.string.rmb) + " " + item.getString("averagePay") + mContext.getString(com.rs.mobile.wportal.R.string.rt_seller_list_01));
			sellerItemViewHolder.foodType.setText(item.getString("floor"));
			sellerItemViewHolder.distance.setText(item.getString("distance"));
			sellerItemViewHolder.count.setText(mContext.getString(com.rs.mobile.wportal.R.string.rt_seller_list_02) + " " + item.getString("salesCount"));
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return convertView;
	}
	
	private class SellerItemViewHolder{
		
		public WImageView thumbnail;
		public TextView name;
		public RatingBar rating;
		public TextView price;
		public TextView foodType;
		public TextView distance;
		public TextView count;
		
	}
	
}
