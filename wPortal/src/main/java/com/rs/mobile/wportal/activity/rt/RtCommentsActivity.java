package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.ShowImageActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.view.rt.FullHeightListView;
import com.rs.mobile.common.activity.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import okhttp3.Request;

public class RtCommentsActivity extends BaseActivity {

	// toolbar
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;

	private PullToRefreshScrollView scroll_view;

	private FullHeightListView lv_comment_list;

	private ReplyAdapter commentsAdapter;

	private LinearLayout.LayoutParams lp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_comments);

		lp = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 82, getResources()
						.getDisplayMetrics()), (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 82, getResources()
						.getDisplayMetrics()));

		initToolbar();
		initViews();
		initDatas();
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
		tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
		setSupportActionBar(toolbar);
	}

	private void initViews() {

		tv_title.setText(getString(com.rs.mobile.wportal.R.string.sm_text008));

		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		scroll_view = (PullToRefreshScrollView) findViewById(com.rs.mobile.wportal.R.id.lv_comments);
		scroll_view.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				if (commentsAdapter != null) {
					commentsAdapter.clear();
				}
				initDatas();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				scroll_view.onRefreshComplete();
			}
		});
		lv_comment_list = (FullHeightListView) findViewById(com.rs.mobile.wportal.R.id.lv_comment_list);
		lv_comment_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				try {

					JSONObject item = (JSONObject) commentsAdapter
							.getItem(position);

					Intent i = new Intent(RtCommentsActivity.this,
							RtSellerDetailActivity.class);
					i.putExtra("restaurantCode",
							item.getString("restaurantCode"));
					startActivity(i);

				} catch (Exception e) {

					L.e(e);

				}

			}
		});
	}

	private void initDatas() {

		try {

			OkHttpHelper helper = new OkHttpHelper(RtCommentsActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			// params.put("divCode", "1");

			helper.addPostRequest(
					new OkHttpHelper.CallbackLogic() {

						@Override
						public void onNetworkError(Request request,
								IOException e) {
							// TODO Auto-generated method stub

							scroll_view.onRefreshComplete();

						}

						@Override
						public void onBizSuccess(String responseDescription,
								JSONObject data, final String all_data) {
							// TODO Auto-generated method stub

							try {

								L.d(all_data);

								scroll_view.onRefreshComplete();

								String status = data.getString("status");

								if (status != null && status.equals("1")) {

									hideNoData();

									data = data.getJSONObject("data");

									final JSONArray list = data
											.getJSONArray("List");

									if (list != null  && list.length() > 0) {

										commentsAdapter = new ReplyAdapter(list);
										lv_comment_list
												.setAdapter(commentsAdapter);

									}else{
										showNoData();	
									}

								} else {
									showNoData();
									// t(data.getString("msg"));

								}

							} catch (Exception e) {
								L.e(e);
							}
						}

						@Override
						public void onBizFailure(String responseDescription,
								JSONObject data, String responseCode) {
							// TODO Auto-generated method stub

							scroll_view.onRefreshComplete();

						}
					},
					Constant.BASE_URL_RT
							+ Constant.RT_MY_COMMENT
							+ "?userID="
							+ S.getShare(RtCommentsActivity.this,
									C.KEY_REQUEST_MEMBER_ID, "")
							+ "&divCode="
							+ C.DIV_CODE
							+ "&currentPage=1&token="
							+ S.getShare(RtCommentsActivity.this,
									C.KEY_JSON_TOKEN, ""), params);
		} catch (Exception e) {
			L.e(e);
		}

	}

	private class ReplyAdapter extends BaseAdapter {

		private JSONArray list;

		ReplyAdapter(JSONArray list) {
			this.list = list;
		}

		void setJSONArray(JSONArray list) {
			this.list = list;
			notifyDataSetChanged();
		}

		void clear() {
			if (list != null) {
				list = null;
			}
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return list != null ? list.length() : 0;
		}

		@Override
		public Object getItem(int position) {

			try {

				return list.getJSONObject(position);

			} catch (Exception e) {

				L.e(e);

			}

			return null;

		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			try {

				ReplyViewHolder viewHolder;
				if (convertView == null) {
					convertView = getLayoutInflater().inflate(
							com.rs.mobile.wportal.R.layout.list_item_rt_my_reply, null);
					viewHolder = new ReplyViewHolder();
					viewHolder.icon_view = (WImageView) convertView
							.findViewById(com.rs.mobile.wportal.R.id.icon_view);
					viewHolder.nickName = (TextView) convertView
							.findViewById(com.rs.mobile.wportal.R.id.nickName);
					viewHolder.date = (TextView) convertView
							.findViewById(com.rs.mobile.wportal.R.id.date);
					viewHolder.ratingBar = (RatingBar) convertView
							.findViewById(com.rs.mobile.wportal.R.id.ratingBar);
					viewHolder.content = (TextView) convertView
							.findViewById(com.rs.mobile.wportal.R.id.content);
					viewHolder.ll_hscrollview = (LinearLayout) convertView
							.findViewById(com.rs.mobile.wportal.R.id.ll_hscrollview);
					viewHolder.hsv_hscrollview = (HorizontalScrollView) convertView
							.findViewById(com.rs.mobile.wportal.R.id.hsv_hscrollview);
					viewHolder.seller_icon_view = (WImageView) convertView
							.findViewById(com.rs.mobile.wportal.R.id.seller_icon_view);
					viewHolder.seller_name = (TextView) convertView
							.findViewById(com.rs.mobile.wportal.R.id.seller_name);
					viewHolder.payment_info = (TextView) convertView
							.findViewById(com.rs.mobile.wportal.R.id.payment_info);
					convertView.setTag(viewHolder);
				} else {
					viewHolder = (ReplyViewHolder) convertView.getTag();
				}

				viewHolder.icon_view.setCircle(true);

				JSONObject reply = list.getJSONObject(position);

				ImageUtil.drawImageFromUri(reply.getString("iconImg"),
						viewHolder.icon_view);
				viewHolder.nickName.setText(reply.getString("nickName"));
				viewHolder.date.setText(reply.getString("date"));
				viewHolder.ratingBar.setRating(Float.parseFloat(reply
						.getString("rate")) / 2);
				viewHolder.content.setText(reply.getString("content"));

				viewHolder.ll_hscrollview.removeAllViews();

				final JSONArray images = reply.getJSONArray("ImageList");

				if (images != null && images.length() > 0) {

					for (int i = 0; i < images.length(); i++) {

						if (i != 0) {
							lp.leftMargin = (int) TypedValue.applyDimension(
									TypedValue.COMPLEX_UNIT_DIP, 5,
									getResources().getDisplayMetrics());
						}

						WImageView image = new WImageView(
								RtCommentsActivity.this);
						viewHolder.ll_hscrollview.addView(image, lp);
						ImageUtil.drawImageFromUri(images.getJSONObject(i)
								.getString("imgUrl"), image);

						image.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								try {

									final String[] imgs = new String[images
											.length()];

									for (int i = 0; i < images.length(); i++) {

										imgs[i] = images.getJSONObject(i)
												.getString("imgUrl");

									}

									Bundle bundle = new Bundle();

									bundle.putStringArray("images", imgs);

									PageUtil.jumpTo(RtCommentsActivity.this,
											ShowImageActivity.class, bundle);

								} catch (Exception e) {

									L.e(e);

								}

							}
						});

					}
					viewHolder.hsv_hscrollview.setVisibility(View.VISIBLE);
				} else {
					viewHolder.hsv_hscrollview.setVisibility(View.GONE);
					viewHolder.ll_hscrollview.removeAllViews();
				}

				if (reply.has("thumnailImage"))
					ImageUtil.drawImageFromUri(
							reply.getString("thumnailImage"),
							viewHolder.seller_icon_view);

				if (reply.has("customName"))
					viewHolder.seller_name.setText(reply
							.getString("customName"));

				if (reply.has("restaurantType") && reply.has("averagePay"))
					viewHolder.payment_info.setText(reply
							.getString("restaurantType")
							+ " "
							+ getString(com.rs.mobile.wportal.R.string.rmb)
							+ reply.getString("averagePay")
							+ "/"
							+ getString(com.rs.mobile.wportal.R.string.ht_text_People));

				return convertView;

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		private class ReplyViewHolder {
			public WImageView icon_view;
			public TextView nickName;
			public TextView date;
			public RatingBar ratingBar;
			public TextView content;
			public HorizontalScrollView hsv_hscrollview;
			public LinearLayout ll_hscrollview;
			public WImageView seller_icon_view;
			public TextView seller_name;
			public TextView payment_info;
		}

	}

}
