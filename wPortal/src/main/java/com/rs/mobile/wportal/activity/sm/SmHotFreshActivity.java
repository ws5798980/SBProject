
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.ObservableScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.sm.SmHzListViewAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import okhttp3.Request;

public class SmHotFreshActivity extends BaseActivity implements OnRefreshListener2<ObservableScrollView> {

	private WImageView good_img;

	private PullToRefreshScrollView scroll_view;

	private String ad_id;

	private View headView;

	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_hotfresh);
		ad_id = getIntent().getStringExtra(C.KEY_JSON_FM_AD_ID);
		initView();
		initData();
	}

	private void initView() {

		LinearLayout close_btn = (LinearLayout) findViewById(R.id.close_btn);
		close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				finish();
			}
		});

		scroll_view = (PullToRefreshScrollView) findViewById(R.id.scroll_view);
		scroll_view.setOnRefreshListener(this);

		good_img = (WImageView) findViewById(R.id.good_img);
		gridView = (GridView) findViewById(R.id.gridView);

		// gridView.setOnRefreshListener(new OnRefreshListener2() {
		//
		// @Override
		// public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		//
		// // TODO Auto-generated method stub
		// initData();
		//
		// }
		//
		// @Override
		// public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		//
		// // TODO Auto-generated method stub
		// t(getResources().getString(R.string.xlistview_no_more_data));
		// gridView.onRefreshComplete();
		// }
		//
		// });
	}

	private void initData() {

		getHotFresh();
	}

	private void getHotFresh() {

		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put(C.KEY_JSON_FM_AD_ID, ad_id);
		params.put("div_code", C.DIV_CODE);
		OkHttpHelper okHttpHelper = new OkHttpHelper(SmHotFreshActivity.this);
		okHttpHelper.addSMPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {

				// TODO Auto-generated method stub
				scroll_view.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {
					JSONObject obj = data.getJSONObject(C.KEY_JSON_DATA);
					ImageUtil.drawImageFromUri(obj.getString(C.KEY_JSON_FM_BANNER_URL), good_img);
					JSONArray array = obj.getJSONArray(C.KEY_JSON_LIST);
					// List<Map<String, String>> listdata =
					// CollectionUtil.jsonArrayToListMap(array);
					gridView.setAdapter(new SmHzListViewAdapter(SmHotFreshActivity.this, array,
							(int) (get_windows_width(SmHotFreshActivity.this) / 2.2), 0, C.KEY_JSON_LIST,
							C.KEY_JSON_FM_ITEM_PRICE, C.KEY_JSON_FM_STOCK_UNIT));
					UiUtil.setGridViewHeight(gridView, 2);
					scroll_view.onRefreshComplete();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				scroll_view.onRefreshComplete();
			}
		}, Constant.SM_BASE_URL + Constant.GETHOT_FRESHLIST, params);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		// TODO Auto-generated method stub
		getHotFresh();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		// TODO Auto-generated method stub
		getHotFresh();
	}

}
