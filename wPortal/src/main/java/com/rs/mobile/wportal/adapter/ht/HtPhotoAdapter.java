package com.rs.mobile.wportal.adapter.ht;

import java.util.List;

import com.rs.mobile.common.activity.ShowImageActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.biz.ht.HotelPhoto;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class HtPhotoAdapter extends BaseAdapter {
	private List<HotelPhoto> data;
	private Context context;
	private float asp;

	public HtPhotoAdapter(Context context, int times, List<HotelPhoto> data, float asp) {
		super();

		this.context = context;
		this.times = times;
		this.data = data;
		this.asp = asp;
	}

	private int times;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.ht_list_item_album_photo, null);

		}
		WImageView img = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.img);
		LinearLayout.LayoutParams parms = (LinearLayout.LayoutParams) img.getLayoutParams();
		parms.width = (int) (getwindowswidth((Activity) context) / times);
		img.setLayoutParams(parms);
		img.setAspectRatio(asp);
		HotelPhoto h = data.get(position);
		ImageUtil.drawImageFromUri(h.getImgurl(), img);
		final String[] imgs = new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			imgs[i] = data.get(i).getImgurl();

		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putStringArray("images", imgs);
				bundle.putInt("position", position);
				PageUtil.jumpTo(context, ShowImageActivity.class, bundle);
			}
		});

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
