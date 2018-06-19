package com.rs.mobile.wportal.activity.market;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.EncodingHandler;
import com.rs.mobile.wportal.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 出场二维码
 * 
 * @author SDY
 * 
 */
public class MarketEscActivity extends Activity {

	private ImageView mk_sec_qr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_mk_esc);

		mk_sec_qr = (ImageView) findViewById(com.rs.mobile.wportal.R.id.mk_sec_qr);

		findViewById(com.rs.mobile.wportal.R.id.mk_sec_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				findViewById(R.id.mk_sec_btn).setClickable(false);
				OkHttpHelper okHttpHelper = new OkHttpHelper(
						MarketEscActivity.this,false);
				HashMap<String, String> params = new HashMap<>();
				params.put("tickets",
						S.getShare(MarketEscActivity.this, "tickets", ""));
				okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

					@Override
					public void onNetworkError(Request request, IOException e) {

					}

					@Override
					public void onBizSuccess(String responseDescription,
							JSONObject data, String flag) {

						try {
							if (data.getInt(C.KEY_JSON_FM_STATUS) > 0) {
								if (data.optString("tickets") != null
										&& !data.optString("tickets")
												.equals(""))
									S.setShare(MarketEscActivity.this,
											"tickets",
											data.optString("tickets"));
							} else {
								S.setShare(MarketEscActivity.this, "tickets",
										"");
							}

							Intent resultIntent = new Intent();
							MarketEscActivity.this.setResult(RESULT_OK, resultIntent);
							finish();
							
						} catch (Exception e) {

						}
					}

					@Override
					public void onBizFailure(String responseDescription,
							JSONObject data, String flag) {

					}
				}, Constant.SM_BASE_URL + Constant.MK_CHECKTICKETS, params);
			}
		});
		setQr();
	}

	/**
	 * 获取二维码
	 */
	private void setQr() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(MarketEscActivity.this);
		HashMap<String, String> params = new HashMap<>();
		params.put("tickets", S.getShare(MarketEscActivity.this, "tickets", ""));
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {

			}

			@Override
			public void onBizSuccess(String responseDescription,
					JSONObject data, String flag) {

				try {
					if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
						String qrCode = data.optString("qrData");
						mk_sec_qr.setImageBitmap(EncodingHandler
								.createQRCode(
										qrCode,
										MarketEscActivity.this.getResources()
												.getDisplayMetrics().widthPixels / 3 * 2));
					}

				} catch (Exception e) {

				}
			}

			@Override
			public void onBizFailure(String responseDescription,
					JSONObject data, String flag) {

			}
		}, Constant.SM_BASE_URL + Constant.MK_GetOutSideQRCode, params);

	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return false;
	}

}
