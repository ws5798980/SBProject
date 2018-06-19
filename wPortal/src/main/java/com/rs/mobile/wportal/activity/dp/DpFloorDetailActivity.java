package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.dp.DpSerchAdapter;
import com.rs.mobile.wportal.adapter.dp.DpViewPagerAdapter;
import com.rs.mobile.wportal.adapter.dp.DpbrandImageAdapter;
import com.rs.mobile.wportal.view.CustomViewPager;
import com.rs.mobile.wportal.view.dp.DpCatogoryButtonView;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class DpFloorDetailActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title, text_detail;
	private CustomViewPager view_pager;
	private String floor_num;
	private LinearLayout horizontal_scroll_area;
	private GridView gv, gv1, gv2;
	private ArrayList<String> brandList = new ArrayList<>();
	private ArrayList<JSONArray> arrList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_dp_floordetail);
		floor_num = getIntent().getStringExtra("floor_num");
		initToolbar();
		initView();

		initData();
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
			tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {
		try {
			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			tv_title.setText(floor_num + "F");
			view_pager = (CustomViewPager) findViewById(com.rs.mobile.wportal.R.id.view_pager);
			horizontal_scroll_area = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.horizontal_scroll_area);
			text_detail = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_detail);
			gv = (GridView) findViewById(com.rs.mobile.wportal.R.id.gv);
			gv1 = (GridView) findViewById(com.rs.mobile.wportal.R.id.gv1);
			gv2 = (GridView) findViewById(com.rs.mobile.wportal.R.id.gv2);
		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}
	}

	private void initViewPager(JSONObject obj) {

		try {

			DpViewPagerAdapter adapter = new DpViewPagerAdapter(DpFloorDetailActivity.this, obj, (float) 2.5);
			view_pager.setAdapter(adapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initData() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;Charset=UTF-8");
		JSONObject obj = new JSONObject();

		JSONObject obj1 = new JSONObject();
		try {
			obj.put("lang_type", C.KEY_JSON_LANG_TYPE);
			obj.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
			obj.put("floor_num", floor_num);
			obj.put("div_code", C.DIV_CODE);
			obj.put("ad_type", "400,402");
			obj.put("event_type", "406,408");
			obj.put("memid", S.get(getApplicationContext(), C.KEY_SHARED_PHONE_NUMBER, ""));
			// obj1.put("myData", obj.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		OkHttpHelper okHttpHelper = new OkHttpHelper(DpFloorDetailActivity.this);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = data;
					initViewPager(obj);
					JSONArray floorBrandCategory = obj.getJSONArray("floorBrandCategory");
					for (int i = 0; i < floorBrandCategory.length(); i++) {
						JSONObject obj1 = new JSONObject(floorBrandCategory.getJSONObject(i).toString());
						brandList.add(obj1.getString("ad_type_name"));
						arrList.add(obj1.getJSONArray("mBrandList"));
					}
					text_detail.setText(obj.getString("floorMsg"));

					initCatogoryView(brandList);
					initGV(arrList.get(0));
					JSONArray floorEvent10 = obj.getJSONArray("floorEvent10");
					JSONObject obj10 = floorEvent10.getJSONObject(0);
					JSONArray eventGoods10 = obj10.getJSONArray("eventGoods10");
					initGV1(eventGoods10);
					JSONArray floorEvent20 = obj.getJSONArray("floorEvent20");
					JSONObject obj20 = floorEvent20.getJSONObject(0);
					JSONArray eventGoods20 = obj20.getJSONArray("eventGoods20");
					initGridView(eventGoods20);
				} catch (Exception e) {
					// TODO: handle exception
					L.e(e);
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_DP + Constant.DP_GET_FLOORDETAIL, headers, obj.toString());
	}

	private void initCatogoryView(ArrayList<String> arr) {
		for (int i = 0; i < arr.size(); i++) {
			final DpCatogoryButtonView v = new DpCatogoryButtonView(DpFloorDetailActivity.this);
			v.setTitle(arr.get(i));
			final int d = i;
			horizontal_scroll_area.addView(v);
			if (i == 0) {
				v.selectButton();

			}
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View c) {
					// TODO Auto-generated method stub
					for (int j = 0; j < horizontal_scroll_area.getChildCount(); j++) {
						((DpCatogoryButtonView) horizontal_scroll_area.getChildAt(j)).unSelectButton();
					}
					v.selectButton();
					initGV(arrList.get(d));
				}

			});
		}

	}

	private void initGV(final JSONArray arr) {
		DpbrandImageAdapter adapter = new DpbrandImageAdapter(arr, DpFloorDetailActivity.this, 4);
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = arr.getJSONObject(position);
					String custom_code = obj.getString("custom_code");
					Bundle bundle = new Bundle();
					bundle.putString("custom_code", custom_code);
					bundle.putString("flag", "custom_code");
					PageUtil.jumpTo(DpFloorDetailActivity.this, DpSerchResultActivity.class, bundle);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// 固定列宽，有多少列
		int col = 4;// listView.getNumColumns();
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
	}

	private void initGV1(final JSONArray arr) {

		DpbrandImageAdapter adapter = new DpbrandImageAdapter(arr, DpFloorDetailActivity.this, 3);
		gv1.setAdapter(adapter);
		gv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = arr.getJSONObject(position);
					String item_code = obj.getString("item_code");
					Bundle bundle = new Bundle();
					bundle.putString("item_code", item_code);
					bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
					PageUtil.jumpTo(DpFloorDetailActivity.this, DpGoodsDetailActivity.class, bundle);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// 固定列宽，有多少列
		int col = 3;// listView.getNumColumns();
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		for (int i = 0; i < adapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = adapter.getView(i, null, gv1);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}
		// 获取listview的布局参数
		ViewGroup.LayoutParams params = gv1.getLayoutParams();
		// 设置高度
		params.height = totalHeight;

		// 设置参数
		gv1.setLayoutParams(params);

	}

	private void initGridView(JSONArray arr) {
		DpSerchAdapter adapter = new DpSerchAdapter(arr, DpFloorDetailActivity.this, "assess_cnt", "item_p", false);
		// 固定列宽，有多少列
		int col = 2;// listView.getNumColumns();
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		for (int i = 0; i < adapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = adapter.getView(i, null, gv2);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}
		// 获取listview的布局参数
		ViewGroup.LayoutParams params = gv2.getLayoutParams();
		// 设置高度
		params.height = totalHeight;

		// 设置参数
		gv2.setLayoutParams(params);
		gv2.setAdapter(adapter);

	}
}
