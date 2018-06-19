package com.rs.mobile.wportal.activity.dp;

import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.R;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DpCultureActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private TextView tv_001;
	private TextView tv_002;
	private TextView tv_003;
	private TextView tv_004;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dp_culture);
		initToolbar();
		initView();

	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		iv_back = (LinearLayout) findViewById(R.id.iv_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_title.setText(getString(R.string.dp_text_035));
		setSupportActionBar(toolbar);
	}

	private void initView() {
		tv_001 = (TextView) findViewById(R.id.tv_001);
		tv_001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(DpCultureActivity.this, DpCultureDetailActivity.class);
			}
		});
		tv_002 = (TextView) findViewById(R.id.tv_002);
		tv_002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		tv_003 = (TextView) findViewById(R.id.tv_003);
		tv_003.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		tv_004 = (TextView) findViewById(R.id.tv_004);
		tv_004.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(DpCultureActivity.this, DpCultureDetailActivity.class);
			}
		});
	}
}
