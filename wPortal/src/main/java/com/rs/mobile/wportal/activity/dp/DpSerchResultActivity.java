package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.dp.DpSerchAdapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import okhttp3.Request;

public class DpSerchResultActivity extends BaseActivity implements OnRefreshListener2<GridView> {

	private Toolbar toolbar;
	private LinearLayout ll_div;
	private TextView tv_divname;
	private LinearLayout ll_search;
	private String brandCode;
	private String searchText, flag;
	private int pageIndex;
	private int pageSize;
	private int total;
	private String sortType, level1, level2, level3;
	private String orderByType;
	private TextView textview_001;
	private TextView textview_002;
	private TextView textview_003;
	private boolean flagPrice = true;// 价格从低到高
	private Drawable drawable1;
	private Drawable drawable2;
	private Drawable drawable3;
	private Drawable drawable4;
	private Drawable drawable5;
	private ListView lv_select;
	private PullToRefreshGridView sv_serch;
	private JSONArray arrtotal = new JSONArray();
	private DpSerchAdapter adapter;
	private TextView tv_tips;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dp_serchresult);
		brandCode = "";
		searchText = "";
		pageIndex = 1;
		pageSize = 10;
		sortType = "1";
		orderByType = "1";
		level1 = "";
		level2 = "";
		level3 = "";

		try {

			brandCode = getIntent().getStringExtra("custom_code");

			level1 = getIntent().getStringExtra("level1");
			level2 = getIntent().getStringExtra("level2");
			level3 = getIntent().getStringExtra("level3");

			searchText = getIntent().getStringExtra("keyWord");

			brandCode = brandCode == null ? "" : brandCode;
			level1 = level1 == null ? "" : level1;
			level2 = level2 == null ? "" : level2;
			level3 = level3 == null ? "" : level3;
			searchText = searchText == null ? "" : searchText;

			// searchText=getIntent().getStringExtra("keyWord");
		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}
		initToolbar();
		initView();
		initSelectList();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		OkHttpHelper okHttpHelper = new OkHttpHelper(DpSerchResultActivity.this);
		HashMap<String, String> params = new HashMap<>();
		params.put("brandCode", brandCode);
		params.put("searchText", searchText);
		params.put("pageIndex", pageIndex + "");
		params.put("pageSize", pageSize + "");
		params.put("sortType", sortType);
		params.put("orderByType", orderByType);
		params.put("level1", level1);
		params.put("level2", level2);
		params.put("level3", level3);
		params.put("divCode", C.DIV_CODE);
		okHttpHelper.addPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				sv_serch.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					sv_serch.onRefreshComplete();
					if (data.get("status").toString().equals("1")) {
						total = Integer.parseInt(data.get("total").toString());
						if (total == 0) {
							sv_serch.setVisibility(View.GONE);
							tv_tips.setVisibility(View.VISIBLE);
						} else {
							sv_serch.setVisibility(View.VISIBLE);
							tv_tips.setVisibility(View.GONE);
						}
						JSONArray arr = data.getJSONArray("data");
						if (pageIndex == 1) {
							arrtotal = arr;
							adapter = new DpSerchAdapter(arrtotal, DpSerchResultActivity.this, "comments", "item_price",
									true);
							sv_serch.setAdapter(adapter);
						} else {
							int sv_position = arrtotal.length() + 1;
							for (int i = 0; i < arr.length(); i++) {
								arrtotal.put(arr.get(i));
							}
							adapter.notifyDataSetChanged();

						}

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv_serch.onRefreshComplete();
			}
		}, Constant.BASE_URL_DP1 + Constant.DP_GETGOODSLIST, params);
	}

	private void initToolbar() {

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		ll_div = (LinearLayout) toolbar.findViewById(R.id.ll_div);

		ll_search = (LinearLayout) toolbar.findViewById(R.id.ll_search);

		setSupportActionBar(toolbar);
	}

	@SuppressLint("NewApi")
	private void initView() {
		sv_serch = (PullToRefreshGridView) findViewById(R.id.sv_serch);
		sv_serch.setOnRefreshListener(this);

		lv_select = (ListView) findViewById(R.id.lv_select);
		ll_div.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ll_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("level1", level1);
				bundle.putString("custom_code", brandCode);
				PageUtil.jumpTo(DpSerchResultActivity.this, DpSeachActivity.class, bundle);
				finish();
			}
		});
		tv_tips = (TextView) findViewById(R.id.tv_tips);
		textview_001 = (TextView) findViewById(R.id.textview_001);
		textview_002 = (TextView) findViewById(R.id.textview_002);
		textview_003 = (TextView) findViewById(R.id.textview_003);
		drawable1 = ContextCompat.getDrawable(DpSerchResultActivity.this, R.drawable.dp_icon_22);
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());

		drawable2 = ContextCompat.getDrawable(DpSerchResultActivity.this, R.drawable.dp_icon_221);
		drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());

		drawable3 = ContextCompat.getDrawable(DpSerchResultActivity.this, R.drawable.dp_icon_sj);
		drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
		drawable4 = ContextCompat.getDrawable(DpSerchResultActivity.this, R.drawable.dp_icon_sj1);
		drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
		drawable5 = ContextCompat.getDrawable(DpSerchResultActivity.this, R.drawable.dp_icon_sj2);
		drawable5.setBounds(0, 0, drawable5.getMinimumWidth(), drawable5.getMinimumHeight());
		textview_001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (lv_select.getVisibility() == 0) {
					lv_select.setVisibility(View.GONE);
				} else

				{
					lv_select.setVisibility(View.VISIBLE);
				}
			}
		});
		textview_002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textview_002.setTextColor(
						ContextCompat.getColor(getApplicationContext(), R.color.textcolor_dp_tabhost_tabspec_selected));
				textview_001.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textcolor_dp_333));
				textview_003.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textcolor_dp_333));
				textview_001.setCompoundDrawables(null, null, drawable2, null);
				textview_003.setCompoundDrawables(null, null, drawable3, null);
				lv_select.setVisibility(View.GONE);
				sortType = "3";
				pageIndex = 1;
				orderByType = "1";
				initData();
			}
		});
		textview_003.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lv_select.setVisibility(View.GONE);
				textview_002.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textcolor_dp_333));
				textview_001.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textcolor_dp_333));
				textview_003.setTextColor(
						ContextCompat.getColor(getApplicationContext(), R.color.textcolor_dp_tabhost_tabspec_selected));
				textview_001.setCompoundDrawables(null, null, drawable2, null);
				if (flagPrice) {
					flagPrice = false;
					textview_003.setCompoundDrawables(null, null, drawable4, null);
					orderByType = "0";
				} else {
					flagPrice = true;
					textview_003.setCompoundDrawables(null, null, drawable5, null);
					orderByType = "1";
				}
				sortType = "4";
				pageIndex = 1;
				initData();
			}
		});

	}

	private void initSelectList() {
		final ArrayList<Map<String, String>> listdata = new ArrayList<Map<String, String>>();
		final String[] arrlist = { getString(R.string.dp_text_042), getString(R.string.dp_text_043),
				getString(R.string.dp_text_044) };
		final String[] arrlist1 = { "1", " 2", "5" };
		for (int i = 0; i < arrlist.length; i++) {
			Map<String, String> listitem = new HashMap<String, String>();
			listitem.put("key", arrlist[i]);
			listdata.add(listitem);
		}
		SimpleAdapter simplead = new SimpleAdapter(this, listdata, R.layout.list_item_select, new String[] { "key" },
				new int[] { R.id.text_name });
		lv_select.setAdapter(simplead);
		lv_select.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				// TODO Auto-generated method stub
				TextView textView = (TextView) arg1.findViewById(R.id.text_name);
				textView.setTextColor(getResources().getColor(R.color.textcolor_dp_tabhost_tabspec_selected));
				arg1.setBackgroundColor(getResources().getColor(R.color.common_bg));
				for (int i = 0; i < listdata.size(); i++) {
					if (i != arg2) {
						TextView textView1 = (TextView) arg0.getChildAt(i).findViewById(R.id.text_name);
						arg0.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
						textView1.setTextColor(getResources().getColor(R.color.black));
					}
				}
				sortType = arrlist1[arg2];
				orderByType = "1";
				pageIndex = 1;
				initData();
				textview_001.setText(arrlist[arg2]);
				textview_001.setTextColor(getResources().getColor(R.color.textcolor_dp_tabhost_tabspec_selected));
				lv_select.setVisibility(View.GONE);
				textview_002.setTextColor(getResources().getColor(R.color.textcolor_dp_333));
				textview_003.setTextColor(getResources().getColor(R.color.textcolor_dp_333));
				textview_003.setCompoundDrawables(null, null, drawable3, null);
				textview_001.setCompoundDrawables(null, null, drawable1, null);

			}
		});
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
		// TODO Auto-generated method stub
		pageIndex = 1;
		initData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
		// TODO Auto-generated method stub
		if (pageIndex * pageSize >= total) {
			t(getResources().getString(R.string.xlistview_no_more_data));
			sv_serch.onRefreshComplete();

		} else {
			pageIndex++;
			initData();

		}
	}
}
