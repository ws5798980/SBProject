package com.rs.mobile.wportal.activity.dp;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.dp.DpReturnProcessAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import okhttp3.Request;

public class DpReturnProcessActivity extends BaseActivity {

	private String item_code;

	private PullToRefreshListView lv;

	private String refundNo;

	private LinearLayout close_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sm_returnprocess);

		refundNo = getIntent().getStringExtra("refundNo");

		item_code = getIntent().getStringExtra(C.KEY_JSON_FM_ITEM_CODE);

		initView();

		initData();

	}

	private void initData() {

		HashMap<String, String> parms = new HashMap<String, String>();

		parms.put("refund_no", refundNo);

		OkHttpHelper okHttpHelper = new OkHttpHelper(DpReturnProcessActivity.this);

		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

				lv.onRefreshComplete();

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				lv.onRefreshComplete();

				try {

					if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("1")) {

						JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);

						lv.setAdapter(new DpReturnProcessAdapter(arr, DpReturnProcessActivity.this));

					}

				} catch (Exception e) {
					// TODO: handle exception

					e(e);

				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				lv.onRefreshComplete();

			}

		}, Constant.BASE_URL_DP1 + Constant.GET_GOODSRETURN_DETAIL, parms);

	}

	private void initView() {

		close_btn = (LinearLayout) findViewById(R.id.close_btn);

		close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}

		});

		lv = (PullToRefreshListView) findViewById(R.id.lv_process);

		lv.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub

				initData();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub

			}

		});

	}

}