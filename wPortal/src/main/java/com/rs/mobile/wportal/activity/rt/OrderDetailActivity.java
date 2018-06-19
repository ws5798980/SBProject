package com.rs.mobile.wportal.activity.rt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.pay.PayResult;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.PaymentUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * 
 * @author ZhaoYun
 * @date 2017-3-13
 */
public class OrderDetailActivity extends BaseActivity {

	// 툴바
	private Toolbar toolbar;
	private TextView tv_title;
	private LinearLayout iv_back;

	// 주문번호
	private String orderNumber;

	// 예약번호
	private String reserveID;

	// 이미지
	private WImageView image_view;

	// 정보01
	private TextView seller_info_01;

	// 정보02
	private TextView seller_info_02;

	// 정보03
	private TextView seller_info_03;

	// 주문번호
	private TextView order_number;

	// 주문 갯수
	private TextView order_count;

	// 금액
	private TextView sum_text_view;

	// 주문 리스트
	private LinearLayout list_view;

	// 주문 시간
	private TextView order_time_text_view;

	// 기타정보 01
	private TextView order_extra_info_01;

	// 포인트 버튼
	private RelativeLayout point_btn;

	// 포인트
	private TextView point_text_view;

	// 포인트 사용 checkbox
	private CheckBox point_check_box;

	// 쿠폰 버튼
	private RelativeLayout coupon_btn;

	// 쿠폰 이름
	private TextView coupon_text_view;

	// 우성결제
	private LinearLayout payment_way_01;
	private CheckBox payment_way_check_box_01;

	// 위쳇결제
	private LinearLayout payment_way_02;
	private CheckBox payment_way_check_box_02;

	// 알리페이결제
	private LinearLayout payment_way_03;
	private CheckBox payment_way_check_box_03;

	// 유니온페이결제
	private LinearLayout payment_way_04;
	private CheckBox payment_way_check_box_04;

	// 포인트/쿠폰 적용 후의 총 금액
	private TextView total_text_view;

	// 결제 버튼
	private TextView payment_btn;

	// 사용가능한 포인트
	private float totalPoint = 0;

	// 사용한 포인트
	private float usedPoint = 0;

	// 사용 가능한 포인트
	private String canConsumePoint = "";

	// 사용한 쿠폰 가격
	private float couponPrice = 0;

	// 본 결제할 금액
	private float orgPrice = 0;

	// 실제 결재할 금액
	private float realPrice = 0;

	private Handler alipayHandler = new Handler() {

		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);

				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();

				String msginfo;

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {

					msginfo = getString(R.string.common_text005);

					addAdvance("A");

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		orderNumber = getIntent().getStringExtra("orderNumber");

		reserveID = getIntent().getStringExtra("reserveID");
		
		String title = getIntent().getStringExtra("title");
		
		String payText = getIntent().getStringExtra("payText");

		setContentView(R.layout.activity_order_detail);
		initToolbar();
		initViews();
		initDates();
		
		if (title != null && !title.equals(""))
			tv_title.setText(title);
		
		if (payText != null && !payText.equals(""))
			payment_btn.setText(payText);
		
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(R.id.toolbar);
			iv_back = (LinearLayout) findViewById(R.id.iv_back);
			tv_title = (TextView) findViewById(R.id.tv_title);
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initViews() {

		try {

			tv_title.setText(getString(R.string.order_detail_title));

			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onBack();
				}
			});

			// 판매자 정보
			image_view = (WImageView) findViewById(R.id.image_view);

			image_view.setRounding(true);

			seller_info_01 = (TextView) findViewById(R.id.seller_info_01);

			seller_info_02 = (TextView) findViewById(R.id.seller_info_02);

			seller_info_03 = (TextView) findViewById(R.id.seller_info_03);

			// 주문 번호
			order_number = (TextView) findViewById(R.id.order_number);

			// 주문갯수
			order_count = (TextView) findViewById(R.id.order_count);

			// 포인트, 쿠폰 적용 전 합계
			sum_text_view = (TextView) findViewById(R.id.sum_text_view);

			// 주문 리스트
			list_view = (LinearLayout) findViewById(R.id.list_view);

			// 주문 시간
			order_time_text_view = (TextView) findViewById(R.id.order_time_text_view);

			// 기타 주문 정보
			order_extra_info_01 = (TextView) findViewById(R.id.order_extra_info_01);

			// 포인트 버튼
			point_btn = (RelativeLayout) findViewById(R.id.point_btn);
			point_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					point_check_box.setChecked(!point_check_box.isChecked());

					setPointCoupon();

				}
			});
			// 포인트
			point_text_view = (TextView) findViewById(R.id.point_text_view);

			// 포인트 사용 checkbox
			point_check_box = (CheckBox) findViewById(R.id.point_check_box);
			point_check_box.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					setPointCoupon();

				}
			});

			// 쿠폰 버튼
			coupon_btn = (RelativeLayout) findViewById(R.id.coupon_btn);
			coupon_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					couponPrice = 0;

					Intent i = new Intent(OrderDetailActivity.this, RtCouponActivity.class);
					i.putExtra("orderNum", orderNumber);
					startActivityForResult(i, 1000);

				}
			});

			// 쿠폰 이름
			coupon_text_view = (TextView) findViewById(R.id.coupon_text_view);

			// 우성결제
			payment_way_01 = (LinearLayout) findViewById(R.id.payment_way_01);
			payment_way_check_box_01 = (CheckBox) findViewById(R.id.payment_way_check_box_01);

			// 위쳇결제
			payment_way_02 = (LinearLayout) findViewById(R.id.payment_way_02);
			payment_way_check_box_02 = (CheckBox) findViewById(R.id.payment_way_check_box_02);

			// 알리페이결제
			payment_way_03 = (LinearLayout) findViewById(R.id.payment_way_03);
			payment_way_check_box_03 = (CheckBox) findViewById(R.id.payment_way_check_box_03);

			// 유니온페이결제
			payment_way_04 = (LinearLayout) findViewById(R.id.payment_way_04);
			payment_way_check_box_04 = (CheckBox) findViewById(R.id.payment_way_check_box_04);

			// 우성결제
			payment_way_01.setOnClickListener(payClickListener);
			payment_way_check_box_01.setOnClickListener(payClickListener);

			// 위쳇결제
			payment_way_02.setOnClickListener(payClickListener);
			payment_way_check_box_02.setOnClickListener(payClickListener);

			// 알리페이결제
			payment_way_03.setOnClickListener(payClickListener);
			payment_way_check_box_03.setOnClickListener(payClickListener);

			// 유니온페이결제
			payment_way_04.setOnClickListener(payClickListener);
			payment_way_check_box_04.setOnClickListener(payClickListener);

			// 총 금액
			total_text_view = (TextView) findViewById(R.id.total_text_view);

			// 결제 버튼
			payment_btn = (TextView) findViewById(R.id.payment_btn);
			payment_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						if (payment_way_check_box_01.isChecked() == true) {

							// 우성페이
							PaymentUtil.showKeyBoard(OrderDetailActivity.this,
									// orderNum
									orderNumber,
									// 쿠폰, 포인트 적용 전
									sum_text_view.getText().toString().trim(),
									// 포인트
									"" + usedPoint, // 
									// 쿠폰 포인트 적용 후
									total_text_view.getText().toString().replaceAll(getString(R.string.money), "").trim(),
									// 쿠폰
									"0", new OnPaymentResultListener() {

										@Override
										public void onResult(JSONObject data) {
											// TODO Auto-generated method stub

											try {

												String status = data.get("status").toString();

												if (status.equals("1")) {

													t(getString(R.string.common_text005));

													addAdvance("Y", data.getJSONObject("data").getString("auth_no"));

												} else {

													T.showToast(OrderDetailActivity.this, data.get("msg").toString());

													// TODO Auto-generated
													// method stub
													if (status.equals("1101")) { // wrong pw

														// showErrorDialog(data.get("msg").toString(),"设置支付密码");

													} else if (status.equals("10")) { //

														// showErrorDialog(data.get("msg").toString(),"确定");

													} else if (status.equals("1711")) { // not enough money

														showErrorDialog(data.get("msg").toString(), getString(R.string.common_text021));

													}

												}

											} catch (Exception e) {

												L.e(e);

											}

										}

										@Override
										public void onFail(String msg) {
											// TODO Auto-generated method stub
											
											T.showToast(OrderDetailActivity.this, msg);

										}
									});

						} else if (payment_way_check_box_02.isChecked() == true) {

							// 위쳇페이
							T.showToast(OrderDetailActivity.this, getString(R.string.ready));

						} else if (payment_way_check_box_03.isChecked() == true) {

							// 알리페이
							/*PaymentUtil.alipayMethod(
									orderNumber, total_text_view.getText().toString()
											.replaceAll(getString(R.string.money), "").trim(),
									OrderDetailActivity.this, MyOrderActivity.class);*/

						} else if (payment_way_check_box_04.isChecked() == true) {

							// 유니온페이
							PaymentUtil.unionPayMethod(
									orderNumber, total_text_view.getText().toString()
											.replaceAll(getString(R.string.money), "").trim(),
									OrderDetailActivity.this, MyOrderActivity.class);

						} else {

							// 아무것도 선택하지 않았을때
							T.showToast(OrderDetailActivity.this, getString(R.string.order_detail_select_pay_way));
						}

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void initDates() {

		try {

			OkHttpHelper helper = new OkHttpHelper(OrderDetailActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(all_data);

						String status = data.getString("status");

						if (status != null && status.equals("1")) {

							data = data.getJSONObject("data");

							totalPoint = data.getString("point").equals("") ? 0
									: Float.parseFloat(data.getString("point"));

							canConsumePoint = data.getString("canConsumePoint").equals("") ? "0"
									: data.getString("canConsumePoint");

							orgPrice = data.getString("totalAmount").equals("") ? 0
									: Float.parseFloat(data.getString("totalAmount"));

							JSONArray orderArr = data.getJSONArray("orderItemList");

							RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
									RtMainActivity.displayWidth / 4, RtMainActivity.displayWidth / 4);

							params.rightMargin = getResources().getDimensionPixelSize(R.dimen.marginx2);

							ImageUtil.drawImageViewBuFullUrl(OrderDetailActivity.this, image_view, data,
									"shopThumImage", "ver", params);

							seller_info_01.setText(data.getString("restaurantName"));

							seller_info_02.setText(data.getString("phone"));

							seller_info_03.setText(data.getString("address"));

							order_number.setText(
									getString(R.string.order_detail_order_number) + " : " + data.getString("orderNum"));

							order_count.setText(orderArr.length() + "");

							sum_text_view.setText("" + orgPrice);

							if (data.has("actualOrderAmount")) {
							
								try {
									
									Float actualOrderAmount = data.getString("actualOrderAmount").equals("") ? 0
											: Float.parseFloat(data.getString("actualOrderAmount"));
									
									if (actualOrderAmount > 0) {
										
										total_text_view.setText(actualOrderAmount + getString(R.string.money));
										
										point_btn.setVisibility(View.GONE);
										coupon_btn.setVisibility(View.GONE);
										
									} else {
										
										total_text_view.setText(orgPrice + getString(R.string.money));
										
										point_btn.setVisibility(View.VISIBLE);
										coupon_btn.setVisibility(View.VISIBLE);
										
									}
								
								} catch (Exception e) {
									
									e(e);
								
									total_text_view.setText(orgPrice + getString(R.string.money));
									
									point_btn.setVisibility(View.VISIBLE);
									coupon_btn.setVisibility(View.VISIBLE);
									
								}
								
							} else {
								
								total_text_view.setText(orgPrice + getString(R.string.money));
								
								point_btn.setVisibility(View.VISIBLE);
								coupon_btn.setVisibility(View.VISIBLE);
								
							}
							order_time_text_view.setText(getString(R.string.order_detail_order_time) + " : "
									+ data.getString("reserveDate"));

							order_extra_info_01.setText(getString(R.string.order_detail_order_extra_01) + " : "
									+ data.getString("personCnt"));

							list_view.removeAllViews();

							for (int i = 0; i < orderArr.length(); i++) {

								LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.list_item_order,
										null);

								TextView name_text_view = (TextView) item.findViewById(R.id.name_text_view);

								TextView count_text_view = (TextView) item.findViewById(R.id.count_text_view);

								TextView price_text_view = (TextView) item.findViewById(R.id.price_text_view);

								JSONObject obj = orderArr.getJSONObject(i);

								name_text_view.setText(obj.getString("itemName"));

								count_text_view.setText("X" + obj.getString("quantity"));

								price_text_view.setText(obj.getString("amount") + getString(R.string.money));

								list_view.addView(item);

							}

							list_view.invalidate();
							
//							setPointCoupon();

							point_text_view.setText(getString(R.string.order_point_msg_01) + 
									canConsumePoint + getString(R.string.order_point_msg_02) + canConsumePoint);
							
//							point_text_view.setText(getString(R.string.order_point_msg_01) + data.getString("point")
//							+ getString(R.string.order_point_msg_02) + "0");
							
							//当积分为0时隐藏积分兰
							if(totalPoint <= 0)
								point_btn.setVisibility(View.GONE);
						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_RT + Constant.RT_GET_ORDER + "?memberID="
					+ S.getShare(OrderDetailActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&orderNum=" + orderNumber
					+ "&token=" + S.getShare(OrderDetailActivity.this, C.KEY_JSON_TOKEN, ""), params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 결제 완료
	 * 
	 * @param payType
	 *            : "Y" or "A" or "W" or "U"
	 */
	public void addAdvance(String payType) {

		try {

			OkHttpHelper helper = new OkHttpHelper(OrderDetailActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(all_data);

						String status = data.getString("status");

						if (status != null && status.equals("1")) {

							finish();

							sendBroadcast(new Intent(RtMainActivity.ACTION_RECEIVE_RT_FINISH));

							Intent reserveDetailIntent = new Intent(OrderDetailActivity.this,
									RtReserveDetailActivity.class);
							reserveDetailIntent.putExtra(C.EXTRA_KEY_MEMBERID,
									S.getShare(OrderDetailActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));// TODO
																										// 这里要在流程通畅后填入实时获取的memberId
							reserveDetailIntent.putExtra(C.EXTRA_KEY_RESERVEID, reserveID);
							reserveDetailIntent.putExtra(C.EXTRA_KEY_ORDERNUM, orderNumber);
							reserveDetailIntent.putExtra(C.EXTRA_KEY_RESERVESTATUS, "B");
							startActivity(reserveDetailIntent);

						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_RT + C.RT_ADD_PAYMENT_ADVANCE + "?memberID="
					+ S.getShare(OrderDetailActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&payType=" + payType
					+ "&orderNum=" + orderNumber + "&orderAmount="
					+ total_text_view.getText().toString().replaceAll(getString(R.string.money), "").trim() + "&token="
					+ S.getShare(OrderDetailActivity.this, C.KEY_JSON_TOKEN, ""), params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 결제 완료
	 * 
	 * @param payType
	 *            : "Y" or "A" or "W" or "U"
	 */
	public void addAdvance(String payType, String auth_no) {

		try {

			OkHttpHelper helper = new OkHttpHelper(OrderDetailActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			// params.put("memberID", S.getShare(OrderDetailActivity.this,
			// C.KEY_REQUEST_MEMBER_ID, ""));
			// params.put("payType", payType);
			// params.put("orderNum", orderNumber);
			// params.put("orderAmount",
			// total_text_view.getText().toString().replaceAll(getString(R.string.money),
			// "").trim());
			// params.put("token", S.getShare(OrderDetailActivity.this,
			// C.KEY_JSON_TOKEN, ""));
			// params.put("point", "0");
			// params.put("wsPayAuthNo", auth_no);

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(all_data);

						String status = data.getString("status");

						if (status != null && status.equals("1")) {

							finish();

							sendBroadcast(new Intent(RtMainActivity.ACTION_RECEIVE_RT_FINISH));

							Intent reserveDetailIntent = new Intent(OrderDetailActivity.this,
									RtReserveDetailActivity.class);
							reserveDetailIntent.putExtra(C.EXTRA_KEY_MEMBERID,
									S.getShare(OrderDetailActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));// TODO
																										// 这里要在流程通畅后填入实时获取的memberId
							reserveDetailIntent.putExtra(C.EXTRA_KEY_RESERVEID, reserveID);
							reserveDetailIntent.putExtra(C.EXTRA_KEY_ORDERNUM, orderNumber);
							reserveDetailIntent.putExtra(C.EXTRA_KEY_RESERVESTATUS, "B");
							startActivity(reserveDetailIntent);

						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_RT + C.RT_ADD_PAYMENT_ADVANCE + "?memberID="
					+ S.getShare(OrderDetailActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&payType=" + payType
					+ "&orderNum=" + orderNumber + "&orderAmount="
					+ total_text_view.getText().toString().replaceAll(getString(R.string.money), "").trim() + "&token="
					+ S.getShare(OrderDetailActivity.this, C.KEY_JSON_TOKEN, "") + "&point=" + usedPoint
					+ "&wsPayAuthNo=" + auth_no, params);

			// }, Constant.BASE_URL_RT + C.RT_ADD_PAYMENT_ADVANCE, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	OnClickListener payClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			int id = v.getId();

			if (id == R.id.payment_way_01 || id == R.id.payment_way_check_box_01) {

				// 우성페이
				payment_way_check_box_01.setChecked(true);
				payment_way_check_box_02.setChecked(false);
				payment_way_check_box_03.setChecked(false);
				payment_way_check_box_04.setChecked(false);

			} else if (id == R.id.payment_way_02 || id == R.id.payment_way_check_box_02) {

				// 위쳇페이
				payment_way_check_box_01.setChecked(false);
				payment_way_check_box_02.setChecked(true);
				payment_way_check_box_03.setChecked(false);
				payment_way_check_box_04.setChecked(false);

			} else if (id == R.id.payment_way_03 || id == R.id.payment_way_check_box_03) {

				// 알리페이
				payment_way_check_box_01.setChecked(false);
				payment_way_check_box_02.setChecked(false);
				payment_way_check_box_03.setChecked(true);
				payment_way_check_box_04.setChecked(false);

			} else if (id == R.id.payment_way_04 || id == R.id.payment_way_check_box_04) {

				// 유니온페이
				payment_way_check_box_01.setChecked(false);
				payment_way_check_box_02.setChecked(false);
				payment_way_check_box_03.setChecked(false);
				payment_way_check_box_04.setChecked(true);

			}

		}
	};

	@Override
	public void onBackPressed() {
		onBack();
	}

	public void onBack() {
		
		try {

			AddUnpayOrder(orderNumber, 
					sum_text_view.getText().toString().trim(),
					total_text_view.getText().toString().replaceAll(getString(R.string.money), "").trim(),
					usedPoint + "", 
					couponPrice + "");
		
		} catch (Exception e) {
			
			e(e);
			
			sendBroadcast(new Intent(RtMainActivity.ACTION_RECEIVE_RT_FINISH));
			finish();
			
		}
	
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void showErrorDialog(String tips1, String tips2) {

		D.showDialog(OrderDetailActivity.this, -1, getString(R.string.sm_text_tips), tips1, tips2,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						D.alertDialog.dismiss();
						
						UtilClear.IntentToLongLiao(OrderDetailActivity.this,
								"cn.ycapp.im.ui.mywallet.MyWalletActivity","");

					}
				}, getString(R.string.cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {
						D.alertDialog.dismiss();
						PageUtil.jumpTo(OrderDetailActivity.this, MyOrderActivity.class);
					}
				});

	}

	/**
	 * 포인트와 쿠폰을 적용
	 * 
	 * 사용한 포인트와 쿠폰을 계산한다
	 */
	public void setPointCoupon() {

		try {

			// 쿠폰 적용
			realPrice = orgPrice - couponPrice;

			if (realPrice < 0) {

				realPrice = 0;

			}

			// 사용한 포인트 초기화
			usedPoint = 0;

			if (point_check_box.isChecked() == true) {

				usedPoint = Float.parseFloat(canConsumePoint);

				realPrice = realPrice - usedPoint;

				// if (realPrice < totalPoint) {
				//
				// //포인트가 결제 가격보다 큰경우
				// usedPoint = realPrice;
				//
				// realPrice = 0;
				//
				// } else {
				//
				// //결제 가격에서 포인트를 뺀다
				// usedPoint = totalPoint;
				//
				// realPrice = realPrice - totalPoint;
				//
				// }

			} else {

				usedPoint = 0;

			}

			point_text_view.setText(getString(R.string.order_point_msg_01) + canConsumePoint
					+ getString(R.string.order_point_msg_02) + canConsumePoint);
			
//			point_text_view.setText(getString(R.string.order_point_msg_01) + totalPoint
//					+ getString(R.string.order_point_msg_02) + usedPoint);

			total_text_view.setText(realPrice + getString(R.string.money));

		} catch (Exception e) {

			L.e(e);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			if (requestCode == 1000) { // 쿠폰 요청

				try {

					String resultData = data.getStringExtra("item");

					if (resultData == null || resultData.equals("")) { // 쿠폰 선택
																		// 안함

						couponPrice = 0;

						coupon_text_view.setText("");

						setPointCoupon();

						return;

					}

					JSONObject item = new JSONObject(resultData);

					setCoupon(item);

				} catch (Exception e) {

					L.e(e);

				}

			} else { // 결제

				if (data == null) {
					return;
				}

				String msg = "";
				/*
				 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
				 */
				String str = data.getExtras().getString("pay_result");
				if (str.equalsIgnoreCase("success")) {

					msg = "支付成功！";

					addAdvance("U");

				} else if (str.equalsIgnoreCase("cancel")) {

					msg = "用户取消了支付";

				} else if (str.equalsIgnoreCase("fail")) {

					msg = "支付失败！";

				} else {

					msg = "支付失败！";

				}

				t(msg);

			}

		}

	}

	/**
	 * 쿠폰 적용
	 * 
	 * @param item
	 */
	public void setCoupon(JSONObject item) {

		try {

			OkHttpHelper helper = new OkHttpHelper(OrderDetailActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(all_data);

						String status = data.getString("status");

						if (status != null && status.equals("1")) {

							String coupon = data.getString("data");

							couponPrice = Float.parseFloat(coupon);

							coupon_text_view.setText(
									getString(R.string.order_coupon_msg_02) + coupon + getString(R.string.money) + ": "
											+ coupon + getString(R.string.order_coupon_msg_03));

							setPointCoupon();

						} else {

							coupon_text_view.setText("");

						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_RT + Constant.RT_GET_COUPON_CAL + "?memberID="
					+ S.getShare(OrderDetailActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&orderNum=" + orderNumber
					+ "&couponCode=" + item.getString("couponNo") + "&token="
					+ S.getShare(OrderDetailActivity.this, C.KEY_JSON_TOKEN, ""), params);

		} catch (Exception e) {

			L.e(e);

		}

	}
	
	/**
	 * 
	 * 종료 요청시 포인트와 쿠폰을 서버에 저장한다
	 * 
	 * 뒤로가기 버튼을 누르는것을 결제가 정상적으로 적용되지 않았다고 판단한다
	 * 
	 * @param orderNo
	 * @param orderAmount
	 * @param realAmount
	 * @param pointAmount
	 * @param couponAmount
	 */
	public void AddUnpayOrder(String orderNo, String orderAmount, String realAmount, String pointAmount, String couponAmount) {

		try {

			OkHttpHelper helper = new OkHttpHelper(OrderDetailActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
					
					sendBroadcast(new Intent(RtMainActivity.ACTION_RECEIVE_RT_FINISH));
					finish();

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(data.toString());

					} catch (Exception e) {

						L.e(e);

					}

					sendBroadcast(new Intent(RtMainActivity.ACTION_RECEIVE_RT_FINISH));
					finish();
					
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
					
					sendBroadcast(new Intent(RtMainActivity.ACTION_RECEIVE_RT_FINISH));
					finish();

				}
			}, Constant.BASE_URL_RT + Constant.RT_ADD_UNPAY_ORDER + "?orderNo=" + orderNo + "&memberID="
					+ S.getShare(OrderDetailActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&orderAmount=" + orderAmount
					+ "&realAmount=" + realAmount + "&pointAmount=" + pointAmount + "&couponAmount=" + couponAmount 
					+ "&token=" + S.getShare(OrderDetailActivity.this, C.KEY_JSON_TOKEN, ""), params);

		} catch (Exception e) {

			L.e(e);
			
			sendBroadcast(new Intent(RtMainActivity.ACTION_RECEIVE_RT_FINISH));
			finish();

		}

	}

}