
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.sm.ReturnGoodsListAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class SmReturnGoodsListActivity extends BaseActivity {

	private LinearLayout close_btn;

	private PullToRefreshListView listView;

	private RelativeLayout id_ad;

	private LinearLayout btn_serch;

	private LinearLayout popwindow;

	private TextView text_service, text_rule;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_returngoodslist);
		initView();
		initData();
	}

	private void initData() {

	}

	@SuppressWarnings("rawtypes")
	private void initView() {

		popwindow = (LinearLayout) findViewById(R.id.popwindow);
		btn_serch = (LinearLayout) findViewById(R.id.btn_serch);
		btn_serch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				popwindow.setVisibility(View.VISIBLE);
			}
		});
		text_rule = (TextView) findViewById(R.id.text_rule);
		text_rule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				popwindow.setVisibility(View.GONE);
				PageUtil.jumpTo(SmReturnGoodsListActivity.this, SmReturnRuleActivity.class);
			}
		});

		text_service = (TextView) findViewById(R.id.text_service);
		text_service.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				popwindow.setVisibility(View.GONE);
				t(getResources().getString(R.string.msg_ready_to_service));
			}
		});
		close_btn = (LinearLayout) findViewById(R.id.btn_back);
		close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				finish();
			}
		});
		id_ad = (RelativeLayout) findViewById(R.id.id_ad);
		listView = (PullToRefreshListView) findViewById(R.id.lv_return_goods_list);
		listView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {

				// TODO Auto-generated method stub
				getUserOrderList();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {

				// TODO Auto-generated method stub
				getUserOrderList();
			}
		});
		getUserOrderList();
	}

	public void getUserOrderList() {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ORDERSTATUS, "0");
			params.put(C.KEY_JSON_FM_PAGEINDEX, 1 + "");
			params.put(C.KEY_JSON_FM_PAGESIZE, "20");

			OkHttpHelper okHttpHelper = new OkHttpHelper(SmReturnGoodsListActivity.this);
			okHttpHelper.addSMPostRequest(new CallbackLogic() {

				private View id_ad;

				@Override
				public void onNetworkError(Request request, IOException e) {

					// TODO Auto-generated method stub
					listView.onRefreshComplete();
				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

					try {
						listView.onRefreshComplete();
						String string = data.getString("total");
						L.d(data + "");
						// TotalCount = Integer.parseInt(string);
						// if (TotalCount - currentPage * 20 < 0) {
						// ((XListView) getListView()).set_booter_gone();
						// }
						JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);
						// if (arr.length()==0) {
						// listView.setVisibility(View.GONE);
						// id_ad.setVisibility(View.VISIBLE);
						// }else {
						// listView.setVisibility(View.VISIBLE);
						// id_ad.setVisibility(View.GONE);
						//
						// }
						// for (int i = 0; i < arr.length(); i++) {
						// array.put(arr.get(i));
						// }
						listView.setAdapter(new ReturnGoodsListAdapter(arr, SmReturnGoodsListActivity.this));

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {

					// TODO Auto-generated method stub
					listView.onRefreshComplete();
				}
			}, Constant.SM_BASE_URL + Constant.GetRefundOrderItemList, params);

		} catch (Exception e) {

			L.e(e);

		}
	}

}
