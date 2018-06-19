
package com.rs.mobile.wportal.activity.dp;

import com.rs.mobile.common.activity.BaseActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DpReturnRuleActivity extends BaseActivity {

	private Toolbar toolbar;

	private TextView tv_title;

	private LinearLayout iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_returnrule);
		initToolbar();

	}

	private void initToolbar() {

		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		tv_title = (TextView) toolbar.findViewById(com.rs.mobile.wportal.R.id.tv_title);
		iv_back = (LinearLayout) toolbar.findViewById(com.rs.mobile.wportal.R.id.iv_back);
		setSupportActionBar(toolbar);
		tv_title.setText(getResources().getString(com.rs.mobile.wportal.R.string.sm_text_returnrule));
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
