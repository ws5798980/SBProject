
package com.rs.mobile.wportal.adapter.sm;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class SmCouponAdapter extends BaseAdapter {

	private List<Map<String, Object>> arr;

	private Context context;

	private String flag;

	public SmCouponAdapter(List<Map<String, Object>> arr, Context context, String flag) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_coupon, parent, false);
		}

		CheckBox checkBox = (CheckBox) convertView.findViewById(com.rs.mobile.wportal.R.id.check_box_coupon);

		TextView coupon_time = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.coupon_time);
		TextView text_money = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_money);
		TextView text_requre = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_requre);

		TextView coupon_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.coupon_name);
		coupon_time.setText(arr.get(position).get("start_date").toString() + "至" + arr.get(position).get("end_date").toString());
		coupon_name.setText(arr.get(position).get("coupon_range_remark").toString());

		text_money.setText(arr.get(position).get("coupon_name").toString());
		text_requre.setText(arr.get(position).get("over_amount_remark").toString());
		if (flag.equals("1")) {
			checkBox.setVisibility(View.GONE);

		} else if (flag.equals("2")) {
			checkBox.setVisibility(View.VISIBLE);

		} else if (flag.equals("3")) {
			checkBox.setVisibility(View.GONE);

		}

		return convertView;
	}

}
