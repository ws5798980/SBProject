package com.rs.mobile.wportal.activity.market;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.rs.mobile.common.D;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.common.util.PaymentUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.market.view.PasswordView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class MarketPayActivity extends Activity {

	private PasswordView pw_view;
	protected int activityCloseEnterAnimation;
	protected int activityCloseExitAnimation;
	private TextView mk_toptime;

	@Override
	public void finish() {
		super.finish();
		MarketPayActivity.this.overridePendingTransition(0, 0);
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mk_pay);
		TypedArray activityStyle = getTheme().obtainStyledAttributes(
				new int[] { android.R.attr.windowAnimationStyle });
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(
				windowAnimationStyleResId,
				new int[] { android.R.attr.activityCloseEnterAnimation,
						android.R.attr.activityCloseExitAnimation });
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		mk_toptime = (TextView) findViewById(R.id.mk_toptime);
		pw_view = (PasswordView) findViewById(R.id.pw_view);
		mk_toptime.setText("¥" + getIntent().getStringExtra("amount"));
		findViewById(R.id.mk_topback).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MarketPayActivity.this.finish();
				MarketPayActivity.this.overridePendingTransition(0,
						R.anim.activity_close);
			}
		});
		pw_view.setOnFinishInput(new PasswordView.OnPasswordInputFinish() {
			@Override
			public void inputFinish() {
				// Toast.makeText(MarketPayActivity.this,
				// pw_view.getStrPassword(), Toast.LENGTH_SHORT).show();
			}
		});

		findViewById(R.id.mk_pay_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Log.d("TAG","pw ===" + pw_view.getStrPassword());
				if (!pw_view.idEmpty()) {
					setOrder();
				} else {
					T.showToast(MarketPayActivity.this, "请输入支付密码");
				}
			}
		});
	}

	private void setOrder() {
						PaymentUtil.submitOrder(MarketPayActivity.this,
								 getIntent().getStringExtra("order_code"),  getIntent().getStringExtra("amount"), "0",
								pw_view.getStrPassword(), getIntent().getStringExtra("amount"), "0",
								new OnPaymentResultListener() {

									@Override
									public void onResult(JSONObject data) {

										String status;

										try {
											status = data.get("status")
													.toString();

											if (status.equals("1")) {
												payNotification() ;
												Intent intent = new Intent(
														MarketPayActivity.this,
														MarketPaySuccessActivity.class);
												intent.putExtra("amount",
														getIntent().getStringExtra("amount"));
												startActivity(intent);
											} else {
												T.showToast(
														MarketPayActivity.this,
														data.get("msg")
																.toString());

												if (status.equals("1101")) { // wrong

													showDialogTip(
															data.get("msg")
																	.toString(),
															getString(R.string.common_text020));

												} else if (status
														.equals("1711")) {

													showDialogTip(
															data.get("msg")
																	.toString(),
															getString(R.string.common_text021));

												}

											}
										} catch (Exception e) {

										}
									}

									@Override
									public void onFail(String msg) {

									}
								});
					
			}


	//支付通知
	private void payNotification(){
		 	OkHttpHelper okHttpHelper = new OkHttpHelper(this);
			HashMap<String, String> params = new HashMap<>(); 
			params.put("tickets", S.getShare(this, "tickets", ""));
			okHttpHelper.addSMPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				}
			}, Constant.SM_BASE_URL + Constant.UpdateRepeatPayStatus, params);
		
		
	}
	
	
	private void showDialogTip(String tips1, String tips2) {

		D.showDialog(MarketPayActivity.this, -1, MarketPayActivity.this
				.getResources().getString(R.string.sm_text_tips), tips1, tips2,
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						D.alertDialog.dismiss();
						UtilClear.IntentToLongLiao(MarketPayActivity.this,
								"cn.ycapp.im.ui.mywallet.MyWalletActivity","");
						finish();

					}
				},
				MarketPayActivity.this.getResources()
						.getString(R.string.cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {
						D.alertDialog.dismiss();
						finish();

					}
				});

	}
}
