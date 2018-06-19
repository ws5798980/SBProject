
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.adapter.sm.SmHzListViewAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class SmClassifyResultActivity extends BaseActivity {

	private EditText editText;

	private TextView text_pop, text_onsale, text_cancel;

	private String category_code, orderByType, sortField;

	private int pageindex, pagesize, total;

	private PullToRefreshGridView grid_view;

	private JSONArray arr = new JSONArray();

	private Map<String, String> params = new HashMap<String, String>();

	private TextView textview_001;

	private TextView textview_002;

	private TextView textview_003;

	private TextView textview_004;

	private LinearLayout underLine001;

	private LinearLayout underLine002;

	private LinearLayout underLine003;

	private LinearLayout underLine004;

	private RelativeLayout relativeLayout_001;

	private RelativeLayout relativeLayout_002;

	private RelativeLayout relativeLayout_003;

	private RelativeLayout relativeLayout_004;

	LinearLayout close_btn;

	private List<TextView> tabTextList = new ArrayList<TextView>();

	private List<LinearLayout> tabBtnList = new ArrayList<LinearLayout>();

	private String level;

	private SmHzListViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_classify_result);

		level = getIntent().getStringExtra("level");
		sortField = "";
		pageindex = 1;
		pagesize = 10;
		initView();

		draw(0);
	}

	@SuppressWarnings("unchecked")
	private void initView() {

		close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);
		close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				finish();
			}
		});
		textview_001 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_001);
		textview_002 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_002);
		textview_003 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_003);
		textview_004 = (TextView) findViewById(com.rs.mobile.wportal.R.id.textview_004);

		underLine001 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine001);
		underLine002 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine002);
		underLine003 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine003);
		underLine004 = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.underLine004);

		relativeLayout_001 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_001);
		relativeLayout_001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				draw(0);
			}
		});

		relativeLayout_002 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_002);
		relativeLayout_002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				draw(1);
			}
		});

		relativeLayout_003 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_003);
		relativeLayout_003.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				draw(2);
			}
		});

		relativeLayout_004 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.relativeLayout_004);
		relativeLayout_004.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				draw(3);
			}
		});

		tabTextList.add(textview_001);
		tabTextList.add(textview_002);
		tabTextList.add(textview_003);
		tabTextList.add(textview_004);

		tabBtnList.add(underLine001);
		tabBtnList.add(underLine002);
		tabBtnList.add(underLine003);
		tabBtnList.add(underLine004);

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
	}

	private void initData() {

		initParams(level, sortField, pageindex, pagesize);
		hideNoData();
		OkHttpHelper okHttpHelper = new OkHttpHelper(SmClassifyResultActivity.this);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {

				// TODO Auto-generated method stub
				grid_view.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {
					runOnUiThread(new Runnable() {
						public void run() {

							try {
								if (data.get(C.KEY_JSON_STATUS).toString().equals("1")) {
									total = Integer.parseInt(data.get("total").toString());
									if (total == 0) {
										showNoData(com.rs.mobile.wportal.R.drawable.icon_nogoods_search, "没有相关商品", null);

									}
									JSONArray jsArray = data.getJSONArray("data");
									if (pageindex == 1) {
										arr = data.getJSONArray("data");
										adapter = new SmHzListViewAdapter(SmClassifyResultActivity.this, arr,
												(int) (get_windows_width(SmClassifyResultActivity.this) / 2.2), 0,
												"data", "item_price", "stock_unit");
										grid_view.setAdapter(adapter);
									} else {

										for (int i = 0; i < jsArray.length(); i++) {

											arr.put(jsArray.get(i));

										}
										adapter.notifyDataSetChanged();
									}

								} else {
									t(data.get("message").toString());
								}
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				grid_view.onRefreshComplete();
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				grid_view.onRefreshComplete();
			}
		}, Constant.SM_BASE_URL + Constant.GETGOODSLIST_CATEGROY, params);
	}

	/**
	 * 
	 * @param category_code
	 * @param orderByType
	 * @param sortField
	 * @param pageindex
	 * @param pagesize
	 * @return
	 */
	private Map<String, String> initParams(String level, String sortField, int pageindex, int pagesize) {

		params.put("level", level);
		params.put("sortField", sortField);
		params.put("pageindex", pageindex + "");
		params.put("pagesize", pagesize + "");
		params.put("div_code", C.DIV_CODE);
		return params;
	}

	private void draw(int index) {

		try {
			String[] sortFieldList = { "hot", "public_date", "sold", "price" };
			setTabBarCorlor(index);
			sortField = sortFieldList[index];
			pageindex = 1;
			arr.put("[]");
			initData();

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void setTabBarCorlor(int i) {

		try {

			for (int j = 0; j < tabTextList.size(); j++) {
				if (i == j) {
					tabTextList.get(j).setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
					tabBtnList.get(j).setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue001));
				} else {
					tabTextList.get(j).setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.black));
					tabBtnList.get(j).setBackgroundColor(getResources().getColor(com.rs.mobile.wportal.R.color.white));
				}

			}

		} catch (Exception e) {

			L.e(e);

		}

	}
}
