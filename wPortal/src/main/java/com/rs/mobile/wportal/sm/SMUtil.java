package com.rs.mobile.wportal.sm;

import android.app.Activity;

import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.dp.DpMyOrderActivity;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class SMUtil {

	public static void smConfirmStatus(String order_num, final Activity act) {

		HashMap<String, String> params = new HashMap<>();
		params.put("order_num", order_num);
		OkHttpHelper okHttpHelper = new OkHttpHelper(act);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				PageUtil.jumpToWithFlag(act, MyOrderActivity.class);

				act.finish();

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.PAYMENT_STATUSSYNC, params);
	}

	public static void smConfirmStatus_giga(String order_num, final Activity act) {

		HashMap<String, String> params = new HashMap<>();
		params.put("order_num", order_num);
		OkHttpHelper okHttpHelper = new OkHttpHelper(act);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				PageUtil.jumpToWithFlag(act, MyOrderActivity.class);

				act.finish();

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.PAYMENT_STATUSSYNC, params);
	}

	public static void dpConfirmStatus(String order_num, final Activity act) {

		HashMap<String, String> params = new HashMap<>();
		params.put("order_num", order_num);
		OkHttpHelper okHttpHelper = new OkHttpHelper(act);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				PageUtil.jumpToWithFlag(act, DpMyOrderActivity.class);

				act.finish();

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_DP1 + Constant.PAYMENT_STATUSSYNC, params);
	}
}