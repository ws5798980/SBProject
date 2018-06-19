package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.adapter.dp.DpBrandParentAdapter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class DpBrandListActivity extends BaseActivity implements OnRefreshListener2<ListView>{
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private PullToRefreshListView sv_homepage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_dp_brandlist);
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
		tv_title.setText(getString(com.rs.mobile.wportal.R.string.dp_text_btn1));

		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		sv_homepage=(PullToRefreshListView)findViewById(com.rs.mobile.wportal.R.id.sv_homepage);
		sv_homepage.setOnRefreshListener(this);
		sv_homepage.setMode(Mode.PULL_FROM_START);
	}

	private void initData() {
		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();

		try {

			obj.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
			obj.put("token", S.getShare(getApplicationContext(), C.KEY_JSON_TOKEN, ""));
			obj.put("lang_type", "chn");
			obj.put("div", C.DIV_CODE);
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;Charset=UTF-8");
		OkHttpHelper okHttpHelper = new OkHttpHelper(DpBrandListActivity.this);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				sv_homepage.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv_homepage.onRefreshComplete();
				JSONObject obj = new JSONObject();

				try {
					obj = new JSONObject(data.toString());
					JSONArray dataArray=obj.getJSONArray("data");
					DpBrandParentAdapter adapter=new DpBrandParentAdapter(dataArray,DpBrandListActivity.this);
					sv_homepage.setAdapter(adapter);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				sv_homepage.onRefreshComplete();

			}
		}, Constant.BASE_URL_DP + Constant.DP_GETBRANDLIST, headers, obj.toString());
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		initData();
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}
}
