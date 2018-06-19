package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.CollectionUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.ClassifyAdapter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class DpClassifyActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private GridView grid_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_dp_classify);
		initToolbar();
		initView();
		getClassify();

	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
		tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
		iv_back.setVisibility(View.GONE);
		tv_title.setText(getResources().getString(com.rs.mobile.wportal.R.string.sm_classify));
		setSupportActionBar(toolbar);
	}

	private void initView() {
		grid_view = (GridView) findViewById(com.rs.mobile.wportal.R.id.grid_view);
	}

	private void getClassify() {

		Map<String, String> param = new HashMap<String, String>();
		param.put("appType", "8");
		OkHttpHelper okHttpHelper = new OkHttpHelper(DpClassifyActivity.this);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {
					JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);
					final List<Map<String, String>> listdata = CollectionUtil.jsonArrayToListMap(arr);

					grid_view.setAdapter(new ClassifyAdapter(listdata, DpClassifyActivity.this));
					grid_view.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

							// TODO Auto-generated method stub
							L.d(arg2 + "====");
							Bundle bundle = new Bundle();
							bundle.putString("level1", listdata.get(arg2).get("level").toString());
							bundle.putString("level2", "");
							bundle.putString("level3", "");
							bundle.putString("flag", "level");
							PageUtil.jumpTo(DpClassifyActivity.this, DpSerchResultActivity.class, bundle);
						}
					});
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_DP1 + Constant.DP_GET_CATEGORY_LIST, param);
	}
}
