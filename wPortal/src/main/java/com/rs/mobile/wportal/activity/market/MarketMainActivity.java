package com.rs.mobile.wportal.activity.market;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MarketMainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_mk_main);
		findViewById(com.rs.mobile.wportal.R.id.mk_main_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				MarketMainActivity.this.finish();
			}
		});
		
		
		findViewById(com.rs.mobile.wportal.R.id.mk_main_qr).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				MarketMainActivity.this.startActivityForResult(new Intent(
						MarketMainActivity.this, CaptureActivity.class), 2000);
			}
		});

	}

	// 回调扫描
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == 2000) {
				String div_code = data.getStringExtra("result");
				try{
				getTickets(div_code.split("=")[1]);
				}catch(Exception e){
					T.showToast(MarketMainActivity.this, "请扫描入场的二维码");
				}
			}
		}
	}

	// 获取票据
	private void getTickets(String div_code) {
		OkHttpHelper okHttpHelper = new OkHttpHelper(MarketMainActivity.this);
		HashMap<String, String> params = new HashMap<>();
		params.put("div_code", div_code);
		params.put("token", S.getShare(this, C.KEY_JSON_TOKEN, ""));
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {

			}

			@Override
			public void onBizSuccess(String responseDescription,
					JSONObject data, String flag) {

				try {

					if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
						JSONObject jb = data.optJSONObject("ticketsInfo");
						S.setShare(MarketMainActivity.this, "tickets",
								jb.optString("tickets"));
						MarketMainActivity.this.startActivity(new Intent(
								MarketMainActivity.this,
								MarketCarActivity.class));
						MarketMainActivity.this.finish();
					}else{
						T.showToast(MarketMainActivity.this, data.optString("message"));
					}

				} catch (Exception e) {

				}
			}

			@Override
			public void onBizFailure(String responseDescription,
					JSONObject data, String flag) {

			}
		}, Constant.SM_BASE_URL + Constant.MK_GETTICKETS, params);

	}

}
