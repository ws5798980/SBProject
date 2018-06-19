package com.rs.mobile.wportal.activity.ht;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.pay.PayResult;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.PaymentUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;
import com.rs.mobile.wportal.view.TimerTextView;
import com.rs.mobile.wportal.view.TimerTextView.OnTimerOverListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class HtPaymentActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private TimerTextView text_leave_time;
	private WImageView img_hotel;
	private TextView text_name;
	private TextView text_type;
	private TextView text_time;
	private TextView text_price;
	private RelativeLayout pay_type_yucheng;
	private ImageView selector_pay_way_yucheng;
	private RelativeLayout pay_type_alipay;
	private ImageView selector_pay_way_alipay;
	private RelativeLayout pay_type_unionpay;
	private ImageView selector_pay_way_unionpay;
	private TextView text_pay;
	private String order_no;
	private String order_amount;
	private String real_amount;
	private String order_Point;
	private String currentTime;
	private String overTime;
	private String hotelName;
	private String bedType;
	private String timeContent;
	private String imgUrl;
	protected int payType = 0;
	private static final int SDK_PAY_FLAG = 1;
	private Handler alipayHandler = new Handler() {

		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case SDK_PAY_FLAG: {

				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult(
						(Map<String, String>) msg.obj);

				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				String msginfo;
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {

					msginfo = getString(com.rs.mobile.wportal.R.string.common_text005);

					t(msginfo);

					return;

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

				PageUtil.jumpToWithFlag(HtPaymentActivity.this,
						MyOrderActivity.class);
				finish();

				break;
			}

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_payment);
		initToolbar();
		initView();
		initData();
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
			tv_title.setText(getString(com.rs.mobile.wportal.R.string.common_text031));
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {
		text_leave_time = (TimerTextView) findViewById(com.rs.mobile.wportal.R.id.text_leave_time);
		img_hotel = (WImageView) findViewById(com.rs.mobile.wportal.R.id.img_hotel);
		text_name = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_name);
		text_type = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_type);
		text_time = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_time);
		text_price = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_price);
		pay_type_yucheng = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.pay_type_yucheng);
		pay_type_yucheng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				payType = 0;

				selector_pay_way_yucheng.setVisibility(View.VISIBLE);

				selector_pay_way_alipay.setVisibility(View.GONE);
				selector_pay_way_unionpay.setVisibility(View.GONE);

			}
		});
		selector_pay_way_yucheng = (ImageView) findViewById(com.rs.mobile.wportal.R.id.selector_pay_way_yucheng);
		pay_type_alipay = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.pay_type_alipay);
		pay_type_alipay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				payType = 1;

				selector_pay_way_yucheng.setVisibility(View.GONE);

				selector_pay_way_alipay.setVisibility(View.VISIBLE);
				selector_pay_way_unionpay.setVisibility(View.GONE);

			}
		});
		selector_pay_way_alipay = (ImageView) findViewById(com.rs.mobile.wportal.R.id.selector_pay_way_alipay);
		pay_type_unionpay = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.pay_type_unionpay);
		pay_type_unionpay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				payType = 2;

				selector_pay_way_yucheng.setVisibility(View.GONE);

				selector_pay_way_alipay.setVisibility(View.GONE);
				selector_pay_way_unionpay.setVisibility(View.VISIBLE);

			}
		});
		selector_pay_way_unionpay = (ImageView) findViewById(com.rs.mobile.wportal.R.id.selector_pay_way_unionpay);
		text_pay = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_pay);
	}

	private void initData() {
		order_no = getIntent().getStringExtra("order_no");
		order_amount = getIntent().getStringExtra("order_amount");
		real_amount = getIntent().getStringExtra("real_amount");
		order_Point = getIntent().getStringExtra("order_Point");
		currentTime = getIntent().getStringExtra("currentTime");
		overTime = getIntent().getStringExtra("overTime");
		hotelName = getIntent().getStringExtra("hotelName");
		bedType = getIntent().getStringExtra("bedType");
		timeContent = getIntent().getStringExtra("timeContent");
		imgUrl = getIntent().getStringExtra("imgUrl");
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date1, date2;
		try {
			date1 = dateFormat.parse(currentTime);
			date2 = dateFormat.parse(overTime);
			long time = date2.getTime() - date1.getTime();
			if (!text_leave_time.isRun()) {
				text_leave_time.setTimes(time);
				text_leave_time.setDetail(getString(com.rs.mobile.wportal.R.string.common_text032));
				text_leave_time.setTextColor(ContextCompat.getColor(
						HtPaymentActivity.this,
						com.rs.mobile.wportal.R.color.textcolor_ht_tabhost_tabspec_selected));
				text_leave_time.start(new OnTimerOverListener() {

					@Override
					public void onOver() {
						// TODO Auto-generated method stub

					}
				});
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageUtil.drawImageFromUri(imgUrl, img_hotel);
		text_name.setText(hotelName);
		text_price
				.setText(getResources().getString(com.rs.mobile.wportal.R.string.rmb) + real_amount);
		text_time.setText(timeContent);
		text_type.setText(bedType);
		text_pay.setText(getString(com.rs.mobile.wportal.R.string.common_text033) + "   "
				+ getResources().getString(com.rs.mobile.wportal.R.string.rmb) + real_amount);
		text_pay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (payType) {
				case 0:
					PaymentUtil.showKeyBoard(HtPaymentActivity.this, order_no,
							order_amount, order_Point, real_amount, "0",
							new OnPaymentResultListener() {

								@Override
								public void onResult(JSONObject data) {

									// TODO Auto-generated method
									// stub
									String status;
									try {
										status = data.get("status").toString();

										if (status.equals("1")) {
											JSONObject data1 = data
													.getJSONObject("data");
											String order_no1 = data1.get(
													"order_no").toString();
											Bundle bundle = new Bundle();
											bundle.putString("orderId",
													order_no);
											bundle.putBoolean("payment", true);
											PageUtil.jumpTo(
													HtPaymentActivity.this,
													HtOrderDetailActivity.class,
													bundle, 2);
											finish();

										} else {

											T.showToast(HtPaymentActivity.this,
													data.get("msg").toString());

											// // TODO Auto-generated
											// // method
											// // stub
											// if (status.equals("1101")) { //
											// wrong
											// // pw
											// showDialogTip(data.get("msg").toString(),
											// "设置支付密码");
											// } else if (status.equals("1711"))
											// { // not
											// // enough
											// // money
											// showDialogTip(data.get("msg").toString(),
											// "去充值");
											// } else { //
											// finish();
											// PageUtil.jumpToWithFlag(SmConfirmActivity.this,
											// MyOrderActivity.class);
											// }
											Bundle bundle = new Bundle();
											bundle.putString("orderId",
													order_no);
											bundle.putBoolean("payment", false);
											PageUtil.jumpTo(
													HtPaymentActivity.this,
													HtOrderDetailActivity.class,
													bundle);
											finish();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
									}

								}

								@Override
								public void onFail(String msg) {
									// TODO Auto-generated method
									// stub

									Bundle bundle = new Bundle();
									bundle.putString("orderId", order_no);
									bundle.putBoolean("payment", false);
									PageUtil.jumpTo(HtPaymentActivity.this,
											HtOrderDetailActivity.class, bundle);

								}
							});
					break;

				case 1:
		/*			PaymentUtil
							.alipayMethod(order_no, real_amount,HtPaymentActivity.this, HtOrderActivity.class);*/
					break;
				case 2:
					PaymentUtil.unionPayMethod(order_no, real_amount,
							HtPaymentActivity.this, HtOrderActivity.class);
					break;
				default:
					break;
				}
			}
		});

	}

	private void showDialogTip(String tips1, String tips2) {

		D.showDialog(HtPaymentActivity.this, -1, HtPaymentActivity.this
				.getResources().getString(com.rs.mobile.wportal.R.string.sm_text_tips), tips1, tips2,
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						D.alertDialog.dismiss();

						UtilClear.IntentToLongLiao(HtPaymentActivity.this,
								"cn.ycapp.im.ui.mywallet.MyWalletActivity","");
						HtPaymentActivity.this.finish();

					}
				},
				HtPaymentActivity.this.getResources()
						.getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						D.alertDialog.dismiss();
						finish();
						// PageUtil.jumpToWithFlag(SmConfirmActivity.this,
						// MyOrderActivity.class);
					}
				});

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);

		if (arg2 == null) {
			return;
		}

		String msg = "";
		/*
		 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
		String str = arg2.getExtras().getString("pay_result");

		if (str.equalsIgnoreCase("success")) {
			// 支付成功后，extra中如果存在result_data，取出校验
			// result_data结构见c）result_data参数说明

			msg = getString(com.rs.mobile.wportal.R.string.common_text005);

			t(msg);

			return;

		} else if (str.equalsIgnoreCase("cancel")) {

			msg = getString(com.rs.mobile.wportal.R.string.common_text008);

		} else if (str.equalsIgnoreCase("fail")) {

			msg = getString(com.rs.mobile.wportal.R.string.common_text007);

		} else {

			msg = getString(com.rs.mobile.wportal.R.string.common_text007);

		}

		t(msg);

		PageUtil.jumpToWithFlag(HtPaymentActivity.this, MyOrderActivity.class);

		finish();

	}
}
