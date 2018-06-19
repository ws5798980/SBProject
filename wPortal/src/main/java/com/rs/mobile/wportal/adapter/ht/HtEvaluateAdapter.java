package com.rs.mobile.wportal.adapter.ht;

import java.util.List;

import com.rs.mobile.wportal.biz.ht.HotelEvaluate;
import com.rs.mobile.common.util.UiUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

public class HtEvaluateAdapter extends BaseAdapter {
	private Drawable d1, d2;

	public HtEvaluateAdapter(List<HotelEvaluate> data, Context context) {
		super();
		this.data = data;
		this.context = context;
	}

	private List<HotelEvaluate> data;
	private Context context;

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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		d1 = ContextCompat.getDrawable(context, com.rs.mobile.wportal.R.drawable.ht_icon_viewreply_01);
		d1.setBounds(0, 0, d1.getMinimumWidth(), d1.getMinimumHeight());
		d2 = ContextCompat.getDrawable(context, com.rs.mobile.wportal.R.drawable.ht_icon_viewreply_02);
		d2.setBounds(0, 0, d2.getMinimumWidth(), d2.getMinimumHeight());
		final HeViewHolder vh;
		if (convertView == null) {
			vh = new HeViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.ht_list_item_evaluate, parent,
					false);
			vh.gv = (GridView) convertView.findViewById(com.rs.mobile.wportal.R.id.gv);
			vh.ratingbar = (RatingBar) convertView.findViewById(com.rs.mobile.wportal.R.id.ratingbar);
			vh.text_content = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_content);
			vh.text_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
			vh.text_reply = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_reply);
			vh.text_reply_content = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_reply_content);
			vh.text_time = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_time);
			vh.text_type = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_type);
			convertView.setTag(vh);
		} else {
			vh = (HeViewHolder) convertView.getTag();
		}
		HotelEvaluate h = data.get(position);
		if (h.getImgurl().size() > 0) {
			vh.gv.setVisibility(View.VISIBLE);
			HtPhotoAdapter adater = new HtPhotoAdapter(context, 3, h.getImgurl(), 1);
			vh.gv.setAdapter(adater);
			UiUtil.setGridViewHeight(vh.gv, 3);
		} else {
			vh.gv.setVisibility(View.GONE);
		}
		vh.ratingbar.setRating(h.getTotal_score());
		vh.text_content.setText(h.getContext());
		vh.text_name.setText(h.getNick_name());
		if (h.getParent_rated().equals("") || h.getParent_rated() == null) {
			vh.text_reply.setVisibility(View.GONE);
			vh.text_reply_content.setVisibility(View.GONE);
		} else {
			vh.text_reply.setVisibility(View.VISIBLE);
			// vh.text_reply_content.setVisibility(View.VISIBLE);
			vh.text_reply_content.setText(h.getParent_rated());
		}
		vh.text_reply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (vh.text_reply_content.getVisibility() == 0) {
					vh.text_reply.setCompoundDrawables(null, null, d1, null);
					vh.text_reply_content.setVisibility(View.GONE);
				} else {
					vh.text_reply.setCompoundDrawables(null, null, d2, null);
					vh.text_reply_content.setVisibility(View.VISIBLE);
				}

			}
		});
		vh.text_time.setText(h.getRatedtime());
		vh.text_type.setText(h.getRoomtypename());

		return convertView;
	}

	static class HeViewHolder {
		TextView text_name;
		RatingBar ratingbar;
		TextView text_time, text_content, text_type, text_reply, text_reply_content;
		GridView gv;

	}
}
