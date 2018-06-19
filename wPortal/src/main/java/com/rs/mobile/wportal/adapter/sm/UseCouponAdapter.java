
package com.rs.mobile.wportal.adapter.sm;

import java.util.ArrayList;

import com.rs.mobile.wportal.biz.Coupon;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UseCouponAdapter extends BaseAdapter {

	private ArrayList<Coupon> arr;

	private Context context;

	private String flag;

	public UseCouponAdapter(ArrayList<Coupon> arr, Context context, String flag) {
		super();
		this.arr = arr;
		this.context = context;
		this.flag = flag;

	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return arr.size();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		return arr.get(position);
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_coupon, parent, false);
		}

		CheckBox checkBox = (CheckBox) convertView.findViewById(com.rs.mobile.wportal.R.id.check_box_coupon);

		TextView coupon_time = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.coupon_time);
		TextView text_money = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_money);
		TextView text_requre = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_requre);

		TextView coupon_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.coupon_name);
		LinearLayout line_bg = (LinearLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.line_bg);

		if (flag.equals("1")) {
			checkBox.setVisibility(View.VISIBLE);

		} else if (flag.equals("2")) {
			checkBox.setVisibility(View.GONE);

		} else if (flag.equals("3")) {
			checkBox.setVisibility(View.GONE);

			// text_view_need.setText(arr.get(position).get(C.KEY_JSON_FM_COUPON_NEEDMONEY).toString());
		}
		coupon_time.setText(arr.get(position).getStart_date() + "至" + arr.get(position).getEnd_date());
		coupon_name.setText(arr.get(position).getCoupon_range_remark());

		text_money.setText(arr.get(position).getCoupon_name());
		text_requre.setText(arr.get(position).getOver_amount_remark());
		checkBox.setChecked(arr.get(position).isCpchecked());

		if (arr.get(position).isCanchecked()) {
			// convertView.setEnabled(true);
			// checkBox.setVisibility(View.VISIBLE);;
			// checkBox.setVisibility(View.VISIBLE);
			checkBox.setEnabled(true);
			line_bg.setBackgroundColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.transparent));
		} else if (!arr.get(position).isCanchecked()) {

			// convertView.setEnabled(false);
			checkBox.setEnabled(false);
			line_bg.setBackgroundColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.transparent20));

		}

		checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				arr.get(position).setCpchecked(!arr.get(position).isCpchecked());
				if (arr.get(position).isCpchecked()) {
					if (arr.get(position).getCoupon_range().equals("0")) {
						for (int i = 0; i < arr.size(); i++) {
							if (i != position) {
								arr.get(i).setCanchecked(false);
								arr.get(i).setCpchecked(false);
							}
						}
					} else {
						for (int i = 0; i < arr.size(); i++) {
							if (i != position && arr.get(i).getCoupon_range().equals(arr.get(position).getCoupon_range())) {
								arr.get(i).setCanchecked(false);
								arr.get(i).setCpchecked(false);
								;

							} else if (arr.get(i).getCoupon_range().equals("0")) {
								arr.get(i).setCanchecked(false);
								arr.get(i).setCpchecked(false);
								;
							}
						}
					}
				} else {
					for (int i = 0; i < arr.size(); i++) {
						arr.get(i).setCanchecked(true);

					}
				}
				notifyDataSetChanged();

			}
		});

		return convertView;
	}

}
