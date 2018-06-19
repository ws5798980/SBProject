package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.CollectionUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.view.XListView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.NewFreshAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import okhttp3.Request;

public class NewFreshActivity extends BaseActivity implements XListView.IXListViewListener {

	private String ad_id;

	private WImageView good_img;

	private XListView listView;

	private LinearLayout close_btn;

	private JSONArray array;

	private View headView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_new_fresh);

		ad_id = getIntent().getStringExtra(C.KEY_JSON_FM_AD_ID);

		initView();

		initData();

	}

	private void initView() {

		close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);

		close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		headView = LayoutInflater.from(NewFreshActivity.this).inflate(com.rs.mobile.wportal.R.layout.activity_listview_head_img, null);

		good_img = (WImageView) headView.findViewById(com.rs.mobile.wportal.R.id.good_img);

		listView = (XListView) findViewById(com.rs.mobile.wportal.R.id.listView);

		listView.addHeaderView(headView);

		listView.setPullLoadEnable(true);

		listView.setXListViewListener(this);

		listView.set_booter_gone();

	}

	private void initData() {

		getNewFresh();
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
		// listView.setAdapter(new NewFreshAdapter(list,
		// NewFreshActivity.this));

	}

	private void getNewFresh() {

		Map<String, String> params = new HashMap<String, String>();

		params.put(C.KEY_JSON_FM_AD_ID, ad_id);

		params.put("div_code", C.DIV_CODE);

		OkHttpHelper okHttpHelper = new OkHttpHelper(NewFreshActivity.this);

		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {

					JSONObject obj = data.getJSONObject(C.KEY_JSON_DATA);

					ImageUtil.drawImageFromUri(obj.getString(C.KEY_JSON_FM_BANNER_URL), good_img);

					array = obj.getJSONArray(C.KEY_JSON_LIST);

					List<Map<String, String>> listdata = CollectionUtil.jsonArrayToListMap(array);

					listView.setAdapter(new NewFreshAdapter(listdata, NewFreshActivity.this));

				} catch (Exception e) {
					// TODO: handle exception

					e(e);

				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.GETNEW_FRESHLIST, params);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

		initData();

		listView.stopLoadMore();

		listView.stopRefresh();

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

		t(getResources().getString(com.rs.mobile.wportal.R.string.xlistview_no_more_data));

		listView.stopLoadMore();

		listView.stopRefresh();

	}

}
