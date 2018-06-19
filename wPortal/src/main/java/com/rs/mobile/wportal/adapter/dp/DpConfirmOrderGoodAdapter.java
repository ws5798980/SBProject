
package com.rs.mobile.wportal.adapter.dp;

import java.util.ArrayList;
import java.util.List;

import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.dp.DpGoodsDetailActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DpConfirmOrderGoodAdapter extends BaseAdapter {

	private List<ShoppingCart> listdata = new ArrayList<ShoppingCart>();

	private Context context;

	private WImageView imageView;

	private TextView goodName, goodPrice, goodNum;

	private TextView refund_status;

	public DpConfirmOrderGoodAdapter(List<ShoppingCart> listdata, Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listdata = listdata;
	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		try {

			if (convertView == null) {

				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_order_item, parent,
						false);

				imageView = (WImageView) convertView.findViewById(R.id.img_confirm_order);

				goodName = (TextView) convertView.findViewById(R.id.good_name);

				goodPrice = (TextView) convertView.findViewById(R.id.good_price);

				goodNum = (TextView) convertView.findViewById(R.id.good_num);

				refund_status = (TextView) convertView.findViewById(R.id.refund_status);

			}

			ImageUtil.drawImageFromUri(listdata.get(position).getimgurl(), imageView);

			goodName.setText(listdata.get(position).getName());

			goodPrice.setText(context.getResources().getString(R.string.rmb) + listdata.get(position).getprice());

			goodNum.setText("x" + listdata.get(position).getNum());

			String refund_flag = listdata.get(position).getRefund_status();

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
					// TODO Auto-generated method stub

					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_ITEM_CODE, listdata.get(position).getId());
					bundle.putString(C.KEY_DIV_CODE, listdata.get(position).getDiv_code());
					PageUtil.jumpTo(context, DpGoodsDetailActivity.class, bundle);

				}
			});

		} catch (Exception e) {

			L.e(e);

		}
		return convertView;
	}

}
