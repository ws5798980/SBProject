package com.rs.mobile.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.R;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.pay.PayResult;
import com.rs.mobile.common.view.SecurityPasswordEditText;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.view.BaseDialog;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

//import com.tencent.mm.opensdk.modelpay.PayReq;

public class PaymentUtil {

	private static SecurityPasswordEditText editText_Pwd;

	private static String payPwd;

	private static AlertDialog dlg;
	private static IWXAPI wechat_api;

	private static String mMode = "00";

	public static void showKeyBoard(final Context context, final String order_no, final String order_amount,
			final String order_Point, final String real_amount, final String sCouponCode,
			final OnPaymentResultListener listener) {

		dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(false);
		dlg.show();
		Window window = dlg.getWindow();
		window.clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		window.setContentView(com.rs.mobile.common.R.layout.starcard_query_pwdvalidate_layout);
		Button ok = (Button) window.findViewById(com.rs.mobile.common.R.id.button_Query);
		ok.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				payPwd = editText_Pwd.getInputnumber();

				if (payPwd == null || payPwd.equals("") || payPwd.length() < 6) {

					Toast.makeText(context, "请输入您的支付密码", Toast.LENGTH_SHORT).show();

				} else {
					dlg.dismiss();
					submitOrder(context, order_no, order_amount, order_Point, payPwd, real_amount, sCouponCode,
							listener);

				}

			}

		});

		// 关闭alert对话框架
		Button btnCancel = (Button) window.findViewById(com.rs.mobile.common.R.id.button1_Cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				dlg.dismiss();
				
				listener.onFail("Cancel");
				
			}
		});
		editText_Pwd = (SecurityPasswordEditText) window.findViewById(com.rs.mobile.common.R.id.password);
		((TextView) window.findViewById(com.rs.mobile.common.R.id.textView_TopTitle)).setText(R.string.payment_password);
		editText_Pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		});

	}

	public static void showKeyBoard_gigapay(final Context context, final String custom_code, final String store_custom_code, final String amount, final OnPaymentResultListener listener) {
		dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(false);
		dlg.show();
		Window window = dlg.getWindow();
		window.clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		window.setContentView(com.rs.mobile.common.R.layout.starcard_query_pwdvalidate_layout);
		Button ok = (Button) window.findViewById(com.rs.mobile.common.R.id.button_Query);
		ok.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				payPwd = editText_Pwd.getInputnumber();

				if (payPwd == null || payPwd.equals("") || payPwd.length() < 6) {

					Toast.makeText(context, "请输入您的支付密码", Toast.LENGTH_SHORT).show();

				} else {
					dlg.dismiss();
					//결제 api
					submitOrder_giga(context, custom_code, payPwd, store_custom_code, amount, listener);
				}

			}

		});

		// 关闭alert对话框架
		Button btnCancel = (Button) window.findViewById(com.rs.mobile.common.R.id.button1_Cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				dlg.dismiss();

				listener.onFail("Cancel");

			}
		});
		editText_Pwd = (SecurityPasswordEditText) window.findViewById(com.rs.mobile.common.R.id.password);
		((TextView) window.findViewById(com.rs.mobile.common.R.id.textView_TopTitle)).setText(R.string.payment_password);
		editText_Pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		});

	}
	
	/**
	 * 오프라인 결
	 * @param context
	 * @param order_no
	 * @param order_amount
	 * @param order_Point
	 * @param real_amount
	 * @param sCouponCode
	 * @param listener
	 */
	public static void showKeyBoardOffline(final Context context, 
			final String store_code, 
			final String parter_key, 
			final String order_no, 
			final String order_date,
			final String order_amount,
			final OnPaymentResultListener listener) {

		dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(false);
		dlg.show();
		Window window = dlg.getWindow();
		window.clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		window.setContentView(com.rs.mobile.common.R.layout.starcard_query_pwdvalidate_layout);
		Button ok = (Button) window.findViewById(com.rs.mobile.common.R.id.button_Query);
		ok.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				payPwd = editText_Pwd.getInputnumber();

				if (payPwd == null || payPwd.equals("") || payPwd.length() < 6) {

					Toast.makeText(context, "请输入您的支付密码", Toast.LENGTH_SHORT).show();

				} else {
					dlg.dismiss();
					submitOrderOffline(context, store_code, parter_key, order_no, order_date, order_amount, payPwd, listener);
				}

			}

		});

		// 关闭alert对话框架
		Button btnCancel = (Button) window.findViewById(com.rs.mobile.common.R.id.button1_Cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				dlg.dismiss();
				
				listener.onFail("Cancel");
				
			}
		});
		editText_Pwd = (SecurityPasswordEditText) window.findViewById(com.rs.mobile.common.R.id.password);
		((TextView) window.findViewById(com.rs.mobile.common.R.id.textView_TopTitle)).setText("输入支付密码");
		editText_Pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		});

	}
	
	public static void showKeyBoard_offline(final Context context, final String order_no, final String order_amount,
			final String order_Point, final String real_amount, final String sCouponCode,
			final OnPaymentResultListener listener) {

		dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(false);
		dlg.show();
		Window window = dlg.getWindow();
		window.clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		window.setContentView(com.rs.mobile.common.R.layout.starcard_query_pwdvalidate_layout);
		Button ok = (Button) window.findViewById(com.rs.mobile.common.R.id.button_Query);
		ok.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				payPwd = editText_Pwd.getInputnumber();

				if (payPwd == null || payPwd.equals("") || payPwd.length() < 6) {

					Toast.makeText(context, "请输入您的支付密码", Toast.LENGTH_SHORT).show();

				} else {
					dlg.dismiss();
					submitOrder_offLine(context, order_no, order_amount, order_Point, payPwd, real_amount, sCouponCode,
							listener);

				}

			}

		});

		// 关闭alert对话框架
		Button btnCancel = (Button) window.findViewById(com.rs.mobile.common.R.id.button1_Cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				dlg.dismiss();
				
				listener.onFail("Cancel");
				
			}
		});
		editText_Pwd = (SecurityPasswordEditText) window.findViewById(com.rs.mobile.common.R.id.password);
		((TextView) window.findViewById(com.rs.mobile.common.R.id.textView_TopTitle)).setText("输入支付密码");
		editText_Pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		});

	}

	public static void submitOrder(final Context context, String order_no, String order_amount, String order_Point,
			String pw, String real_amount, final String sCouponCode, final OnPaymentResultListener listener) {

		HashMap<String, String> params = new HashMap<String, String>();

		Encryption encryption = new Encryption();
		encryption.encryption(pw);
		pw = encryption.getPassword();

		params.put(C.KEY_JSON_FM_SPAY_USER_ID, S.get(context, C.KEY_JSON_CUSTOM_CODE));
		params.put(C.KEY_JSON_FM_ORDER_NO, order_no);
		params.put(C.KEY_JSON_FM_ORDER_AMOUNT, order_amount);
		params.put(C.KEY_JSON_FM_SPAYPWD, pw);
		params.put("order_Point", order_Point);
		params.put("real_amount", real_amount);
		params.put("sCouponCode", sCouponCode);
		params.put(C.KEY_JSON_TOKEN, S.getShare(context, C.KEY_JSON_TOKEN, ""));
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// EditorInfo.IME_ACTION_UNSPECIFIED
				listener.onFail(e.getMessage());
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag1) {
				try {

					listener.onResult(data);

					T.showToast(context, data.getString("msg"));

				} catch (Exception e) {
					L.e(e);
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				listener.onFail(responseDescription);
			}
		}, C.WPAYMENT, params);
	}

	public static void submitOrder_giga(final Context context, String custom_code, String password, String store_custom_code,
								   String amount, final OnPaymentResultListener listener) {

		HashMap<String, String> params = new HashMap<String, String>();

		Encryption encryption = new Encryption();
		encryption.encryption(password);
		password = encryption.getPassword();

		params.put("custom_code", custom_code);
		params.put("password", password);
		params.put("store_custom_code", store_custom_code);
		params.put("amount", amount);

		Log.d("sbproject", "custom_code : " + custom_code +", password : " + password +", store_custom_code : " + store_custom_code +", amount : " + amount);
		Toast.makeText(context, "custom_code : " + custom_code +", password : " + password +", store_custom_code : " + store_custom_code +", amount : " + amount, Toast.LENGTH_LONG).show();
		params.put(C.KEY_JSON_TOKEN, S.getShare(context, C.KEY_JSON_TOKEN, ""));
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// EditorInfo.IME_ACTION_UNSPECIFIED
				listener.onFail(e.getMessage());
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag1) {
				try {

					listener.onResult(data);

					T.showToast(context, data.getString("msg"));

				} catch (Exception e) {
					L.e(e);
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				listener.onFail(responseDescription);
			}
		}, C.GIGA_PAYMENT, params);
	}


	
	/**
	 * 오프라인 결제 
	 * @param context
	 * @param store_code
	 * @param parter_key
	 * @param order_no
	 * @param order_date
	 * @param order_amount
	 * @param listener
	 */
	private static void submitOrderOffline(final Context context, 
			String store_code, 
			String parter_key, 
			String order_no,
			String order_date, 
			String order_amount, 
			String pw,
			final OnPaymentResultListener listener) {

		HashMap<String, String> params = new HashMap<String, String>();

		Encryption encryption = new Encryption();
		encryption.encryption(pw);
		pw = encryption.getPassword();

		params.put(C.KEY_JSON_FM_SPAY_USER_ID, S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));
		params.put(C.KEY_JSON_TOKEN, S.getShare(context, C.KEY_JSON_TOKEN, ""));
		params.put(C.KEY_JSON_FM_SPAYPWD, pw);
		params.put("store_code", store_code);
		params.put("partner_key", parter_key);
		params.put("order_no", order_no);
		params.put("order_date", order_date);
		params.put("order_amount", order_amount);
		
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				// EditorInfo.IME_ACTION_UNSPECIFIED
				listener.onFail(e.getMessage());
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag1) {
				// TODO Auto-generated method stub

				try {

					listener.onResult(data);

					T.showToast(context, data.getString("msg"));

				} catch (Exception e) {
					// TODO Auto-generated catch block
					L.e(e);
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				listener.onFail(responseDescription);
			}
		}, C.WPAYMENT_OFFLINE_POS, params);
	}
	
	private static void submitOrder_offLine(final Context context, String order_no, String order_amount, String order_Point,
			String pw, String real_amount, final String sCouponCode, final OnPaymentResultListener listener) {

		HashMap<String, String> params = new HashMap<String, String>();

		Encryption encryption = new Encryption();
		encryption.encryption(pw);
		pw = encryption.getPassword();

		params.put(C.KEY_JSON_FM_SPAY_USER_ID, S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));
		params.put(C.KEY_JSON_FM_ORDER_NO, order_no);
		params.put(C.KEY_JSON_FM_ORDER_AMOUNT, order_amount);
		params.put(C.KEY_JSON_FM_SPAYPWD, pw);
		params.put("order_Point", order_Point);
		params.put("real_amount", real_amount);
		params.put("sCouponCode", sCouponCode);
		params.put("pay_Type", "1");
		params.put(C.KEY_JSON_TOKEN, S.getShare(context, C.KEY_JSON_TOKEN, ""));
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				// EditorInfo.IME_ACTION_UNSPECIFIED
				listener.onFail(e.getMessage());
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag1) {
				// TODO Auto-generated method stub

				try {

					listener.onResult(data);

					T.showToast(context, data.getString("msg"));

				} catch (Exception e) {
					// TODO Auto-generated catch block
					L.e(e);
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				listener.onFail(responseDescription);
			}
		}, C.WPAYMENT_OFFLINE, params);
	}

	public static class Encryption {
		private String userPassword;

		/**
		 * 패스워드 암호화
		 * 
		 * @param userPassword
		 *            사용자 패스워드
		 * @return 암호화 된 사용자 패스워드 암호화 방식 : SHA-512
		 */
		public boolean encryption(String userPassword) {
			MessageDigest md;
			boolean isSuccess;
			String tempPassword = "";

			try {
				md = MessageDigest.getInstance("SHA-512");

				md.update(userPassword.getBytes());

				byte[] mb = md.digest();
				for (int i = 0; i < mb.length; i++) {
					byte temp = mb[i];
					String s = Integer.toHexString(new Byte(temp));
					while (s.length() < 2) {
						s = "0" + s;
					}
					s = s.substring(s.length() - 2);
					tempPassword += s;
				}
				setPassword(tempPassword);
				isSuccess = true;
			} catch (NoSuchAlgorithmException e) {
				isSuccess = false;
				return isSuccess;
			}
			return isSuccess;
		}

		private void setPassword(String temppassword) {
			this.userPassword = temppassword;
		}

		public String getPassword() {
			return userPassword;
		}

	}

	public static void hideKeyBoard() {
		dlg.dismiss();
	}

	public static void alipayMethod(String pay_order_no, String pay_order_amount, final Activity context,
									final int SDK_PAY_FLAG, final Handler mHandler) {

		HashMap<String, String> params = new HashMap<>();
		params.put("pay_order_no", pay_order_no);
		params.put("pay_order_amount", pay_order_amount);
		params.put("payment_type","2");
		params.put("pay_order_type","1");
		OkHttpHelper okHttpHelper = new OkHttpHelper(context);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				String bbbb = e.toString();
			}

			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data, String flag) {
				// TODO Auto-generated method stub
				final PayTask alipay = new PayTask(context);
				try {
					if (data.getInt("Status") == 0) {
						new Thread(new Runnable() {
							public void run() {
								String orderinfo;
								try {
									orderinfo = data.getString("Data");
									Map<String, String> result = alipay.payV2(orderinfo, true);
									Message msg = new Message();
									msg.what = SDK_PAY_FLAG;
									msg.obj = result;
									mHandler.sendMessage(msg);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}).start();

					} else {
						T.showToastL(context, data.getString("message"));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				// alipay.payV2(arg0, arg1)
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				String aaa = data.toString();
			}
		}, C.PAY_MENT_TOTAL_URL + C.PAY_MENT_TOTAL_PATH, params);

	}

	public static void unionPayMethod(String pay_order_no, String pay_order_amount, final Activity act, final Class redo_act) {
		HashMap<String, String> params = new HashMap<>();
		/*params.put("pay_order_no", pay_order_no);
		params.put("pay_order_amount", pay_order_amount);
		params.put("access_token", S.getShare(act, C.KEY_JSON_TOKEN, ""));*/
		params.put("pay_order_no", pay_order_no);
		params.put("pay_order_amount", pay_order_amount);
		params.put("payment_type","3");
		params.put("pay_order_type","1");
		OkHttpHelper okhttp = new OkHttpHelper(act);
		okhttp.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					//Toast.makeText(act, "Bizsuccess" + data.toString(), Toast.LENGTH_LONG).show();
					Log.d("rsapp", data.toString());
					String tn=data.getString("Data");
					UPPayAssistEx.startPay(act, null, null, tn, mMode);
					//PageUtil.jumpToWithFlag(act, redo_act);
				} catch (Exception e) {
					// TODO: handle exception
					L.e(e);
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, C.PAY_MENT_TOTAL_URL + C.PAY_MENT_TOTAL_PATH, params);
		PageUtil.jumpToWithFlag(act, redo_act);
		act.finish();
	}

	public static void wechatPayMethod(String pay_order_no, String pay_order_amount, final Activity act)
	{
		//wechat_api = WXAPIFactory.createWXAPI(act, C.RS_WECHAT_APPID);

		try {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("pay_order_no", pay_order_no);
			params.put("pay_order_amount", pay_order_amount);
			params.put("payment_type", "1");
			params.put("pay_order_type", "1");

			OkHttpHelper helper = new OkHttpHelper(
					act);
			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
				@Override
				public void onNetworkError(Request request,
										   IOException e) {
					// TODO Auto-generated method stub
					String check = e.toString();
				}

				@Override
				public void onBizSuccess(
						String responseDescription,
						JSONObject data, String flag) {
					try {
						//Toast.makeText(act, "Bizsuccess" + data.getString("Data"), Toast.LENGTH_LONG).show();
						JSONObject wexins = new JSONObject(data.getString("Data"));
						Log.d("rsapp", data.toString());

						if (data.getInt("Status") == 0) {
							PayReq req = new PayReq();
							req.appId = wexins.getString("appid");
							req.partnerId = wexins.getString("partnerid");
							req.prepayId = wexins.getString("prepayid");
							req.nonceStr = wexins.getString("noncestr");
							req.timeStamp = wexins.getString("timestamp");
							//String bbbb = wexins.getString("package");
							req.packageValue ="Sign=WXPay";
							req.sign = wexins.getString("sign");
							//req.extData			= json.getString("ordid");
							wechat_api = WXAPIFactory.createWXAPI(act, req.appId);
							wechat_api.sendReq(req);
						} else {
							Log.d("rsapp", wexins.getString("msg"));
						}

						//PageUtil.jumpToWithFlag(act, redo_act);
					} catch (JSONException jex) {
						Log.d("rsapp", jex.toString());
					}
				}

				@Override
				public void onBizFailure(
						String responseDescription,
						JSONObject data, String responseCode) {
					Log.d("rsapp", "onBizFailure" + data.toString());
					//Toast.makeText(act, data.toString(), Toast.LENGTH_LONG).show();
					// TODO Auto-generated method stub

				}
			}, C.PAY_MENT_TOTAL_URL + C.PAY_MENT_TOTAL_PATH, params);
		} catch (Exception ex) {
			String aaa = ex.toString();
		}
		//PageUtil.jumpToWithFlag(act, redo_act);
		//act.finish();

	}

}
