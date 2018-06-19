
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.SmHzListViewAdapter;
import com.rs.mobile.common.activity.BaseActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import okhttp3.Request;

public class SmSearchResultActivity extends BaseActivity {

	private EditText editText;

	private TextView text_pop, text_onsale, text_cancel;

	LinearLayout nodata;

	private String keyWord, orderByType, sortField;

	int pagesize;

	int pageindex, total;

	private PullToRefreshGridView grid_view;

	private Map<String, String> params = new HashMap<String, String>();

	private ListView list_select;

	private JSONArray arr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_search_result);

		keyWord = getIntent().getStringExtra("keyWord");
		orderByType = "1";
		sortField = C.KEY_JSON_FM_ITEM_CODE;
		pageindex = 1;
		pagesize = 20;
		initView();
		initData();

	}

	private void initView() {

		editText = (EditText) findViewById(com.rs.mobile.wportal.R.id.edit_text);
		editText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub

				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					String query = editText.getText().toString();
					keyWord = query;
					initData();

					return true;
				}

				return false;
			}
		});
		text_cancel = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_cancel);
		text_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				finish();
			}
		});
		text_pop = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_pop);
		text_pop.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
		text_pop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				if (list_select.getVisibility() == View.VISIBLE) {
					list_select.setVisibility(View.GONE);
				} else

				{
					list_select.setVisibility(View.VISIBLE);
				}
				text_onsale.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
				text_pop.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
			}
		});

		text_onsale = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_onsale);
		text_onsale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				sortField = "sold";
				initData();

				text_pop.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
				text_onsale.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
				list_select.setVisibility(View.GONE);
			}
		});
		nodata = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.nodata);
		grid_view = (PullToRefreshGridView) findViewById(com.rs.mobile.wportal.R.id.grid_view);
		grid_view.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {

				// TODO Auto-generated method stub
				pageindex = 1;
				initData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {

				// TODO Auto-generated method stub
				if (pageindex * pagesize >= total) {
					t(getResources().getString(com.rs.mobile.wportal.R.string.xlistview_no_more_data));
					grid_view.onRefreshComplete();
					return;
				}
				pageindex++;

				initData();
			}

		});
		list_select = (ListView) findViewById(com.rs.mobile.wportal.R.id.list_select);
		final ArrayList<Map<String, String>> listdata = new ArrayList<Map<String, String>>();
		final String[] arrlist = { getString(com.rs.mobile.wportal.R.string.sm_text_sort), getString(com.rs.mobile.wportal.R.string.common_text071),
				getString(com.rs.mobile.wportal.R.string.common_text072) };
		final String[] arrlist1 = { "item_code", " public_date", " price", "sold" };
		for (int i = 0; i < arrlist.length; i++) {
			Map<String, String> listitem = new HashMap<String, String>();
			listitem.put("key", arrlist[i]);
			listdata.add(listitem);
		}
		SimpleAdapter simplead = new SimpleAdapter(this, listdata, com.rs.mobile.wportal.R.layout.list_item_select, new String[] { "key" },
				new int[] { com.rs.mobile.wportal.R.id.text_name });
		list_select.setAdapter(simplead);
		list_select.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				// TODO Auto-generated method stub
				TextView textView = (TextView) arg1.findViewById(com.rs.mobile.wportal.R.id.text_name);
				textView.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
				arg1.setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.common_bg));
				for (int i = 0; i < listdata.size(); i++) {
					if (i != arg2) {
						TextView textView1 = (TextView) arg0.getChildAt(i).findViewById(com.rs.mobile.wportal.R.id.text_name);
						arg0.getChildAt(i).setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
						textView1.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
					}
				}
				sortField = arrlist1[arg2];
				initData();
				text_pop.setText(arrlist[arg2]);
				text_pop.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
				list_select.setVisibility(View.GONE);
				text_onsale.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));

			}
		});
	}

	private void initData() {

		initParams(keyWord, pageindex + "", pagesize + "");
		OkHttpHelper okHttpHelper = new OkHttpHelper(SmSearchResultActivity.this);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				grid_view.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				try {
					grid_view.onRefreshComplete();
					total = Integer.parseInt(data.get("total").toString());
					JSONArray jsArray = data.getJSONArray("data");
					if (pageindex == 1) {
						jsArray = data.getJSONArray("data");
						arr = jsArray;
					} else {

						for (int i = 0; i < jsArray.length(); i++) {

							arr.put(jsArray.get(i));

						}

					}
					if (arr.length() == 0) {
						grid_view.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					} else {
						grid_view.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}
					grid_view.setAdapter(new SmHzListViewAdapter(SmSearchResultActivity.this, arr,
							(int) (get_windows_width(SmSearchResultActivity.this) / 2.2), 0, "", "price",
							"stock_unit"));

				} catch (Exception e) {
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				grid_view.onRefreshComplete();
			}
		}, Constant.XS_BASE_URL+"StoreCate/requestSearchWordStoreList", params);
	}


	private Map<String, String> initParams(String searchword, String pg, String pageSize) {

//		params.put("keyWord", keyWord);
//		params.put("orderByType", orderByType);
//		params.put("sortField", sortField);
//		params.put("pageindex", pageindex);
//		params.put("pagesize", pagesize);
//		params.put("div_code", C.DIV_CODE);
//		params.put("appType", "6");

		params.put("lang_type", AppConfig.LANG_TYPE);
		params.put("custom_code", S.get(SmSearchResultActivity.this, C.KEY_JSON_CUSTOM_CODE));
		params.put("token", S.get(SmSearchResultActivity.this, C.KEY_JSON_TOKEN));
		params.put("searchword", searchword);
		params.put("latitude", "37.434668");
		params.put("longitude", "122.160742");
		params.put("pg", pg);
		params.put("PageSize", pageSize);

		return params;
	}
}
