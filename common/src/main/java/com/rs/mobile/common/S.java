package com.rs.mobile.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class S {

	public static final String KEY_MAIN_IMAGE_JSON = "key_sharedPreferences_main_image_json";
	
	private static SharedPreferences pref;
	
	private static SharedPreferences.Editor editor;
	
	/**
	 * SharedPreferences get
	 * @param context
	 * @param key
	 * @return value
	 */
	public static String get(Context context, String key) {
		
		String returnValue = "";
		
		try {
			
			if (pref == null) {
				
				pref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
				
			}
			
			returnValue = pref.getString(key, "");
			
			pref = null;
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return returnValue;
		
	}
	
	/**
	 * SharedPreferences get
	 * @param context
	 * @param key
	 * @return value
	 */
	public static String get(Context context, String key, String defaultValue) {
		
		String returnValue = "";
		
		try {
			
			if (pref == null) {
				
				pref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
				
			}
			
			returnValue = pref.getString(key, defaultValue);
			
			pref = null;
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return returnValue;
		
	}
	
	/**
	 * SharedPreferences set
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void set(Context context, String key, String value) {
		
		try {
			
			if (pref == null) {
				
				pref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
				
			}
			
			editor = pref.edit();
			
			editor.putString(key, value);
			
			editor.commit();
			
			editor = null;
					
			pref = null;
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	/**
	 * SharedPreferences set
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setShare(Context context, String key, String value) {
		
		try {
			
			if (pref == null) {
				
				pref = context.getSharedPreferences("config", Activity.MODE_PRIVATE);
				
			}
			
			editor = pref.edit();
			
			editor.putString(key, value);
			
			editor.commit();
			
			editor = null;
					
			pref = null;
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	/**
	 * SharedPreferences set
	 * @param context
	 * @param key
	 * @param value
	 */
	public static String getShare(Context context, String key, String defaultValue) {
		
		String returnValue = "";
		
		try {
			
			if (pref == null) {
				
				pref = context.getSharedPreferences("config", Activity.MODE_PRIVATE);
				
			}
			
			returnValue = pref.getString(key, defaultValue);
			
			pref = null;
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return returnValue;
		
	}
	
	public static void addList(Context context, String key, String value) {
		
		try {
			
			String data = get(context, key);
			
			if (data == null || data.equals("")) {
				
				data = value;
				
			} else {
				
				data = data + "," + value;
				
			}
			
			set(context, key, data);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public static void deleteList(Context context, String key, int position) {
		
		try {
			
			String data = get(context, key);
			
			String[] list = data.split(",");
			
			data = "";
			
			for (int i = 0; i < list.length; i++) {
				
				if (i == position) {
					
					continue;
					
				} else {
					
					if (data.equals("")) {
						
						data = list[i];
						
					} else {
						
						data = data + "," + list[i];
						
					}
					
				}
				
			}
			
			set(context, key, data);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public static String[] getList(Context context, String key) {
		
		try {
			
			String data = get(context, key);
			
			if (data == null || data.equals(""))
				return null;
			
			return data.split(",");
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		
		return null;
	}
	
}
