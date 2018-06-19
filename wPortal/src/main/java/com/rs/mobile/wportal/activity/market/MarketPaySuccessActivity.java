package com.rs.mobile.wportal.activity.market;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.market.fragment.MarketMainFragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MarketPaySuccessActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mk_success);
		String amount = getIntent().getStringExtra("amount");
		TextView mk_pay_amount = (TextView) findViewById(R.id.mk_pay_amount);
		mk_pay_amount.setText(amount);
		
		findViewById(R.id.mk_pay_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						MarketPaySuccessActivity.this.startActivity(new Intent(
								MarketPaySuccessActivity.this,
								MarketMainFragmentActivity.class));
						MarketPaySuccessActivity.this.finish();
					}
				});
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return false;
	}

}
