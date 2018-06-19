
package com.rs.mobile.wportal.adapter.sm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.XListView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.EvaluateListActivity;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;
import com.rs.mobile.wportal.activity.sm.OrderDetailActivity;
import com.rs.mobile.wportal.activity.sm.OrderDetailPayActivity;
import com.rs.mobile.wportal.activity.sm.SmConfirmActivity;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.wportal.fragment.sm.MyOrderCommonFragment;
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

@SuppressLint("NewApi")
public class MyOrderAdapter extends BaseAdapter {

	private Activity context;

	private JSONArray jsonArray;

	private MyOrderCommonFragment fragment;

	private Object syncPaymentStatus;

	private String serverTime;

	private ArrayList<ShoppingCart> listAttach = new ArrayList<ShoppingCart>();

	private class ViewHolder {

		private TextView text_orderTime;

		private TextView text_orderStatus;

		private ListView listView;

		private TextView text_totoal, cancelBtn, paymentBtn, delete_btn, payment_btnAgain;

		private TimerTextView timer_textview;

	}

	public MyOrderAdapter(Activity context, JSONArray jsonArray, MyOrderCommonFragment fragment, String serverTime) {

		this.context = context;

		this.jsonArray = jsonArray;

		this.fragment = fragment;

		this.serverTime = serverTime;

	}

	@Override
	public int getCount() {

		return jsonArray.length();
	}

	@Override
	public Object getItem(int position) {

		try {

			return jsonArray.get(position);

		} catch (Exception e) {

			L.e(e);

		}

		return null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		try {

			ViewHolder itemView = null;

			if (convertView == null) {

				itemView = new ViewHolder();

				convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.list_item_myorder_common,
						parent, false);

				itemView.listView = (ListView) convertView.findViewById(com.rs.mobile.wportal.R.id.listView);

				itemView.text_orderStatus = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_orderStatus);

				itemView.text_orderTime = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_orderTime);

				itemView.text_totoal = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.text_totoal);

				itemView.cancelBtn = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.cancel_btn);

				itemView.paymentBtn = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.payment_btn);

				itemView.payment_btnAgain = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.payment_btnAgain);

				itemView.timer_textview = (TimerTextView) convertView.findViewById(com.rs.mobile.wportal.R.id.payment_btn1);

				itemView.delete_btn = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.delete_btn);

				convertView.setTag(itemView);

			} else {

				itemView = (ViewHolder) convertView.getTag();

			}
			itemView.cancelBtn.setVisibility(View.GONE);
			itemView.paymentBtn.setVisibility(View.GONE);

			itemView.payment_btnAgain.setVisibility(View.GONE);

			itemView.timer_textview.setVisibility(View.GONE);

			itemView.delete_btn.setVisibility(View.GONE);
			final JSONObject jsonObject = new JSONObject(getItem(position).toString());

			final String order_id = jsonObject.getString("order_code");

			boolean deletable = jsonObject.getBoolean("deletable");

			boolean buyAgain = jsonObject.getBoolean("buyAgain");

			itemView.text_orderTime.setText(
					context.getString(com.rs.mobile.wportal.R.string.common_text075) + jsonObject.get(C.KEY_JSON_FM_ORDERDATE).toString());

			syncPaymentStatus = jsonObject.get("syncPaymentStatus").toString();

			// 주문 상태
			final String flag = jsonObject.getString("ONLINE_ORDER_STATUS");

			// 배송 상태
			final String flag_exp = jsonObject.getString("express_status");

			// ?
			final String flag_ass = jsonObject.getString("assess_status");

			final String div_code = jsonObject.getString("div_code");

			if (deletable) {
				itemView.delete_btn.setVisibility(View.VISIBLE);
				itemView.delete_btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						D.showDialog(context, -1, context.getString(com.rs.mobile.wportal.R.string.common_text001),
								context.getString(com.rs.mobile.wportal.R.string.common_text002), context.getString(com.rs.mobile.wportal.R.string.common_text003),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										D.alertDialog.dismiss();
										deleteOrder(order_id);
									}
								}, context.getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

									@Override
									public void onClick(View v) {
										D.alertDialog.dismiss();
									}
								});

					}
				});
			} else {
				itemView.delete_btn.setVisibility(View.GONE);
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {

						Bundle bundle = new Bundle();

						bundle.putString(C.KEY_JSON_FM_ORDERCODE, jsonObject.getString("order_code"));

						if (flag.equals("1")) {
							PageUtil.jumpTo(context, OrderDetailPayActivity.class, bundle);

							((Activity) context).finish();

						} else {
							bundle.putString("flag_ass", flag_ass);

							bundle.putString("flag", flag);

							PageUtil.jumpTo(context, OrderDetailActivity.class, bundle);

							((Activity) context).finish();

						}

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			// 결제 금액
			final String totolprice = jsonObject.get(C.KEY_JSON_FM_ORDERPRICE).toString();

			// 실제 결제 금액
			final String realPrice = jsonObject.get("real_amount").toString();

			// 포인트
			final String point = jsonObject.get("pointAmount").toString();

			// ?
			String payment_status = jsonObject.get("payment_status").toString();

			String sale_custom_code = jsonObject.get("sale_custom_code").toString();

			itemView.text_totoal.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.order_bewrite1)
					+ jsonObject.get(C.KEY_JSON_FM_ORDERNUM) + context.getResources().getString(com.rs.mobile.wportal.R.string.order_bewrite2)
					+ jsonObject.get(C.KEY_JSON_FM_ORDERPRICE));

			JSONArray arr = jsonObject.getJSONArray(C.KEY_JSON_FM_ORDER_GROUPGOODLIST);

			final ArrayList<ShoppingCart> list = new ArrayList<ShoppingCart>();

			for (int i = 0; i < arr.length(); i++) {
				JSONObject jsobj = arr.getJSONObject(i);
				ShoppingCart shoppingCart = new ShoppingCart(jsobj.get(C.KEY_JSON_FM_ITEM_CODE).toString(),
						jsobj.get(C.KEY_JSON_FM_ITEM_NAME).toString(),
						Float.parseFloat(jsobj.get(C.KEY_JSON_FM_ITEM_PRICE).toString()),
						jsobj.get(C.KEY_JSON_FM_ITEM_IMAGE_URL).toString(), jsobj.getInt(C.KEY_JSON_FM_ITEM_QUANTITI),
						false, jsobj.get(C.KEY_JSON_FM_STOCK_UNIT).toString(), div_code, sale_custom_code);
				shoppingCart.setRefund_status(jsobj.get("refund_status").toString());
				list.add(shoppingCart);
			}

			if (flag.equals("1")) { // 결제대기

				// "우자" 결제 상태로 인한 코드
				if (syncPaymentStatus.equals("1")) { // 결제 성공

					itemView.text_orderStatus.setText(context.getString(com.rs.mobile.wportal.R.string.common_text076));

					itemView.timer_textview.setVisibility(View.GONE);

					itemView.paymentBtn.setVisibility(View.GONE);

					itemView.cancelBtn.setVisibility(View.VISIBLE);

					itemView.cancelBtn.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.cancel_order));

					itemView.cancelBtn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							D.showDialog(context, -1, context.getString(com.rs.mobile.wportal.R.string.common_text001),
									context.getString(com.rs.mobile.wportal.R.string.common_text015),
									context.getString(com.rs.mobile.wportal.R.string.common_text003), new OnClickListener() {

										@Override
										public void onClick(View v) {
											D.alertDialog.dismiss();
											cancelOrder(order_id, position);
										}
									}, context.getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

										@Override
										public void onClick(View v) {
											D.alertDialog.dismiss();
										}
									});

						}
					});

				} else { // 결재가 완료되지 않음

					final Bundle bundle = new Bundle();

					bundle.putString(C.KEY_JSON_FM_ORDERCODE, jsonObject.getString("order_code"));

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					String stringTime = jsonObject.getString(C.KEY_JSON_FM_INVALID_DATE);

					stringTime = stringTime.replace("T", " ");

					Date target = null;

					target = dateFormat.parse(stringTime);

					Date servicedate = dateFormat.parse(serverTime);

					long diff = target.getTime() - servicedate.getTime();

					if (diff > 0) {

						// 결제 가능한 시간

						itemView.text_orderStatus.setText(context.getString(com.rs.mobile.wportal.R.string.common_text077));

						itemView.timer_textview.setVisibility(View.VISIBLE);
						itemView.timer_textview.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Log.d("MyOrderAdapter", "timer_textview");
								Bundle bundle = new Bundle();

								bundle.putParcelableArrayList("goods", list);

								bundle.putParcelableArrayList("attachment", listAttach);
								bundle.putString("total", totolprice);
								bundle.putString("onCartProcess", "false");
								PageUtil.jumpTo(context, SmConfirmActivity.class, bundle);
								context.finish();

								/*if (UiUtil.checkLogin(SmGoodsDetailActivity.this)) {

								}*/

/*								PaymentUtil.showKeyBoard(context, order_id, totolprice, point, realPrice, "0",
										new OnPaymentResultListener() {

											@Override
											public void onResult(JSONObject data) {

												// stub
												String status;
												try {
													status = data.get("status").toString();

													if (status.equals("1")) {
														JSONObject data1 = data.getJSONObject("data");
														String order_no1 = data1.get("order_no").toString();
														T.showToast(context,
																context.getString(com.rs.mobile.wportal.R.string.common_text005));

														smConfirmStatus1(order_no1);

													} else {

														T.showToast(context, data.get("msg").toString());

														// method
														// stub
														if (status.equals("1101")) { // wrong
																						// pw
															showDialogTip(data.get("msg").toString(),
																	context.getString(com.rs.mobile.wportal.R.string.common_text020));
														} else if (status.equals("1711")) { // not
																							// enough
																							// money
															showDialogTip(data.get("msg").toString(),
																	context.getString(com.rs.mobile.wportal.R.string.common_text021));
														} else { //
															// context.finish();
															// PageUtil.jumpToWithFlag(context,
															// MyOrderActivity.class);
														}
													}
												} catch (JSONException e) {
													// block
													e.printStackTrace();
												}

											}

											@Override
											public void onFail(String msg) {
												// stub

												PageUtil.jumpToWithFlag(context, MyOrderActivity.class);

											}
										});
										*/
							}
						});

						itemView.paymentBtn.setVisibility(View.GONE);

						itemView.cancelBtn.setVisibility(View.VISIBLE);

						itemView.cancelBtn.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.cancel_order));

						itemView.cancelBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								D.showDialog(context, -1, context.getString(com.rs.mobile.wportal.R.string.common_text001),
										context.getString(com.rs.mobile.wportal.R.string.common_text015),
										context.getString(com.rs.mobile.wportal.R.string.common_text003), new OnClickListener() {

											@Override
											public void onClick(View v) {
												// stub
												D.alertDialog.dismiss();
												cancelOrder(order_id, position);
											}
										}, context.getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

											@Override
											public void onClick(View v) {
												// stub
												D.alertDialog.dismiss();
											}
										});

							}
						});

						itemView.timer_textview.setTimes(diff);

						itemView.timer_textview.setDetail(context.getString(com.rs.mobile.wportal.R.string.rt_text053));

						if (!itemView.timer_textview.isRun()) {

							itemView.timer_textview.start(new TimerTextView.OnTimerOverListener() {

								@Override
								public void onOver() {

									notifyDataSetChanged();

								}
							});

						}

					} else {

						// 기간 만료
						itemView.timer_textview.setVisibility(View.GONE);

						itemView.cancelBtn.setVisibility(View.GONE);

						itemView.text_orderStatus.setText(context.getString(com.rs.mobile.wportal.R.string.common_text024));

					}

				}

			} else if (flag.equals("3")) { // 수령대기

				itemView.text_orderStatus.setText(context.getString(com.rs.mobile.wportal.R.string.common_text078));

				itemView.cancelBtn.setVisibility(View.GONE);

				itemView.timer_textview.setVisibility(View.GONE);

				itemView.paymentBtn.setVisibility(View.VISIBLE);

				itemView.paymentBtn.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.confirm_pick));

				itemView.paymentBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						D.showDialog(context, -1, context.getString(com.rs.mobile.wportal.R.string.common_text001),
								context.getString(com.rs.mobile.wportal.R.string.common_text080), context.getString(com.rs.mobile.wportal.R.string.common_text003),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										D.alertDialog.dismiss();
										confirmGetGoods(order_id, "false");
									}
								}, context.getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										D.alertDialog.dismiss();
									}
								});

					}
				});

			} else if (flag.equals("5")) { // 평가대기
				itemView.text_orderStatus.setText(R.string.order_finish);
				if (flag_ass.equals("1")) { // 평가 대기

					// itemView.text_orderStatus.setText(context.getString(R.string.common_text079));

					itemView.cancelBtn.setVisibility(View.GONE);

					itemView.timer_textview.setVisibility(View.GONE);

					itemView.paymentBtn.setVisibility(View.VISIBLE);

					itemView.paymentBtn.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.sm_text_evaluate));

					itemView.paymentBtn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							Bundle bundle = new Bundle();

							bundle.putString(C.KEY_JSON_FM_ORDERCODE, order_id);

							bundle.putParcelableArrayList("goods", list);

							PageUtil.jumpTo(context, EvaluateListActivity.class, bundle);

						}
					});

				} else { // 평가완료

					itemView.cancelBtn.setVisibility(View.GONE);

					itemView.timer_textview.setVisibility(View.GONE);

					itemView.paymentBtn.setVisibility(View.GONE);

				}

			} else if (flag.equals("4")) { // 취소된 주문

				itemView.text_orderStatus.setText(context.getString(com.rs.mobile.wportal.R.string.common_text082));

				itemView.cancelBtn.setVisibility(View.GONE);

				itemView.timer_textview.setVisibility(View.GONE);

				itemView.paymentBtn.setVisibility(View.GONE);

			} else if (flag.equals("2")) { // 결제 완료된 주문

				itemView.text_orderStatus.setText(context.getString(com.rs.mobile.wportal.R.string.common_text109));

				itemView.cancelBtn.setVisibility(View.VISIBLE);

				itemView.timer_textview.setVisibility(View.GONE);

				itemView.paymentBtn.setVisibility(View.GONE);

				itemView.cancelBtn.setText(context.getResources().getString(com.rs.mobile.wportal.R.string.cancel_order));

				itemView.cancelBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						// TODO Auto-generated method stub
						D.showDialog(context, -1, context.getString(com.rs.mobile.wportal.R.string.common_text001),
								context.getString(com.rs.mobile.wportal.R.string.common_text015), context.getString(com.rs.mobile.wportal.R.string.common_text003),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										D.alertDialog.dismiss();
										cancelOrder(order_id, position);
									}
								}, context.getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										D.alertDialog.dismiss();
									}
								});

					}
				});

			} else if (flag.equals("6")) {
				itemView.text_orderStatus.setText(R.string.order_finish);
			}

			itemView.listView.setAdapter(new confirmOrderGoodAdapter(list, context));

			itemView.listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

					try {
						Log.d("MyOrderAdapter", "timer_textview");
						Bundle bundle = new Bundle();

						bundle.putString(C.KEY_JSON_FM_ORDERCODE, jsonObject.getString("order_code"));

						if (flag.equals("1")) { // 미결제 상태

							bundle.putParcelableArrayList("goods", list);

							PageUtil.jumpTo(context, OrderDetailPayActivity.class, bundle);

						} else {

							bundle.putString("flag_ass", flag_ass);

							bundle.putString("flag", flag);

							bundle.putParcelableArrayList("goods", list);

							PageUtil.jumpTo(context, OrderDetailActivity.class, bundle);

						}

					} catch (Exception e) {

						L.e(e);

					}

				}
			});
			itemView.payment_btnAgain.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d("MyOrderAdapter", "payment_btnAgain");
					Bundle bundle = new Bundle();

					bundle.putParcelableArrayList("goods", list);
					bundle.putParcelableArrayList("attachment", listAttach);
					bundle.putString("total", totolprice);
					bundle.putString("onCartProcess", "false");
					PageUtil.jumpTo(context, SmConfirmActivity.class, bundle);
					context.finish();
				}
			});
			if (buyAgain) {
				itemView.payment_btnAgain.setVisibility(View.VISIBLE);

				itemView.payment_btnAgain.setText(R.string.payment_again);

			} else {
				itemView.payment_btnAgain.setVisibility(View.GONE);
			}

			((BaseActivity) context).setListViewHeight(itemView.listView);

		} catch (Exception e) {

			L.e(e);

		}

		return convertView;
	}

	/**
	 * 상품 수령
	 * 
	 * @param order_id
	 *            : position
	 */
	private void confirmGetGoods(final String order_id, String hasRefundConfirm) {

		try {

			Map<String, String> params = new HashMap<String, String>();

			params.put("order_num", order_id);

			params.put("hasRefundConfirm", hasRefundConfirm);
			OkHttpHelper okHttpHelper = new OkHttpHelper(context);

			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					try {

						if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("1")) {

							T.showToast(context, data.getString("message"));
							fragment.getUserOrderList();

							notifyDataSetInvalidated();

						} else if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("-16421")) {

							D.showDialog(context, -1, context.getString(com.rs.mobile.wportal.R.string.common_text001),
									data.getString("message"), context.getString(com.rs.mobile.wportal.R.string.common_text003),
									new OnClickListener() {

										@Override
										public void onClick(View v) {
											// method stub
											D.alertDialog.dismiss();
											confirmGetGoods(order_id, "true");
										}
									}, context.getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

										@Override
										public void onClick(View v) {
											// method stub
											D.alertDialog.dismiss();
										}
									});

						} else {
							T.showToast(context, data.optString("message"));
						}

					} catch (Exception e) {

						L.e(e);

					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				}

			}, Constant.SM_BASE_URL + Constant.CONFIRM_ORDER_DELIVERY, params);

		} catch (Exception e) {

			L.e(e);

		}
	}

	/**
	 * 
	 * @param order_id
	 */
	private void remindOrder(String order_id) {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ORDERCODE, order_id);
			OkHttpHelper okHttpHelper = new OkHttpHelper(context);
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
							T.showToast(context, context.getResources().getString(com.rs.mobile.wportal.R.string.success));
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
	 * 주문 취
	 * 
	 * @param order_id
	 * @param i
	 */
	private void cancelOrder(String order_id, final int i) {

		try {

			Map<String, String> params = new HashMap<String, String>();

			params.put("order_num", order_id);

			OkHttpHelper okHttpHelper = new OkHttpHelper(context);

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

							T.showToast(context, data.getString("message"));
							fragment.getUserOrderList();

							//fragment.onRefresh();

							notifyDataSetInvalidated();

						} else {
							fragment.getUserOrderList();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block

						L.e(e);

					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					fragment.getUserOrderList();
				}
			}, Constant.SM_BASE_URL + Constant.CANCEL_ORDER, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void deleteOrder(String ordernum) {
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
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
					T.showToast(context, data.getString("message"));
					fragment.getUserOrderList();
					//fragment.onRefresh();

					notifyDataSetInvalidated();
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

	private void showDialogTip(String tips1, String tips2) {

		D.showDialog(context, -1, context.getResources().getString(com.rs.mobile.wportal.R.string.sm_text_tips), tips1, tips2,
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						D.alertDialog.dismiss();
						UtilClear.IntentToLongLiao(context,
								"cn.ycapp.im.ui.mywallet.MyWalletActivity","");
					}
				}, context.getResources().getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						D.alertDialog.dismiss();
						context.finish();
						PageUtil.jumpToWithFlag(context, MyOrderActivity.class);
					}
				});

	}

	public void smConfirmStatus1(String order_num) {

		HashMap<String, String> params = new HashMap<>();
		params.put("order_num", order_num);
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				//fragment.getUserOrderList();
				fragment.onRefresh();

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.PAYMENT_STATUSSYNC, params);
	}
}
