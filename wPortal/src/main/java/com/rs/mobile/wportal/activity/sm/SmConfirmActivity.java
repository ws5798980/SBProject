
package com.rs.mobile.wportal.activity.sm;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.pay.PayResult;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.PaymentUtil;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.LGUPlaus_Pay;
import com.rs.mobile.wportal.adapter.sm.confirmOrderGoodAdapter;
import com.rs.mobile.wportal.biz.Coupon;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.wportal.sm.SMUtil;
import com.rs.mobile.wportal.view.AmountView;
import com.rs.mobile.wportal.view.SecurityPasswordEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

import static com.umeng.socialize.utils.ContextUtil.getContext;

public class SmConfirmActivity extends BaseActivity {

	private LinearLayout close_btn;

	private RelativeLayout rela_receiver, select_time, rela_amount;

	private TextView text_receiver, text_phone, text_address, text_balance, text_incharge, text_totoal_002,
			commit_order, text_coupon, text_logis, point;

	private ListView listView;

	private AmountView amountView;

	private List<ShoppingCart> listdata;

	private List<ShoppingCart> list_attach = new ArrayList<>();

	private confirmOrderGoodAdapter adapter;

	private String id_code;

	private String guid;

	private String price;

	private String orderprice;

	private String orderid;

	private Keyboard k1;// 数字键盘

	private View pay_password_view;

	private RelativeLayout pay_type_yucheng, pay_type_wechat, pay_type_alipay, korea_lg_uplus;

	private ImageView selector_pay_way_yucheng, selector_pay_way_wecaht, selector_pay_way_alipay, selector_pay_way_lg_uplus;

	private int payType = 4; // 0 : yucheng, 1 : wechat, 2 : alipay, 3 : Unionpay, 4 : LG U+

	private int flag = 0;

	private boolean isReadyToPay = false;

	private CheckBox check_box;

	private JSONObject pointOption;

	private boolean canCoupons;

	private boolean canPoint;

	private String canMaxPointAmount;

	private String canUsePoint = "0";

	private String real_amount;

	private ArrayList<Coupon> listMergeUsedCoupons = new ArrayList<>();

	private ArrayList<Coupon> listMergeNotUsedCoupons = new ArrayList<>();

	private ArrayList<Coupon> listMergeUsedCouponsSelect = new ArrayList<>();

	private JSONArray goodsCategoryList;

	private boolean pointselect;

	private boolean candelevery = false;

	/***
	 * 优惠券折扣金额
	 */
	private float couponMoney = 0;

	private final String mMode = "00";

	private SecurityPasswordEditText editText_Pwd;

	private String payPwd;

	private Handler handle = new Handler() {

		public void handleMessage(Message msg) {

			super.handleMessage(msg);

		};
	};

	private Handler alipayHandler = new Handler() {

		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case SDK_PAY_FLAG: {

				@SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);

				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				String msginfo;
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {

					msginfo = getString(R.string.common_text005);

					SMUtil.smConfirmStatus(orderid, SmConfirmActivity.this);

					t(msginfo);

					return;

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

				PageUtil.jumpToWithFlag(SmConfirmActivity.this, MyOrderActivity.class);
				finish();

				break;
			}

			default:
				break;
			}
		};
	};

	private String total;

	private String shopCartFlag;

	private String real_amount1;

	private RelativeLayout rela_coupon;

	private String order_out_id;

	private String payInfo = "";

	private RelativeLayout pay_type_unionpay;

	private ImageView selector_pay_way_unionpay;

	private TextView text_dele;

	// 商户PID
	public static final String PARTNER = "2088421639854635";

	// 商户收款账号
	public static final String SELLER = "3197169063@qq.com";

	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALNAQo8+Z8BgC6NuTZK/eKJ89poW8Q0CX/p46+yHbu5mDuwFeyr1rpDGIqGfvGemQfiAq1dJR+2AWbldJH9gDTPuWlZVTdl2aClfVQzn1Cm1Vv/GTOVa31PdubRLYr2+uVhvZ6HLPvtBtyfGbvr6ora/j29CRDCYAdUaye9WDBzXAgMBAAECgYBOT8zEeCcrzMpI5G/PpQc3NhEm0M8PN/Jmo12vQJweW0g5pUtiQWO6rFWE9xyAzAoWX2B8Ce/6uxB4A1FmtLgPNO3A6HdF/Be3HLaF0JyjTlY+aM6kg6owShIVZwpJBj3oN7CTlp+FeXgBxMr4rhqRHA8IvUuCbfWVusSaPYITQQJBANY//O/gd7GQauTGbHOo4PvGqtuBRcdvgSYV8ZBTse1qJcxtYv1sGKQuODmTmzvZEY4G9U7Ekr2z4iuu7eHDzEMCQQDWLlMwbFKldl2Eqe2RsDhjyZVfabmXdDj2/hsO5I7IBS4CPwa7oLUK/u24FtYvxgEepfmE4K7dBb5t1TiDHy3dAkEAsyzgQ1vdvcmhG6I1oKRjQyxqRxhdWuSNhWRkmOblrj9PTR9N4dI86VEeBEjFUc+/Np/rFuyeK7f8NbwdhOlSMQJBAI/mj/34yY5h+HMpuHQp8bkZt0Jjxk37yEoqcAORjFzXHhJkRiHJ19mLbixBK6btYdM1sG+WheX59ffVSiLO0AECQQCgdlLZPWZ5kSFhm7tflmBlfAFZRHbWxRWvrlf78NI72fHhKf8gbLJY7lYOgn0h38rM5N/Ned7fYLBLf9h5NNDy";

	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	private static final int SDK_PAY_FLAG = 1;

	private String message = "";

	protected RelativeLayout pointrela;

	public String sale_custome_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		try {

			setContentView(R.layout.activity_sm_confirm);

			shopCartFlag = getIntent().getStringExtra("onCartProcess");
			sale_custome_code = getIntent().getStringExtra("sale_custom_code");

			initView();

			initData();

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void initData() {

		try {

			listdata = getIntent().getParcelableArrayListExtra("goods");

			list_attach = getIntent().getParcelableArrayListExtra("attachment");

			adapter = new confirmOrderGoodAdapter(listdata, SmConfirmActivity.this);

			listView.setAdapter(adapter);

			setListViewHeight(listView);

			getMyAddressList();

		} catch (Exception e) {

			L.e(e);

		}

	}

	//주문확인
	private void initView() {

		try {

			text_totoal_002 = (TextView) findViewById(R.id.text_totoal_002);
			text_coupon = (TextView) findViewById(R.id.text_coupon);
			rela_coupon = (RelativeLayout) findViewById(R.id.rela_coupon);
			text_logis = (TextView) findViewById(R.id.text_logis);
			check_box = (CheckBox) findViewById(R.id.check_box);
			point = (TextView) findViewById(R.id.point);
			close_btn = (LinearLayout) findViewById(R.id.close_btn);
			text_dele = (TextView) findViewById(R.id.text_dele);
			pointrela = (RelativeLayout) findViewById(R.id.pointrela);
			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					finish();
				}
			});

			rela_receiver = (RelativeLayout) findViewById(R.id.rela_receiver);
			rela_receiver.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent i = new Intent(SmConfirmActivity.this, SmAddressActivity.class);

					i.putExtra("typeSelect", true);

					startActivityForResult(i, 1000);

				}
			});

			rela_amount = (RelativeLayout) findViewById(R.id.rela_amount);

			text_receiver = (TextView) findViewById(R.id.text_receiver);

			text_phone = (TextView) findViewById(R.id.text_phone);

			text_address = (TextView) findViewById(R.id.text_address);

			text_balance = (TextView) findViewById(R.id.text_balance);

			text_incharge = (TextView) findViewById(R.id.text_incharge);

			commit_order = (TextView) findViewById(R.id.commit_order);
			commit_order.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					isReadyToPay = true;
					//candelevery = true;

					/*Intent intent = new Intent(SmConfirmActivity.this, MyOrderActivity.class);
					startActivity(intent);
					finish();*/
					//TODO  改写
					if (isReadyToPay == true && candelevery) {

						//추천인이 없다면...
						/*if(!checkParent())
						{
							AlertDialog.Builder builder = new AlertDialog.Builder(SmConfirmActivity.this, AlertDialog.THEME_HOLO_DARK);
							builder .setTitle("Alert")
									.setMessage(getString(R.string.mk_reference_09))
									.setPositiveButton("계속진행", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											//Auto matting API를 타야함
											autoMattingAPIHttp();
										}
									})
									.setNegativeButton("매칭하기", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {

											startActivityForResult(new Intent(SmConfirmActivity.this,PersnalReference_Serche.class),10001);
										}
									});
							builder.show();

						} else {*/
							creatOrder();
						//}
					} else if (!candelevery && isReadyToPay) {

						showDialogAddress();

					} else {
						t(message);
					}

				}
			});

			listView = (ListView) findViewById(R.id.listView);

			selector_pay_way_yucheng = (ImageView) findViewById(R.id.selector_pay_way_yucheng);
			selector_pay_way_wecaht = (ImageView) findViewById(R.id.selector_pay_way_wecaht);
			selector_pay_way_alipay = (ImageView) findViewById(R.id.selector_pay_way_alipay);
			selector_pay_way_unionpay = (ImageView) findViewById(R.id.selector_pay_way_unionpay);
			selector_pay_way_lg_uplus = (ImageView) findViewById(R.id.selector_pay_way_lg_uplus);
			pay_type_yucheng = (RelativeLayout) findViewById(R.id.pay_type_yucheng);
			pay_type_wechat = (RelativeLayout) findViewById(R.id.pay_type_wechat);
			pay_type_alipay = (RelativeLayout) findViewById(R.id.pay_type_alipay);
			pay_type_unionpay = (RelativeLayout) findViewById(R.id.pay_type_unionpay);
			korea_lg_uplus = (RelativeLayout) findViewById(R.id.korea_lg_uplus);

			korea_lg_uplus.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					payType = 4;

					selector_pay_way_lg_uplus.setImageResource(R.drawable.icon_selected_s);
					selector_pay_way_yucheng.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_wecaht.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_alipay.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_unionpay.setImageResource(R.drawable.icon_selected_n);
				}
			});

			pay_type_yucheng.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					payType = 0;

					selector_pay_way_lg_uplus.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_yucheng.setImageResource(R.drawable.icon_selected_s);
					selector_pay_way_wecaht.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_alipay.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_unionpay.setImageResource(R.drawable.icon_selected_n);

				}
			});

			pay_type_wechat.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					 payType = 1;
					//
					selector_pay_way_lg_uplus.setImageResource(R.drawable.icon_selected_n);
					 selector_pay_way_yucheng.setImageResource(R.drawable.icon_selected_n);
					 selector_pay_way_wecaht.setImageResource(R.drawable.icon_selected_s);
					 selector_pay_way_alipay.setImageResource(R.drawable.icon_selected_n);
					 selector_pay_way_unionpay.setImageResource(R.drawable.icon_selected_n);
					t(getString(R.string.cancel));

				}
			});

			pay_type_alipay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					payType = 2;

					selector_pay_way_lg_uplus.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_wecaht.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_yucheng.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_alipay.setImageResource(R.drawable.icon_selected_s);
					selector_pay_way_unionpay.setImageResource(R.drawable.icon_selected_n);

				}
			});

			pay_type_unionpay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					payType = 3;

					selector_pay_way_lg_uplus.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_wecaht.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_yucheng.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_alipay.setImageResource(R.drawable.icon_selected_n);
					selector_pay_way_unionpay.setImageResource(R.drawable.icon_selected_s);

				}
			});

		} catch (Exception e) {

			L.e(e);

		}

	}

	public String id="";
	String name, mobile, address, position, latitude, longitude, zip_code, zip_name;
	//20180328
	private void getMyAddressList(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("lang_type", AppConfig.LANG_TYPE);
		params.put("custom_code", S.get(this, C.KEY_JSON_CUSTOM_CODE));
		params.put("token", S.get(this, C.KEY_JSON_TOKEN));
		params.put("pg", "1");
		params.put("pagesize", "10");

		OkHttpHelper okHttpHelper = new OkHttpHelper(getContext());
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				checkBeforeCreatOrder();

				try {
					JSONArray arr = data.getJSONArray("bcm100ts");
					if (arr.length() == 0) {
						//showNoData(getView(), "您还没有收货地址哦！", null);
						showDialogAddress();

						return;
					}

					for (int i = 0; i < arr.length(); i++) {

						JSONObject obj = arr.getJSONObject(i);

						boolean hasDefault = obj.getBoolean("default_add");
						if (hasDefault == true) {
							id = obj.get("seq_num").toString();
							name = obj.get("delivery_name").toString();
							mobile = obj.get("mobilepho").toString();
							address = obj.get("to_address").toString();
							zip_name = obj.getString("zip_name");
							zip_code = obj.getString("zip_code");
							candelevery = obj.getBoolean("default_add");
							text_receiver.setText(name);
							text_phone.setText(mobile);

							String tmp_addr = id + ", " + zip_code + " " + address;
							text_address.setText(tmp_addr);

//						position = obj.getString("location");

//						latitude = obj.getString("latitude");
//						longitude = obj.getString("longitude");
//						hasDelivery = obj.getBoolean("hasDelivery");
							return;
						}
						/*hasDelivery = false;
						myAddress = new Address(name, mobile, address, hasDefault, id, "", "",
								"", zip_name, zip_code, hasDelivery);
						list.add(myAddress);*/
					}
					/*for (int i = 0; i < arr.length(); i++) {

						JSONObject obj = arr.getJSONObject(i);

						boolean hasDefault = obj.getBoolean("hasDefault");

						if (hasDefault == true) {

							text_receiver.setText(obj.get("name").toString());

							text_phone.setText(obj.get("mobile").toString());

							text_address.setText(obj.getString("location") + " " + obj.getString("address"));

							id_code = obj.get("id").toString();

							candelevery = obj.getBoolean("hasDelivery");
							if (!candelevery) {

								text_dele.setVisibility(View.VISIBLE);

								showDialogAddress();

								return;
							}

							return;

						}

					}*/


					/*adapter = new AddressAdapter(list, getContext(), handle);
					lv.setAdapter(adapter);
					if(((SmAddressActivity)getActivity()).typeSelect){
						lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
								Intent intent = new Intent();
								intent.putExtra("id", list.get(i).getId());
								intent.putExtra("name", list.get(i).getName());
								intent.putExtra("address", list.get(i).getAddress());
								intent.putExtra("mobile", list.get(i).getPhone());
								getActivity().setResult(RESULT_OK, intent);
								getActivity().finish();
							}
						});
					}*/
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
			}

			@Override
			public void onNetworkError(Request request, IOException e) {
			}
		}, Constant.XS_BASE_URL+"MyInfo/MyinfoAddressList", GsonUtils.createGsonString(params));
	}

	/*private void getMyAddressList() {

		try {

			Map<String, String> params = new HashMap<String, String>();

			params.put("div_code", C.DIV_CODE);

			OkHttpHelper okHttpHelper = new OkHttpHelper(SmConfirmActivity.this);

			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

					Log.d("wport", e.toString());
				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

					try {
						Log.d("wport", "getMyAddressList" + data.toString());
						checkBeforeCreatOrder();
						JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);
						if (arr.length() == 0) {
							showDialogAddress();

							return;
						}
						for (int i = 0; i < arr.length(); i++) {

							JSONObject obj = arr.getJSONObject(i);

							boolean hasDefault = obj.getBoolean("hasDefault");

							if (hasDefault == true) {

								text_receiver.setText(obj.get("name").toString());

								text_phone.setText(obj.get("mobile").toString());

								text_address.setText(obj.getString("location") + " " + obj.getString("address"));

								id_code = obj.get("id").toString();

								candelevery = obj.getBoolean("hasDelivery");
								if (!candelevery) {

									text_dele.setVisibility(View.VISIBLE);

									showDialogAddress();

									return;
								}

								return;

							}

						}
						showDialogAddress();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						L.e(e);
					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					Log.d("wportal", data.toString());
				}
			}, Constant.SM_BASE_URL + Constant.GET_USER_SHOPADDRESS_LIST, params);

		} catch (Exception e) {

			L.e(e);

		}
	}*/

	private void creatOrder() {

		try {

			HashMap<String, String> params = new HashMap<String, String>();

			JSONObject valueLocation = new JSONObject();

			valueLocation.put("locationId", id);
			//valueLocation.put("locationId", "489");

			params.put("validateInfo", valueLocation.toString());
			params.put("guid", guid);
			if (canPoint && check_box.isChecked()) {
				params.put("point_amount", canMaxPointAmount);
			} else {
				params.put("point_amount", "0");
			}
			String useCouponsIds = "";
			for (int i = 0; i < listMergeUsedCouponsSelect.size(); i++) {
				useCouponsIds = useCouponsIds + listMergeUsedCouponsSelect.get(i).getId() + ",";
			}
			if (listMergeUsedCouponsSelect.size() > 0) {
				useCouponsIds = useCouponsIds.substring(0, useCouponsIds.length() - 1);
			}

			params.put("useCouponsIds", useCouponsIds);
			OkHttpHelper okHttpHelper = new OkHttpHelper(SmConfirmActivity.this);

			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					String bbbb = request.toString();
				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					try {
						L.d("creatOrder" + data.toString());
						if (data.getString(C.KEY_JSON_FM_STATUS).equals("1")) {

							orderprice = data.getString("amount");

							orderid = data.getString("order_code");

							String pointAmount = data.get("pointAmount").toString();

							String real_amount = data.get("real_amount").toString();

							// keyboardUtil = new
							// KeyboardUtil(SmConfirmActivity.this,
							// SmConfirmActivity.this,
							// pay_password_view, orderid, orderprice,
							// canUsePoint, real_amount, "0");
							//
							// keyboardUtil.showKeyboard();
							switch (payType) {
							case 0:
								PaymentUtil.showKeyBoard(SmConfirmActivity.this, orderid, orderprice, pointAmount,
										real_amount, "0", new OnPaymentResultListener() {

											@Override
											public void onResult(JSONObject data) {
												// stub
												String status;
												try {
													status = data.get("status").toString();

													if (status.equals("1")) {
														JSONObject data1 = data.getJSONObject("data");
														String order_no1 = data1.get("order_no").toString();
														t(getString(R.string.common_text005));

														SMUtil.smConfirmStatus(order_no1, SmConfirmActivity.this);

													} else {

														T.showToast(SmConfirmActivity.this, data.get("msg").toString());

														// method
														// stub
														if (status.equals("1101")) { // wrong
																						// pw
															showDialogTip(data.get("msg").toString(),
																	getString(R.string.common_text020));
														} else if (status.equals("1711")) { // not
																							// enough
																							// money
															showDialogTip(data.get("msg").toString(),
																	getString(R.string.common_text021));
														} else { //
															finish();
															PageUtil.jumpToWithFlag(SmConfirmActivity.this,
																	MyOrderActivity.class);
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

												PageUtil.jumpToWithFlag(SmConfirmActivity.this, MyOrderActivity.class);
												finish();

											}
										});
								break;

							case 1:
								PaymentUtil.wechatPayMethod(orderid, real_amount, SmConfirmActivity.this);
								break;
							case 2:
								PaymentUtil.alipayMethod(orderid, real_amount, SmConfirmActivity.this, SDK_PAY_FLAG, alipayHandler);
								break;
							case 3:
								PaymentUtil.unionPayMethod(orderid, real_amount, SmConfirmActivity.this, MyOrderActivity.class);
								break;
							/*========================== PG source start ================================*/

							case 4:
								Intent intent = new Intent(SmConfirmActivity.this, LGUPlaus_Pay.class);
								intent.putExtra("OrderNumber", orderid);
								intent.putExtra("OrderMoney", real_amount);
								intent.putExtra("OrderUserName", name);
								intent.putExtra("GiftInfo","api새로 받을것");

								startActivityForResult(intent, 1001);
							/*Message message = new Message();
							message.what = 1;
							message.obj = orderid + "|" + orderprice;
							lguplusHandler.sendMessage(message);*/
								break;
							/* ============================== PG source end ======================= */
							default:
								break;
							}

						} else {

							t(data.getString("message"));

						}

					} catch (Exception e) {
						L.e(e);

					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					String aaa = data.toString();

				}
			}, Constant.SM_BASE_URL + Constant.CREATE_ORDER, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			if (requestCode == 1000) {

				text_receiver.setText(data.getStringExtra("name"));

				text_phone.setText(data.getStringExtra("mobile"));

				text_address.setText(data.getStringExtra("address"));

				id_code = data.getStringExtra("id");

//				candelevery = data.getBooleanExtra("hasDelivery", false);
//				if (!candelevery) {
//					rela_receiver
//							.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), com.rs.mobile.wportal.R.color.transparent));
//					text_dele.setVisibility(View.VISIBLE);
//					return;
//				}
//
//				text_dele.setVisibility(View.GONE);
//				rela_receiver.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), com.rs.mobile.wportal.R.color.white));

			} else if (requestCode == 101) {
				listMergeUsedCouponsSelect.clear();
				couponMoney = 0;
				listMergeUsedCouponsSelect = data.getParcelableArrayListExtra("listMergeUsedCouponsSelect");

				try {
					for (int i = 0; i < listMergeUsedCouponsSelect.size(); i++) {
						for (int j = 0; j < goodsCategoryList.length(); j++) {
							JSONObject js = goodsCategoryList.getJSONObject(j);
							String GoodsCategoryId = js.get("GoodsCategoryId").toString();
							L.d(GoodsCategoryId + "goods");
							L.d(listMergeUsedCouponsSelect.get(i).getCoupon_range() + "coupon");
							String GoodsCategoryTotalAmount = js.get("GoodsCategoryTotalAmount").toString();
							if (listMergeUsedCouponsSelect.get(i).getCoupon_range().equals("0")) {
								if (listMergeUsedCouponsSelect.get(i).getCoupon_type().equals("1")) {

									float cmoney = Float.parseFloat(total)
											* Float.parseFloat(listMergeUsedCouponsSelect.get(i).getDc_amount());
									L.d(cmoney + "cmoney");
									float upper_limit_amount = Float
											.parseFloat(listMergeUsedCouponsSelect.get(i).getUpper_limit_amount());
									if (cmoney < upper_limit_amount) {
										couponMoney = couponMoney + cmoney;
									} else {
										couponMoney = couponMoney + upper_limit_amount;
									}

								} else {
									float cmoney = Float.parseFloat(listMergeUsedCouponsSelect.get(i).getDc_amount());
									couponMoney = couponMoney + cmoney;

								}

								break;
							} else if (listMergeUsedCouponsSelect.get(i).getCoupon_range().equals(GoodsCategoryId)) {

								if (listMergeUsedCouponsSelect.get(i).getCoupon_type().equals("1")) {

									float cmoney = Float.parseFloat(GoodsCategoryTotalAmount)
											* Float.parseFloat(listMergeUsedCouponsSelect.get(i).getDc_amount());

									float upper_limit_amount = Float
											.parseFloat(listMergeUsedCouponsSelect.get(i).getUpper_limit_amount());
									if (cmoney < upper_limit_amount) {
										couponMoney = couponMoney + cmoney;
									} else {
										couponMoney = couponMoney + upper_limit_amount;
									}

								} else {
									float cmoney = Float.parseFloat(listMergeUsedCouponsSelect.get(i).getDc_amount());
									couponMoney = couponMoney + cmoney;

								}
								L.d(couponMoney + "couponMoney");
							}
						}
					}
					if (pointselect) {
						real_amount1 = Float.parseFloat(total) - couponMoney - Float.parseFloat(canMaxPointAmount) + "";
					} else {
						real_amount1 = Float.parseFloat(total) - couponMoney + "";
					}

					text_totoal_002.setText(real_amount1);
				} catch (Exception e) {
					L.e(e);
				}
			} else {

				if (data == null) {
					return;
				}

				String msg = "";
				/*
				 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
				 */
				String str = data.getExtras().getString("pay_result");

				if (str.equalsIgnoreCase("success")) {
					// 支付成功后，extra中如果存在result_data，取出校验
					// result_data结构见c）result_data参数说明

					msg = getString(R.string.common_text005);

					SMUtil.smConfirmStatus(orderid, SmConfirmActivity.this);

					t(msg);

					return;

				} else if (str.equalsIgnoreCase("cancel")) {

					msg = getString(R.string.common_text008);

				} else if (str.equalsIgnoreCase("fail")) {

					msg = getString(R.string.common_text007);

				} else {

					msg = getString(R.string.common_text007);

				}

				t(msg);

				PageUtil.jumpToWithFlag(SmConfirmActivity.this, MyOrderActivity.class);

				finish();

			}

		} else if(resultCode == 10001) {

		}
	}

	private void showDialogAddress() {

		showDialog(getString(R.string.sm_text_tips), getString(R.string.sm_text_addressdetail),
				getString(R.string.gps_setting_go), new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						D.alertDialog.dismiss();
						Intent i = new Intent(SmConfirmActivity.this, SmAddressActivity.class);

						i.putExtra("typeSelect", true);

						startActivityForResult(i, 1000);

					}
				}, getString(R.string.gps_setting_cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						D.alertDialog.dismiss();
					}
				});
	}

	private void checkBeforeCreatOrder() {

		HashMap<String, String> params = new HashMap<String, String>();
		try {
			JSONArray arr = new JSONArray();

			for (int i = 0; i < listdata.size(); i++) {

				JSONObject j = new JSONObject();
				j.put(C.KEY_JSON_FM_ITEM_CODE, listdata.get(i).getId());
				j.put(C.KEY_JSON_FM_COUNT, listdata.get(i).getNum() + "");
				j.put("unit_price", listdata.get(i).getprice() + "");
				if(sale_custome_code != null){
					j.put("custom_code", sale_custome_code);
				}else{
					j.put("custom_code", listdata.get(i).sale_custom_code);
				}
				arr.put(j);

			}
			JSONArray arr_attach = new JSONArray();
			for (int i = 0; i < list_attach.size(); i++) {
				JSONObject k = new JSONObject();
				k.put("custom_code", "YC01");
				k.put("item_code", list_attach.get(i).getId());
				k.put("unit_price", list_attach.get(i).getprice() + "");

				arr_attach.put(k);
			}

			JSONObject jsonObject = new JSONObject();

			jsonObject.put("goods", arr);
			jsonObject.put("g", arr_attach);
			jsonObject.put("onCartProcess", shopCartFlag);
			//jsonObject.put("key", listdata.get(0).getDiv_code());
			jsonObject.put("key", "2");

			params.put("orderInfo", jsonObject.toString());
			//params.put("appType", "6");

			//params.put(C.KEY_JSON_TOKEN, S.getShare(SmConfirmActivity.this, C.KEY_JSON_TOKEN, ""));

		} catch (JSONException e1) {
			e1.printStackTrace();
			L.d("checkBeforeCreatOrder : Exception : " + e1.toString());
		}
		OkHttpHelper okHttpHelper = new OkHttpHelper(SmConfirmActivity.this);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				Log.d("wportal", e.toString());
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				Log.i("123132", "checkBeforeCreatOrder="+responseDescription);
				try {
					if (data.get("status").toString().equals("1")) {
						JSONObject json = data.getJSONObject("data");
						guid = json.getString("guid");
						total = json.get("totalprice").toString();
						real_amount = total;
						text_totoal_002.setText(real_amount);
						String money = json.get("expressAmount").toString();
//						text_logis.setText(getString(R.string.common_text009) + getResources().getString(R.string.rmb)
//								+ json.get("expressAmount").toString());
						text_logis.setText(money);
						canCoupons = json.getBoolean("canCoupons");
						goodsCategoryList = json.getJSONArray("goodsCategoryList");

						rela_coupon.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								Intent i = new Intent(SmConfirmActivity.this, UseCouponActivity.class);

								i.putParcelableArrayListExtra("listMergeUsedCoupons", listMergeUsedCoupons);

								i.putParcelableArrayListExtra("listMergeNotUsedCoupons", listMergeNotUsedCoupons);

								startActivityForResult(i, 101);

							}
						});
						canPoint = json.getBoolean("canPoint");
						if (canPoint) {
							pointOption = json.getJSONObject("pointOption");
							canMaxPointAmount = pointOption.get("canMaxPointAmount").toString();
							if (Float.parseFloat(canMaxPointAmount) > 0) {
								pointrela.setVisibility(View.VISIBLE);
							} else {
								pointrela.setVisibility(View.GONE);
							}
							point.setText(getString(R.string.common_text011) + canMaxPointAmount);

							check_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

								@Override
								public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

									if (isChecked) {
										pointselect = true;
										canUsePoint = canMaxPointAmount;
										real_amount1 = Float.parseFloat(total) - Float.parseFloat(canMaxPointAmount)
												- couponMoney + "";
										text_totoal_002.setText(real_amount1);
									} else {
										pointselect = false;
										canUsePoint = "0";
										real_amount1 = Float.parseFloat(total) - couponMoney + "";
										text_totoal_002.setText(real_amount1);

									}
								}
							});

						} else {
							point.setText(getString(R.string.common_text012));
						}
						JSONObject jscoupon = json.getJSONObject("coupons");
						JSONArray mergeUsedCoupons = jscoupon.getJSONArray("mergeUsedCoupons");
						if (!canCoupons) {
							text_coupon.setText("无可用优惠券");
						} else {
							text_coupon.setText(mergeUsedCoupons.length() + "张优惠券");
						}
						for (int i = 0; i < mergeUsedCoupons.length(); i++) {
							JSONObject j = mergeUsedCoupons.getJSONObject(i);
							Coupon c = new Coupon(j.get("id").toString(), j.get("coupon_no").toString(),
									j.get("coupon_name").toString(), j.get("coupon_type").toString(),
									j.get("coupon_range").toString(), j.get("coupon_range_remark").toString(),
									j.get("over_amount").toString(), j.get("dc_amount").toString(),
									j.get("upper_limit_amount").toString(), j.get("over_amount_remark").toString(),
									j.get("start_date").toString(), j.get("end_date").toString(),
									j.get("is_valid").toString(), j.get("receive_date").toString(),
									j.get("platform").toString(), true, false);
							listMergeUsedCoupons.add(c);
						}
						JSONArray mergeNotUsedCoupons = jscoupon.getJSONArray("mergeNotUsedCoupons");
						for (int i = 0; i < mergeNotUsedCoupons.length(); i++) {
							JSONObject j = mergeNotUsedCoupons.getJSONObject(i);
							Coupon c = new Coupon(j.get("id").toString(), j.get("coupon_no").toString(),
									j.get("coupon_name").toString(), j.get("coupon_type").toString(),
									j.get("coupon_range").toString(), j.get("coupon_range_remark").toString(),
									j.get("over_amount").toString(), j.get("dc_amount").toString(),
									j.get("upper_limit_amount").toString(), j.get("over_amount_remark").toString(),
									j.get("start_date").toString(), j.get("end_date").toString(),
									j.get("is_valid").toString(), j.get("receive_date").toString(),
									j.get("platform").toString(), true, false);

							listMergeNotUsedCoupons.add(c);
						}

						isReadyToPay = true;

					} else {
						message = data.getString("message");
						t(message);
					}
				} catch (JSONException e) {
					L.e(e);
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				//Util.Debug_Toast_Message(getApplicationContext(), "onBizFailure : " + responseDescription);
				Log.d("wportal", data.toString());
			}
		}, Constant.SM_BASE_URL + Constant.CHECK_BEFORE_CREATEORDER, params);
	}

	private void showDialogTip(String tips1, String tips2) {

		D.showDialog(SmConfirmActivity.this, -1, SmConfirmActivity.this.getResources().getString(R.string.sm_text_tips),
				tips1, tips2, new OnClickListener() {

					@Override
					public void onClick(View v) {

						D.alertDialog.dismiss();
						UtilClear.IntentToLongLiao(SmConfirmActivity.this,
								"cn.ycapp.im.ui.mywallet.MyWalletActivity","");
							SmConfirmActivity.this.finish();
					}
				}, SmConfirmActivity.this.getResources().getString(R.string.cancel), new OnClickListener() {

					@Override
					public void onClick(View v) {

						D.alertDialog.dismiss();
						finish();
						PageUtil.jumpToWithFlag(SmConfirmActivity.this, MyOrderActivity.class);
					}
				});

	}

	private boolean checkParent()
	{
		//여기서 다시 검사하는 루틴이 들어가야함   반듯이  로그인시에 추천인이 있었다면 싱글톤에 저장되어 있어 다음에 앱을 들어올때도 가지고 있어서
		//데이터를 삭제해도 폰에 살아있다
		//중간에 추천인이 삭제 될 경우가 있을까?   추천인이 없다가 추가 되었을 경우가 있음

		if((S.getShare(getApplicationContext(), C.KEY_REQUEST_PARENT_ID,"").compareTo("null") == 0) ||
				(S.getShare(getApplicationContext(), C.KEY_REQUEST_PARENT_ID, "").compareTo("") == 0))
		{
			return false;
		}
		return true;
	}

	private void autoMattingAPIHttp() {
		try {

			HashMap<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "application/json;Charset=UTF-8");

			JSONObject j1 = new JSONObject();
			try {

				String aaaa = S.getShare(getApplicationContext(), C.KEY_REQUEST_PARENT_ID,"");

				j1.put(C.RS_KEY_HOSPITAL_LANGTYPE, "chn"); //memid
				j1.put(C.RS_KEY_HOSPITAL_CUSTOM_CODE, S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, "")); //mempwd

			} catch (JSONException e1) {
				e1.printStackTrace();
			}


			OkHttpHelper helper = new OkHttpHelper(
					SmConfirmActivity.this);
			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request,
										   IOException e) {

				}

				@Override
				public void onBizSuccess(
						String responseDescription,
						JSONObject data, String flag) {
					try {
						if(data.getString("status").compareTo("1") == 0)
						{
							S.setShare(getApplicationContext(), C.KEY_REQUEST_PARENT_ID, data.getString("parent_id"));
							t(data.getString("msg"));
						} else if(data.getString("status").compareTo("2") == 0)
						{
							t(data.getString("msg"));
						}
					} catch (JSONException e) {

					}
				}

				@Override
				public void onBizFailure(
						String responseDescription,
						JSONObject data, String responseCode) {
					//Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();

				}
			}, Constant.BASE_REFERENCE_URL + Constant.PERSONAL_REFERENCE_AUTO_MATTING, headers, j1.toString());
		} catch (Exception e) {

			L.e(e);

		}
	}

}
