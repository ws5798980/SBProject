package com.rs.mobile.wportal.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView.PullScroolListener;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.common.activity.LocationActivity;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.Util;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.UtilCheckLogin.CheckListener;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.A;
import com.rs.mobile.wportal.CheckUpdate;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.Activity_Customer_Service_Main;
import com.rs.mobile.wportal.activity.Activity_rs_Agency_Create;
import com.rs.mobile.wportal.activity.MainActivity;
import com.rs.mobile.wportal.activity.dp.DpAddressActivity;
import com.rs.mobile.wportal.activity.market.fragment.MarketMainFragmentActivity;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;
import com.rs.mobile.wportal.activity.sm.SmMainActivity;
import com.rs.mobile.wportal.adapter.JSONArrayListAdapter;
import com.rs.mobile.wportal.adapter.ViewPagerAdapter;
import com.rs.mobile.wportal.adapter.ViewPagerAdapter.ItemClickListener;
import com.rs.mobile.wportal.persnal.PersnalCenterActivity;
import com.rs.mobile.wportal.view.MenuButtonView;
import com.rs.mobile.wportal.view.NewsView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class MainFragment extends Fragment {

	private Context context;

	private MainActivity activity;

	private View rootView;

	private ImageView branchBtn;

	private TextView branchTextView;

	private LinearLayout persnalBtn, qrBtn;

	private ImageView persnalImageView, qrImageView;

	private ViewPager viewPager;

	private ViewPagerAdapter viewPagerAdapter;

	private LinearLayout menuArea, menu_area2;

	private MenuButtonView menu01, menu02, menu03, menu04, menu05, menu06,
			menu07, menu08, menu_sw_01, menu_sw_02, menu_sw_03, menu_sw_04,
			menu_sw_05, menu_sw_06, menu_sw_07, menu_sw_08;

	private LinearLayout newsArea;

	private PullToRefreshScrollView scrollView;

	// private LinearLayout branchLayout, branchArea;

	private ImageView branchArrowImageView;

	private ListView branchListView;

	private JSONArrayListAdapter branchListAdapter;

	private String newsCurrentPage = "1";

	private String newsNextPage = "1";

	private int currentBranchPosition = 0;

	private LayoutInflater inflator;

	private RelativeLayout introduceView;

	private View introduceMenuView;

	private View introduceBannerView;

	private int topY = 0;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		rootView = inflater.inflate(R.layout.activity_main, container, false);

		context = container.getContext();

		activity = (MainActivity) context;

		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		try {

			if (Util.checkNetwork(context) == true) {

				inflator = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				// branchArea = (LinearLayout)
				// rootView.findViewById(R.id.branch_list_area);
				//
				// branchLayout = (LinearLayout)
				// rootView.findViewById(R.id.branch_list_layout);
				// branchLayout.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				//
				// try {
				//
				// // showBranchList();
				//
				// } catch (Exception e) {
				//
				// L.e(e);
				//
				// }
				//
				// }
				// });

				branchArrowImageView = (ImageView) rootView
						.findViewById(R.id.branch_arrow_image_view);

				// branchListView = (ListView)
				// rootView.findViewById(R.id.branch_list_view);

				branchBtn = (ImageView) rootView.findViewById(R.id.branch_btn);
				branchBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							showLocationActivity();

						} catch (Exception e) {

							L.e(e);

						}

					}
				});

				branchTextView = (TextView) rootView
						.findViewById(R.id.branch_text_view);
				branchTextView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							showLocationActivity();

						} catch (Exception e) {

							L.e(e);

						}

					}
				});

				persnalImageView = (ImageView) rootView
						.findViewById(R.id.persnal_img_view);
				qrImageView = (ImageView) rootView
						.findViewById(R.id.qr_img_view);

				persnalBtn = (LinearLayout) rootView
						.findViewById(R.id.persnal_btn);
				persnalBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// if(S.getShare(context, C.KEY_JSON_TOKEN,
						// "").equals("")){
						// startActivity(new
						// Intent(context,LoginActivity.class));
						// }else{
						// UiUtil.startActivity(context, new Intent(
						// context, PersnalCenterActivity.class),
						// UiUtil.TYPE_BUTTON, persnalImageView);
						// }

						UtilClear.CheckLogin(context, new CheckListener() {

							@Override
							public void onDoNext() {
								UiUtil.startActivity(context, new Intent(
										context, PersnalCenterActivity.class),
										UiUtil.TYPE_BUTTON, persnalImageView);
							}
						});

					}
				});

				qrBtn = (LinearLayout) rootView.findViewById(R.id.qr_btn);
				qrBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						UiUtil.startActivityForResult(activity, 2000,
								new Intent(activity, CaptureActivity.class),
								UiUtil.TYPE_BUTTON, qrImageView);

					}
				});

				viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);

				menuArea = (LinearLayout) rootView.findViewById(R.id.menu_area);
				menu_area2 = (LinearLayout) rootView
						.findViewById(R.id.menu_area2);

				menu_sw_01 = (MenuButtonView) rootView
						.findViewById(R.id.menu_sw_01);
				menu01 = (MenuButtonView) rootView.findViewById(R.id.menu_01);
				menu01.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_home));
				menu_sw_01.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_home));
				menu01.setText(R.string.icon_01);
				menu_sw_01.setText(R.string.icon_01);

				menu_sw_01.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							/*Intent i = new Intent(context, DpMainActivity.class);

							UiUtil.startActivity(context, i,
									UiUtil.TYPE_BUTTON, menu_sw_01);*/
							/*final Activity context,final String title ,final String content ,final String url
    		,final String imgUrl,int id_below,final String type, final String itemCode*/
							try {
								Bundle bundle = new Bundle();
								bundle.putString(C.KEY_INTENT_URL, "http://www.renshengyaoye.com:8993/Mobile/index.aspx");
								PageUtil.jumpTo(activity, WebActivity.class, bundle);
							} catch (Exception e) {
								L.e(e);
							}

						} catch (Exception e) {

							L.e(e);

						}
					}
				});
				menu01.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							/*Intent i = new Intent(context, DpMainActivity.class);

							UiUtil.startActivity(context, i,
									UiUtil.TYPE_BUTTON, menu01);*/
							try {
								Bundle bundle = new Bundle();
								bundle.putString(C.KEY_INTENT_URL, "http://www.renshengyaoye.com:8993/Mobile/index.aspx");
								PageUtil.jumpTo(activity, WebActivity.class, bundle);
							} catch (Exception e) {
								L.e(e);
							}

						} catch (Exception e) {

							L.e(e);

						}
					}
				});

				//셔롱에서는 레스토랑이였음
				menu_sw_02 = (MenuButtonView) rootView
						.findViewById(R.id.menu_sw_02);
				menu02 = (MenuButtonView) rootView.findViewById(R.id.menu_02);
				menu_sw_02.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_shoppingmall));
				menu02.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_shoppingmall));
				menu02.setText(R.string.icon_02);
				menu_sw_02.setText(R.string.icon_02);
				menu_sw_02.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							// Intent i = new Intent(context,
							// TempActivity.class);
							//
							// i.putExtra("code", 1);
							//
							// UiUtil.startActivity(context, i,
							// UiUtil.TYPE_BUTTON, menu02);

							HashMap<String, String> extra = new HashMap<String, String>();

							/*UiUtil.startActivity(context, RtMainActivity.class,
									extra, UiUtil.TYPE_BUTTON, menu_sw_02);*/
							UiUtil.startActivity(context, SmMainActivity.class,
									extra, UiUtil.TYPE_BUTTON, menu_sw_02);

						} catch (Exception e) {

							L.e(e);

						}
					}
				});
				menu02.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							// Intent i = new Intent(context,
							// TempActivity.class);
							//
							// i.putExtra("code", 1);
							//
							// UiUtil.startActivity(context, i,
							// UiUtil.TYPE_BUTTON, menu02);

							HashMap<String, String> extra = new HashMap<String, String>();

							/*UiUtil.startActivity(context, RtMainActivity.class,
									extra, UiUtil.TYPE_BUTTON, menu02);*/
							UiUtil.startActivity(context, SmMainActivity.class,
									extra, UiUtil.TYPE_BUTTON, menu_sw_02);

						} catch (Exception e) {

							L.e(e);

						}
					}
				});

				//셔롱에서는 외식
				menu_sw_03 = (MenuButtonView) rootView
						.findViewById(R.id.menu_sw_03);
				menu03 = (MenuButtonView) rootView.findViewById(R.id.menu_03);
				/*menu03.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_community));*/
				menu03.setIcon(getResources().getDrawable(
						R.drawable.icon_myorder));

				menu03.setText(R.string.order_rs_oper);
				/*menu_sw_03.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_community));*/
				menu_sw_03.setIcon(getResources().getDrawable(
						R.drawable.icon_myorder));
				menu_sw_03.setText(R.string.order_rs_oper);
				menu_sw_03.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							/*HashMap<String, String> extra = new HashMap<String, String>();

							UiUtil.startActivity(context, SmMainActivity.class,
									extra, UiUtil.TYPE_BUTTON, menu_sw_03);*/
							UiUtil.startActivity(MainFragment.this.context, new Intent(MainFragment.this.context, MyOrderActivity.class),
									UiUtil.TYPE_BUTTON, menu_sw_03);

						} catch (Exception e) {

							L.e(e);

						}
					}
				});

				menu03.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							/*HashMap<String, String> extra = new HashMap<String, String>();

							UiUtil.startActivity(context, SmMainActivity.class,
									extra, UiUtil.TYPE_BUTTON, menu03);*/
							UiUtil.startActivity(MainFragment.this.context, new Intent(MainFragment.this.context, MyOrderActivity.class),
									UiUtil.TYPE_BUTTON, menu_sw_03);

						} catch (Exception e) {

							L.e(e);

						}
					}
				});

				//셔롱에서는 호텔
				menu_sw_04 = (MenuButtonView) rootView
						.findViewById(R.id.menu_sw_04);
				menu04 = (MenuButtonView) rootView.findViewById(R.id.menu_04);
				menu04.setIcon(getResources()
						.getDrawable(R.drawable.icon_rs_service));
				menu04.setText(R.string.icon_04);
				menu_sw_04.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_service));
				menu_sw_04.setText(R.string.icon_04);
				menu_sw_04.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
					// TODO Auto-generated method stub
						try {

							Intent i = new Intent(context, Activity_Customer_Service_Main.class);

							//i.putExtra("code", 7);

							UiUtil.startActivity(context, i,
									UiUtil.TYPE_BUTTON, menu_sw_04);

						} catch (Exception e) {

							L.e(e);

						}

						// try {
						//
						// Intent i = new Intent(context, HtMainActivity.class);
						//
						// UiUtil.startActivity(context, i, UiUtil.TYPE_BUTTON,
						// menu04);
						//
						// } catch (Exception e) {
						//
						// L.e(e);
						//
						// }
					}
				});

				menu04.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							Intent i = new Intent(context, Activity_Customer_Service_Main.class);

							//i.putExtra("code", 7);

							UiUtil.startActivity(context, i,
									UiUtil.TYPE_BUTTON, menu_sw_04);

						} catch (Exception e) {

							L.e(e);

						}

						// try {
						//
						// Intent i = new Intent(context, HtMainActivity.class);
						//
						// UiUtil.startActivity(context, i, UiUtil.TYPE_BUTTON,
						// menu04);
						//
						// } catch (Exception e) {
						//
						// L.e(e);
						//
						// }
					}
				});

				//셔롱에서는 지갑
				menu_sw_05 = (MenuButtonView) rootView
						.findViewById(R.id.menu_sw_05);
				menu05 = (MenuButtonView) rootView.findViewById(R.id.menu_05);
				menu05.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_message));
				menu05.setText(R.string.icon_05);
				menu_sw_05.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_message));
				menu_sw_05.setText(R.string.icon_05);
				menu_sw_05.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String log_phoneNum = S.getShare(context, C.KEY_REQUEST_MEMBER_ID,"");
						UtilClear.IntentToLongLiao(context,
								"cn.rsapp.im.ui.activity.MainActivity",S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));
					}
				});
				menu05.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						String log_phoneNum = S.getShare(context, C.KEY_REQUEST_MEMBER_ID,"");
						UtilClear.IntentToLongLiao(context,
								"cn.rsapp.im.ui.activity.MainActivity", S.getShare(context, C.KEY_REQUEST_MEMBER_ID,""));
					}
				});

				//셔롱에서 오프라인몰
				menu_sw_06 = (MenuButtonView) rootView
						.findViewById(R.id.menu_sw_06);
				menu06 = (MenuButtonView) rootView.findViewById(R.id.menu_06);
				menu06.setIcon(getResources().getDrawable(
						R.drawable.icon_wallet));
				menu06.setText(R.string.icon_09);

				menu_sw_06.setIcon(getResources().getDrawable(
						R.drawable.icon_wallet));
				menu_sw_06.setText(R.string.icon_09);
				menu_sw_06.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
							UtilClear.IntentToLongLiao(context,
									"cn.rsapp.im.ui.mywallet.MyWalletActivity",S.get(context, C.KEY_REQUEST_MEMBER_ID,""));
						//	offLineHand();
					}
				});
				menu06.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						UtilClear.IntentToLongLiao(context,
								"cn.rsapp.im.ui.mywallet.MyWalletActivity",S.get(context, C.KEY_REQUEST_MEMBER_ID,""));
							//offLineHand();
					}
				});

				//셔롱에서 메신저
				menu_sw_07 = (MenuButtonView) rootView
						.findViewById(R.id.menu_sw_07);
				menu07 = (MenuButtonView) rootView.findViewById(R.id.menu_07);
				menu07.setIcon(getResources().getDrawable(
						R.drawable.icon_address));
				menu07.setIcon(getResources().getDrawable(
						R.drawable.icon_address
				));
				menu07.setText(R.string.address_rs_oper);
				menu_sw_07.setIcon(getResources().getDrawable(
						R.drawable.icon_address));
				menu07.setIcon(getResources().getDrawable(
						R.drawable.icon_address
				));
				menu_sw_07.setText(R.string.address_rs_oper);
				menu_sw_07.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						/*UtilClear.IntentToLongLiao(context,
								"cn.ycapp.im.ui.activity.MainActivity");*/
						PageUtil.jumpTo(MainFragment.this.context, DpAddressActivity.class);

					}
				});

				menu07.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						/*UtilClear.IntentToLongLiao(context,
								"cn.ycapp.im.ui.activity.MainActivity");*/
						PageUtil.jumpTo(MainFragment.this.context, DpAddressActivity.class);
					}
				});

				//셔롱에서 광리 미디어
				menu_sw_08 = (MenuButtonView) rootView
						.findViewById(R.id.menu_sw_08);
				menu08 = (MenuButtonView) rootView.findViewById(R.id.menu_08);
				menu08.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_agent));
				menu08.setText(R.string.icon_08);
				menu_sw_08.setIcon(getResources().getDrawable(
						R.drawable.icon_rs_agent));
				menu_sw_08.setText(R.string.icon_08);
				menu_sw_08.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							Intent i = new Intent(context, Activity_rs_Agency_Create.class);
							//i.putExtra("code", 7);
							UiUtil.startActivity(context, i,
									UiUtil.TYPE_BUTTON, menu_sw_08);

						} catch (Exception e) {

							L.e(e);

						}

					}
				});
				menu08.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							Intent i = new Intent(context, Activity_rs_Agency_Create.class);
							//i.putExtra("code", 7);
							UiUtil.startActivity(context, i,
									UiUtil.TYPE_BUTTON, menu_sw_08);


						} catch (Exception e) {

							L.e(e);

						}

						// try {
						//
						// HashMap<String, String> extra = new HashMap<String,
						// String>();
						//
						// UiUtil.startActivity(context,
						// MainActivity.class,
						// extra,
						// UiUtil.TYPE_BUTTON, menu08);
						//
						// } catch (Exception e) {
						//
						// L.e(e);
						//
						// }

					}
				});

				newsArea = (LinearLayout) rootView.findViewById(R.id.news_area);

				scrollView = (PullToRefreshScrollView) rootView
						.findViewById(R.id.scroll_view);
				scrollView.setOnRefreshListener(new OnRefreshListener2() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase refreshView) {
						// TODO Auto-generated method stub

						refresh();

					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						// TODO Auto-generated method stub

						if (newsNextPage != null && !newsNextPage.equals("")
								&& !newsNextPage.equals("0")) {

							drawAddNews();

						} else {

							scrollView.onRefreshComplete();

						}

					}
				});

				//branchTextView.setText(C.DIV_NAME);
				branchTextView.setText(R.string.rs_location_wehi);

				introduceView = (RelativeLayout) rootView
						.findViewById(R.id.introduce_view);
				introduceView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						introduceView.setVisibility(View.GONE);

					}
				});

				introduceMenuView = (View) rootView
						.findViewById(R.id.introduce_menu_view);

				introduceBannerView = (View) rootView
						.findViewById(R.id.introduce_banner_view);

				initCatch();
				draw();
				scrollView.setPullScroolListener(new PullScroolListener() {

					@Override
					public void onScrollType(int x, int y) {
						// TODO Auto-generated method stub
						// getViewY();
						if (topY == 0) {
							topY = menuArea.getTop()
									- getResources().getDimensionPixelOffset(
											R.dimen.marginx2);
						}

						if (y >= topY) {
							menu_area2.setVisibility(View.VISIBLE);
						} else {
							menu_area2.setVisibility(View.GONE);
						}
					}
				});
				CheckUpdate.getScanInfo(getActivity());
			}

		} catch (Exception e) {

			L.e(e);

		}

	}
	
	//线下超市操作
	private void offLineHand(){
		UtilClear.CheckLogin(context, new CheckListener() {

			@Override
			public void onDoNext() {
//				S.setShare(context, "tickets", "");
				if(S.getShare(context, "tickets", "").equals("")){
					context.startActivity(new Intent(context,
							MarketMainFragmentActivity.class));
				}else{
					OkHttpHelper okHttpHelper = new OkHttpHelper(context);
					HashMap<String, String> params = new HashMap<>();
					params.put("tickets", S.getShare(context, "tickets", ""));
					okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

						@Override
						public void onNetworkError(Request request, IOException e) {

						}

						@Override
						public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

							try {
								if (data.getInt(C.KEY_JSON_FM_STATUS) > 0) {
									if(data.optString("tickets") != null && !data.optString("tickets").equals(""))
										S.setShare(context, "tickets", data.optString("tickets"));
								}else{
									S.setShare(context, "tickets","");
								}
									context.startActivity(new Intent(context,
											MarketMainFragmentActivity.class));
							} catch (Exception e) {

							}
						}

						@Override
						public void onBizFailure(String responseDescription, JSONObject data, String flag) {

						}
					}, Constant.SM_BASE_URL + Constant.MK_CHECKTICKETS, params);

					
				}
			}
		});
		
		
	}
	
	public void refresh() {
		newsArea.removeAllViews();
		newsArea.invalidate();

		draw();
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

	private void initCatch() {

		String obj = S.get(context, "mainKey", "");
		if (!obj.equals("")) {
			try {
				final JSONObject date = new JSONObject(obj);
				//branchTextView.setText(C.DIV_NAME);
				branchTextView.setText(R.string.rs_location_wehi);
				newsCurrentPage = "1";

				newsNextPage = "1";

				// ((Activity) context).runOnUiThread(new Runnable() {
				//
				// @Override
				// public void run() {
				// // TODO Auto-generated method stub

				// scrollView.onRefreshComplete();

				drawViewPager(date);

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						drawNewsView(true, date);
					}
				}, 300);

				String shareUrl = activity.getIntent().getStringExtra(
						C.KEY_INTENT_URL);

				if (shareUrl != null && !shareUrl.equals("")) {

					activity.startShareActivity(shareUrl);

				}

				// app
				if (S.get(activity, C.KEY_SHARED_INTRODUCE, "0").equals("0")) {

					introduceView.setVisibility(View.VISIBLE);

					int menuHeight = menuArea.getHeight();

					int bannerHeight = viewPager.getHeight();

					introduceBannerView.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							(int) (bannerHeight + activity.getResources()
									.getDimension(R.dimen.marginx2))));

					introduceMenuView.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							(int) (menuHeight + activity.getResources()
									.getDimension(R.dimen.marginx4))));

					S.set(activity, C.KEY_SHARED_INTRODUCE, "1");

				} else {

					introduceView.setVisibility(View.GONE);

				}

				// }}
				// );

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void draw() {

		try {

			//branchTextView.setText(C.DIV_NAME);
			branchTextView.setText(R.string.rs_location_wehi);
			newsCurrentPage = "1";

			newsNextPage = "1";

			OkHttpHelper helper = new OkHttpHelper(context, false);

			HashMap<String, String> params = new HashMap<String, String>();

			// JSONObject j = new
			// JSONObject(A.branchArr.get(currentBranchPosition).toString());

			params.put(C.KEY_JSON_PLACE, C.DIV_CODE);

			params.put(C.KEY_JSON_BANNER_TYPE, "1");

			params.put(C.KEY_JSON_CURRENT_PAGE, newsCurrentPage);

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

					scrollView.onRefreshComplete();

				}

				@Override
				public void onBizSuccess(String responseDescription,
						final JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					S.set(context, "mainKey", data.toString());

					try {

						((Activity) context).runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								try {

									L.d(all_data);

									scrollView.onRefreshComplete();
									drawViewPager(data);

									newsArea.removeAllViews();

									new Handler().postDelayed(new Runnable() {

										@Override
										public void run() {
											drawNewsView(true, data);
										}
									}, 300);

									// drawNewsView(true, data);

									String shareUrl = activity.getIntent()
											.getStringExtra(C.KEY_INTENT_URL);

									if (shareUrl != null
											&& !shareUrl.equals("")) {

										activity.startShareActivity(shareUrl);

									}

									// app 소개
									if (S.get(activity, C.KEY_SHARED_INTRODUCE,
											"0").equals("0")) {

										introduceView
												.setVisibility(View.VISIBLE);

										int menuHeight = menuArea.getHeight();

										int bannerHeight = viewPager
												.getHeight();

										introduceBannerView
												.setLayoutParams(new LayoutParams(
														LayoutParams.MATCH_PARENT,
														(int) (bannerHeight + activity
																.getResources()
																.getDimension(
																		R.dimen.marginx2))));

										introduceMenuView
												.setLayoutParams(new LayoutParams(
														LayoutParams.MATCH_PARENT,
														(int) (menuHeight + activity
																.getResources()
																.getDimension(
																		R.dimen.marginx4))));

										S.set(activity, C.KEY_SHARED_INTRODUCE,
												"1");

									} else {

										introduceView.setVisibility(View.GONE);

									}

								} catch (Exception e) {

									L.e(e);

									D.showDialog(activity, -1,
											getString(R.string.title_error),
											getString(R.string.msg_app_error),
											getString(R.string.finish),
											new OnClickListener() {

												@Override
												public void onClick(View v) {
													// TODO Auto-generated
													// method stub

													activity.finish();

												}
											}, false);

								}

							}
						});

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription,
						JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

					scrollView.onRefreshComplete();

				}
			}, C.BASE_URL + Constant.MAIN_JSON_URL, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void drawAddNews() {

		try {

			OkHttpHelper helper = new OkHttpHelper(context);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_JSON_PLACE, C.DIV_CODE);

			params.put(C.KEY_JSON_BANNER_TYPE, "1");

			params.put(C.KEY_JSON_CURRENT_PAGE, newsNextPage);

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

					scrollView.onRefreshComplete();

				}

				@Override
				public void onBizSuccess(String responseDescription,
						final JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						((Activity) context).runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								try {

									L.d(all_data);

									scrollView.onRefreshComplete();

									drawNewsView(false, data);

								} catch (Exception e) {

									L.e(e);

								}

							}
						});

					} catch (Exception e) {

						L.e(e);

						scrollView.onRefreshComplete();

					}

				}

				@Override
				public void onBizFailure(String responseDescription,
						JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

					scrollView.onRefreshComplete();

				}
			}, C.BASE_URL + Constant.MAIN_JSON_URL, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void drawViewPager(final JSONObject data) {

		try {

			new Handler().post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					try {

						JSONArray arr = new JSONArray(data.get(
								C.KEY_JSON_BANNER).toString());

						if (arr == null || arr.length() == 0) {

							activity.findViewById(R.id.banner_area)
									.setVisibility(View.GONE);

							return;

						} else {

							activity.findViewById(R.id.banner_area)
									.setVisibility(View.VISIBLE);

						}

					} catch (Exception e) {

						L.e(e);

					}

					if (viewPagerAdapter != null) {

						int c = viewPagerAdapter.items.size();

						for (int i = 0; i < c; i++) {

							viewPagerAdapter.destroyItem(viewPager, i, null);

						}

						viewPagerAdapter.items.clear();

						viewPagerAdapter.notifyDataSetChanged();

						viewPager.removeAllViews();

						viewPagerAdapter.views.clear();

						viewPager.setAdapter(null);

						viewPager.invalidate();

					}

					viewPagerAdapter = new ViewPagerAdapter(inflator, data);
					viewPagerAdapter
							.setOnItemClickListener(new ItemClickListener() {

								@Override
								public void onClick(Object obj) {
									// TODO Auto-generated method stub

									try {

										String url = ((JSONObject) obj)
												.getString(C.KEY_JSON_URL);

										if (url == null || url.equals("")) {

											activity.t(activity
													.getString(R.string.ready));

											return;

										}

										Intent i = new Intent(context,
												WebActivity.class);
										i.putExtra(C.KEY_INTENT_URL, url);
										context.startActivity(i);

									} catch (Exception e) {

										L.e(e);

									}

								}
							});
					viewPager.setAdapter(viewPagerAdapter);

				}
			});

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 뉴스 리스트 그리기
	 * 
	 * @param data
	 */
	public void drawNewsView(boolean fromMain, final JSONObject data) {

		try {

			newsCurrentPage = data.getString(C.KEY_JSON_CURRENT_PAGE);

			newsNextPage = data.getString(C.KEY_JSON_NEXT_PAGE);

			JSONArray arr = new JSONArray(data.get(C.KEY_JSON_NEWS).toString());

			if (arr == null || arr.length() == 0) {

				activity.findViewById(R.id.news_layout)
						.setVisibility(View.GONE);

				activity.findViewById(R.id.news_empty_layout).setVisibility(
						View.VISIBLE);

				return;

			} else {

				activity.findViewById(R.id.news_layout).setVisibility(
						View.VISIBLE);

				activity.findViewById(R.id.news_empty_layout).setVisibility(
						View.GONE);

			}
			for (int i = 0; i < arr.length(); i++) {

				final NewsView view = new NewsView(context);

				view.initView(context, inflator);

				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						activity.newsHeight);

				view.setLayoutParams(layoutParams);

				view.invalidate();

				final JSONObject json = new JSONObject(arr.get(i).toString());

				view.setTitle(json.getString(C.KEY_JSON_TITLE));

				view.setContent(json.getString(C.KEY_JSON_CONTENT));

				view.setDate(json.getString(C.KEY_JSON_DATE));

				view.setCount(json.getString(C.KEY_JSON_REPLY_COUNT));

				String imgUrl = json.getString(C.KEY_JSON_IMAGE_URL);

				if (imgUrl == null || imgUrl.equals("")) {

					view.getImageView().setVisibility(View.GONE);

					RelativeLayout contentArea = (RelativeLayout) view
							.findViewById(R.id.content_area);

					contentArea.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));

				} else {

					view.getImageView().setVisibility(View.VISIBLE);

					// ImageUtil.drawImageView(context, view.getImageView(),
					// json, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);

					ImageUtil.drawImageViewBuFullUrl(context,
							view.getImageView(), json, C.KEY_JSON_IMAGE_URL,
							C.KEY_JSON_VERSION);

				}

				// view.getImageView().setRounding(true);

				view.invalidate();

				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						try {

							HashMap<String, String> extra = new HashMap<String, String>();
							extra.put(C.KEY_INTENT_URL,
									json.getString(C.KEY_JSON_URL));
							UiUtil.startActivity(context, WebActivity.class,
									extra, UiUtil.TYPE_LIST, view);

						} catch (Exception e) {

							L.e(e);

						}

					}
				});

				newsArea.addView(view);

				if (fromMain == true && i == 0) {
				} else {

					LinearLayout.LayoutParams p = (android.widget.LinearLayout.LayoutParams) view
							.getLayoutParams();
					p.topMargin = 2;
					view.setLayoutParams(p);

				}

				view.invalidate();
				newsArea.invalidate();

				// if (view.getTitleTextView().getLineCount() < 2) {
				//
				// view.getContentTextView().setMaxLines(2);
				// view.getContentTextView().setLines(2);
				//
				// } else {
				//
				// view.getContentTextView().setMaxLines(1);
				// view.getContentTextView().setLines(1);
				//
				// }

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 
	 */
	// public void showBranchDialog() {
	//
	// try {
	//
	// UiUtil.doButtonClickAnimation(context, branchBtn);
	// UiUtil.doButtonClickAnimation(context, branchTextView);
	//
	// String[] branchList = new String[A.branchArr.length()];
	//
	// for (int i = 0; i < branchList.length; i++) {
	//
	// branchList[i] = new
	// JSONObject(A.branchArr.get(i).toString()).getString(C.KEY_JSON_NAME);
	//
	// }
	//
	// ((BaseActivity)context).showSingleChoiceDialog(-1, -1, -1, R.string.ok,
	// R.string.cancel, new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// // TODO Auto-generated method stub
	//
	// try {
	//
	// dialog.dismiss();
	//
	// JSONObject j = new
	// JSONObject(A.branchArr.get(currentBranchPosition).toString());
	//
	// branchTextView.setText(j.getString(C.KEY_JSON_NAME));
	//
	// newsArea.removeAllViews();
	//
	// newsArea.invalidate();
	//
	// draw();
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// }
	//
	// }
	// }, new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// // TODO Auto-generated method stub
	//
	// dialog.dismiss();
	//
	// }
	// }, branchList, currentBranchPosition, new
	// DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// // TODO Auto-generated method stub
	//
	// try {
	//
	// currentBranchPosition = which;
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// }
	//
	// }
	// }, true);
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// }
	//
	// }

	// public void showBranchList() {
	//
	// try {
	//
	// if (branchLayout.getVisibility() == View.VISIBLE) {
	//
	// Animation branchArrowAnimation = AnimationUtils.loadAnimation(context,
	// R.anim.rotate_out);
	//
	// Animation branchAreaAnimation = AnimationUtils.loadAnimation(context,
	// R.anim.scale_top_out);
	//
	// branchArrowImageView.setRotation(0);
	//
	// branchAreaAnimation.setAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// branchLayout.setVisibility(View.GONE);
	//
	// }
	// });
	//
	// branchArea.setVisibility(View.GONE);
	//
	// branchArea.startAnimation(branchAreaAnimation);
	//
	// branchArrowImageView.startAnimation(branchArrowAnimation);
	//
	// } else {
	//
	// branchLayout.setVisibility(View.VISIBLE);
	//
	// branchArea.setVisibility(View.VISIBLE);
	//
	// final Animation branchLayoutAnimation =
	// AnimationUtils.loadAnimation(context,
	// R.anim.fade_out);
	//
	// Animation branchAreaAnimation = AnimationUtils.loadAnimation(context,
	// R.anim.scale_bottom_in);
	//
	// Animation branchArrowAnimation = AnimationUtils.loadAnimation(context,
	// R.anim.rotate_in);
	// branchArrowAnimation.setAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// branchArrowImageView.setRotation(180);
	//
	// }
	// });
	// branchArea.startAnimation(branchAreaAnimation);
	//
	// branchArrowImageView.startAnimation(branchArrowAnimation);
	//
	// branchArea.setLayoutAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// branchLayout.startAnimation(branchLayoutAnimation);
	//
	// }
	// });
	//
	// branchListAdapter = new
	// JSONArrayListAdapter(JSONArrayListAdapter.TYPE_SINGLE_CHOICE_NO_RADIO_BUTTON,
	// A.branchArr);
	//
	// branchListView.setAdapter(branchListAdapter);
	//
	// branchListView.setOnItemClickListener(new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long
	// arg3) {
	// // TODO Auto-generated method stub
	//
	// try {
	//
	// Animation branchArrowAnimation = AnimationUtils.loadAnimation(context,
	// R.anim.rotate_out);
	//
	// Animation branchAreaAnimation = AnimationUtils.loadAnimation(context,
	// R.anim.scale_top_out);
	//
	// branchArrowImageView.setRotation(0);
	//
	// branchAreaAnimation.setAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// branchLayout.setVisibility(View.GONE);
	//
	// }
	// });
	//
	// branchArea.setVisibility(View.GONE);
	//
	// branchArea.startAnimation(branchAreaAnimation);
	//
	// branchArrowImageView.startAnimation(branchArrowAnimation);
	//
	// currentBranchPosition = arg2;
	//
	// JSONObject j = new JSONObject(A.branchArr.get(arg2).toString());
	//
	// branchTextView.setText(j.getString(C.KEY_JSON_NAME));
	//
	// newsArea.removeAllViews();
	//
	// newsArea.invalidate();
	//
	// draw();
	//
	// branchLayout.setVisibility(View.GONE);
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// }
	//
	// }
	// });
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

}
