
package com.rs.mobile.wportal.adapter.sm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.activity.sm.SmGoodsDetailActivity;
import com.rs.mobile.common.util.CollectionUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SmHzListViewAdapter extends BaseAdapter {

	int viewwidth, count, flag;

	Context context;

	JSONArray arr;

	Activity activity;

	ImageView imgcart;

	String stringKey_1, stringKey_2;

	private String stringKey3;

	/**
	 * 
	 * @param context
	 * @param data
	 * @param viewwidth
	 * @param flag
	 * @param stringKey_1
	 *            keyarray
	 * @param stringKey_2
	 *            price
	 * @param stringKey3
	 *            stock_unit
	 */
	public SmHzListViewAdapter(Context context, JSONArray arr, int viewwidth, int flag, String stringKey_1,
			String stringKey_2, String stringKey3) {
		// TODO Auto-generated constructor stub
		this.context = context;
		L.d("sucess");
		this.flag = flag;
		this.viewwidth = viewwidth;
		this.stringKey_2 = stringKey_2;
		this.stringKey_1 = stringKey_1;
		this.stringKey3 = stringKey3;
		try {
			this.arr = arr;
			L.d(arr.toString());
			this.count = arr.length();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return arr.length();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		return CollectionUtil.jsonArrayToListMap(arr).get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.sm_hzlistview_item, parent, false);
		}

		TextView textviewname, textviewprice;
		textviewname = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.sm_hzlistview_item_textview_name);
		textviewprice = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.sm_hzlistview_item_textview_price);

		WImageView imgview = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.sm_hzlistview_item_img);

		LayoutParams param = imgview.getLayoutParams();

		param.width = (int) (viewwidth);

		imgview.setLayoutParams(new LinearLayout.LayoutParams((int) (viewwidth), (int) (viewwidth)));

		imgview.setScaleType(ScaleType.CENTER_INSIDE);

		imgview.invalidate();

		imgcart = (ImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.sm_hzlistview_cart);

		try {
			final JSONObject jsonObject = new JSONObject(arr.get(position).toString());
			final String item_code = jsonObject.get(C.KEY_JSON_FM_ITEM_CODE).toString();
			final String div_code = jsonObject.getString(C.KEY_DIV_CODE);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
					bundle.putString(C.KEY_DIV_CODE, div_code);
					PageUtil.jumpTo(context, SmGoodsDetailActivity.class, bundle);
				}
			});
			ImageUtil.drawImageView1(context, imgview, jsonObject, C.KEY_JSON_IMAGE_URL002, C.KEY_JSON_VERSION);
			textviewname.setText(jsonObject.get(C.KEY_JSON_ITEM_NAME).toString());
			textviewprice
					.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.rmb) + jsonObject.get(stringKey_2).toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flag == 0) {
			imgcart.setVisibility(View.GONE);
		} else {
			imgcart.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

}
