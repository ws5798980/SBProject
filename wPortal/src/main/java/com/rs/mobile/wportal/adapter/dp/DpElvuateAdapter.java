
package com.rs.mobile.wportal.adapter.dp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.activity.dp.DpGoodsDetailActivity;
import com.rs.mobile.wportal.adapter.ht.HtPhotoAdapter;
import com.rs.mobile.wportal.biz.ht.HotelPhoto;
import com.rs.mobile.wportal.fragment.dp.DpEvaluateCommonPage;

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
import android.widget.RatingBar;
import android.widget.TextView;
import okhttp3.Request;

@TargetApi(23)
@SuppressLint("NewApi")
public class DpElvuateAdapter extends BaseAdapter {

	DpGoodsDetailActivity activity;

	JSONArray listdata;

	int width;

	Context context;

	int kk = 0;

	DpEvaluateCommonPage f;

	public DpElvuateAdapter(JSONArray listdata, Context context, int width, DpEvaluateCommonPage f) {
		// TODO Auto-generated constructor stub
		this.width = width;
		this.context = context;
		this.listdata = listdata;
		this.f = f;

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

		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.goods_fragment_evaluate_item,
					parent, false);

		}

		try {
			GridView gv = (GridView) convertView.findViewById(com.rs.mobile.wportal.R.id.gv);
			WImageView v = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.img_head);
			TextView textView = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_name);
			TextView textView2 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_content);
			TextView textTime = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_time);
			final TextView textpraise = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.textpraise);
			final android.view.animation.Animation animation = AnimationUtils.loadAnimation(context,
					com.rs.mobile.wportal.R.anim.add_score_anim);
			final TextView addOne_tv = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.addOne_tv);

			RatingBar ratingBar = (RatingBar) convertView.findViewById(com.rs.mobile.wportal.R.id.ratingbar);
			JSONObject jsonObject = new JSONObject(getItem(position).toString());
			final String likes_count = jsonObject.get("likes_count").toString();
			final String commentId = jsonObject.getString("comment_id");
			if (likes_count.equals("0")) {
				textpraise.setText(context.getString(com.rs.mobile.wportal.R.string.common_text073));
			} else {
				textpraise.setText(likes_count);
			}
			if (jsonObject.getBoolean("hasLikes")) {
				Drawable drawable = ContextCompat.getDrawable(context, com.rs.mobile.wportal.R.drawable.icon_kd);

				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				textpraise.setCompoundDrawables(drawable, null, null, null);
				textpraise.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.red));

				textpraise.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						T.showToast(context, context.getString(com.rs.mobile.wportal.R.string.common_text074));
					}
				});
			} else {
				Drawable drawable = ContextCompat.getDrawable(context, com.rs.mobile.wportal.R.drawable.ic_price);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				textpraise.setCompoundDrawables(drawable, null, null, null);
				textpraise.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.red));

				textpraise.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						addOne_tv.setVisibility(View.VISIBLE);
						addOne_tv.setAnimation(animation);
						Drawable drawable = ContextCompat.getDrawable(context, com.rs.mobile.wportal.R.drawable.icon_kd);
						drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
						textpraise.setCompoundDrawables(drawable, null, null, null);
						textpraise.setTextColor(ContextCompat.getColor(context, com.rs.mobile.wportal.R.color.red));

						textpraise.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								// TODO Auto-generated method stub
								T.showToast(context, context.getString(com.rs.mobile.wportal.R.string.common_text074));
							}
						});
						new Handler().postDelayed(new Runnable() {

							public void run() {

								addOne_tv.setVisibility(View.INVISIBLE);

							}
						}, 1000);
						sendLikes(commentId, position);

						textpraise.setText((Integer.parseInt(likes_count) + 1) + "");

					}
				});
			}
			textView.setText(jsonObject.get("nick_name").toString());
			// L.d(listdata.get(position).get("level").toString());
			ratingBar.setRating(Float.parseFloat(jsonObject.get("score").toString()));
			textView2.setText(jsonObject.get("text").toString());
			textTime.setText(jsonObject.getString("commnet_time"));
			ImageUtil.drawImageFromUri(jsonObject.get("head_url").toString(), v);
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
			// final int position1 = i;
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
			// bundle.putInt("position", position1);
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
			final JSONArray arr = jsonObject.getJSONArray("images");
			List<HotelPhoto> list = new ArrayList<>();

			for (int i = 0; i < arr.length(); i++) {
				HotelPhoto hotelPhoto = new HotelPhoto(1, "", arr.getString(i));
				list.add(hotelPhoto);
			}
			if (arr.length() > 0) {
				gv.setVisibility(View.VISIBLE);
				HtPhotoAdapter adater = new HtPhotoAdapter(context, 3, list, 1);
				gv.setAdapter(adater);
				UiUtil.setGridViewHeight(gv, 3);
			} else {
				gv.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			// TODO: handle exception
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
		}, Constant.BASE_URL_DP1 + Constant.SEND_LIKES, params);
	}
}
