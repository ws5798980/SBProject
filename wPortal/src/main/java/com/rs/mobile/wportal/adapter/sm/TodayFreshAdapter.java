
package com.rs.mobile.wportal.adapter.sm;

import java.util.List;
import java.util.Map;

import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.activity.sm.SmGoodsDetailActivity;
import com.rs.mobile.common.C;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TodayFreshAdapter extends BaseAdapter {

	private List<Map<String, String>> arr;

	private Context context;

	public TodayFreshAdapter(List<Map<String, String>> arr, Context context) {
		super();
		this.arr = arr;
		this.context = context;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_today_fresh, parent,
					false);

		}
		Map<String, String> data = arr.get(position);
		WImageView imageView = (WImageView) convertView.findViewById(R.id.img_good);
		TextView text_name = (TextView) convertView.findViewById(R.id.text_name);
		TextView text_price = (TextView) convertView.findViewById(R.id.text_price);
		TextView text_cart = (TextView) convertView.findViewById(R.id.text_cart);
		final String item_code = data.get(C.KEY_JSON_FM_ITEM_CODE).toString();
		ImageUtil.drawImageFromUri(data.get(C.KEY_JSON_FM_ITEM_IMAGE_URL).toString(), imageView);
		text_name.setText(data.get(C.KEY_JSON_FM_ITEM_NAME).toString());
		text_price.setText(
				context.getResources().getString(R.string.rmb) + data.get(C.KEY_JSON_FM_ITEM_PRICE).toString());
		text_cart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				if (UiUtil.checkLogin(context)) {
					SmGoodsDetailActivity.addToShopcart(item_code, "1", context, C.DIV_CODE, "");
				} else {

				}

			}
		});

		return convertView;
	}

}
