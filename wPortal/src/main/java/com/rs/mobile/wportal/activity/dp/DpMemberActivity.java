package com.rs.mobile.wportal.activity.dp;

import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.activity.BaseActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DpMemberActivity extends BaseActivity {

	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private RelativeLayout rela001;
	private RelativeLayout rela002;
	private RelativeLayout rela003;
	private RelativeLayout rela004;
	private RelativeLayout rela005;
	private String[] textTitle = { getApplicationContext().getString(com.rs.mobile.wportal.R.string.dp_text_021),
			getApplicationContext().getString(com.rs.mobile.wportal.R.string.dp_text_022),
			getApplicationContext().getString(com.rs.mobile.wportal.R.string.dp_text_023),
			getApplicationContext().getString(com.rs.mobile.wportal.R.string.dp_text_024),
			getApplicationContext().getString(com.rs.mobile.wportal.R.string.dp_text_025) };
	private String[] textContent = { getApplicationContext().getString(com.rs.mobile.wportal.R.string.dp_text_036),
			getApplicationContext().getString(com.rs.mobile.wportal.R.string.dp_text_037),
			getApplicationContext().getString(com.rs.mobile.wportal.R.string.dp_text_038),
			getApplicationContext().getString(com.rs.mobile.wportal.R.string.dp_text_039),
			getApplicationContext().getString(com.rs.mobile.wportal.R.string.dp_text_040) };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_dp_member);
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
		tv_title.setText(getString(com.rs.mobile.wportal.R.string.dp_text_041));
		setSupportActionBar(toolbar);
	}

	private void initView() {

		rela001 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela001);
		rela001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("textTitle", textTitle[0]);
				bundle.putString("textContent", textContent[0]);
				PageUtil.jumpTo(DpMemberActivity.this, DpMemberDetailActivity.class, bundle);
			}
		});
		rela002 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela002);
		rela002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("textTitle", textTitle[1]);
				bundle.putString("textContent", textContent[1]);
				PageUtil.jumpTo(DpMemberActivity.this, DpMemberDetailActivity.class, bundle);
			}
		});
		rela003 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela003);
		rela003.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("textTitle", textTitle[2]);
				bundle.putString("textContent", textContent[2]);
				PageUtil.jumpTo(DpMemberActivity.this, DpMemberDetailActivity.class, bundle);
			}
		});
		rela004 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela004);
		rela004.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("textTitle", textTitle[3]);
				bundle.putString("textContent", textContent[3]);
				PageUtil.jumpTo(DpMemberActivity.this, DpMemberDetailActivity.class, bundle);
			}
		});
		rela005 = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela005);
		rela005.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("textTitle", textTitle[4]);
				bundle.putString("textContent", textContent[4]);
				PageUtil.jumpTo(DpMemberActivity.this, DpMemberDetailActivity.class, bundle);
			}
		});
	}
}
