package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.ShowImageActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.UtilCheckLogin.CheckListener;
import com.rs.mobile.wportal.adapter.ViewPagerAdapter;
import com.rs.mobile.wportal.view.rt.FullHeightListView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import okhttp3.Request;

public class RtSellerDetailActivity extends BaseActivity {

	private JSONObject mainData;

	private LinearLayout iv_back;
	private TextView tv_title;
	private ImageView iv_right1;
	private ImageView iv_right2;
	private TextView tv_right3;

	private ImageView iv_favorite;
	private ImageView iv_share;

	private ViewPager vp_seller_detail_banners;

	private TextView tv_seller_detail_sellername;

	private RatingBar rb_seller_detail_rating;

	private TextView tv_seller_detail_address;

	private TextView tv_business_hour;
	private ImageView iv_seller_detail_callphone;

	private TextView tv_seller_detail_brief;

	private FullHeightListView lv_comment_list;
	private ReplyAdapter commentsAdapter;
	private TextView tv_rate;
	private TextView tv_comment_num;
	private TextView tv_score1;
	private TextView tv_score2;
	private TextView tv_score3;

	private RadioGroup rg_comments;

	private PullToRefreshScrollView scroll_view;

	private TextView resolvation_btn;

	private TextView menu_btn;

	private String currentPage = "1";

	private String nextPage = "1";

	private String restaurantCode;

	private DecimalFormat decimalFloatFormat = new DecimalFormat("#.#");

	private Receiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_seller_detail);

		receiver = new Receiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(RtMainActivity.ACTION_RECEIVE_RT_FINISH);
		registerReceiver(receiver, filter);

		restaurantCode = getIntent().getStringExtra("restaurantCode");

		tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
		tv_title.setText(getString(com.rs.mobile.wportal.R.string.rt_seller_detail_title));

		iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		iv_right1 = (ImageView) findViewById(com.rs.mobile.wportal.R.id.iv_right1);
		iv_right1.setVisibility(View.GONE);
		// iv_right1.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// try {
		//
		// if (UiUtil.checkIsLogin(RtSellerDetailActivity.this)) {
		//
		// String imageUrl = mainData.getJSONArray("images").getJSONObject(0)
		// .getString(C.KEY_JSON_IMAGE_URL);
		//
		// UiUtil.share(RtSellerDetailActivity.this, C.TYPE_RESTRAUNT,
		// restaurantCode,
		// mainData.getString("restaurantName"), mainData.getString("address"),
		// imageUrl);
		//
		// } else {
		//
		// PageUtil.jumpTo(RtSellerDetailActivity.this, LoginActivity.class);
		// }
		//
		// } catch (Exception e) {
		//
		// L.e(e);
		//
		// }
		//
		// }
		// });

		iv_right2 = (ImageView) findViewById(com.rs.mobile.wportal.R.id.iv_right2);
		iv_right2.setVisibility(View.GONE);
		// iv_right2.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// try {
		//
		// if (UiUtil.checkIsLogin(RtSellerDetailActivity.this)) {
		//
		// addFavorite();
		//
		// } else {
		//
		// PageUtil.jumpTo(RtSellerDetailActivity.this, LoginActivity.class);
		//
		// }
		//
		// } catch (Exception e) {
		//
		// L.e(e);
		//
		// }
		//
		// }
		// });

		iv_share = (ImageView) findViewById(com.rs.mobile.wportal.R.id.iv_share);
		iv_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				UtilClear.CheckLogin(RtSellerDetailActivity.this,
						new CheckListener() {

							@Override
							public void onDoNext() {
								try {
									String imageUrl = mainData
											.getJSONArray("images")
											.getJSONObject(0)
											.getString(C.KEY_JSON_IMAGE_URL);

									UiUtil.share(
											RtSellerDetailActivity.this,
											C.TYPE_RESTRAUNT,
											restaurantCode,
											mainData.getString("restaurantName"),
											mainData.getString("address"),
											imageUrl);

								} catch (Exception e) {

									L.e(e);

								}
							}

						});

			}
		});

		iv_favorite = (ImageView) findViewById(com.rs.mobile.wportal.R.id.iv_favorite);
		iv_favorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				UtilClear.CheckLogin(RtSellerDetailActivity.this,
						new CheckListener() {

							@Override
							public void onDoNext() {
								addFavorite();
								}
							}
				);
			}
		});

		tv_right3 = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_right3);
		tv_right3.setText(getString(com.rs.mobile.wportal.R.string.rt_eating_together));
		LayoutParams p = (LayoutParams) tv_right3.getLayoutParams();
		p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		tv_right3.setLayoutParams(p);
		tv_right3.setVisibility(View.VISIBLE);
		tv_right3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				UtilClear.CheckLogin(RtSellerDetailActivity.this,
						new CheckListener() {

							@Override
							public void onDoNext() {
								Intent i = new Intent(RtSellerDetailActivity.this,
										RtReserveSelectActivity.class);
								i.putExtra("restaurantCode", restaurantCode);
								i.putExtra("from", "0");
								startActivity(i);
								}
							}
				);

			}
		});

		vp_seller_detail_banners = (ViewPager) findViewById(com.rs.mobile.wportal.R.id.vp_seller_detail_banners);

		tv_seller_detail_sellername = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_seller_detail_sellername);

		rb_seller_detail_rating = (RatingBar) findViewById(com.rs.mobile.wportal.R.id.rb_seller_detail_rating);

		tv_seller_detail_address = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_seller_detail_address);

		tv_business_hour = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_business_hour);
		iv_seller_detail_callphone = (ImageView) findViewById(com.rs.mobile.wportal.R.id.iv_seller_detail_callphone);

		tv_seller_detail_brief = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_seller_detail_brief);

		lv_comment_list = (FullHeightListView) findViewById(com.rs.mobile.wportal.R.id.lv_comment_list);
		tv_rate = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_rate);
		tv_comment_num = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_comment_num);
		tv_score1 = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_score1);
		tv_score2 = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_score2);
		tv_score3 = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_score3);

		rg_comments = (RadioGroup) findViewById(com.rs.mobile.wportal.R.id.rg_comments);

		resolvation_btn = (TextView) findViewById(com.rs.mobile.wportal.R.id.resolvation_btn);
		resolvation_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				UtilClear.CheckLogin(RtSellerDetailActivity.this,
						new CheckListener() {

							@Override
							public void onDoNext() {
								Intent i = new Intent(RtSellerDetailActivity.this,
										RtReserveSelectActivity.class);
								i.putExtra("restaurantCode", restaurantCode);
								startActivity(i);
								}
							}
				);
			}
		});

		menu_btn = (TextView) findViewById(com.rs.mobile.wportal.R.id.menu_btn);
		menu_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				UtilClear.CheckLogin(RtSellerDetailActivity.this,
						new CheckListener() {

							@Override
							public void onDoNext() {
								Intent i = new Intent(RtSellerDetailActivity.this,
										RtMenuListActivity.class);
								i.putExtra("restaurantCode", restaurantCode);
								startActivity(i);
								}
							}
				);
			}
		});

		scroll_view = (PullToRefreshScrollView) findViewById(com.rs.mobile.wportal.R.id.scroll_view);
		scroll_view.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				if (commentsAdapter != null) {
					commentsAdapter.clear();
				}
				draw();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				scroll_view.onRefreshComplete();
			}
		});

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				scroll_view.getRefreshableView().scrollTo(0, 0);
			}
		}, 500);

		draw();
	}

	@Override
	protected void onDestroy() {

		unregisterReceiver(receiver);

		super.onDestroy();
	}

	private void draw() {

		try {

			OkHttpHelper helper = new OkHttpHelper(RtSellerDetailActivity.this);

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
									mainData = data.getJSONObject("data");

									iv_favorite.setBackgroundResource(mainData
											.getString("favoriteYN")
											.equals("Y") ? com.rs.mobile.wportal.R.drawable.icon_h
											: com.rs.mobile.wportal.R.drawable.icon_favorite);

									vp_seller_detail_banners = (ViewPager) findViewById(com.rs.mobile.wportal.R.id.vp_seller_detail_banners);
									ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
											(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
											mainData, "images");
									vp_seller_detail_banners
											.setAdapter(viewPagerAdapter);

									// tv_seller_detail_sellername.setText(mainData.getString("restaurantFullName"));
									tv_seller_detail_sellername
											.setText(mainData
													.getString("restaurantName"));

									float rate = Float.parseFloat(mainData
											.getString("rate"));
									float ratePercent = rate / 10.0f;
									float rating = ratePercent * 5.0f;
									rb_seller_detail_rating.setRating(rating);

									String address = mainData
											.getString("address");
									if (!TextUtils.isEmpty(address)) {
										tv_seller_detail_address
												.setText(address);
										tv_seller_detail_address
												.setOnClickListener(new OnClickListener() {

													@Override
													public void onClick(View v) {
														// TODO Auto-generated
														// method stub

														try {

															Intent intent = new Intent();
															Bundle bundle = new Bundle();

															bundle.putDouble(
																	"location_lat",
																	Double.parseDouble(mainData
																			.getString("latitude")));
															bundle.putDouble(
																	"location_lng",
																	Double.parseDouble(mainData
																			.getString("longitude")));

															// bundle.putDouble("location_lng",
															// Double.parseDouble(mainData.getString("latitude")));
															// bundle.putDouble("location_lat",
															// Double.parseDouble(mainData.getString("longitude")));

															bundle.putString(
																	"location_name",
																	mainData.getString("restaurantName"));
															intent.setClassName(
																	"cn.ycapp.im",
																	"cn.ycapp.im.ui.activity.AMAPLocationActivity");
															intent.putExtras(bundle);
															startActivity(intent);

														} catch (Exception e) {

															L.e(e);

														}
													}
												});
									} else {
										tv_seller_detail_address.setText("");
										tv_seller_detail_address
												.setVisibility(View.GONE);
									}

									String businessTime = mainData
											.getString("businessTime");
									if (!TextUtils.isEmpty(businessTime)) {
										businessTime = getString(com.rs.mobile.wportal.R.string.common_text061)
												+ businessTime;
										tv_business_hour.setText(businessTime);
									} else {
										tv_business_hour.setText("");
									}

									final String phone = mainData
											.getString("phone");
									if (!TextUtils.isEmpty(phone)) {
										iv_seller_detail_callphone
												.setVisibility(View.VISIBLE);
										iv_seller_detail_callphone
												.setOnClickListener(new OnClickListener() {

													@Override
													public void onClick(View v) {
														Uri uri = Uri
																.parse("tel:"
																		+ phone);
														Intent callIntent = new Intent(
																Intent.ACTION_DIAL,
																uri);
														startActivity(callIntent);
													}
												});
									} else {
										iv_seller_detail_callphone
												.setVisibility(View.GONE);
									}

									tv_seller_detail_brief.setText(mainData
											.getString("info"));

									tv_rate.setText(decimalFloatFormat
											.format(rate));
									tv_comment_num.setText("("
											+ mainData.getString("rateCount")
											+ ") 评价");
									tv_score1
											.setText(getString(com.rs.mobile.wportal.R.string.common_text062)
													+ decimalFloatFormat.format(mainData
															.getDouble("score1")));
									tv_score2
											.setText(getString(com.rs.mobile.wportal.R.string.common_text063)
													+ decimalFloatFormat.format(mainData
															.getDouble("score2")));
									tv_score3
											.setText(getString(com.rs.mobile.wportal.R.string.common_text064)
													+ decimalFloatFormat.format(mainData
															.getDouble("score3")));

									if (mainData.has("reply")) {

										final JSONArray list = mainData
												.getJSONArray("reply");

										final JSONArray images = new JSONArray();

										if (list != null) {
											for (int i = 0; i < list.length(); i++) {
												JSONObject reply = list
														.getJSONObject(i);
												if (reply
														.getJSONArray("images") != null
														&& reply.getJSONArray(
																"images")
																.length() > 0) {
													images.put(reply);
												}
											}

											if (images.length() > 0) {

												rg_comments
														.setOnCheckedChangeListener(new OnCheckedChangeListener() {

															@Override
															public void onCheckedChanged(
																	RadioGroup group,
																	int checkedId) {
																if (group
																		.getCheckedRadioButtonId() == com.rs.mobile.wportal.R.id.rb_all) {
																	commentsAdapter = new ReplyAdapter(
																			list);
																	lv_comment_list
																			.setAdapter(commentsAdapter);
																} else {
																	commentsAdapter = new ReplyAdapter(
																			images);
																	lv_comment_list
																			.setAdapter(commentsAdapter);
																}
															}
														});
											} else {
												rg_comments
														.setOnCheckedChangeListener(null);
											}

											commentsAdapter = new ReplyAdapter(
													list);
											lv_comment_list
													.setAdapter(commentsAdapter);

										}
									}
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
					Constant.BASE_URL_RT + Constant.RT_RESTAURANT_DETAIL
							+ restaurantCode + "&customCode="
							+ S.getShare(this, C.KEY_REQUEST_MEMBER_ID, ""),
					params);
		} catch (Exception e) {
			L.e(e);
		}

	}

	private void drawList() {
		try {

		} catch (Exception e) {
			L.e(e);
		}
	}

	private class ReplyAdapter extends BaseAdapter {

		private JSONArray list;

		private LinearLayout.LayoutParams lp;

		ReplyAdapter(JSONArray list) {

			lp = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 82, getResources()
							.getDisplayMetrics()),
					(int) TypedValue.applyDimension(
							TypedValue.COMPLEX_UNIT_DIP, 82, getResources()
									.getDisplayMetrics()));

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
							com.rs.mobile.wportal.R.layout.list_item_rt_reply, null);
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
					convertView.setTag(viewHolder);

				} else {
					viewHolder = (ReplyViewHolder) convertView.getTag();
				}

				JSONObject reply = (JSONObject) getItem(position); // list.getJSONObject(position);

				ImageUtil.drawImageFromUri(reply.getString("iconImg"),
						viewHolder.icon_view);

				viewHolder.icon_view.setCircle(true);

				viewHolder.nickName.setText(reply.getString("nickName"));
				viewHolder.date.setText(reply.getString("date"));
				viewHolder.ratingBar.setRating(Float.parseFloat(reply
						.getString("rate")) / 2);
				viewHolder.content.setText(reply.getString("content"));

				viewHolder.ll_hscrollview.removeAllViews();

				final JSONArray images = reply.getJSONArray("images");

				if (images != null && images.length() > 0) {

					for (int i = 0; i < images.length(); i++) {

						if (i != 0) {
							lp.leftMargin = (int) TypedValue.applyDimension(
									TypedValue.COMPLEX_UNIT_DIP, 5,
									getResources().getDisplayMetrics());
						}

						WImageView image = new WImageView(
								RtSellerDetailActivity.this);
						viewHolder.ll_hscrollview.addView(image, lp);
						ImageUtil.drawImageFromUri(images.getJSONObject(i)
								.getString("imgUrl"), image);

						image.setRounding(false);

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

									PageUtil.jumpTo(
											RtSellerDetailActivity.this,
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
				}

				return convertView;

			} catch (Exception e) {
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
		}

	}

	private void addFavorite() {

		try {

			OkHttpHelper helper = new OkHttpHelper(RtSellerDetailActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(
					new OkHttpHelper.CallbackLogic() {

						@Override
						public void onNetworkError(Request request,
								IOException e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onBizSuccess(String responseDescription,
								JSONObject data, final String all_data) {
							// TODO Auto-generated method stub

							try {

								L.d(all_data);

								String status = data.getString("status");

								if (status != null && status.equals("0")) {

									iv_favorite
											.setBackgroundResource(com.rs.mobile.wportal.R.drawable.icon_favorite);

								} else if (status != null && status.equals("1")) {

									iv_favorite
											.setBackgroundResource(com.rs.mobile.wportal.R.drawable.icon_h);

								}

							} catch (Exception e) {
								L.e(e);
							}
						}

						@Override
						public void onBizFailure(String responseDescription,
								JSONObject data, String responseCode) {
							// TODO Auto-generated method stub

						}
					},
					Constant.BASE_URL_RT
							+ Constant.RT_ADD_FAVORITE
							+ "?customCode="
							+ S.getShare(RtSellerDetailActivity.this,
									C.KEY_REQUEST_MEMBER_ID, "")
							+ "&restaurantCode="
							+ restaurantCode
							+ "&token="
							+ S.getShare(RtSellerDetailActivity.this,
									C.KEY_JSON_TOKEN, ""), params);
		} catch (Exception e) {
			L.e(e);
		}

	}

	public class Receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			try {

				if (intent.getAction().equals(
						RtMainActivity.ACTION_RECEIVE_RT_FINISH)) {

					finish();

				}

			} catch (Exception e) {

				L.e(e);

			}

		}

	}

}
