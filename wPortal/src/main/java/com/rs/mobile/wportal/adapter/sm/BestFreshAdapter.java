
package com.rs.mobile.wportal.adapter.sm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.activity.sm.SmGoodsDetailActivity;
import com.rs.mobile.common.image.ImageUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BestFreshAdapter extends BaseAdapter {

	private JSONArray arr;

	private Context context;

	public BestFreshAdapter(JSONArray arr, Context context) {
		super();
		this.arr = arr;
		this.context = context;
	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return arr.length();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		try {
			return new JSONObject(arr.getJSONObject(position).toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_best_fresh, parent,
					false);

		}
		try {
			WImageView good_img = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.good_img);
			TextView text_title = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_title);
			TextView text_content = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_content);
			TextView good_evaluate_num = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.good_evaluate_num);
			JSONObject jsonObject = (JSONObject) getItem(position);
			ImageUtil.drawImageFromUri(jsonObject.getString(C.KEY_JSON_FM_ITEM_IMAGE_URL), good_img);
			text_title.setText(jsonObject.getString(C.KEY_JSON_FM_ITEM_NAME));
			text_content.setText(jsonObject.getString(C.KEY_JSON_FM_ITEM_DESCRIBE));
			good_evaluate_num.setText(jsonObject.get(C.KEY_JSON_FM_GOOD_NUM).toString()
					+ context.getResources().getString(com.rs.mobile.wportal.R.string.best_fresh_detail));
			final String item_code = jsonObject.get(C.KEY_JSON_FM_ITEM_CODE).toString();
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
					bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
					PageUtil.jumpTo(context, SmGoodsDetailActivity.class, bundle);
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}

		return convertView;
	}

}
