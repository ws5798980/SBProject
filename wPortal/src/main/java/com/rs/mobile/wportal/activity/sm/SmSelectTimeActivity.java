
package com.rs.mobile.wportal.activity.sm;

import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.DateString;
import com.rs.mobile.wportal.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SmSelectTimeActivity extends BaseActivity {

	private CheckBox checkBox_001, checkBox_002, checkBox_003, checkBox_004, checkBox_005, checkBox_006;

	private TextView textTime_001, textTime_002, textTime_003, text_week_001, text_week_002, text_week_003;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_selecttime);
		initView();
	}

	private void initView() {

		try {

			LinearLayout close_btn = (LinearLayout) findViewById(R.id.close_btn);
			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					finish();
				}
			});

			checkBox_001 = (CheckBox) findViewById(R.id.checkBox_001);
			checkBox_001.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.putExtra("time", checkBox_001.getText().toString());
					setResult(RESULT_OK, i);
					finish();
				}
			});

			checkBox_002 = (CheckBox) findViewById(R.id.checkBox_002);
			checkBox_002.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.putExtra("time", checkBox_002.getText().toString());
					setResult(RESULT_OK, i);
					finish();
				}
			});

			checkBox_003 = (CheckBox) findViewById(R.id.checkBox_003);
			checkBox_003.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.putExtra("time", checkBox_003.getText().toString());
					setResult(RESULT_OK, i);
					finish();
				}
			});

			checkBox_004 = (CheckBox) findViewById(R.id.checkBox_004);
			checkBox_004.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.putExtra("time", checkBox_004.getText().toString());
					setResult(RESULT_OK, i);
					finish();
				}
			});

			checkBox_005 = (CheckBox) findViewById(R.id.checkBox_005);
			checkBox_005.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.putExtra("time", checkBox_005.getText().toString());
					setResult(RESULT_OK, i);
					finish();
				}
			});

			checkBox_006 = (CheckBox) findViewById(R.id.checkBox_006);
			checkBox_006.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.putExtra("time", checkBox_006.getText().toString());
					setResult(RESULT_OK, i);
					finish();
				}
			});

			textTime_001 = (TextView) findViewById(R.id.textTime_001);
			textTime_001.setText(DateString.StringDateTime(0));

			textTime_002 = (TextView) findViewById(R.id.textTime_002);
			textTime_002.setText(DateString.StringDateTime(1));

			textTime_003 = (TextView) findViewById(R.id.textTime_003);
			textTime_003.setText(DateString.StringDateTime(2));

			text_week_001 = (TextView) findViewById(R.id.text_week_001);
			text_week_001.setText(DateString.StringWeek(0));

			text_week_002 = (TextView) findViewById(R.id.text_week_002);
			text_week_002.setText(DateString.StringWeek(1));

			text_week_003 = (TextView) findViewById(R.id.text_week_003);
			text_week_003.setText(DateString.StringWeek(2));

		} catch (Exception e) {

			L.e(e);

		}

	}
}
