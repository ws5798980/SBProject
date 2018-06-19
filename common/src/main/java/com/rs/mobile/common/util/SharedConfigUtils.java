package com.rs.mobile.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedConfigUtils {
	private static SharedConfigUtils instance;
	private static final String CONFIG_FILE_NAME =  "CONFIG";
	private SharedPreferences sharedPreferences;
	private Editor editor;
	
	private SharedConfigUtils(Context context) {
		sharedPreferences = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}
	
	public static SharedConfigUtils getInstance(Context context) {
		if (instance == null) {
			instance = new SharedConfigUtils(context);
		}
		return instance;
	}
	
	public boolean writeData(String key, String value) {
		boolean result = false;
		editor.putString(key, value);
		result = editor.commit();
		return result;
	}
	public boolean writeData(String key, float value) {
		boolean result = false;
		editor.putFloat(key, value);
		result = editor.commit();
		return result;
	}
	
	public boolean writeData(String key, int value) {
		boolean result = false;
		editor.putInt(key, value);
		result = editor.commit();
		return result;
	}
	
	public boolean writeData(String key, long value) {
		boolean result = false;
		editor.putLong(key, value);
		result = editor.commit();
		return result;
	}
	
	public boolean writeData(String key, boolean value) {
		boolean result = false;
		editor.putBoolean(key, value);
		result = editor.commit();
		return result;
	}
	 
	public String readString(String key, String def) {
		String result = sharedPreferences.getString(key, def);
		return result;
	}
	public float readFloat(String key, float def) {
		float result = sharedPreferences.getFloat(key, def);
		return result;
	}
	public int readInt(String key, int def) {
		int result = sharedPreferences.getInt(key, def);
		return result;
	}
	
	public long readLong(String key, long def) {
		long result = sharedPreferences.getLong(key, def);
		return result;
	}
	
	public boolean readBoolean(String key, boolean def) {
		boolean result = sharedPreferences.getBoolean(key, def);
		return result;
	}
	
	public boolean deleteItem(String item) {
		boolean result = false;
		editor.remove(item);
		result = editor.commit();
		return result;
	}
	
	public boolean isExist(String item) {
		boolean result = false;
		result = sharedPreferences.contains(item);
		return result;
	}
	
	public void clearConfig() {
		editor.clear().commit();
	}
}
