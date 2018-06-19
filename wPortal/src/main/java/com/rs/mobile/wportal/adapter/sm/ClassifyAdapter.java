
package com.rs.mobile.wportal.adapter.sm;

import java.util.List;
import java.util.Map;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.image.ImageUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassifyAdapter extends BaseAdapter {

	private List<Map<String, String>> listData;

	private Context context;

	public ClassifyAdapter(List<Map<String, String>> listData, Context context) {
		super();
		this.listData = listData;
		this.context = context;
	}

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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_classify, parent, false);

		}
		Map<String, String> data = listData.get(position);
		SimpleDraweeView img_icon = (SimpleDraweeView) convertView.findViewById(com.rs.mobile.wportal.R.id.img_icon);
		TextView text_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
		// TextView text_en_name = (TextView)
		// convertView.findViewById(R.id.text_en_name);
		if (data.get(C.KEY_JSON_FM_ICON_URL).equals("")) {

			// ImageUtil.drawImageFromUri(ImageUtil.getImageUriFromAssets("icon_404.png"),
			// img_icon);
			img_icon.setImageURI(ImageUtil.getImageUriFromAssets("icon_404.png"));

		} else {
			ImageUtil.drawImageFromUri1(data.get(C.KEY_JSON_FM_ICON_URL), img_icon);
		}

		// text_en_name.setText(data.get(C.KEY_JSON_FM_CATEGORY_NAME_EN).toString());
		text_name.setText(data.get(C.KEY_JSON_FM_CATEGORY_NAME).toString());

		return convertView;
	}

}
