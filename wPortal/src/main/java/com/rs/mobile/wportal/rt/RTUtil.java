package com.rs.mobile.wportal.rt;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.common.util.OnPaymentResultListener;

import android.content.Context;
import okhttp3.Request;

public class RTUtil {
	
	/**
	 * 예약 취소
	 * 
	 * @param context
	 * @param reserveID
	 * @param cancelCode
	 * @param cancelReason
	 */
	public static void cancelReserver(final Context context, String reserveID, String cancelCode, String cancelReason,
			final OnPaymentResultListener listener) {

		try {

			OkHttpHelper helper = new OkHttpHelper(context);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
					listener.onFail(e.getMessage());
				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(all_data);

						String status = data.getString("status");

						if (status != null && status.equals("1")) {

							listener.onResult(data);

							T.showToast(context, data.getString("msg"));

						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
					listener.onFail(responseDescription);
				}
			}, Constant.BASE_URL_RT + Constant.RT_CANCEL_RESERVE + "?userId=" + S.getShare(context, C.KEY_REQUEST_MEMBER_ID, "")
					+ "&token=" + S.getShare(context, C.KEY_JSON_TOKEN, "") + "&reserveID=" + reserveID + "&cancelCode="
					+ cancelCode + "&cancelReason=" + cancelReason, params);

		} catch (Exception e) {

			L.e(e);

		}

	}
	
}