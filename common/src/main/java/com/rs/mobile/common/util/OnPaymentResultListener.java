package com.rs.mobile.common.util;

import org.json.JSONObject;

public interface OnPaymentResultListener {
		
	void onResult(JSONObject data);
	
	void onFail(String msg);
	
}
