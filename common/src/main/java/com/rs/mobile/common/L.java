package com.rs.mobile.common;

import android.util.Log;

public class L {

	public static final String TAG = "w_log";
	
	public static void d(String l) {
		
		Log.d(TAG, l);
		
	}
	
	public static void e(Exception e) {
		
		Log.e(TAG, e.getClass().toString());
		
		Log.e(TAG, "Exception: "+Log.getStackTraceString(e));
		
		e.printStackTrace();
		
	}
	
}
