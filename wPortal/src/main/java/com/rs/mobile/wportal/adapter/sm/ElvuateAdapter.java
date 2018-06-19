
package com.rs.mobile.wportal.adapter.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.sm.SmGoodsDetailActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.adapter.ht.HtPhotoAdapter;
import com.rs.mobile.wportal.biz.ht.HotelPhoto;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import okhttp3.Request;

@TargetApi(23)
@SuppressLint("NewApi")
public class ElvuateAdapter extends BaseAdapter {

	SmGoodsDetailActivity activity;

	JSONArray listdata;

	int width;

	Context context;

	int kk = 0;

	boolean visible;

	public ElvuateAdapter(JSONArray listdata, Context context, int width, boolean visible) {
		// TODO Auto-generated constructor stub
		this.width = width;
		this.context = context;
		this.listdata = listdata;
		this.visible = visible;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return listdata.length();
	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		try {
			return listdata.get(position);
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.goods_fragment_evaluate_item,
					parent, false);
			viewHolder.v = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.img_head);
			viewHolder.gv = (GridView) convertView.findViewById(com.rs.mobile.wportal.R.id.gv);
			viewHolder.textView = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
			viewHolder.textView2 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_content);
			viewHolder.textTime = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_time);
			viewHolder.textpraise = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.textpraise);
			viewHolder.animation = AnimationUtils.loadAnimation(context, com.rs.mobile.wportal.R.anim.add_score_anim);
			viewHolder.addOne_tv = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.addOne_tv);
			viewHolder.ratingBar = (RatingBar) convertView.findViewById(com.rs.mobile.wportal.R.id.ratingbar);
			viewHolder.img_good = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.img_good);
			viewHolder.line_good = (LinearLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.line_good);
			viewHolder.text_good = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_good);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		try {

			JSONObject jsonObject = new JSONObject(getItem(position).toString());
			final String likes_count = jsonObject.get("likes_count").toString();
			final String commentId = jsonObject.getString("comment_id");
			if (likes_count.equals("0")) {
				viewHolder.textpraise.setText(context.getString(com.rs.mobile.wportal.R.string.common_text073));
			} else {
				viewHolder.textpraise.setText(likes_count);
			}
			if (jsonObject.getBoolean("hasLikes")) {
				Drawable drawable = ContextCompat.getDrawable(context, com.rs.mobile.wportal.R.drawable.icon_kd);

				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				viewHolder.textpraise.setCompoundDrawables(drawable, null, null, null);
				viewHolder.textpraise.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.red));

				viewHolder.textpraise.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						T.showToast(context, context.getString(com.rs.mobile.wportal.R.string.common_text074));
					}
				});
			} else {
				Drawable drawable = ContextCompat.getDrawable(context, com.rs.mobile.wportal.R.drawable.ic_price);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				viewHolder.textpraise.setCompoundDrawables(drawable, null, null, null);
				viewHolder.textpraise.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.red));

				viewHolder.textpraise.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						viewHolder.addOne_tv.setVisibility(View.VISIBLE);
						viewHolder.addOne_tv.setAnimation(viewHolder.animation);
						Drawable drawable = ContextCompat.getDrawable(context, com.rs.mobile.wportal.R.drawable.icon_kd);
						drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
						viewHolder.textpraise.setCompoundDrawables(drawable, null, null, null);
						viewHolder.textpraise.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.red));

						viewHolder.textpraise.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								// TODO Auto-generated method stub
								T.showToast(context, context.getString(com.rs.mobile.wportal.R.string.common_text074));
							}
						});
						new Handler().postDelayed(new Runnable() {

							public void run() {

								viewHolder.addOne_tv.setVisibility(View.INVISIBLE);

							}
						}, 1000);
						sendLikes(commentId, position);

						viewHolder.textpraise.setText((Integer.parseInt(likes_count) + 1) + "");

					}
				});
			}
			if (visible) {
				viewHolder.line_good.setVisibility(View.VISIBLE);
				ImageUtil.drawImageFromUri(jsonObject.getString("image_url"), viewHolder.img_good);
				viewHolder.text_good.setText(jsonObject.getString("item_name"));
			}
			viewHolder.textView.setText(jsonObject.get("nick_name").toString());
			// L.d(listdata.get(position).get("level").toString());
			viewHolder.ratingBar.setRating(Float.parseFloat(jsonObject.get("score").toString()));
			viewHolder.textView2.setText(jsonObject.get("text").toString());
			viewHolder.textTime.setText(jsonObject.getString("commnet_time"));
			ImageUtil.drawImageFromUri(jsonObject.get("head_url").toString(), viewHolder.v);
			final JSONArray arr = jsonObject.getJSONArray("images");
			List<HotelPhoto> list = new ArrayList<>();

			for (int i = 0; i < arr.length(); i++) {
				HotelPhoto hotelPhoto = new HotelPhoto(1, "", arr.getString(i));
				list.add(hotelPhoto);
			}
			if (arr.length() > 0) {
				viewHolder.gv.setVisibility(View.VISIBLE);
				HtPhotoAdapter adater = new HtPhotoAdapter(context, 3, list, 1);
				viewHolder.gv.setAdapter(adater);
				UiUtil.setGridViewHeight(viewHolder.gv, 3);
			} else {
				viewHolder.gv.setVisibility(View.GONE);
			}

			// WImageView v1;
			// LinearLayout.LayoutParams lp = new
			// LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
			// LinearLayout.LayoutParams.WRAP_CONTENT);
			// lp.setMargins(StringUtil.dip2px(context, 10), 0,
			// StringUtil.dip2px(context, 10), 0);
			// lp.height = width;
			// lp.width = width;
			// LinearLayout linearLayout = new LinearLayout(context);
			// linearLayout.setOrientation(LinearLayout.HORIZONTAL);
			//
			// linearLayout.setGravity(Gravity.CENTER);
			// LinearLayout l = (LinearLayout)
			// convertView.findViewById(R.id.lineimg);
			// HorizontalScrollView h = (HorizontalScrollView)
			// convertView.findViewById(R.id.horizscrollview);
			// List<Map<String, String>> list = new ArrayList<Map<String,
			// String>>();
			// final JSONArray arr = jsonObject.getJSONArray("images");
			// l.removeAllViews();
			// final String[] imgs = new String[arr.length()];
			// if (arr.length() > 0) {
			// h.setVisibility(View.VISIBLE);
			// for (int i = 0; i < arr.length(); i++) {
			// imgs[i] = arr.getString(i);
			//
			// }
			// for (int i = 0; i < arr.length(); i++) {
			// v1 = new WImageView(context);
			// v1.setScaleType(ScaleType.CENTER_INSIDE);
			//
			// v1.setImageURI(Uri.parse(arr.getString(i)));
			// v1.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			//
			// // TODO Auto-generated method stub
			// Bundle bundle = new Bundle();
			// bundle.putStringArray("images", imgs);
			//
			// PageUtil.jumpTo(context, ShowImageActivity.class, bundle);
			// }
			// });
			//
			// v1.setLayoutParams(lp);
			// linearLayout.addView(v1);
			//
			// }
			//
			// l.addView(linearLayout);
			//
			// } else {
			// h.setVisibility(View.GONE);
			// }
			//
		} catch (Exception e) {
			// TODO: handle exception
			L.d("text");
			L.e(e);
		}

		return convertView;
	}

	public void sendLikes(String commentId, final int index) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("commentId", commentId);
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {
					JSONObject json = listdata.getJSONObject(index);
					json.put("hasLikes", true);
					json.put("likes_count", Integer.parseInt(json.get("likes_count").toString()) + 1);
					notifyDataSetChanged();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.SEND_LIKES, params);
	}

	private class ViewHolder {
		public WImageView v, img_good;
		public TextView textView, text_good;
		public TextView textView2;
		public TextView textTime;
		public TextView textpraise;
		public android.view.animation.Animation animation;
		public TextView addOne_tv;
		public RatingBar ratingBar;
		public GridView gv;
		public LinearLayout line_good;

	}
}
