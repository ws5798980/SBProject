
package com.rs.mobile.wportal.adapter.sm;

import java.util.ArrayList;
import java.util.List;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.activity.sm.SmGoodsDetailActivity;
import com.rs.mobile.wportal.biz.ShoppingCart;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class confirmOrderGoodAdapter extends BaseAdapter {

	private List<ShoppingCart> listdata = new ArrayList<ShoppingCart>();

	private Context context;

	private WImageView imageView;

	private TextView goodName, goodPrice, goodNum;

	private TextView refund_status;

	public confirmOrderGoodAdapter(List<ShoppingCart> listdata, Context context) {
		this.context = context;
		this.listdata = listdata;
	}

	@Override
	public int getCount() {

		return listdata.size();
	}

	@Override
	public Object getItem(int position) {

		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		try {

			if (convertView == null) {

				convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.confirm_order_item, parent,
						false);

				imageView = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.img_confirm_order);

				goodName = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.good_name);

				goodPrice = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.good_price);

				goodNum = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.good_num);

				refund_status = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.refund_status);

			}

			ImageUtil.drawImageFromUri(listdata.get(position).getimgurl(), imageView);

			goodName.setText(listdata.get(position).getName());
			goodPrice.setText(StringUtil.formatTosepara(listdata.get(position).getprice()) + "원");

			goodNum.setText("x" + listdata.get(position).getNum());

			String refund_flag = listdata.get(position).getRefund_status();
			L.d("++++++++++++++" + refund_flag);
			if (refund_flag.equals("0")) {
				refund_status.setText("");
			} else if (refund_flag.equals("1")) {
				refund_status.setText("退款中");
			} else {
				refund_status.setText("退款完成");
			}

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString("ItemCode", listdata.get(position).getId());
					bundle.putString(C.KEY_DIV_CODE, listdata.get(position).getDiv_code());
					bundle.putString("SaleCustomCode", listdata.get(position).sale_custom_code);
					PageUtil.jumpTo(context, SmGoodsDetailActivity.class, bundle);

				}
			});

		} catch (Exception e) {

			L.e(e);

		}
		return convertView;
	}

}
