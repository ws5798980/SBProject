package com.rs.mobile.common.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.LoginActivity;
import com.rs.mobile.common.network.OkHttpHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;

import okhttp3.Request;

public class UtilCheckLogin {

	private CheckListener checkListener = null;
	private CheckError checkError = null;

	public void setCheckListener(CheckListener checkListener) {
		this.checkListener = checkListener;
	}

	public void setCheckError(CheckError checkError) {
		this.checkError = checkError;
	}

	public interface CheckListener {
		void onDoNext();

	}

	public interface CheckError {
		void onError();

	}

	private String encryption(String userPassword) {

		MessageDigest md;

		String encritPassword = "";

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
				encritPassword += s;
			}

		} catch (Exception e) {
			String ecryption_error = e.toString();
		}

		return encritPassword;
	}

	/**
	 * 检查匹配登陆（每个要求登陆接口都需要调用）
	 * 
	 * @param context
	 */
	public void checkLogin(final Context context , final boolean needLogin) {

		JSONObject obj = new JSONObject();
		try {
			HashMap<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "application/json;Charset=UTF-8");

			obj.put("deviceNo", Util.getDeviceId(context));
			obj.put("sgin", encryption(Util.getDeviceId(context)
					+ "ycssologin1212121212121"));

			OkHttpHelper helper = new OkHttpHelper(context);

			helper.addGetRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					String netError_result = e.toString();
				}

				@Override
				public void onBizSuccess(String responseDescription,
										 final JSONObject data, final String flag) {

					try {
						if (data.getString("status").equals("1")
								&& data.getString("flag").equals("1501")) {
							S.setShare(context, C.KEY_JSON_TOKEN,
									// data.getString(C.KEY_JSON_TOKEN));
									data.getString(C.KEY_JSON_TOKEN) + "|"
											+ data.getString("ssoId") + "|"
											+ data.getString("custom_code")
											+ "|" + Util.getDeviceId(context));

							S.set(context, C.KEY_JSON_TOKEN,
									// data.getString(C.KEY_JSON_TOKEN));
									data.getString(C.KEY_JSON_TOKEN) + "|"
											+ data.getString("ssoId") + "|"
											+ data.getString("custom_code")
											+ "|" + Util.getDeviceId(context));

							S.set(context, C.KEY_JSON_CUSTOM_CODE, data.getString("custom_code"));
							S.setShare(context, C.KEY_JSON_CUSTOM_CODE, data.getString("custom_code"));


//							S.setShare(context, C.KEY_REQUEST_MEMBER_ID,
//									data.getString("custom_code"));
							/*S.setShare(context, C.KEY_REQUEST_PARENT_ID, data.getString("parent_id"));*/
							if (checkListener != null)
								checkListener.onDoNext();
						} else {
							if(needLogin)
								context.startActivity(new Intent(context,
										LoginActivity.class));
							if (checkError != null)
								checkError.onError();
						}
						checkError = null;

					} catch (Exception e) {

						L.e(e);

					}

				}

//				@Override
//				public void onBizSuccess(String responseDescription,
//						final JSONObject data, final String flag) {
//					try {
//						if (data.getString("status").equals("1")
//								&& data.getString("flag").equals("1501")) {
//							S.set(context, C.KEY_JSON_TOKEN, data.getString(C.KEY_JSON_TOKEN) + "|aaaaaa|" + data.getString("custom_code"));
//							S.set(context, C.KEY_JSON_CUSTOM_CODE, data.getString("custom_code"));
//							S.set(context, C.KEY_JSON_SSOID, data.getString("ssoId"));
////							S.setShare(context, C.KEY_JSON_TOKEN,
////							// data.getString(C.KEY_JSON_TOKEN));
////									data.getString(C.KEY_JSON_TOKEN) + "|"
////											+ data.getString("ssoId") + "|"
////											+ data.getString("custom_code")
////											+ "|" + Util.getDeviceId(context));
////							S.setShare(context, C.KEY_REQUEST_MEMBER_ID,
////									data.getString("custom_code"));
////							/*S.setShare(context, C.KEY_REQUEST_PARENT_ID, data.getString("parent_id"));*/
//							if (checkListener != null)
//								checkListener.onDoNext();
//						} else {
//							if(needLogin)
//								context.startActivity(new Intent(context,
//									LoginActivity.class));
//							if (checkError != null)
//								checkError.onError();
//						}
//						checkError = null;
//
//					} catch (Exception e) {
//
//						L.e(e);
//
//					}
//
//				}

				@Override
				public void onBizFailure(String responseDescription,
						JSONObject data, String responseCode) {
					if(data != null){
						Log.d("rsapp", data.toString());
					}
				}
			}, C.CHECK_LOGIN
					+ "?deviceNo="
					+ Util.getDeviceId(context)
					+ "&sign="
					+ encryption(Util.getDeviceId(context)
							+ "ycssologin1212121212121"));

		} catch (Exception e) {
			e.getMessage();

		}
		Log.d("TAG", C.CHECK_LOGIN
				+ "?deviceNo="
				+ Util.getDeviceId(context)
				+ "&sign="
				+ encryption(Util.getDeviceId(context)
						+ "ycssologin1212121212121"));
	}

}
