package com.rs.mobile.wportal.activity.sm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
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
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.pay.PayResult;
import com.rs.mobile.common.util.EncodingHandler;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.PaymentUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.sm.OrderDetailAdapter;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.wportal.sm.SMUtil;
import com.rs.mobile.wportal.view.SecurityPasswordEditText;
import com.rs.mobile.wportal.view.TimerTextView;
import com.rs.mobile.wportal.view.TimerTextView.OnTimerOverListener;

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

public class OrderDetailPayActivity extends BaseActivity {

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

	private ImageView selector_pay_way_yucheng, selector_pay_way_union_pay, selector_pay_way_alipay, selector_pay_way_wechat;

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

					msginfo = getString(R.string.common_text005);

					SMUtil.smConfirmStatus(order_code, OrderDetailPayActivity.this);

				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {

						msginfo = getString(R.string.common_text006);

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误

						msginfo = getString(R.string.common_text007);
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
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sm_order_detail_pay);
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

				msg = getString(R.string.common_text005);

				SMUtil.smConfirmStatus(order_code, OrderDetailPayActivity.this);

			} else if (str.equalsIgnoreCase("cancel")) {

				msg = getString(R.string.common_text008);

			} else if (str.equalsIgnoreCase("fail")) {

				msg = getString(R.string.common_text007);

			} else {

				msg = getString(R.string.common_text007);

			}

			t(msg);

		}
	}

	private void initData(String orderCode) {

		getOrderDetail(orderCode);

	}

	private void initView() {

		try {

			pay_line = (LinearLayout) findViewById(R.id.pay_line);

			pay_type_yucheng = (RelativeLayout) findViewById(R.id.pay_type_yucheng);

			pay_type_yucheng.setOnClickListener(payClickListener);

			pay_type_union_pay = (RelativeLayout) findViewById(R.id.pay_type_union_pay);

			pay_type_union_pay.setOnClickListener(payClickListener);

			pay_type_alipay = (RelativeLayout) findViewById(R.id.pay_type_alipay);

			pay_type_alipay.setOnClickListener(payClickListener);

			pay_type_wechat = (RelativeLayout) findViewById(R.id.pay_type_wechat);

			pay_type_wechat.setOnClickListener(payClickListener);

			selector_pay_way_yucheng = (ImageView) findViewById(R.id.selector_pay_way_yucheng);

			selector_pay_way_union_pay = (ImageView) findViewById(R.id.selector_pay_way_union_pay);

			selector_pay_way_alipay = (ImageView) findViewById(R.id.selector_pay_way_alipay);

			selector_pay_way_wechat = (ImageView) findViewById(R.id.selector_pay_way_wechat);

			timer_textview = (TimerTextView) findViewById(R.id.time_text_view);

			cance_order = (TextView) findViewById(R.id.cance_order);

			close_btn = (LinearLayout) findViewById(R.id.close_btn);

			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			buttom_line = (LinearLayout) findViewById(R.id.buttom_line);

			text_receiver = (TextView) findViewById(R.id.text_receiver);

			text_phone = (TextView) findViewById(R.id.text_phone);

			text_address = (TextView) findViewById(R.id.text_address);

			text_order_code = (TextView) findViewById(R.id.text_order_code);

			text_call = (TextView) findViewById(R.id.text_call);

			// text_payment = (TextView) findViewById(R.id.text_payment);

			text_send_time = (TextView) findViewById(R.id.text_send_time);

			text_account = (TextView) findViewById(R.id.text_account);

			order_money = (TextView) findViewById(R.id.order_money);

			good_coupon = (TextView) findViewById(R.id.good_coupon);

			good_freight = (TextView) findViewById(R.id.good_freight);

			good_point = (TextView) findViewById(R.id.good_point);

			text_pay_money = (TextView) findViewById(R.id.text_paymoney);

			myCodePicture = (ImageView) findViewById(R.id.img_my_code_picture);

			myCodePicture2 = (ImageView) findViewById(R.id.img_my_code_picture2);

			myCodePicture.setImageBitmap(EncodingHandler.CreateOneDCode(this, order_code));
			myCodePicture2.setImageBitmap(
					EncodingHandler.createQRCode(order_code, get_windows_width(getApplicationContext()) / 3 * 2));
			tv_qr = (TextView) findViewById(R.id.tv_qr);

			tv_qr.setText(order_code);

			list_view = (ListView) findViewById(R.id.list_view);

			list_view.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub

					try {

						JSONObject item = (JSONObject) arg0.getItemAtPosition(arg2);

						Bundle bundle = new Bundle();

						bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item.getString("item_code"));

						bundle.putString(C.KEY_DIV_CODE, item.getString(C.KEY_DIV_CODE));
						PageUtil.jumpTo(OrderDetailPayActivity.this, SmGoodsDetailActivity.class, bundle);

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

			params.put("appType", "6");
			OkHttpHelper okHttpHelper = new OkHttpHelper(OrderDetailPayActivity.this);

			okHttpHelper.addSMPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
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

						String syncPaymentStatus = obj.get("syncPaymentStatus").toString();

						if (diff > 0 && !syncPaymentStatus.equals("1")) {

							pay_line.setVisibility(View.VISIBLE);

							buttom_line.setVisibility(View.VISIBLE);

							timer_textview.setTimes(diff);

							timer_textview.setDetail(getString(R.string.common_text018));

							if (!timer_textview.isRun()) {

								timer_textview.start(new OnTimerOverListener() {

									@Override
									public void onOver() {
										// TODO Auto-generated method stub

										t(getString(R.string.common_text019));

										finish();

									}
								});
							}

							timer_textview.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									creatOrder();
//									switch (payType) {
//									case 0:
//
//										try {
//
//											PaymentUtil.showKeyBoard(OrderDetailPayActivity.this, order_code,
//													orderPrice, obj.get("point").toString(), realPrice, "0",
//													new OnPaymentResultListener() {
//
//														@Override
//														public void onResult(JSONObject data) {
//															// TODO
//															// Auto-generated
//															// method stub
//
//															String status;
//
//															try {
//																status = data.get("status").toString();
//
//																if (status.equals("1")) {
//
//																	JSONObject data1 = data.getJSONObject("data");
//
//																	String order_no1 = data1.get("order_no").toString();
//
//																	t(getString(R.string.common_text005));
//
//																	SMUtil.smConfirmStatus(order_code,
//																			OrderDetailPayActivity.this);
//
//																} else {
//
//																	T.showToast(OrderDetailPayActivity.this,
//																			data.get("msg").toString());
//
//																	// TODO
//																	// Auto-generated
//																	// method
//																	// stub
//																	if (status.equals("1101")) { // wrong
//																									// pw
//
//																		showDialogTip(data.get("msg").toString(),
//																				getString(R.string.common_text020));
//
//																	} else if (status.equals("1711")) { // not
//																										// enough
//																										// money
//
//																		showDialogTip(data.get("msg").toString(),
//																				getString(R.string.common_text021));
//
//																	}
//
//																}
//															} catch (Exception e) {
//																// TODO
//																// Auto-generated
//																// catch block
//
//																e(e);
//
//															}
//
//														}
//
//														@Override
//														public void onFail(String msg) {
//															// TODO
//															// Auto-generated
//															// method stub
//
//														}
//													});
//
//										} catch (Exception e) {
//
//											L.e(e);
//
//										}
//										break;
//									case 1:
//										PaymentUtil.wechatPayMethod(order_code, realPrice, OrderDetailPayActivity.this);
//										break;
//									case 2:
//										//PaymentUtil.alipayMethod(order_code, realPrice, OrderDetailPayActivity.this, MyOrderActivity.class);
//										PaymentUtil.alipayMethod(order_code, realPrice,  OrderDetailPayActivity.this, SDK_PAY_FLAG,
//												alipayHandler);
//										break;
//									case 3:
//										PaymentUtil.unionPayMethod(order_code, realPrice, OrderDetailPayActivity.this, MyOrderActivity.class);
//										break;
//									default:
//										break;
//
//									}
								}
							});

						} else {

							pay_line.setVisibility(View.GONE);

							timer_textview.setVisibility(View.GONE);

							cance_order.setText("订单已失效");

						}

						text_order_code.setText(getResources().getString(R.string.order_code) + " : "
								+ obj.get(C.KEY_JSON_FM_ORDERCODE).toString());

						text_pay_money
								.setText(getString(R.string.common_text016) + getResources().getString(R.string.rmb)
										+ obj.get(C.KEY_JSON_FM_REAL_PRICE).toString());

						text_account.setText(getString(R.string.common_text017) + getResources().getString(R.string.rmb)
								+ obj.get(C.KEY_JSON_FM_ORDERPRICE).toString());

						order_money.setText(
								getResources().getString(R.string.rmb) + obj.get(C.KEY_JSON_FM_ORDERPRICE).toString());

						good_coupon
								.setText(getResources().getString(R.string.rmb) + obj.get("coupons_amount").toString());

						good_freight.setText(
								getResources().getString(R.string.rmb) + obj.get(C.KEY_JSON_FM_FREIGHT).toString());

						good_point.setText(obj.get("point").toString());

						// text_payment.setText(obj.get(C.KEY_JSON_FM_PAYMENT).toString());

						text_send_time.setText(obj.get(C.KEY_JSON_FM_DISTRIBUTION).toString());

						JSONArray arr = obj.getJSONArray(C.KEY_JSON_FM_GOODSLIST);

						final String item_code = obj.get(C.KEY_JSON_FM_ORDERCODE).toString();

						list_view.setAdapter(new OrderDetailAdapter(arr, OrderDetailPayActivity.this,
								obj.get(C.KEY_JSON_FM_ORDERCODE).toString(), "1"));

						setListViewHeight(list_view);

					} catch (Exception e) {

						L.e(e);

					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.GET_ORDER_DETAIL, params);

		} catch (Exception e) {

			L.e(e);

		}
	}

	OnClickListener payClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			int id = v.getId();

			if (id == R.id.pay_type_yucheng) {

				payType = 0;

				selector_pay_way_yucheng.setImageResource(R.drawable.icon_selected_s);

				selector_pay_way_union_pay.setImageResource(R.drawable.icon_selected_n);

				selector_pay_way_alipay.setImageResource(R.drawable.icon_selected_n);

			} else if (id == R.id.pay_type_union_pay) {

				payType = 3;

				selector_pay_way_yucheng.setImageResource(R.drawable.icon_selected_n);

				selector_pay_way_union_pay.setImageResource(R.drawable.icon_selected_s);

				selector_pay_way_alipay.setImageResource(R.drawable.icon_selected_n);

				selector_pay_way_wechat.setImageResource(R.drawable.icon_selected_n);

			} else if (id == R.id.pay_type_alipay) {

				payType = 2;

				selector_pay_way_yucheng.setImageResource(R.drawable.icon_selected_n);

				selector_pay_way_union_pay.setImageResource(R.drawable.icon_selected_n);

				selector_pay_way_alipay.setImageResource(R.drawable.icon_selected_s);

				selector_pay_way_wechat.setImageResource(R.drawable.icon_selected_n);

			} else if (id == R.id.pay_type_wechat) {

				payType = 1;

				selector_pay_way_yucheng.setImageResource(R.drawable.icon_selected_n);

				selector_pay_way_union_pay.setImageResource(R.drawable.icon_selected_n);

				selector_pay_way_alipay.setImageResource(R.drawable.icon_selected_n);

				selector_pay_way_alipay.setImageResource(R.drawable.icon_selected_s);

			}

		}
	};

	private void confirmStatus(String order_num) {

		HashMap<String, String> params = new HashMap<>();

		params.put("order_num", order_num);

		OkHttpHelper okHttpHelper = new OkHttpHelper(OrderDetailPayActivity.this);

		okHttpHelper.addSMPostRequest(new CallbackLogic() {

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
		}, Constant.SM_BASE_URL + Constant.PAYMENT_STATUSSYNC, params);

	}

	private void showDialogTip(String tips1, String tips2) {

		D.showDialog(OrderDetailPayActivity.this, -1,
				OrderDetailPayActivity.this.getResources().getString(R.string.sm_text_tips), tips1, tips2,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						D.alertDialog.dismiss();
						
						
						UtilClear.IntentToLongLiao(OrderDetailPayActivity.this,
								"cn.ycapp.im.ui.mywallet.MyWalletActivity","");
						finish();

					}
				}, OrderDetailPayActivity.this.getResources().getString(R.string.cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						D.alertDialog.dismiss();

						PageUtil.jumpToWithFlag(OrderDetailPayActivity.this, MyOrderActivity.class);

					}
				});

	}


	private void creatOrder() {

//		try {
//
//			HashMap<String, String> params = new HashMap<String, String>();
//
//			JSONObject valueLocation = new JSONObject();
//
//			valueLocation.put("locationId", id);
//			//valueLocation.put("locationId", "489");
//
//			params.put("validateInfo", valueLocation.toString());
//			params.put("guid", guid);
//			if (canPoint && check_box.isChecked()) {
//				params.put("point_amount", canMaxPointAmount);
//			} else {
//				params.put("point_amount", "0");
//			}
//			String useCouponsIds = "";
//			for (int i = 0; i < listMergeUsedCouponsSelect.size(); i++) {
//				useCouponsIds = useCouponsIds + listMergeUsedCouponsSelect.get(i).getId() + ",";
//			}
//			if (listMergeUsedCouponsSelect.size() > 0) {
//				useCouponsIds = useCouponsIds.substring(0, useCouponsIds.length() - 1);
//			}
//
//			params.put("useCouponsIds", useCouponsIds);
//			OkHttpHelper okHttpHelper = new OkHttpHelper(OrderDetailPayActivity.this);
//
//			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {
//
//				@Override
//				public void onNetworkError(Request request, IOException e) {
//					String bbbb = request.toString();
//				}
//
//				@Override
//				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
//					try {
//						L.d("creatOrder" + data.toString());
//						if (data.getString(C.KEY_JSON_FM_STATUS).equals("1")) {
//
//							String orderprice = data.getString("amount");
//
//							String orderid = data.getString("order_code");
//
//							String pointAmount = data.get("pointAmount").toString();
//
//							String real_amount = data.get("real_amount").toString();
//
//							// keyboardUtil = new
//							// KeyboardUtil(SmConfirmActivity.this,
//							// SmConfirmActivity.this,
//							// pay_password_view, orderid, orderprice,
//							// canUsePoint, real_amount, "0");
//							//
//							// keyboardUtil.showKeyboard();
//							switch (payType) {
//								case 0:
//									PaymentUtil.showKeyBoard(OrderDetailPayActivity.this, orderid, orderprice, pointAmount,
//											real_amount, "0", new OnPaymentResultListener() {
//
//												@Override
//												public void onResult(JSONObject data) {
//													// stub
//													String status;
//													try {
//														status = data.get("status").toString();
//
//														if (status.equals("1")) {
//															JSONObject data1 = data.getJSONObject("data");
//															String order_no1 = data1.get("order_no").toString();
//															t(getString(R.string.common_text005));
//
//															SMUtil.smConfirmStatus(order_no1, OrderDetailPayActivity.this);
//
//														} else {
//
//															T.showToast(OrderDetailPayActivity.this, data.get("msg").toString());
//
//															// TODO Auto-generated
//															// method
//															// stub
//															if (status.equals("1101")) { // wrong
//																// pw
//																showDialogTip(data.get("msg").toString(),
//																		getString(R.string.common_text020));
//															} else if (status.equals("1711")) { // not
//																// enough
//																// money
//																showDialogTip(data.get("msg").toString(),
//																		getString(R.string.common_text021));
//															} else { //
//																finish();
//																PageUtil.jumpToWithFlag(OrderDetailPayActivity.this,
//																		MyOrderActivity.class);
//															}
//														}
//													} catch (JSONException e) {
//														// TODO Auto-generated catch
//														// block
//														e.printStackTrace();
//													}
//
//												}
//
//												@Override
//												public void onFail(String msg) {
//													// TODO Auto-generated method
//													// stub
//
//													PageUtil.jumpToWithFlag(OrderDetailPayActivity.this, MyOrderActivity.class);
//													finish();
//
//												}
//											});
//									break;
//
//								case 1:
//									PaymentUtil.wechatPayMethod(orderid, real_amount, OrderDetailPayActivity.this);
//									break;
//								case 2:
//									PaymentUtil.alipayMethod(orderid, real_amount, OrderDetailPayActivity.this, SDK_PAY_FLAG, alipayHandler);
//									break;
//								case 3:
//									PaymentUtil.unionPayMethod(orderid, real_amount, OrderDetailPayActivity.this, MyOrderActivity.class);
//									break;
//								default:
//									break;
//							}
//
//						} else {
//
//							t(data.getString("message"));
//
//						}
//
//					} catch (Exception e) {
//
//						L.e(e);
//
//					}
//				}
//
//				@Override
//				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
//					String aaa = data.toString();
//
//				}
//			}, Constant.SM_BASE_URL + Constant.CREATE_ORDER, params);
//
//		} catch (Exception e) {
//
//			L.e(e);
//
//		}

	}
}