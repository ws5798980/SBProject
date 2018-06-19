package com.rs.mobile.wportal.adapter.rt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.PaymentUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.rt.RtETBoardActivity;
import com.rs.mobile.wportal.activity.rt.RtEvaluateActivity;
import com.rs.mobile.wportal.activity.rt.RtReserveActivity;
import com.rs.mobile.wportal.activity.rt.RtSellerDetailActivity;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;
import com.rs.mobile.wportal.adapter.CommonAdapter;
import com.rs.mobile.wportal.adapter.CommonViewHolder;
import com.rs.mobile.wportal.biz.rt.Reserve;
import com.rs.mobile.wportal.fragment.rt.RtReserveClassifyFragment;
import com.rs.mobile.wportal.rt.RTUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class RtReserveClassifyAdapter extends CommonAdapter<Reserve> {

	public static final int TYPE_STAY_PAY = 1;// 待付款
	public static final int TYPE_STAY_CHOOSEDMENU = 2;// 已点菜
	public static final int TYPE_STAY_RESERVED = 3;// 已预约
	public static final int TYPE_STAY_NO_COMMENT = 4;// 已完成(未评价)
	public static final int TYPE_STAY_COMMENTED = 5;// 已完成(已评价)
	public static final int TYPE_STAY_REFUNDING = 6;// 退款中
	public static final int TYPE_STAY_REFUNDCOMPLETED = 7;// 退款完成

	public static final int TYPE_INVALID = 0;// 无效type
	private List<Reserve> datas;

	public RtReserveClassifyAdapter(Context context, List<Reserve> datas, int layoutId) {
		super(context, datas, layoutId);
		this.datas = datas;
	}

	@Override
	protected void convert(final CommonViewHolder viewHolder, final Reserve reserve) {
		int type = getItemViewType(viewHolder.getPosition());

		final String groupYN = reserve.getGroupYN();

		final String groupId = reserve.getGroupId();

		final String restaurantCode = reserve.getRestaurantCode();

		// String groupId = reserve.getGroupId();

		String restaurantName = reserve.getRestaurantName() == null ? "" : reserve.getRestaurantName();

		if (groupYN != null && groupYN.equals("Y")) {// && groupId != null &&
														// !groupId.equals(""))
														// {

			restaurantName = restaurantName + "(" + mContext.getResources().getString(com.rs.mobile.wportal.R.string.rt_eating_together)
					+ ")";

		}

		viewHolder.setText(com.rs.mobile.wportal.R.id.tv_restaurantname, restaurantName).setText(com.rs.mobile.wportal.R.id.tv_reservestatus, type == TYPE_STAY_PAY
				? mContext.getString(com.rs.mobile.wportal.R.string.common_text092) : // 미결제
				type == TYPE_STAY_CHOOSEDMENU ? mContext.getString(com.rs.mobile.wportal.R.string.common_text093) : // 주문된
																								// 오더
						type == TYPE_STAY_RESERVED ? mContext.getString(com.rs.mobile.wportal.R.string.common_text094) : // 예약된
																									// 오더
								type == TYPE_STAY_NO_COMMENT ? mContext.getString(com.rs.mobile.wportal.R.string.common_text095) : // 완료된
																												// 오더
										type == TYPE_STAY_COMMENTED ? mContext.getString(com.rs.mobile.wportal.R.string.common_text095) : // 완료된
																													// 오더
												type == TYPE_STAY_REFUNDING
														? mContext.getString(com.rs.mobile.wportal.R.string.common_text096) : // 환불중
														type == TYPE_STAY_REFUNDCOMPLETED
																? mContext.getString(com.rs.mobile.wportal.R.string.common_text097) : // 환불완료
																"")
				.setText(com.rs.mobile.wportal.R.id.tv_date, reserve.getDate() == null ? ""
						: reserve.getDate())
				.setText(com.rs.mobile.wportal.R.id.tv_reservedate, reserve.getReserveDate() == null ? ""
						: reserve.getReserveDate());
		// 状态
		TextView tv_reservestatus = viewHolder.getView(com.rs.mobile.wportal.R.id.tv_reservestatus);
		// 金额
		LinearLayout ll_amount = viewHolder.getView(com.rs.mobile.wportal.R.id.ll_amount);
		TextView tv_amount_title = viewHolder.getView(com.rs.mobile.wportal.R.id.tv_amount_title);
		TextView tv_amount = viewHolder.getView(com.rs.mobile.wportal.R.id.tv_amount);
		// 就餐时间
		LinearLayout ll_reservedate = viewHolder.getView(com.rs.mobile.wportal.R.id.ll_reservedate);
		// 就餐人数
		LinearLayout ll_personcount = viewHolder.getView(com.rs.mobile.wportal.R.id.ll_personcount);
		TextView tv_personcount = viewHolder.getView(com.rs.mobile.wportal.R.id.tv_personcount);
		// 点餐详情
		LinearLayout ll_ordermenu = viewHolder.getView(com.rs.mobile.wportal.R.id.ll_ordermenu);
		TextView tv_ordermenu = viewHolder.getView(com.rs.mobile.wportal.R.id.tv_ordermenu);

		// Bottom
		LinearLayout ll_bottom = viewHolder.getView(com.rs.mobile.wportal.R.id.ll_bottom);
		// 提示
		TextView tv_hint = viewHolder.getView(com.rs.mobile.wportal.R.id.tv_hint);
		// 按钮组合
		TextView tv_btn1 = viewHolder.getView(com.rs.mobile.wportal.R.id.tv_btn1);
		TextView tv_btn2 = viewHolder.getView(com.rs.mobile.wportal.R.id.tv_btn2);
		TextView tv_btn3 = viewHolder.getView(com.rs.mobile.wportal.R.id.tv_btn3);

		final BaseActivity activity = (BaseActivity) mContext;

		switch (type) {
		case TYPE_STAY_PAY:// 未付款 미결제
			tv_reservestatus.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_rt_reserve_status_pay);

			ll_amount.setVisibility(View.GONE);

			ll_personcount.setVisibility(View.VISIBLE);
			tv_personcount.setText(reserve.getPersonCount() == 0 ? mContext.getString(com.rs.mobile.wportal.R.string.common_text098)
					: reserve.getPersonCount() + mContext.getString(com.rs.mobile.wportal.R.string.ht_text_People));

			if (!TextUtils.isEmpty(reserve.getOrderNum())) {
				ll_ordermenu.setVisibility(View.VISIBLE);
				tv_ordermenu.setText(reserve.getOrderMenu() == null ? "" : reserve.getOrderMenu());
			} else {
				ll_ordermenu.setVisibility(View.GONE);
			}

			ll_bottom.setVisibility(View.VISIBLE);
			tv_hint.setText(mContext.getString(com.rs.mobile.wportal.R.string.rt_list_payment_01) + reserve.getPersonCount() +
					mContext.getString(com.rs.mobile.wportal.R.string.rt_list_payment_03) + reserve.getRealAmount() + mContext.getString(com.rs.mobile.wportal.R.string.rt_list_payment_04));
			tv_btn1.setVisibility(View.GONE);
			tv_btn2.setVisibility(View.VISIBLE);
			tv_btn2.setText(mContext.getString(com.rs.mobile.wportal.R.string.common_text014));
			tv_btn3.setVisibility(View.VISIBLE);
			tv_btn3.setText(mContext.getString(com.rs.mobile.wportal.R.string.rt_text053));
			tv_btn3.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_rt_reserve_bottombtn_red);
			tv_btn2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					cancelReserve(mContext.getString(com.rs.mobile.wportal.R.string.common_text014),
							mContext.getString(com.rs.mobile.wportal.R.string.common_text048), mContext.getString(com.rs.mobile.wportal.R.string.common_text014),
							mContext.getString(com.rs.mobile.wportal.R.string.common_text049), reserve.getReserveId());
				}
			});
			tv_btn3.setTextColor(mContext.getResources().getColor(com.rs.mobile.wportal.R.color.backgroundColor_rt_red_button));
			tv_btn3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (groupYN != null && groupYN.equals("Y") && groupId != null && !groupId.equals("")) {

						Intent i = new Intent(mContext, RtETBoardActivity.class);
						i.putExtra("groupID", groupId);
						i.putExtra("restaurantCode", restaurantCode);
						activity.startActivity(i);

					} else {
						
						//즉시결제
					
//						Intent payIntent = new Intent(mContext, OrderDetailActivity.class);
//						payIntent.putExtra("orderNumber", reserve.getOrderNum());
//						activity.startActivity(payIntent);
						
						// 우성페이
						PaymentUtil.showKeyBoard(mContext,
								// orderNum
								reserve.getOrderNum(),
								// 쿠폰, 포인트 적용 전
								"" + reserve.getAmount(),
								// 포인트
								"" + reserve.getPointAmount(), 
								// 쿠폰 포인트 적용 후
								"" + reserve.getRealAmount(),
								// 쿠폰
								"" + reserve.getCouponsAmount(), new OnPaymentResultListener() {

									@Override
									public void onResult(JSONObject data) {
										// TODO Auto-generated method stub

										try {

											String status = data.get("status").toString();

											if (status.equals("1")) {

												T.showToast(mContext, mContext.getString(com.rs.mobile.wportal.R.string.common_text005));

												addAdvance("Y", data.getJSONObject("data").getString("auth_no"), reserve);

											} else {

												T.showToast(mContext, data.get("msg").toString());

												// TODO Auto-generated
												// method stub
												if (status.equals("1101")) { // wrong pw

													// showErrorDialog(data.get("msg").toString(),"设置支付密码");

												} else if (status.equals("10")) { //

													// showErrorDialog(data.get("msg").toString(),"确定");

												} else if (status.equals("1711")) { // not enough money

													showErrorDialog(data.get("msg").toString(), mContext.getString(com.rs.mobile.wportal.R.string.common_text021));

												}

											}

										} catch (Exception e) {

											L.e(e);

										}

									}

									@Override
									public void onFail(String msg) {
										// TODO Auto-generated method stub
										
										T.showToast(mContext, msg);

									}
								});

					}
				}
			});

			break;

		case TYPE_STAY_CHOOSEDMENU:// 已点菜 주문된 오더
			tv_reservestatus.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_rt_reserve_status_choosedmenu);

			ll_amount.setVisibility(View.VISIBLE);
			tv_amount_title.setText(mContext.getString(com.rs.mobile.wportal.R.string.common_text101));
			tv_amount.setText(reserve.getRealAmount() + mContext.getString(com.rs.mobile.wportal.R.string.money));

			ll_personcount.setVisibility(View.VISIBLE);
			tv_personcount.setText(reserve.getPersonCount() == 0 ? mContext.getString(com.rs.mobile.wportal.R.string.common_text098)
					: reserve.getPersonCount() + mContext.getString(com.rs.mobile.wportal.R.string.ht_text_People));

			if (!TextUtils.isEmpty(reserve.getOrderNum())) {
				ll_ordermenu.setVisibility(View.VISIBLE);
				tv_ordermenu.setText(reserve.getOrderMenu() == null ? "" : reserve.getOrderMenu());
			} else {
				ll_ordermenu.setVisibility(View.GONE);
			}

			ll_bottom.setVisibility(View.VISIBLE);
			tv_hint.setText(mContext.getString(com.rs.mobile.wportal.R.string.common_text102));
			tv_btn1.setVisibility(View.GONE);
			tv_btn2.setVisibility(View.GONE);
			tv_btn3.setVisibility(View.GONE);

			break;

		case TYPE_STAY_RESERVED:// 已预约 예약된
			tv_reservestatus.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_rt_reserve_status_reserved);

			ll_amount.setVisibility(View.GONE);

			ll_personcount.setVisibility(View.VISIBLE);
			tv_personcount.setText(reserve.getPersonCount() == 0 ? mContext.getString(com.rs.mobile.wportal.R.string.common_text098)
					: reserve.getPersonCount() + mContext.getString(com.rs.mobile.wportal.R.string.ht_text_People));

			ll_ordermenu.setVisibility(View.GONE);

			ll_bottom.setVisibility(View.VISIBLE);
			tv_hint.setText(mContext.getString(com.rs.mobile.wportal.R.string.common_text103));
			tv_btn1.setVisibility(View.GONE);
			tv_btn2.setVisibility(View.GONE);
			tv_btn3.setVisibility(View.GONE);

			break;

		case TYPE_STAY_NO_COMMENT:// 已完成(未评价) 완료된 미평가
			tv_reservestatus.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_rt_reserve_status_nocomment);

			ll_amount.setVisibility(View.GONE);

			ll_personcount.setVisibility(View.VISIBLE);
			tv_personcount.setText(reserve.getPersonCount() == 0 ? mContext.getString(com.rs.mobile.wportal.R.string.common_text098)
					: reserve.getPersonCount() + mContext.getString(com.rs.mobile.wportal.R.string.ht_text_People));

			if (!TextUtils.isEmpty(reserve.getOrderNum())) {
				ll_ordermenu.setVisibility(View.VISIBLE);
				tv_ordermenu.setText(reserve.getOrderMenu() == null ? "" : reserve.getOrderMenu());
			} else {
				ll_ordermenu.setVisibility(View.GONE);
			}

			ll_bottom.setVisibility(View.VISIBLE);
			tv_hint.setText("");
			tv_btn1.setVisibility(View.GONE);
			tv_btn2.setVisibility(View.GONE);
			tv_btn3.setVisibility(View.VISIBLE);
			tv_btn3.setText(mContext.getString(com.rs.mobile.wportal.R.string.common_text105));
			tv_btn1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 주문삭제
				}
			});
			tv_btn2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 다시주문
				}
			});
			tv_btn3.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_rt_reserve_bottombtn_red);
			tv_btn3.setTextColor(mContext.getResources().getColor(com.rs.mobile.wportal.R.color.backgroundColor_rt_red_button));
			tv_btn3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Bundle bundle = new Bundle();
					bundle.putString("saleCustomCode", datas.get(viewHolder.getPosition()).getRestaurantCode());
					bundle.putString("orderNumber", datas.get(viewHolder.getPosition()).getOrderNum());
					bundle.putString("saleName", datas.get(viewHolder.getPosition()).getRestaurantName());
					PageUtil.jumpTo(mContext, RtEvaluateActivity.class, bundle);
				}
			});
			break;

		case TYPE_STAY_COMMENTED:// 已完成(已评价) //완료된
			tv_reservestatus.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_rt_reserve_status_commented);

			ll_amount.setVisibility(View.GONE);

			ll_personcount.setVisibility(View.VISIBLE);
			tv_personcount.setText(reserve.getPersonCount() == 0 ? mContext.getString(com.rs.mobile.wportal.R.string.common_text098)
					: reserve.getPersonCount() + mContext.getString(com.rs.mobile.wportal.R.string.ht_text_People));

			if (!TextUtils.isEmpty(reserve.getOrderNum())) {
				ll_ordermenu.setVisibility(View.VISIBLE);
				tv_ordermenu.setText(reserve.getOrderMenu() == null ? "" : reserve.getOrderMenu());
			} else {
				ll_ordermenu.setVisibility(View.GONE);
			}

			ll_bottom.setVisibility(View.VISIBLE);
			tv_hint.setText("");
			tv_btn1.setVisibility(View.GONE);
			tv_btn2.setVisibility(View.GONE);
			tv_btn3.setVisibility(View.VISIBLE);
			tv_btn3.setText(mContext.getString(com.rs.mobile.wportal.R.string.common_text104));
			tv_btn3.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_rt_reserve_bottombtn_black);
			tv_btn3.setTextColor(mContext.getResources().getColor(com.rs.mobile.wportal.R.color.textColor_rt_toolbar_title));
			tv_btn1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 주문삭제
				}
			});
			tv_btn2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 다시주문
				}
			});
			tv_btn3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 평가보러가기

					Intent restaurantIntent = new Intent(mContext, RtSellerDetailActivity.class);
					restaurantIntent.putExtra("restaurantCode", reserve.getRestaurantCode());
					mContext.startActivity(restaurantIntent);

				}
			});
			break;

		case TYPE_STAY_REFUNDING:// 退款中 환불중
			tv_reservestatus.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_rt_reserve_status_refunding);

			ll_amount.setVisibility(View.VISIBLE);
			tv_amount_title.setText(mContext.getString(com.rs.mobile.wportal.R.string.money_to_return));
			tv_amount.setText(reserve.getAmount() + mContext.getString(com.rs.mobile.wportal.R.string.money));

			ll_personcount.setVisibility(View.GONE);

			ll_ordermenu.setVisibility(View.GONE);

			ll_bottom.setVisibility(View.GONE);

			break;

		case TYPE_STAY_REFUNDCOMPLETED:// 退款完成 환불 완료
			tv_reservestatus.setBackgroundResource(com.rs.mobile.wportal.R.drawable.bg_rt_reserve_status_refundcompleted);

			ll_amount.setVisibility(View.VISIBLE);
			tv_amount_title.setText(mContext.getString(com.rs.mobile.wportal.R.string.money_to_return));
			tv_amount.setText(reserve.getAmount() + mContext.getString(com.rs.mobile.wportal.R.string.money));

			ll_personcount.setVisibility(View.GONE);

			ll_ordermenu.setVisibility(View.GONE);

			ll_bottom.setVisibility(View.GONE);

			// ll_bottom.setVisibility(View.VISIBLE);
			// tv_hint.setText("");
			// tv_btn1.setVisibility(View.VISIBLE);
			// tv_btn1.setOnClickListener(new OnClickListener() { //주문 삭제
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			//
			// activity.showDialog("주문삭제", "해당 주문을 삭제 하시겠습니까?", "주문삭제", new
			// OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// PaymentUtil.cancelReserver(mContext, reserve.getReserveId(),
			// "01", "", new OnPaymentResultListener() {
			//
			// @Override
			// public void onResult(JSONObject data) {
			// // TODO Auto-generated method stub
			//
			// try {
			//
			// String status = data.get("status").toString();
			//
			// if (status.equals("1")) {
			//
			// activity.t("주문 삭제 성공");
			//
			// activity.finish();
			//
			// }
			//
			// } catch (Exception e) {
			//
			// L.e(e);
			//
			// }
			// }
			//
			// @Override
			// public void onFail(String msg) {
			// // TODO Auto-generated method stub
			//
			// }
			// });
			// D.alertDialog.dismiss();
			// }
			// }, "취소", new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// D.alertDialog.dismiss();
			// }
			// });
			//
			// }
			// });
			// tv_btn2.setVisibility(View.VISIBLE);
			// tv_btn2.setOnClickListener(new OnClickListener() { //다시 주문
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// Intent payIntent = new Intent(mContext,
			// OrderDetailActivity.class);
			// payIntent.putExtra("orderNumber", reserve.getOrderNum());
			// activity.startActivity(payIntent);
			// }
			// });
			// tv_btn3.setVisibility(View.GONE);

			break;

		default:
			tv_reservestatus.setBackground(null);

			ll_amount.setVisibility(View.GONE);

			ll_personcount.setVisibility(View.GONE);

			ll_ordermenu.setVisibility(View.GONE);

			ll_bottom.setVisibility(View.GONE);

			break;
		}

		// hide
		if (reserve.getVisit() == 1) {
			ll_reservedate.setVisibility(View.GONE);
			ll_personcount.setVisibility(View.GONE);
		}
		// show
		else {
			ll_reservedate.setVisibility(View.VISIBLE);
			ll_personcount.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public int getViewTypeCount() {
		return 8;
	}

	@Override
	public int getItemViewType(int position) {
		int type = TYPE_INVALID;
		switch (mDatas.get(position).getReserveStatus()) {
		case "O":
			// 待付款
			type = TYPE_STAY_PAY;// 1
			break;

		case "B":
			// 已点菜
			type = TYPE_STAY_CHOOSEDMENU;// 2
			break;

		case "R":
			// 已预约
			type = TYPE_STAY_RESERVED;// 3
			break;

		case "F":
			// 已完成(未评价)
			type = TYPE_STAY_NO_COMMENT;// 4
			break;

		case "S":
			// 已完成(已评价)
			type = TYPE_STAY_COMMENTED;// 5
			break;

		case "C":
			// 退款中
			type = TYPE_STAY_REFUNDING;// 6
			break;

		case "CF":
			// 退款完成
			type = TYPE_STAY_REFUNDCOMPLETED;// 7
			break;

		default:
			break;
		}
		return type;
	}

	private void cancelReserve(String title, String msg, String okMsg, final String resultMsg, final String reserveID) {

		((BaseActivity) mContext).showDialog(title, msg, okMsg, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RTUtil.cancelReserver(mContext, reserveID, "01", "", new OnPaymentResultListener() {

					@Override
					public void onResult(JSONObject data) {
						// TODO Auto-generated method stub

						try {

							String status = data.get("status").toString();

							if (status.equals("1")) {

								T.showToast(mContext, resultMsg);

								RtReserveActivity activity = (RtReserveActivity) mContext;

								RtReserveClassifyFragment fragment = (RtReserveClassifyFragment) activity.mListFragment
										.get(activity.currentPosition);

								fragment.resetCurrentPage();

								fragment.requestReserveList(true);

							}

						} catch (Exception e) {

							L.e(e);

						}
					}

					@Override
					public void onFail(String msg) {
						// TODO Auto-generated method stub

					}
				});
				D.alertDialog.dismiss();
			}
		}, mContext.getResources().getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				D.alertDialog.dismiss();
			}
		});

	}
	
	/**
	 * 결제 완료
	 * 
	 * @param payType
	 *            : "Y" or "A" or "W" or "U"
	 */
	public void addAdvance(String payType, String auth_no, final Reserve reserve) {

		try {

			OkHttpHelper helper = new OkHttpHelper(mContext);

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

							RtReserveActivity activity = (RtReserveActivity) mContext;

							RtReserveClassifyFragment fragment = (RtReserveClassifyFragment) activity.mListFragment
									.get(activity.currentPosition);

							fragment.resetCurrentPage();

							fragment.requestReserveList(true);

							
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
					+ S.getShare(mContext, C.KEY_REQUEST_MEMBER_ID, "") + "&payType=" + payType
					+ "&orderNum=" + reserve.getOrderNum() + "&orderAmount="
					+ reserve.getRealAmount() + "&token="
					+ S.getShare(mContext, C.KEY_JSON_TOKEN, "") + "&point=" + reserve.getPointAmount()
					+ "&wsPayAuthNo=" + auth_no, params);

			// }, Constant.BASE_URL_RT + C.RT_ADD_PAYMENT_ADVANCE, params);

		} catch (Exception e) {

			L.e(e);

		}

	}
	
	private void showErrorDialog(String tips1, String tips2) {

		D.showDialog(mContext, -1, mContext.getString(com.rs.mobile.wportal.R.string.sm_text_tips), tips1, tips2,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						D.alertDialog.dismiss();
						UtilClear.IntentToLongLiao(mContext,
								"cn.ycapp.im.ui.mywallet.MyWalletActivity","");

					}
				}, mContext.getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						D.alertDialog.dismiss();
						PageUtil.jumpTo(mContext, MyOrderActivity.class);
					}
				});

	}

}
