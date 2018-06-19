package com.rs.mobile.wportal.adapter.ht;

import java.util.List;

import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.biz.ht.HtOrder;
import com.rs.mobile.wportal.activity.ht.HtOrderDetailActivity;
import com.rs.mobile.wportal.activity.ht.HtOrderResultActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HtOrderAdapter extends BaseAdapter {
	public HtOrderAdapter(List<HtOrder> listData, Context context) {
		super();
		this.listData = listData;
		this.context = context;
	}

	private List<HtOrder> listData;
	private Context context;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderOrder vh;
		if (convertView == null) {
			vh = new ViewHolderOrder();
			convertView = LayoutInflater.from(context).inflate(com.rs.mobile.wportal.R.layout.ht_list_item_myorder, parent, false);
			vh.text_bedType = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_bedType);
			vh.text_id = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_id);
			vh.text_money = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_money);
			vh.text_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
			vh.text_status = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_status);
			vh.text_time = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_time);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolderOrder) convertView.getTag();
		}
		final HtOrder h = listData.get(position);
		vh.text_bedType.setText(h.getRoomTypeName());
		vh.text_id.setText(context.getString(com.rs.mobile.wportal.R.string.common_text084) + h.getOrderId());
		vh.text_money.setText(context.getString(com.rs.mobile.wportal.R.string.rmb) + h.getRoomPrice());
		vh.text_name.setText(h.getHotelName());
		final String status = h.getOrderStatus();
		switch (status) {
		case "0":
			vh.text_status.setText(context.getString(com.rs.mobile.wportal.R.string.common_text077));
			break;
		case "1":
			vh.text_status.setText(context.getString(com.rs.mobile.wportal.R.string.common_text076));
			break;
		case "2":
			vh.text_status.setText(context.getString(com.rs.mobile.wportal.R.string.common_text024));
			break;
		case "3":
			vh.text_status.setText(context.getString(com.rs.mobile.wportal.R.string.common_text026));
			break;
		case "4":
			vh.text_status.setText(context.getString(com.rs.mobile.wportal.R.string.common_text085));
			break;
		case "5":
			vh.text_status.setText(context.getString(com.rs.mobile.wportal.R.string.common_text086));
			break;
		case "6":
			vh.text_status.setText(context.getString(com.rs.mobile.wportal.R.string.common_text081));
			break;
		case "8":
			vh.text_status.setText("已过保留时间");
			break;
		case "9":
			vh.text_status.setText("等待接单");
			break;
		case "10":
			vh.text_status.setText("酒店已接单");
			break;
		case "11":
			vh.text_status.setText("酒店拒绝接收");
			break;

		default:
			break;
		}
		vh.text_time.setText(h.getArriveTime() + context.getString(com.rs.mobile.wportal.R.string.ht_text_077) + h.getLeaveTime());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (status.equals("1")) {
					Bundle bundle = new Bundle();
					bundle.putString("orderId", h.getOrderId());
					PageUtil.jumpTo(context, HtOrderResultActivity.class, bundle);
				} else {
					Bundle bundle = new Bundle();
					bundle.putString("orderId", h.getOrderId());
					PageUtil.jumpTo(context, HtOrderDetailActivity.class, bundle);
				}

			}
		});
		return convertView;
	}

	static class ViewHolderOrder {
		TextView text_id, text_status, text_name, text_bedType, text_time, text_money;
	}
}
