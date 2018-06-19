package com.rs.mobile.wportal.activity.dp;

import com.rs.mobile.common.activity.BaseActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DpMemberDetailActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private String textTitle;
	private String textContent;
	private TextView text_comtent;
	private TextView tx_commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_dp_member_detail);
		textTitle = getIntent().getStringExtra("textTitle");
		textContent = getIntent().getStringExtra("textContent");
		initToolbar();
		initView();

	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
		tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		;
		tv_title.setText(textTitle);
		setSupportActionBar(toolbar);
	}

	private void initView() {
		text_comtent = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_comtent);
		text_comtent.setText(textContent);
		tx_commit = (TextView) findViewById(com.rs.mobile.wportal.R.id.tx_commit);
		tx_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				t(getString(com.rs.mobile.wportal.R.string.complete));
				finish();
			}
		});
	}
}
