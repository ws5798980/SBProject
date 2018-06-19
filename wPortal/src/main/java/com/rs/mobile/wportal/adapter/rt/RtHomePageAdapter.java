package com.rs.mobile.wportal.adapter.rt;

import java.util.List;

import org.json.JSONArray;

import com.rs.mobile.common.T;
import com.rs.mobile.wportal.biz.rt.SellerType;
import com.willli.gridpager.GridViewPager;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.AbstractPagerListAdapter;
import com.rs.mobile.wportal.biz.rt.SellerItem;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class RtHomePageAdapter extends BaseAdapter {

	private Context mContext;
	private List<Object> mDates;
	private static final int TYPE_SELLERTYPE = 0;
	private static final int TYPE_AD = 1;
	private static final int TYPE_SELLER = 2;

	private LayoutInflater mLayoutInflater;

	public RtHomePageAdapter(Context context, List<Object> dates) {
		this.mContext = context;
		this.mDates = dates;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mDates == null ? 0 : mDates.size();
	}

	@Override
	public Object getItem(int position) {
		return mDates == null ? 0 : mDates.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public int getItemViewType(int position) {
		// GridViewPager
		if (position == 0) {
			return TYPE_SELLERTYPE;
		}
		// ViewPager
		else if (position == 1) {
			return TYPE_AD;
		}
		// ListItem
		else {
			return TYPE_SELLER;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		switch (type) {
		case TYPE_SELLERTYPE:
			SellerTypeViewHolder sellerTypeViewHolder;
			if (convertView == null) {
				sellerTypeViewHolder = new SellerTypeViewHolder();
				convertView = mLayoutInflater.inflate(R.layout.layout_rt_sellertype_grid, null);
				sellerTypeViewHolder.gridViewPager = (GridViewPager) convertView.findViewById(R.id.gvp_sellertype);
				convertView.setTag(sellerTypeViewHolder);
			} else {
				sellerTypeViewHolder = (SellerTypeViewHolder) convertView.getTag();
			}
			sellerTypeViewHolder.gridViewPager
					.setAdapter(new SellerTypeGridPagerAdapter((List<SellerType>) getItem(position)));
			return convertView;

		case TYPE_AD:
			SellerADViewHolder sellerADViewHolder;
			if (convertView == null) {
				sellerADViewHolder = new SellerADViewHolder();
				convertView = mLayoutInflater.inflate(R.layout.layout_rt_sellerad_pager, null);
				sellerADViewHolder.viewPager = (ViewPager) convertView.findViewById(R.id.vp_sellerad);
				convertView.setTag(sellerADViewHolder);
			} else {
				sellerADViewHolder = (SellerADViewHolder) convertView.getTag();
			}
			// sellerADViewHolder.viewPager.setAdapter(
			// new SellerADViewPagerAdapter((List<SellerAD>)
			// getItem(position)));
			return convertView;

		case TYPE_SELLER:
			SellerItemViewHolder sellerItemViewHolder;
			if (convertView == null) {
				sellerItemViewHolder = new SellerItemViewHolder();
				convertView = mLayoutInflater.inflate(R.layout.layout_rt_sellerlist_item, null);
				sellerItemViewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.iv_thumbnail);
				sellerItemViewHolder.name = (TextView) convertView.findViewById(R.id.tv_sellername);
				sellerItemViewHolder.rating = (RatingBar) convertView.findViewById(R.id.rb_sellerrating);
				sellerItemViewHolder.price = (TextView) convertView.findViewById(R.id.tv_price);
				sellerItemViewHolder.foodType = (TextView) convertView.findViewById(R.id.tv_foodType);
				sellerItemViewHolder.distance = (TextView) convertView.findViewById(R.id.tv_distance);
				sellerItemViewHolder.count = (TextView) convertView.findViewById(R.id.tv_count);
				convertView.setTag(sellerItemViewHolder);
			} else {
				sellerItemViewHolder = (SellerItemViewHolder) convertView.getTag();
			}
			// Setting values
			SellerItem item = (SellerItem) getItem(position);
			sellerItemViewHolder.thumbnail.setImageResource(R.drawable.ic_launcher);
			sellerItemViewHolder.name.setText("必胜客（友谊店）");
			sellerItemViewHolder.rating.setRating(4);
			sellerItemViewHolder.price.setText("¥60/人");
			sellerItemViewHolder.foodType.setText("干锅");
			sellerItemViewHolder.distance.setText("203m");
			sellerItemViewHolder.count.setText("已接待:1520");
			return convertView;

		default:
			break;
		}
		return convertView;
	}

	private class SellerTypeViewHolder {
		public GridViewPager gridViewPager;
	}

	private class SellerTypeGridPagerAdapter extends BaseAdapter {

		private List<SellerType> mSellerTypes;

		public SellerTypeGridPagerAdapter(List<SellerType> sellerTypes) {
			this.mSellerTypes = sellerTypes;
		}

		@Override
		public int getCount() {
			return mSellerTypes == null ? 0 : mSellerTypes.size();
		}

		@Override
		public Object getItem(int position) {
			return mSellerTypes.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(R.layout.layout_rt_sellertype_grid_item, null);
			}
			ImageView iv_sellertype = (ImageView) convertView.findViewById(R.id.iv_sellertype);
			TextView tv_sellertype = (TextView) convertView.findViewById(R.id.tv_sellertype);
			iv_sellertype.setImageResource(R.drawable.icon_zz);
			tv_sellertype.setText(mContext.getString(R.string.rt_text064));
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					T.showToast(mContext, "seller type clicked");
				}
			});
			return convertView;
		}

	}

	private class SellerADViewHolder {
		public ViewPager viewPager;
	}

	private class SellerADViewPagerAdapter extends AbstractPagerListAdapter<JSONArray> {

		public SellerADViewPagerAdapter(JSONArray data) {
			super(data);
		}

		@Override
		public View newView(int position) {
			LinearLayout linearLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.layout_rt_sellerad_pager_item,
					null);
			ImageView iv_sellerad = (ImageView) linearLayout.findViewById(R.id.iv_sellerad);
			iv_sellerad.setImageResource(R.drawable.banner_sellerad);
			linearLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					T.showToast(mContext, "banner clicked");
				}
			});
			return linearLayout;
		}

	}

	private class SellerItemViewHolder {

		public ImageView thumbnail;
		public TextView name;
		public RatingBar rating;
		public TextView price;
		public TextView foodType;
		public TextView distance;
		public TextView count;

	}

}
