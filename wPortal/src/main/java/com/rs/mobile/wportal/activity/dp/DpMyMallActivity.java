package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.ObservableScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.S;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.adapter.dp.DpViewPagerAdapter;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.dp.DpSerchAdapter;
import com.rs.mobile.wportal.view.CustomViewPager;
import com.rs.mobile.wportal.view.MenuButtonView;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class DpMyMallActivity extends BaseActivity implements OnRefreshListener2<ObservableScrollView> {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private CustomViewPager view_pager;
	private MenuButtonView dp_menu_01;
	private MenuButtonView dp_menu_02;
	private MenuButtonView dp_menu_03;
	private MenuButtonView dp_menu_04;
	private MenuButtonView dp_menu_05;
	private MenuButtonView dp_menu_06;
	private MenuButtonView dp_menu_07;
	private MenuButtonView dp_menu_08;
	private ArrayList<MenuButtonView> menuButtonList = new ArrayList<>();
	private ArrayList<WImageView> viewList1 = new ArrayList<>();
	private ArrayList<WImageView> viewList2 = new ArrayList<>();
	private WImageView banner_001;
	private WImageView banner_002;
	private WImageView banner_003;
	private WImageView banner_004;
	private WImageView banner_005;
	private WImageView banner_006;
	private WImageView banner_007;
	private WImageView banner_008;
	private WImageView banner_009;
	private WImageView banner_010;
	private WImageView banner_011;
	private WImageView banner_012;
	private GridView gv;
	protected DpSerchAdapter adapter;
	private PullToRefreshScrollView sv;
	private LinearLayout ll_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dp_mymall);
		iniToolbar();
		initView();

		initData();
	}

	private void iniToolbar() {
		try {

			toolbar = (Toolbar) findViewById(R.id.toolbar);
			ll_search = (LinearLayout) findViewById(R.id.ll_search);
			ll_search.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PageUtil.jumpTo(DpMyMallActivity.this, DpSeachActivity.class);
				}
			});
			iv_back = (LinearLayout) findViewById(R.id.iv_back);
			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			tv_title = (TextView) findViewById(R.id.tv_title);
			tv_title.setText(getResources().getString(R.string.dp_text_btn4));
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initView() {
		sv = (PullToRefreshScrollView) findViewById(R.id.sv);
		sv.setOnRefreshListener(this);
		sv.setMode(Mode.PULL_FROM_START);
		view_pager = (CustomViewPager) findViewById(R.id.view_pager);
		dp_menu_01 = (MenuButtonView) findViewById(R.id.dp_menu_01);
		dp_menu_02 = (MenuButtonView) findViewById(R.id.dp_menu_02);
		dp_menu_03 = (MenuButtonView) findViewById(R.id.dp_menu_03);
		dp_menu_04 = (MenuButtonView) findViewById(R.id.dp_menu_04);
		dp_menu_05 = (MenuButtonView) findViewById(R.id.dp_menu_05);
		dp_menu_06 = (MenuButtonView) findViewById(R.id.dp_menu_06);
		dp_menu_07 = (MenuButtonView) findViewById(R.id.dp_menu_07);
		dp_menu_08 = (MenuButtonView) findViewById(R.id.dp_menu_08);

		menuButtonList.add(dp_menu_01);
		menuButtonList.add(dp_menu_02);
		menuButtonList.add(dp_menu_03);
		menuButtonList.add(dp_menu_04);
		menuButtonList.add(dp_menu_05);
		menuButtonList.add(dp_menu_06);
		menuButtonList.add(dp_menu_07);
		menuButtonList.add(dp_menu_08);

		banner_001 = (WImageView) findViewById(R.id.banner_001);
		banner_002 = (WImageView) findViewById(R.id.banner_002);
		banner_003 = (WImageView) findViewById(R.id.banner_003);
		banner_004 = (WImageView) findViewById(R.id.banner_004);
		banner_005 = (WImageView) findViewById(R.id.banner_005);
		banner_006 = (WImageView) findViewById(R.id.banner_006);

		viewList1.add(banner_001);
		viewList1.add(banner_002);
		viewList1.add(banner_003);
		viewList1.add(banner_004);
		viewList1.add(banner_005);
		viewList1.add(banner_006);

		banner_007 = (WImageView) findViewById(R.id.banner_007);
		banner_008 = (WImageView) findViewById(R.id.banner_008);
		banner_009 = (WImageView) findViewById(R.id.banner_009);
		banner_010 = (WImageView) findViewById(R.id.banner_010);
		banner_011 = (WImageView) findViewById(R.id.banner_011);
		banner_012 = (WImageView) findViewById(R.id.banner_012);

		viewList2.add(banner_007);
		viewList2.add(banner_008);
		viewList2.add(banner_009);
		viewList2.add(banner_010);
		viewList2.add(banner_011);
		viewList2.add(banner_012);
		gv = (GridView) findViewById(R.id.gv);

	}

	private void initViewPager(JSONObject obj) {

		try {

			DpViewPagerAdapter adapter = new DpViewPagerAdapter(DpMyMallActivity.this, obj, (float) 2.5);
			view_pager.setAdapter(adapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initData() {
		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();

		try {
			obj.put("lang_type", C.KEY_JSON_LANG_TYPE);
			obj.put("memid", S.getShare(getApplicationContext(),
					S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""), ""));
			obj.put("mall_home_id", "186731755542e810");
			obj.put("token",
					S.getShare(getApplicationContext(), S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""), ""));
			obj.put("app_type", "G");
			obj.put("page_type", "mall");
			obj.put("div_code", C.DIV_CODE);
			obj1.put("myData", obj.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}

		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;Charset=UTF-8");
		OkHttpHelper okHttpHelper = new OkHttpHelper(DpMyMallActivity.this);
		okHttpHelper.addPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				sv.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv.onRefreshComplete();
				try {
					JSONObject obj = new JSONObject(data.toString());
					initViewPager(obj);
					JSONArray arrIcon = obj.getJSONArray("icon");
					initIcon(arrIcon);
					JSONArray event10 = obj.getJSONArray("event10");
					JSONObject obj1 = new JSONObject(event10.getJSONObject(0).toString());
					JSONArray goods10 = obj1.getJSONArray("goods10");
					initView001(goods10);
					JSONArray event20 = obj.getJSONArray("event20");
					JSONObject obj2 = new JSONObject(event20.getJSONObject(0).toString());
					JSONArray goods20 = obj2.getJSONArray("goods20");
					initView002(goods20);
					JSONArray event30 = obj.getJSONArray("event30");
					JSONObject obj3 = new JSONObject(event30.getJSONObject(0).toString());
					JSONArray goods30 = obj3.getJSONArray("goods30");
					initGridView(goods30);
					sv.getRefreshableView().smoothScrollTo(0, 0);

				} catch (Exception e) {
					// TODO: handle exception
					L.e(e);
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv.onRefreshComplete();
			}
		}, Constant.BASE_URL_DP + Constant.Dp_GET_MALLMAIN, headers, obj.toString());
	}

	private void initIcon(JSONArray arr) {
		try {
			for (int i = 0; i < arr.length(); i++) {
				JSONObject jsonObj = new JSONObject(arr.getJSONObject(i).toString());
				final String level1 = jsonObj.get("level1").toString();
				final String level2 = jsonObj.get("level2").toString();
				final String level3 = jsonObj.get("level3").toString();
				menuButtonList.get(i).setIcon(Uri.parse(jsonObj.getString("icon")));
				menuButtonList.get(i).setText(jsonObj.getString("title"));
				menuButtonList.get(i).setIconParam(get_windows_width(DpMyMallActivity.this) / 8,
						get_windows_width(DpMyMallActivity.this) / 8);
				if (jsonObj.get("link_type").equals("1")) {
					menuButtonList.get(i).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Bundle bundle = new Bundle();
							bundle.putString("flag", "level");
							bundle.putString("level1", level1);
							bundle.putString("level2", level2);
							bundle.putString("level3", level3);
							PageUtil.jumpTo(DpMyMallActivity.this, DpSerchResultActivity.class, bundle);
						}
					});
				} else {
					menuButtonList.get(i).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							PageUtil.jumpTo(DpMyMallActivity.this, DpClassifyActivity.class);
						}
					});
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}
	}

	private void initView001(JSONArray arr) {
		try {
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = new JSONObject(arr.getJSONObject(i).toString());
				final String item_code = obj.get("item_code").toString();
				String image_url = obj.getString("image_url");
				ImageUtil.drawImageFromUri(image_url, viewList1.get(i));
				viewList1.get(i).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Bundle bundle = new Bundle();
						bundle.putString("item_code", item_code);
						bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
						PageUtil.jumpTo(DpMyMallActivity.this, DpGoodsDetailActivity.class, bundle);
					}
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initView002(JSONArray arr) {
		try {
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = new JSONObject(arr.getJSONObject(i).toString());
				final String item_code = obj.get("item_code").toString();
				String image_url = obj.getString("image_url");
				ImageUtil.drawImageFromUri(image_url, viewList2.get(i));
				viewList2.get(i).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Bundle bundle = new Bundle();
						bundle.putString("item_code", item_code);
						bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
						PageUtil.jumpTo(DpMyMallActivity.this, DpGoodsDetailActivity.class, bundle);
					}
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initGridView(JSONArray arr) {
		adapter = new DpSerchAdapter(arr, DpMyMallActivity.this, "assess_cnt", "item_p", false);
		// 固定列宽，有多少列
		int col = 2;// listView.getNumColumns();
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		for (int i = 0; i < adapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = adapter.getView(i, null, gv);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}
		// 获取listview的布局参数
		ViewGroup.LayoutParams params = gv.getLayoutParams();
		// 设置高度
		params.height = totalHeight;

		// 设置参数
		gv.setLayoutParams(params);
		gv.setAdapter(adapter);

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		// TODO Auto-generated method stub
		initData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		// TODO Auto-generated method stub

	}
}
