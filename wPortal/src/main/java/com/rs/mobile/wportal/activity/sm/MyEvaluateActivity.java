package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.L;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.XListView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.ElvuateAdapter;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.UiUtil;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class MyEvaluateActivity extends BaseActivity implements XListView.IXListViewListener {
	private Toolbar toolbar;

	private LinearLayout iv_back;

	private TextView tv_title;

	private TextView textView;

	private String commentStatus, has_imgs;

	private List<Map<String, Object>> listdata;

	private String item_code;

	private int pageIndex;

	private int pageSize;

	private int TotalCount;

	private JSONArray array;

	private XListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_my_comments);
		initToolbar();
		initView();
		initData();
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
		tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
		setSupportActionBar(toolbar);
		tv_title.setText(getString(com.rs.mobile.wportal.R.string.my_evaluate));

		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initView() {
		lv = (XListView) findViewById(com.rs.mobile.wportal.R.id.lv);
		lv.setXListViewListener(this);

		lv.setPullLoadEnable(true);
		pageIndex = 1;
		pageSize = 20;

		listdata = new ArrayList<Map<String, Object>>();
		try {
			array = new JSONArray("[]");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initData() {
		HashMap<String, String> param = new HashMap<String, String>();

		param.put("pageIndex", pageIndex + "");
		param.put("pageSize", pageSize + "");
		OkHttpHelper okHttpHelper = new OkHttpHelper(MyEvaluateActivity.this);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				try {
					JSONObject jsonObject = data;

					String string = data.getString("total");

					TotalCount = Integer.parseInt(string);
					if (TotalCount - pageIndex * pageSize <= 0) {
						lv.set_booter_gone();
					}
					JSONArray jsonArray = jsonObject.getJSONArray("data");

					for (int i = 0; i < jsonArray.length(); i++) {

						array.put(jsonArray.get(i));
					}

					// listdata=CollectionUtil.jsonArrayToListMapObject(jsonArray);
					ElvuateAdapter adapter = new ElvuateAdapter(array, MyEvaluateActivity.this,
							UiUtil.get_windows_width(getApplicationContext()) / 3, true);
					lv.setAdapter(adapter);

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.GetUserOfComment, param);
	}

	@Override
	public void onRefresh() {

		// TODO Auto-generated method stub
		try {

			pageIndex = 1;
			try {
				array = new JSONArray("[]");
				initData();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				L.e(e);
			}

			lv.stopLoadMore();
			lv.stopRefresh();
		} catch (Exception e) {

			L.e(e);

		}
	}

	@Override
	public void onLoadMore() {

		// TODO Auto-generated method stub
		try {

			if (TotalCount - pageIndex * 20 <= 0) {
				//t(getString(com.rs.mobile.wportal.R.string.common_text068));

				lv.stopLoadMore();
				lv.stopRefresh();

				return;
			} else {
				pageIndex++;
				initData();

				lv.stopLoadMore();
				lv.stopRefresh();
			}

		} catch (Exception e) {

			L.e(e);

		}
	}

}
