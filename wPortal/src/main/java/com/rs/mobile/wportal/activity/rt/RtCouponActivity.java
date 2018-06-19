package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.rt.CouponListAdapter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

/**
 * 
 * @author ZhaoYun
 * @date 2017-3-13
 */
public class RtCouponActivity extends BaseActivity {

	private Toolbar toolbar;
	private TextView tv_title;
	private LinearLayout iv_back;

	private ListView list_view;
	
	private TextView use_btn;
	
	private CouponListAdapter couponListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(com.rs.mobile.wportal.R.layout.activity_coupon_list);
		initToolbar(); 
		initViews();
		initDates();
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

	private void initViews() {
		
		try {
			
			tv_title.setText(getString(com.rs.mobile.wportal.R.string.rt_menu));
			
			iv_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			
			list_view = (ListView)findViewById(com.rs.mobile.wportal.R.id.list_view);
			list_view.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					// TODO Auto-generated method stub
					
					try {
					
						if (couponListAdapter.getSelectedId() == position) {
							
							couponListAdapter.setSelectedId(-1);
							
						} else {
						
							couponListAdapter.setSelectedId(position);
						
						}
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			use_btn = (TextView) findViewById(com.rs.mobile.wportal.R.id.use_btn);
			use_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
	
						int position = couponListAdapter.getSelectedId();
					
						if (position == -1) {
							
							//선택 안함
							setResult(RESULT_OK, getIntent().putExtra("item", ""));
							
						} else {
							
							JSONObject item = (JSONObject) couponListAdapter.getItem(position);
							
							setResult(RESULT_OK, getIntent().putExtra("item", item.toString()));
							
						}
						finish();
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	public void initDates() {

		try {
		
			OkHttpHelper helper = new OkHttpHelper(RtCouponActivity.this);
	
			HashMap<String, String> params = new HashMap<String, String>();
	
			params.put("", "");
	
			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
	
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
				}
	
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub
	
					try {
	
						L.d(all_data);
	
						String status = data.getString("status");
	
						if (status != null && status.equals("1")) {
							
							data = data.getJSONObject("data");
							
							JSONArray arr = data.getJSONArray("CouponList");
							
							couponListAdapter = new CouponListAdapter(RtCouponActivity.this, arr);
							
							list_view.setAdapter(couponListAdapter);
							
						}
	
					} catch (Exception e) {
	
						L.e(e);
	
					}
	
				}
	
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
	
				}
			}, Constant.BASE_URL_RT + Constant.RT_ARR_COUPON + "?memberID=" + S.getShare(RtCouponActivity.this, C.KEY_REQUEST_MEMBER_ID, "") +
					"&orderNum=" + getIntent().getStringExtra("orderNum"), params);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}

	@Override
	public void onBackPressed() {
		onBack();
	}

	public void onBack() {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}