package com.rs.mobile.common.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;

public class TempActivity extends BaseActivity {

	public RelativeLayout bodyLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.common.R.layout.activity_temp);
		
		bodyLayout = (RelativeLayout)findViewById(com.rs.mobile.common.R.id.body_layout);

		int code = getIntent().getIntExtra("code", 0);
		
		if (code == 0) {
			
			bodyLayout.setBackgroundResource(com.rs.mobile.common.R.drawable.shop_img);
			
		} else if (code == 1) {
			
			bodyLayout.setBackgroundResource(com.rs.mobile.common.R.drawable.rest_img);
			
		} else if (code == 3) {
			
			bodyLayout.setBackgroundResource(com.rs.mobile.common.R.drawable.hotel_img);
			
		} else if (code == 5) {
			
			bodyLayout.setBackgroundResource(com.rs.mobile.common.R.drawable.parking_img);
			
		} else if (code == 7) {
			
			bodyLayout.setBackgroundResource(com.rs.mobile.common.R.drawable.kr_img);
			
		}
		
		
	}
	
}
