
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.ArrayList;
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
import com.rs.mobile.common.view.XListView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.SmCouponAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import okhttp3.Request;

public class SmCouponActivity extends BaseActivity implements XListView.IXListViewListener {

	private XListView lv_coupon;

	private List<Map<String, Object>> arr_total = new ArrayList<>();

	private List<Map<String, Object>> arr_child = new ArrayList<>();

	private int pageIndex, pageSize, total;

	private LinearLayout close_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_coupon);
		initView();
		initData();

	}

	private void initData() {

		pageIndex = 1;
		pageSize = 20;
		getMyCoupon();

		// lv_coupon.setAdapter(new SmCouponAdapter(arr, SmCouponActivity.this,
		// "1"));
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
		lv_coupon = (XListView) findViewById(com.rs.mobile.wportal.R.id.lv_coupon);
		lv_coupon.setXListViewListener(this);
		lv_coupon.setPullLoadEnable(true);
	}

	private void getMyCoupon() {

		OkHttpHelper okHttpHelper = new OkHttpHelper(SmCouponActivity.this);
		Map<String, String> params = new HashMap<>();
		params.put("pageIndex", pageIndex + "");
		params.put("pageSize", pageSize + "");
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {
					String status = data.get(C.KEY_JSON_FM_STATUS).toString();
					if (status.equals("1")) {

						total = data.getInt("total");
						if (total - pageIndex * 20 <= 0) {
							lv_coupon.set_booter_gone();

						}
						JSONArray arr = data.getJSONArray("data");
						arr_child = CollectionUtil.jsonArrayToListMapObject(arr);
						for (int i = 0; i < arr.length(); i++) {
							arr_total.add(arr_child.get(i));
						}
						if (total == 0) {
							lv_coupon.setVisibility(View.GONE);
						} else {
							lv_coupon.setVisibility(View.VISIBLE);
						}
						lv_coupon.setAdapter(new SmCouponAdapter(arr_total, SmCouponActivity.this, "1"));

					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.GETMYCOUPON, params);

	}

	@Override
	public void onRefresh() {

		// TODO Auto-generated method stub
		try {

			pageIndex = 1;
			try {
				arr_total.clear();
				getMyCoupon();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				L.e(e);
			}

			lv_coupon.stopLoadMore();
			lv_coupon.stopRefresh();

		} catch (Exception e) {

			L.e(e);

		}
	}

	@Override
	public void onLoadMore() {

		// TODO Auto-generated method stub
		try {

			if (total - pageIndex * 20 <= 0) {
				Toast.makeText(SmCouponActivity.this, ".", Toast.LENGTH_SHORT).show();
				lv_coupon.stopLoadMore();
				lv_coupon.stopRefresh();

			} else {

				pageIndex++;
				getMyCoupon();

				lv_coupon.stopLoadMore();
				lv_coupon.stopRefresh();
			}

		} catch (Exception e) {

			L.e(e);

		}

	}
}
