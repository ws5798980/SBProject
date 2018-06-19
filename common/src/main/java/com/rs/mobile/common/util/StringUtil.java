package com.rs.mobile.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class StringUtil {

	/**
	 * Please use the method called {@link android.text.TextUtils#isEmpty} instead
	 * 
	 * @param str
	 * @return
	 * @author ZhaoYun
	 * @date 2017-3-11
	 */
	public static boolean isEmpty(String str) {
		// TODO
        return str == null || str.length() == 0;

    }

	/**
	 * 遍历map数据
	 * 
	 * */
	public static void map_key_value(Map<String, String> paramsKeyValue,
			String baseUrl) {
		Log.e("map遍历", "baseUrl= " + baseUrl);
		for (String key : paramsKeyValue.keySet()) {
			Log.e("map遍历",
					"key= " + key + " and value= " + paramsKeyValue.get(key));
		}
	}

	/**
	 * String转double转int
	 * 
	 * */
	public static int string_to_int(String string) {
		int intgeo = 0;
		if (string.contains(".")) {
			String str = string.substring(0, string.indexOf("."));
			intgeo = Integer.parseInt(str);
			return intgeo;
		}
		intgeo = Integer.parseInt(string);
		return intgeo;
	}

	/**
	 * 业务相关 将“2014年12月”替换成“2014-12”
	 */
	public static String yearMonthTo(String yearMonthString) {
		return yearMonthString.replace("年", "-").replace("月", "");
	}

	/**
	 * 业务相关 将“2014-12”替换成“2014年12月”
	 */
	public static String yearMonthFrom(String str) {
		return str.replace("-", "年") + "月";
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 保存Cookie
	 */
	public static void savePreference(Context context, String value) {
		SharedPreferences preference = context.getSharedPreferences("cookie",
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putString("cookie", value);
		editor.commit();//
	}

	/**
	 * 得到Cookie
	 */
	public static String getPreference(Context context) {
		SharedPreferences preference = context.getSharedPreferences("cookie",
				Context.MODE_PRIVATE);
		return preference.getString("cookie", "");
	}

	/**
	 * 判断cookie是否存在
	 */
	public static boolean isCookId(Context context) {
		SharedPreferences preference = context.getSharedPreferences("cookie",
				Context.MODE_PRIVATE);
		Log.d("cook", preference.getString("cookie", ""));
        return preference.getString("cookie", "") != null
                && !preference.getString("cookie", "").equals("")
                && !preference.getString("cookie", "").equals("null");
	}
	 public static float formatFloat2(float d) {
	        // 旧方法，已经不再推荐使用
//	        BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);

	        
	        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
	        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);

	        
	        return bg.floatValue();
	    }


//	public static String formatTosepara(float data) {
//		DecimalFormat df = new DecimalFormat("#,###");
//		return df.format(data);
//	}

	public static String formatTosepara(Object data) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(data);
	}
}
