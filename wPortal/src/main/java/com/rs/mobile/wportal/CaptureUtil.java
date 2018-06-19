package com.rs.mobile.wportal;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.PaymentUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.activity.rt.OrderDetailActivity;
import com.rs.mobile.wportal.activity.rt.RtMenuListActivity;
import com.rs.mobile.wportal.activity.rt.RtReserveDetailActivity;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;
import com.rs.mobile.wportal.activity.xsgr.PaymentActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

/*
 * qr, barcode scaner
 */
public class CaptureUtil {

	public static void handleResultScaning(final Context context,
			String resultString, String flag) {

		try {

			if (resultString.contains(C.BASE_URL)) {

				Intent intent = new Intent(context, WebActivity.class);
				intent.putExtra(C.KEY_INTENT_URL, resultString);
				context.startActivity(intent);

			} else if(resultString.startsWith("giga://qrPay"))
			{
				String c_code=resultString.replace("giga://qrPay?","");

				//todo: 将c_code 传给扫码后的支付界面进行处理。
				Intent intent = new Intent(context, PaymentActivity.class);
				intent.putExtra("code", c_code);
				context.startActivity(intent);
			}else if (resultString.startsWith(C.KEY_URL_SCHEME)) {
				// yuchang://call_view:LoginActivity?id=dlwpdlr&pw=a1249711

				String[] s = resultString.split("\\?");

				// call activity
				if (s[0].contains(C.KEY_URL_CALL_VIEW)) {

					// getExtra
					String[] p = s[1].split("&");

					HashMap<String, String> extra = new HashMap<String, String>();

					for (int i = 0; i < p.length; i++) {

						String[] keyValue = p[i].split("=");

						extra.put(keyValue[0], keyValue[1]);

					}

					// class
					Class<?> cls = Class
							.forName(context.getPackageName() + ".activity."
									+ s[0].split("://")[1].split(":")[1]);

					UiUtil.startActivity(context, cls, extra);

				} else if (s[0].contains(C.KEY_URL_RESEVER_RESTRAUNT)) {

					// yuchang://reserverRestraunt?1&A01&01-003-02
					String[] p = s[1].split("&");

					reserve(context, p[1], p[2]);

					return;

				} else if (s[0].contains(C.KEY_URL_PAY_FROM_QR)) {

					// yuchang://payFromQR:2?1&A01&201703270000000006Q

					String[] p = s[1].split("&");

					reserve(context, p[0], p[1], p[2]);

					return;

				} else if (s[0].contains(C.HOST_PAY_POS)) {

					// yuchang://payFromQR:2?1&A01&201703270000000006Q

					UtilClear.CheckLogin(context, new UtilCheckLogin.CheckError() {

						@Override
						public void onError() {
							return;
						}
					});

					// store_code&parter_key&order_no&order_date&order_amount

					String[] p = s[1].split("\\|");

					// 우성페이
					PaymentUtil.showKeyBoardOffline(context, p[0], p[1], p[2],
							p[3], p[4], new OnPaymentResultListener() {

								@Override
								public void onResult(JSONObject data) {
									// TODO Auto-generated method stub

									try {

										String status = data.get("status")
												.toString();

										if (status.equals("1")) {

											T.showToast(
													context,
													context.getString(R.string.common_text005));

										} else {

											T.showToast(context, data
													.get("msg").toString());

											// TODO Auto-generated
											// method stub
											if (status.equals("1101")) { // wrong
																			// pw

												// showErrorDialog(data.get("msg").toString(),"设置支付密码");

											} else if (status.equals("10")) { //

												// showErrorDialog(data.get("msg").toString(),"确定");

											} else if (status.equals("1711")) { // not
																				// enough
																				// money

												showErrorDialog(
														context,
														data.get("msg")
																.toString(),
														context.getString(R.string.common_text021));

											}

										}

									} catch (Exception e) {

										L.e(e);

									}

								}

								@Override
								public void onFail(String msg) {
									T.showToast(context, msg);
								}
							});

					return;

				}

			} else if (Util.isNumeric(resultString)) {//扫描全数字的条形码

				D.showAlertDialog(context, -1, "扫描标识", resultString, "确定", new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						D.alertDialog.dismiss();
					}
				});
				
				// 신선마트
//				Bundle bundle = new Bundle();
//				bundle.putString(C.KEY_JSON_FM_ITEM_CODE, resultString);
//				bundle.putString(C.KEY_DIV_CODE, C.DIV_CODE);
//				bundle.putInt("IntentStatus", 1);
//				PageUtil.jumpTo(context, SmGoodsDetailActivity.class, bundle);

			} else {

				Intent intent = new Intent(context, WebActivity.class);
				intent.putExtra(C.KEY_INTENT_URL, resultString);
				context.startActivity(intent);

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 외식 식당 예약
	 * 
	 * @param restaurantCode
	 * @param tableName
	 */
	public static void reserve(final Context context,
			final String restaurantCode, String tableName) {

		try {

			UtilClear.CheckLogin(context, new UtilCheckLogin.CheckError() {

				@Override
				public void onError() {
					return;
				}
			});

			OkHttpHelper helper = new OkHttpHelper(context);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(
					new OkHttpHelper.CallbackLogic() {

						@Override
						public void onNetworkError(Request request,
								IOException e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onBizSuccess(String responseDescription,
								JSONObject data, final String all_data) {
							// TODO Auto-generated method stub

							try {

								L.d(all_data);

								String status = data.getString("status");

								if (status != null && status.equals("1")) {

									String msg = data.getString("msg");

									T.showToast(context, msg);

									data = data.getJSONObject("data");

									String reserveStatus = data
											.getString("reserveStatus");

									if (reserveStatus != null) {

										if (reserveStatus.equals("Q")) {

											// 일반
											Intent i = new Intent(context,
													RtMenuListActivity.class);
											i.putExtra("reserveID",
													data.getString("reserveID"));
											i.putExtra("restaurantCode",
													restaurantCode);
											context.startActivity(i);

										} else if (reserveStatus.equals("B")) {

											// 결제 완료
											Intent i = new Intent(
													context,
													RtReserveDetailActivity.class);
											i.putExtra(
													"memberID",
													S.getShare(
															context,
															C.KEY_REQUEST_MEMBER_ID,
															""));// TODO
																	// 这里要在流程通畅后填入实时获取的memberId
											i.putExtra("reserveID",
													data.getString("reserveID"));
											i.putExtra("orderNum", data
													.getString("orderNumber"));
											i.putExtra("reserveStatus",
													reserveStatus);
											context.startActivity(i);

										} else if (reserveStatus.equals("O")) {

											// 예약 완료(메뉴 골랐어)
											Intent i = new Intent(context,
													OrderDetailActivity.class);
											i.putExtra("orderNumber", data
													.getString("orderNumber"));
											context.startActivity(i);

										} else if (reserveStatus.equals("R")) {

											// 예약 완료(메뉴 안골랐어)
											Intent i = new Intent(context,
													RtMenuListActivity.class);
											i.putExtra("reserveID",
													data.getString("reserveID"));
											i.putExtra("restaurantCode",
													restaurantCode);
											context.startActivity(i);

										}

									}

								}

							} catch (Exception e) {

								L.e(e);

							}

						}

						@Override
						public void onBizFailure(String responseDescription,
								JSONObject data, String responseCode) {
							// TODO Auto-generated method stub

						}
					},
					Constant.BASE_URL_RT + Constant.RT_QR_CODE_RESEVER
							+ "?restaurantCode=" + restaurantCode
							+ "&tableName=" + tableName + "&userID="
							+ S.getShare(context, C.KEY_REQUEST_MEMBER_ID, "")
							+ "&token="
							+ S.getShare(context, C.KEY_JSON_TOKEN, ""), params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 외식 식당 예약
	 * 
	 * @param divCode
	 * @param restaurantCode
	 * @param tempOrderNum
	 */
	public static void reserve(final Context context, String divCode,
			String restaurantCode, String tempOrderNum) {

		try {

			UtilClear.CheckLogin(context, new UtilCheckLogin.CheckError() {

				@Override
				public void onError() {
					return;
				}
			});

			OkHttpHelper helper = new OkHttpHelper(context);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(
					new OkHttpHelper.CallbackLogic() {

						@Override
						public void onNetworkError(Request request,
								IOException e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onBizSuccess(String responseDescription,
								JSONObject data, final String all_data) {
							// TODO Auto-generated method stub

							try {

								L.d(all_data);

								String status = data.getString("status");

								String msg = "";

								if (data.has("msg"))
									msg = data.getString("msg");

								if (status != null && status.equals("1")) {

									data = data.getJSONObject("data");

									if (data.has("payer") == true
											&& data.getString("payer") != null
											&& !data.getString("payer").equals(
													"")) {

										// 결제 완료
										Intent i = new Intent(context,
												RtReserveDetailActivity.class);
										i.putExtra("memberID", S.getShare(
												context,
												C.KEY_REQUEST_MEMBER_ID, ""));// 这里要在流程通畅后填入实时获取的memberId
										i.putExtra("reserveID",
												data.getString("reserveID"));
										i.putExtra("orderNum",
												data.getString("orderNum"));
										i.putExtra("reserveStatus", "B");
										context.startActivity(i);

										L.d(S.getShare(context,
												C.KEY_REQUEST_MEMBER_ID, ""));
										L.d(data.getString("reserveID"));
										L.d(data.getString("orderNum"));
										L.d("B");

									} else {

										// 예약 완료(메뉴 골랐어)
										Intent i = new Intent(context,
												OrderDetailActivity.class);

										i.putExtra("orderNumber",
												data.getString("orderNum"));

										context.startActivity(i);

									}

								}

								T.showToast(context, msg);

							} catch (Exception e) {

								L.e(e);

							}

						}

						@Override
						public void onBizFailure(String responseDescription,
								JSONObject data, String responseCode) {
							// TODO Auto-generated method stub

						}
					},
					Constant.BASE_URL_RT + Constant.RT_SET_ORDER_BY_QR
							+ "?divCode=" + divCode + "&restaurantCode="
							+ restaurantCode + "&userID="
							+ S.getShare(context, C.KEY_REQUEST_MEMBER_ID, "")
							+ "&tempOrderNum=" + tempOrderNum, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private static void showErrorDialog(final Context context, String tips1,
			String tips2) {

		D.showDialog(context, -1, context.getString(R.string.sm_text_tips),
				tips1, tips2, new OnClickListener() {

					@Override
					public void onClick(View v) {
						D.alertDialog.dismiss();
						UtilClear.IntentToLongLiao(context,
								"cn.ycapp.im.ui.mywallet.MyWalletActivity","");
					}
				}, context.getString(R.string.cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						D.alertDialog.dismiss();
						PageUtil.jumpTo(context, MyOrderActivity.class);
					}
				});

	}

}
