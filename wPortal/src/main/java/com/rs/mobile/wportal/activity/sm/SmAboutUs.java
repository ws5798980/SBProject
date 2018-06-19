package com.rs.mobile.wportal.activity.sm;

import com.rs.mobile.common.activity.BaseActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SmAboutUs extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_aboutus);
		
		LinearLayout close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);
		
		close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
			
		});
		
		TextView textView = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_ver);
		
		textView.setText(getResources().getString(com.rs.mobile.wportal.R.string.app_name) +"v"+ getVersionCode(SmAboutUs.this) + "");
		
	}

	public String getVersionCode(Context context) {
		
		String versionCode = null ;
		
		PackageManager pm = context.getPackageManager();
		
		PackageInfo info = null;
		
		try {
			
			info = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
			
		} catch (Exception e) {
			
			e(e);
			
		}
		
		if (info != null) {
			
			versionCode = info.versionName;
			
		}
		
		return versionCode;
	}
}
