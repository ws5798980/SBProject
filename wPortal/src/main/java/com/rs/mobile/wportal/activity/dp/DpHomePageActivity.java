package com.rs.mobile.wportal.activity.dp;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ObservableScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.common.activity.LocationActivity;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.A;
import com.rs.mobile.wportal.CaptureUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.dp.DpViewPagerAdapter;
import com.rs.mobile.wportal.view.CustomViewPager;
import com.rs.mobile.wportal.view.MenuButtonView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class DpHomePageActivity extends BaseActivity
		implements OnClickListener, OnRefreshListener2<ObservableScrollView> {
	private Toolbar toolbar;
	private LinearLayout ll_div;
	private TextView tv_divname;
	private LinearLayout ll_search;
	private ImageView iv_right;

	private CustomViewPager viewPager;
	private MenuButtonView menu01;
	private MenuButtonView menu02;
	private MenuButtonView menu03;
	private MenuButtonView menu04;
	private MenuButtonView menu05;
	private MenuButtonView menu06;
	private MenuButtonView menu07;
	private MenuButtonView menu08;
	private RelativeLayout rela_001, rela_002;
	private TextView floor_num, level_name, floor_num2, level_name2;
	private WImageView banner_001, banner_002, banner_003, banner_004, banner_005, banner_006, banner_007, banner_008;
	private Activity activity = DpHomePageActivity.this;
	private PullToRefreshScrollView sv_homepage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_dp_homepage);
		initToolbar();
		initView();
		getMainData();
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		ll_div = (LinearLayout) toolbar.findViewById(com.rs.mobile.wportal.R.id.ll_div);
		ll_div.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showLocationActivity();
			}
		});
		tv_divname = (TextView) toolbar.findViewById(com.rs.mobile.wportal.R.id.tv_divname);
		tv_divname.setText(C.DIV_NAME);
		ll_search = (LinearLayout) toolbar.findViewById(com.rs.mobile.wportal.R.id.ll_search);
		ll_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(activity, DpSeachActivity.class);
			}
		});
		iv_right = (ImageView) toolbar.findViewById(com.rs.mobile.wportal.R.id.iv_right);
		iv_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UiUtil.startActivityForResult(activity, 2000, new Intent(activity, CaptureActivity.class),
						UiUtil.TYPE_BUTTON, iv_right);
			}
		});
		setSupportActionBar(toolbar);
	}

	private void initViewPager(JSONObject data) {

		DpViewPagerAdapter adapter = new DpViewPagerAdapter(DpHomePageActivity.this, data, (float) 2.5);
		viewPager.setAdapter(adapter);

	}

	private void initView() {

		sv_homepage = (PullToRefreshScrollView) findViewById(com.rs.mobile.wportal.R.id.sv_homepage);
		sv_homepage.setOnRefreshListener(this);
		sv_homepage.setMode(Mode.PULL_FROM_START);
		viewPager = (CustomViewPager) findViewById(com.rs.mobile.wportal.R.id.view_pager);
		viewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		menu01 = (MenuButtonView) findViewById(com.rs.mobile.wportal.R.id.dp_menu_01);
		menu01.setIcon(ContextCompat.getDrawable(this, com.rs.mobile.wportal.R.drawable.dp_icon_ipp));
		menu01.setText(com.rs.mobile.wportal.R.string.dp_text_btn1);
		menu01.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);

		menu02 = (MenuButtonView) findViewById(com.rs.mobile.wportal.R.id.dp_menu_02);
		menu02.setIcon(ContextCompat.getDrawable(this, com.rs.mobile.wportal.R.drawable.dp_icon_ihd));
		menu02.setText(com.rs.mobile.wportal.R.string.dp_text_btn2);
		menu02.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);

		menu03 = (MenuButtonView) findViewById(com.rs.mobile.wportal.R.id.dp_menu_03);
		menu03.setIcon(ContextCompat.getDrawable(this, com.rs.mobile.wportal.R.drawable.dp_icon_iwh));
		menu03.setText(com.rs.mobile.wportal.R.string.dp_text_btn6);
		menu03.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);

		menu04 = (MenuButtonView) findViewById(com.rs.mobile.wportal.R.id.dp_menu_04);
		menu04.setIcon(ContextCompat.getDrawable(this, com.rs.mobile.wportal.R.drawable.dp_icon_isc));
		menu04.setText(com.rs.mobile.wportal.R.string.dp_text_btn4);
		menu04.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);

		menu05 = (MenuButtonView) findViewById(com.rs.mobile.wportal.R.id.dp_menu_05);
		menu05.setIcon(ContextCompat.getDrawable(this, com.rs.mobile.wportal.R.drawable.dp_icon_icc));
		menu05.setText(com.rs.mobile.wportal.R.string.dp_text_btn5);
		menu05.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);

		menu06 = (MenuButtonView) findViewById(com.rs.mobile.wportal.R.id.dp_menu_06);
		menu06.setIcon(ContextCompat.getDrawable(this, com.rs.mobile.wportal.R.drawable.icon_myorder));
		menu06.setText(com.rs.mobile.wportal.R.string.sm_my_order);
		menu06.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);

		menu07 = (MenuButtonView) findViewById(com.rs.mobile.wportal.R.id.dp_menu_07);
		menu07.setIcon(ContextCompat.getDrawable(this, com.rs.mobile.wportal.R.drawable.icon_wallet));
		menu07.setText(com.rs.mobile.wportal.R.string.icon_05);
		menu07.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);

		menu08 = (MenuButtonView) findViewById(com.rs.mobile.wportal.R.id.dp_menu_08);
		menu08.setIcon(ContextCompat.getDrawable(this, com.rs.mobile.wportal.R.drawable.dp_icon_ikf));
		menu08.setText(com.rs.mobile.wportal.R.string.dp_text_btn8);
		menu08.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);

		menu01.setOnClickListener(this);
		menu02.setOnClickListener(this);
		menu03.setOnClickListener(this);
		menu04.setOnClickListener(this);
		menu05.setOnClickListener(this);
		menu06.setOnClickListener(this);
		menu07.setOnClickListener(this);
		menu08.setOnClickListener(this);

		rela_001 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela_001);
		rela_001.setOnClickListener(this);
		rela_002 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela_002);
		rela_002.setOnClickListener(this);

		floor_num = (TextView) findViewById(com.rs.mobile.wportal.R.id.floor_num);
		level_name = (TextView) findViewById(com.rs.mobile.wportal.R.id.level_name);
		floor_num2 = (TextView) findViewById(com.rs.mobile.wportal.R.id.floor_num2);
		level_name2 = (TextView) findViewById(com.rs.mobile.wportal.R.id.level_name2);

		banner_001 = (WImageView) findViewById(com.rs.mobile.wportal.R.id.banner_001);
		banner_002 = (WImageView) findViewById(com.rs.mobile.wportal.R.id.banner_002);
		banner_003 = (WImageView) findViewById(com.rs.mobile.wportal.R.id.banner_003);
		banner_004 = (WImageView) findViewById(com.rs.mobile.wportal.R.id.banner_004);
		banner_005 = (WImageView) findViewById(com.rs.mobile.wportal.R.id.banner_005);
		banner_006 = (WImageView) findViewById(com.rs.mobile.wportal.R.id.banner_006);
		banner_007 = (WImageView) findViewById(com.rs.mobile.wportal.R.id.banner_007);
		banner_008 = (WImageView) findViewById(com.rs.mobile.wportal.R.id.banner_008);

	}

	private void getMainData() {
		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();

		try {

			obj.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
			obj.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
			obj.put("lang_type", "chn");
			obj.put("div", C.DIV_CODE);
			// obj1.put("myData", obj.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;Charset=UTF-8");
		OkHttpHelper okHttpHelper = new OkHttpHelper(DpHomePageActivity.this);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				sv_homepage.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv_homepage.onRefreshComplete();

				try {
					JSONObject obj = new JSONObject();

					obj = data;

					initViewPager(obj);
					S.setShare(getApplicationContext(), C.KEY_JSON_BANNER, obj.toString());

					JSONArray category = obj.getJSONArray("category");
					JSONArray arrFloor1 = new JSONArray();
					JSONArray arrFloor2 = new JSONArray();
					for (int i = 0; i < category.length(); i++) {
						if (category.getJSONObject(i).getString("floor_num").equals("1")) {
							arrFloor1 = category.getJSONObject(i).getJSONArray("data");
							level_name.setText(category.getJSONObject(i).getString("level_name"));
							for (int j = 0; j < arrFloor1.length(); j++) {
								if (arrFloor1.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
										.getString("display_num").contains("a")) {
									ImageUtil.drawImageFromUri(arrFloor1.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).getString("image_url"), banner_001);
									final String custom_code = arrFloor1.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).get("custom_code").toString();
									banner_001.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											Bundle bundle = new Bundle();
											bundle.putString("custom_code", custom_code);
											bundle.putString("flag", "custom_code");
											PageUtil.jumpTo(DpHomePageActivity.this, DpSerchResultActivity.class,
													bundle);
										}
									});
								}
								;
							}
							for (int j = 0; j < arrFloor1.length(); j++) {
								if (arrFloor1.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
										.getString("display_num").contains("b")) {
									ImageUtil.drawImageFromUri(arrFloor1.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).getString("image_url"), banner_002);
									final String custom_code = arrFloor1.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).get("custom_code").toString();
									banner_002.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											Bundle bundle = new Bundle();
											bundle.putString("custom_code", custom_code);
											bundle.putString("flag", "custom_code");
											PageUtil.jumpTo(DpHomePageActivity.this, DpSerchResultActivity.class,
													bundle);
										}
									});

								}
								;
							}
							for (int j = 0; j < arrFloor1.length(); j++) {
								if (arrFloor1.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
										.getString("display_num").contains("c")) {
									ImageUtil.drawImageFromUri(arrFloor1.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).getString("image_url"), banner_003);
									final String custom_code = arrFloor1.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).get("custom_code").toString();
									banner_003.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											Bundle bundle = new Bundle();
											bundle.putString("custom_code", custom_code);
											bundle.putString("flag", "custom_code");
											PageUtil.jumpTo(DpHomePageActivity.this, DpSerchResultActivity.class,
													bundle);
										}
									});

								}
								;
							}
							for (int j = 0; j < arrFloor1.length(); j++) {
								if (arrFloor1.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
										.getString("display_num").contains("d")) {
									ImageUtil.drawImageFromUri(arrFloor1.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).getString("image_url"), banner_004);
									final String custom_code = arrFloor1.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).get("custom_code").toString();
									banner_004.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											Bundle bundle = new Bundle();
											bundle.putString("custom_code", custom_code);
											bundle.putString("flag", "custom_code");
											PageUtil.jumpTo(DpHomePageActivity.this, DpSerchResultActivity.class,
													bundle);
										}
									});

								}
								;
							}
						} else if (category.getJSONObject(i).getString("floor_num").equals("2")) {
							arrFloor2 = category.getJSONObject(i).getJSONArray("data");

							level_name2.setText(category.getJSONObject(i).getString("level_name"));
							for (int j = 0; j < arrFloor2.length(); j++) {
								if (arrFloor2.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
										.getString("display_num").contains("a")) {
									ImageUtil.drawImageFromUri(arrFloor2.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).getString("image_url"), banner_005);
									final String custom_code = arrFloor2.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).get("custom_code").toString();
									banner_005.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											Bundle bundle = new Bundle();
											bundle.putString("custom_code", custom_code);
											bundle.putString("flag", "custom_code");
											PageUtil.jumpTo(DpHomePageActivity.this, DpSerchResultActivity.class,
													bundle);
										}
									});

								}
								;
							}
							for (int j = 0; j < arrFloor2.length(); j++) {
								if (arrFloor2.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
										.getString("display_num").contains("b")) {
									ImageUtil.drawImageFromUri(arrFloor2.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).getString("image_url"), banner_006);
									final String custom_code = arrFloor2.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).get("custom_code").toString();
									banner_006.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											Bundle bundle = new Bundle();
											bundle.putString("custom_code", custom_code);
											bundle.putString("flag", "custom_code");
											PageUtil.jumpTo(DpHomePageActivity.this, DpSerchResultActivity.class,
													bundle);
										}
									});
								}
								;
							}
							for (int j = 0; j < arrFloor2.length(); j++) {
								if (arrFloor2.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
										.getString("display_num").contains("c")) {
									ImageUtil.drawImageFromUri(arrFloor2.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).getString("image_url"), banner_007);
									final String custom_code = arrFloor2.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).get("custom_code").toString();
									banner_007.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											Bundle bundle = new Bundle();
											bundle.putString("custom_code", custom_code);
											bundle.putString("flag", "custom_code");
											PageUtil.jumpTo(DpHomePageActivity.this, DpSerchResultActivity.class,
													bundle);
										}
									});

								}
								;
							}
							for (int j = 0; j < arrFloor2.length(); j++) {
								if (arrFloor2.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
										.getString("display_num").contains("d")) {
									ImageUtil.drawImageFromUri(arrFloor2.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).getString("image_url"), banner_008);
									final String custom_code = arrFloor2.getJSONObject(j).getJSONArray("eventImage")
											.getJSONObject(0).get("custom_code").toString();
									banner_008.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											Bundle bundle = new Bundle();
											bundle.putString("custom_code", custom_code);
											bundle.putString("flag", "custom_code");
											PageUtil.jumpTo(DpHomePageActivity.this, DpSerchResultActivity.class,
													bundle);
										}
									});

								}
								;
							}
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
					L.e(e);
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv_homepage.onRefreshComplete();
			}
		}, Constant.BASE_URL_DP + Constant.DP_GETHOMEPAGE, headers, obj.toString());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case com.rs.mobile.wportal.R.id.dp_menu_01:
			UiUtil.startActivity(DpHomePageActivity.this,
					new Intent(DpHomePageActivity.this, DpBrandListActivity.class), UiUtil.TYPE_BUTTON, menu01);

			break;
		case com.rs.mobile.wportal.R.id.dp_menu_02:
			UiUtil.startActivity(DpHomePageActivity.this, new Intent(DpHomePageActivity.this, DpActionActivity.class),
					UiUtil.TYPE_BUTTON, menu02);

			break;
		case com.rs.mobile.wportal.R.id.dp_menu_03:
			// if (UiUtil.checkIsLogin(activity)) {
			// UiUtil.startActivity(DpHomePageActivity.this,
			// new Intent(DpHomePageActivity.this, DpCouponActivity.class),
			// UiUtil.TYPE_BUTTON, menu03);
			// } else {
			// UiUtil.startActivity(DpHomePageActivity.this, new
			// Intent(DpHomePageActivity.this, LoginActivity.class),
			// UiUtil.TYPE_BUTTON, menu03);
			// }
			UiUtil.startActivity(DpHomePageActivity.this, new Intent(DpHomePageActivity.this, DpCultureActivity.class),
					UiUtil.TYPE_BUTTON, menu03);
			break;
		case com.rs.mobile.wportal.R.id.dp_menu_04:
			UiUtil.startActivity(DpHomePageActivity.this, new Intent(DpHomePageActivity.this, DpMyMallActivity.class),
					UiUtil.TYPE_BUTTON, menu04);
			break;
		case com.rs.mobile.wportal.R.id.dp_menu_05:
			UiUtil.startActivity(DpHomePageActivity.this,
					new Intent(DpHomePageActivity.this, DpFloorListActivity.class), UiUtil.TYPE_BUTTON, menu05);
			break;
		case com.rs.mobile.wportal.R.id.dp_menu_06:
			UiUtil.startActivity(DpHomePageActivity.this, new Intent(DpHomePageActivity.this, DpMyOrderActivity.class),
					UiUtil.TYPE_BUTTON, menu06);
			break;
		case com.rs.mobile.wportal.R.id.dp_menu_07:
			UtilClear.IntentToLongLiao(DpHomePageActivity.this, "cn.ycapp.im.ui.mywallet.MyWalletActivity","");
			break;
		case com.rs.mobile.wportal.R.id.dp_menu_08:
			Bundle bundle2 = new Bundle();
			bundle2.putString(C.KEY_INTENT_URL, "http://ssadmin.dxbhtm.com:8090/20_CM/cmList.aspx");
			PageUtil.jumpTo(DpHomePageActivity.this, WebActivity.class, bundle2);
			break;
		case com.rs.mobile.wportal.R.id.rela_001:
			Bundle bundle = new Bundle();
			bundle.putString("floor_num", "1");
			PageUtil.jumpTo(DpHomePageActivity.this, DpFloorDetailActivity.class, bundle);
			break;
		case com.rs.mobile.wportal.R.id.rela_002:
			Bundle bundle1 = new Bundle();
			bundle1.putString("floor_num", "2");
			PageUtil.jumpTo(DpHomePageActivity.this, DpFloorDetailActivity.class, bundle1);
			break;
		default:
			break;
		}

	}

	public void showLocationActivity() {

		Intent i = new Intent(activity, LocationActivity.class);

		i.putExtra("type", C.TYPE_PORTAL);

		Location location = A.getLocation();

		if (location != null) {

			i.putExtra("lon", "" + location.getLongitude());

			i.putExtra("lat", "" + location.getLatitude());

		} else {

			// 기본 위치 설정
			i.putExtra("lon", "" + "113.027417");

			i.putExtra("lat", "" + "28.184747");

		}

		activity.startActivityForResult(i, 1000);

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == Activity.RESULT_OK) {
			if (arg0 == 1000) {
				C.DIV_CODE = arg2.getStringExtra("divCode");

				C.DIV_NAME = arg2.getStringExtra("divName");
				tv_divname.setText(C.DIV_NAME);
				getMainData();
			} else if (arg0 == 2000) { // captureActivity

				CaptureUtil.handleResultScaning(DpHomePageActivity.this, arg2.getStringExtra("result"), "DP");

			}
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		// TODO Auto-generated method stub
		getMainData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		// TODO Auto-generated method stub

	}
}
