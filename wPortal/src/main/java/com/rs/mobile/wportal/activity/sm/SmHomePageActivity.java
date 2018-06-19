
package com.rs.mobile.wportal.activity.sm;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.A;
import com.rs.mobile.wportal.CaptureUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.Activity_Customer_Service_Main;
import com.rs.mobile.wportal.activity.dp.DpAddressActivity;
import com.rs.mobile.wportal.adapter.sm.SmViewPagerAdapter;
import com.rs.mobile.wportal.view.CustomViewPager;
import com.rs.mobile.wportal.view.MenuButtonView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class SmHomePageActivity extends BaseActivity {

	private LinearLayout btnSearch;

	private PullToRefreshScrollView scrollView;

	private CustomViewPager viewPager;

	private RadioGroup radioGroup;

	private MenuButtonView menu01, menu02, menu03, menu04, menu05, menu06, menu07, menu08;

	private RelativeLayout eventBtn001, eventBtn002, eventBtn003;

	private WImageView eventIcon001, eventIcon002, eventIcon003;

	private TextView eventTitle001, eventTitle002, eventTitle003;

	private TextView eventContent001, eventContent002, eventContent003;

	private WImageView eventSimpleDraweeView001, eventSimpleDraweeView002, eventSimpleDraweeView003;

	private HorizontalScrollView horizontalScrollView002;

	private WImageView newArrivalProduct;

	private LinearLayout gridView;

	private String rmb;

	private TextView new_text;

	private String ad_id_002;

	private String ad_id_001;

	private String ad_id_003;

	private String ad_id_004;

	private LinearLayout ll_div;

	private TextView tv_divname;

	private Activity activity = SmHomePageActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try {

			setContentView(R.layout.activity_sm_homepage);
			initview();
			initdata();

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initview() {

		try {
			tv_divname = (TextView) findViewById(R.id.tv_divname);
			tv_divname.setText(C.DIV_NAME);
			ll_div = (LinearLayout) findViewById(R.id.ll_div);
			ll_div.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showLocationActivity();
				}
			});
			rmb = getResources().getString(R.string.rmb);

			btnSearch = (LinearLayout) findViewById(R.id.btn_serch);
			btnSearch.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					PageUtil.jumpTo(SmHomePageActivity.this, SmSeachActivity.class);
				}
			});

			scrollView = (PullToRefreshScrollView) findViewById(R.id.scroll_view);
			scrollView.setMode(Mode.PULL_FROM_START);
			scrollView.setOnRefreshListener(new OnRefreshListener2() {

				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub

					gridView.removeAllViews();

					initdata();

				}

				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub

					// initdata();

				}
			});

			viewPager = (CustomViewPager) findViewById(R.id.view_pager);
			viewPager.addOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {

					// TODO Auto-generated method stub
					radioGroup.check(radioGroup.getChildAt(arg0).getId());

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

			radioGroup = (RadioGroup) findViewById(R.id.radio_group);

			menu01 = (MenuButtonView) findViewById(R.id.sm_menu_01);
			menu01.setIcon(this.getResources().getDrawable(R.drawable.icon_myorder));
			menu01.setText(R.string.sm_my_order);
			menu01.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);
			menu01.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						if (UiUtil.checkLogin(SmHomePageActivity.this) == true) {

							UiUtil.startActivity(SmHomePageActivity.this,
									new Intent(SmHomePageActivity.this, MyOrderActivity.class), UiUtil.TYPE_BUTTON,
									menu01);

						}

					} catch (Exception e) {

						L.e(e);

					}
				}
			});
			menu02 = (MenuButtonView) findViewById(R.id.sm_menu_02);
			menu02.setIcon(this.getResources().getDrawable(R.drawable.icon_address));
			menu02.setText(R.string.address_rs_oper);
			menu02.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);
			menu02.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

						
						/*UtilClear.CheckLogin(SmHomePageActivity.this, new CheckListener(){

							@Override
							public void onDoNext() {
								UiUtil.startActivity(SmHomePageActivity.this,
										new Intent(SmHomePageActivity.this, PointsActivity.class), UiUtil.TYPE_BUTTON,
										menu02);
							}
							
						});*/
					try {
						if (UiUtil.checkLogin(SmHomePageActivity.this)) {
							/*UiUtil.startActivity(SmHomePageActivity.this,
									new Intent(SmHomePageActivity.this, SmCollectionActivity.class), UiUtil.TYPE_BUTTON,
									menu04);*/
							PageUtil.jumpTo(SmHomePageActivity.this, DpAddressActivity.class);
						}

					} catch (Exception e) {

						L.e(e);

					}
				}
			});
			menu03 = (MenuButtonView) findViewById(R.id.sm_menu_03);
			menu03.setIcon(this.getResources().getDrawable(R.drawable.icon_wallet));
			menu03.setText(R.string.icon_06);
			menu03.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);
			menu03.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						// t(getResources().getString(R.string.msg_ready_to_service));

						/*if (UiUtil.checkLogin(SmHomePageActivity.this)) {
							UiUtil.startActivity(SmHomePageActivity.this,
									new Intent(SmHomePageActivity.this, SmCouponActivity.class), UiUtil.TYPE_BUTTON,
									menu03);
						}*/
						UtilClear.IntentToLongLiao(SmHomePageActivity.this,
								"cn.rsapp.im.ui.mywallet.MyWalletActivity", S.getShare(getApplicationContext(),C.KEY_REQUEST_MEMBER_ID,""));

					} catch (Exception e) {

						L.e(e);

					}
				}
			});
			menu04 = (MenuButtonView) findViewById(R.id.sm_menu_04);
			//menu04.setIcon(this.getResources().getDrawable(R.drawable.iicon_onlinecustomerservice));
			menu04.setIcon(this.getResources().getDrawable(R.drawable.icon_rs_service));
			menu04.setText(R.string.sm_custom_service);
			menu04.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);
			menu04.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						UiUtil.startActivityForResult(SmHomePageActivity.this, 2000,
								new Intent(SmHomePageActivity.this, Activity_Customer_Service_Main.class), UiUtil.TYPE_BUTTON, menu04);
					} catch (Exception e) {

						L.e(e);

					}

				}
			});
			menu05 = (MenuButtonView) findViewById(R.id.sm_menu_05);
			menu05.setIcon(this.getResources().getDrawable(R.drawable.icon_wallet));
			menu05.setText(R.string.icon_05);
			menu05.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);
			menu05.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					/*UtilClear.IntentToLongLiao(SmHomePageActivity.this,
							"cn.ycapp.im.ui.mywallet.MyWalletActivity");*/
				}
			});
			menu06 = (MenuButtonView) findViewById(R.id.sm_menu_06);
			menu06.setIcon(this.getResources().getDrawable(R.drawable.icon_scanning));
			menu06.setText(R.string.sm_qr);
			menu06.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);
			menu06.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						UiUtil.startActivityForResult(SmHomePageActivity.this, 2000,
								new Intent(SmHomePageActivity.this, CaptureActivity.class), UiUtil.TYPE_BUTTON, menu06);

					} catch (Exception e) {

						L.e(e);

					}
				}
			});
			menu07 = (MenuButtonView) findViewById(R.id.sm_menu_07);
			menu07.setIcon(this.getResources().getDrawable(R.drawable.iicon_onlinecustomerservice));
			menu07.setText(R.string.sm_custom_service);
			menu07.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);
			menu07.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {
						Bundle bundle = new Bundle();
						bundle.putString(C.KEY_INTENT_URL, "http://ssadmin.dxbhtm.com:8090/20_CM/cmList.aspx");
						PageUtil.jumpTo(activity, WebActivity.class, bundle);
					} catch (Exception e) {
						L.e(e);
					}
				}
			});
			menu08 = (MenuButtonView) findViewById(R.id.sm_menu_08);
			menu08.setIcon(this.getResources().getDrawable(R.drawable.icon_address));
			menu08.setText(R.string.sm_address);
			menu08.setIconParam(get_windows_height(activity) / 12, get_windows_height(activity) / 12);
			menu08.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {
						if (UiUtil.checkLogin(SmHomePageActivity.this) == true) {
							UiUtil.startActivity(SmHomePageActivity.this,
									new Intent(SmHomePageActivity.this, SmAddressActivity.class), UiUtil.TYPE_BUTTON,
									menu08);

						}

					} catch (Exception e) {

						L.e(e);

					}
				}
			});

			eventBtn001 = (RelativeLayout) findViewById(R.id.event_btn_001);
			eventBtn001.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_AD_ID, ad_id_001);
					PageUtil.jumpTo(SmHomePageActivity.this, SmBestFreshActivity.class, bundle);
				}
			});

			eventBtn002 = (RelativeLayout) findViewById(R.id.event_btn_002);
			eventBtn002.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_AD_ID, ad_id_002);
					PageUtil.jumpTo(SmHomePageActivity.this, SmHotFreshActivity.class, bundle);
				}
			});

			eventBtn003 = (RelativeLayout) findViewById(R.id.event_btn_003);
			eventBtn003.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_AD_ID, ad_id_003);
					PageUtil.jumpTo(SmHomePageActivity.this, SmTodayFreshActivity.class, bundle);
				}
			});

			eventIcon001 = (WImageView) findViewById(R.id.event_icon_001);
			eventIcon002 = (WImageView) findViewById(R.id.event_icon_002);
			eventIcon003 = (WImageView) findViewById(R.id.event_icon_003);

			eventTitle001 = (TextView) findViewById(R.id.event_title_001);
			eventTitle002 = (TextView) findViewById(R.id.event_title_002);
			eventTitle003 = (TextView) findViewById(R.id.event_title_003);

			eventContent001 = (TextView) findViewById(R.id.event_content_001);
			eventContent002 = (TextView) findViewById(R.id.event_content_002);
			eventContent003 = (TextView) findViewById(R.id.event_content_003);

			eventSimpleDraweeView001 = (WImageView) findViewById(R.id.event_simple_draweeView_001);

			eventSimpleDraweeView002 = (WImageView) findViewById(R.id.event_simple_draweeView_002);

			eventSimpleDraweeView003 = (WImageView) findViewById(R.id.event_simple_draweeView_003);

			RelativeLayout.LayoutParams parms = (android.widget.RelativeLayout.LayoutParams) eventSimpleDraweeView001
					.getLayoutParams();
			parms.width = (int) (getwindowswidth() / 3);
			eventSimpleDraweeView001.setLayoutParams(parms);
			parms = (android.widget.RelativeLayout.LayoutParams) eventSimpleDraweeView002.getLayoutParams();
			parms.width = getwindowswidth() / 5;
			eventSimpleDraweeView002.setLayoutParams(parms);
			parms = (android.widget.RelativeLayout.LayoutParams) eventSimpleDraweeView003.getLayoutParams();
			parms.width = getwindowswidth() / 5;
			eventSimpleDraweeView003.setLayoutParams(parms);
			parms = (android.widget.RelativeLayout.LayoutParams) eventIcon001.getLayoutParams();
			parms.width = getwindowswidth() / 17;
			eventIcon001.setLayoutParams(parms);
			parms = (android.widget.RelativeLayout.LayoutParams) eventIcon002.getLayoutParams();
			parms.width = getwindowswidth() / 17;
			eventIcon002.setLayoutParams(parms);
			parms = (android.widget.RelativeLayout.LayoutParams) eventIcon003.getLayoutParams();
			parms.width = getwindowswidth() / 17;
			eventIcon003.setLayoutParams(parms);

			new_text = (TextView) findViewById(R.id.new_text);
			new_text.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_AD_ID, ad_id_004);
					PageUtil.jumpTo(SmHomePageActivity.this, NewFreshActivity.class, bundle);
				}
			});

			newArrivalProduct = (WImageView) findViewById(R.id.new_rrival_product_simple_draweeView);
			newArrivalProduct.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_AD_ID, ad_id_004);
					PageUtil.jumpTo(SmHomePageActivity.this, NewFreshActivity.class, bundle);
				}
			});

			horizontalScrollView002 = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view_002);

			horizontalScrollView002.setOnTouchListener(new View.OnTouchListener() {

				int lastY = 0;

				@Override
				public boolean onTouch(View v, MotionEvent event) {

					scrollView.requestDisallowInterceptTouchEvent(true);

					int action = event.getActionMasked();
					switch (action) {
					case MotionEvent.ACTION_DOWN:
						lastY = (int) event.getY();
						break;
					case MotionEvent.ACTION_CANCEL:
						scrollView.requestDisallowInterceptTouchEvent(false);
						break;
					case MotionEvent.ACTION_UP:
						scrollView.requestDisallowInterceptTouchEvent(false);
						break;
					case MotionEvent.ACTION_MOVE:

						if (Math.abs(lastY - event.getY()) < StringUtil.dip2px(getApplicationContext(), 100)) {

							scrollView.requestDisallowInterceptTouchEvent(true);

						}

						break;
					}
					return false;
				}
			});

			gridView = (LinearLayout) findViewById(R.id.grid_view);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initdata() {

		try {

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("div_code", C.DIV_CODE);
			OkHttpHelper okHttpHelper = new OkHttpHelper(SmHomePageActivity.this);
			okHttpHelper.addSMPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

					// TODO Auto-generated method stub
					L.d("no network");

					scrollView.onRefreshComplete();

				}

				@Override
				public void onBizSuccess(String responseDescription, final JSONObject jsonData, String all_data) {
					// TODO Auto-generated method stub

					try {

						radioGroup.removeAllViews();
						gridView.removeAllViews();
						final JSONObject data = jsonData.getJSONObject(C.KEY_JSON_DATA);
						SmViewPagerAdapter adapter = new SmViewPagerAdapter(SmHomePageActivity.this, data);
						viewPager.setAdapter(adapter);
						int movieCount = adapter.getCount();

						if (movieCount > 0) {
							Resources res = getResources();

							for (int i = 0; i < movieCount; i++) {

								RadioButton btn = new RadioButton(SmHomePageActivity.this);
								btn.setPadding(5, 5, 5, 5);
								btn.setButtonDrawable(res.getDrawable(R.drawable.bg_viewpage_yuan));
								btn.setGravity(Gravity.CENTER);

								radioGroup.addView(btn);

							}
							radioGroup.setVisibility(View.GONE);
							radioGroup.check(radioGroup.getChildAt(0).getId());
						} else {

						}

						JSONArray jsonArray = data.getJSONArray(C.KEY_JSON_EVENTAD);

						JSONObject jsonObject3 = new JSONObject(jsonArray.get(0).toString());

						JSONObject jsonObject4 = new JSONObject(jsonArray.get(1).toString());

						ad_id_002 = jsonObject4.get(C.KEY_JSON_FM_AD_ID).toString();

						JSONObject jsonObject5 = new JSONObject(jsonArray.get(2).toString());

						ad_id_001 = jsonObject3.get(C.KEY_JSON_FM_AD_ID).toString();

						ad_id_003 = jsonObject5.get(C.KEY_JSON_FM_AD_ID).toString();

						eventTitle001.setText(jsonObject3.getString(C.KEY_JSON_AD_TITLE));
						eventTitle002.setText(jsonObject4.getString(C.KEY_JSON_AD_TITLE));
						eventTitle003.setText(jsonObject5.getString(C.KEY_JSON_AD_TITLE));
						eventContent001.setText(jsonObject3.getString(C.KEY_JSON_SUB_TITLE));
						eventContent002.setText(jsonObject4.getString(C.KEY_JSON_SUB_TITLE));
						eventContent003.setText(jsonObject5.getString(C.KEY_JSON_SUB_TITLE));
						ImageUtil.drawImageView1(SmHomePageActivity.this, eventSimpleDraweeView001, jsonObject3,
								C.KEY_JSON_ADIMAGE, C.KEY_JSON_VERSION);
						ImageUtil.drawImageView1(SmHomePageActivity.this, eventIcon001, jsonObject3, C.KEY_JSON_ICON,
								C.KEY_JSON_VERSION);
						ImageUtil.drawImageView1(SmHomePageActivity.this, eventSimpleDraweeView002, jsonObject4,
								C.KEY_JSON_ADIMAGE, C.KEY_JSON_VERSION);
						ImageUtil.drawImageView1(SmHomePageActivity.this, eventIcon002, jsonObject4, C.KEY_JSON_ICON,
								C.KEY_JSON_VERSION);
						ImageUtil.drawImageView1(SmHomePageActivity.this, eventSimpleDraweeView003, jsonObject5,
								C.KEY_JSON_ADIMAGE, C.KEY_JSON_VERSION);
						ImageUtil.drawImageView1(SmHomePageActivity.this, eventIcon003, jsonObject5, C.KEY_JSON_ICON,
								C.KEY_JSON_VERSION);

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								try {

									int viewWidth = (int) (getwindowswidth() / 3);

									JSONObject jsonObjectNewAD = data.getJSONObject(C.KEY_JSON_NEWAD);
									ad_id_004 = jsonObjectNewAD.getString(C.KEY_JSON_FM_AD_ID);
									JSONArray fArr = jsonObjectNewAD.getJSONArray(C.KEY_JSON_LIST);

									ImageUtil.drawImageView1(SmHomePageActivity.this, newArrivalProduct,
											jsonObjectNewAD, C.KEY_JSON_ADIMAGE, C.KEY_JSON_VERSION);

									final int fCount = fArr.length();

									LinearLayout fLayout = (LinearLayout) findViewById(R.id.sm_hzlistview_002_layout);

									fLayout.removeAllViews();

									for (int i = 0; i < fCount; i++) {

										final JSONObject j = new JSONObject(fArr.get(i).toString());

										fLayout.addView(getListItem(j, viewWidth, viewWidth));

									}

									horizontalScrollView002.invalidate();

								} catch (Exception e) {

									L.e(e);

								}
							}
						});

						//쇼핑몰 홈 상품 리스트
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								try {

									JSONArray gridArr = data.getJSONObject(C.KEY_JSON_LIKEAD)
											.getJSONArray(C.KEY_JSON_LIST);

									int count = Math.round(gridArr.length() / 2);

									for (int i = 0; i < count; i++) {

										View gridArea = LayoutInflater.from(SmHomePageActivity.this)
												.inflate(R.layout.list_item_grid_area, null);

										//상품이 10개면 이후부터 OTC 이미지를 넣는다
										if(i == 10)
										{
											ImageView img_otc_title = (ImageView) gridArea.findViewById(R.id.img_otc_title);
											img_otc_title.setVisibility(View.VISIBLE);
										}

										LinearLayout leftItem = (LinearLayout) gridArea.findViewById(R.id.left_item);
										LinearLayout rightItem = (LinearLayout) gridArea.findViewById(R.id.right_item);

										int index = i * 2;

										if (index < count * 2) {
											leftItem.addView(getListItem(gridArr.getJSONObject(index),
													(int) (getwindowswidth() / 2.2), (int) (getwindowswidth() / 2.2)));

										}

										if (index + 1 < count * 2) {
											rightItem.addView(getListItem(gridArr.getJSONObject(index + 1),
													(int) (getwindowswidth() / 2.2), (int) (getwindowswidth() / 2.2)));

										}

										gridView.addView(gridArea);

									}

									gridView.invalidate();

								} catch (Exception e) {

									L.e(e);

								}

							}
						});

					} catch (Exception e) {
						// TODO: handle exception
						L.e(e);
					}

					scrollView.onRefreshComplete();

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

					scrollView.onRefreshComplete();

				}
			}, Constant.SM_BASE_URL + Constant.GET_MAINQUERY_URL, params);

		} catch (Exception e) {

			L.e(e);

			scrollView.onRefreshComplete();

		}

	}

	public int getwindowswidth() {

		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 窗口的宽度
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	public int getwindowsheight() {

		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 窗口的宽度
		int screenheight = dm.heightPixels;
		return screenheight;
	}

	public SMListItemView getListItem(final JSONObject josnObject, int viewWidth, int viewHeight) {

		SMListItemView v = new SMListItemView(SmHomePageActivity.this);

		try {

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			v.setLayoutParams(params);

			v.setGravity(Gravity.CENTER);

			params = new LinearLayout.LayoutParams(viewWidth, viewWidth);

			v.getSm_hzlistview_item_img().setLayoutParams(params);

			ImageUtil.drawImageView1(SmHomePageActivity.this, v.getSm_hzlistview_item_img(), josnObject,
					C.KEY_JSON_IMAGE_URL002, C.KEY_JSON_VERSION);

			v.setName(josnObject.get(C.KEY_JSON_ITEM_NAME).toString());

			v.setPrice(rmb + josnObject.get(C.KEY_JSON_ITEM_PRICE).toString() + "/"
					+ josnObject.get("stock_unit").toString());

			final String item_code = josnObject.get(C.KEY_JSON_FM_ITEM_CODE).toString();

			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);
					bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
					PageUtil.jumpTo(SmHomePageActivity.this, SmGoodsDetailActivity.class, bundle);
				}
			});

		} catch (Exception e) {

			L.e(e);

		}

		return v;

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
			i.putExtra("lon", "" + "122.1712303162");

			i.putExtra("lat", "" + "37.4192539597");

		}

		activity.startActivityForResult(i, 1000);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == 2000) { // captureActivity

				CaptureUtil.handleResultScaning(SmHomePageActivity.this, data.getStringExtra("result"), "");

			} else if (requestCode == 1000) {
				C.DIV_CODE = data.getStringExtra("divCode");

				C.DIV_NAME = data.getStringExtra("divName");
				tv_divname.setText(C.DIV_NAME);
				initdata();
			}
		}
	}

}
