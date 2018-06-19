package com.rs.mobile.wportal.adapter.dp;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.activity.BaseActivity;
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
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DpSerchAdapter extends BaseAdapter {
	public DpSerchAdapter(JSONArray arr, Context context, String assessKey, String priceKey, boolean priceFlag) {
		super();
		this.arr = arr;
		this.context = context;
		this.assessKey = assessKey;
		this.priceKey = priceKey;
		this.priceFlag = priceFlag;

	}

	private JSONArray arr;
	private Context context;
	private String assessKey;
	private String priceKey;
	private boolean priceFlag;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return arr.getJSONObject(position);
		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dp_list_item_serch, parent, false);
			vh.dp_name = (TextView) convertView.findViewById(R.id.dp_name);
			vh.dp_item_textview_price = (TextView) convertView.findViewById(R.id.dp_item_textview_price);
			vh.dp_item_textview_eluate = (TextView) convertView.findViewById(R.id.dp_item_textview_eluate);
			vh.dp_img = (WImageView) convertView.findViewById(R.id.dp_img);
			LayoutParams param = vh.dp_img.getLayoutParams();

			param.width = (int) (BaseActivity.get_windows_width(context) / 2);

			vh.dp_img.setLayoutParams(new LinearLayout.LayoutParams((int) (param.width), (int) (param.width)));

			vh.dp_img.invalidate();

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		try {
			JSONObject obj = arr.getJSONObject(position);
			vh.dp_name.setText(obj.getString("item_name"));
			if (priceFlag) {
				vh.dp_item_textview_price.setText(obj.get(priceKey).toString());
			} else {
				vh.dp_item_textview_price.setText(obj.get(priceKey).toString());
			}

			final String item_code = obj.get("item_code").toString();

			final String div_code = obj.optString(C.KEY_DIV_CODE, C.DIV_CODE);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
					bundle.putString(C.KEY_DIV_CODE, div_code);
					PageUtil.jumpTo(context, DpGoodsDetailActivity.class, bundle);

				}
			});
			ImageUtil.drawImageFromUri(obj.getString("image_url"), vh.dp_img);
			vh.dp_item_textview_eluate.setText(obj.get(assessKey) + context.getString(R.string.dp_text_evaluate));
		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}
		return convertView;
	}

	static class ViewHolder {
		TextView dp_name;
		TextView dp_item_textview_price;
		TextView dp_item_textview_eluate;
		WImageView dp_img;

	}
}
