package com.rs.mobile.common;

import android.content.Context;
import android.widget.Toast;

/**
 * 토스트 관련 공통 클래스
 * 
 * @author JoonHo
 *
 */
public class T {

	private static Toast toast;
	
	/**
	 * 짧은 토스트 메세지
	 */
	public static void showToast(Context context, int str) {
		
		try {
			
			checkToast(context);
			
			toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
			toast.show();
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public static void showToast(Context context, String str) {
		
		try {
			
			checkToast(context);
			
			toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
			toast.show();
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	/**
	 * 짧은 토스트 메세지
	 */
	public static void showToastL(Context context, int str) {
		
		try {
			
			checkToast(context);
			
			toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
			toast.show();
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public static void showToastL(Context context, String str) {
		
		try {
			
			checkToast(context);
			
			toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
			toast.show();
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public static void checkToast(Context context) {
		
		try {
			
			if (toast != null) {
				
				toast.cancel();
			
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
}
