package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.sm.OrderDetailAdapter;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.util.EncodingHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import okhttp3.Request;

public class OrderDetailActivity extends BaseActivity {

	private TextView text_receiver, text_phone, text_address, text_lgtime, text_lgstate, text_order_code, text_call,
			text_payment, text_send_time, text_pay_money, text_confirm, text_back_payment, text_check_logistics,
			text_account;

	private TextView order_money, good_coupon, good_freight, good_point, delete_btn;

	private LinearLayout btn_area;

	private RelativeLayout rela_lg;

	private ListView list_view;

	private LinearLayout close_btn;

	private ArrayList<ShoppingCart> list;

	// private String item_code;

	private String order_code;

	private ImageView myCodePicture;

	private ImageView myCodePicture2;

	private TextView tv_qr;

	boolean deletable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_order_detail);

		list = getIntent().getParcelableArrayListExtra("goods");

		order_code = getIntent().getStringExtra(C.KEY_JSON_FM_ORDERCODE);

		initView();

	}

	@Override
	protected void onResume() {
		super.onResume();

		initData(order_code);

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
			order_money = (TextView) findViewById(com.rs.mobile.wportal.R.id.order_money);

			good_coupon = (TextView) findViewById(com.rs.mobile.wportal.R.id.good_coupon);

			good_freight = (TextView) findViewById(com.rs.mobile.wportal.R.id.good_freight);

			good_point = (TextView) findViewById(com.rs.mobile.wportal.R.id.good_point);

			close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);

			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					finish();

				}
			});
			delete_btn = (TextView) findViewById(com.rs.mobile.wportal.R.id.delete_btn);
			delete_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) { // 수령대기

					D.showDialog(OrderDetailActivity.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
							getString(com.rs.mobile.wportal.R.string.common_text002), getString(com.rs.mobile.wportal.R.string.common_text003),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									// method stub
									D.alertDialog.dismiss();
									deleteOrder(order_code);
								}
							}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

								@Override
								public void onClick(View v) {
									// method stub
									D.alertDialog.dismiss();
								}
							});

					// remindOrder(order_code);

				}
			});
			btn_area = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.btn_area);

			rela_lg = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.rela_lg);

			rela_lg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					PageUtil.jumpTo(OrderDetailActivity.this, SmLogisticsActivity.class);
				}
			});

			text_receiver = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_receiver);

			text_phone = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_phone);

			text_address = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_address);

			text_lgtime = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_lgtime);

			text_lgstate = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_lgstate);

			text_order_code = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_order_code);

			text_call = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_call);

			text_payment = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_payment);

			text_send_time = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_send_time);

			// 결제 상태
			final String flag = getIntent().getStringExtra("flag");

			// ?
			final String flag_ass = getIntent().getStringExtra("flag_ass");

			text_pay_money = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_paymoney);

			list_view = (ListView) findViewById(com.rs.mobile.wportal.R.id.list_view);

			list_view.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

					try {

						JSONObject item = (JSONObject) arg0.getItemAtPosition(arg2);

						Bundle bundle = new Bundle();

						bundle.putString(C.KEY_JSON_FM_ITEM_CODE, item.getString("item_code"));
						bundle.putString(C.KEY_DIV_CODE, item.getString(C.KEY_DIV_CODE));
						PageUtil.jumpTo(OrderDetailActivity.this, SmGoodsDetailActivity.class, bundle);

					} catch (Exception e) {

						L.e(e);

					}
				}
			});
			text_confirm = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_confirm);

			text_account = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_account);

			text_confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (flag.equals("1")) { // 결제대기

						Bundle bundle = new Bundle();

						bundle.putString(C.KEY_JSON_FM_ORDERCODE, order_code);

						PageUtil.jumpTo(OrderDetailActivity.this, OrderDetailPayActivity.class, bundle);

					} else if (flag.equals("3")) { // 수령대기

						// TODO Auto-generated method stub
						D.showDialog(OrderDetailActivity.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
								getString(com.rs.mobile.wportal.R.string.common_text013), getString(com.rs.mobile.wportal.R.string.common_text003),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated
										// method stub
										D.alertDialog.dismiss();
										confirmGetGoods(order_code, "false");
									}
								}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated
										// method stub
										D.alertDialog.dismiss();
									}
								});

						// remindOrder(order_code);

					} else if (flag.equals("5")) { // 평가대기

						Bundle bundle = new Bundle();

						bundle.putString(C.KEY_JSON_FM_ORDERCODE, order_code);

						bundle.putParcelableArrayList("goods", list);

						Intent i = new Intent(OrderDetailActivity.this, EvaluateListActivity.class);

						i.putExtras(bundle);

						startActivityForResult(i, 1000);

					}

				}
			});
			text_back_payment = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_back_payment);

			text_back_payment.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Bundle bundle = new Bundle();

					bundle.putParcelableArrayList("goods", list);

					bundle.putString(C.KEY_JSON_FM_ORDERCODE, order_code);

					PageUtil.jumpTo(OrderDetailActivity.this, ReturnGoodActivity.class, bundle);

				}
			});
			text_check_logistics = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_check_logistics);

			text_check_logistics.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					PageUtil.jumpTo(OrderDetailActivity.this, SmLogisticsActivity.class);

				}
			});

			if (flag.equals("1")) { // 결제대기

				btn_area.setVisibility(View.VISIBLE);

				text_confirm.setVisibility(View.VISIBLE);

				text_back_payment.setVisibility(View.GONE);

				text_check_logistics.setVisibility(View.GONE);

			} else if (flag.equals("2")) { // 결제 완료된 주문

				btn_area.setVisibility(View.VISIBLE);

				text_confirm.setVisibility(View.GONE);

				text_back_payment.setVisibility(View.GONE);

				text_check_logistics.setVisibility(View.VISIBLE);
				text_check_logistics.setText(getString(com.rs.mobile.wportal.R.string.common_text014));
				text_check_logistics.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						D.showDialog(OrderDetailActivity.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
								getString(com.rs.mobile.wportal.R.string.common_text015), getString(com.rs.mobile.wportal.R.string.common_text003),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated
										// method stub
										D.alertDialog.dismiss();
										cancelOrder(order_code);
									}
								}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated
										// method stub
										D.alertDialog.dismiss();
									}
								});

					}
				});

			} else if (flag.equals("3")) { // 수령대기

				btn_area.setVisibility(View.VISIBLE);

				text_confirm.setVisibility(View.VISIBLE);

				text_back_payment.setVisibility(View.GONE);

				text_check_logistics.setVisibility(View.VISIBLE);

			} else if (flag.equals("4")) { // 취소된 주문

				btn_area.setVisibility(View.VISIBLE);

			} else if (flag.equals("5")) { // 평가대기

				if (flag_ass.equals("1")) { // 평가 대기

					text_confirm.setVisibility(View.VISIBLE);

					text_confirm.setText(getResources().getString(com.rs.mobile.wportal.R.string.sm_text_evaluate));

					text_back_payment.setVisibility(View.GONE);

					text_check_logistics.setVisibility(View.GONE);

				} else { // 평가 완료

					delete_btn.setVisibility(View.VISIBLE);

				}

			} else {

				btn_area.setVisibility(View.GONE);

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 오더 정보 가져오기
	 * 
	 * @param order_code
	 */
	private void getOrderDetail(String order_code) {

		try {

			Map<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_JSON_FM_ORDERCODE, order_code);

			params.put("appType", "6");

			OkHttpHelper okHttpHelper = new OkHttpHelper(OrderDetailActivity.this);

			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					try {

						JSONObject obj = data.getJSONObject(C.KEY_JSON_DATA);

						JSONObject jsonObjectAddress = obj.getJSONObject(C.KEY_JSON_FM_ADDRESS);

						JSONObject jsonlg = obj.getJSONObject(C.KEY_JSON_FM_EXPSTATUS);

						String ONLINE_ORDER_STATUS = obj.get("ONLINE_ORDER_STATUS").toString();

						deletable = obj.getBoolean("deletable");
						if (deletable) {
							delete_btn.setVisibility(View.VISIBLE);
						}

						text_receiver.setText(jsonObjectAddress.get(C.KEY_JSON_FM_USER_NAME).toString());
						Log.d("necroprg", "text_receiver : " + text_receiver.getText().toString());

						text_phone.setText(jsonObjectAddress.get(C.KEY_JSON_FM_MOBILE).toString());
						Log.d("necroprg", "text_phone : " + text_phone.getText().toString());

						text_address.setText(jsonObjectAddress.get(C.KEY_JSON_FM_USER_TEXT).toString());
						Log.d("necroprg", "text_address : " + text_address.getText().toString());

						text_lgtime.setText(jsonlg.get(C.KEY_JSON_FM_EXP_Date).toString());
						Log.d("necroprg", "text_lgtime : " + text_lgtime.getText().toString());

						text_lgstate.setText(jsonlg.get(C.KEY_JSON_FM_EXP_LOCATION).toString());
						Log.d("necroprg", "text_lgstate : " + text_lgstate.getText().toString());

						text_order_code.setText(getResources().getString(com.rs.mobile.wportal.R.string.order_code) + " : "
								+ obj.get(C.KEY_JSON_FM_ORDERCODE).toString());
						Log.d("necroprg", "text_order_code : " + text_order_code.getText().toString());

						String payment2 = obj.get(C.KEY_JSON_FM_REAL_PRICE).toString();
						text_pay_money
								.setText(getString(com.rs.mobile.wportal.R.string.common_text016) + payment2);
						Log.d("necroprg", "text_pay_money : " + text_pay_money.getText().toString());

						order_money.setText(
								getResources().getString(com.rs.mobile.wportal.R.string.rmb) + obj.get(C.KEY_JSON_FM_ORDERPRICE).toString());
						Log.d("necroprg", "order_money : " + order_money.getText().toString());

						good_coupon
								.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + obj.get("coupons_amount").toString());

						good_freight.setText(
								getResources().getString(com.rs.mobile.wportal.R.string.rmb) + obj.get(C.KEY_JSON_FM_FREIGHT).toString());

						good_point.setText(new DecimalFormat("0.00").format(obj.getDouble("point")));

						text_payment.setText(obj.get(C.KEY_JSON_FM_PAYMENT).toString());
						Log.d("necroprg", "text_payment : " + text_payment.getText().toString());

						text_send_time.setText(obj.get(C.KEY_JSON_FM_DISTRIBUTION).toString());
						Log.d("necroprg", "text_send_time : " + text_send_time.getText().toString());

						String payment1 = obj.get(C.KEY_JSON_FM_ORDERPRICE).toString();
						text_account.setText(getString(com.rs.mobile.wportal.R.string.common_text017) + getResources().getString(com.rs.mobile.wportal.R.string.rmb)
								+ payment1.substring(0, payment1.indexOf(".")) + "원");
						Log.d("necroprg", "text_account : " + text_account.getText().toString());

						JSONArray arr = obj.getJSONArray(C.KEY_JSON_FM_GOODSLIST);

						list_view.setAdapter(new OrderDetailAdapter(arr, OrderDetailActivity.this,
								obj.get(C.KEY_JSON_FM_ORDERCODE).toString(), ONLINE_ORDER_STATUS));

						setListViewHeight(list_view);

					} catch (Exception e) {

						//L.e(e);
						Log.d("necroprg", e.toString());

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

	/**
	 * 상품 수령
	 * 
	 * @param order_id
	 */
	private void remindOrder(String order_id) {

		try {

			Map<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_JSON_FM_ORDERCODE, order_id);

			OkHttpHelper okHttpHelper = new OkHttpHelper(OrderDetailActivity.this);

			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					try {
						if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("1")) {
							t(getResources().getString(com.rs.mobile.wportal.R.string.success));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.ORDER_REMIND, params);

		} catch (Exception e) {

			L.e(e);

		}
	}

	/**
	 * 상품 수령
	 * 
	 * @param order_id
	 *            : position
	 */
	private void confirmGetGoods(String order_id, String hasRefundConfirm) {

		try {

			Map<String, String> params = new HashMap<String, String>();

			params.put("order_num", order_id);
			params.put("hasRefundConfirm", hasRefundConfirm);
			OkHttpHelper okHttpHelper = new OkHttpHelper(OrderDetailActivity.this);

			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

					try {

						if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("1")) {

							T.showToast(OrderDetailActivity.this, data.getString("message"));

							finish();

						} else if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("-16421")) {

							// TODO Auto-generated method stub

							// TODO Auto-generated method stub
							D.showDialog(OrderDetailActivity.this, -1, getString(com.rs.mobile.wportal.R.string.common_text001),
									data.getString("message"), getString(com.rs.mobile.wportal.R.string.common_text003),
									new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											D.alertDialog.dismiss();
											confirmGetGoods(order_code, "true");
										}
									}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											D.alertDialog.dismiss();
										}
									});

						} else {
							t(data.optString("message"));
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block

						L.e(e);

					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}

			}, Constant.SM_BASE_URL + Constant.CONFIRM_ORDER_DELIVERY, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == 1000) {

				finish();

			}
		}
	}

	private void cancelOrder(String order_id) {

		try {

			Map<String, String> params = new HashMap<String, String>();

			params.put("order_num", order_id);

			OkHttpHelper okHttpHelper = new OkHttpHelper(OrderDetailActivity.this);

			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					try {

						if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("1")) {

							t(data.getString("message"));

							finish();

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.SM_BASE_URL + Constant.CANCEL_ORDER, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void deleteOrder(String ordernum) {
		OkHttpHelper okHttpHelper = new OkHttpHelper(OrderDetailActivity.this);
		HashMap<String, String> paramsKeyValue = new HashMap<>();
		paramsKeyValue.put("order_num", ordernum);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					t(data.getString("message"));
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.DeleteOrder, paramsKeyValue);
	}
}