package com.rs.mobile.wportal.activity.ht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.astuetz.PagerSlidingTabStrip;
import com.rs.mobile.wportal.fragment.ht.HotelEvaluateListFragment;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.ht.HtMyFragmentPageAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class HtEvaluateListActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private PagerSlidingTabStrip tabs;
	private ViewPager viewPager;
	private String[] s = new String[5];
	private String HotelInfoID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ht_evaluate_list);
		HotelInfoID = getIntent().getStringExtra("HotelInfoID");
		initToolbar();
		initView();
		initData();
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(R.id.toolbar);
			iv_back = (LinearLayout) findViewById(R.id.iv_back);
			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			tv_title = (TextView) findViewById(R.id.tv_title);
			tv_title.setText(getString(R.string.ht_text_evaluate_list));
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		tabs.setDividerColor(0x00000000);
		tabs.setDividerPadding(StringUtil.dip2px(getApplicationContext(), 0));
		tabs.setTabPaddingLeftRight(StringUtil.dip2px(getApplicationContext(), 0));
		tabs.setIndicatorColorResource(R.color.textcolor_ht_tabhost_tabspec_selected);
		tabs.setIndicatorHeight(StringUtil.dip2px(getApplicationContext(), 2));
		tabs.setSelectedTextColor(
				ContextCompat.getColor(getApplicationContext(), R.color.textcolor_ht_tabhost_tabspec_selected));

	}

	private void initData() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(HtEvaluateListActivity.this);
		HashMap<String, String> params = new HashMap<>();
		params.put("HotelInfoID", HotelInfoID);
		params.put("OpIndex", "0");
		params.put("iPageIndex", "1");
		params.put("iPageSize", "10");
		okHttpHelper.addPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					if (data.get("status").toString().equals("1")) {
						JSONObject obj = data.getJSONObject("data");
						JSONArray HotelRated = obj.getJSONArray("HotelRated");
						JSONObject objRated = HotelRated.getJSONObject(0);
						s[0] = getResources().getString(R.string.ht_text_type0) + "(" + objRated.getInt("total_count")
								+ ")";
						s[1] = getString(R.string.ht_text_083) + "(" + objRated.getInt("ratedforPraise_count") + ")";
						s[2] = getString(R.string.ht_text_084) + "(" + objRated.getInt("ratedforzhong_count") + ")";
						s[3] = getString(R.string.ht_text_085) + "(" + objRated.getInt("ratedforLow_count") + ")";
						s[4] = getString(R.string.ht_text_086) + "(" + objRated.getInt("ratedforImg_count") + ")";
						List<Fragment> flist = new ArrayList<>();
						HotelEvaluateListFragment f1 = new HotelEvaluateListFragment(HotelInfoID, "0");
						HotelEvaluateListFragment f2 = new HotelEvaluateListFragment(HotelInfoID, "2");
						HotelEvaluateListFragment f3 = new HotelEvaluateListFragment(HotelInfoID, "3");
						HotelEvaluateListFragment f4 = new HotelEvaluateListFragment(HotelInfoID, "4");
						HotelEvaluateListFragment f5 = new HotelEvaluateListFragment(HotelInfoID, "1");
						flist.add(f1);
						flist.add(f2);
						flist.add(f3);
						flist.add(f4);
						flist.add(f5);
						HtMyFragmentPageAdapter adapter = new HtMyFragmentPageAdapter(getSupportFragmentManager(),
								flist, s);
						viewPager.setAdapter(adapter);

						tabs.setViewPager(viewPager);
					} else {
						t(data.getString("msg"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_HT + Constant.HT_GET_RATELIST, params);

	}
}
