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
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.LocationActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.rt.RtHomePageADAdapter;
import com.rs.mobile.wportal.adapter.rt.RtHomePageGridAdapter;
import com.willli.gridpager.GridViewPager;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.wportal.A;
import com.rs.mobile.wportal.CaptureUtil;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

/**
 * 订单模块主页
 * 
 * @author ZhaoYun
 * @date 2017-3-11
 */
public class RtHomePageActivity extends BaseActivity implements OnClickListener, OnRefreshListener2<ObservableScrollView> {

	// private String divcode;
	// private String divName;
	private String currentPage = "1";
	private String nextPage = "1";

	private Toolbar toolbar;
	private LinearLayout ll_div;
	private TextView tv_divname;
	private LinearLayout ll_search;
	private ImageView iv_right;

	// private PullToRefreshListView lv_homepage;
	private PullToRefreshScrollView sv_homepage;
	private GridViewPager gvp_sellertype;
	private ViewPager vp_sellerad;
	private LinearLayout ll_sellerlists;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_homepage);
		// {"divCode":"4","zipCode":"410002","divName":"于成大型超市3","lon":"28.1167930","lat":"112.9826590","areaName":null}
		// divcode = getIntent().getStringExtra("divcode");
		// divName = getIntent().getStringExtra("divName");
		// divcode = "1";
		// divName = "朝阳广场";
		initToolbar();
		initViews();
		initDatas();
		initListeners();
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		ll_div = (LinearLayout) toolbar.findViewById(com.rs.mobile.wportal.R.id.ll_div);
		tv_divname = (TextView) toolbar.findViewById(com.rs.mobile.wportal.R.id.tv_divname);
		ll_search = (LinearLayout) toolbar.findViewById(com.rs.mobile.wportal.R.id.ll_search);
		iv_right = (ImageView) toolbar.findViewById(com.rs.mobile.wportal.R.id.iv_right);
		setSupportActionBar(toolbar);
	}

	private void initViews() {
		// lv_homepage = (PullToRefreshListView) findViewById(R.id.lv_homepage);
		tv_divname.setText(C.DIV_NAME);
		sv_homepage = (PullToRefreshScrollView) findViewById(com.rs.mobile.wportal.R.id.sv_homepage);
		// gvp_sellertype_layout = (LinearLayout)
		// getLayoutInflater().inflate(R.layout.layout_rt_sellertype_grid,
		// null);
		gvp_sellertype = (GridViewPager) findViewById(com.rs.mobile.wportal.R.id.gvp_sellertype);
		// vp_sellerad_layout = (LinearLayout)
		// getLayoutInflater().inflate(R.layout.layout_rt_sellerad_pager, null);
		vp_sellerad = (ViewPager) findViewById(com.rs.mobile.wportal.R.id.vp_sellerad);
		ll_sellerlists = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_sellerlists);
	}

	private void initDatas() {

		OkHttpHelper helper = new OkHttpHelper(RtHomePageActivity.this);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");
		// params.put("divCode", "1");
		helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				sv_homepage.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {

				try {

					L.d(all_data);

					sv_homepage.onRefreshComplete();

					String status = data.getString("status");

					if (status != null && status.equals("1")) {

						data = data.getJSONObject("data");

						// 메뉴 리스트
						JSONArray foodCodeList = data.getJSONObject("foodCode").getJSONArray("foodCodeList");
						gvp_sellertype.setAdapter(new RtHomePageGridAdapter(RtHomePageActivity.this, foodCodeList,
								RtMainActivity.displayWidth / 7));

						// 광고
						JSONArray advertiseList = data.getJSONObject("advertise").getJSONArray("advertiseList");
						vp_sellerad.setAdapter(new RtHomePageADAdapter(RtHomePageActivity.this, advertiseList,
								RtMainActivity.displayWidth / 4));

						// 상가
						ll_sellerlists.removeAllViews();
						JSONArray restaurantList = data.getJSONObject("restaurant").getJSONArray("restaurantList");
						currentPage = data.getJSONObject("restaurant").getString("currentPage");
						nextPage = data.getJSONObject("restaurant").getString("nextPage");

						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
								RtMainActivity.displayWidth / 4, RtMainActivity.displayWidth / 4);

						for (int i = 0; i < restaurantList.length(); i++) {

							try {

								final JSONObject item = restaurantList.getJSONObject(i);

								RelativeLayout rl_seller_item = (RelativeLayout) getLayoutInflater()
										.inflate(com.rs.mobile.wportal.R.layout.layout_rt_sellerlist_item, null);

								WImageView iv_thumbnail = (WImageView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.iv_thumbnail);

								TextView tv_sellername = (TextView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.tv_sellername);

								RatingBar rb_sellerrating = (RatingBar) rl_seller_item
										.findViewById(com.rs.mobile.wportal.R.id.rb_sellerrating);

								TextView tv_price = (TextView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.tv_price);

								final TextView tv_foodType = (TextView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.tv_foodType);

								final TextView tv_distance = (TextView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.tv_distance);

								final TextView tv_count = (TextView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.tv_count);

								ImageUtil.drawIamge(iv_thumbnail, Uri.parse(item.getString("shopThumImage")), params);

								// iv_thumbnail.setRounding(true);

								tv_sellername.setText(item.getString("restaurantName"));

								rb_sellerrating.setRating(Float.parseFloat(item.getString("averageRate")) / 2);

								tv_price.setText(getString(com.rs.mobile.wportal.R.string.rmb) + " " + item.getString("averagePay")
										+ getString(com.rs.mobile.wportal.R.string.rt_seller_list_01));

								// 위치 정보
								tv_foodType.setText(item.getString("floor"));

								// 거리정보
								tv_distance.setText(item.getString("distance"));

								// 방문수
								tv_count.setText(
										getString(com.rs.mobile.wportal.R.string.rt_seller_list_02) + " " + item.getString("salesCount"));

								rl_seller_item.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										sellerItemClicked(v, item);

									}

								});

								ll_sellerlists.addView(rl_seller_item);

								LayoutParams marginParams = (LayoutParams) rl_seller_item.getLayoutParams();

								marginParams.setMargins(0, 0, 0, 1);

								rl_seller_item.setLayoutParams(marginParams);

								ll_sellerlists.invalidate();

								// new Handler().postDelayed(new Runnable() {
								//
								// @Override
								// public void run() {
								// // TODO Auto-generated method stub
								//
								// //위치 정보
								// RelativeLayout.LayoutParams floorParams =
								// (android.widget.RelativeLayout.LayoutParams)
								// tv_foodType.getLayoutParams();
								//
								// floorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
								// RelativeLayout.TRUE);
								//
								// tv_foodType.setLayoutParams(floorParams);
								//
								// //거리정보
								// RelativeLayout.LayoutParams distanceParams =
								// (android.widget.RelativeLayout.LayoutParams)
								// tv_distance.getLayoutParams();
								//
								// distanceParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
								// RelativeLayout.TRUE);
								//
								// tv_distance.setLayoutParams(distanceParams);
								//
								// //방문수
								// RelativeLayout.LayoutParams countParams =
								// (android.widget.RelativeLayout.LayoutParams)
								// tv_count.getLayoutParams();
								//
								// countParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
								// RelativeLayout.TRUE);
								//
								// tv_count.setLayoutParams(countParams);
								//
								// ll_sellerlists.invalidate();
								//
								// }
								// }, 1000);

							} catch (Exception e) {

								L.e(e);

							}
						}
					}
				} catch (Exception e) {
					L.e(e);
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				sv_homepage.onRefreshComplete();
			}
		}, Constant.BASE_URL_RT + Constant.RT_MAIN + "?divcode=" + C.DIV_CODE, params);

	}

	private void addList() {

		OkHttpHelper helper = new OkHttpHelper(RtHomePageActivity.this);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");
		helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				sv_homepage.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
				try {
					L.d(all_data);
					sv_homepage.onRefreshComplete();
					String status = data.getString("status");
					if (status != null && status.equals("1")) {
						data = data.getJSONObject("data");
						JSONArray restaurantList = data.getJSONArray("restaurantList");
						currentPage = data.getString("currentPage");
						nextPage = data.getString("nextPage");
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
								RtMainActivity.displayWidth / 4, RtMainActivity.displayWidth / 4);
						for (int i = 0; i < restaurantList.length(); i++) {
							try {
								final JSONObject item = restaurantList.getJSONObject(i);
								RelativeLayout rl_seller_item = (RelativeLayout) getLayoutInflater()
										.inflate(com.rs.mobile.wportal.R.layout.layout_rt_sellerlist_item, null);
								WImageView iv_thumbnail = (WImageView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.iv_thumbnail);
								TextView tv_sellername = (TextView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.tv_sellername);
								RatingBar rb_sellerrating = (RatingBar) rl_seller_item
										.findViewById(com.rs.mobile.wportal.R.id.rb_sellerrating);
								TextView tv_price = (TextView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.tv_price);
								TextView tv_foodType = (TextView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.tv_foodType);
								TextView tv_distance = (TextView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.tv_distance);
								TextView tv_count = (TextView) rl_seller_item.findViewById(com.rs.mobile.wportal.R.id.tv_count);
								ImageUtil.drawIamge(iv_thumbnail, Uri.parse(item.getString("shopThumImage")), params);

								iv_thumbnail.setRounding(true);

								tv_sellername.setText(item.getString("restaurantName"));
								rb_sellerrating.setRating(Float.parseFloat(item.getString("averageRate")));
								tv_price.setText(getString(com.rs.mobile.wportal.R.string.rmb) + " " + item.getString("averagePay")
										+ getString(com.rs.mobile.wportal.R.string.rt_seller_list_01));
								tv_foodType.setText(item.getString("floor"));
								tv_distance.setText(item.getString("distance"));
								tv_count.setText(
										getString(com.rs.mobile.wportal.R.string.rt_seller_list_02) + " " + item.getString("salesCount"));
								rl_seller_item.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										sellerItemClicked(v, item);
									}

								});
								ll_sellerlists.addView(rl_seller_item);
								LayoutParams marginParams = (LayoutParams) rl_seller_item.getLayoutParams();
								marginParams.setMargins(0, 0, 0, 1);
								rl_seller_item.setLayoutParams(marginParams);
							} catch (Exception e) {
								L.e(e);
							}
						}
					}
				} catch (Exception e) {
					L.e(e);
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				sv_homepage.onRefreshComplete();
			}
		}, Constant.BASE_URL_RT + Constant.RT_MAIN_LIST + "?divcode=" + C.DIV_CODE + "&currentPage=" + nextPage,
				params);

	}

	private void initListeners() {
		ll_div.setOnClickListener(this);
		ll_search.setOnClickListener(this);
		iv_right.setOnClickListener(this);
		sv_homepage.setOnRefreshListener(this);
	}

	private void sellerItemClicked(View v, JSONObject item) {
		try {
			Intent sellerDetailIntent = new Intent(this, RtSellerDetailActivity.class);
			sellerDetailIntent.putExtra("restaurantCode", item.getString("restaurantCode"));
			startActivity(sellerDetailIntent);
		} catch (Exception e) {
			L.e(e);
		}
	}

	private void jumpToSearch(int divCode) {
		Intent searchSellerIntent = new Intent(this, RtSearchSellerActivity.class);
		searchSellerIntent.putExtra(C.EXTRA_KEY_DIVCODE, divCode);
		startActivity(searchSellerIntent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case com.rs.mobile.wportal.R.id.ll_div:
			try {

				Intent i = new Intent(RtHomePageActivity.this, LocationActivity.class);

				i.putExtra("type", C.TYPE_RESTRAUNT);

				Location location = A.getLocation();

				if (location != null) {

					i.putExtra("lon", "" + location.getLongitude());

					i.putExtra("lat", "" + location.getLatitude());

				} else {

					// 기본 위치 설정
					i.putExtra("lon", "" + "113.027417");

					i.putExtra("lat", "" + "28.184747");

				}

				startActivityForResult(i, 1000);

			} catch (Exception e) {

				L.e(e);

			}
			break;

		case com.rs.mobile.wportal.R.id.ll_search:
			jumpToSearch(1);
			break;

		case com.rs.mobile.wportal.R.id.iv_right:
			Intent i = new Intent(RtHomePageActivity.this, CaptureActivity.class);
			startActivityForResult(i, 2000);
			break;

		default:
			break;
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 1000) {

				C.DIV_CODE = data.getStringExtra("divCode");

				C.DIV_NAME = data.getStringExtra("divName");

				tv_divname.setText(C.DIV_NAME);

				initDatas();
			} else if (requestCode == 2000) { // captureActivity

				CaptureUtil.handleResultScaning(RtHomePageActivity.this, data.getStringExtra("result"), "");

			}
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		ll_sellerlists.removeAllViews();
		ll_sellerlists.invalidate();
		initDatas();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		if (nextPage != null && !nextPage.equals("") && !nextPage.equals("0")) {
			addList();
		} else {
			sv_homepage.onRefreshComplete();
		}
	}

}
