
package com.rs.mobile.wportal.adapter.sm;

import java.util.List;
import java.util.Map;

import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
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

public class NewFreshAdapter extends BaseAdapter {

	private List<Map<String, String>> listdata;

	private Context context;

	public NewFreshAdapter(List<Map<String, String>> listdata, Context context) {
		super();
		this.listdata = listdata;
		this.context = context;
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
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_new_fresh, parent, false);
		}
		final Map<String, String> data = listdata.get(position);
		WImageView good_img = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.good_img);
		GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(context.getResources())
				.setFadeDuration(500)

				// .setRoundingParams(RoundingParams.fromCornersRadius(5))
				.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
				.setFailureImage(context.getResources().getDrawable(com.rs.mobile.wportal.R.drawable.img_failed_to_load),
						ScalingUtils.ScaleType.CENTER)
				.setRetryImage(context.getResources().getDrawable(com.rs.mobile.wportal.R.drawable.icon_reload),
						ScalingUtils.ScaleType.CENTER)
				// .setProgressBarImage(new
				// AutoRotateDrawable(context.getResources().getDrawable(R.drawable.icon_reload),
				// 500), ScalingUtils.ScaleType.CENTER)
				.setProgressBarImage(
						new AutoRotateDrawable(context.getResources().getDrawable(com.rs.mobile.wportal.R.drawable.img_loading), 700),
						ScalingUtils.ScaleType.CENTER)
				// .setProgressBarImage(ContextCompat.getDrawable(context,
				// R.drawable.img_progress))
				.build();
		good_img.setHierarchy(hierarchy);
		TextView text_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
		TextView text_price = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_price);
		TextView text_to_buy = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_to_buy);
		ImageUtil.drawImageFromUri(data.get(C.KEY_JSON_FM_ITEM_IMAGE_URL), good_img);
		text_name.setText(data.get(C.KEY_JSON_FM_ITEM_NAME).toString());
		text_price.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.rmb) + data.get(C.KEY_JSON_FM_ITEM_PRICE));
		text_to_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString(C.KEY_JSON_FM_ITEM_CODE, data.get(C.KEY_JSON_FM_ITEM_CODE));
				bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
				PageUtil.jumpTo(context, SmGoodsDetailActivity.class, bundle);
			}
		});
		return convertView;
	}

}
