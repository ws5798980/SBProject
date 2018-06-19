
package com.rs.mobile.wportal.adapter.dp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.wportal.activity.sm.EvaluateListActivity;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.PaymentUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.dp.DpConfirmActivity;
import com.rs.mobile.wportal.activity.dp.DpMyOrderActivity;
import com.rs.mobile.wportal.activity.dp.DpOrderDetailActivity;
import com.rs.mobile.wportal.activity.dp.DpOrderDetailPayActivity;
import com.rs.mobile.wportal.fragment.dp.DpMyOrderCommonFragment;
import com.rs.mobile.wportal.view.TimerTextView;
import com.rs.mobile.wportal.view.TimerTextView.OnTimerOverListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

@SuppressLint("NewApi")
public class DpMyOrderAdapter extends BaseAdapter {

	private Activity context;

	private JSONArray jsonArray;

	private DpMyOrderCommonFragment fragment;

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

	public DpMyOrderAdapter(Activity context, JSONArray jsonArray, DpMyOrderCommonFragment fragment,
			String serverTime) {
		// TODO Auto-generated constructor stub

		this.context = context;

		this.jsonArray = jsonArray;

		this.fragment = fragment;

		this.serverTime = serverTime;

	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return jsonArray.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		try {

			return jsonArray.get(position);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			L.e(e);

		}

		return null;
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		try {

			ViewHolder itemView = null;

			if (convertView == null) {

				itemView = new ViewHolder();

				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_myorder_common,
						parent, false);

				itemView.listView = (ListView) convertView.findViewById(R.id.listView);

				itemView.text_orderStatus = (TextView) convertView.findViewById(R.id.text_orderStatus);

				itemView.text_orderTime = (TextView) convertView.findViewById(R.id.text_orderTime);

				itemView.text_totoal = (TextView) convertView.findViewById(R.id.text_totoal);

				itemView.cancelBtn = (TextView) convertView.findViewById(R.id.cancel_btn);

				itemView.paymentBtn = (TextView) convertView.findViewById(R.id.payment_btn);

				itemView.payment_btnAgain = (TextView) convertView.findViewById(R.id.payment_btnAgain);

				itemView.timer_textview = (TimerTextView) convertView.findViewById(R.id.payment_btn1);

				itemView.delete_btn = (TextView) convertView.findViewById(R.id.delete_btn);

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
					context.getString(R.string.common_text075) + jsonObject.get(C.KEY_JSON_FM_ORDERDATE).toString());

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
						// TODO Auto-generated method stub

						// TODO Auto-generated method stub

						// TODO Auto-generated method stub
						D.showDialog(context, -1, context.getString(R.string.common_text001),
								context.getString(R.string.common_text002), context.getString(R.string.common_text003),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										D.alertDialog.dismiss();
										deleteOrder(order_id);
									}
								}, context.getString(R.string.cancel), new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
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
					// TODO Auto-generated method stub

					try {

						Bundle bundle = new Bundle();

						bundle.putString(C.KEY_JSON_FM_ORDERCODE, jsonObject.getString("order_code"));

						if (flag.equals("1")) {

							PageUtil.jumpTo(context, DpOrderDetailPayActivity.class, bundle);

							((Activity) context).finish();

						} else {

							bundle.putString("flag_ass", flag_ass);

							bundle.putString("flag", flag);

							PageUtil.jumpTo(context, DpOrderDetailActivity.class, bundle);

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

			itemView.text_totoal.setText(context.getResources().getString(R.string.order_bewrite1)
					+ jsonObject.get(C.KEY_JSON_FM_ORDERNUM) + context.getResources().getString(R.string.order_bewrite2)
					+ jsonObject.get(C.KEY_JSON_FM_ORDERPRICE));

			JSONArray arr = jsonObject.getJSONArray(C.KEY_JSON_FM_ORDER_GROUPGOODLIST);

			final ArrayList<ShoppingCart> list = new ArrayList<ShoppingCart>();

			for (int i = 0; i < arr.length(); i++) {

				JSONObject jsobj = arr.getJSONObject(i);

				ShoppingCart shoppingCart = new ShoppingCart(jsobj.get(C.KEY_JSON_FM_ITEM_CODE).toString(),
						jsobj.get(C.KEY_JSON_FM_ITEM_NAME).toString(),
						Float.parseFloat(jsobj.get(C.KEY_JSON_FM_ITEM_PRICE).toString()),
						jsobj.get(C.KEY_JSON_FM_ITEM_IMAGE_URL).toString(), jsobj.getInt(C.KEY_JSON_FM_ITEM_QUANTITI),
						false, jsobj.get(C.KEY_JSON_FM_STOCK_UNIT).toString(), div_code, "");
				shoppingCart.setRefund_status(jsobj.get("refund_status").toString());
				list.add(shoppingCart);
			}

			if (flag.equals("1")) { // 결제대기

				// "우자" 결제 상태로 인한 코드
				if (syncPaymentStatus.equals("1")) { // 결제 성공

					itemView.text_orderStatus.setText(context.getString(R.string.common_text076));

					itemView.timer_textview.setVisibility(View.GONE);

					itemView.paymentBtn.setVisibility(View.GONE);

					itemView.cancelBtn.setVisibility(View.VISIBLE);

					itemView.cancelBtn.setText(context.getResources().getString(R.string.cancel_order));

					itemView.cancelBtn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							// TODO Auto-generated method stub
							D.showDialog(context, -1, context.getString(R.string.common_text001),
									context.getString(R.string.common_text015),
									context.getString(R.string.common_text003), new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											D.alertDialog.dismiss();
											cancelOrder(order_id, position);
										}
									}, context.getString(R.string.cancel), new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
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

						itemView.text_orderStatus.setText(context.getString(R.string.common_text077));

						itemView.timer_textview.setVisibility(View.VISIBLE);
						itemView.timer_textview.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								//결제 모듈
							}
						});

						itemView.paymentBtn.setVisibility(View.GONE);

						itemView.cancelBtn.setVisibility(View.VISIBLE);

						itemView.cancelBtn.setText(context.getResources().getString(R.string.cancel_order));

						itemView.cancelBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								// TODO Auto-generated method stub
								D.showDialog(context, -1, context.getString(R.string.common_text001),
										context.getString(R.string.common_text015),
										context.getString(R.string.common_text003), new OnClickListener() {

											@Override
											public void onClick(View v) {
												// TODO Auto-generated method
												// stub
												D.alertDialog.dismiss();
												cancelOrder(order_id, position);
											}
										}, context.getString(R.string.cancel), new OnClickListener() {

											@Override
											public void onClick(View v) {
												// TODO Auto-generated method
												// stub
												D.alertDialog.dismiss();
											}
										});

							}
						});

						itemView.timer_textview.setTimes(diff);

						itemView.timer_textview.setDetail(context.getString(R.string.rt_text053));

						if (!itemView.timer_textview.isRun()) {

							itemView.timer_textview.start(new OnTimerOverListener() {

								@Override
								public void onOver() {
									// TODO Auto-generated method stub

									notifyDataSetChanged();

								}
							});

						}

					} else {

						// 기간 만료
						itemView.timer_textview.setVisibility(View.GONE);

						itemView.cancelBtn.setVisibility(View.GONE);

						itemView.text_orderStatus.setText(context.getString(R.string.common_text024));

					}

				}

			} else if (flag.equals("3")) { // 수령대기

				itemView.text_orderStatus.setText(context.getString(R.string.common_text078));

				itemView.cancelBtn.setVisibility(View.GONE);

				itemView.timer_textview.setVisibility(View.GONE);

				itemView.paymentBtn.setVisibility(View.VISIBLE);

				itemView.paymentBtn.setText(context.getResources().getString(R.string.confirm_pick));

				itemView.paymentBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						D.showDialog(context, -1, context.getString(R.string.common_text001),
								context.getString(R.string.common_text080), context.getString(R.string.common_text003),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										D.alertDialog.dismiss();
										confirmGetGoods(order_id, "false");
									}
								}, context.getString(R.string.cancel), new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										D.alertDialog.dismiss();
									}
								});

					}
				});

			} else if (flag.equals("5")) { // 평가대기
				itemView.text_orderStatus.setText("交易完成");
				if (flag_ass.equals("1")) { // 평가 대기

					itemView.cancelBtn.setVisibility(View.GONE);

					itemView.timer_textview.setVisibility(View.GONE);

					itemView.paymentBtn.setVisibility(View.VISIBLE);

					itemView.paymentBtn.setText(context.getResources().getString(R.string.sm_text_evaluate));

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

					itemView.text_orderStatus.setText("交易完成");

					itemView.cancelBtn.setVisibility(View.GONE);

					itemView.timer_textview.setVisibility(View.GONE);

					itemView.paymentBtn.setVisibility(View.GONE);

				}

			} else if (flag.equals("4")) { // 취소된 주문

				itemView.text_orderStatus.setText(context.getString(R.string.common_text082));

				itemView.cancelBtn.setVisibility(View.GONE);

				itemView.timer_textview.setVisibility(View.GONE);

				itemView.paymentBtn.setVisibility(View.GONE);

			} else if (flag.equals("2")) { // 결제 완료된 주문

				itemView.text_orderStatus.setText(context.getString(R.string.common_text109));

				itemView.cancelBtn.setVisibility(View.VISIBLE);

				itemView.timer_textview.setVisibility(View.GONE);

				itemView.paymentBtn.setVisibility(View.GONE);

				itemView.cancelBtn.setText(context.getResources().getString(R.string.cancel_order));

				itemView.cancelBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						// TODO Auto-generated method stub
						D.showDialog(context, -1, context.getString(R.string.common_text001),
								context.getString(R.string.common_text015), context.getString(R.string.common_text003),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										D.alertDialog.dismiss();
										cancelOrder(order_id, position);
									}
								}, context.getString(R.string.cancel), new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										D.alertDialog.dismiss();
									}
								});

					}
				});

			} else if (flag.equals("6")) {
				itemView.text_orderStatus.setText("交易完成");
			}

			itemView.listView.setAdapter(new DpConfirmOrderGoodAdapter(list, context));

			itemView.listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					// TODO Auto-generated method stub

					try {

						Bundle bundle = new Bundle();

						bundle.putString(C.KEY_JSON_FM_ORDERCODE, jsonObject.getString("order_code"));

						if (flag.equals("1")) { // 미결제 상태

							bundle.putParcelableArrayList("goods", list);

							PageUtil.jumpTo(context, DpOrderDetailPayActivity.class, bundle);

						} else {

							bundle.putString("flag_ass", flag_ass);

							bundle.putString("flag", flag);

							bundle.putParcelableArrayList("goods", list);

							PageUtil.jumpTo(context, DpOrderDetailActivity.class, bundle);

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
					Bundle bundle = new Bundle();

					bundle.putParcelableArrayList("goods", list);

					bundle.putParcelableArrayList("attachment", listAttach);
					bundle.putString("total", totolprice);
					bundle.putString("onCartProcess", "false");
					bundle.putString(C.KEY_DIV_CODE, div_code);
					PageUtil.jumpTo(context, DpConfirmActivity.class, bundle);
					context.finish();

				}
			});
			if (buyAgain) {
				itemView.payment_btnAgain.setVisibility(View.VISIBLE);

				itemView.payment_btnAgain.setText("再次购买");

			} else {
				itemView.payment_btnAgain.setVisibility(View.GONE);
			}

			((BaseActivity) context).setListViewHeight(itemView.listView);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			L.e(e);

		}

		return convertView;
	}

	/**
	 * 상품 수령
	 * 
	 * @param order_id
	 * @param i
	 *            : position
	 */
	private void confirmGetGoods(final String order_id, String hasRefundConfirm) {

		try {

			Map<String, String> params = new HashMap<String, String>();

			params.put("order_num", order_id);

			params.put("hasRefundConfirm", hasRefundConfirm);

			OkHttpHelper okHttpHelper = new OkHttpHelper(context);

			okHttpHelper.addSMPostRequest(new CallbackLogic() {

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

							notifyDataSetInvalidated();

						} else if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("-16421")) {

							// TODO Auto-generated method stub

							// TODO Auto-generated method stub
							D.showDialog(context, -1, context.getString(R.string.common_text001),
									data.getString("message"), context.getString(R.string.common_text003),
									new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											D.alertDialog.dismiss();
											confirmGetGoods(order_id, "true");
										}
									}, context.getString(R.string.cancel), new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated
											// method stub
											D.alertDialog.dismiss();
										}
									});

						} else {
							T.showToast(context, data.optString("message"));
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

	/**
	 * 
	 * @param order_id
	 */
	private void remindOrder(String order_id) {

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put(C.KEY_JSON_FM_ORDERCODE, order_id);
			OkHttpHelper okHttpHelper = new OkHttpHelper(context);
			okHttpHelper.addSMPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

					// TODO Auto-generated method stub
					try {
						if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("1")) {
							T.showToast(context, context.getResources().getString(R.string.success));
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

			okHttpHelper.addSMPostRequest(new CallbackLogic() {

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

							notifyDataSetInvalidated();

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
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		HashMap<String, String> paramsKeyValue = new HashMap<>();
		paramsKeyValue.put("order_num", ordernum);
		okHttpHelper.addSMPostRequest(new CallbackLogic() {

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

		D.showDialog(context, -1, context.getResources().getString(R.string.sm_text_tips), tips1, tips2,
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						D.alertDialog.dismiss();
						
						UtilClear.IntentToLongLiao(context,
								"cn.ycapp.im.ui.mywallet.MyWalletActivity","");
					}
				}, context.getResources().getString(R.string.cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						D.alertDialog.dismiss();
						context.finish();
						PageUtil.jumpToWithFlag(context, DpMyOrderActivity.class);
					}
				});

	}

	public void smConfirmStatus1(String order_num) {

		HashMap<String, String> params = new HashMap<>();
		params.put("order_num", order_num);
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		okHttpHelper.addSMPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				fragment.getUserOrderList();

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.PAYMENT_STATUSSYNC, params);
	}
}
