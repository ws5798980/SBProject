
package com.rs.mobile.wportal.adapter.dp;

import java.util.List;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.image.ImageUtil;
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
import android.widget.TextView;

public class DpMyCollectonAdapter extends BaseAdapter {

	private Context context;

	private List<ShoppingCart> list;

	private Handler handler;

	static class ViewHolder {

		private WImageView wImageView;

		private CheckBox checkBox;

		private TextView textView001;

		private TextView textView002;
	}

	public DpMyCollectonAdapter(List<ShoppingCart> list, Context context, Handler handler) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.handler = handler;
	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		ViewHolder itemView = null;
		final ShoppingCart shoppingCart = list.get(position);
		if (convertView == null) {
			itemView = new ViewHolder();

			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_mycollection, parent,
					false);

			itemView.wImageView = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.shopingcart_item_image);
			LayoutParams params = itemView.wImageView.getLayoutParams();
			params.width = context.getResources().getDisplayMetrics().widthPixels / 4;
			itemView.wImageView.setLayoutParams(params);
			itemView.checkBox = (CheckBox) convertView.findViewById(com.rs.mobile.wportal.R.id.checkbox);
			itemView.textView001 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_goodsname);
			itemView.textView002 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.textprice);

			convertView.setTag(itemView);
		} else {
			itemView = (ViewHolder) convertView.getTag();
		}
		itemView.checkBox.setChecked(shoppingCart.isChoosed());
		ImageUtil.drawImageFromUri(shoppingCart.getimgurl(), itemView.wImageView);
		itemView.textView001.setText(shoppingCart.getName());
		itemView.textView002.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.rmb) + shoppingCart.getprice());
		final boolean isChecked = itemView.checkBox.isChecked();
		itemView.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				handler.sendMessage(handler.obtainMessage(0, position));

				list.get(position).setChoosed(!isChecked);
				L.d(list.get(position).isChoosed() + "");
				notifyDataSetChanged();
			}
		});

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				bundle.putString(C.KEY_JSON_FM_ITEM_CODE, shoppingCart.getId());
				bundle.putString(C.KEY_DIV_CODE, shoppingCart.getDiv_code());
				PageUtil.jumpTo(context, DpGoodsDetailActivity.class, bundle);

			}
		});

		return convertView;
	}

}
