package com.rs.mobile.wportal.activity.dp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.pay.PayResult;
import com.rs.mobile.common.util.EncodingHandler;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.PaymentUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.dp.DpOrderDetailAdapter;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.wportal.sm.SMUtil;
import com.rs.mobile.wportal.view.SecurityPasswordEditText;
import com.rs.mobile.wportal.view.TimerTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class DpOrderDetailPayActivity extends BaseActivity {

	private TextView text_receiver, text_phone, text_address, text_lgtime, text_lgstate, text_order_code, text_call,
			text_payment, text_send_time, text_order_money, text_freight, text_pay_money, cance_order;

	private TextView text_account;

	private TimerTextView timer_textview;

	private ListView list_view;

	private LinearLayout close_btn, buttom_line;

	private ArrayList<ShoppingCart> list;

	private RelativeLayout pay_type_yucheng, pay_type_union_pay, pay_type_alipay;

	private String order_code;

	private TextView order_money;

	private TextView good_coupon;

	private TextView good_freight;

	private TextView good_point;

	private ImageView selector_pay_way_yucheng, selector_pay_way_union_pay, selector_pay_way_alipay;

	private int payType = 0;

	private static final int SDK_PAY_FLAG = 1;

	private SecurityPasswordEditText editText_Pwd;

	private String payPwd;

	private Handler alipayHandler = new Handler() {

		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG:

				@SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);

				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();

				String msginfo;

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {

					msginfo = getString(com.rs.mobile.wportal.R.string.common_text005);

					SMUtil.dpConfirmStatus(order_code, DpOrderDetailPayActivity.this);

				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {

						msginfo = getString(com.rs.mobile.wportal.R.string.common_text006);

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误

						msginfo = getString(com.rs.mobile.wportal.R.string.common_text007);
					}
				}

				t(msginfo);

				break;

			default:
				break;
			}
		};
	};
	private LinearLayout pay_line;

	private RelativeLayout pay_type_wechat;

	private ImageView myCodePicture;

	private ImageView myCodePicture2;

	private TextView tv_qr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_order_detail_pay);
		list = getIntent().getParcelableArrayListExtra("goods");

		order_code = getIntent().getStringExtra(C.KEY_JSON_FM_ORDERCODE);

		initView();

		initData(order_code);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			if (data == null) {
				return;
			}

			String msg = "";
			/*
			 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
			 */
			String str = data.getExtras().getString("pay_result");
			if (str.equalsIgnoreCase("success")) {

				msg = getString(com.rs.mobile.wportal.R.string.common_text005);
				;

				SMUtil.dpConfirmStatus(order_code, DpOrderDetailPayActivity.this);

			} else if (str.equalsIgnoreCase("cancel")) {

				msg = getString(com.rs.mobile.wportal.R.string.common_text008);

			} else if (str.equalsIgnoreCase("fail")) {

				msg = getString(com.rs.mobile.wportal.R.string.common_text007);

			} else {

				msg = getString(com.rs.mobile.wportal.R.string.common_text007);

			}

			t(msg);

		}
	}

	private void initData(String orderCode) {

		getOrderDetail(orderCode);

	}

	private void initView() {

		try {
			myCodePicture = (ImageView) findViewById(com.rs.mobile.wportal.R.id.img_my_code_picture);

			myCodePicture2 = (ImageView) findViewById(com.rs.mobile.wportal.R.id.img_my_code_picture2);

			myCodePicture.setImageBitmap(EncodingHandler.CreateOneDCode(this, order_code));
			myCodePicture2.setImageBitmap(
					EncodingHandler.createQRCode(order_code, get_windows_width(getApplicationContext()) / 3 * 2));

			tv_qr = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_qr);

			tv_qr.setText(order_code);

			pay_line = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.pay_line);

			pay_type_yucheng = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.pay_type_yucheng);

			pay_type_yucheng.setOnClickListener(payClickListener);

			pay_type_union_pay = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.pay_type_union_pay);

			pay_type_union_pay.setOnClickListener(payClickListener);

			pay_type_alipay = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.pay_type_alipay);

			pay_type_alipay.setOnClickListener(payClickListener);

			pay_type_wechat = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.pay_type_wechat);

			pay_type_wechat.setOnClickListener(payClickListener);

			selector_pay_way_yucheng = (ImageView) findViewById(com.rs.mobile.wportal.R.id.selector_pay_way_yucheng);

			selector_pay_way_union_pay = (ImageView) findViewById(com.rs.mobile.wportal.R.id.selector_pay_way_union_pay);

			selector_pay_way_alipay = (ImageView) findViewById(com.rs.mobile.wportal.R.id.selector_pay_way_alipay);

			timer_textview = (TimerTextView) findViewById(com.rs.mobile.wportal.R.id.time_text_view);

			cance_order = (TextView) findViewById(com.rs.mobile.wportal.R.id.cance_order);

			close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);

			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			buttom_line = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.buttom_line);

			text_receiver = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_receiver);

			text_phone = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_phone);

			text_address = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_address);

			text_order_code = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_order_code);

			text_call = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_call);

			// text_payment = (TextView) findViewById(R.id.text_payment);

			text_send_time = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_send_time);

			text_account = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_account);

			order_money = (TextView) findViewById(com.rs.mobile.wportal.R.id.order_money);

			good_coupon = (TextView) findViewById(com.rs.mobile.wportal.R.id.good_coupon);

			good_freight = (TextView) findViewById(com.rs.mobile.wportal.R.id.good_freight);

			good_point = (TextView) findViewById(com.rs.mobile.wportal.R.id.good_point);

			text_pay_money = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_paymoney);

			list_view = (ListView) findViewById(com.rs.mobile.wportal.R.id.list_view);

			list_view.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub

					try {

						JSONObject item = (JSONObject) arg0.getItemAtPosition(arg2);

						Bundle bundle = new Bundle();

						bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item.getString("item_code"));
						bundle.putString(C.KEY_DIV_CODE, item.getString(C.KEY_DIV_CODE));
						PageUtil.jumpTo(DpOrderDetailPayActivity.this, DpGoodsDetailActivity.class, bundle);

					} catch (Exception e) {

						L.e(e);

					}
				}
			});

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void getOrderDetail(final String order_code) {

		try {

			Map<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_JSON_FM_ORDERCODE, order_code);

			params.put("appType", "8");
			OkHttpHelper okHttpHelper = new OkHttpHelper(DpOrderDetailPayActivity.this);

			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					try {

						final JSONObject obj = data.getJSONObject(C.KEY_JSON_DATA);

						JSONObject jsonObjectAddress = obj.getJSONObject(C.KEY_JSON_FM_ADDRESS);

						text_receiver.setText(jsonObjectAddress.get(C.KEY_JSON_FM_USER_NAME).toString());

						text_phone.setText(jsonObjectAddress.get(C.KEY_JSON_FM_MOBILE).toString());

						text_address.setText(jsonObjectAddress.get(C.KEY_JSON_FM_USER_TEXT).toString());

						// text_lgtime.setText(jsonlg.get(C.KEY_JSON_FM_EXP_Date).toString());
						//
						// text_lgstate.setText(jsonlg.get(C.KEY_JSON_FM_EXP_LOCATION).toString());

						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						String stringTime = obj.getString(C.KEY_JSON_FM_INVALID_DATE);

						String serverTime = data.getString("serverTime");

						Date now = dateFormat.parse(serverTime);

						Date target = null;

						target = dateFormat.parse(stringTime);

						long diff = target.getTime() - now.getTime();

						final String orderPrice = obj.get(C.KEY_JSON_FM_ORDERPRICE).toString();

						final String realPrice = obj.get(C.KEY_JSON_FM_REAL_PRICE).toString();

						final String syncPaymentStatus = obj.get("syncPaymentStatus").toString();

						if (diff > 0 && !syncPaymentStatus.equals("1")) {

							pay_line.setVisibility(View.VISIBLE);

							buttom_line.setVisibility(View.VISIBLE);

							timer_textview.setTimes(diff);

							timer_textview.setDetail(getString(com.rs.mobile.wportal.R.string.common_text018));

							if (!timer_textview.isRun()) {

								timer_textview.start(new TimerTextView.OnTimerOverListener() {

									@Override
									public void onOver() {
										// TODO Auto-generated method stub

										t(getString(com.rs.mobile.wportal.R.string.common_text019));

										finish();

									}
								});
							}

							timer_textview.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									switch (payType) {
									case 0:
										try {
											PaymentUtil.showKeyBoard(DpOrderDetailPayActivity.this, order_code,
													orderPrice, obj.get("point").toString(), realPrice, "0",
													new OnPaymentResultListener() {

														@Override
														public void onResult(JSONObject data) {
															// TODO
															// Auto-generated
															// method stub

															String status;

															try {
																status = data.get("status").toString();

																if (status.equals("1")) {

																	JSONObject data1 = data.getJSONObject("data");

																	String order_no1 = data1.get("order_no").toString();

																	t(getString(com.rs.mobile.wportal.R.string.common_text005));

																	SMUtil.dpConfirmStatus(order_code,
																			DpOrderDetailPayActivity.this);

																} else {

																	T.showToast(DpOrderDetailPayActivity.this,
																			data.get("msg").toString());

																	// TODO
																	// Auto-generated
																	// method
																	// stub
																	if (status.equals("1101")) { // wrong
																									// pw

																		showDialogTip(data.get("msg").toString(),
																				getString(com.rs.mobile.wportal.R.string.common_text020));

																	} else if (status.equals("1711")) { // not
																										// enough
																										// money

																		showDialogTip(data.get("msg").toString(),
																				getString(com.rs.mobile.wportal.R.string.common_text021));

																	}

																}
															} catch (Exception e) {
																// TODO
																// Auto-generated
																// catch block

																e(e);

															}

														}

														@Override
														public void onFail(String msg) {
															// TODO
															// Auto-generated
															// method stub

														}
													});
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										break;
									case 1:
										// unionPayMethod(order_code,
										// realPrice);
										PaymentUtil.unionPayMethod(order_code, realPrice,
												DpOrderDetailPayActivity.this, DpMyOrderActivity.class);
										break;
									case 2:
						//				PaymentUtil.alipayMethod(order_code, realPrice, DpOrderDetailPayActivity.this, DpMyOrderActivity.class);
										break;
									default:
										break;

									}
								}
							});

						} else {

							pay_line.setVisibility(View.GONE);

							timer_textview.setVisibility(View.GONE);

							cance_order.setText(getString(com.rs.mobile.wportal.R.string.common_text022));

						}

						text_order_code.setText(getResources().getString(com.rs.mobile.wportal.R.string.order_code) + " : "
								+ obj.get(C.KEY_JSON_FM_ORDERCODE).toString());

						text_pay_money
								.setText(getString(com.rs.mobile.wportal.R.string.common_text016) + getResources().getString(com.rs.mobile.wportal.R.string.rmb)
										+ obj.get(C.KEY_JSON_FM_REAL_PRICE).toString());

						text_account.setText(getString(com.rs.mobile.wportal.R.string.common_text017) + getResources().getString(com.rs.mobile.wportal.R.string.rmb)
								+ obj.get(C.KEY_JSON_FM_ORDERPRICE).toString());

						order_money.setText(
								getResources().getString(com.rs.mobile.wportal.R.string.rmb) + obj.get(C.KEY_JSON_FM_ORDERPRICE).toString());

						good_coupon
								.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + obj.get("coupons_amount").toString());

						good_freight.setText(
								getResources().getString(com.rs.mobile.wportal.R.string.rmb) + obj.get(C.KEY_JSON_FM_FREIGHT).toString());

						good_point.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + obj.get("point").toString());

						// text_payment.setText(obj.get(C.KEY_JSON_FM_PAYMENT).toString());

						text_send_time.setText(obj.get(C.KEY_JSON_FM_DISTRIBUTION).toString());

						JSONArray arr = obj.getJSONArray(C.KEY_JSON_FM_GOODSLIST);

						final String item_code = obj.get(C.KEY_JSON_FM_ORDERCODE).toString();

						list_view.setAdapter(new DpOrderDetailAdapter(arr, DpOrderDetailPayActivity.this,
								obj.get(C.KEY_JSON_FM_ORDERCODE).toString(), "1"));

						list_view.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
								// TODO Auto-generated method stub

								try {

									Bundle bundle = new Bundle();

									bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item_code);

									PageUtil.jumpTo(DpOrderDetailPayActivity.this, DpGoodsDetailActivity.class, bundle);

								} catch (Exception e) {

									L.e(e);

								}

							}
						});

						setListViewHeight(list_view);

					} catch (Exception e) {

						L.e(e);

					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_DP1 + Constant.GET_ORDER_DETAIL, params);

		} catch (Exception e) {

			L.e(e);

		}
	}

	OnClickListener payClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			int id = v.getId();

			if (id == com.rs.mobile.wportal.R.id.pay_type_yucheng) {

				payType = 0;

				selector_pay_way_yucheng.setImageResource(com.rs.mobile.wportal.R.drawable.icon_selected_s);

				selector_pay_way_union_pay.setImageResource(com.rs.mobile.wportal.R.drawable.icon_selected_n);

				selector_pay_way_alipay.setImageResource(com.rs.mobile.wportal.R.drawable.icon_selected_n);

			} else if (id == com.rs.mobile.wportal.R.id.pay_type_union_pay) {

				payType = 1;

				selector_pay_way_yucheng.setImageResource(com.rs.mobile.wportal.R.drawable.icon_selected_n);

				selector_pay_way_union_pay.setImageResource(com.rs.mobile.wportal.R.drawable.icon_selected_s);

				selector_pay_way_alipay.setImageResource(com.rs.mobile.wportal.R.drawable.icon_selected_n);

			} else if (id == com.rs.mobile.wportal.R.id.pay_type_alipay) {

				payType = 2;

				selector_pay_way_yucheng.setImageResource(com.rs.mobile.wportal.R.drawable.icon_selected_n);

				selector_pay_way_union_pay.setImageResource(com.rs.mobile.wportal.R.drawable.icon_selected_n);

				selector_pay_way_alipay.setImageResource(com.rs.mobile.wportal.R.drawable.icon_selected_s);

			} else if (id == com.rs.mobile.wportal.R.id.pay_type_wechat) {
				t(getString(com.rs.mobile.wportal.R.string.ready));
			}

		}
	};

	private void confirmStatus(String order_num) {

		HashMap<String, String> params = new HashMap<>();

		params.put("order_num", order_num);

		OkHttpHelper okHttpHelper = new OkHttpHelper(DpOrderDetailPayActivity.this);

		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_DP1 + Constant.PAYMENT_STATUSSYNC, params);

	}

	private void showDialogTip(String tips1, String tips2) {

		D.showDialog(DpOrderDetailPayActivity.this, -1,
				DpOrderDetailPayActivity.this.getResources().getString(com.rs.mobile.wportal.R.string.sm_text_tips), tips1, tips2,
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						D.alertDialog.dismiss();
						UtilClear.IntentToLongLiao(DpOrderDetailPayActivity.this,  "cn.ycapp.im.ui.mywallet.MyWalletActivity","");
						finish();

					}
				}, DpOrderDetailPayActivity.this.getResources().getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						D.alertDialog.dismiss();

						finish();

					}
				});

	}

}