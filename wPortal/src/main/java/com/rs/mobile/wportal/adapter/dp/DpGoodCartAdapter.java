
package com.rs.mobile.wportal.adapter.dp;

import java.util.List;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.activity.dp.DpShoppingCartActivity;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.wportal.activity.dp.DpGoodsDetailActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class DpGoodCartAdapter extends BaseAdapter {

	List<ShoppingCart> listdata;

	private Handler handler;

	Context context;

	// static class ViewHolder{
	// public CheckBox checkBox;
	// public WImageView wImageView;
	// public TextView textView001;
	// public TextView textView002;
	// public AmountView amountView;
	//
	// }
	public DpGoodCartAdapter(List<ShoppingCart> listdata, Context context, Handler handler) {
		// TODO Auto-generated constructor stub
		this.listdata = listdata;
		this.context = context;
		this.handler = handler;
		// isSelected = new HashMap<Integer, Boolean>();
		// numbers = new HashMap<Integer, Integer>();

		// initDate();
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
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		try {

			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.sm_shoppingcart_item, parent,
						false);

			}

			WImageView wImageView = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.shopingcart_item_image);
			LayoutParams params = wImageView.getLayoutParams();
			params.width = context.getResources().getDisplayMetrics().widthPixels / 4;
			wImageView.setLayoutParams(params);
			wImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_ITEM_CODE, listdata.get(position).getId());
					PageUtil.jumpTo(context, DpGoodsDetailActivity.class, bundle);

				}
			});
			CheckBox checkBox = (CheckBox) convertView.findViewById(com.rs.mobile.wportal.R.id.checkbox);

			TextView textView001 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_goodsname);

			TextView textView002 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.textprice);

			ImageView btnDecrease = (ImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.btnDecrease);

			final TextView etAmount = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.et_amount);

			ImageView btnIncrease = (ImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.btnIncrease);

			etAmount.setText("" + listdata.get(position).getNum());
			textView001.setText(listdata.get(position).getName() + "");
			textView002.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.rmb) + listdata.get(position).getprice());
			ImageUtil.drawImageFromUri(listdata.get(position).getimgurl(), wImageView);
			if (listdata.get(position).isChoosed()) {
				checkBox.setChecked(true);
			} else {
				checkBox.setChecked(false);
			}

			btnDecrease.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						int amount = Integer.parseInt(etAmount.getText().toString());

						if (amount >= 1) {
							amount--;
							etAmount.setText(amount + "");
						}
						etAmount.clearFocus();

						listdata.get(position).setNum(amount);

						notifyDataSetChanged();

						handler.sendMessage(handler.obtainMessage(10, getTotalPrice()));

						DpShoppingCartActivity.upDateShopcart(context);

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			btnIncrease.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						int amount = Integer.parseInt(etAmount.getText().toString());

						amount++;
						etAmount.setText(amount + "");

						etAmount.clearFocus();

						listdata.get(position).setNum(amount);

						handler.sendMessage(handler.obtainMessage(10, getTotalPrice()));

						notifyDataSetChanged();
						DpShoppingCartActivity.upDateShopcart(context);

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			checkBox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					listdata.get(position).setChoosed(!listdata.get(position).isChoosed());

					notifyDataSetChanged();

					handler.sendMessage(handler.obtainMessage(11, getTotalPrice()));

				}
			});

		} catch (Exception e) {

			L.e(e);

		}

		return convertView;
	}

	public float getTotalPrice() {

		ShoppingCart bean = null;
		float totalPrice = 0;
		for (int i = 0; i < listdata.size(); i++) {
			bean = listdata.get(i);
			if (bean.isChoosed()) {
				totalPrice += bean.getNum() * bean.getprice();
			}
		}
		return (float) (Math.round(totalPrice * 100)) / 100;
	}

}
