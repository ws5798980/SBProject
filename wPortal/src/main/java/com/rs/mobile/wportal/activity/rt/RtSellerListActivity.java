package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.rt.RtSellerListAdapter;
import com.rs.mobile.wportal.biz.rt.SellerTypeCondition;
import com.rs.mobile.wportal.view.rt.autolabel.AutoLabelUI;
import com.rs.mobile.wportal.view.rt.autolabel.Label;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.wportal.CaptureUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ScrollView;
import android.widget.TextView;
import okhttp3.Request;

/**
 * 
 * @author ZhaoYun
 * @date 2017-3-13
 */
public class RtSellerListActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	private String currentType = "";

	private Toolbar toolbar;
	private LinearLayout ll_search;
	private LinearLayout iv_back;
	private ImageView iv_right;

	private LinearLayout ll_cbgroup;
	private CheckBox cb_seller_sort_all;
	private CheckBox cb_seller_sort_near;
	private CheckBox cb_seller_sort_intelligence;
	private CheckBox cb_seller_sort_filter;
	private CheckBox[] cbs;
	private ImageView iv_refreshaddr;
	private PullToRefreshListView lv_sellers;
	private RtSellerListAdapter sellerListAdapter;
	private TextView iv_refreshtext ;

	private JSONArray aMenuJSONArray;
	private JSONArray bMenuJSONArray;
	private JSONArray cMenuJSONArray;
	private JSONArray dMenuJSONArray;

	private PopupWindow menu;

	private SellerTypeCondition condition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_seller_list);
		if (getIntent().getSerializableExtra(C.EXTRA_KEY_SELLERTYPE_CONDITION) != null) {
			condition = (SellerTypeCondition) getIntent().getSerializableExtra(C.EXTRA_KEY_SELLERTYPE_CONDITION);
		} else {
			condition = new SellerTypeCondition();
		}
		initToolbar();
		initViews();
		initListeners();
		initDates();
		
		String searchTypeSortAll = condition.getDisplayName();
		
		if (searchTypeSortAll != null && !searchTypeSortAll.equals(""))
			cb_seller_sort_all.setText(searchTypeSortAll);
		
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
			ll_search = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_search);
			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
			iv_right = (ImageView) findViewById(com.rs.mobile.wportal.R.id.iv_right);
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initViews() {
		ll_cbgroup = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_cbgroup);
		iv_refreshtext = (TextView) findViewById(com.rs.mobile.wportal.R.id.iv_refreshtext);
		cb_seller_sort_all = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.cb_seller_sort_all);
		cb_seller_sort_near = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.cb_seller_sort_near);
		cb_seller_sort_intelligence = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.cb_seller_sort_intelligence);
		cb_seller_sort_filter = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.cb_seller_sort_filter);

		cbs = new CheckBox[] { cb_seller_sort_all, cb_seller_sort_near, cb_seller_sort_intelligence,
				cb_seller_sort_filter };
		iv_refreshaddr = (ImageView) findViewById(com.rs.mobile.wportal.R.id.iv_refreshaddr);
		lv_sellers = (PullToRefreshListView) findViewById(com.rs.mobile.wportal.R.id.lv_sellers);
	}

	private void initListeners() {
		iv_back.setOnClickListener(this);
		ll_search.setOnClickListener(this);
		iv_right.setOnClickListener(this);

		cb_seller_sort_all.setOnClickListener(this);
		cb_seller_sort_near.setOnClickListener(this);
		cb_seller_sort_intelligence.setOnClickListener(this);
		cb_seller_sort_filter.setOnClickListener(this);

		iv_refreshaddr.setOnClickListener(this);
		lv_sellers.setOnItemClickListener(this);
		lv_sellers.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub

				sellerListAdapter.setListItems(new JSONArray());
				sellerListAdapter.notifyDataSetChanged();
				condition.resetPage();
				drawList(condition, true);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub

				if (!condition.isCanLoadMore()) {
					lv_sellers.post(new Runnable() {
						@Override
						public void run() {
							lv_sellers.onRefreshComplete();
						}
					});
				} else {
					drawList(condition, false);
				}

			}
		});
	}

	private void initDates() {
		drawList(condition, true);
	}

	// @Override
	// public void onPullDownToRefresh(PullToRefreshBase refreshView) {
	// sellerListAdapter.setListItems(new JSONArray());
	// sellerListAdapter.notifyDataSetChanged();
	// condition.resetPage();
	// drawList(condition, true);
	//
	// }
	//
	// @Override
	// public void onPullUpToRefresh(PullToRefreshBase refreshView) {
	// if (!condition.isCanLoadMore()) {
	// lv_sellers.onRefreshComplete();
	// return;
	// }
	// drawList(condition, false);
	// }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		try {
			JSONObject j = (JSONObject) sellerListAdapter.getItem(position - 1);

			Intent sellerDetailIntent = new Intent(this, RtSellerDetailActivity.class);
			sellerDetailIntent.putExtra("restaurantCode", j.getString("restaurantCode"));
			startActivity(sellerDetailIntent);

		} catch (Exception e) {
			L.e(e);
		}
	}

	private void queryFilterSetA(String divCode) {
		currentType = "A";
		OkHttpHelper helper = new OkHttpHelper(RtSellerListActivity.this);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");
		helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				cb_seller_sort_all.setChecked(false);
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				try {
					// error
					if (data.has("Message") || (data.has("status") && data.getInt("status") != 1)) {
						cb_seller_sort_all.setChecked(false);
					}
					// success
					else {
						aMenuJSONArray = data.getJSONArray("data");
						showMenuA();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				cb_seller_sort_all.setChecked(false);
			}
		}, Constant.BASE_URL_RT + Constant.RT_COMMONCODE + "?divCode=" + divCode + "&codeType=A", params);
	}

	private void queryFilterSetB(String divCode) {
		currentType = "B";
		OkHttpHelper helper = new OkHttpHelper(RtSellerListActivity.this);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");
		helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				cb_seller_sort_near.setChecked(false);
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				try {
					// error
					if (data.has("Message") || (data.has("status") && data.getInt("status") != 1)) {
						cb_seller_sort_near.setChecked(false);
					}
					// success
					else {
						bMenuJSONArray = data.getJSONObject("data").getJSONArray("Place");
						showMenuB();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				cb_seller_sort_near.setChecked(false);
			}
		}, Constant.BASE_URL_RT + Constant.RT_COMMONCODE + "?divCode=" + divCode + "&codeType=B", params);
	}

	private void queryFilterSetC(String divCode) {
		currentType = "C";
		OkHttpHelper helper = new OkHttpHelper(RtSellerListActivity.this);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");
		helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				cb_seller_sort_intelligence.setChecked(false);
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				try {
					// error
					if (data.has("Message") || (data.has("status") && data.getInt("status") != 1)) {
						cb_seller_sort_intelligence.setChecked(false);
					}
					// success
					else {
						cMenuJSONArray = data.getJSONArray("data");
						showMenuC();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				cb_seller_sort_intelligence.setChecked(false);
			}
		}, Constant.BASE_URL_RT + Constant.RT_COMMONCODE + "?divCode=" + divCode + "&codeType=C", params);
	}

	private void queryFilterSetD(String divCode) {
		currentType = "D";
		OkHttpHelper helper = new OkHttpHelper(RtSellerListActivity.this);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");
		helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				cb_seller_sort_filter.setChecked(false);
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				try {
					// error
					if (data.has("Message") || (data.has("status") && data.getInt("status") != 1)) {
						cb_seller_sort_filter.setChecked(false);
					}
					// success
					else {
						dMenuJSONArray = data.getJSONObject("data").getJSONArray("MainCodeList");
						showMenuD();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				cb_seller_sort_filter.setChecked(false);
			}
		}, Constant.BASE_URL_RT + Constant.RT_COMMONCODE + "?divCode=" + divCode + "&codeType=D", params);
	}

	private void selfDismissTypePop() {
		if (menu != null && menu.isShowing()) {
			menu.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case com.rs.mobile.wportal.R.id.ll_search:
			jumpToSearch(C.DIV_CODE);
			break;

		case com.rs.mobile.wportal.R.id.iv_refreshaddr:

			break;

		case com.rs.mobile.wportal.R.id.iv_back:
			onBack();
			break;

		case com.rs.mobile.wportal.R.id.iv_right:
			Intent i = new Intent(RtSellerListActivity.this, CaptureActivity.class);
			startActivityForResult(i, 2000);
			break;

		case com.rs.mobile.wportal.R.id.cb_seller_sort_all:
		case com.rs.mobile.wportal.R.id.cb_seller_sort_near:
		case com.rs.mobile.wportal.R.id.cb_seller_sort_intelligence:
		case com.rs.mobile.wportal.R.id.cb_seller_sort_filter:
			checkChanged(v.getId(), (CheckBox) v);
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		onBack();
	}

	public void onBack() {
		finish();
	}

	@Override
	protected void onDestroy() {
		if (menu != null && menu.isShowing()) {
			menu.dismiss();
		}
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 2000) { // captureActivity
				CaptureUtil.handleResultScaning(RtSellerListActivity.this, data.getStringExtra("result"), "");
			}
		}
	}

	private void jumpToSearch(String divCode) {
		Intent searchSellerIntent = new Intent(this, RtSearchSellerActivity.class);
		searchSellerIntent.putExtra(C.EXTRA_KEY_DIVCODE, divCode);
		startActivity(searchSellerIntent);
	}

	public void drawList(final SellerTypeCondition condition, final boolean isInit) {

		// 초기화
		if (sellerListAdapter == null) {
			JSONArray arr = new JSONArray();
			sellerListAdapter = new RtSellerListAdapter(RtSellerListActivity.this, arr);
			lv_sellers.setAdapter(sellerListAdapter);
		}

		// 리스트를 다시 그려야 하는 경우
		if (isInit == true) {

			sellerListAdapter.setListItems(new JSONArray());
			sellerListAdapter.notifyDataSetChanged();

		}

		OkHttpHelper helper = new OkHttpHelper(RtSellerListActivity.this);

		HashMap<String, String> params = new HashMap<String, String>();

		params.put("", "");

		params.put("divCode", condition.getDivCode() + "");
		params.put("floorCode", condition.getFloorCode() == null ? "" : condition.getFloorCode());
		params.put("categoryCode", condition.getCategoryCode() == null ? "" : condition.getCategoryCode());
		params.put("timeCode", condition.getTimeCode() == null ? "" : condition.getTimeCode());
		params.put("setMenuCode", condition.getSetMenuCode() == null ? "" : condition.getSetMenuCode());
		params.put("serviceCode", condition.getServiceCode() == null ? "" : condition.getServiceCode());
		params.put("searchValue", condition.getSearchValue() == null ? "" : condition.getSearchValue());
		params.put("smartSearch", condition.getSmartSearch() == null ? "" : condition.getSmartSearch());
		params.put("holyDay", condition.getHolyDay() + "");
		params.put("takeOut", condition.getTakeOut() + "");
		params.put("currentPage", condition.getCurrentPage() + "");

		helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				lv_sellers.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
				try {
					L.d(all_data);
					lv_sellers.onRefreshComplete();
					String status = data.getString("status");

					if (status != null && status.equals("1")) {

						data = data.getJSONObject("data");

						JSONArray restaurantList = data.getJSONArray("restaurantList");

						int currentPage = data.getInt("currentPage");

						int nextPage = data.getInt("nextPage");

						condition.setCurrentPage(currentPage + 1);

						condition.setCanLoadMore(nextPage == 0 ? false : true);

						JSONArray tempArr = sellerListAdapter.getListItems();

						for (int i = 0; i < restaurantList.length(); i++) {

							final JSONObject item = restaurantList.getJSONObject(i);
							tempArr.put(item);

						}
						sellerListAdapter.setListItems(tempArr);
						sellerListAdapter.notifyDataSetChanged();
					}
				} catch (Exception e) {
					L.e(e);
				}

				lv_sellers.onRefreshComplete();
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				lv_sellers.onRefreshComplete();
			}
		}, Constant.BASE_URL_RT + Constant.RT_RESTAURANT_LIST + "?divCode=" + condition.getDivCode() + "&floorCode="
				+ (condition.getFloorCode() == null ? "" : condition.getFloorCode()) + "&categoryCode="
				+ (condition.getCategoryCode() == null ? "" : condition.getCategoryCode()) + "&timeCode="
				+ (condition.getTimeCode() == null ? "" : condition.getTimeCode()) + "&setMenuCode="
				+ (condition.getSetMenuCode() == null ? "" : condition.getSetMenuCode()) + "&serviceCode="
				+ (condition.getServiceCode() == null ? "" : condition.getServiceCode()) + "&searchValue="
				+ (condition.getSearchValue() == null ? "" : condition.getSearchValue()) + "&smartSearch="
				+ (condition.getSmartSearch() == null ? "" : condition.getSmartSearch()) + "&holyDay="
				+ condition.getHolyDay() + "&takeOut=" + condition.getTakeOut() + "&currentPage="
				+ condition.getCurrentPage(), params);
	}

	private void checkChanged(int resId, CheckBox cb) {
		if (cb.isChecked()) {
			selfDismissTypePop();// 主动关闭pop
			for (int i = 0; i < cbs.length; i++) {
				CheckBox icb = cbs[i];
				if (!cb.equals(icb)) {
					icb.setChecked(false);
				}
			}
			switch (cb.getId()) {
			case com.rs.mobile.wportal.R.id.cb_seller_sort_all:
				showMenuA();
				break;
			case com.rs.mobile.wportal.R.id.cb_seller_sort_near:
				showMenuB();
				break;
			case com.rs.mobile.wportal.R.id.cb_seller_sort_intelligence:
				showMenuC();
				break;
			case com.rs.mobile.wportal.R.id.cb_seller_sort_filter:
				showMenuD();
				break;
			default:
				break;
			}
		} else {
			selfDismissTypePop();
		}
	}

	private void showMenuA() {
		// local
		if (aMenuJSONArray != null) {
			menu = instantiationPop(instantiationMenuA(aMenuJSONArray), "A");
			menu.showAsDropDown(ll_cbgroup);
		}
		// network
		else {
			queryFilterSetA(C.DIV_CODE);
		}
	}

	private void showMenuB() {
		// local
		if (bMenuJSONArray != null) {
			menu = instantiationPop(instantiationMenuB(bMenuJSONArray), "B");
			menu.showAsDropDown(ll_cbgroup);
		}
		// network
		else {
			queryFilterSetB(C.DIV_CODE);
		}
	}

	private void showMenuC() {
		// local
		if (cMenuJSONArray != null) {
			menu = instantiationPop(instantiationMenuC(cMenuJSONArray), "C");
			menu.showAsDropDown(ll_cbgroup);
		}
		// network
		else {
			queryFilterSetC(C.DIV_CODE);
		}
	}

	private void showMenuD() {
		// local
		if (dMenuJSONArray != null) {
			menu = instantiationPop(instantiationMenuD(dMenuJSONArray), "D");
			menu.showAsDropDown(ll_cbgroup);
		}
		// network
		else {
			queryFilterSetD(C.DIV_CODE);
		}
	}

	private PopupWindow instantiationPop(View contentView, String type) {
		if (menu != null && menu.isShowing()) {
			menu.dismiss();
		}
		currentType = type;
		menu = new PopupWindow(contentView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		menu.setTouchable(true);
		menu.setOutsideTouchable(true);
		menu.getContentView().setFocusableInTouchMode(true);
		menu.getContentView().setFocusable(true);
		menu.setBackgroundDrawable(new BitmapDrawable());
		menu.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				
				
				if ("A".equals(currentType)) {
					cb_seller_sort_all.setChecked(false);
				} else if ("B".equals(currentType)) {
					cb_seller_sort_near.setChecked(false);
				} else if ("C".equals(currentType)) {
					cb_seller_sort_intelligence.setChecked(false);
				} else if ("D".equals(currentType)) {
					cb_seller_sort_filter.setChecked(false);
				}
			}
		});
		return menu;
	}

	private View instantiationMenuA(final JSONArray jsonArray) {
		ListView contentView = new ListView(this);
		contentView.setBackgroundColor(Color.WHITE);
		contentView.setSelector(getResources().getDrawable(com.rs.mobile.wportal.R.drawable.bg_rt_sellertype1_list_item));
		contentView.setDivider(null);
		contentView.setDividerHeight(0);
		contentView.setAdapter(new AllAdapter(jsonArray));
		contentView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				menu.dismiss();
				try {
					
					String subCodeName = jsonArray.getJSONObject(position).getString("SUB_CODE");
					if (subCodeName != null && !subCodeName.equals(condition.getCategoryCode())) {
						
						condition.resetSearchCondition();
						condition.setCategoryCode(subCodeName);
						drawList(condition, true);
						
						cb_seller_sort_all.setText(jsonArray.getJSONObject(position).getString("SUB_CODE_NAME"));
						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		return contentView;
	}

	private View instantiationMenuB(final JSONArray jsonArray) {
		LinearLayout contentView = (LinearLayout) getLayoutInflater().inflate(com.rs.mobile.wportal.R.layout.layout_doublelist_menu, null);
		contentView.setFocusable(true);
		contentView.setFocusableInTouchMode(true);
		final ListView placeLv = (ListView) contentView.findViewById(com.rs.mobile.wportal.R.id.lv_place);
		final ListView floorLv = (ListView) contentView.findViewById(com.rs.mobile.wportal.R.id.lv_floor);
		final List<String> placeList = new ArrayList<>();
		final List<JSONArray> floorList = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				placeList.add(jsonArray.getJSONObject(i).getString("PlaceName"));
				floorList.add(jsonArray.getJSONObject(i).getJSONArray("FloorList"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		final PlaceAdapter placeAdapter = new PlaceAdapter(placeList);
		placeLv.setAdapter(placeAdapter);
		placeLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					
					iv_refreshtext.setText("当前：" + placeList.get(position));
					
					placeAdapter.setSelectedPosition(position);
					placeLv.setSelection(position);
					placeAdapter.notifyDataSetChanged();
					floorLv.setAdapter(new FloorAdapter(position, floorList.get(position)));
				
				} catch (Exception e) {
					
					L.e(e);
					
				}
			}
		});
		floorLv.setAdapter(new FloorAdapter(0, floorList.get(0)));
		floorLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				menu.dismiss();

				try {
					String floorCode = jsonArray.getJSONObject(((FloorAdapter) parent.getAdapter()).getParentPosition())
							.getJSONArray("FloorList").getJSONObject(position).getString("SUB_CODE");
					if (floorCode != null && !floorCode.equals(condition.getFloorCode())) {

						condition.resetSearchCondition();

						condition.setFloorCode(floorCode);
						drawList(condition, true);
						
						cb_seller_sort_near.setText(jsonArray.getJSONObject(((FloorAdapter) parent.getAdapter()).getParentPosition())
								.getJSONArray("FloorList").getJSONObject(position).getString("SUB_CODE_NAME"));
						
					}
					// drawList(condition);
				} catch (JSONException e) {
					L.e(e);
				}
			}
		});
		return contentView;
	}

	private View instantiationMenuC(final JSONArray jsonArray) {

		LinearLayout contentView = (LinearLayout) getLayoutInflater().inflate(com.rs.mobile.wportal.R.layout.layout_single_menu, null);
		ListView listView = (ListView) contentView.findViewById(com.rs.mobile.wportal.R.id.list_view);
		listView.setAdapter(new IntelligenceAdapter(jsonArray));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				menu.dismiss();
				try {
					String subCodeName = jsonArray.getJSONObject(position).getString("SUB_CODE");

					// if (subCodeName != null &&
					// !subCodeName.equals(condition.getSetMenuCode())) {
					//
					// condition.resetSearchCondition();
					// condition.setSetMenuCode(subCodeName);
					// drawList(condition, true);
					// }

					if (subCodeName != null && !subCodeName.equals(condition.getSmartSearch())) {
						condition.resetSearchCondition();
						condition.setSmartSearch(subCodeName);
						drawList(condition, true);
						
						cb_seller_sort_intelligence.setText(jsonArray.getJSONObject(position).getString("SUB_CODE_NAME"));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		return contentView;
	}

	private class AllAdapter extends BaseAdapter {

		private JSONArray allList;

		public AllAdapter(JSONArray allList) {
			this.allList = allList;
		}

		@Override
		public int getCount() {
			return allList.length();
		}

		@Override
		public Object getItem(int position) {
			try {
				return allList.getJSONObject(position);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			AllViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new AllViewHolder();
				convertView = getLayoutInflater().inflate(com.rs.mobile.wportal.R.layout.layout_rt_simple_list_item, null);
				viewHolder.tv_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_name);
				viewHolder.tv_count = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_count);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (AllViewHolder) convertView.getTag();
			}
			try {
				viewHolder.tv_name.setText(allList.getJSONObject(position).getString("SUB_CODE_NAME"));
				viewHolder.tv_count.setText(allList.getJSONObject(position).getInt("CUSTOM_COUNT") + "");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return convertView;
		}

		private class AllViewHolder {
			public TextView tv_name;
			public TextView tv_count;
		}

	}

	private class IntelligenceAdapter extends BaseAdapter {

		private JSONArray allList;

		public IntelligenceAdapter(JSONArray allList) {
			this.allList = allList;
		}

		@Override
		public int getCount() {
			return allList.length();
		}

		@Override
		public Object getItem(int position) {
			try {
				return allList.getJSONObject(position);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			IntelligenceAdapterViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new IntelligenceAdapterViewHolder();
				convertView = getLayoutInflater().inflate(com.rs.mobile.wportal.R.layout.layout_rt_simple_list_item, null);
				viewHolder.tv_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_name);
				viewHolder.tv_count = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_count);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (IntelligenceAdapterViewHolder) convertView.getTag();
			}
			try {
				viewHolder.tv_name.setText(allList.getJSONObject(position).getString("SUB_CODE_NAME"));
				if (allList.getJSONObject(position).has("CUSTOM_COUNT"))
					viewHolder.tv_count.setText(allList.getJSONObject(position).getString("CUSTOM_COUNT") + "");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return convertView;
		}

		private class IntelligenceAdapterViewHolder {
			public TextView tv_name;
			public TextView tv_count;
		}

	}

	private class PlaceAdapter extends BaseAdapter {

		private List<String> placeList;

		private int selectedPosition = 0;

		public void setSelectedPosition(int selectedPosition) {
			if (selectedPosition != this.selectedPosition) {
				this.selectedPosition = selectedPosition;
			}
		}

		public PlaceAdapter(List<String> placeList) {
			this.placeList = placeList;
		}

		@Override
		public int getCount() {
			return placeList.size();
		}

		@Override
		public Object getItem(int position) {
			return placeList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			PlaceViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new PlaceViewHolder();
				convertView = new TextView(RtSellerListActivity.this);
				((TextView) convertView).setPadding(
						(int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,
								getResources().getDisplayMetrics())),
						(int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
								getResources().getDisplayMetrics())),
						(int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,
								getResources().getDisplayMetrics())),
						(int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
								getResources().getDisplayMetrics())));
				((TextView) convertView).setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
				((TextView) convertView).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
				((TextView) convertView).setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.textColor_rt_toolbar_title));
				convertView.setFocusable(false);
				viewHolder.tv_place = (TextView) convertView;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (PlaceViewHolder) convertView.getTag();
			}
			viewHolder.tv_place.setText(placeList.get(position));
			if (position == selectedPosition) {
				convertView.setBackgroundColor(Color.WHITE);
			} else {
				convertView.setBackgroundColor(
						RtSellerListActivity.this.getResources().getColor(com.rs.mobile.wportal.R.color.backgroundColor_rt_window));
			}
			return convertView;
		}

		private class PlaceViewHolder {
			public TextView tv_place;
		}

	}

	private class FloorAdapter extends BaseAdapter {

		private final int parentPosition;
		private JSONArray jsonArray;

		public FloorAdapter(int parentPosition, JSONArray jsonArray) {
			this.parentPosition = parentPosition;
			this.jsonArray = jsonArray;
		}

		public int getParentPosition() {
			return parentPosition;
		}

		@Override
		public int getCount() {
			return jsonArray.length();
		}

		@Override
		public Object getItem(int position) {
			try {
				return jsonArray.getJSONObject(position).getString("SUB_CODE_NAME");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			FloorViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new FloorViewHolder();
				convertView = new TextView(RtSellerListActivity.this);
				((TextView) convertView).setPadding(
						(int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,
								getResources().getDisplayMetrics())),
						(int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
								getResources().getDisplayMetrics())),
						(int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,
								getResources().getDisplayMetrics())),
						(int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
								getResources().getDisplayMetrics())));
				((TextView) convertView).setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
				((TextView) convertView).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
				((TextView) convertView).setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.textColor_rt_toolbar_title));
				viewHolder.tv_floor = (TextView) convertView;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (FloorViewHolder) convertView.getTag();
			}
			try {
				viewHolder.tv_floor.setText(jsonArray.getJSONObject(position).getString("SUB_CODE_NAME"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				L.e(e);
			}
			return convertView;
		}

		private class FloorViewHolder {
			public TextView tv_floor;
		}

	}

	private View instantiationMenuD(JSONArray jsonArray) {
		ScrollView scrollView = (ScrollView) getLayoutInflater().inflate(com.rs.mobile.wportal.R.layout.layout_rt_sellertype4_pop, null);
		LinearLayout contentView = (LinearLayout) scrollView.findViewById(com.rs.mobile.wportal.R.id.ll_selltype4);

		final CheckBox cb_sellertype_takeout = (CheckBox) scrollView.findViewById(com.rs.mobile.wportal.R.id.cb_sellertype_takeout);
		final CheckBox cb_sellertype_holyday = (CheckBox) scrollView.findViewById(com.rs.mobile.wportal.R.id.cb_sellertype_holyday);
		AutoLabelUI al_sellertype4_lables_002 = null;
		AutoLabelUI al_sellertype4_lables_003 = null;
		AutoLabelUI al_sellertype4_lables_004 = null;

		TextView tv_sellertype_reset = (TextView) scrollView.findViewById(com.rs.mobile.wportal.R.id.tv_sellertype_reset);
		TextView tv_sellertype_complete = (TextView) scrollView.findViewById(com.rs.mobile.wportal.R.id.tv_sellertype_complete);

		JSONArray f002JSONArray = null;
		JSONArray f003JSONArray = null;
		JSONArray f004JSONArray = null;

		try {
			contentView.setFocusable(true);
			contentView.setFocusableInTouchMode(true);

			LinearLayout ll_selltype4 = (LinearLayout) contentView.findViewById(com.rs.mobile.wportal.R.id.ll_selltype4);

			try {
				for (int i = 0; i < jsonArray.length(); i++) {
					if ("F002".equals(jsonArray.getJSONObject(i).getString("MainCode"))) {
						f002JSONArray = jsonArray.getJSONObject(i).getJSONArray("SubCodeList");
					} else if ("F003".equals(jsonArray.getJSONObject(i).getString("MainCode"))) {
						f003JSONArray = jsonArray.getJSONObject(i).getJSONArray("SubCodeList");
					} else if ("F004".equals(jsonArray.getJSONObject(i).getString("MainCode"))) {
						f004JSONArray = jsonArray.getJSONObject(i).getJSONArray("SubCodeList");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (f002JSONArray != null) {
				LinearLayout f002Layout = (LinearLayout) getLayoutInflater()
						.inflate(com.rs.mobile.wportal.R.layout.layout_rt_sellertype4_labels, null);
				TextView f002Title = (TextView) f002Layout.findViewById(com.rs.mobile.wportal.R.id.tv_labels_title);
				f002Title.setText(getString(com.rs.mobile.wportal.R.string.common_text065));
				al_sellertype4_lables_002 = (AutoLabelUI) f002Layout.findViewById(com.rs.mobile.wportal.R.id.al_sellertype4_lables);
				for (int i = 0; i < f002JSONArray.length(); i++) {
					al_sellertype4_lables_002.addLabel(f002JSONArray.getJSONObject(i).getString("SUB_CODE_NAME"));
				}
				ll_selltype4.addView(f002Layout);
			}
			if (f003JSONArray != null) {
				LinearLayout f003Layout = (LinearLayout) getLayoutInflater()
						.inflate(com.rs.mobile.wportal.R.layout.layout_rt_sellertype4_labels, null);
				TextView f003Title = (TextView) f003Layout.findViewById(com.rs.mobile.wportal.R.id.tv_labels_title);
				f003Title.setText(getString(com.rs.mobile.wportal.R.string.common_text066));
				al_sellertype4_lables_003 = (AutoLabelUI) f003Layout.findViewById(com.rs.mobile.wportal.R.id.al_sellertype4_lables);
				for (int i = 0; i < f003JSONArray.length(); i++) {
					al_sellertype4_lables_003.addLabel(f003JSONArray.getJSONObject(i).getString("SUB_CODE_NAME"));
				}
				ll_selltype4.addView(f003Layout);
			}
			if (f004JSONArray != null) {
				LinearLayout f004Layout = (LinearLayout) getLayoutInflater()
						.inflate(com.rs.mobile.wportal.R.layout.layout_rt_sellertype4_labels, null);
				TextView f004Title = (TextView) f004Layout.findViewById(com.rs.mobile.wportal.R.id.tv_labels_title);
				f004Title.setText(getString(com.rs.mobile.wportal.R.string.common_text067));
				al_sellertype4_lables_004 = (AutoLabelUI) f004Layout.findViewById(com.rs.mobile.wportal.R.id.al_sellertype4_lables);
				for (int i = 0; i < f004JSONArray.length(); i++) {
					al_sellertype4_lables_004.addLabel(f004JSONArray.getJSONObject(i).getString("SUB_CODE_NAME"));
				}
				ll_selltype4.addView(f004Layout);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		final AutoLabelUI finalal_sellertype4_lables_002 = al_sellertype4_lables_002;
		final AutoLabelUI finalal_sellertype4_lables_003 = al_sellertype4_lables_003;
		final AutoLabelUI finalal_sellertype4_lables_004 = al_sellertype4_lables_004;

		final JSONArray finalf002JSONArray = f002JSONArray;
		final JSONArray finalf003JSONArray = f003JSONArray;
		final JSONArray finalf004JSONArray = f004JSONArray;

		tv_sellertype_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cb_sellertype_takeout.setChecked(false);
				cb_sellertype_holyday.setChecked(false);
				if (finalal_sellertype4_lables_002 != null) {
					for (Label label : finalal_sellertype4_lables_002.getLabels()) {
						if (label.isSelected()) {
							label.getLabelPanel().setSelected(false);
						}
					}
					finalal_sellertype4_lables_002.onClickLabel(null);
				}
				if (finalal_sellertype4_lables_003 != null) {
					for (Label label : finalal_sellertype4_lables_003.getLabels()) {
						if (label.isSelected()) {
							label.getLabelPanel().setSelected(false);
						}
					}
					finalal_sellertype4_lables_003.onClickLabel(null);
				}
				if (finalal_sellertype4_lables_004 != null) {
					for (Label label : finalal_sellertype4_lables_004.getLabels()) {
						if (label.isSelected()) {
							label.getLabelPanel().setSelected(false);
						}
					}
					finalal_sellertype4_lables_004.onClickLabel(null);
				}
			}
		});
		tv_sellertype_complete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menu.dismiss();

				condition.resetSearchCondition();
				condition.setTakeOut(cb_sellertype_takeout.isChecked() ? 1 : 2);
				condition.setHolyDay(cb_sellertype_holyday.isChecked() ? 1 : 2);
				// F002 -> 用餐时段 -> List -> Child -> SUB_CODE -> timeCode
				// F003 -> 用餐人数 -> List -> Child -> SUB_CODE -> setMenuCode
				// f004 -> 餐厅服务 -> List -> Child -> SUB_CODE -> serviceCode
				try {
					if (finalal_sellertype4_lables_002 != null) {
						for (int i = 0; i < finalal_sellertype4_lables_002.getLabels().size(); i++) {
							Label label = finalal_sellertype4_lables_002.getLabels().get(i);
							if (label.isSelected()) {
								condition.setTimeCode(finalf002JSONArray.getJSONObject(i).getString("SUB_CODE"));
							}
						}
					}

					if (finalal_sellertype4_lables_003 != null) {
						for (int i = 0; i < finalal_sellertype4_lables_003.getLabels().size(); i++) {
							Label label = finalal_sellertype4_lables_003.getLabels().get(i);
							if (label.isSelected()) {
								condition.setSetMenuCode(finalf003JSONArray.getJSONObject(i).getString("SUB_CODE"));
							}
						}
					}

					if (finalal_sellertype4_lables_004 != null) {
						for (int i = 0; i < finalal_sellertype4_lables_004.getLabels().size(); i++) {
							Label label = finalal_sellertype4_lables_004.getLabels().get(i);
							if (label.isSelected()) {
								condition.setServiceCode(finalf004JSONArray.getJSONObject(i).getString("SUB_CODE"));
							}
						}
					}

					drawList(condition, true);

				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

			}
		});
		return scrollView;
	}

}