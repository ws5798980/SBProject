package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.ObservableScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.TodayFreshAdapter;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.CollectionUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.view.MyGridView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import okhttp3.Request;

public class SmTodayFreshActivity extends BaseActivity implements OnRefreshListener2<ObservableScrollView> {

	private WImageView good_img;

	private MyGridView gridView;

	private String ad_id;

	private PullToRefreshScrollView scroll_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_today_fresh);
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

		gridView = (MyGridView) findViewById(R.id.gridView);
	}

	private void initData() {

		getTodayFresh();
		// List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		// for (int i = 0; i < 6; i++) {
		// Map<String, String> map=new HashMap<String, String>();
		// map.put(C.KEY_JSON_FM_ITEM_IMAGE_URL,
		// "http://192.168.2.19/Unipartner/yechung/13266719905_636189115083400259.png");
		// map.put(C.KEY_JSON_FM_ITEM_NAME, "ddddd");
		// map.put(C.KEY_JSON_FM_ITEM_PRICE, "88");
		// map.put(C.KEY_JSON_FM_STOCK_UNIT, "44");
		// list.add(map);
		// }
		// gridView.setAdapter(new TodayFreshAdapter(list,
		// SmTodayFreshActivity.this));
	}

	private void getTodayFresh() {

		Map<String, String> params = new HashMap<String, String>();
		params.put(C.KEY_JSON_FM_AD_ID, ad_id);
		params.put("div_code", C.DIV_CODE);
		OkHttpHelper okHttpHelper = new OkHttpHelper(SmTodayFreshActivity.this);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				scroll_view.onRefreshComplete();
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {

					scroll_view.onRefreshComplete();
					JSONObject obj = data.getJSONObject(C.KEY_JSON_DATA);
					ImageUtil.drawImageFromUri(obj.getString(C.KEY_JSON_FM_BANNER_URL), good_img);
					JSONArray array = obj.getJSONArray(C.KEY_JSON_LIST);
					final List<Map<String, String>> listdata = CollectionUtil.jsonArrayToListMap(array);
					gridView.setAdapter(new TodayFreshAdapter(listdata, SmTodayFreshActivity.this));
					// UiUtil.setGridViewHeight(gridView, 3);

					gridView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

							// TODO Auto-generated method stub
							Bundle bundle = new Bundle();
							bundle.putString(C.KEY_JSON_FM_ITEM_CODE,
									listdata.get(arg2).get(C.KEY_JSON_FM_ITEM_CODE).toString());
							bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
							PageUtil.jumpTo(SmTodayFreshActivity.this, SmGoodsDetailActivity.class, bundle);
						}
					});
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				scroll_view.onRefreshComplete();
			}
		}, Constant.SM_BASE_URL + Constant.GETTODAY_FRESHLISt, params);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		// TODO Auto-generated method stub
		getTodayFresh();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
		// TODO Auto-generated method stub
		getTodayFresh();
	}
}
