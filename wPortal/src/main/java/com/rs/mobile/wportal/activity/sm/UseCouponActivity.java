package com.rs.mobile.wportal.activity.sm;

import java.util.ArrayList;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.sm.UseCouponAdapter;
import com.rs.mobile.wportal.biz.Coupon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UseCouponActivity extends BaseActivity {

	private RelativeLayout relativeLayout_001, relativeLayout_002;

	private TextView textview_001, textview_002, text_use;

	private LinearLayout underLine001, underLine002;

	private PullToRefreshListView lv;

	private ArrayList<Coupon> listMergeUsedCoupons = new ArrayList<>();

	private ArrayList<Coupon> listMergeNotUsedCoupons = new ArrayList<>();

	private ArrayList<Coupon> listMergeUsedCouponsSelect = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_usecoupon);
		initView();
		initData();
	}

	private void initView() {

		lv = (PullToRefreshListView) findViewById(R.id.lv);

		findViewById(R.id.close_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		relativeLayout_001 = (RelativeLayout) findViewById(R.id.relativeLayout_001);
		relativeLayout_001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				textview_001.setTextColor(ContextCompat.getColor(UseCouponActivity.this, R.color.mainblue001));
				underLine001.setBackgroundColor(ContextCompat.getColor(UseCouponActivity.this, R.color.mainblue001));
				underLine001.setVisibility(View.VISIBLE);
				textview_002.setTextColor(ContextCompat.getColor(UseCouponActivity.this, R.color.black));
				underLine002.setVisibility(View.GONE);
				lv.setAdapter(new UseCouponAdapter(listMergeUsedCoupons, UseCouponActivity.this, "1"));
			}
		});
		relativeLayout_002 = (RelativeLayout) findViewById(R.id.relativeLayout_002);
		relativeLayout_002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				textview_002.setTextColor(ContextCompat.getColor(UseCouponActivity.this, R.color.mainblue001));
				underLine002.setBackgroundColor(ContextCompat.getColor(UseCouponActivity.this, R.color.mainblue001));
				underLine002.setVisibility(View.VISIBLE);
				textview_001.setTextColor(ContextCompat.getColor(UseCouponActivity.this, R.color.black));
				underLine001.setVisibility(View.GONE);
				lv.setAdapter(new UseCouponAdapter(listMergeNotUsedCoupons, UseCouponActivity.this, "2"));
			}
		});
		textview_001 = (TextView) findViewById(R.id.textview_001);
		textview_002 = (TextView) findViewById(R.id.textview_002);
		underLine001 = (LinearLayout) findViewById(R.id.underLine001);
		underLine002 = (LinearLayout) findViewById(R.id.underLine002);
		text_use = (TextView) findViewById(R.id.text_use);
		text_use.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				for (int i = 0; i < listMergeUsedCoupons.size(); i++) {
					if (listMergeUsedCoupons.get(i).isCpchecked()) {
						listMergeUsedCouponsSelect.add(listMergeUsedCoupons.get(i));
					}
				}
				Intent result = new Intent();

				result.putParcelableArrayListExtra("listMergeUsedCouponsSelect", listMergeUsedCouponsSelect);
				UseCouponActivity.this.setResult(Activity.RESULT_OK, result);
				finish();

			}
		});

	}

	private void initData() {

		listMergeNotUsedCoupons = getIntent().getParcelableArrayListExtra("listMergeNotUsedCoupons");
		listMergeUsedCoupons = getIntent().getParcelableArrayListExtra("listMergeUsedCoupons");

		lv.setAdapter(new UseCouponAdapter(listMergeUsedCoupons, UseCouponActivity.this, "1"));
	}
}
