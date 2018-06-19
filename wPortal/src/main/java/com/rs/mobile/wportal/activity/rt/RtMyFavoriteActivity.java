package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.ObservableScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class RtMyFavoriteActivity extends BaseActivity implements
		OnRefreshListener2<ObservableScrollView> {

	private String currentPage = "1";
	private String nextPage = "1";

	private Toolbar toolbar;
	private TextView tv_title;
	private LinearLayout iv_back;

	// private PullToRefreshListView lv_homepage;
	private PullToRefreshScrollView sv_homepage;
	private LinearLayout ll_sellerlists;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_favorates);

		initToolbar();
		initViews();
		initListeners();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		nextPage = "1";
		ll_sellerlists.removeAllViews();
		ll_sellerlists.invalidate();
		drawList();

	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		tv_title = (TextView) toolbar.findViewById(com.rs.mobile.wportal.R.id.tv_title);
		iv_back = (LinearLayout) toolbar.findViewById(com.rs.mobile.wportal.R.id.iv_back);
		setSupportActionBar(toolbar);
	}

	private void initViews() {

		tv_title.setText(getString(com.rs.mobile.wportal.R.string.rt_mine_favorates));

		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		sv_homepage = (PullToRefreshScrollView) findViewById(com.rs.mobile.wportal.R.id.sv_homepage);

		ll_sellerlists = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_sellerlists);
	}

	private void drawList() {

		OkHttpHelper helper = new OkHttpHelper(RtMyFavoriteActivity.this);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");
		// params.put("divCode", "1");
		helper.addPostRequest(
				new OkHttpHelper.CallbackLogic() {

					@Override
					public void onNetworkError(Request request, IOException e) {
						sv_homepage.onRefreshComplete();
					}

					@Override
					public void onBizSuccess(String responseDescription,
							JSONObject data, final String all_data) {
						try {
							L.d(all_data);

							sv_homepage.onRefreshComplete();

							String status = data.getString("status");

							if (status != null && status.equals("1")) {

								data = data.getJSONObject("data");

								// 상가
								JSONArray restaurantList = data
										.getJSONArray("FavoriteData");
								if (restaurantList != null
										&& restaurantList.length() > 0) {
									hideNoData();
									currentPage = data.getString("currentPage");

									nextPage = data.getString("nextPage");

									RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
											RtMainActivity.displayWidth / 4,
											RtMainActivity.displayWidth / 4);

									for (int i = 0; i < restaurantList.length(); i++) {

										try {

											final JSONObject item = restaurantList
													.getJSONObject(i);

											RelativeLayout rl_seller_item = (RelativeLayout) getLayoutInflater()
													.inflate(
															com.rs.mobile.wportal.R.layout.layout_rt_sellerlist_item,
															null);

											WImageView iv_thumbnail = (WImageView) rl_seller_item
													.findViewById(com.rs.mobile.wportal.R.id.iv_thumbnail);

											TextView tv_sellername = (TextView) rl_seller_item
													.findViewById(com.rs.mobile.wportal.R.id.tv_sellername);

											RatingBar rb_sellerrating = (RatingBar) rl_seller_item
													.findViewById(com.rs.mobile.wportal.R.id.rb_sellerrating);

											TextView tv_price = (TextView) rl_seller_item
													.findViewById(com.rs.mobile.wportal.R.id.tv_price);

											TextView tv_foodType = (TextView) rl_seller_item
													.findViewById(com.rs.mobile.wportal.R.id.tv_foodType);

											TextView tv_distance = (TextView) rl_seller_item
													.findViewById(com.rs.mobile.wportal.R.id.tv_distance);

											TextView tv_count = (TextView) rl_seller_item
													.findViewById(com.rs.mobile.wportal.R.id.tv_count);

											ImageUtil
													.drawIamge(
															iv_thumbnail,
															Uri.parse(item
																	.getString("shopThumImage")),
															params);

											iv_thumbnail.setRounding(true);

											tv_sellername
													.setText(item
															.getString("restaurantName"));

											rb_sellerrating
													.setRating(Float.parseFloat(item
															.getString("averageRate")));

											tv_price.setText(getString(com.rs.mobile.wportal.R.string.rmb)
													+ " "
													+ item.getString("averagePay")
													+ getString(com.rs.mobile.wportal.R.string.rt_seller_list_01));

											// 위치 정보
											tv_foodType.setText(item
													.getString("floor"));

											// RelativeLayout.LayoutParams
											// floorParams =
											// (android.widget.RelativeLayout.LayoutParams)
											// tv_foodType.getLayoutParams();
											//
											// floorParams.addRule(RelativeLayout.ALIGN_BOTTOM,
											// R.id.iv_thumbnail);
											//
											// tv_foodType.setLayoutParams(floorParams);

											// 거리정보
											tv_distance.setText(item
													.getString("distance"));

											// RelativeLayout.LayoutParams
											// distanceParams =
											// (android.widget.RelativeLayout.LayoutParams)
											// tv_distance.getLayoutParams();
											//
											// distanceParams.addRule(RelativeLayout.ALIGN_BOTTOM,
											// R.id.iv_thumbnail);
											//
											// tv_distance.setLayoutParams(distanceParams);

											// 방문수
											tv_count.setText(getString(com.rs.mobile.wportal.R.string.rt_seller_list_02)
													+ " "
													+ item.getString("salesCount"));

											// RelativeLayout.LayoutParams
											// countParams =
											// (android.widget.RelativeLayout.LayoutParams)
											// tv_count.getLayoutParams();
											//
											// countParams.addRule(RelativeLayout.ALIGN_BOTTOM,
											// R.id.iv_thumbnail);
											//
											// tv_count.setLayoutParams(countParams);

											rl_seller_item
													.setOnClickListener(new OnClickListener() {

														@Override
														public void onClick(
																View v) {
															sellerItemClicked(
																	v, item);
														}

													});

											rl_seller_item
													.setOnLongClickListener(new OnLongClickListener() {

														@Override
														public boolean onLongClick(
																View v) {
															// TODO
															// Auto-generated
															// method stub

															showDialog(
																	getString(com.rs.mobile.wportal.R.string.delete),
																	getString(com.rs.mobile.wportal.R.string.msg_delete),
																	getString(com.rs.mobile.wportal.R.string.delete),
																	new OnClickListener() {

																		@Override
																		public void onClick(
																				View v) {
																			// TODO
																			// Auto-generated
																			// method
																			// stub

																			try {

																				OkHttpHelper helper = new OkHttpHelper(
																						RtMyFavoriteActivity.this);

																				HashMap<String, String> params = new HashMap<String, String>();

																				params.put(
																						"",
																						"");

																				helper.addPostRequest(
																						new OkHttpHelper.CallbackLogic() {

																							@Override
																							public void onNetworkError(
																									Request request,
																									IOException e) {
																								// TODO
																								// Auto-generated
																								// method
																								// stub

																							}

																							@Override
																							public void onBizSuccess(
																									String responseDescription,
																									JSONObject data,
																									final String all_data) {
																								// TODO
																								// Auto-generated
																								// method
																								// stub

																								try {

																									L.d(all_data);

																									String status = data
																											.getString("status");

																									if (status != null
																											&& status
																													.equals("0")) {

																										nextPage = "1";
																										ll_sellerlists
																												.removeAllViews();
																										ll_sellerlists
																												.invalidate();
																										drawList();

																									}
																								} catch (Exception e) {
																									L.e(e);
																								}
																							}

																							@Override
																							public void onBizFailure(
																									String responseDescription,
																									JSONObject data,
																									String responseCode) {
																								// TODO
																								// Auto-generated
																								// method
																								// stub

																							}
																						},
																						Constant.BASE_URL_RT
																								+ Constant.RT_ADD_FAVORITE
																								+ "?customCode="
																								+ S.getShare(
																										RtMyFavoriteActivity.this,
																										C.KEY_REQUEST_MEMBER_ID,
																										"")
																								+ "&restaurantCode="
																								+ item.getString("restaurantCode")
																								+ "&token="
																								+ S.getShare(
																										RtMyFavoriteActivity.this,
																										C.KEY_JSON_TOKEN,
																										""),
																						params);
																			} catch (Exception e) {
																				L.e(e);
																			}

																			D.alertDialog
																					.dismiss();

																		}
																	},
																	getString(com.rs.mobile.wportal.R.string.cancel),
																	new OnClickListener() {

																		@Override
																		public void onClick(
																				View v) {
																			// TODO
																			// Auto-generated
																			// method
																			// stub
																			D.alertDialog
																					.dismiss();
																		}
																	});

															return false;
														}
													});

											ll_sellerlists
													.addView(rl_seller_item);

											LayoutParams marginParams = (LayoutParams) rl_seller_item
													.getLayoutParams();

											marginParams.setMargins(0, 0, 0, 1);

											rl_seller_item
													.setLayoutParams(marginParams);

										} catch (Exception e) {

											L.e(e);

										}

									}
								} else {

									showNoData();

								}

							} else {
								showNoData();

							}

						} catch (Exception e) {
							L.e(e);
						}
					}

					@Override
					public void onBizFailure(String responseDescription,
							JSONObject data, String responseCode) {
						sv_homepage.onRefreshComplete();
					}
				},
				Constant.BASE_URL_RT
						+ Constant.RT_MY_FAVORITE
						+ "?userID="
						+ S.getShare(RtMyFavoriteActivity.this,
								C.KEY_REQUEST_MEMBER_ID, "")
						+ "&currentPage="
						+ nextPage
						+ "&token="
						+ S.getShare(RtMyFavoriteActivity.this,
								C.KEY_JSON_TOKEN, ""), params);

	}

	private void initListeners() {
		sv_homepage.setOnRefreshListener(this);
	}

	private void sellerItemClicked(View v, JSONObject item) {
		try {
			Intent sellerDetailIntent = new Intent(this,
					RtSellerDetailActivity.class);
			sellerDetailIntent.putExtra("restaurantCode",
					item.getString("restaurantCode"));
			startActivity(sellerDetailIntent);
		} catch (Exception e) {
			L.e(e);
		}
	}

	@Override
	public void onBackPressed() {
		onBack();
	}

	private void onBack() {
		finish();
	}

	@Override
	public void onPullDownToRefresh(
			PullToRefreshBase<ObservableScrollView> refreshView) {
		nextPage = "1";
		ll_sellerlists.removeAllViews();
		ll_sellerlists.invalidate();
		drawList();
	}

	@Override
	public void onPullUpToRefresh(
			PullToRefreshBase<ObservableScrollView> refreshView) {
		if (nextPage != null && !nextPage.equals("") && !nextPage.equals("0")) {
			drawList();
		} else {
			sv_homepage.onRefreshComplete();
		}
	}

}
